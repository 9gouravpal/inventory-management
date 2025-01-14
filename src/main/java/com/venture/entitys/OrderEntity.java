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
@Table(name = "orderEntiy")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long orderId;
	private Long supplierId;
	private String supplierEmail;
	private List<String> productName;
	private List<String> quentity;
	private List<String> categoryName;
	private LocalDate orderDate;
}
