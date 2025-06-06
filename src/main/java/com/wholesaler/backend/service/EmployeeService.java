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
    public Optional<EmployeeDTO> getEmployeeByIdAsDTO(Integer employeeId) {
        return employeeRepository.findById(employeeId).map(this::convertEmployeeToDTO);
    }

    // post new employee
    public Employee addEmployee(String firstName, String lastName, String emailAddress, String phoneNumber, String address, String city, String postalCode, String country) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmailAddress(emailAddress);
        employee.setPhoneNumber(phoneNumber);
        employee.setAddress(address);
        employee.setCity(city);
        employee.setPostalCode(postalCode);
        employee.setCountry(country);
        employee.setOrders(null);
        return employeeRepository.save(employee);
    }

    // post updated employee
    public Employee saveEmployee(Employee customer) {
        return employeeRepository.save(customer);
    }

    // put
    public Optional<Employee> updateEmployee(Integer employeeId, String firstName, String lastName, String emailAddress, String phoneNumber, String address, String city, String postalCode, String country) {
        return employeeRepository.findById(employeeId).map(employee -> {
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmailAddress(emailAddress);
            employee.setPhoneNumber(phoneNumber);
            employee.setAddress(address);
            employee.setCity(city);
            employee.setPostalCode(postalCode);
            employee.setCountry(country);
            employee.setOrders(employee.getOrders());
            return employeeRepository.save(employee);
        });
    }

    // delete
    public void deleteEmployee(Integer employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        }
    }
}
