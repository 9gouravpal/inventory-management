package com.venture.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.SaleInvoice;

@Repository
public interface SaleInvoiceRepo extends JpaRepository<SaleInvoice, Long> {

	Optional<SaleInvoice> findBySaleCode(String saleCode);

}
