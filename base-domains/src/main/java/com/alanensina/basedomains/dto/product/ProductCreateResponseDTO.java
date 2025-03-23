package com.alanensina.basedomains.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreateResponseDTO(
        UUID productId,
        String name,
        BigDecimal price,
        boolean available,
        int stock
) {
}
