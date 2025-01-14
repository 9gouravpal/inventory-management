package com.venture.dtos;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMasterDto {
	private Long productId;
	private String productName;
	private Long categoryId;
	private String categoryName;
	private Long subCategoryId;
	private String subCategoryName;
	private Long brandId;
	private String brandName;
	private String unitOfMeassurement;
	private Double discount;
	private Double salePrice;
	private int quantity;
	private int skuNumber;
	private String attributes;
	private int reorderQuantity;
	private Double vatTax;
	private MultipartFile file;
	private String productImage;
	private String productDiscription;
}
