package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.SupplierMaster;

@Repository
public interface SupplierMasterRepo extends JpaRepository<SupplierMaster, Long> {

}
