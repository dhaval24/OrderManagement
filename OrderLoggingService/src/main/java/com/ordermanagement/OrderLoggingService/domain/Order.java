package com.ordermanagement.OrderLoggingService.domain;

import java.io.Serializable;
/**
 * An entity class that represents order.
 */
public class Order implements Serializable {

    /**
     * a unique id used to represent this order
     */
    private String orderNumber;

    /**
     * Product associated with the order
     */
    private String productId;

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
