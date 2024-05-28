package com.microserviceshop.UserService.repository;

import com.microserviceshop.UserService.entity.Permission;
import com.microserviceshop.UserService.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
