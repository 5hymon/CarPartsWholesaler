package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.CustomerDTO;
import com.wholesaler.backend.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /customers/all - get all customers
    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomersAsDTO();
    }

    // GET /customers/{customerId} - get customer by ID
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId") Integer customerId) {
        Optional<CustomerDTO> customer = customerService.getCustomerByIdAsDTO(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new customer
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // PUT - update customer by ID
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerService.updateCustomer(customerId, updatedCustomer);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return ResponseEntity.ok(customerService.saveCustomer(customer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete customer by ID
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        Optional<CustomerDTO> customerOptional = customerService.getCustomerByIdAsDTO(customerId);
        if (customerOptional.isPresent()) {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
