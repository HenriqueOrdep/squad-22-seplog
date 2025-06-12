package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.FormularioTemplateDTO;
import com.integraju.requisicao.model.FormularioTemplate;
import com.integraju.requisicao.service.FormularioTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Formulários", description = "Gerenciamento de templates e formulários vinculados às requisições.")
@RestController
@RequestMapping("/api/formularios-template")
@RequiredArgsConstructor
public class FormularioTemplateController {

    private final FormularioTemplateService formularioTemplateService;

    @Operation(summary = "Listar todos os templates", description = "Retorna todos os formulários templates.", tags = {"Formulários"})
    @ApiResponse(responseCode = "200", description = "Templates retornados com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<FormularioTemplate>> listar() {
        return ResponseEntity.ok(formularioTemplateService.listarTodos());
    }

    @Operation(summary = "Listar templates por serviço", description = "Retorna templates de formulário associados a um serviço.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/servico/{servicoId}")
    public ResponseEntity<List<FormularioTemplate>> listarPorServico(
            @Parameter(description = "ID do serviço", example = "3") @PathVariable Integer servicoId) {
        return ResponseEntity.ok(formularioTemplateService.listarPorServico(servicoId));
    }

    @Operation(summary = "Criar novo template", description = "Cria um novo template de formulário.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<FormularioTemplate> criar(
            @Parameter(description = "Dados do template a ser criado") @RequestBody @Valid FormularioTemplateDTO dto) {
        return ResponseEntity.ok(formularioTemplateService.salvar(dto));
    }

    @Operation(summary = "Atualizar template", description = "Atualiza um formulário template existente.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}")
    public ResponseEntity<FormularioTemplate> atualizar(
            @Parameter(description = "ID do template") @PathVariable Integer id,
            @RequestBody @Valid FormularioTemplateDTO dto) {
        try {
            return ResponseEntity.ok(formularioTemplateService.atualizar(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar template", description = "Remove um formulário template pelo ID.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do template") @PathVariable Integer id) {
        formularioTemplateService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
