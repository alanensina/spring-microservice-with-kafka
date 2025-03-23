package com.alanensina.basedomains.dto.product;

import java.math.BigDecimal;

public record ProductCreateRequestDTO(
        String name,
        BigDecimal price,
        boolean available,
        int stock
) {
}
