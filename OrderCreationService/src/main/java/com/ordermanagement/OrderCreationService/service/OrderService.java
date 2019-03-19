package com.ordermanagement.OrderCreationService.service;

import com.microsoft.applicationinsights.TelemetryClient;
import com.ordermanagement.OrderCreationService.config.RabitConfig;
import com.ordermanagement.OrderCreationService.domain.Order;
import com.ordermanagement.OrderCreationService.exceptions.OrderNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order) throws OrderNullException {
        if (order == null) {
            throw new OrderNullException();
        }
        try {
            this.rabbitTemplate.convertAndSend(RabitConfig.QUEUE_ORDERS, order);

        } catch (AmqpException e) {
            logger.error("Exception occurred while putting data in queue " + e);
        }
    }

}
