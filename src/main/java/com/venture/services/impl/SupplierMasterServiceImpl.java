package com.venture.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.venture.dtos.SupplierMasterDto;
import com.venture.entitys.SupplierMaster;
import com.venture.exception.InvalidInputException;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.SupplierMasterRepo;
import com.venture.service.SupplierMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SupplierMasterServiceImpl implements SupplierMasterService {

	private final SupplierMasterRepo supplierMasterRepo;
	private final InventoryMapper mapper;

	public SupplierMasterServiceImpl(SupplierMasterRepo supplierMasterRepo, InventoryMapper mapper) {
		super();
		this.supplierMasterRepo = supplierMasterRepo;
		this.mapper = mapper;
	}

	@Override
	public SupplierMasterDto createSupplier(SupplierMasterDto dto) {
		try {
			SupplierMaster master = this.mapper.toSupplierMaster(dto);
			master = this.supplierMasterRepo.saveAndFlush(master);
			dto = this.mapper.toSupplierMasterDto(master);
		} catch (InvalidInputException e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public SupplierMasterDto updateSupplierMaster(SupplierMasterDto dto) {
		SupplierMaster master = this.mapper.toSupplierMaster(dto);
		try {
			Optional<SupplierMaster> optional = this.supplierMasterRepo.findById(dto.getSuplierId());
			if (optional.isPresent()) {
				master.setSuplierId(optional.get().getSuplierId());
				dto = this.mapper.toSupplierMasterDto(master);
			}
		} catch (InvalidInputException e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public List<SupplierMasterDto> getAllSupplier() {
		try {
			List<SupplierMaster> list = this.supplierMasterRepo.findAll();
			return this.mapper.toListSupplierMaster(list);
		} catch (Exception e) {
			log.error("Internal service error", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public SupplierMasterDto getSupplierById(Long supplierId) {
		try {
			Optional<SupplierMaster> master = this.supplierMasterRepo.findById(supplierId);
			if (!master.isEmpty()) {
				throw new DataAccessResourceFailureException("supplier id is invalid");
			}
			return this.mapper.toSupplierMasterDto(master.get());
		} catch (RuntimeException e) {
			log.error("Internal service eerror:{}", e.getMessage(), supplierId);
			return null;
		}
	}
}
