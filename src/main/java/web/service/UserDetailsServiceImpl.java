package web.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.entity.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;



    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        if (login.equals("ADMIN")) {
        User user = new User("root","root", 3, "root@root.com", new BCryptPasswordEncoder().encode("root"));
            if (userRepository.findByLogin("ADMIN").isEmpty()) {
                user.setRoles(new HashSet<>(roleRepository.findAll()));
                userRepository.save(user);
            }
            return user;
        }
        return userRepository.findByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("user " + login + " was not found"));
    }
}