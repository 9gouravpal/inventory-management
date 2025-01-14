package com.venture.services.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.venture.dtos.SaleInvoiceDto;
import com.venture.entitys.ProductMaster;
import com.venture.entitys.SaleInvoice;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.ProductMasterRepo;
import com.venture.repo.SaleInvoiceRepo;
import com.venture.service.SaleInvoiceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SaleInvoiceServiceImpl implements SaleInvoiceService {

	private SaleInvoiceRepo saleInvoiceRepo;
	private InventoryMapper mapper;
	private ProductMasterRepo productMasterRepo;

	public SaleInvoiceServiceImpl(SaleInvoiceRepo saleInvoiceRepo, InventoryMapper mapper,
			ProductMasterRepo productMasterRepo) {
		super();
		this.saleInvoiceRepo = saleInvoiceRepo;
		this.mapper = mapper;
		this.productMasterRepo = productMasterRepo;
	}

	@Override
	public SaleInvoiceDto createSaleInvoice(SaleInvoiceDto dto) {
		try {
			SaleInvoice saleInvoice = this.mapper.toSaleInvoice(dto);
			saleInvoice.setSaleCode(productPurchaseCode());
			saleInvoice.setDate(LocalDate.now());
			saleInvoice = this.saleInvoiceRepo.saveAndFlush(saleInvoice);
			dto = this.mapper.toSaleInvoiceDto(saleInvoice);

			List<Long> productIds = saleInvoice.getProductId();
			List<Integer> saleQuantities = dto.getSaleQuntity();

			if (productIds.size() != saleQuantities.size()) {
				throw new RuntimeException("Mismatch between product IDs and sale quantities");
			}

			int index = 0;
			for (Long productId : productIds) {
				Integer saleQuantity = saleQuantities.get(index);

				ProductMaster master = this.productMasterRepo.findById(productId)
						.orElseThrow(() -> new RuntimeException("Product ID not found: " + productId));

				if (master.getQuantity() < saleQuantity) {
					throw new RuntimeException("Insufficient stock for product ID: " + productId);
				}

				master.setQuantity(master.getQuantity() - saleQuantity);
				this.productMasterRepo.save(master);

				index++;
			}
			return dto;
		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
			return null;
		}

	}

	public String productPurchaseCode() {
		String purchase = "SL";
		String subString = System.currentTimeMillis() + purchase;
		return subString.substring(3);
	}

	@Override
	public Map<String, Object> getAllSaleInvoice(int page, int pageSize) {
		Map<String, Object> map = new HashMap<>();
		try {
			Pageable pageable = PageRequest.of(page, pageSize);

			Page<SaleInvoice> page1 = this.saleInvoiceRepo.findAll(pageable);
			List<SaleInvoiceDto> dtos = this.mapper.toListSaleInvoice(page1.getContent());
			map.put("object", dtos);
			map.put("totalPages", page1.getTotalPages());
			map.put("currentPage", page1.getNumber());
			map.put("totalItems", page1.getTotalElements());

		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return map;
	}

	@Override
	public byte[] generatePdfBySaleCode(String saleCode) throws Exception {
		// Find sale invoice by sale code
		SaleInvoice invoice = saleInvoiceRepo.findBySaleCode(saleCode)
				.orElseThrow(() -> new RuntimeException("Invoice not found for sale code: " + saleCode));

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();

		// Add header
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		Paragraph header = new Paragraph("Sales Invoice", headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
		document.add(Chunk.NEWLINE);

		// Add invoice details
		Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
		Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

		document.add(new Paragraph("Invoice Details:", boldFont));
		document.add(new Paragraph("Sale Code: " + invoice.getSaleCode(), normalFont));
		document.add(new Paragraph("Date: " + invoice.getDate(), normalFont));
		document.add(Chunk.NEWLINE);

		// Customer details
		document.add(new Paragraph("Customer Information:", boldFont));
		document.add(new Paragraph("Name: " + invoice.getCostumerName(), normalFont));
		document.add(new Paragraph("Phone: " + invoice.getCostumerPhoneNo(), normalFont));
		document.add(new Paragraph("Email: " + invoice.getCostumerEmail(), normalFont));
		document.add(Chunk.NEWLINE);

		// Create table for products
		PdfPTable table = new PdfPTable(4); // 4 columns
		table.setWidthPercentage(100);

		// Add table headers
		Stream.of("Product", "Quantity", "Price", "Total").forEach(columnTitle -> {
			PdfPCell header1 = new PdfPCell();
			header1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header1.setBorderWidth(2);
			header1.setPhrase(new Phrase(columnTitle, boldFont));
			table.addCell(header1);
		});

		// Add product details
		IntStream.range(0, invoice.getProductId().size()).forEach(i -> {
			ProductMaster product = productMasterRepo.findById(invoice.getProductId().get(i)).orElse(null);

			if (product != null) {
				table.addCell(new Phrase(product.getProductName(), normalFont));
				table.addCell(new Phrase(String.valueOf(invoice.getSaleQuntity().get(i)), normalFont));
				table.addCell(new Phrase(String.format("₹%.2f", invoice.getSalePrice().get(i)), normalFont));
				table.addCell(new Phrase(
						String.format("₹%.2f", invoice.getSalePrice().get(i) * invoice.getSaleQuntity().get(i)),
						normalFont));
			}
		});

		document.add(table);
		document.add(Chunk.NEWLINE);

		// Add totals
		document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", invoice.getTotalAmount()), boldFont));
		document.add(new Paragraph("GST: ₹" + String.format("%.2f", invoice.getGst()), normalFont));
		document.add(new Paragraph("Discount (" + invoice.getDiscountPercntage() + "%): ₹"
				+ String.format("%.2f", invoice.getDiscountAmount()), normalFont));
		document.add(new Paragraph("Net Amount: ₹" + String.format("%.2f", invoice.getNetAmount()), boldFont));
		document.add(new Paragraph("Payment Mode: " + invoice.getPayMode(), normalFont));

		document.close();
		return baos.toByteArray();
	}

}
