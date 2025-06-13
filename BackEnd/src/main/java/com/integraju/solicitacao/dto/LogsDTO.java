package com.integraju.solicitacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogsDTO {

    private Integer id;

    @NotNull(message = "O ID da solicitação é obrigatório")
    private Integer solicitacaoId;

    @NotNull(message = "O ID do analista é obrigatório")
    private Integer analistaId;

    @NotBlank(message = "O status anterior é obrigatório")
    private String statusAnterior;

    @NotBlank(message = "O novo status é obrigatório")
    private String statusNovo;

    @Size(max = 5000, message = "Comentário muito longo")
    private String comentario;
}
