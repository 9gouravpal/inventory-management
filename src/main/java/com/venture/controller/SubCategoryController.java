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

import com.venture.dtos.SubCategoryDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.SubCategoryService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/subCategory")
@CrossOrigin(origins = "*")
@RestController
@Tag(name = "SubCategory-API")
public class SubCategoryController {

	private final SubCategoryService service;
	private final ResponseWithObject responseWithObject;

	public SubCategoryController(SubCategoryService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/addSubCategory")
	public ResponseEntity<Object> addSubCategory(@RequestBody SubCategoryDto dto) {
		SubCategoryDto subCategoryDto = this.service.addsuSubCategory(dto);
		if (subCategoryDto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "Invalid data");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, subCategoryDto);
	}

	@GetMapping("/getAllSubCategory")
	public ResponseEntity<Object> getAllSubCategory() {
		List<SubCategoryDto> list = this.service.getAllSubCategory();
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "list is empty");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

	@GetMapping("/getSubCategoryByCategoryId")
	public ResponseEntity<Object> getAllSubCategoryByCategoryId(@RequestParam Long categoryId) {
		List<SubCategoryDto> list = this.service.getSubCategoryByCategoryId(categoryId);
		if (list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "empty list");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
	}

	@PutMapping("/updateSubCategory")
	public ResponseEntity<Object> updateSubCategory(@RequestBody SubCategoryDto entity) {
		SubCategoryDto dto = this.service.updateSubCategory(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid in");
		}

		return responseWithObject.generateResponse(AppConstants.SUCCESS, HttpStatus.OK, dto);
	}
}
