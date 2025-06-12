package com.integraju.requisicao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivoRequisicaoDTO {

    private Integer id;

    @NotBlank
    private String nomeArquivo;

    @NotBlank
    private String caminhoArquivo;

    @NotNull
    private Integer requisicaoId;

    private LocalDateTime enviadoEm;
}
