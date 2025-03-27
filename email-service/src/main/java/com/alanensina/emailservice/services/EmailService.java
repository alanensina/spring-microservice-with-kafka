package com.alanensina.emailservice.services;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.exceptions.EmailErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender sender;

    @Value(value = "${spring.mail.username}")
    private String senderName;

    public EmailService(JavaMailSender sender) {
        this.sender = sender;
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

    private String getNewOrderTextMessage(OrderDTO dto) {

        String header = """
                Hello %s
                
                Your order has been placed!
                Check the list of items below:
                
                """.formatted(dto.name());

        StringBuilder message = new StringBuilder(header);

        dto.products().forEach(p -> {
            message.append(
                    p.productName()).append("- Qtd: ").append(p.quantity()).append(" - Price: ").append(p.unitPrice()).append("€ - Total: ").append(p.total()).append("€ \n");
        });

        message.append("\n");
        message.append("Total price: ").append(dto.totalPrice()).append("€").append("\n");
        message.append("Thank you!");

        return message.toString();
    }

    public void sendPaidEmail(OrderDTO dto) {
        throw new EmailErrorException("Email not developed yet!", HttpStatus.BAD_REQUEST);
    }

    public void sendShippedEmail(OrderDTO dto) {
        throw new EmailErrorException("Email not developed yet!", HttpStatus.BAD_REQUEST);
    }

    public void sendFinishedEmail(OrderDTO dto) {
        throw new EmailErrorException("Email not developed yet!", HttpStatus.BAD_REQUEST);
    }

    public void sendCancelledEmail(OrderDTO dto) {
        throw new EmailErrorException("Email not developed yet!", HttpStatus.BAD_REQUEST);
    }
}
