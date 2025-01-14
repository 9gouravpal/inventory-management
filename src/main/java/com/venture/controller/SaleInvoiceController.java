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

import com.venture.dtos.SaleInvoiceDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.SaleInvoiceService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sale-invoice")
@Tag(name = "Sale-Invoice-API")
public class SaleInvoiceController {

	private SaleInvoiceService service;
	private ResponseWithObject responseWithObject;

	public SaleInvoiceController(SaleInvoiceService service, ResponseWithObject responseWithObject) {
		super();
		this.service = service;
		this.responseWithObject = responseWithObject;
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createSaleInvoice(@RequestBody SaleInvoiceDto entity) {
		SaleInvoiceDto dto = this.service.createSaleInvoice(entity);
		if (dto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST, "invalid data");
		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);
	}

	@GetMapping("/getAllSales")
	public ResponseEntity<Object> getAllSales(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int pageSize) {
		Map<String, Object> map = service.getAllSaleInvoice(page, pageSize);
		if (map.isEmpty()) {
			return responseWithObject.generateResponse(AppConstants.NA, HttpStatus.NO_CONTENT, "no data found");
		}
		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, map);
	}

	@GetMapping("/download")
	public ResponseEntity<byte[]> generatePdf(@RequestParam("salecode") String salecode) throws Exception {
		byte[] pdfBytes = service.generatePdfBySaleCode(salecode);

		if (pdfBytes != null && pdfBytes.length > 0) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + salecode + ".pdf")
					.body(pdfBytes);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
