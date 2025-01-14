package com.venture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.TransactionDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.TransactionService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transction")
@CrossOrigin(origins = "*")
@Tag(name = "Transction-API")
public class TransctionController {

	private final TransactionService service;
	private final ResponseWithObject responseWithObject;

	public TransctionController(TransactionService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/createTransaction")
	public ResponseEntity<Object> addTransction(@RequestBody TransactionDto entity) {
		TransactionDto dto = service.saveAllTerTransaction(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.INTERNAL_ERROR, HttpStatus.BAD_REQUEST,
					"data is invalid");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllTrannsction")
	public ResponseEntity<Object> getMethodName() {
		List<TransactionDto> list = this.service.getAllTransactionDtos();
		if (!list.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, list);
		}
		return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "LIst is empty");
	}

}
