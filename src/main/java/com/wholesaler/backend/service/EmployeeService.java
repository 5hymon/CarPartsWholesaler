package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.EmployeeDTO;
import com.wholesaler.backend.dto.OrderSimpleDTO;
import com.wholesaler.backend.model.Employee;
import com.wholesaler.backend.model.Order;
import com.wholesaler.backend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        List<OrderSimpleDTO> orderDTOs = employee.getOrders().stream()
                .map(this::convertOrderToSimpleDTO)
                .collect(Collectors.toList());

        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmailAddress(),
                employee.getPhoneNumber(),
                employee.getAddress(),
                employee.getCity(),
                employee.getPostalCode(),
                employee.getCountry(),
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
    public List<EmployeeDTO> getAllEmployeesAsDTO() {
        List<Employee> customers = employeeRepository.findAll();

        return customers.stream()
                .map(this::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<EmployeeDTO> getEmployeeByIdAsDTO(Integer customerId) {
        return employeeRepository.findById(customerId).map(this::convertEmployeeToDTO);
    }

    // post
    public Employee saveEmployee(Employee customer) {
        return employeeRepository.save(customer);
    }

    // put
    public Optional<Employee> updateEmployee(Integer customerId, Employee updatedEmployee) {
        return employeeRepository.findById(customerId).map(customer -> {
            customer.setFirstName(customer.getFirstName());
            customer.setLastName(customer.getLastName());
            customer.setEmailAddress(customer.getEmailAddress());
            customer.setPhoneNumber(customer.getPhoneNumber());
            customer.setAddress(customer.getAddress());
            customer.setCity(customer.getCity());
            customer.setPostalCode(customer.getPostalCode());
            customer.setCountry(customer.getCountry());
            return employeeRepository.save(customer);
        });
    }

    // delete
    public void deleteEmployee(Integer customerId) {
        if (employeeRepository.existsById(customerId)) {
            employeeRepository.deleteById(customerId);
        }
    }
}
