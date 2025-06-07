package com.wholesaler.backend.repository;

import com.wholesaler.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o.orderId FROM Order o ORDER BY 1 DESC LIMIT 1")
    Integer getLastOrderId();
}
