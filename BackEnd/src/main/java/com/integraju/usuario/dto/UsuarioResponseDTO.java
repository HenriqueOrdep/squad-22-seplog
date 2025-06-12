package com.integraju.usuario.dto;

import com.integraju.usuario.model.Endereco;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private String cpfOuCnpj;
    private String tipoPessoa;
    private String telefone;
    private Endereco endereco;
}
