package com.Lvprasad.SecurityAppApplication.SecurityApp.dto;


import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Permission;
import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;

    private Set<Permission> permissions;
}
