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

import com.venture.dtos.AccountsDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.AccountsService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
@Tag(name = "Account-API")
public class AccountController {
	/*
	 * gourav pal
	 */
	private final ResponseWithObject responseWithObject;
	private final AccountsService service;

	public AccountController(ResponseWithObject responseWithObject, AccountsService service) {
		super();
		this.responseWithObject = responseWithObject;
		this.service = service;
	}

	@PostMapping("create")
	public ResponseEntity<Object> creatAccount(@RequestBody AccountsDto entity) {
		Object object = this.service.createAccount(entity);
		if (object == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, object);
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, object);

	}

	@GetMapping("/getAllAccount")
	public ResponseEntity<Object> getallAccounts() {
		List<AccountsDto> dtos = this.service.getAllAccountsDtos();
		if (dtos.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "list is empty");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dtos);
	}

	@PutMapping("/updateAccount")
	public ResponseEntity<Object> putMethodName(@RequestBody AccountsDto entity) {
		AccountsDto dto = this.service.updateAccoount(entity);
		if (dto == null) {
			responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"inalid input data");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}

	@GetMapping("/getAccountById")
	public ResponseEntity<Object> getAccontById(@RequestParam Long accountid) {
		AccountsDto dto = service.getByAccountId(accountid);
		if (dto == null) {
			responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY, "no data found");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

}
