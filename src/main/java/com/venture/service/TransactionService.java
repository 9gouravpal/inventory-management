package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.TransactionDto;

@Service
public interface TransactionService {

	TransactionDto saveAllTerTransaction(TransactionDto dto);

	List<TransactionDto> getAllTransactionDtos();

}
