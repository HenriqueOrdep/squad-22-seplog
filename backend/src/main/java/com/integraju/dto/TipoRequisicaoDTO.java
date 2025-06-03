package com.integraju.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipoRequisicaoDTO {

    @NotBlank(message = "Nome do tipo de requisição é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "ID do serviço vinculado é obrigatório")
    private Integer servicoId;
}
