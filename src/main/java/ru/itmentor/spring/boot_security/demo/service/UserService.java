package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService {

    List<User> findAll();

    User findById(int id);

    User findByName(String name);

    void save(User user);

    void update(int id, User updUser);

    void delete(int id);
}