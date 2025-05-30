package com.integraju.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "setores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setor {

    @Id
    @GeneratedValue(generator = "Integer")
    @Column(columnDefinition = "Integer", updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nome;
}
