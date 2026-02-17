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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Set<Permission> userPermissions = Stream.of(
                    PermissionType.COMMENT_CREATE,
                    PermissionType.COMMENT_DELETE_OWN,
                    PermissionType.FAVORITE_ADD,
                    PermissionType.FAVORITE_DELETE_OWN
                )
                .map(this::getPermission)
                .collect(Collectors.toSet());

        createOrUpdateRole(RoleType.ROLE_USER, userPermissions);

        Set<Permission> allPermissions = Set.copyOf(permissionRepository.findAll());

        createOrUpdateRole(RoleType.ROLE_ADMIN, allPermissions);
    }

    private void createOrUpdateRole(RoleType roleType, Set<Permission> permissions) {

        Role role = roleRepository.findByName(roleType.name())
                .orElse(Role.builder().name(roleType.name()).build());

        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    private Permission getPermission(PermissionType type) {
        return permissionRepository.findByName(type.name())
                .orElseThrow(() ->
                        new IllegalStateException("Permission initialization failed for: " + type.name()));
    }
}
