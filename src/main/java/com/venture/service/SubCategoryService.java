package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.SubCategoryDto;

@Service
public interface SubCategoryService {

	SubCategoryDto addsuSubCategory(SubCategoryDto dto);

	List<SubCategoryDto> getAllSubCategory();

	List<SubCategoryDto> getSubCategoryByCategoryId(Long categoryId);

	SubCategoryDto updateSubCategory(SubCategoryDto dto);

}
