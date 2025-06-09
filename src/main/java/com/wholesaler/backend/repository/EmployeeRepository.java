package com.wholesaler.backend.repository;

import com.wholesaler.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e.employeeId FROM Employee e ORDER BY 1 DESC LIMIT 1")
    Integer getLastEmployeeId();

    Optional<Employee> findByEmailAddress(String emailAddress);
}
