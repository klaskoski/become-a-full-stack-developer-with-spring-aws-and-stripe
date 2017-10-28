package com.training.fullstack.backend.repositories;

import com.training.fullstack.backend.domain.backend.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
