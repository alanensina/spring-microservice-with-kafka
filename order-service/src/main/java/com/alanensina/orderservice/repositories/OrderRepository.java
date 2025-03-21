package com.alanensina.orderservice.repositories;

import com.alanensina.orderservice.domains.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
