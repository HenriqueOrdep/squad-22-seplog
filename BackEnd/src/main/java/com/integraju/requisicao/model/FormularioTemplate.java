package com.integraju.requisicao.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formularios_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(name = "campo_nome", nullable = false, length = 100)
    private String campoNome;

    @Column(name = "campo_tipo", nullable = false, length = 50)
    private String campoTipo;

    @Column(name = "campo_opcoes", columnDefinition = "TEXT")
    private String campoOpcoes;

    @Column(nullable = false)
    private boolean obrigatorio;

    private Integer ordem;

    @Override
    public String toString() {
        return "FormularioTemplate{" +
                "id=" + id +
                ", campoNome='" + campoNome + '\'' +
                ", campoTipo='" + campoTipo + '\'' +
                ", obrigatorio=" + obrigatorio +
                ", ordem=" + ordem +
                '}';
    }
}
