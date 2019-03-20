package com.ordermanagement.OrderLoggingService.listener;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.RequestTelemetry;
import com.microsoft.applicationinsights.web.internal.RequestTelemetryContext;
import com.microsoft.applicationinsights.web.internal.ThreadContext;
import com.microsoft.applicationinsights.web.internal.correlation.tracecontext.Traceparent;
import com.ordermanagement.OrderLoggingService.config.RabitConfig;
import com.ordermanagement.OrderLoggingService.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderLogger {

    private static final Logger logger = LoggerFactory.getLogger(OrderLogger.class);

    @Autowired
    TelemetryClient telemetryClient;

    @RabbitListener(queues = RabitConfig.QUEUE_ORDERS)
    public void processOrder(Order order) {
        RequestTelemetry requestTelemetry = createRequestTelemetry(order);
        logger.info("order received " + order.toString());
        logger.info("orderNumber " + order.getOrderNumber());
        logger.info("productId " + order.getProductId());
        telemetryClient.trackRequest(requestTelemetry);
    }

    private RequestTelemetry createRequestTelemetry(Order order) {
        RequestTelemetryContext requestTelemetryContext = new RequestTelemetryContext(new Date().getTime());
        RequestTelemetry requestTelemetry = requestTelemetryContext.getHttpRequestTelemetry();

        String incomingTraceId = getIncomingTraceParent(order.getCorrelationId());
        String incomingSpanId = getIncomingSpanId(order.getCorrelationId());
        Traceparent traceparent = new Traceparent(0, incomingTraceId, null, 0);

        requestTelemetry.setId("|" + traceparent.getTraceId() + "." + traceparent.getSpanId()
                + ".");

        requestTelemetry.getContext().getOperation().setId(incomingTraceId);
        requestTelemetry.getContext().getOperation().setParentId(order.getCorrelationId());

        requestTelemetry.setName("Process message");
        requestTelemetry.setSource("type:RabbitMQ | " + RabitConfig.QUEUE_ORDERS);
        ThreadContext.setRequestTelemetryContext(requestTelemetryContext);
        return requestTelemetry;
    }

    private String getIncomingTraceParent(String diagnosticId) {
        diagnosticId = diagnosticId.substring(1, diagnosticId.length()-1);
        return diagnosticId.split("\\.")[0];
    }

    private String getIncomingSpanId(String diagnosticId) {
        diagnosticId = diagnosticId.substring(1, diagnosticId.length()-1);
        return diagnosticId.split("\\.")[1];
    }

}
