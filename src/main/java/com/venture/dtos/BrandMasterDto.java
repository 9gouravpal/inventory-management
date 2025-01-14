package com.venture.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandMasterDto {

	private Long brandId;
	private String brandName;
	private Long subCategoryId;
	private String subCategoryName;
}
