package com.example.spectest.service;

import com.example.spectest.DuponDto;
import com.example.spectest.entity.*;
import com.example.spectest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;

@Service
public class EconomicService {

    @Autowired
    protected CapitalRepository capitalRepository;

    @Autowired
    protected EmployeesRepository employeesRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Autowired
    protected CalendarRepository calendarRepository;

    @Autowired
    private DuponService duponService;

    @Autowired
    private DuponRepository duponRepository;

    public void chekCapital() {
        if (capitalRepository.findAll().size() == 0) {
            capitalRepository.save(new Capital(10000d, createStartDate()));
        }
    }

    public Employee addEmployee(String position, Double salary) {
        return employeesRepository.save(new Employee(position, salary));
    }

    public Transaction createTransaction(Double increase, Double decrease, String name) {
        return transactionRepository.save(new Transaction(increase, decrease, name, calendarRepository.findTopByOrderByIdDesc().getGlobalDate()));
    }

    public LocalDate createStartDate() {
        return calendarRepository.save(new Calendar().setGlobalDate(LocalDate.now())).getGlobalDate();
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

    public void addToWarehouse(Product product, Long count) {
        transactionRepository.save(new Transaction(0d, 0d - product.getPrice() * count, "Закупка " + product.getName(), getGlobalDate()));
        warehouseRepository.save(new Warehouse(product, count));
    }

    protected LocalDate getGlobalDate() {
        return calendarRepository.findTopByOrderByIdDesc().getGlobalDate();
    }

    public List<DuponDto> getDuponData() {
        List<Dupon> dupon = duponRepository.findByOrderByIdDesc();
        List<Dupon> dupons= new ArrayList<>();
        if (dupon.size()>=2) {
            dupons.add(dupon.get(1));
            dupons.add(dupon.get(0));
            return DuponDto.fromDuponList(dupons);
        }
        return DuponDto.fromDuponList(dupon);
    }

    private void capitalRecount() {
        sailWarehouse();
        paySalary();
        Capital capital = capitalRepository.save(new Capital(getMonthTransactions(), calendarRepository.findTopByOrderByIdDesc().getGlobalDate(), capitalRepository.findTopByOrderByIdDesc().getTotal()));
        duponRepository.save(new Dupon(duponService.getRevenue(), duponService.getActives(), capital, duponService.getCleanIncome()));
    }

    protected List<Transaction> getMonthTransactions() {
        return transactionRepository.findAll().stream()
                .filter(e -> (e.getDate().getMonthValue() == getGlobalDate().getMonthValue()) && (e.getDate().getYear() == getGlobalDate().getYear()))
                .collect(Collectors.toList());
    }

    private void paySalary() {
        transactionRepository.save(new Transaction(0d, getSalaryData(), "Зарплата", getGlobalDate()));
    }

    protected Double getSalaryData() {
        return 0-employeesRepository.findAll().stream().map(Employee::getSalary)
                .reduce(0d, Double::sum);
    }

    public CapitalRepository getCapitalRepository() {
        return capitalRepository;
    }

    private void sailWarehouse() {
        long cass = warehouseRepository.findAll().stream().filter(e -> e.getProduct().getForUse()).count();
        long empl = employeesRepository.findAll().size();
        long dif = empl;
        if (empl > cass) {
            dif = cass;
        }
        Long possible = 300 * dif;
        List<Warehouse> prods = warehouseRepository.findAll().stream().filter(e -> !e.getProduct().getForUse()).collect(Collectors.toList());
        for(Warehouse prod : prods) {
            if (possible == 0) break;
            if (possible >= prod.getCount()) {
                double plus = prod.getCount() * prod.getProduct().getPrice() * 1.5;
                double minus = 0 - (plus/100 * 13);
                transactionRepository.save(new Transaction(plus, minus, "Продажа " + prod.getProduct().getName(), getGlobalDate()));
                warehouseRepository.deleteById(prod.getId());
                possible -= prod.getCount();
            } else {
                Warehouse warehouse = warehouseRepository.findById(prod.getId()).orElse(new Warehouse());
                warehouse.setCount(warehouse.getCount() - possible);
                double plus = possible * prod.getProduct().getPrice() * 1.5;
                double minus = 0 - (plus/100 * 13);
                transactionRepository.save(new Transaction(plus, minus, "Продажа " + prod.getProduct().getName(), getGlobalDate()));
                warehouseRepository.save(warehouse);
            }
        }

    }


}
