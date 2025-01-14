package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.CategoryDto;

@Service
public interface CategoryService {

	CategoryDto productCategory(CategoryDto dto);

	CategoryDto getCategoryById(Long categoryId);

	List<CategoryDto> getAllCategory();

	CategoryDto updateCategory(CategoryDto dto);

}
