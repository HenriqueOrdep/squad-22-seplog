package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "requisicoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Requisicao {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_requisicao_id", nullable = false)
    private TipoRequisicao tipoRequisicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRequisicao status = StatusRequisicao.PENDENTE;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();



}



