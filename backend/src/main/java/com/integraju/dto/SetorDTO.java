package com.integraju.dto;

import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetorDTO {
    private Integer id;    // Alterado de Long para Integer
    private String nome;
}
