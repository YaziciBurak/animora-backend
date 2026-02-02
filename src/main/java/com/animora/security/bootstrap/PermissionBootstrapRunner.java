package com.animora.security.bootstrap;

import com.animora.domain.entiy.Permission;
import com.animora.domain.entiy.Role;
import com.animora.domain.repository.PermissionRepository;
import com.animora.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionBootstrapRunner implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        Permission animeCreate = createPermission("ANIME_CREATE");
        Permission animeRead = createPermission("ANIME_READ");
        Permission animeUpdate = createPermission("ANIME_UPDATE");
        Permission animeDelete = createPermission("ANIME_DELETE");

        createRoleIfNotFound("ROLE_USER", Set.of(animeRead));

        createRoleIfNotFound("ROLE_ADMIN", Set.of(
                animeCreate,
                animeRead,
                animeUpdate,
                animeDelete
        ));
    }

    private Permission createPermission(String name) {
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(Permission.builder().name(name).build()));
    }

    private void createRoleIfNotFound(String roleName, Set<Permission> permissions) {
        Role role = roleRepository.findByName(roleName)
                .orElse(Role.builder().name(roleName).build());

        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}
