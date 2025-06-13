package com.integraju.solicitacao.model;

import com.integraju.analista.model.Analista;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs_solicitacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    private Solicitacao solicitacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analista_id", nullable = true)
    private Analista analista;

    @Column(name = "status_anterior")
    private String statusAnterior;

    @Column(name = "status_novo")
    private String statusNovo;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime data;

    @PrePersist
    public void prePersist() {
        this.data = LocalDateTime.now();
    }
}
