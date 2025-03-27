package com.alanensina.emailservice.services;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.dto.order.OrderProductsDTO;
import com.alanensina.basedomains.exceptions.EmailErrorException;
import com.alanensina.basedomains.exceptions.OrderErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender sender;

    @Value(value = "${spring.mail.username}")
    private String senderName;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void validateEvent(OrderDTO dto) {

        if(dto == null){
            String errorMessage = "OrderDTO received is null.";
            LOGGER.error(errorMessage);
            throw new EmailErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(dto.orderId() == null || dto.userId() == null || dto.name().isEmpty() ||
                dto.email().isEmpty() || dto.date() == null || dto.status().isEmpty() ||
                dto.totalPrice() == null ||dto.products() == null){
            String errorMessage = "One or more fields of the OrderDTO is null or empty: " + dto;
            LOGGER.error(errorMessage);
            throw new EmailErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<OrderProductsDTO> products = dto.products();

        if(products.isEmpty()){
            String errorMessage = "List of OrderProductsDTO received is null or empty. OrderDTO:" + dto;
            LOGGER.error(errorMessage);
            throw new EmailErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        products.forEach(p -> {
            if(p.productId() == null || p.productName().isEmpty() || p.quantity() <= 0 ||
                    p.total() == null || p.unitPrice() == null){
                String errorMessage = "One or more products of the OrderProductsDTO is null or empty.";
                LOGGER.error(errorMessage);
                throw new EmailErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    public void sendNewOrderEmail(OrderDTO dto) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.email());
            message.setSubject("New order placed: ID" + dto.orderId());
            message.setText(getNewOrderTextMessage(dto));
            sender.send(message);
        } catch (Exception e) {
            String errorMessage = "Error to send NewOrderEmail. Error: " +e.getMessage();
            LOGGER.error(errorMessage);
            throw new EmailErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TODO: need to develop the text message
    private String getNewOrderTextMessage(OrderDTO dto) {
        return "Email text message." + dto;
    }
}
