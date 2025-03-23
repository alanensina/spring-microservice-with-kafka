package com.alanensina.stockservice.kafka;

import com.alanensina.basedomains.dto.order.OrderEvent;
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
    public void consume(OrderEvent event){
        LOGGER.info(String.format("Order event received in Stock-Service. %s", event.toString()));

        //TODO: need to call the StockService to save the products


    }
}
