package com.alanensina.basedomains.dto.order;

import java.io.Serializable;

public class OrderEvent implements Serializable {

    private String message;
    private String status;
    private OrderDTO order;

    public OrderEvent(String message, String status, OrderDTO order) {
        this.message = message;
        this.status = status;
        this.order = order;
    }

    public OrderEvent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", order=" + order +
                '}';
    }
}
