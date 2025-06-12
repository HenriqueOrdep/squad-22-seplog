package com.integraju.analista.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "analistas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Analista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(nullable = false)
    @JsonIgnore
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "analista_setor",
            joinColumns = @JoinColumn(name = "analista_id"),
            inverseJoinColumns = @JoinColumn(name = "setor_id")
    )
    private List<Setor> setores;
}
