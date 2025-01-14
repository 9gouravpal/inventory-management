package com.venture.services.impl;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.venture.dtos.ProductMasterDto;
import com.venture.entitys.BrandMaster;
import com.venture.entitys.Category;
import com.venture.entitys.ProductMaster;
import com.venture.entitys.SubCategory;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.BrandMasterRepository;
import com.venture.repo.CategoryRepository;
import com.venture.repo.ProductMasterRepo;
import com.venture.repo.SubCategoryRepo;
import com.venture.service.ProductMasterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductMasterServiceImpl implements ProductMasterService {

	private final ProductMasterRepo productMasterRepo;
	private final InventoryMapper mapper;
	private final SubCategoryRepo subCategoryRepo;
	private final CategoryRepository categoryRepository;
	private final BrandMasterRepository brandMasterRepository;

	public ProductMasterServiceImpl(ProductMasterRepo productMasterRepo, InventoryMapper mapper,
			SubCategoryRepo subCategoryRepo, CategoryRepository categoryRepository,
			BrandMasterRepository brandMasterRepository) {
		super();
		this.productMasterRepo = productMasterRepo;
		this.mapper = mapper;
		this.subCategoryRepo = subCategoryRepo;
		this.categoryRepository = categoryRepository;
		this.brandMasterRepository = brandMasterRepository;
	}

	@Override
	public ProductMasterDto addPrductDetails(ProductMasterDto dto, MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				// Validate file size (e.g., max 5MB)
				if (file.getSize() > 5 * 1024 * 1024) {
					throw new IllegalArgumentException("File size should not exceed 5MB");
				}

				// Validate file type
				String contentType = file.getContentType();
				if (contentType != null && !contentType.startsWith("image/")) {
					throw new IllegalArgumentException("Only image files are allowed");
				}
			}

			ProductMaster master = this.mapper.toProductMaster(dto);

			if (file != null && !file.isEmpty()) {
				master.setProductImage(file.getBytes());
			}

			master = this.productMasterRepo.saveAndFlush(master);
			return this.mapper.toProductMasterDto(master);

		} catch (Exception e) {
			log.error("Error adding product details: {}", e.getMessage());
			throw new RuntimeException("Failed to add product details", e);
		}
	}

//	@Override
//	public ProductMasterDto addPrductDetails(ProductMasterDto dto, MultipartFile file) {
//		try {
//			ProductMaster master = this.mapper.toProductMasterDto(dto);
//			master.setProductImage(file.getBytes());
//			master = this.productMasterRepo.saveAndFlush(master);
//			dto = this.mapper.toProductMaster(master);
//		} catch (Exception e) {
//			log.error("Internal service errors:{}", e.getMessage());
//		}
//		return dto;
//	}

	@Override
	public List<ProductMasterDto> getAllProduct() {
		List<ProductMasterDto> list = new LinkedList<>();
		try {
			List<ProductMaster> masters = this.productMasterRepo.findAll();
			for (ProductMaster productMaster : masters) {
				ProductMasterDto dto = this.mapper.toProductMasterDto(productMaster);
				BrandMaster brandMaster = this.brandMasterRepository.findById(dto.getBrandId())
						.orElseThrow(() -> new RuntimeException("Brand Id invalid"));
				SubCategory subCategory = this.subCategoryRepo.findById(dto.getSubCategoryId())
						.orElseThrow(() -> new RuntimeException("subCategoryId not found"));
				Category category = this.categoryRepository.findById(dto.getCategoryId())
						.orElseThrow(() -> new RuntimeException("CategoryId invalid"));
				dto.setBrandName(brandMaster.getBrandName());
				dto.setSubCategoryName(subCategory.getSubCategoryName());
				dto.setCategoryName(category.getCategoryName());
				if (productMaster.getProductImage() != null) {
					dto.setProductImage(Base64.getEncoder().encodeToString(productMaster.getProductImage()));
				} else {
					log.warn("Product image is null for product {}", productMaster.getProductId());
					dto.setProductImage(null);
				}
				list.add(dto);
			}
		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return list;
	}

	@Override
	public ProductMasterDto getProductById(Long productId) {
		ProductMasterDto dto = new ProductMasterDto();

		try {
			Optional<ProductMaster> optional = this.productMasterRepo.findById(productId);
			if (optional.isPresent()) {

				ProductMaster master = optional.get();

				String base64 = Base64.getEncoder().encodeToString(master.getProductImage());
				dto = this.mapper.toProductMasterDto(master);
				dto.setProductImage(base64);
				dto.setFile(null);

			}
		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public ProductMasterDto updateProductMaster(ProductMasterDto dto, MultipartFile file) {
		ProductMaster master = this.mapper.toProductMaster(dto);
		try {
			Optional<ProductMaster> optional = this.productMasterRepo.findById(master.getProductId());
			if (optional.isPresent()) {
				master.setProductId(optional.get().getProductId());
				master.setProductImage(file.getBytes());
				this.productMasterRepo.save(master);
				dto = this.mapper.toProductMasterDto(master);

			}

		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}
}
