package com.example.spectest.repository;

import com.example.spectest.entity.Product;
import com.example.spectest.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
