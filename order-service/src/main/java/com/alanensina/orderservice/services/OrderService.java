package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.order.OrderCreateRequestDTO;
import com.alanensina.basedomains.dto.order.OrderCreateResponseDTO;
import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.dto.order.OrderEventDTO;
import com.alanensina.basedomains.dto.product.UpdateProductStockByOrder;
import com.alanensina.basedomains.enums.OrderStatus;
import com.alanensina.basedomains.exceptions.OrderErrorException;
 import com.alanensina.orderservice.domains.Order;
import com.alanensina.orderservice.domains.User;
import com.alanensina.orderservice.kafka.OrderProducer;
import com.alanensina.orderservice.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${stock.api.base.url}")
    private String STOCK_API_BASE_URL;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository, UserService userService, RestTemplate restTemplate) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<OrderCreateResponseDTO> create(OrderCreateRequestDTO dto) {

        User user = userService.findUserById(dto.userId());
        List<UpdateProductStockByOrder> orderProducts = new ArrayList<>();

        updateStock(dto, orderProducts, user);


        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPaid(false);
        newOrder.setStatus(OrderStatus.CREATED);

        try{
            newOrder = orderRepository.save(newOrder);
        } catch (Exception e) {
            String errorMessage = "Error to save an order. Order: " + newOrder + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        OrderDTO orderDTO = new OrderDTO(newOrder.getOrderId(),
                user.getUserId(),
                newOrder.getDate(),
                newOrder.getStatus().name(),
                newOrder.isPaid(),
                newOrder.getTotalPrice());

        OrderEventDTO event = new OrderEventDTO(orderDTO);

        try{
            orderProducer.sendMessage(event);
        } catch (Exception e) {
            String errorMessage = "Error to send event to Kafka. Event: " + event + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(new OrderCreateResponseDTO(
                    newOrder.getOrderId(),
                    user.getUserId(),
                    newOrder.getDate(),
                    newOrder.getStatus().name(),
                    newOrder.isPaid(),
                    newOrder.getTotalPrice()
        ));
    }

    private void updateStock(OrderCreateRequestDTO dto, List<UpdateProductStockByOrder> orderProducts, User user) {
        try{
            List products = restTemplate.getForObject(STOCK_API_BASE_URL+"/available", List.class);

            if(products == null || products.isEmpty()){
                String errorMessage = "Unable to process an new order. Products stock is empty.";
                LOGGER.error(errorMessage);
                throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch (Exception e) {
            String errorMessage = "Error to get the available products. Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<OrderCreateResponseDTO>> list() {

        //TODO: NEED TO DEVELOP
        return ResponseEntity.ok(Collections.emptyList());
    }
}
