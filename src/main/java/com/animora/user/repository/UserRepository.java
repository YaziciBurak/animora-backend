package com.animora.user.repository;

import com.animora.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("""
SELECT DISTINCT u FROM User u
JOIN FETCH u.roles r
JOIN FETCH r.permissions
WHERE u.email = :email
""")
    Optional<User> findByEmailWithRolesAndPermissions(@Param("email") String email);
}
