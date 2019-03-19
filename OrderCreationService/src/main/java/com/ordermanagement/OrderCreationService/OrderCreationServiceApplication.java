package com.ordermanagement.OrderCreationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class OrderCreationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderCreationServiceApplication.class, args);
	}

}
