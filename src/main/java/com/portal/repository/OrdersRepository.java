package com.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portal.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByRazorpayOrderId(String razorpayOrderId);
}
