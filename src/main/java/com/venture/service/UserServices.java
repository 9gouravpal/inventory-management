package com.venture.service;

import org.springframework.stereotype.Service;

import com.venture.dtos.UserDto;

@Service
public interface UserServices {

	UserDto saveUserDetails(UserDto dto);

}
