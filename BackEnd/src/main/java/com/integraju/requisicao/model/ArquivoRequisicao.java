package com.integraju.requisicao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivos_requisicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivoRequisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nomeArquivo;

    @Column(nullable = false)
    private String caminhoArquivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisicao_id", nullable = false)
    @JsonIgnore
    private Requisicao requisicao;

    @Column(name = "enviado_em", updatable = false)
    private LocalDateTime enviadoEm;

    @PrePersist
    public void prePersist() {
        this.enviadoEm = LocalDateTime.now();
    }
}
