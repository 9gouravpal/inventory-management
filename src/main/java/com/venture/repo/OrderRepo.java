package com.venture.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venture.entitys.OrderEntity;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

}
