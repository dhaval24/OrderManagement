package com.ordermanagement.OrderLoggingService.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabitConfig {

    public static final String QUEUE_ORDERS = "orders-queue";
    public static final String EXCHANGE_ORDERS = "orders-exchange";

    @Bean
    Queue ordersQueue() {
        return QueueBuilder.durable(QUEUE_ORDERS).build();
    }


    @Bean
    TopicExchange ordersExchange() {
        return new TopicExchange(EXCHANGE_ORDERS);
    }

    @Bean
    Binding binding(Queue ordersQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(QUEUE_ORDERS);
    }
}
