package com.venture.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.venture.dtos.CategoryDto;
import com.venture.entitys.Category;
import com.venture.exception.DataNotFoundException;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.CategoryRepository;
import com.venture.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;
	private final InventoryMapper mapper;

	public CategoryServiceImpl(CategoryRepository repository, InventoryMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public CategoryDto productCategory(CategoryDto dto) {
		try {
			Category category = this.mapper.toCategory(dto);
			category = this.repository.saveAndFlush(category);
			dto = mapper.toCategoryDto(category);
		} catch (RuntimeException e) {
			log.error("Invalid input in exception:{}", e.getMessage());

		}
		return dto;
	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		try {
			Optional<Category> category = this.repository.findById(categoryId);
			if (!category.isPresent()) {
				throw new DataAccessResourceFailureException("you provide category id is invalid");
			} else {
				return this.mapper.toCategoryDto(category.get());
			}

		} catch (RuntimeException e) {
			log.error("Data not founds CategoryService:{}", categoryId, e.getMessage());
			throw new RuntimeException("Internal service error:");
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		try {
			List<Category> list = this.repository.findAll();
			return this.mapper.toListCategory(list);
		} catch (DataNotFoundException e) {
			log.error("Internal service error:{}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public CategoryDto updateCategory(CategoryDto dto) {
		Category category = this.mapper.toCategory(dto);
		try {
			Optional<Category> optional = this.repository.findById(dto.getCategoryId());
			if (optional.isPresent()) {
				category.setCategoryId(optional.get().getCategoryId());
				this.repository.save(category);
				dto = this.mapper.toCategoryDto(category);
			}
		} catch (DataNotFoundException e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}
}
