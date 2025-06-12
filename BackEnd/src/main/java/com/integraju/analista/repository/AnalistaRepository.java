package com.integraju.analista.repository;

import com.integraju.analista.model.Analista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalistaRepository extends JpaRepository<Analista, Integer> {
    Optional<Analista> findByEmail(String email);
}
