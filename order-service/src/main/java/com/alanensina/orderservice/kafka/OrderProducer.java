package com.alanensina.orderservice.kafka;

import com.alanensina.basedomains.dto.order.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final NewTopic topic;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEvent event){
        LOGGER.info(String.format("Sending Order event -> %s", event.toString()));

        try{
            var message = MessageBuilder
                    .withPayload(event)
                    .setHeader(KafkaHeaders.TOPIC, topic.name())
                    .build();
            kafkaTemplate.send(message);
        } catch (Exception e) {
            LOGGER.error(String.format("Error to send an Order event -> %s", event));
            throw new RuntimeException(e);
        }

        LOGGER.info(String.format("Order event sent successfully! -> %s", event.toString()));
    }
}
