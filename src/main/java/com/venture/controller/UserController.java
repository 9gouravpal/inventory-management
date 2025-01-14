package com.venture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.JwtAuthResponse;
import com.venture.dtos.LoginDto;
import com.venture.dtos.UserDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.UserServices;
import com.venture.services.impl.JwtUserDetails;
import com.venture.utils.AppConstants;
import com.venture.utils.JwtHelper;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
@Tag(name = "User-Register-login-API")
@Slf4j
public class UserController {

	private final UserServices services;
	private JwtUserDetails jwtUserDetails;
	private AuthenticationManager authentication1;
	private JwtHelper jwtHelper;
	private final ResponseWithObject responseWithObject;

	public UserController(UserServices services, JwtUserDetails jwtUserDetails, AuthenticationManager authentication1,
			JwtHelper jwtHelper, ResponseWithObject responseWithObject) {
		super();
		this.services = services;
		this.jwtUserDetails = jwtUserDetails;
		this.authentication1 = authentication1;
		this.jwtHelper = jwtHelper;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/register")
	public ResponseEntity<Object> postMethodName(@RequestBody UserDto entity) {
		UserDto userDto = services.saveUserDetails(entity);
		if (userDto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "invalid input");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, userDto);

	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto entity) {
		Authentication authentication = authentication1
				.authenticate(new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword()));
		if (authentication.isAuthenticated()) {
			log.info("valid");
		} else {
			log.error("Invalid");
		}
		String token = null;
		UserDetails userDetails = jwtUserDetails.loadUserByUsername(entity.getUsername());

		token = this.jwtHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setTokenType("Bearer");
		jwtAuthResponse.setRole(userDetails.getAuthorities().stream().findFirst().get().getAuthority());
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, jwtAuthResponse);
	}

	@GetMapping("/log-out")
	public ResponseEntity<Object> logout(HttpServletRequest httpServletRequest) {
		String headerString = httpServletRequest.getHeader("Authorization");
		if (headerString == null || !headerString.startsWith("Bearer")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");

		}
		String token = headerString.substring(7);
		if (Boolean.TRUE.equals(jwtHelper.isTokenExpired(token))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired");
		}
		return ResponseEntity.ok().build();
	}
}
