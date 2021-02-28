package com.example.spectest.repository;

import com.example.spectest.entity.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {
    public Capital findTopByOrderByIdDesc();
}
