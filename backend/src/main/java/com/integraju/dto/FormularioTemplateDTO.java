package com.integraju.dto;

import lombok.Data;



@Data
public class FormularioTemplateDTO {
    private Integer tipoRequisicaoId;
    private String campoNome;
    private String campoTipo;
    private String campoOpcoes;
    private boolean obrigatorio;
    private Integer ordem;
}
