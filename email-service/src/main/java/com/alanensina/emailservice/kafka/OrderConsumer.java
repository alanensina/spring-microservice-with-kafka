package com.alanensina.emailservice.kafka;

import com.alanensina.basedomains.dto.order.OrderEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderEventDTO event){
        LOGGER.info(String.format("Order event received in Email-Service. %s", event.toString()));

        //TODO: Need to call the email service to send the email

    }
}
