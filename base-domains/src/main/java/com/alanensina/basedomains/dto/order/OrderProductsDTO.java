package com.alanensina.basedomains.dto.order;

import java.util.UUID;

public record OrderProductsDTO(
        UUID productId,
        int quantity

) {
}
