package com.integraju.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class DevolutivaDTO {

    @NotNull(message = "Id da requisição é obrigatório")
    private Integer requisicaoId;

    @NotNull(message = "Id do usuário analista é obrigatório")
    private Integer usuarioAnalistaId;

    @NotBlank(message = "Comentário da devolutiva é obrigatório")
    private String comentario;

    @NotNull(message = "Novo status é obrigatório")
    private String novoStatus;
}
