package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.EmployeeDTO;
import com.wholesaler.backend.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "Zarządzanie pracownikami i ich zamówieniami")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /employees/all - get all employees
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkich pracowników")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployeesAsDTO();
    }

    // GET /employees/{employeeId} - get employee by ID
    @GetMapping("/{employeeId}")
    @Operation(summary = "Pobierz pracownika po ID")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") Integer employeeId) {
        Optional<EmployeeDTO> employee = employeeService.getEmployeeByIdAsDTO(employeeId);
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new employee
    @PostMapping
    @Operation(summary = "Dodaj nowego pracownika")
    public Employee addEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("password") String password,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("country") String country) {
        return employeeService.addEmployee(firstName, lastName, emailAddress, password, phoneNumber, address, city, postalCode, country);
    }

    // PUT - update employee by ID
    @PutMapping("/{employeeId}")
    @Operation(summary = "Aktualizuj pracownika o podanym ID")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("employeeId") Integer employeeId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("emailAddress") String emailAddress,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("country") String country) {
        Optional<Employee> employeeOptional = employeeService.updateEmployee(employeeId, firstName, lastName, emailAddress, phoneNumber, address, city, postalCode, country);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return ResponseEntity.ok(employeeService.saveEmployee(employee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete employee by ID
    @DeleteMapping("/{employeeId}")
    @Operation(summary = "Usuń pracownika o podanym ID")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("employeeId") Integer employeeId) {
        Optional<EmployeeDTO> employeeOptional = employeeService.getEmployeeByIdAsDTO(employeeId);
        if (employeeOptional.isPresent()) {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
