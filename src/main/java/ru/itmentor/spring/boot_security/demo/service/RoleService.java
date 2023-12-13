package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;



public interface RoleService {
    List<Role> findAll();

    Role findById(int id);

    void save(Role role);

    void update(int id, Role updRole);

    void delete(int id);
}
