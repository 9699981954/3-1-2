package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;

public class AdminController {
    private final UserServiceImpl userServiceImpl;
    private final RoleServiceImpl roleServiceImpl;


    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userServiceImpl.findAll());
        return "admin/index";
    }

    @GetMapping("/show")
    public String show(@RequestParam(value = "id") int id, Model model) {
        model.addAttribute("user", userServiceImpl.findById(id));
        return "admin/show";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userServiceImpl.findById(id));
//        return "admin/show";
//    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest) {
        if (bindingRequest.hasErrors()) return "admin/new";
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") int id) {
        model.addAttribute("user", userServiceImpl.findById(id));
        return "admin/edit.html";
    }

//    @GetMapping("/{id}/edit.html")
//    public String edit.html(Model model, @PathVariable("id") int id) {
//        model.addAttribute("user", userServiceImpl.findById(id));
//        return "admin/edit.html";
//    }


    @PatchMapping("/")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest,
                         @RequestParam(value = "id") int id) {
        if (bindingRequest.hasErrors()) return "admin/edit.html";
        userServiceImpl.update(id, user);
        return "redirect:/admin";
    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingRequest,
//                         @PathVariable("id") int id) {
//        if (bindingRequest.hasErrors()) return "admin/edit.html";
//        userServiceImpl.update(id, user);
//        return "redirect:/admin";
//    }


    @DeleteMapping("/")
    public String delete(@RequestParam("id") int id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id") int id) {
//        userServiceImpl.delete(id);
//        return "redirect:/admin";
//    }


}
