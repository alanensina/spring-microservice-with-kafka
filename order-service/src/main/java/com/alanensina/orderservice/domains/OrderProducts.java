package com.alanensina.orderservice.domains;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ORDER_PRODUCTS")
public class OrderProducts {

    @Id
    @GeneratedValue
    private UUID orderProductsId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    public UUID getOrderProductsId() {
        return orderProductsId;
    }

    public void setOrderProductsId(UUID orderProductsId) {
        this.orderProductsId = orderProductsId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
