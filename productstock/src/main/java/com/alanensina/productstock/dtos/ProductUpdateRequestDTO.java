package com.alanensina.productstock.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateRequestDTO(
        @NotNull UUID id,
        String name,
        @Positive BigDecimal unitPrice,
        Boolean available,
        @Positive Integer stock
) {
}
