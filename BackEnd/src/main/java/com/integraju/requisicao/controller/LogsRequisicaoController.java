package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.LogsRequisicaoDTO;
import com.integraju.requisicao.model.LogsRequisicao;
import com.integraju.requisicao.service.LogsRequisicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Logs de Requisição", description = "Histórico de eventos e atualizações das solicitações públicas.")
@RestController
@RequestMapping("/api/logs-requisicoes")
@RequiredArgsConstructor
public class LogsRequisicaoController {

    private final LogsRequisicaoService logsRequisicaoService;

    @Operation(
            summary = "Listar todos os logs",
            description = "Retorna todos os logs de requisição registrados no sistema.",
            tags = {"Logs de Requisição"}
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<LogsRequisicao>> listarTodos() {
        return ResponseEntity.ok(logsRequisicaoService.listarTodos());
    }

    @Operation(
            summary = "Listar logs por requisição",
            description = "Retorna os logs vinculados à requisição especificada.",
            tags = {"Logs de Requisição"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA') or hasAuthority('CIDADAO')")
    @GetMapping("/requisicao/{id}")
    public ResponseEntity<List<LogsRequisicao>> listarPorRequisicao(
            @Parameter(description = "ID da requisição", example = "10") @PathVariable Integer id) {
        return ResponseEntity.ok(logsRequisicaoService.listarPorRequisicao(id));
    }

    @Operation(
            summary = "Criar novo log",
            description = "Registra um novo log de alteração em uma requisição.",
            tags = {"Logs de Requisição"}
    )
    @ApiResponse(responseCode = "200", description = "Log criado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<LogsRequisicao> criar(
            @Parameter(description = "Dados do novo log") @RequestBody @Valid LogsRequisicaoDTO dto) {
        return ResponseEntity.ok(logsRequisicaoService.salvar(dto));
    }

    @Operation(
            summary = "Atualizar log existente",
            description = "Atualiza um log de requisição pelo ID.",
            tags = {"Logs de Requisição"}
    )
    @ApiResponse(responseCode = "200", description = "Log atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}")
    public ResponseEntity<LogsRequisicao> atualizar(
            @Parameter(description = "ID do log", example = "3") @PathVariable Integer id,
            @Parameter(description = "Dados atualizados") @RequestBody @Valid LogsRequisicaoDTO dto) {
        LogsRequisicao atualizado = logsRequisicaoService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Deletar log",
            description = "Remove um log de requisição pelo ID.",
            tags = {"Logs de Requisição"}
    )
    @ApiResponse(responseCode = "204", description = "Log removido com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do log a ser excluído", example = "5") @PathVariable Integer id) {
        logsRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
