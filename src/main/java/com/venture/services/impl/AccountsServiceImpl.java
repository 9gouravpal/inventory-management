package com.venture.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import com.venture.dtos.AccountsDto;
import com.venture.entitys.Accounts;
import com.venture.mapper.InventoryMapper;
import com.venture.repo.AccountsRepo;
import com.venture.service.AccountsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountsServiceImpl implements AccountsService {

	private final AccountsRepo repo;
	private final InventoryMapper mapper;

	public AccountsServiceImpl(AccountsRepo repo, InventoryMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	@Override
	public AccountsDto createAccount(AccountsDto dto) {
		try {
			Accounts accounts = this.mapper.toAccount(dto);
			accounts = this.repo.saveAndFlush(accounts);
			dto = mapper.toAccountDto(accounts);
		} catch (Exception e) {
			log.error("Internal service error", e.getMessage());
		}
		return dto;
	}

	@Override
	public List<AccountsDto> getAllAccountsDtos() {
		try {
			List<Accounts> list = this.repo.findAll();
			return mapper.toListAccount(list);
		} catch (Exception e) {
			log.error("Ingernal service error:{}", e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public AccountsDto getByAccountId(Long accountId) {
		try {
			Optional<Accounts> accounts = this.repo.findById(accountId);
			if (!accounts.isPresent()) {
				throw new DataAccessResourceFailureException("account id not found");
			}
			return this.mapper.toAccountDto(accounts.get());
		} catch (Exception e) {
			log.error("Inernal service error:{}", e.getMessage());
			return null;
		}
	}

	@Override
	public AccountsDto updateAccoount(AccountsDto dto) {
		Accounts accounts = this.mapper.toAccount(dto);
		try {
			Optional<Accounts> optional = this.repo.findById(dto.getAccounId());
			if (!optional.isPresent()) {
				throw new RuntimeException("account id not found");

			}
			accounts.setAccounId(optional.get().getAccounId());
			this.repo.saveAndFlush(accounts);
			dto = this.mapper.toAccountDto(accounts);
			return dto;
		} catch (Exception e) {
			log.error("INternla service errror{}", e.getMessage());
			return null;
		}
	}
}
