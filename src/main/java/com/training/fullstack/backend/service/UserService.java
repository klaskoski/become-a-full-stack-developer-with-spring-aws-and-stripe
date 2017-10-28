package com.training.fullstack.backend.service;

import com.training.fullstack.backend.domain.backend.Plan;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.domain.backend.UserRole;
import com.training.fullstack.backend.repositories.PlanRepository;
import com.training.fullstack.backend.repositories.RoleRepository;
import com.training.fullstack.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user, Plan plan, Set<UserRole> userRoles) {

        if (!planRepository.exists(plan.getId())){
            plan = planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole userRole : userRoles) {
            roleRepository.save(userRole.getRole());
        }

        user.getUserRoles().addAll(userRoles);
        user = userRepository.save(user);
        return user;
    }
}
