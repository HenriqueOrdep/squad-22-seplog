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
public class DevolutivaDTO {

    private Integer id;

    @NotBlank(message = "Mensagem da devolutiva é obrigatória")
    private String mensagem;

    @NotNull(message = "ID da solicitação é obrigatório")
    private Integer solicitacaoId;

    @NotNull(message = "ID do analista é obrigatório")
    private Integer analistaId;

    private LocalDateTime criadoEm;
}