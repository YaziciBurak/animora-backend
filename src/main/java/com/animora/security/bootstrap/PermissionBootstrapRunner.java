package com.animora.security.bootstrap;

import com.animora.domain.entiy.Permission;
import com.animora.domain.entiy.Role;
import com.animora.domain.repository.PermissionRepository;
import com.animora.domain.repository.RoleRepository;
import com.animora.security.permission.PermissionType;
import com.animora.security.permission.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(1)
public class PermissionBootstrapRunner implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        for (PermissionType type : PermissionType.values()) {
            permissionRepository.findByName(type.name())
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder().name(type.name()).build()
                    ));
        }

        Set<Permission> userPermissions = Set.of(
                permissionRepository.findByName(PermissionType.ANIME_READ.name()).orElseThrow(),
                permissionRepository.findByName(PermissionType.COMMENT_CREATE.name()).orElseThrow(),
                permissionRepository.findByName(PermissionType.COMMENT_READ.name()).orElseThrow(),
                permissionRepository.findByName(PermissionType.COMMENT_DELETE.name()).orElseThrow(),
                permissionRepository.findByName(PermissionType.FAVORITE_ADD.name()).orElseThrow()
        );

        createRoleIfNotFound(RoleType.ROLE_USER.name(), userPermissions);

        Set<Permission> allPermissions = Set.copyOf(permissionRepository.findAll());

        createRoleIfNotFound(RoleType.ROLE_ADMIN.name(), allPermissions);
    }

    private void createRoleIfNotFound(String roleName, Set<Permission> permissions) {
        Role role = roleRepository.findByName(roleName)
                .orElse(Role.builder().name(roleName).build());

        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}
