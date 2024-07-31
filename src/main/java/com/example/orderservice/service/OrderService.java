package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.specification.SearchBody;
import org.springframework.data.domain.Page;

public interface OrderService {
    Page<OrderDTO> findAllAndSorting(SearchBody searchBody);
    OrderDTO findById(String id);
    OrderDTO createOrder(OrderDTO order);
    Object updateOrder(OrderDTO order);
    Object deleteOrder(String id);
    Page<OrderDTO> findByUserId(Long userId, SearchBody searchBody);
    OrderDTO findCartByUserId(Long userId);
}



