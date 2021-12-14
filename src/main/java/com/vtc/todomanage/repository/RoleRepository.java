package com.vtc.todomanage.repository;

import com.vtc.todomanage.model.ERole;
import com.vtc.todomanage.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
