package com.alanensina.emailservice.kafka;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.dto.order.OrderEventDTO;
import com.alanensina.basedomains.utils.EventUtils;
import com.alanensina.emailservice.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.alanensina.basedomains.enums.OrderConstants.*;


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
        LOGGER.info("Order event received in Email-Service: " + event.toString());

        OrderDTO dto = event.orderDTO();

        EventUtils.validateEvent(dto);

        var status = dto.status();

        switch(status){

            case CREATED:
                emailService.sendNewOrderEmail(dto);
                break;

            case PAID:
                emailService.sendPaidEmail(dto);
                break;

            case SHIPPED:
                emailService.sendShippedEmail(dto);
                break;

            case FINISHED:
                emailService.sendFinishedEmail(dto);
                break;

            case CANCELLED:
                emailService.sendCancelledEmail(dto);
                break;

            default:
                LOGGER.error("Status not found!");
                break;
        }
    }
}
