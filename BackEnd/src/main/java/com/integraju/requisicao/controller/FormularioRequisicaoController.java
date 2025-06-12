package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.FormularioRequisicaoDTO;
import com.integraju.requisicao.model.FormularioRequisicao;
import com.integraju.requisicao.service.FormularioRequisicaoService;
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
@RequestMapping("/api/formularios-requisicao")
@RequiredArgsConstructor
public class FormularioRequisicaoController {

    private final FormularioRequisicaoService formularioRequisicaoService;

    @Operation(summary = "Listar todos os formulários de requisição", description = "Retorna todos os formulários preenchidos vinculados a requisições.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<FormularioRequisicao>> listarTodos() {
        return ResponseEntity.ok(formularioRequisicaoService.listarTodos());
    }

    @Operation(summary = "Listar formulários por requisição", description = "Retorna os formulários associados a uma requisição específica.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA') or hasAuthority('CIDADAO')")
    @GetMapping("/requisicao/{requisicaoId}")
    public ResponseEntity<List<FormularioRequisicao>> listarPorRequisicao(
            @Parameter(description = "ID da requisição") @PathVariable Integer requisicaoId) {
        return ResponseEntity.ok(formularioRequisicaoService.listarPorRequisicao(requisicaoId));
    }

    @Operation(summary = "Preencher formulário de requisição", description = "Permite que o cidadão preencha um formulário vinculado a uma requisição.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('CIDADAO')")
    @PostMapping
    public ResponseEntity<FormularioRequisicao> criar(
            @Parameter(description = "Dados do formulário preenchido") @RequestBody @Valid FormularioRequisicaoDTO dto) {
        return ResponseEntity.ok(formularioRequisicaoService.salvar(dto));
    }

    @Operation(summary = "Atualizar formulário preenchido", description = "Permite que o cidadão edite um formulário já preenchido.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('CIDADAO')")
    @PutMapping("/{id}")
    public ResponseEntity<FormularioRequisicao> atualizar(
            @Parameter(description = "ID do formulário") @PathVariable Integer id,
            @RequestBody @Valid FormularioRequisicaoDTO dto) {
        try {
            return ResponseEntity.ok(formularioRequisicaoService.atualizar(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar formulário preenchido", description = "Permite que o analista exclua um formulário vinculado a uma requisição.", tags = {"Formulários"})
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do formulário") @PathVariable Integer id) {
        formularioRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
