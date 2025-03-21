package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.orderservice.kafka.OrderProducer;
import com.alanensina.orderservice.repositories.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<OrderDTO> create(OrderDTO dto) {





        return null;
    }
}
