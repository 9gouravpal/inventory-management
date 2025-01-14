package com.venture.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.PurchaseInvoice;

@Repository
public interface PurchaseInvoiceRepo extends JpaRepository<PurchaseInvoice, Long> {

	Optional<PurchaseInvoice> findByPurchseCode(String purchaeCode);

}
