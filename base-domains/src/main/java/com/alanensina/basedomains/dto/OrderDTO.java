package com.alanensina.basedomains.dto;

import java.math.BigDecimal;

public record OrderDTO(
        String orderId,
        String name,
        int quantity,
        BigDecimal price
) {
}
