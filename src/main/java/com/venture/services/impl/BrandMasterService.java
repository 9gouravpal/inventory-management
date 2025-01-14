package com.venture.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.BrandMasterDto;

@Service
public interface BrandMasterService {

	BrandMasterDto addBrandMaster(BrandMasterDto dto);

	List<BrandMasterDto> getAllBrandMaster();

	BrandMasterDto updateBrandMaster(BrandMasterDto dto);

	BrandMasterDto getBrandMaster(Long brandId);

	List<BrandMasterDto> getAllBrandBySubCategorayId(Long subCategoryId);

}
