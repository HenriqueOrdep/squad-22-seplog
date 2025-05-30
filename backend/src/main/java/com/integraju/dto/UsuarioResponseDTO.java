package com.integraju.dto;

import com.integraju.model.Endereco;
import lombok.Data;



@Data
public class UsuarioResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private String tipoPessoa;
    private String telefone;
    private String perfil;
    private Endereco endereco;
}
