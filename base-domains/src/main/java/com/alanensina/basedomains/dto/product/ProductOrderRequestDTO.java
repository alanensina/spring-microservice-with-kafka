package com.alanensina.basedomains.dto.product;

import java.util.UUID;

public record ProductOrderRequestDTO(
        UUID productId,
        int quantity
) {
}
