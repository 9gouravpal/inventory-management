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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_invoice")
public class PurchaseInvoice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long purchaseId;
	private String purchseCode;
	private Long supplierId;
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

}
