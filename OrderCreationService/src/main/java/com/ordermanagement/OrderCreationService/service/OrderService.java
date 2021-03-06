package com.ordermanagement.OrderCreationService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.OrderCreationService.config.RabbitConfig;
import com.ordermanagement.OrderCreationService.domain.Order;
import com.ordermanagement.OrderCreationService.exceptions.OrderNullException;
import com.ordermanagement.OrderCreationService.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order, CorrelationData correlationData) throws OrderNullException {
        if (order == null) {
            throw new OrderNullException();
        }
        try {
            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, order, correlationData);

        } catch (AmqpException e) {
            logger.error("Exception occurred while putting data in queue " + e);
        }
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

}
