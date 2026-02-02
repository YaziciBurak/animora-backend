package com.animora.security.auth;

import com.animora.domain.entiy.Role;
import com.animora.domain.repository.RoleRepository;
import com.animora.security.auth.dto.request.LoginRequest;
import com.animora.security.auth.dto.request.RegisterRequest;
import com.animora.security.auth.dto.response.LoginResponse;
import com.animora.security.auth.dto.response.RegisterResponse;
import com.animora.security.jwt.JwtService;
import com.animora.user.entity.User;
import com.animora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmailWithRolesAndPermissions(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already in use");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        User savedUser = userRepository.findByEmailWithRolesAndPermissions(user.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(savedUser);

        return new RegisterResponse(token);
     }
}
