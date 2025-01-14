package com.venture.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.venture.dtos.PurchaseInvoiceDto;

@Service
public interface PurchaseInvoiceService {

	PurchaseInvoiceDto purchaseProduct(PurchaseInvoiceDto dto);

	PurchaseInvoiceDto getPurchaseInvoice(String purchaeCode);

	Map<String, Object> getAllPurchseInvoise(int page, int pageSize);

	byte[] generatePdfByPurchaseCode(String purchaseCode) throws Exception;

}
