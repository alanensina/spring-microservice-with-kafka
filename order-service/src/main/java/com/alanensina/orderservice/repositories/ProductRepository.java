package com.alanensina.orderservice.repositories;

import com.alanensina.orderservice.domains.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByAvailableTrue();
}
