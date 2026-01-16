package com.animora.security.bootstrap;

import com.animora.domain.entiy.Role;
import com.animora.domain.repository.RoleRepository;
import com.animora.user.entity.User;
import com.animora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "app.bootstrap.admin.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class AdminBootstrapRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));

        if(userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = User.builder()
                .email(adminEmail)
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .createdAt(LocalDateTime.now())
                .roles(Set.of(userRole, adminRole))
                .build();

        userRepository.save(admin);
    }
}
