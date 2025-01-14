package com.venture.exception;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.venture.resmodel.ResponseWithObject;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = IOException.class)
	public ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
		log.error("wrong request::" + e.getMessage());
		return new ResponseWithObject().generateResponse("wrong request", HttpStatus.INTERNAL_SERVER_ERROR, "006");

	}

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	ResponseEntity<Object> handleControllerException(HttpServletRequest request, Throwable ex) {
		log.error("missing parameter ::" + ex.getMessage());
		return new ResponseWithObject().generateResponse("missing parameter", HttpStatus.INTERNAL_SERVER_ERROR, "007");

	}

	@ExceptionHandler(MissingServletRequestPartException.class)
	@ResponseBody
	ResponseEntity<Object> handleMissingServletRequestPartException(HttpServletRequest request, Throwable ex) {
		log.error("Should select atleast file for submit" + ex.getMessage());
		return new ResponseWithObject().generateResponse("Should select atleast file for submit",
				HttpStatus.INTERNAL_SERVER_ERROR, "009");

	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	ResponseEntity<Object> handleIllegalArgumentException(HttpServletRequest request, Throwable ex) {
		log.error("Illegal arugement" + ex.getMessage());
		return new ResponseWithObject().generateResponse("Illegal arugement", HttpStatus.BAD_REQUEST, "014");

	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseBody
	ResponseEntity<Object> handleUsernameNotFoundException(HttpServletRequest request, Throwable ex) {
		log.error("INVALID_CREDENTIALS" + ex.getMessage());
		return new ResponseWithObject().generateResponse("INVALID_CREDENTIALS ", HttpStatus.UNAUTHORIZED, "1001");

	}

	@ExceptionHandler(RequestRejectedException.class)
	@ResponseBody
	ResponseEntity<Object> handleRequestRejectedException(HttpServletRequest request, Throwable ex) {
		log.error("INVALID_CREDENTIALS " + ex.getMessage());
		return new ResponseWithObject().generateResponse("INVALID_CREDENTIALS ", HttpStatus.UNAUTHORIZED, "000");

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<HashMap<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {

		HashMap<String, String> hash = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(errorHash -> {
			String feildname = ((FieldError) errorHash).getField();
			String message = errorHash.getDefaultMessage();
			hash.put(feildname, message);
		});

		return new ResponseEntity<>(hash, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, Throwable ex) {
		log.error("Wrong method type " + ex.getMessage());
		return new ResponseWithObject().generateResponse("Wrong method type ", HttpStatus.METHOD_NOT_ALLOWED, "000");

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	ResponseEntity<Object> handleHttpMessageNotReadableException(HttpServletRequest request, Throwable ex) {
		log.error("Wrong method type " + ex.getMessage());
		return new ResponseWithObject().generateResponse(" Required request body is missing", HttpStatus.BAD_REQUEST,
				"000");

	}

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseBody
	ResponseEntity<Object> handleExpiredJwtException(HttpServletRequest request, Throwable ex) {
		log.error("authentication expired " + ex.getMessage());
		return new ResponseWithObject().generateResponse("authentication expired",
				HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "911");

	}

	@ExceptionHandler(value = { ServletException.class })
	public ResponseEntity<Object> servletException(HttpServletRequest request, Throwable ex) {
		String message = ex.getMessage();
		if ("JWT Token has expired".equals(message)) {
			message = "Authentication expired";
		}
		return new ResponseWithObject().generateResponse(message, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "912");
	}

	@ExceptionHandler(SignatureException.class)
	@ResponseBody
	ResponseEntity<Object> handleSignatureException(HttpServletRequest request, Throwable ex) {
		log.error("authentication not provided " + ex.getMessage());
		return new ResponseWithObject().generateResponse("authentication not provided",
				HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "913");

	}

}
