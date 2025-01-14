package com.venture.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.SubCategory;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

	List<SubCategory> findByCategoryId(Long categoryId);

}
