package com.example.spectest.controller;

import com.example.spectest.entity.Role;
import com.example.spectest.entity.User;
import com.example.spectest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {

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


}
