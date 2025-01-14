package com.venture.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.PurchaseInvoiceDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.PurchaseInvoiceService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/purchase-invoice")
@Tag(name = "Purchase-invoice-API")
public class PurchaseInvoiceController {

	private final PurchaseInvoiceService service;
	private final ResponseWithObject responseWithObject;

	public PurchaseInvoiceController(PurchaseInvoiceService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/create-invoice")
	public ResponseEntity<Object> createInvoice(@RequestBody PurchaseInvoiceDto entity) {
		PurchaseInvoiceDto dto = this.service.purchaseProduct(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "invalid data");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getInvoiceById")
	public ResponseEntity<Object> getInvoiceById(@RequestParam String purchasCode) {
		PurchaseInvoiceDto dto = this.service.getPurchaseInvoice(purchasCode);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid purcahse code");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllInvoice")
	public ResponseEntity<Object> getAllInvoice(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int pageSize) {
		Map<String, Object> map = this.service.getAllPurchseInvoise(page, pageSize);
		if (map.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "empty map data");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, map);
	}

	@GetMapping("/getPurchaseInvoice-pdf")
	public ResponseEntity<byte[]> getMethodName(@RequestParam String purchaseCode) throws Exception {
		byte[] pdfBytes = service.generatePdfByPurchaseCode(purchaseCode);
		if (pdfBytes != null && pdfBytes.length > 0) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + purchaseCode + ".pdf")
					.body(pdfBytes);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}
