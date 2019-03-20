package com.ordermanagement.OrderCreationService.domain;

import java.io.Serializable;

public class Order implements Serializable {

    private String orderNumber;

    private String productId;

    private String spanId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
