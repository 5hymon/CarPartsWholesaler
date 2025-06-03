package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.OrderDetail;
import com.wholesaler.backend.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/details")
public class OrderDetailController {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    // GET /details/all - get all details
    @GetMapping("/all")
    public List<OrderDetail> getAllDetails() {
        return orderDetailsRepository.findAll();
    }

    // GET /details/{detailId} - get detail by ID
    @GetMapping("/{detailId}")
    public ResponseEntity<OrderDetail> getDetail(@PathVariable("detailId") Integer detailId) {
        Optional<OrderDetail> detail = orderDetailsRepository.findById(detailId);
        if (detail.isPresent()) {
            return new ResponseEntity<>(detail.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new detail
    @PostMapping
    public OrderDetail addDetail(@RequestBody OrderDetail detail) {
        return orderDetailsRepository.save(detail);
    }

    // PUT - update detail by ID
    @PutMapping("/{detailId}")
    public ResponseEntity<OrderDetail> updateDetail(@PathVariable("detailId") Integer detailId, @RequestBody OrderDetail updatedOrder) {
        Optional<OrderDetail> detailOptional = orderDetailsRepository.findById(detailId);
        if (detailOptional.isPresent()) {
            OrderDetail detail = detailOptional.get();
            detail.setOrder(updatedOrder.getOrder());
            detail.setPart(updatedOrder.getPart());
            detail.setUnitPrice(updatedOrder.getUnitPrice());
            detail.setQuantity(updatedOrder.getQuantity());
            detail.setDiscount(updatedOrder.getDiscount());
            return ResponseEntity.ok(orderDetailsRepository.save(detail));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete detail by ID
    @DeleteMapping("/{detailId}")
    public ResponseEntity<OrderDetail> deleteOrder(@PathVariable("detailId") Integer detailId) {
        Optional<OrderDetail> detailOptional = orderDetailsRepository.findById(detailId);
        if (detailOptional.isPresent()) {
            orderDetailsRepository.deleteById(detailId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
