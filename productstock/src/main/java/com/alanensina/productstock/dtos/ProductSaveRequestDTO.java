package com.alanensina.productstock.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductSaveRequestDTO(
        @NotBlank String name,
        @NotNull @Positive BigDecimal unitPrice,
        boolean available,
        @Positive int stock
) {
}
