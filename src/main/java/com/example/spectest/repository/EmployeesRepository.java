package com.example.spectest.repository;

import com.example.spectest.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository  extends JpaRepository<Employee, Long> {
}
