package com.example.spectest.repository;

import com.example.spectest.entity.Dupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuponRepository extends JpaRepository<Dupon, Long> {
    public List<Dupon> findByOrderByIdDesc();
}
