package com.training.fullstack.backend.repositories;

import com.training.fullstack.backend.domain.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username );
}
