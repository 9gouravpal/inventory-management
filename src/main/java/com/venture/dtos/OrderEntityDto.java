package com.venture.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityDto {

	private Long orderId;
	private Long supplierId;
	private String supplierEmail;
	private List<String> productName;
	private List<String> quentity;

	private List<String> categoryName;

	private LocalDate orderDate;
}
