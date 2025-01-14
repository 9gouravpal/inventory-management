package com.venture.services.impl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.TransactionDto;
import com.venture.entitys.Accounts;
import com.venture.entitys.Transaction;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.AccountsRepo;
import com.venture.repo.TransactionRepo;
import com.venture.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	private final InventoryMapper mapper;
	private final TransactionRepo transactionRepo;
	private final AccountsRepo accountsRepo;

	public TransactionServiceImpl(InventoryMapper mapper, TransactionRepo transactionRepo, AccountsRepo accountsRepo) {
		super();
		this.mapper = mapper;
		this.transactionRepo = transactionRepo;
		this.accountsRepo = accountsRepo;
	}

	@Override
	public TransactionDto saveAllTerTransaction(TransactionDto dto) {

		try {
			Transaction transaction = this.mapper.toTrancscation(dto);
			transaction.setDate(LocalDate.now());

			transaction = this.transactionRepo.saveAndFlush(transaction);
			dto = this.mapper.toTranscstionDto(transaction);

		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());
		}
		return dto;
	}

	@Override
	public List<TransactionDto> getAllTransactionDtos() {
		List<TransactionDto> transactionDtos = new LinkedList<>();
		try {
			List<Transaction> list = this.transactionRepo.findAll();
			for (Transaction transaction : list) {
				TransactionDto dto = this.mapper.toTranscstionDto(transaction);
				List<Long> accLongs = dto.getAccountId();
				int index = 0;
				for (Long account : accLongs) {
					Accounts accounts = this.accountsRepo.findById(account)
							.orElseThrow(() -> new RuntimeException("account id not found"));
					if (index == 0) {
						dto.setDebitAccount(accounts.getAccountName());
					} else if (index == 1) {
						dto.setCreditAccount(accounts.getAccountName());
					}
					index++;
				}
				transactionDtos.add(dto);
			}
		} catch (Exception e) {
			log.error("Internal service error:{}", e.getMessage());

		}
		return transactionDtos;
	}

}
