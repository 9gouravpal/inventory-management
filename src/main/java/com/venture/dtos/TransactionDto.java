package com.venture.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long transactionId;
	private LocalDate date;

	private Double amount;

	private List<Long> accountId;
	private String invoiceType;
	private String invoiceCode;
	private String debitAccount;
	private String creditAccount;
}
