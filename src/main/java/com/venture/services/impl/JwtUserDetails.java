package com.venture.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.venture.entitys.User;
import com.venture.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetails implements UserDetailsService {
	private final UserRepo userRepo;

	public JwtUserDetails(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User userDetails = userRepo.findByUsername(username);
			if (userDetails == null) {
				throw new IllegalArgumentException("Invalid username or password.");
			}
			Set<SimpleGrantedAuthority> authorities = getAuthority(userDetails.getRoles());
			return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
					userDetails.getPassword(), true, true, true, true, authorities);
		} catch (UsernameNotFoundException e) {
			log.error("user name not found:{}", e.getMessage());
			throw new IllegalArgumentException("Invalid username or password.");
		}

	}

	public Set<SimpleGrantedAuthority> getAuthority(List<String> roles) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
