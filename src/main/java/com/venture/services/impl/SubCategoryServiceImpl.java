package com.venture.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.venture.dtos.SubCategoryDto;
import com.venture.entitys.Category;
import com.venture.entitys.SubCategory;
import com.venture.exception.InvalidInputException;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.CategoryRepository;
import com.venture.repo.SubCategoryRepo;
import com.venture.service.SubCategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {

	private final SubCategoryRepo subCategoryRepo;
	private final CategoryRepository categoryRepository;
	private final InventoryMapper mapper;

	public SubCategoryServiceImpl(SubCategoryRepo subCategoryRepo, CategoryRepository categoryRepository,
			InventoryMapper mapper) {
		super();
		this.subCategoryRepo = subCategoryRepo;
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	@Override
	public SubCategoryDto addsuSubCategory(SubCategoryDto dto) {
		try {
			SubCategory subCategory = this.mapper.toSubCategory(dto);
			subCategory = this.subCategoryRepo.saveAndFlush(subCategory);
			dto = this.mapper.toSubCategoryDto(subCategory);
		} catch (InvalidInputException e) {
			log.error("internal service error:{}", e.getMessage());

		}
		return dto;
	}

	@Override
	public List<SubCategoryDto> getAllSubCategory() {
		List<SubCategoryDto> subCategoryDtos = new ArrayList<>();
		try {
			List<SubCategory> list = this.subCategoryRepo.findAll();
			for (SubCategory subCategory : list) {
				SubCategoryDto dto = this.mapper.toSubCategoryDto(subCategory);
				Category category = this.categoryRepository.findById(dto.getCategoryId())
						.orElseThrow(() -> new RuntimeException("category Id is invalid"));
				dto.setCategoryName(category.getCategoryName());
				subCategoryDtos.add(dto);
			}
		} catch (RuntimeException e) {
			log.error("Internal service errors:{}", e.getMessage());

		}
		return subCategoryDtos;
	}

	@Override
	public List<SubCategoryDto> getSubCategoryByCategoryId(Long categoryId) {
		List<SubCategoryDto> subCategoryDtos = new ArrayList<>();
		try {
			List<SubCategory> list = this.subCategoryRepo.findByCategoryId(categoryId);
			for (SubCategory subCategory : list) {
				SubCategoryDto dto = this.mapper.toSubCategoryDto(subCategory);
				Category category = this.categoryRepository.findById(dto.getCategoryId())
						.orElseThrow(() -> new RuntimeException("categoryId is invalid"));
				dto.setCategoryName(category.getCategoryName());
				subCategoryDtos.add(dto);
			}

		} catch (RuntimeException e) {
			log.error("Internal service error:{}", e.getMessage());

		}
		return subCategoryDtos;
	}

	@Override
	public SubCategoryDto updateSubCategory(SubCategoryDto dto) {
		SubCategory category = this.mapper.toSubCategory(dto);
		try {
			Optional<SubCategory> optional = this.subCategoryRepo.findById(dto.getCategoryId());
			if (optional.isPresent()) {

				category.setCategoryId(optional.get().getCategoryId());
				this.subCategoryRepo.save(category);
				dto = this.mapper.toSubCategoryDto(category);
			}

		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}
}
