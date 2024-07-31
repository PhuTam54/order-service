package com.example.orderservice.repositories;

import com.example.orderservice.entities.OrderDetail;
import com.example.orderservice.entities.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findOrderDetailsByOrder_Id(String id);
    OrderDetail findOrderDetailById(OrderDetailId id);
}
