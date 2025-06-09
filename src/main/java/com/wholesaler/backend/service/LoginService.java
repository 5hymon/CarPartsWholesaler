package com.wholesaler.backend.service;

import com.wholesaler.backend.model.Customer;
import com.wholesaler.backend.model.Employee;
import com.wholesaler.backend.repository.CustomerRepository;
import com.wholesaler.backend.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // log in
    public Integer areLogCredentialsCorrect(String emailAddress, String password) {
        Optional<Customer> customerOpt = customerRepository.findByEmailAddress(emailAddress);
//        System.out.println("Given Email: " + emailAddress);
//        System.out.println("Given Password: " + password);

        if (customerOpt.isPresent()) {
            Customer c = customerOpt.get();
//            System.out.println("USR: " + c.getEmailAddress());
//            System.out.println("USR: " + c.getPassword());
            if (passwordEncoder.matches(password, c.getPassword())) {
                return 1; // zwykły użytkownik
            } else if (password.equals(c.getPassword())) {
                return 1;
            }
        }
        Optional<Employee> employeeOpt = employeeRepository.findByEmailAddress(emailAddress);
        if (employeeOpt.isPresent()) {
            Employee e = employeeOpt.get();
//            System.out.println("EMP: " + e.getEmailAddress());
//            System.out.println("EMP: " + e.getPassword());
            if (passwordEncoder.matches(password, e.getPassword())) {
                return 2; // admin
            } else if (password.equals(e.getPassword())) {
                return 2;
            }
        }
        return 0; // nieudane logowanie
    }

}



