package com.integraju.service;

import com.integraju.dto.UsuarioDTO;
import com.integraju.dto.UsuarioResponseDTO;
import com.integraju.model.Usuario;
import com.integraju.repository.UsuarioRepository;
import com.integraju.util.UsuarioConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioConverter::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .map(UsuarioConverter::toResponseDTO);
    }

    public UsuarioResponseDTO salvar(UsuarioDTO dto) {
        Usuario usuario = UsuarioConverter.toModel(dto);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        // Valor padrão para perfil, se não for informado
        if (dto.getPerfil() == null || dto.getPerfil().isBlank()) {
            usuario.setPerfil("SOLICITANTE");
        } else {
            usuario.setPerfil(dto.getPerfil().toUpperCase());
        }

        usuario = usuarioRepository.save(usuario);
        return UsuarioConverter.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizar(Integer id, UsuarioDTO dto) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) {
            return null;
        }

        Usuario usuarioExistente = optUsuario.get();
        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setCpfOuCnpj(dto.getCpfOuCnpj());
        usuarioExistente.setTipoPessoa(dto.getTipoPessoa());
        usuarioExistente.setTelefone(dto.getTelefone());
        usuarioExistente.setEndereco(dto.getEndereco());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        if (dto.getPerfil() != null && !dto.getPerfil().isBlank()) {
            usuarioExistente.setPerfil(dto.getPerfil().toUpperCase());
        }

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return UsuarioConverter.toResponseDTO(usuarioExistente);
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
