package com.example.spectest.entity;

import javax.persistence.*;

@Entity
public class Dupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double revenue;
    private Double actives;
    @OneToOne
    @JoinColumn(name = "capitalId_id")
    private Capital capital;
    private Double income;

    public Dupon() {
    }

    public Dupon(Double revenue, Double actives, Capital capital, Double income) {
        this.revenue = revenue;
        this.actives = actives;
        this.capital = capital;
        this.income = income;
    }

    public Long getId() {
        return id;
    }

    public Double getRevenue() {
        return revenue;
    }

    public Double getActives() {
        return actives;
    }

    public Capital getCapital() {
        return capital;
    }

    public Double getIncome() {
        return income;
    }
}
