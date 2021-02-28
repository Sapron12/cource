package com.example.spectest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String position;
    private Long salary;

    public Employee() {
    }

    public Employee(String position, Long salary) {
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    public Long getSalary() {
        return salary;
    }

    public Employee setSalary(Long salary) {
        this.salary = salary;
        return this;
    }
}
