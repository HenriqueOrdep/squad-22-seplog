package com.integraju.analista.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetorDTO {

    private Integer id;

    @NotBlank(message = "Nome do setor é obrigatório")
    private String nome;
}

