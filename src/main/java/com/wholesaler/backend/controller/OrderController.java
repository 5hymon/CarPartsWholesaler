package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.OrderDTO;
import com.wholesaler.backend.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /orders/all - get all orders
    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrdersAsDTO();
    }

    // GET /orders/{orderId} - get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") Integer orderId) {
        Optional<OrderDTO> order = orderService.getOrderByIdAsDTO(orderId);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new order
    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // PUT - update order by ID
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable("orderId") Integer orderId, @RequestBody Order updatedOrder) {
        Optional<Order> orderOptional = orderService.updateOrder(orderId, updatedOrder);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return ResponseEntity.ok(orderService.saveOrder(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete order by ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") Integer orderId) {
        Optional<OrderDTO> orderOptional = orderService.getOrderByIdAsDTO(orderId);
        if (orderOptional.isPresent()) {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
