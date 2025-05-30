package com.integraju.dto;

import lombok.Data;



@Data
public class LogsRequisicaoDTO {
    private Integer requisicaoId;
    private String statusAnterior;
    private String statusNovo;
    private Integer usuarioResponsavel;
}
