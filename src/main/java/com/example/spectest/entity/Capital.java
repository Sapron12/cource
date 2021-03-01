package com.example.spectest.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Capital {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private Double total;

    public Capital() {
    }

    public Capital(List<Transaction> transactions, LocalDate date, Double lastTotal) {
        this.total = transactions.stream()
                .map(e -> e.getDecrease() + e.getIncrease())
                .reduce(lastTotal, Double::sum);
        this.date = date;

    }
    public Double getTotal() {
        return total;
    }
}
