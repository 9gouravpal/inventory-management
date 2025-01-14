package com.venture.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.BrandMaster;

@Repository
public interface BrandMasterRepository extends JpaRepository<BrandMaster, Long> {

	List<BrandMaster> findBySubCategoryId(Long subCategoryId);

}
