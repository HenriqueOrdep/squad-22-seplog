package com.integraju.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormularioRequisicaoDTO {

    @NotNull(message = "ID da requisição é obrigatório")
    private Integer requisicaoId;

    @NotBlank(message = "Nome do campo é obrigatório")
    private String campoNome;

    @NotBlank(message = "Valor do campo é obrigatório")
    private String valor;
}
