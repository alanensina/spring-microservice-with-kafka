package com.alanensina.basedomains.dto.order;

import com.alanensina.basedomains.dto.product.ProductOrderRequestDTO;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequestDTO(
        UUID userId,
        List<ProductOrderRequestDTO> products
) {
}
