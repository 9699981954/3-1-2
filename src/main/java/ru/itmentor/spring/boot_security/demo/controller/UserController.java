package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;


import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String myPage (Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        model.addAttribute("simpleGrantedAuthority", new SimpleGrantedAuthority("ADMIN"));
        return "user";
    }


}