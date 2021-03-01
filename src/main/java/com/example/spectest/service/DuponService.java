package com.example.spectest.service;


import com.example.spectest.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DuponService extends EconomicService {
    public Double getActives() {
        return warehouseRepository.findAll().stream().map(e -> {
            if (e.getProduct().getForUse()) {
                return e.getCount() * e.getProduct().getPrice();
            }
            return (e.getCount() * e.getProduct().getPrice() * 1.5);
        }).reduce(0d, Double::sum);
    }

    // чистая прибыль/выручка * выручка/активы * активы/капитал

    //выручка
    public Double getRevenue() {
        return transactionRepository.findAll().stream()
                .filter(e -> (e.getDate().getMonthValue() == getGlobalDate().getMonthValue()) && (e.getDate().getYear() == getGlobalDate().getYear()))
                .map(Transaction::getIncrease)
                .reduce(0d, Double::sum);
    }

    // чистая прибыль
    public Double getCleanIncome() {
        return getSalaryData() + getMonthTransactions().stream().map(e -> e.getIncrease() + e.getDecrease()).reduce(0d, Double::sum);
    }

}
