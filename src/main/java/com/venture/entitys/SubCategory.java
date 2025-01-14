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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sub_category")
@Entity
public class SubCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long subCategoryId;
	private Long categoryId;
	private String subCategoryName;
}
