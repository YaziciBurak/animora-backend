package com.animora.security.userdetails;

import com.animora.user.entity.User;
import com.animora.user.exception.UserNotFoundException;
import com.animora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmailWithRolesAndPermissions(email)
            .orElseThrow(UserNotFoundException::new);

        return new CustomUserDetails(user);
    }
}
