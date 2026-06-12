package com.Lvprasad.SecurityAppApplication.SecurityApp.utils;

import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Permission;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Permission.*;
import static com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role.*;

public class PermissionMappings {

    private static final Map<Role, Set<Permission>> map = Map.of(

            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(USER_VIEW, POST_VIEW,USER_UPDATE,POST_UPDATE),
            ADMIN, Set.of(USER_VIEW, POST_VIEW,USER_UPDATE,POST_UPDATE, USER_DELETE, POST_DELETE, USER_CREATE)

    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map.get(role).stream().map(
                permission -> new SimpleGrantedAuthority(permission.name())

        ).collect(Collectors.toSet());
    }

}
