package com.alanensina.basedomains.utils;

import com.alanensina.basedomains.dto.order.OrderDTO;
import com.alanensina.basedomains.dto.order.OrderProductsDTO;
import com.alanensina.basedomains.exceptions.EventUtilsErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

public class EventUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventUtils.class);

    private EventUtils(){}

    public static void validateEvent(OrderDTO dto) {

        if(dto == null){
            String errorMessage = "OrderDTO received is null.";
            LOGGER.error(errorMessage);
            throw new EventUtilsErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(dto.orderId() == null || dto.userId() == null || dto.name().isEmpty() ||
                dto.email().isEmpty() || dto.date() == null || dto.status().isEmpty() ||
                dto.totalPrice() == null ||dto.products() == null){
            String errorMessage = "One or more fields of the OrderDTO is null or empty: " + dto;
            LOGGER.error(errorMessage);
            throw new EventUtilsErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<OrderProductsDTO> products = dto.products();

        if(products.isEmpty()){
            String errorMessage = "List of OrderProductsDTO received is null or empty. OrderDTO:" + dto;
            LOGGER.error(errorMessage);
            throw new EventUtilsErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        products.forEach(p -> {
            if(p.productId() == null || p.productName().isEmpty() || p.quantity() <= 0 ||
                    p.total() == null || p.unitPrice() == null){
                String errorMessage = "One or more products of the OrderProductsDTO is null or empty.";
                LOGGER.error(errorMessage);
                throw new EventUtilsErrorException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }
}
