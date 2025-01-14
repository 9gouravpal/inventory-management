package com.venture.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryDto {
	private Long subCategoryId;
	private Long categoryId;
	private String subCategoryName;
	private String categoryName;
}
