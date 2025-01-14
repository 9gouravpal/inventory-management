package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.SupplierMasterDto;

@Service
public interface SupplierMasterService {

	SupplierMasterDto createSupplier(SupplierMasterDto dto);

	SupplierMasterDto updateSupplierMaster(SupplierMasterDto dto);

	List<SupplierMasterDto> getAllSupplier();

	SupplierMasterDto getSupplierById(Long supplierId);

}
