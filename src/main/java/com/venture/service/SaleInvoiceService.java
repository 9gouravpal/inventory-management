package com.venture.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.venture.dtos.SaleInvoiceDto;

@Service
public interface SaleInvoiceService {

	SaleInvoiceDto createSaleInvoice(SaleInvoiceDto dto);

	Map<String, Object> getAllSaleInvoice(int page, int pageSize);

	byte[] generatePdfBySaleCode(String saleCode) throws Exception;

}
