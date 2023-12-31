package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.orElse(null);
    }

    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void update(int id, Role updRole) {
        updRole.setId(id);
        roleRepository.save(updRole);
    }

    @Transactional
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

}