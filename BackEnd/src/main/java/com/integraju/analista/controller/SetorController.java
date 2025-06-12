package com.integraju.analista.controller;

import com.integraju.analista.dto.SetorDTO;
import com.integraju.analista.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Analistas e Setores", description = "Gestão de analistas públicos e seus setores vinculados.")
@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @Operation(summary = "Listar setores", description = "Retorna a lista de todos os setores cadastrados.", tags = {"Analistas e Setores"})
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<SetorDTO>> listarTodos() {
        return ResponseEntity.ok(setorService.listarTodos());
    }

    @Operation(summary = "Buscar setor por ID", description = "Retorna os dados de um setor a partir do ID.", tags = {"Analistas e Setores"})
    @ApiResponse(responseCode = "200", description = "Setor encontrado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(
            @Parameter(description = "ID do setor", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(setorService.buscarPorId(id));
    }

    @Operation(summary = "Criar novo setor", description = "Cadastra um novo setor no sistema.", tags = {"Analistas e Setores"})
    @ApiResponse(responseCode = "201", description = "Setor criado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<SetorDTO> criar(
            @Parameter(description = "Dados do setor a ser criado", required = true)
            @RequestBody @Valid SetorDTO dto) {
        SetorDTO criado = setorService.salvar(dto);
        URI location = URI.create("/api/setores/" + criado.getId());
        return ResponseEntity.created(location).body(criado);
    }

    @Operation(summary = "Atualizar setor", description = "Atualiza os dados de um setor existente.", tags = {"Analistas e Setores"})
    @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(
            @Parameter(description = "ID do setor", example = "1") @PathVariable Integer id,
            @Parameter(description = "Novos dados do setor") @RequestBody @Valid SetorDTO dto) {
        SetorDTO atualizado = setorService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Excluir setor", description = "Remove um setor pelo seu ID.", tags = {"Analistas e Setores"})
    @ApiResponse(responseCode = "204", description = "Setor excluído com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do setor a ser excluído", example = "1")
            @PathVariable Integer id) {
        setorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
