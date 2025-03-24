package com.alanensina.basedomains.dto.product;

import java.util.UUID;

public record UpdateProductStockByOrder(
        UUID product,
        int quantity
) {
}
