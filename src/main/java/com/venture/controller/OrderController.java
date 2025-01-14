package com.venture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.OrderEntityDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.OrderService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Order")
@Tag(name = "Order-API")
public class OrderController {

	private final ResponseWithObject responseWithObject;
	private final OrderService service;

	public OrderController(ResponseWithObject responseWithObject, OrderService service) {
		super();
		this.responseWithObject = responseWithObject;
		this.service = service;
	}

	@PostMapping("/createOrder")
	public ResponseEntity<Object> createOrder(@RequestBody OrderEntityDto entity) {
		String message = this.service.createOrder(entity);
		if ("Email sent succesfully!".equals(message)) {
			return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, message);
		}
		return responseWithObject.generateResponse(AppConstants.INTERNAL_ERROR, HttpStatus.BAD_GATEWAY, message);
	}

	@GetMapping("/getAllOrders")
	public ResponseEntity<Object> getAllOrderDetails() {
		List<OrderEntityDto> list = this.service.getAllOrderDetails();
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, list);
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

}
