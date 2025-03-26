package com.alanensina.basedomains.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderProductsDTO(
        UUID productId,
        String productName,
        BigDecimal unitPrice,
        BigDecimal total,
        int quantity
) {
}
