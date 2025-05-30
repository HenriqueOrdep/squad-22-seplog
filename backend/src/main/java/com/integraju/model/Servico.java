package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "servicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servico {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;
}
