package com.integraju.solicitacao.controller;

import com.integraju.solicitacao.dto.LogsDTO;
import com.integraju.solicitacao.model.Logs;
import com.integraju.solicitacao.service.LogsService;
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

@Tag(name = "Logs de Solicitação", description = "Histórico de eventos e atualizações das solicitações públicas.")
@RestController
@RequestMapping("/api/logs-solicitacoes")
@RequiredArgsConstructor
public class LogsController {

    private final LogsService logsService;

    @Operation(
            summary = "Listar todos os logs",
            description = "Retorna todos os logs de solicitação registrados no sistema.",
            tags = {"Logs de Solicitação"}
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<Logs>> listarTodos() {
        return ResponseEntity.ok(logsService.listarTodos());
    }

    @Operation(
            summary = "Listar logs por solicitação",
            description = "Retorna os logs vinculados à solicitação especificada.",
            tags = {"Logs de Solicitação"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de logs retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA') or hasAuthority('CIDADAO')")
    @GetMapping("/solicitacao/{id}")
    public ResponseEntity<List<Logs>> listarPorSolicitacao(
            @Parameter(description = "ID da solicitação", example = "10") @PathVariable Integer id) {
        return ResponseEntity.ok(logsService.listarPorSolicitacao(id));
    }

    @Operation(
            summary = "Criar novo log",
            description = "Registra um novo log de alteração em uma solicitação.",
            tags = {"Logs de Solicitação"}
    )
    @ApiResponse(responseCode = "200", description = "Log criado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<Logs> criar(
            @Parameter(description = "Dados do novo log") @RequestBody @Valid LogsDTO dto) {
        return ResponseEntity.ok(logsService.salvar(dto));
    }

    @Operation(
            summary = "Atualizar log existente",
            description = "Atualiza um log de solicitação pelo ID.",
            tags = {"Logs de Solicitação"}
    )
    @ApiResponse(responseCode = "200", description = "Log atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}")
    public ResponseEntity<Logs> atualizar(
            @Parameter(description = "ID do log", example = "3") @PathVariable Integer id,
            @Parameter(description = "Dados atualizados") @RequestBody @Valid LogsDTO dto) {
        Logs atualizado = logsService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Deletar log",
            description = "Remove um log de solicitação pelo ID.",
            tags = {"Logs de Solicitação"}
    )
    @ApiResponse(responseCode = "204", description = "Log removido com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do log a ser excluído", example = "5") @PathVariable Integer id) {
        logsService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
