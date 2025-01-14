package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.ProductMaster;

@Repository
public interface ProductMasterRepo extends JpaRepository<ProductMaster, Long> {

}
