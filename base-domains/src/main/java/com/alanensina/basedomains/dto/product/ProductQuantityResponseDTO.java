package com.alanensina.basedomains.dto.product;

import java.util.UUID;

public record ProductQuantityResponseDTO(
        UUID productId,
        String name,
        int stock
) {
}
