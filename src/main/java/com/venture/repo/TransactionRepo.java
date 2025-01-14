package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
