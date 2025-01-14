package com.venture.services.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.venture.dtos.PurchaseInvoiceDto;
import com.venture.entitys.PurchaseInvoice;
import com.venture.entitys.SupplierMaster;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.ProductMasterRepo;
import com.venture.repo.PurchaseInvoiceRepo;
import com.venture.repo.SupplierMasterRepo;
import com.venture.service.PurchaseInvoiceService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseInvoiceServiceImpl implements PurchaseInvoiceService {

	private final PurchaseInvoiceRepo purchaseInvoiceRepo;
	private final InventoryMapper mapper;
	private final ProductMasterRepo productMasterRepo;
	private final SupplierMasterRepo supplierMasterRepo;

	public PurchaseInvoiceServiceImpl(PurchaseInvoiceRepo purchaseInvoiceRepo, InventoryMapper mapper,
			ProductMasterRepo productMasterRepo, SupplierMasterRepo supplierMasterRepo) {
		super();
		this.purchaseInvoiceRepo = purchaseInvoiceRepo;
		this.mapper = mapper;
		this.productMasterRepo = productMasterRepo;
		this.supplierMasterRepo = supplierMasterRepo;
	}

	@Override
	public PurchaseInvoiceDto purchaseProduct(PurchaseInvoiceDto dto) {
		try {
			PurchaseInvoice invoice = this.mapper.toPurchaseInvoice(dto);
			invoice.setPurchaseDate(LocalDate.now());
			invoice.setPurchseCode(productPurchaseCode());
			invoice = this.purchaseInvoiceRepo.saveAndFlush(invoice);
			dto = this.mapper.toPurchaseInvoiceDto(invoice);

		} catch (Exception e) {
			log.error("Internal service errors:{}", e.getMessage());

		}
		return dto;
	}

	public String productPurchaseCode() {
		String purchase = "PR";
		String subString = System.currentTimeMillis() + purchase;
		return subString.substring(3);
	}

	@Override
	public PurchaseInvoiceDto getPurchaseInvoice(String purchaeCode) {
		try {
			Optional<PurchaseInvoice> optional = this.purchaseInvoiceRepo.findByPurchseCode(purchaeCode);
			if (!optional.isPresent()) {
				throw new IllegalArgumentException("invalid purchase Id");
			}
			return this.mapper.toPurchaseInvoiceDto(optional.get());
		} catch (RuntimeException e) {
			log.error("Internal service error:{}", e.getMessage(), purchaeCode);
			return null;
		}
	}

	@Override
	public Map<String, Object> getAllPurchseInvoise(int page, int pageSize) {
		Map<String, Object> map = new HashMap<>();
		try {

			Pageable pageable = PageRequest.of(page, pageSize);
			Page<PurchaseInvoice> page2 = this.purchaseInvoiceRepo.findAll(pageable);
			List<PurchaseInvoiceDto> list = this.mapper.toListPurchaseInvoice(page2.getContent());
			map.put("object", list);
			map.put("totalPages", page2.getTotalPages());
			map.put("currentPage", page2.getNumber());
			map.put("totalItems", page2.getTotalElements());

		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return map;

	}

	@Override
	public byte[] generatePdfByPurchaseCode(String purchaseCode) throws Exception {
		Optional<PurchaseInvoice> optionalInvoice = this.purchaseInvoiceRepo.findByPurchseCode(purchaseCode);

		if (!optionalInvoice.isPresent()) {
			throw new IllegalArgumentException("Invalid purchase code");
		}

		PurchaseInvoice invoice = optionalInvoice.get();
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		document.open();

		// Add header
		Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
		Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

		Paragraph header = new Paragraph("Purchase Invoice", headerFont);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);
		document.add(Chunk.NEWLINE);

		// Add invoice details
		document.add(new Paragraph("Purchase Details:", boldFont));
		document.add(new Paragraph("Purchase Code: " + invoice.getPurchseCode(), normalFont));
		document.add(new Paragraph("Purchase Date: " + invoice.getPurchaseDate(), normalFont));
//		document.add(new Paragraph("Supplier ID: " + invoice.getSupplierId(), normalFont));
		Optional<SupplierMaster> master = this.supplierMasterRepo.findById(invoice.getSupplierId());
		if (!master.isPresent()) {
			throw new RuntimeException("Supplier Id not found");
		}
		document.add(new Paragraph("Supplier Name:" + master.get().getSupplierName(), normalFont));
		if (invoice.getSupplierMemo() != null && !invoice.getSupplierMemo().isEmpty()) {
			document.add(new Paragraph("Supplier Memo: " + invoice.getSupplierMemo(), normalFont));
		}

		document.add(Chunk.NEWLINE);

		// Create products table
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);

		// Add table headers
		Stream.of("Product", "Quantity", "Purchase Price", "Selling Price", "Tax %", "Amount").forEach(columnTitle -> {
			PdfPCell header1 = new PdfPCell();
			header1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header1.setBorderWidth(2);
			header1.setPhrase(new Phrase(columnTitle, boldFont));
			table.addCell(header1);
		});

		// Add product details
		IntStream.range(0, invoice.getProductId().size()).forEach(i -> {
			// Get product details if needed
			table.addCell(new Phrase(String.valueOf(invoice.getProductId().get(i)), normalFont));
			table.addCell(new Phrase(String.valueOf(invoice.getQuantity().get(i)), normalFont));
			table.addCell(new Phrase(String.format("₹%.2f", invoice.getPurchasePrice().get(i)), normalFont));
			table.addCell(new Phrase(String.format("₹%.2f", invoice.getSellingPrice().get(i)), normalFont));
			table.addCell(new Phrase(invoice.getTaxPercntage().get(i) + "%", normalFont));
			table.addCell(new Phrase(String.format("₹%.2f", invoice.getAmount().get(i)), normalFont));
		});

		document.add(table);
		document.add(Chunk.NEWLINE);

		// Add totals
		document.add(new Paragraph("Total Amount: ₹" + String.format("%.2f", invoice.getTotalAmount()), boldFont));
		document.add(new Paragraph("Tax Amount: ₹" + String.format("%.2f", invoice.getTaxAmount()), normalFont));
		document.add(new Paragraph("Payable Amount: ₹" + String.format("%.2f", invoice.getPaybleAmount()), boldFont));
		document.add(new Paragraph("Paid Amount: ₹" + String.format("%.2f", invoice.getPaidAmount()), normalFont));
		document.add(new Paragraph("Due Amount: ₹" + String.format("%.2f", invoice.getDueAmount()), boldFont));

		if (invoice.getNote() != null && !invoice.getNote().isEmpty()) {
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Note:", boldFont));
			document.add(new Paragraph(invoice.getNote(), normalFont));
		}

		document.close();
		return baos.toByteArray();
	}
}
