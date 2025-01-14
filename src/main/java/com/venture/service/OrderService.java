package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.OrderEntityDto;

@Service
public interface OrderService {

	String createOrder(OrderEntityDto dto);

	List<OrderEntityDto> getAllOrderDetails();

}
