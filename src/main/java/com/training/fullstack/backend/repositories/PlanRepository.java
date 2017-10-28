package com.training.fullstack.backend.repositories;

import com.training.fullstack.backend.domain.backend.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
