package com.integraju.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCidadaoDTO {

    @NotBlank(message = "Login (CPF ou e-mail) é obrigatório")
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}
