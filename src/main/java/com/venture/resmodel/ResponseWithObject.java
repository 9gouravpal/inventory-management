package com.venture.resmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.venture.utils.AppConstants;

@Component
public class ResponseWithObject {

	public ResponseEntity<Object> generateResponse(String message, HttpStatus status, String customcode, Object obj) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RCCODE, customcode);
		map.put(AppConstants.RRES, obj);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(HttpHeaders.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());

	}

	public ResponseEntity<Object> generateResponseList(String message, HttpStatus status, String customcode,
			List<?> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RCCODE, customcode);
		map.put(AppConstants.RRES, list);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(HttpHeaders.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());

	}

	public ResponseEntity<Object> generateResponse(String message, HttpStatus status, String customcode,
			List<String> list) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RCCODE, customcode);
		map.put(AppConstants.RRES, list);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(HttpHeaders.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());

	}

	public ResponseEntity<Object> generateResponse(String message, HttpStatus status, String customcode,
			Map<String, String> hash) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RCCODE, customcode);
		map.put(AppConstants.RRES, hash);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(HttpHeaders.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());

	}

	public ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object obj) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());

		map.put(AppConstants.RRES, obj);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(HttpHeaders.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());
	}

	public ResponseEntity<Object> generateResponseHeader(String message, HttpStatus status, Object obj,
			Map<?, ?> headMap) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RRES, obj);
		HttpHeaders headers2 = new HttpHeaders();
		headMap.forEach((k, v) -> headers2.add(String.valueOf(k), String.valueOf(v)));
		headers2.add(AppConstants.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());
	}

	public ResponseEntity<Object> generateResponse(String message, HttpStatus status, String customcode) {
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstants.RMSG, message);
		map.put(AppConstants.RSTATUS, status.value());
		map.put(AppConstants.RCCODE, customcode);
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add(AppConstants.CONTENT_TYPE, AppConstants.APPLICATION_JSON);
		return new ResponseEntity<>(map, headers2, status.value());
	}

}
