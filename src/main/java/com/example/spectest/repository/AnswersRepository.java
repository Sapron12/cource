package com.example.spectest.repository;

import com.example.spectest.entity.QuestionsAndAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswersRepository extends JpaRepository<QuestionsAndAnswers, Long> {
}
