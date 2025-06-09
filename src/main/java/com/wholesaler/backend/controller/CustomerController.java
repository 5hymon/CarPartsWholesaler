package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.CustomerDTO;
import com.wholesaler.backend.service.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers", description = "Zarządzanie klientami i ich zamówieniami")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /customers/all - get all customers
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkich klientów")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomersAsDTO();
    }

    // GET /customers/{customerId} - get customer by ID
    @GetMapping("/{customerId}")
    @Operation(summary = "Pobierz klienta po ID")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId") Integer customerId) {
        Optional<CustomerDTO> customer = customerService.getCustomerByIdAsDTO(customerId);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email/{emailAddress}")
    @Operation(summary = "Pobierz klienta po email")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("emailAddress") String emailAddress) {
        Optional<CustomerDTO> customer = customerService.getCustomerByEmailAsDTO(emailAddress);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new customer
    @PostMapping
    @Operation(summary = "Dodaj nowego klienta")
    public Customer addCustomer(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("country") String country) {
        return customerService.addCustomer(firstName, lastName, emailAddress, password, phoneNumber, address, city, postalCode, country);
    }

    // PUT - update customer by ID
    @PutMapping("/{customerId}")
    @Operation(summary = "Aktualizuj klienta o podanym ID")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("country") String country) {
        Optional<Customer> customerOptional = customerService.updateCustomer(customerId, firstName, lastName, emailAddress, phoneNumber, address, city, postalCode, country);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return ResponseEntity.ok(customerService.saveCustomer(customer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete customer by ID
    @DeleteMapping("/{customerId}")
    @Operation(summary = "Usuń klienta o podanym ID")
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
