package com.integraju.solicitacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivos_solicitacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivoAnexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false)
    private String caminhoArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id", nullable = false)
    @JsonIgnore
    private Solicitacao solicitacao;

    @Column(name = "enviado_em", updatable = false)
    private LocalDateTime enviadoEm;

    @PrePersist
    public void prePersist() {
        this.enviadoEm = LocalDateTime.now();
    }
}
