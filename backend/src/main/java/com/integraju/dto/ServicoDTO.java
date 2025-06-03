package com.integraju.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServicoDTO {

    private Integer id;

    @NotBlank(message = "Nome do serviço é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição do serviço é obrigatória")
    private String descricao;

    @NotNull(message = "ID do setor vinculado é obrigatório")
    private Integer setorId;
}
