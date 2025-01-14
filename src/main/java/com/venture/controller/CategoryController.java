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

import com.venture.dtos.CategoryDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.CategoryService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category-API")
@CrossOrigin(origins = "*")
public class CategoryController {

	private final CategoryService service;
	private final ResponseWithObject responseWithObject;

	public CategoryController(CategoryService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/addCategory")
	public ResponseEntity<Object> addCategory(@RequestBody CategoryDto dto) {
		CategoryDto dto2 = service.productCategory(dto);
		if (dto2 == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "Data is invalid");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.CREATED, dto2);

	}

	@GetMapping("/getCategoryById")
	public ResponseEntity<Object> getDataByCategoryId(@RequestParam Long categoryId) {
		CategoryDto dto = this.service.getCategoryById(categoryId);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"id is invalid");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllCategory")
	public ResponseEntity<Object> getAllCategory() {
		List<CategoryDto> list = this.service.getAllCategory();
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "list is empty");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

	@PutMapping("/updateCategory")
	public ResponseEntity<Object> putMethodName(@RequestBody CategoryDto entity) {
		CategoryDto dto = this.service.updateCategory(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"Data is invalid");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}
}
