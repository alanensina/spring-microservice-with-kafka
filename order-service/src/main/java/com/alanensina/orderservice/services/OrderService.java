package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.order.OrderCreateRequestDTO;
import com.alanensina.basedomains.dto.order.OrderCreateResponseDTO;
import com.alanensina.basedomains.enums.OrderStatus;
import com.alanensina.basedomains.exceptions.OrderErrorException;
import com.alanensina.basedomains.exceptions.UserErrorException;
import com.alanensina.orderservice.domains.Order;
import com.alanensina.orderservice.domains.User;
import com.alanensina.orderservice.kafka.OrderProducer;
import com.alanensina.orderservice.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository, UserService userService) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public ResponseEntity<OrderCreateResponseDTO> create(OrderCreateRequestDTO dto) {

        User user = userService.findUserById(dto.userId());

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTotalPrice(dto.totalPrice());
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPaid(false);
        newOrder.setStatus(OrderStatus.CREATED);

        try{
            LOGGER.info(String.format("Saving order: %s", newOrder));
            newOrder = orderRepository.save(newOrder);
            LOGGER.info(String.format("Order saved successfully: %s", newOrder));

            return ResponseEntity.ok(new OrderCreateResponseDTO(
                    newOrder.getOrderId(),
                    user.getUserId(),
                    newOrder.getDate(),
                    newOrder.getStatus().name(),
                    newOrder.isPaid(),
                    newOrder.getTotalPrice()
            ));
        } catch (Exception e) {
            String errorMessage = "Error to save an order. Order: " + newOrder + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
