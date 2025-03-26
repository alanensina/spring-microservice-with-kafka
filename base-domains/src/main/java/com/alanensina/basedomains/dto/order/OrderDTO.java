package com.alanensina.basedomains.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID orderId,
        UUID userId,
        String name,
        String email,
        LocalDateTime date,
        String status,
        boolean paid,
        BigDecimal totalPrice,
        List<OrderProductsDTO> products
) {
}
