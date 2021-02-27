package com.example.spectest.controller;

import com.example.spectest.entity.QuestionsAndAnswers;
import com.example.spectest.entity.Role;
import com.example.spectest.entity.Test;
import com.example.spectest.entity.User;
import com.example.spectest.repository.AnswersRepository;
import com.example.spectest.repository.TestRepository;
import com.example.spectest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

@Controller
public class MainController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMainPage(Map<String, Object> model) {

        model.put("users", userRepository.findAll());
        return "Main";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Map<String, Object> model) {


        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Map<String, Object> model) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.save(user);
            return "redirect:/login";
        }
        model.put("error", "Пользователь с таким именем существует");
        return "registration";
    }

    @GetMapping("/createUser")
    public String createUser() {
        User user = new User("username", "1234");
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        if (userRepository.findByUsername("username") == null) {
            userRepository.save(user);
        }
        return "redirect:/";
    }

    @GetMapping("/test")
    public String getTestPage(
            @AuthenticationPrincipal User user,
            Map<String, Object> map
    ) {

        List<Test> tests = testRepository.findAll();
        map.put("tests", tests);
        System.out.print(user);
        return "test";
    }

    @GetMapping("/createTest")
    public String createTest() {

        testRepository.save(new Test("Hard").setQuestionsAndAnswers(
                of(answersRepository.save(
                        new QuestionsAndAnswers("1 + 1", "2")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("4 + 1", "5")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("2 + 1", "3")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("1 + 41", "42")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("5 + 1", "6")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("41 + 2", "43")
                        ),
                        answersRepository.save(
                                new QuestionsAndAnswers("16 + 1", "17")
                        )

                ).collect(Collectors.toList()))
        );

        return "redirect:/test";
    }

    @GetMapping("test/{test}")
    public String getCurrentTest(@PathVariable Test test, Map<String, Object> map) {
        map.put("test", test);
        return "currentTest";
    }

    @PostMapping("test/{test}")
    public String sendTest(@PathVariable Test test, @RequestParam(name = "answer") List<String> answers, Map<String, Object> map, @AuthenticationPrincipal User user) {
        System.out.println(Arrays.toString(answers.toArray()));
        List<QuestionsAndAnswers> answersList = test.getQuestionsAndAnswers();
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < answersList.size(); i++) {
            if (!answersList.get(i).getAnswer().equals(answers.get(i))) {
                message.append("\n").append(String.valueOf(i)).append(" - ответ неверный");
            }
        }
        if (message.toString().equals("")){
            message.append("Все ответы вреные - тест пройден");
            user.setTestStatus("Тестирование пройдено успешно");
            userRepository.save(user);
        }
        map.put("message", message.toString());

        return getCurrentTest(test, map);
    }

}
