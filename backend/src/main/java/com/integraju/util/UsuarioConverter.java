package com.integraju.util;

import com.integraju.dto.UsuarioDTO;
import com.integraju.dto.UsuarioResponseDTO;
import com.integraju.model.Usuario;
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
                .perfil(dto.getPerfil())
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
        response.setPerfil(usuario.getPerfil());
        response.setEndereco(usuario.getEndereco());
        return response;
    }
}
