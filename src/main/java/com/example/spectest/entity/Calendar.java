package com.example.spectest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate globalDate;

    public Long getId() {
        return id;
    }

    public LocalDate getGlobalDate() {
        return globalDate;
    }

    public Calendar setGlobalDate(LocalDate globalDate) {
        this.globalDate = globalDate;
        return this;
    }
}
