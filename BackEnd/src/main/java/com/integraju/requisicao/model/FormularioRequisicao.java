package com.integraju.requisicao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formularios_requisicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioRequisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", nullable = false)
    private Requisicao requisicao;

    @Column(name = "campo_nome", nullable = false, length = 100)
    private String campoNome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String valor;
}
