package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
