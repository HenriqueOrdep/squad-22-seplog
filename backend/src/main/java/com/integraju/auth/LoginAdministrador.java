package com.integraju.auth;

import com.integraju.model.PerfilAdmin;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginAdministrador {
    private String email;
    private String senha;
    private PerfilAdmin tipo;
}
