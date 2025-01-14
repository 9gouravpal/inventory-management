package com.venture.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierMasterDto {

	private Long suplierId;
	private String supplierName;
	private String phoneNo;

	private String email;
	private String addres;
}
