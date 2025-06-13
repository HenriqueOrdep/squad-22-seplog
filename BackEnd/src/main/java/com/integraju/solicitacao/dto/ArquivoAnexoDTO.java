package com.integraju.solicitacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivoAnexoDTO {

    private Integer id;

    @NotBlank(message = "Nome do arquivo é obrigatório")
    private String nomeArquivo;

    @NotBlank(message = "Caminho do arquivo é obrigatório")
    private String caminhoArquivo;

    @NotNull(message = "ID da solicitação é obrigatório")
    private Integer solicitacaoId;

    private LocalDateTime enviadoEm;
}
