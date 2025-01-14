package com.venture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.venture.dtos.AccountsDto;

@Service
public interface AccountsService {

	AccountsDto createAccount(AccountsDto dto);

	List<AccountsDto> getAllAccountsDtos();

	AccountsDto getByAccountId(Long accountId);

	AccountsDto updateAccoount(AccountsDto dto);

}
