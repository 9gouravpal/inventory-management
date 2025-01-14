package com.venture.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.venture.dtos.AccountsDto;
import com.venture.dtos.BrandMasterDto;
import com.venture.dtos.CategoryDto;
import com.venture.dtos.OrderEntityDto;
import com.venture.dtos.ProductMasterDto;
import com.venture.dtos.PurchaseInvoiceDto;
import com.venture.dtos.SaleInvoiceDto;
import com.venture.dtos.SubCategoryDto;
import com.venture.dtos.SupplierMasterDto;
import com.venture.dtos.TransactionDto;
import com.venture.entitys.Accounts;
import com.venture.entitys.BrandMaster;
import com.venture.entitys.Category;
import com.venture.entitys.OrderEntity;
import com.venture.entitys.ProductMaster;
import com.venture.entitys.PurchaseInvoice;
import com.venture.entitys.SaleInvoice;
import com.venture.entitys.SubCategory;
import com.venture.entitys.SupplierMaster;
import com.venture.entitys.Transaction;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface InventoryMapper {

	Category toCategory(CategoryDto dto);

	CategoryDto toCategoryDto(Category category);

	List<CategoryDto> toListCategory(List<Category> list);

	SubCategory toSubCategory(SubCategoryDto dto);

	List<SubCategoryDto> toListSubCategory(List<SubCategory> list);

	SubCategoryDto toSubCategoryDto(SubCategory subCategory);

	BrandMaster toBrandMaster(BrandMasterDto dto);

	BrandMasterDto toBrandMasterDto(BrandMaster brandMaster);

	// Removed invalid spaces and ensured correctness
//	@Mapping(target = "productImage", ignore = true)
//	ProductMaster toProductMasterDto(ProductMasterDto dto);

//	ProductMasterDto toProductMaster(ProductMasterDto dto);
//
//	@Mapping(target = "productImage", ignore = true)
//	ProductMasterDto toProductMaster(ProductMaster master);

	SupplierMaster toSupplierMaster(SupplierMasterDto dto);

	SupplierMasterDto toSupplierMasterDto(SupplierMaster master);

	List<SupplierMasterDto> toListSupplierMaster(List<SupplierMaster> list);

	PurchaseInvoice toPurchaseInvoice(PurchaseInvoiceDto dto);

	PurchaseInvoiceDto toPurchaseInvoiceDto(PurchaseInvoice invoice);

	List<PurchaseInvoiceDto> toListPurchaseInvoice(List<PurchaseInvoice> content);

	SaleInvoice toSaleInvoice(SaleInvoiceDto dto);

	SaleInvoiceDto toSaleInvoiceDto(SaleInvoice saleInvoice);

	List<SaleInvoiceDto> toListSaleInvoice(List<SaleInvoice> content);

	Accounts toAccount(AccountsDto dto);

	AccountsDto toAccountDto(Accounts accounts);

	List<AccountsDto> toListAccount(List<Accounts> list);

	Transaction toTrancscation(TransactionDto dto);

	TransactionDto toTranscstionDto(Transaction transaction);

	List<TransactionDto> toListTransctionDto(List<Transaction> list);

	OrderEntity toOrderEntity(OrderDto dto);

	OrderEntity toOrderEntity(OrderEntityDto dto);

	OrderEntityDto toOrderDto(OrderEntity entity);

	List<OrderEntityDto> toListOrder(List<OrderEntity> list);

	@Mapping(target = "productImage", ignore = true)
	ProductMaster toProductMaster(ProductMasterDto dto);

	@Mapping(target = "productImage", ignore = true)
	ProductMasterDto toProductMasterDto(ProductMaster master);
}
