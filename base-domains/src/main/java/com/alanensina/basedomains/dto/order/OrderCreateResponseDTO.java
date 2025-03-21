package com.alanensina.basedomains.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderCreateResponseDTO(
        UUID orderId,
        UUID userId,
        LocalDateTime date,
        String status,
        boolean paid,
        BigDecimal totalPrice
) {
}
