package com.integraju.dto;

import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;


@Data
public class RequisicaoDTO {

    @NotNull(message = "Id do usuário é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "Id do tipo de requisição é obrigatório")
    private Integer tipoRequisicaoId;

    @Valid
    private List<FormularioRequisicaoDTO> formulario;
}
