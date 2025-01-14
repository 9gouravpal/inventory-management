package com.venture.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand_master")
public class BrandMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long brandId;
	private String brandName;
	private Long subCategoryId;

}
