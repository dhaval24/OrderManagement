package com.ordermanagement.OrderCreationService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * An entity class that represents order.
 */
@Entity
@Table(name="ordertable")
public class Order implements Serializable {

    /**
     * a unique id used to represent this order
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderNumber;

    /**
     * Product associated with the order
     */
    @Column(name = "productid")
    private String productId;

    /**
     * Product name
     */
    @Column(name = "productname")
    private String productName;

    protected Order() {
    }

    public Order(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "orderNumber: " + orderNumber + " productId: " + productId + " productName: " + productName;
    }

}
