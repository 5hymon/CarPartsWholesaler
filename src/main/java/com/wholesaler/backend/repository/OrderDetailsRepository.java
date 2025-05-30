package com.wholesaler.backend.repository;

import com.wholesaler.backend.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Integer> {
}
