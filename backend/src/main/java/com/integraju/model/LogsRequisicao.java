package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "logs_requisicoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogsRequisicao {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", nullable = false)
    private Requisicao requisicao;

    @Column(name = "status_anterior")
    private String statusAnterior;

    @Column(name = "status_novo")
    private String statusNovo;

    @Column(name = "data")
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_responsavel")
    private Usuario usuarioResponsavel;

    @Column(columnDefinition = "TEXT")
    private String comentario;
}
