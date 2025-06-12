package com.integraju.requisicao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioRequisicaoDTO {

    @NotNull(message = "ID da requisição é obrigatório")
    private Integer requisicaoId;

    @NotBlank(message = "Nome do campo é obrigatório")
    @Size(max = 100, message = "Nome do campo deve ter no máximo 100 caracteres")
    private String campoNome;

    @NotBlank(message = "Valor do campo é obrigatório")
    @Size(max = 5000, message = "Valor do campo excede o limite permitido")
    private String valor;
}
