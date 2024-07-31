package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDetailDTO;
import com.example.orderservice.entities.OrderDetail;
import com.example.orderservice.entities.OrderDetailId;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.mapper.OrderDetailMapper;

import com.example.orderservice.repositories.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    public List<OrderDetailDTO> findOrderDetailByOrderId(String id) {
        return orderDetailRepository.findOrderDetailsByOrder_Id(id).stream().map(orderDetailMapper.INSTANCE::orderDetailToOrderDetailDTO).collect(Collectors.toList());
    }

    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO == null) {
            throw new CustomException("OrderDetailDTO is null", HttpStatus.BAD_REQUEST);
        }
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(orderDetailDTO.getId());
        if (orderDetail != null) {
            if (orderDetail.getUnitPrice() == null) {
                orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());
            }
            orderDetail.setQuantity(orderDetail.getQuantity() + orderDetailDTO.getQuantity());
            return orderDetailMapper.INSTANCE.orderDetailToOrderDetailDTO(orderDetailRepository.save(orderDetail));
        }

        orderDetail = orderDetailMapper.INSTANCE.orderDetailDTOToOrderDetail(orderDetailDTO);
        orderDetail.setId(new OrderDetailId(orderDetailDTO.getId().getOrderId(), orderDetailDTO.getId().getProductId()));
        orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());

        return orderDetailMapper.INSTANCE.orderDetailToOrderDetailDTO(
                orderDetailRepository.save(orderDetail));
    }

    public OrderDetailDTO updateOrderDetail(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO == null) {
            throw new CustomException("OrderDetailDTO is null", HttpStatus.BAD_REQUEST);
        }
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(orderDetailDTO.getId());
        if (orderDetail == null) {
            throw new CustomException("OrderDetail not found", HttpStatus.NOT_FOUND);
        }
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetailMapper.INSTANCE.orderDetailToOrderDetailDTO(orderDetailRepository.save(orderDetail));
    }

    public void deleteOrderDetail(OrderDetailId id) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);
        if (orderDetail == null) {
            throw new CustomException("OrderDetail not found", HttpStatus.NOT_FOUND);
        }
        orderDetailRepository.delete(orderDetail);
    }

    public OrderDetailDTO findOrderDetailById(OrderDetailId id) {
        OrderDetail orderDetail = orderDetailRepository.findOrderDetailById(id);
//        if (orderDetail == null) {
//            throw new CustomException("OrderDetail not found", HttpStatus.NOT_FOUND);
//        }
        return orderDetailMapper.INSTANCE.orderDetailToOrderDetailDTO(orderDetail);
    }
}
