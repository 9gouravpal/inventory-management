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

import com.venture.dtos.SupplierMasterDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.SupplierMasterService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/supplierMaster")
@Tag(name = "Supplier-maseter-API")
public class SupplierMasterController {

	private final SupplierMasterService service;
	private final ResponseWithObject responseWithObject;

	public SupplierMasterController(SupplierMasterService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/createSupplier")
	public ResponseEntity<Object> createSuppler(@RequestBody SupplierMasterDto entity) {
		SupplierMasterDto dto = service.createSupplier(entity);

		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "invalid data");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getSupplierById")
	public ResponseEntity<Object> getSupplierById(@RequestParam Long supplierId) {
		SupplierMasterDto dto = this.service.getSupplierById(supplierId);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid id provide by you");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllSupplier")
	public ResponseEntity<Object> getAllSupplier() {
		List<SupplierMasterDto> list = this.service.getAllSupplier();
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "no data ");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

	@PutMapping("/updateSupplier")
	public ResponseEntity<Object> updateSupplier(@RequestBody SupplierMasterDto entity) {
		SupplierMasterDto dto = this.service.updateSupplierMaster(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid data");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}
}
