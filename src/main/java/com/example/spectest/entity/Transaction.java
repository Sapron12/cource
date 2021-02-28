package com.example.spectest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double increase;
    private Double decrease;
    private LocalDate date;
    private String name;

    public Transaction() {
    }

    public Transaction(Double increase, Double decrease, String name, LocalDate date) {
        this.increase = increase;
        this.decrease = decrease;
        this.date = date;
        this.name = name;
    }

    private String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Double getIncrease() {
        return increase;
    }

    public Double getDecrease() {
        return decrease;
    }

    public LocalDate getDate() {
        return date;
    }

    public Transaction setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
