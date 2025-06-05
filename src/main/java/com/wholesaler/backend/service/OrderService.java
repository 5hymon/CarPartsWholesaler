package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.OrderDTO;
import com.wholesaler.backend.dto.OrderDetailDTO;
import com.wholesaler.backend.model.Order;
import com.wholesaler.backend.model.OrderDetail;
import com.wholesaler.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO convertOrderToDTO(Order order) {
        List<OrderDetailDTO> detailsDTO = order.getOrderDetails().stream()
                .map(this::convertOrderDetailToDTO)
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getPaymentMethod(),
                order.getEmployee().getEmployeeId(),
                order.getCustomer().getCustomerId(),
                detailsDTO
        );
    }

    private OrderDetailDTO convertOrderDetailToDTO(OrderDetail detail) {
        var part = detail.getPart();

        return new OrderDetailDTO(
                detail.getPartId(),
                part.getPartName(),
                part.getPartDescription(),
                part.getUnitPrice(),
                detail.getQuantity(),
                detail.getDiscount(),
                detail.getUnitPrice()
        );
    }

    // get all
    public List<OrderDTO> getAllOrdersAsDTO() {
        return orderRepository.findAll().stream()
                .map(this::convertOrderToDTO)
                .collect(Collectors.toList());
    }

    // get mapped by id
    public Optional<OrderDTO> getOrderByIdAsDTO(Integer id) {
        return orderRepository.findById(id).map(this::convertOrderToDTO);
    }

    // post
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // put
    public Optional<Order> updateOrder(Integer id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setEmployee(updatedOrder.getEmployee());
            order.setCustomer(updatedOrder.getCustomer());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setOrderStatus(updatedOrder.getOrderStatus());
            order.setPaymentMethod(updatedOrder.getPaymentMethod());
            return orderRepository.save(order);
        });
    }

    // delete
    public void deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        }
    }
}
