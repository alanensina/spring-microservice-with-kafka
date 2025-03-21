package com.alanensina.basedomains.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        String name,
        int quantity,
        BigDecimal price
) {
}
