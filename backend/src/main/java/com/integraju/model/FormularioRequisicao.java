package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "formularios_requisicao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioRequisicao {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", nullable = false)
    private Requisicao requisicao;

    @Column(name = "campo_nome", nullable = false)
    private String campoNome;

    @Column(nullable = false)
    private String valor;
}
