package com.ordermanagement.OrderLoggingService.listener;

import com.ordermanagement.OrderLoggingService.config.RabitConfig;
import com.ordermanagement.OrderLoggingService.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderLogger {

    private static final Logger logger = LoggerFactory.getLogger(OrderLogger.class);

    @RabbitListener(queues = RabitConfig.QUEUE_ORDERS)
    public void processOrder(Order order) {
        logger.info("order recieved " + order);
    }
}
