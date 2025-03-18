package com.alanensina.productstock.dtos;

import com.alanensina.productstock.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        BigDecimal unitPrice,
        boolean available,
        int stock
) {

    public static ProductResponseDTO from(Product product){
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getUnitPrice(),
                product.isAvailable(),
                product.getStock());
    }

    public static List<ProductResponseDTO> from(List<Product> products){
        return products.stream().map(
                product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getUnitPrice(),
                        product.isAvailable(),
                        product.getStock()))
                .toList();
    }
}
