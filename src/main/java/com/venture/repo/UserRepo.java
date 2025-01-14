package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venture.entitys.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
