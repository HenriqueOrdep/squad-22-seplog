package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "formularios_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioTemplate {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_requisicao_id", nullable = false)
    private TipoRequisicao tipoRequisicao;

    @Column(name = "campo_nome", nullable = false)
    private String campoNome;

    @Column(name = "campo_tipo", nullable = false)
    private String campoTipo;

    @Column(name = "campo_opcoes")
    private String campoOpcoes;

    private boolean obrigatorio;

    private Integer ordem;
}
