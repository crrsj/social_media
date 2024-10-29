package com.api.sm.config;

import com.api.sm.entity.Role;
import com.api.sm.entity.User;
import com.api.sm.repository.RoleRepository;
import com.api.sm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Value.ADMIN.name());
        var userAdmin = userRepository.findByUsername("admin");
        userAdmin.ifPresentOrElse(
          user -> {
                  System.out.println();

        },
        () -> {
            var user = new User();
            user.setUsername("admin");
            user.setPassword(bCryptPasswordEncoder.encode("123"));
            user.setRoles(Set.of(roleAdmin));
            userRepository.save(user);
        }
        );

    }
}



