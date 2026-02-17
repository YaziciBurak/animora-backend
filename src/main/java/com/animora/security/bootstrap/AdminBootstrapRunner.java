package com.animora.security.bootstrap;

import com.animora.domain.entiy.Role;
import com.animora.domain.repository.RoleRepository;
import com.animora.security.permission.RoleType;
import com.animora.user.entity.User;
import com.animora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
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
@Order(2)
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

        Role adminRole = getRequiredRole();

        if(userRepository.existsByEmail(adminEmail)) {
            return;
        }

        User admin = User.builder()
                .email(adminEmail)
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .createdAt(LocalDateTime.now())
                .roles(Set.of(adminRole))
                .build();

        userRepository.save(admin);
    }

    private Role getRequiredRole() {
        return roleRepository.findByName(RoleType.ROLE_ADMIN.name())
                .orElseThrow(() ->
                        new IllegalStateException(
                                RoleType.ROLE_ADMIN.name() + " not initialized during bootstrap phase"
                        ));
    }
}
