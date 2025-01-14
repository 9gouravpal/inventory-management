package com.venture.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.venture.dtos.UserDto;
import com.venture.entitys.User;
import com.venture.exception.InvalidInputException;
import com.venture.mapper.VcsMapper;
import com.venture.repo.UserRepo;
import com.venture.service.UserServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServicesImpl implements UserServices {

	private final UserRepo repo;
	private final VcsMapper mapper;
	private final PasswordEncoder encoder;

	public UserServicesImpl(UserRepo repo, VcsMapper mapper, PasswordEncoder encoder) {
		super();
		this.repo = repo;
		this.mapper = mapper;
		this.encoder = encoder;
	}

	@Override
	public UserDto saveUserDetails(UserDto dto) {
		try {
			User user = this.mapper.toUser(dto);
			user.setPassword(encoder.encode(dto.getPassword()));
			user = repo.save(user);
			dto = mapper.toUserDto(user);
		} catch (InvalidInputException e) {
			log.error("Internal service error{}", e.getMessage());
		}
		return dto;
	}
}
