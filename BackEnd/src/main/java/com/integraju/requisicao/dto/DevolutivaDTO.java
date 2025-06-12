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
public class DevolutivaDTO {

    private Integer id;

    @NotBlank
    private String mensagem;

    @NotNull
    private Integer requisicaoId;

    @NotNull
    private Integer analistaId;

    private LocalDateTime criadoEm;
}
