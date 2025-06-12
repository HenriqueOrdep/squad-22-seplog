package com.integraju.extras.util;

import com.integraju.usuario.dto.UsuarioDTO;
import com.integraju.usuario.dto.UsuarioResponseDTO;
import com.integraju.usuario.model.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioConverter {

    public static Usuario toModel(UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpfOuCnpj(dto.getCpfOuCnpj())
                .tipoPessoa(dto.getTipoPessoa())
                .telefone(dto.getTelefone())
                .senha(dto.getSenha())
                .endereco(dto.getEndereco())
                .build();
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setCpfOuCnpj(usuario.getCpfOuCnpj());
        response.setTipoPessoa(usuario.getTipoPessoa());
        response.setTelefone(usuario.getTelefone());
        response.setEndereco(usuario.getEndereco());
        return response;
    }
}
