package com.alanensina.orderservice.repositories;

import com.alanensina.orderservice.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
