package com.venture.entitys;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sale_invoice")
public class SaleInvoice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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
