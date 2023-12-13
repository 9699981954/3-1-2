package ru.itmentor.spring.boot_security.demo.service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    final RoleServiceImpl roleServiceImpl;
    final private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleServiceImpl) {
        this.userRepository = userRepository;
        this.roleServiceImpl = roleServiceImpl;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Transactional
    public User findByName(String name) {
        Optional<User> user = userRepository.findAll().stream()
                .filter(u -> Objects.equals(u.getUsername(), name))
                .findFirst();
        return user.orElse(null);
    }

    @Transactional
    public void save(User user) {
        user.setPassword((new BCryptPasswordEncoder().encode(user.getPassword())));
        user.setRole(roleServiceImpl.findById(2));
        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User updUser) {
        updUser.setId(id);
        userRepository.save(updUser);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' не найден!", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
