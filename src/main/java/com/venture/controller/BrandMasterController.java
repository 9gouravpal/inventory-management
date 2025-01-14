package com.venture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.BrandMasterDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.services.impl.BrandMasterService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Brand-Master-API")
@RequestMapping("/brandMaster")
public class BrandMasterController {

	private final BrandMasterService service;
	private final ResponseWithObject responseWithObject;

	public BrandMasterController(BrandMasterService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/createBrand")
	public ResponseEntity<Object> createBrandMaster(@RequestBody BrandMasterDto entity) {
		BrandMasterDto dto = this.service.addBrandMaster(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "invalid data");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllBrand")
	public ResponseEntity<Object> getAllBrand() {
		List<BrandMasterDto> list = this.service.getAllBrandMaster();
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "empty list");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

	@GetMapping("/getBrandById")
	public ResponseEntity<Object> getBrandById(@RequestParam Long brandId) {
		BrandMasterDto dto = this.service.getBrandMaster(brandId);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"brand Id is invalid");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@PutMapping("/updateBrand")
	public ResponseEntity<Object> updateBrandMaster(@RequestBody BrandMasterDto entity) {
		BrandMasterDto dto = service.updateBrandMaster(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"Invalid data");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}

	@GetMapping("/getBrandBySubCategoryId")
	public ResponseEntity<Object> getBrandBySubCategorayId(@RequestParam Long subCategoryId) {
		List<BrandMasterDto> dtos = this.service.getAllBrandBySubCategorayId(subCategoryId);
		if (dtos.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "no data in list");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dtos);
	}

}
