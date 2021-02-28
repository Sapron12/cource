package com.example.spectest.service;

import com.example.spectest.entity.*;
import com.example.spectest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EconomicService {

    @Autowired
    private CapitalRepository capitalRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    public Employee addEmployee(String position, Long salary) {
        return employeesRepository.save(new Employee(position, salary));
    }

    public Transaction createTransaction(Double increase, Double decrease, String name) {
        return transactionRepository.save(new Transaction(increase, decrease, name, calendarRepository.findTopByOrderByIdDesc().getGlobalDate()));
    }

    public void createStartDate() {
        calendarRepository.save(new Calendar().setGlobalDate(LocalDate.now()));
    }

    public void nextMonth() {
        LocalDate lastDate = calendarRepository.findTopByOrderByIdDesc().getGlobalDate();
        capitalRecount();
        calendarRepository.save(new Calendar().setGlobalDate(lastDate.plusMonths(1)));
    }

    public List<Capital> getCapitalInfo() {
        return capitalRepository.findAll();
    }

    public List<Employee> getEmploeeInfo() {
        return employeesRepository.findAll();
    }

    public List<Transaction> getTransactionInfo() {
        return transactionRepository.findAll();
    }

    public List<Calendar> getCalendarInfo() {
        return calendarRepository.findAll();
    }

    public List<Warehouse> getWarehouseInfo() {
        return warehouseRepository.findAll();
    }

    public List<Product> getProductInfo() {
        return productRepository.findAll();
    }

    private void capitalRecount() {
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(e -> e.getDate().getMonthValue() == calendarRepository.findTopByOrderByIdDesc().getGlobalDate().getMonthValue())
                .collect(Collectors.toList());

        capitalRepository.save(new Capital(transactions, calendarRepository.findTopByOrderByIdDesc().getGlobalDate(), capitalRepository.findTopByOrderByIdDesc().getTotal()));
    }
}
