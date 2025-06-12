package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.RequisicaoDTO;
import com.integraju.requisicao.model.LogsRequisicao;
import com.integraju.requisicao.model.Requisicao;
import com.integraju.requisicao.model.StatusRequisicao;
import com.integraju.requisicao.service.RequisicaoService;
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

@Tag(name = "Requisições", description = "Envio, acompanhamento e gestão de solicitações públicas.")
@RestController
@RequestMapping("/api/requisicoes")
@RequiredArgsConstructor
public class RequisicaoController {

    private final RequisicaoService requisicaoService;

    @Operation(
            summary = "Criar nova requisição",
            description = "Permite que o cidadão ou analista crie uma nova requisição.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Requisição criada com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<Requisicao> criar(
            @Parameter(description = "Dados da requisição a ser criada")
            @RequestBody @Valid RequisicaoDTO dto) {
        return ResponseEntity.ok(requisicaoService.salvar(dto));
    }

    @Operation(
            summary = "Atualizar status da requisição",
            description = "Permite que o analista atualize o status de uma requisição, adicionando opcionalmente um comentário.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Requisicao> atualizarStatus(
            @Parameter(description = "ID da requisição", example = "10") @PathVariable Integer id,
            @Parameter(description = "ID do analista responsável", example = "3") @RequestParam Integer analistaId,
            @Parameter(description = "Novo status da requisição", example = "EM_ANALISE") @RequestParam StatusRequisicao novoStatus,
            @Parameter(description = "Comentário opcional") @RequestParam(required = false) String comentario) {
        return ResponseEntity.ok(requisicaoService.atualizarStatus(id, analistaId, novoStatus, comentario));
    }

    @Operation(
            summary = "Buscar requisição por ID",
            description = "Retorna os dados de uma requisição específica.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Requisição encontrada.")
    @ApiResponse(responseCode = "404", description = "Requisição não encontrada.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> buscar(
            @Parameter(description = "ID da requisição", example = "5") @PathVariable Integer id) {
        return requisicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar requisições por usuário",
            description = "Retorna todas as requisições criadas por um determinado cidadão.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Requisicao>> listarPorUsuario(
            @Parameter(description = "ID do usuário", example = "4") @PathVariable Integer usuarioId) {
        return ResponseEntity.ok(requisicaoService.listarPorUsuario(usuarioId));
    }

    @Operation(
            summary = "Listar requisições por setor",
            description = "Retorna todas as requisições vinculadas a um setor público específico.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/setor/{setorId}")
    public ResponseEntity<List<Requisicao>> listarPorSetor(
            @Parameter(description = "ID do setor", example = "2") @PathVariable Integer setorId) {
        return ResponseEntity.ok(requisicaoService.listarPorSetor(setorId));
    }

    @Operation(
            summary = "Listar requisições por status",
            description = "Retorna todas as requisições com o status informado.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/status")
    public ResponseEntity<List<Requisicao>> listarPorStatus(
            @Parameter(description = "Status da requisição", example = "EM_ANALISE") @RequestParam StatusRequisicao status) {
        return ResponseEntity.ok(requisicaoService.listarPorStatus(status));
    }

    @Operation(
            summary = "Listar logs da requisição",
            description = "Retorna o histórico de alterações (logs) de uma requisição.",
            tags = {"Requisições"}
    )
    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}/logs")
    public ResponseEntity<List<LogsRequisicao>> listarLogs(
            @Parameter(description = "ID da requisição") @PathVariable Integer id) {
        return ResponseEntity.ok(requisicaoService.listarLogsPorRequisicao(id));
    }
}
