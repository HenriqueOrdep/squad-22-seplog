package com.integraju.service;

import com.integraju.model.Administrador;
import com.integraju.model.PerfilAdmin;
import com.integraju.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public String autenticarAdministrador(String login, String senha) {
        Administrador admin = administradorRepository.findByEmail(login)
                .orElseThrow(() -> new BadCredentialsException("Administrador não encontrado"));

        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        return admin.getPerfil().name();
    }

    public void autenticarAdministradorPorPerfil(String email, String senha, PerfilAdmin tipo) {
        Administrador admin = administradorRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Administrador não encontrado"));

        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }

        if (!admin.getPerfil().equals(tipo)) {
            throw new BadCredentialsException("Perfil incompatível");
        }
    }
}
