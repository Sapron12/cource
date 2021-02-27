package com.example.spectest.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<QuestionsAndAnswers> questionsAndAnswers;

    public Test(String name) {
        this.name = name;
    }

    public Test() {

    }

    public List<QuestionsAndAnswers> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public Test setQuestionsAndAnswers(List<QuestionsAndAnswers> questionsAndAnswers) {
        this.questionsAndAnswers = questionsAndAnswers;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
