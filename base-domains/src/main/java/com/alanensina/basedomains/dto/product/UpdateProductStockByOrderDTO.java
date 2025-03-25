package com.alanensina.basedomains.dto.product;

import java.util.UUID;

public record UpdateProductStockByOrderDTO(
        UUID productId,
        int quantity
) {
}
