package com.integraju.usuario.repository;

import com.integraju.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCpfOuCnpj(String cpfOuCnpj);
    Optional<Usuario> findByEmail(String email);
}
