package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.OrderDTO;
import com.wholesaler.backend.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Zarządzanie zamówieniami")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET /orders/all - get all orders
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie zamówienia")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrdersAsDTO();
    }

    // GET /orders/{orderId} - get order by ID
    @GetMapping("/{orderId}")
    @Operation(summary = "Pobierz zamówienie po ID")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") Integer orderId) {
        Optional<OrderDTO> order = orderService.getOrderByIdAsDTO(orderId);
        if (order.isPresent()) {
            return new ResponseEntity<>(order.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /orders/byemail/{emailAddress} - get order by email
    @GetMapping("/byemail/{emailAddress}")
    @Operation(summary = "Pobierz zamówienia klienta po adresie email")
    public ResponseEntity<List<OrderDTO>> getOrdersByEmail(@PathVariable String emailAddress) {
        List<OrderDTO> orders = orderService.getOrdersByEmailAsDTO(emailAddress);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    // POST - add new order
    @PostMapping
    @Operation(summary = "Dodaj nowe zamówienie")
    public ResponseEntity<OrderDTO> addOrder(
            @RequestParam("customerEmailAddress") String customerEmailAddress,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("partId") List<Integer> partsId,
            @RequestParam("quantity") List<Integer> quantities) {
        Order order = orderService.addOrder(customerEmailAddress, "W trakcie", paymentMethod, partsId, quantities, 0.0);
        if (order != null) {
            OrderDTO orderDTO = orderService.convertOrderToDTO(orderService.saveOrder(order));
            return ResponseEntity.ok(orderDTO);
        } else {
            return null;
        }
    }

    // PUT - update order by ID
    @PutMapping("/{orderId}")
    @Operation(summary = "Aktualizuj zamówienie o podanym ID")
    public ResponseEntity<Order> updateOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestParam("orderStatus") String orderStatus,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("discount") Double discount) {
        Optional<Order> orderOptional = orderService.updateOrder(orderId, orderStatus, paymentMethod, quantity, discount);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return ResponseEntity.ok(orderService.saveOrder(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete order by ID
    @DeleteMapping("/{orderId}")
    @Operation(summary = "Usuń zamówienie o podanym ID")
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
