package com.integraju.analista.service;

import com.integraju.analista.model.Analista;
import com.integraju.analista.model.Setor;
import com.integraju.analista.repository.AnalistaRepository;
import com.integraju.analista.repository.SetorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalistaService {

    private final AnalistaRepository analistaRepository;
    private final SetorRepository setorRepository;
    private final PasswordEncoder passwordEncoder;

    public void autenticarAnalista(String email, String senha) {
        Analista analista = analistaRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Analista não encontrado"));

        if (!passwordEncoder.matches(senha, analista.getSenha())) {
            throw new BadCredentialsException("Senha inválida");
        }
    }

    @Transactional
    public void associarSetorAoAnalista(Integer analistaId, Integer setorId) {
        Analista analista = analistaRepository.findById(analistaId)
                .orElseThrow(() -> new IllegalArgumentException("Analista não encontrado com ID: " + analistaId));

        Setor setor = setorRepository.findById(setorId)
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com ID: " + setorId));

        if (!analista.getSetores().contains(setor)) {
            analista.getSetores().add(setor);
            analistaRepository.save(analista);
        }
    }
}
