package com.example.spectest.controller;

import com.example.spectest.entity.Product;
import com.example.spectest.entity.Role;
import com.example.spectest.entity.User;
import com.example.spectest.repository.UserRepository;
import com.example.spectest.service.EconomicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private EconomicService economicService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMainPage(Map<String, Object> model) {
        model.put("prod", economicService.getProductInfo());
        model.put("ware", economicService.getWarehouseInfo());
        model.put("tran", economicService.getTransactionInfo());
        model.put("emp", economicService.getEmploeeInfo());
        model.put("users", userRepository.findAll());
        model.put("capital", economicService.getCapitalRepository().findTopByOrderByIdDesc().getTotal());
        model.put("dupon", economicService.getDuponData());
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

    @PostMapping("/prod/{product}")
    public String buyProduct(@PathVariable Product product, @RequestParam(name = "count") Long count, Map<String, Object> map) {
        economicService.addToWarehouse(product, count);
        return "redirect:/";
    }

    @PostMapping("/next")
    public String nextMonth() {
        economicService.nextMonth();
        return "redirect:/";
    }


//    @PostMapping("test/{test}")
//    public String sendTest(@PathVariable Test test, @RequestParam(name = "answer") List<String> answers, Map<String, Object> map, @AuthenticationPrincipal User user) {
//        System.out.println(Arrays.toString(answers.toArray()));
//        List<QuestionsAndAnswers> answersList = test.getQuestionsAndAnswers();
//        StringBuilder message = new StringBuilder();
//        for (int i = 0; i < answersList.size(); i++) {
//            if (!answersList.get(i).getAnswer().equals(answers.get(i))) {
//                message.append("\n").append(String.valueOf(i)).append(" - ответ неверный");
//            }
//        }
//        if (message.toString().equals("")){
//            message.append("Все ответы вреные - тест пройден");
//            user.setTestStatus("Тестирование пройдено успешно");
//            userRepository.save(user);
//        }
//        map.put("message", message.toString());
//
//        return getCurrentTest(test, map);
//    }
}
