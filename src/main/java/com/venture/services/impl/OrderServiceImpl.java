package com.venture.services.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.OrderEntityDto;
import com.venture.entitys.OrderEntity;
import com.venture.entitys.SupplierMaster;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.OrderRepo;
import com.venture.repo.SupplierMasterRepo;
import com.venture.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final SupplierMasterRepo supplierMasterRepo;
	private final OrderRepo orderRepo;
	private final InventoryMapper mapper;
	private final EmailServiceImpl emailServiceImpl;

	public OrderServiceImpl(OrderRepo orderRepo, InventoryMapper mapper, EmailServiceImpl emailServiceImpl,
			SupplierMasterRepo supplierMasterRepo) {
		super();
		this.supplierMasterRepo = supplierMasterRepo;
		this.orderRepo = orderRepo;
		this.mapper = mapper;
		this.emailServiceImpl = emailServiceImpl;
	}

	@Override
	public String createOrder(OrderEntityDto dto) {
		String message = "Email not sent!";
		try {
			OrderEntity entity = this.mapper.toOrderEntity(dto);
			entity.setOrderDate(LocalDate.now());
			entity = this.orderRepo.saveAndFlush(entity);
			dto = this.mapper.toOrderDto(entity);
			Thread.sleep(5000);
			sendEnquiryEmail(dto);
			message = "Email sent succesfully!";
		} catch (Exception e) {
			log.error("Internal service error: {}", e.getMessage());
		}
		return message;
	}

	private void sendEnquiryEmail(OrderEntityDto enquiryDto) {
		SupplierMaster supplierMaster = this.getSupplierDetails(enquiryDto.getSupplierId());

		String emailBody = String.format("""
				New Order Details:

				Order ID            : %s
				Supplier ID         : %s
				Supplier Name       : %s
				Supplier Email      : %s
				Product Name        : %s
				Quantity           : %s
				Category Name       : %s
				Order Date         : %s
				""", enquiryDto.getOrderId(), enquiryDto.getSupplierId(), supplierMaster.getSupplierName(),
				supplierMaster.getEmail(), enquiryDto.getProductName(), enquiryDto.getQuentity(),
				enquiryDto.getCategoryName(), enquiryDto.getOrderDate());

		emailServiceImpl.sendSimpleEmail(supplierMaster.getEmail(), "New Order", emailBody);
	}

	public SupplierMaster getSupplierDetails(Long supplierID) {
		return this.supplierMasterRepo.findById(supplierID)
				.orElseThrow(() -> new RuntimeException("Supplier ID not found"));
	}

	@Override
	public List<OrderEntityDto> getAllOrderDetails() {
		try {
			List<OrderEntity> list = this.orderRepo.findAll();
			return this.mapper.toListOrder(list);
		} catch (Exception e) {
			log.error("Internal service error{}", e.getMessage());
			return Collections.emptyList();
		}
	}

}
