package com.example.spectest.repository;

import com.example.spectest.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    public Calendar findTopByOrderByIdDesc();
}
