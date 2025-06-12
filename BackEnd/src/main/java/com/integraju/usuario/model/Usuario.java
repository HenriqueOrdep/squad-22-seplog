package com.integraju.usuario.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 18)
    private String cpfOuCnpj;

    @Column(nullable = false)
    private String tipoPessoa;

    @Column(nullable = false)
    private String telefone;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String perfil;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}
