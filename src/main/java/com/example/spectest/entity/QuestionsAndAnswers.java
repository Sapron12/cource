package com.example.spectest.entity;

import javax.persistence.*;

@Entity
public class QuestionsAndAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;
    private String answer;
//    @ManyToOne
//    @JoinColumn(name = "test_id")
//    private Test test;

    public QuestionsAndAnswers(String question, String answer) {
        this.question = question;
        this.answer = answer;
//        this.test = test;
    }

    public QuestionsAndAnswers() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }


    public String getAnswer() {
        return answer;
    }

    public QuestionsAndAnswers setQuestion(String question) {
        this.question = question;
        return this;
    }

    public QuestionsAndAnswers setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

}
