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
public class PurchaseInvoiceDto {
	private Long purchaseId;
	private String purchseCode;
	private Long supplierId;
	private String supplierName;
	private LocalDate purchaseDate;
	private String supplierMemo;
	private String note;
	private List<Long> productId;

	private List<Integer> quantity;
	private List<Double> purchasePrice;
	private List<Double> sellingPrice;
	private List<Double> amount;
	private List<Integer> taxPercntage;
	private List<Double> taxInPrice;

	private Double totalAmount;
	private Double taxAmount;
	private Double paybleAmount;
	private Double dueAmount;
	private Double paidAmount;
	private List<String> productName;
}
