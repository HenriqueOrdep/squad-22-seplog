package com.integraju.dto;

import com.integraju.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotBlank(message = "CPF ou CNPJ é obrigatório")
    private String cpfOuCnpj;

    @NotBlank(message = "Tipo de pessoa é obrigatório")
    private String tipoPessoa;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @Valid
    private Endereco endereco;
}
