package com.alanensina.orderservice.controllers;

import com.alanensina.basedomains.dto.order.OrderCreateRequestDTO;
import com.alanensina.basedomains.dto.order.OrderCreateResponseDTO;
import com.alanensina.orderservice.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponseDTO> createOrder(@RequestBody OrderCreateRequestDTO dto){
        return orderService.create(dto);
    }
}
