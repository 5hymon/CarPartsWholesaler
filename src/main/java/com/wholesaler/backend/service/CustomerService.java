package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.CustomerDTO;
import com.wholesaler.backend.dto.OrderSimpleDTO;
import com.wholesaler.backend.model.Customer;
import com.wholesaler.backend.model.Order;
import com.wholesaler.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerDTO convertCustomerToDTO(Customer customer) {
        List<OrderSimpleDTO> orderDTOs = customer.getOrders().stream()
                .map(this::convertOrderToSimpleDTO)
                .collect(Collectors.toList());

        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmailAddress(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getCity(),
                customer.getPostalCode(),
                customer.getCountry(),
                orderDTOs
        );
    }

    private OrderSimpleDTO convertOrderToSimpleDTO(Order order) {
        return new OrderSimpleDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getPaymentMethod()
        );
    }

    // get all
    public List<CustomerDTO> getAllCustomersAsDTO() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(this::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<CustomerDTO> getCustomerByIdAsDTO(Integer customerId) {
        return customerRepository.findById(customerId).map(this::convertCustomerToDTO);
    }

    // post new customer
    public Customer addCustomer(String firstName, String lastName, String emailAddress, String phoneNumber, String address, String city, String postalCode, String country) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmailAddress(emailAddress);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setPostalCode(postalCode);
        customer.setCountry(country);
        customer.setOrders(null);
        customer.setPassword(customer.setFirstPassword());
        return customerRepository.save(customer);
    }

    // post updated customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // put
    public Optional<Customer> updateCustomer(Integer customerId, String firstName, String lastName, String emailAddress, String phoneNumber, String address, String city, String postalCode, String country) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmailAddress(emailAddress);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setPostalCode(postalCode);
            customer.setCountry(country);
            customer.setOrders(customer.getOrders());
            return customerRepository.save(customer);
        });
    }

    // delete
    public void deleteCustomer(Integer customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
        }
    }
}
