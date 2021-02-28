package com.example.spectest.repository;

import com.example.spectest.entity.Transaction;
import com.example.spectest.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByDateBetween(LocalDate first, LocalDate second);
}
