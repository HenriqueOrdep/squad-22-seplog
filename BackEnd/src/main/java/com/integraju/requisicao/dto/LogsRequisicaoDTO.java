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
public class LogsRequisicaoDTO {

    private Integer id;

    @NotNull(message = "O ID da requisição é obrigatório")
    private Integer requisicaoId;

    @NotNull(message = "O ID do analista é obrigatório")
    private Integer analistaId;

    @NotBlank(message = "O status anterior é obrigatório")
    private String statusAnterior;

    @NotBlank(message = "O novo status é obrigatório")
    private String statusNovo;

    @Size(max = 5000, message = "Comentário muito longo")
    private String comentario;
}
