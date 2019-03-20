package com.ordermanagement.OrderCreationService.controller;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.web.internal.ThreadContext;
import com.microsoft.applicationinsights.web.internal.correlation.TraceContextCorrelation;
import com.ordermanagement.OrderCreationService.domain.Order;
import com.ordermanagement.OrderCreationService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @Autowired
    TelemetryClient telemetryClient;

    @PostMapping
    public void placeOrder(@RequestBody Order order) {
        boolean success = false;
        RequestTelemetry requestTelemetry = ThreadContext.getRequestTelemetryContext().getHttpRequestTelemetry();
        order.setCorrelationId(requestTelemetry.getId());
        long start = System.currentTimeMillis();
        try {
            service.sendOrder(order);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            RemoteDependencyTelemetry rdd = new RemoteDependencyTelemetry("Queue",
                    "Publish", new Duration(end-start), success);
            String traceParent = TraceContextCorrelation.generateChildDependencyTraceparent();
            String childId = TraceContextCorrelation.createChildIdFromTraceparentString(traceParent);
            rdd.setId(childId);
            telemetryClient.trackDependency(rdd);
        }
    }
}
