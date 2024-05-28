package com.microserviceshop.UserService.repository;

import com.microserviceshop.UserService.entity.Permission;
import com.microserviceshop.UserService.entity.Role;
import com.microserviceshop.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
