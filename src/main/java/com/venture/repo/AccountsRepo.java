package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.Accounts;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {

}
