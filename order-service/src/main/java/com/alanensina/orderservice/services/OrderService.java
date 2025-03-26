package com.alanensina.orderservice.services;

import com.alanensina.basedomains.dto.order.*;
import com.alanensina.basedomains.dto.product.ProductOrderRequestDTO;
import com.alanensina.basedomains.enums.OrderStatus;
import com.alanensina.basedomains.exceptions.OrderErrorException;
 import com.alanensina.orderservice.domains.Order;
import com.alanensina.orderservice.domains.OrderProducts;
import com.alanensina.orderservice.domains.Product;
import com.alanensina.orderservice.domains.User;
import com.alanensina.orderservice.kafka.OrderProducer;
import com.alanensina.orderservice.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository, UserService userService, ProductService productService) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public ResponseEntity<OrderCreateResponseDTO> create(OrderCreateRequestDTO dto) {

        User user = userService.findUserById(dto.userId());

        List<OrderProducts> products = new ArrayList<>();

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPaid(false);
        newOrder.setStatus(OrderStatus.CREATED);
        newOrder.setTotalPrice(BigDecimal.ZERO);

        updateStock(products, dto.products(), newOrder);

        newOrder.setProducts(products);

        try{
            newOrder = orderRepository.save(newOrder);
        } catch (Exception e) {
            String errorMessage = "Error to save an order. Order: " + newOrder + ". Error message: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new OrderErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<OrderProductsDTO> productsDTO = getOrderProductsDTO(newOrder.getProducts());

        OrderDTO orderDTO = new OrderDTO(newOrder.getOrderId(),
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                newOrder.getDate(),
                newOrder.getStatus().name(),
                newOrder.isPaid(),
                newOrder.getTotalPrice(),
                productsDTO);

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

    private List<OrderProductsDTO> getOrderProductsDTO(List<OrderProducts> products) {
        return products.stream().map(
                p -> new OrderProductsDTO(
                        p.getProduct().getProductId(),
                        p.getProduct().getName(),
                        p.getProduct().getPrice(),
                        p.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(
                                        p.getQuantity())),
                        p.getQuantity()
                        )
        ).toList();
    }

    private void updateStock(List<OrderProducts> orderProducts, List<ProductOrderRequestDTO> requestedProducts, Order order) {
        requestedProducts.forEach(req -> {
            OrderProducts orderProduct = new OrderProducts();
            Product p = productService.updateStock(req.productId(), req.quantity());
            orderProduct.setProduct(p);
            orderProduct.setQuantity(req.quantity());
            orderProduct.setOrder(order);
            orderProducts.add(orderProduct);

            order.setTotalPrice(
                    order.getTotalPrice().add(
                            new BigDecimal(req.quantity()).multiply(p.getPrice())
                    )
            );
        });
    }
}
