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
public class FormularioTemplateDTO {

    @NotNull(message = "ID do serviço é obrigatório")
    private Integer servicoId;

    @NotBlank(message = "Nome do campo é obrigatório")
    @Size(max = 100, message = "O nome do campo deve ter no máximo 100 caracteres")
    private String campoNome;

    @NotBlank(message = "Tipo do campo é obrigatório")
    @Size(max = 50, message = "O tipo do campo deve ter no máximo 50 caracteres")
    private String campoTipo;

    @Size(max = 5000, message = "Opções do campo excederam o limite permitido")
    private String campoOpcoes;

    private boolean obrigatorio;

    private Integer ordem;
}
