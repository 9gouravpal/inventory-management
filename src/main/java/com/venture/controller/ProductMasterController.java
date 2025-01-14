package com.venture.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.venture.dtos.ProductMasterDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.ProductMasterService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Productmaster-API")
@RequestMapping(value = "/productMaster")
public class ProductMasterController {

	private final ResponseWithObject responseWithObject;
	private final ProductMasterService service;

	public ProductMasterController(ResponseWithObject responseWithObject, ProductMasterService service) {
		super();
		this.responseWithObject = responseWithObject;
		this.service = service;
	}

	@PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> addProductDetails(@ModelAttribute ProductMasterDto dto) {
		try {
			if (dto == null) {
				return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
						"Product details cannot be null");
			}

			MultipartFile file = dto.getFile();
			if (file != null && !file.isEmpty()) {
				// Check file size (max 10MB)
				if (file.getSize() > 10 * 1024 * 1024) {
					return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
							"File size should not exceed 10MB");
				}

				String contentType = file.getContentType();
				if (contentType != null) {
					// Allow both image and PDF files
					if (!contentType.startsWith("image/") && !contentType.equals("application/pdf")) {
						return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
								"Only image files (JPG, PNG, etc.) and PDF files are allowed");
					}
				}

				// Validate file extension
				String originalFilename = file.getOriginalFilename();
				if (originalFilename != null) {
					String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
					if (!isValidFileExtension(extension)) {
						return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
								"Invalid file type. Allowed types: jpg, jpeg, png, gif, pdf");
					}
				}
			}

			ProductMasterDto savedProduct = service.addPrductDetails(dto, dto.getFile());

			if (savedProduct == null) {
				return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
						"Failed to add product");
			}

			return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, savedProduct);

		} catch (Exception e) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
					"Error processing request: " + e.getMessage());
		}
	}

	private boolean isValidFileExtension(String extension) {
		Set<String> validExtensions = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "pdf"));
		return validExtensions.contains(extension);
	}

	@GetMapping("/getAllProduct")
	public ResponseEntity<Object> getAllProductDetails() {
		List<ProductMasterDto> products = service.getAllProduct();

		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/getProductById")
	public ResponseEntity<Object> getProductById(@RequestParam Long param) {
		ProductMasterDto dto = this.service.getProductById(param);
		if (dto == null) {
			return this.responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"product id invalid");
		}

		return this.responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}

	@PutMapping(value = "/updateProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updatePrdouctDetails(@ModelAttribute ProductMasterDto dto) {

		ProductMasterDto productMasterDto = service.updateProductMaster(dto, dto.getFile());

		if (productMasterDto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid product id");

		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}
}
