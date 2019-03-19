package com.ordermanagement.OrderLoggingService.domain;

import java.io.Serializable;

public class Order implements Serializable {

    private String orderNumber;

    private String productId;

    private String parentTraceId;

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

    public String getParentTraceId() {
        return parentTraceId;
    }

    public void setParentTraceId(String parentTraceId) {
        this.parentTraceId = parentTraceId;
    }
}
