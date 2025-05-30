package com.integraju.dto;

import com.integraju.model.Endereco;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private String tipoPessoa;
    private String telefone;
    private String senha;
    private String perfil;
    private Endereco endereco;
}
