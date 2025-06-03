package com.integraju.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormularioTemplateDTO {

    @NotNull(message = "ID do tipo de requisição é obrigatório")
    private Integer tipoRequisicaoId;

    @NotBlank(message = "Nome do campo é obrigatório")
    private String campoNome;

    @NotBlank(message = "Tipo do campo é obrigatório")
    private String campoTipo;

    private String campoOpcoes;

    private boolean obrigatorio;

    private Integer ordem;
}
