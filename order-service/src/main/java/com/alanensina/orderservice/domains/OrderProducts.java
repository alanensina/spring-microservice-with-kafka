package com.alanensina.orderservice.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "ORDER_PRODUCTS")
public class OrderProducts {

    @Id
    @GeneratedValue
    private UUID orderProductsId;


    private UUID orderId;
    private UUID productId;
    private int quantity;

    public UUID getOrderProductsId() {
        return orderProductsId;
    }

    public void setOrderProductsId(UUID orderProductsId) {
        this.orderProductsId = orderProductsId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
