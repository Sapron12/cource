package com.example.spectest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double price;
    private Boolean forUse;

    public Product() {
    }

    public Product(String name, Double price, Boolean forUse) {
        this.name = name;
        this.price = price;
        this.forUse = forUse;
    }

    public Boolean getForUse() {
        return forUse;
    }

    public Product setForUse(Boolean forUse) {
        this.forUse = forUse;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }
}
