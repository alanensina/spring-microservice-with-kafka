package com.alanensina.emailservice.kafka;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.dto.order.OrderEventDTO;
import com.alanensina.emailservice.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private final EmailService emailService;

    public OrderConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEventDTO event){
        LOGGER.info(String.format("Order event received in Email-Service. %s", event.toString()));

        OrderDTO dto = event.orderDTO();

        emailService.validateEvent(dto);

        var status = dto.status();

        //TODO: need to create more emails for each type of status
        //TODO: need to clean the code too
        switch(status){

            case "CREATED":
                emailService.sendNewOrderEmail(dto);
                break;

            default:
                break;
        }
    }
}
