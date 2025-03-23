package com.alanensina.stockservice.repositories;

import com.alanensina.stockservice.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
