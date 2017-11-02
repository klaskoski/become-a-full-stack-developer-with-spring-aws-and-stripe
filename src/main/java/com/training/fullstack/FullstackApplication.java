package com.training.fullstack;

import com.training.fullstack.backend.domain.backend.*;
import com.training.fullstack.backend.service.UserService;
import com.training.fullstack.common.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class FullstackApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(FullstackApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FullstackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = UserUtils.createBasicUser();
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, new Role(RoleEnum.ADMIN)));

		userService.createUser(user, PlanEnum.BASIC, userRoles);
		LOG.info("User {} created", user.getUsername());
	}
}
