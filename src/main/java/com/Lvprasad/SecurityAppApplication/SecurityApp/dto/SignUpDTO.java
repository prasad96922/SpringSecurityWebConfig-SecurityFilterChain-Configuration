package com.Lvprasad.SecurityAppApplication.SecurityApp.dto;


import com.Lvprasad.SecurityAppApplication.SecurityApp.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
