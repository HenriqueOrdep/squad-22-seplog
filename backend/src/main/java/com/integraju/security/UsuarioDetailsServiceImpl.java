package com.integraju.security;

import com.integraju.model.Usuario;
import com.integraju.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String cpfOuCnpj) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCpfOuCnpj(cpfOuCnpj)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com CPF/CNPJ: " + cpfOuCnpj));

        return User.builder()
                .username(usuario.getCpfOuCnpj())
                .password(usuario.getSenha())
                .authorities(Collections.emptyList())
                .build();
    }
}
