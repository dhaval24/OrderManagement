package com.ordermanagement.OrderCreationService.controller;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.Duration;
import com.microsoft.applicationinsights.telemetry.RemoteDependencyTelemetry;
import com.microsoft.applicationinsights.web.internal.correlation.TraceContextCorrelation;
import com.ordermanagement.OrderCreationService.domain.Order;
import com.ordermanagement.OrderCreationService.service.OrderService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService service;

    @Autowired
    TelemetryClient telemetryClient;

    @PostMapping
    public void placeOrder(@RequestBody Order order) {

        service.createOrder(order);

        boolean success = false;

        // create a ChildTraceParent from the request parent.
        String traceParent = TraceContextCorrelation.generateChildDependencyTraceparent();

        // Create ApplicationInsights format child correlationId from W3C format Traceparent.
        String childId = TraceContextCorrelation.createChildIdFromTraceparentString(traceParent);

        // Create a new AMQP CorrelationData object with child id which can be transmitted along with the message.
        CorrelationData correlationData = new CorrelationData(childId);
        long start = System.currentTimeMillis();
        try {
            service.sendOrder(order, correlationData);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();

            // Create a dependency telemetry of type RabitMQ
            RemoteDependencyTelemetry rdd = new RemoteDependencyTelemetry("RabbitMQ",
                    "Publish", new Duration(end-start), success);
            rdd.setTimestamp(new Date());
            rdd.setType("RabbitMQ");

            // set the ID of this telemetry item (used to correlate with parent operation - in this case request)
            rdd.setId(childId);
            telemetryClient.trackDependency(rdd);
        }
    }
}
