package com.venture.services.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.venture.dtos.BrandMasterDto;
import com.venture.entitys.BrandMaster;
import com.venture.entitys.SubCategory;
import com.venture.exception.DataNotFoundException;
import com.venture.exception.InvalidInputException;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.BrandMasterRepository;
import com.venture.repo.SubCategoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BrandMasterServiceImpl implements BrandMasterService {

	private final BrandMasterRepository brandMasterRepository;
	private final SubCategoryRepo subCategoryRepo;
	private final InventoryMapper mapper;

	public BrandMasterServiceImpl(BrandMasterRepository brandMasterRepository, SubCategoryRepo subCategoryRepo,
			InventoryMapper mapper) {
		super();
		this.brandMasterRepository = brandMasterRepository;
		this.subCategoryRepo = subCategoryRepo;
		this.mapper = mapper;
	}

	@Override
	public BrandMasterDto addBrandMaster(BrandMasterDto dto) {
		try {
			BrandMaster brandMaster = this.mapper.toBrandMaster(dto);
			brandMaster = this.brandMasterRepository.saveAndFlush(brandMaster);
			dto = this.mapper.toBrandMasterDto(brandMaster);
		} catch (InvalidInputException e) {
			log.error("Intrenal serviec error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public List<BrandMasterDto> getAllBrandMaster() {
		List<BrandMasterDto> list = new LinkedList<>();
		try {
			List<BrandMaster> brandMasters = this.brandMasterRepository.findAll();
			for (BrandMaster brandMaster : brandMasters) {
				BrandMasterDto dto = this.mapper.toBrandMasterDto(brandMaster);
				SubCategory subCategory = this.subCategoryRepo.findById(dto.getSubCategoryId())
						.orElseThrow(() -> new RuntimeException("subcategoryId is invalid"));
				dto.setSubCategoryName(subCategory.getSubCategoryName());
				list.add(dto);
			}

		} catch (DataNotFoundException e) {
			log.error("Internal service errors:{}", e.getMessage());
		}
		return list;
	}

	@Override
	public BrandMasterDto updateBrandMaster(BrandMasterDto dto) {
		BrandMaster master = this.mapper.toBrandMaster(dto);

		try {
			Optional<BrandMaster> optional = this.brandMasterRepository.findById(dto.getBrandId());
			if (optional.isPresent()) {
				master.setBrandId(optional.get().getBrandId());
				this.brandMasterRepository.save(master);
				dto = this.mapper.toBrandMasterDto(master);
			}
		} catch (DataNotFoundException e) {
			log.error("Internal serivce error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public BrandMasterDto getBrandMaster(Long brandId) {
		BrandMasterDto dto = new BrandMasterDto();
		try {
			Optional<BrandMaster> master = this.brandMasterRepository.findById(brandId);
			if (master.isPresent()) {
				dto = this.mapper.toBrandMasterDto(master.get());
			}
		} catch (DataNotFoundException e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public List<BrandMasterDto> getAllBrandBySubCategorayId(Long subCategoryId) {
		List<BrandMasterDto> list = new LinkedList<>();
		try {
			List<BrandMaster> brandMasters = this.brandMasterRepository.findBySubCategoryId(subCategoryId);
			if (brandMasters.isEmpty()) {
				throw new IllegalArgumentException("subCategoryId not found");
			}
			for (BrandMaster brandMaster : brandMasters) {
				BrandMasterDto dto = this.mapper.toBrandMasterDto(brandMaster);
				SubCategory subCategory = this.subCategoryRepo.findById(dto.getSubCategoryId())
						.orElseThrow(() -> new RuntimeException("subCategoryId not found"));
				dto.setSubCategoryName(subCategory.getSubCategoryName());
				list.add(dto);
			}
		} catch (InvalidInputException e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return list;
	}
}
