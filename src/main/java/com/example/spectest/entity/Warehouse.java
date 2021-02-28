package com.example.spectest.entity;

import javax.persistence.*;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Long count;

    public Warehouse() {
    }

    public Warehouse(Product product, Long count) {
        this.product = product;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Warehouse setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public Warehouse setCount(Long count) {
        this.count = count;
        return this;
    }
}
