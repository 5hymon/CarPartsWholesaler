package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.EmployeeDTO;
import com.wholesaler.backend.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /employees/all - get all employees
    @GetMapping("/all")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployeesAsDTO();
    }

    // GET /employees/{employeeId} - get employee by ID
    @GetMapping("/{employeeId}")
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
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // PUT - update employee by ID
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") Integer employeeId, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employeeOptional = employeeService.updateEmployee(employeeId, updatedEmployee);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return ResponseEntity.ok(employeeService.saveEmployee(employee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete employee by ID
    @DeleteMapping("/{employeeId}")
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
