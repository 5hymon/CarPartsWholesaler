package com.wholesaler.backend.service;

import com.wholesaler.backend.model.Customer;
import com.wholesaler.backend.model.Employee;
import com.wholesaler.backend.repository.CustomerRepository;
import com.wholesaler.backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public LoginService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    // log in
    public boolean areLogCredentialsCorrect(String emailAddress, String password) {
        Optional<Customer> customerOptional = customerRepository.findByEmailAddress(emailAddress);
        Optional<Employee> employeeOptional = employeeRepository.findByEmailAddress(emailAddress);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            return customer.getPassword().equals(password);
        } else if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return employee.getPassword().equals(password);
        }
        return false;
    }
}



