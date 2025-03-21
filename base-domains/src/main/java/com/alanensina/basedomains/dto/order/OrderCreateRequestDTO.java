package com.alanensina.basedomains.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreateRequestDTO(
        UUID userId,
        BigDecimal totalPrice
) {
}
