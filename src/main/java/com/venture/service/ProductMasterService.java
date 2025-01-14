package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.venture.dtos.ProductMasterDto;

@Service
public interface ProductMasterService {

	ProductMasterDto addPrductDetails(ProductMasterDto dto, MultipartFile file);

	List<ProductMasterDto> getAllProduct();

	ProductMasterDto getProductById(Long productId);

	ProductMasterDto updateProductMaster(ProductMasterDto dto, MultipartFile file);

}
