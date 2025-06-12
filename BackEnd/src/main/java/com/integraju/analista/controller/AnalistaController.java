package com.integraju.analista.controller;

import com.integraju.analista.service.AnalistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Analistas e Setores", description = "Gestão de analistas públicos e seus setores vinculados.")
@RestController
@RequestMapping("/api/analistas")
@RequiredArgsConstructor
public class AnalistaController {

    private final AnalistaService analistaService;

    @Operation(
            summary = "Associar analista a um setor",
            description = "Associa o analista identificado ao setor informado. Requer permissão de analista.",
            tags = {"Analistas e Setores"}
    )
    @ApiResponse(responseCode = "200", description = "Associação realizada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping("/{analistaId}/setores/{setorId}")
    public ResponseEntity<Void> associarSetor(
            @Parameter(description = "ID do analista", example = "1")
            @PathVariable Integer analistaId,

            @Parameter(description = "ID do setor a ser associado", example = "5")
            @PathVariable Integer setorId
    ) {
        analistaService.associarSetorAoAnalista(analistaId, setorId);
        return ResponseEntity.ok().build();
    }
}
