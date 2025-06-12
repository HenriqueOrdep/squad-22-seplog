package com.integraju.usuario.service;

import com.integraju.usuario.dto.UsuarioDTO;
import com.integraju.usuario.dto.UsuarioResponseDTO;
import com.integraju.usuario.model.Usuario;
import com.integraju.usuario.repository.UsuarioRepository;
import com.integraju.extras.util.UsuarioConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        usuario.setPerfil("CIDADAO");
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

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return UsuarioConverter.toResponseDTO(usuarioExistente);
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public void autenticarCidadao(String login, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(login)
                .or(() -> usuarioRepository.findByCpfOuCnpj(login))
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Optional<Usuario> buscarEntidadePorId(Integer id) {
        return usuarioRepository.findById(id);
    }
}
