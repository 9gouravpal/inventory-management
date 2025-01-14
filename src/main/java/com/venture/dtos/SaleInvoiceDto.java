package com.venture.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleInvoiceDto {

	private Long saleId;
	private String costumerName;
	private String costumerPhoneNo;
	private String costumerEmail;
	private String saleCode;
	private List<Long> productId;
	private List<Integer> saleQuntity;
	private List<Double> salePrice;
	private Double totalAmount;
	private Double gst;
	private int discountPercntage;
	private Double discountAmount;
	private Double netAmount;
	private String payMode;
	private LocalDate date;
}
