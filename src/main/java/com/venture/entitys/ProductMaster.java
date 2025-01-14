package com.venture.entitys;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_master")
public class ProductMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long productId;
	private String productName;
	private Long categoryId;
	private Long subCategoryId;
	private Long brandId;
	private String unitOfMeassurement;
	private Double discount;
	private Double salePrice;
	private int quantity;
	private int skuNumber;
	private String attributes;
	private int reorderQuantity;
	private Double vatTax;
	private byte[] productImage;
	private String productDiscription;

}
