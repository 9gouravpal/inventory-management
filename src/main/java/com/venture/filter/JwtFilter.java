package com.venture.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.venture.services.impl.JwtUserDetails;
import com.venture.utils.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtHelper jwtHelper;
	private final JwtUserDetails userDetails;

	public JwtFilter(JwtHelper jwtHelper, JwtUserDetails userDetails) {
		super();
		this.jwtHelper = jwtHelper;
		this.userDetails = userDetails;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String reqestHeader = request.getHeader("Authorization");
		log.info(" Header :  [{}]", reqestHeader);
		String username = null;
		String token = null;

		if (reqestHeader != null && reqestHeader.startsWith("Bearer")) {
			token = reqestHeader.substring(7);
			try {
				username = jwtHelper.getUsernameFromToken(token);

			} catch (IllegalArgumentException e) {
				log.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
			} catch (ExpiredJwtException e) {
				log.info("Given jwt token is expired !!");
				e.printStackTrace();
			} catch (MalformedJwtException e) {
				log.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
			} catch (Exception e) {
				log.error("token not valid ");
				e.printStackTrace();
			}

		} else {
			log.info("Invalid Header Value !! ");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				UserDetails userDetails = this.userDetails.loadUserByUsername(username);
				boolean validToken = this.jwtHelper.validateToken(token, userDetails);
				if (validToken) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					log.info("Validation fails !!");
				}
			} catch (UsernameNotFoundException e) {
				log.error("User not found: {}", e.getMessage());
				// Handle user not found scenario (e.g., send a specific response)
			} catch (JwtException e) {
				log.error("Token validation failed: {}", e.getMessage());
				// Handle token validation failure (e.g., send a specific response)
			} catch (Exception e) {
				log.error("An unexpected error occurred: {}", e.getMessage());
				// Handle any other exceptions
			}

		}
		filterChain.doFilter(request, response);
	}

}
