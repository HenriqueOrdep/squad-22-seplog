package com.integraju.solicitacao.controller;

import com.integraju.solicitacao.dto.SolicitacaoDTO;
import com.integraju.solicitacao.model.Logs;
import com.integraju.solicitacao.model.Solicitacao;
import com.integraju.solicitacao.model.StatusRequisicao;
import com.integraju.solicitacao.service.SolicitacaoService;
import com.integraju.security.JwtService;
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

@Tag(name = "Solicitações", description = "Envio, acompanhamento e gestão de solicitações públicas.")
@RestController
@RequestMapping("/api/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final JwtService jwtService;

    @Operation(summary = "Criar nova solicitação", description = "Permite que o cidadão crie uma nova solicitação.")
    @ApiResponse(responseCode = "200", description = "Solicitação criada com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO')")
    @PostMapping
    public ResponseEntity<Solicitacao> criar(
            @RequestBody @Valid SolicitacaoDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String cpf = jwtService.extractUsername(token);

        return ResponseEntity.ok(solicitacaoService.salvar(dto, cpf));
    }

    @Operation(summary = "Atualizar status da solicitação", description = "Permite que o analista atualize o status de uma solicitação, adicionando um comentário.")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}/status")
    public ResponseEntity<Solicitacao> atualizarStatus(
            @PathVariable Integer id,
            @RequestParam Integer analistaId,
            @RequestParam StatusRequisicao novoStatus,
            @RequestParam(required = false) String comentario) {

        return ResponseEntity.ok(solicitacaoService.atualizarStatus(id, analistaId, novoStatus, comentario));
    }

    @Operation(summary = "Buscar solicitação por ID", description = "Retorna os dados de uma solicitação específica.")
    @ApiResponse(responseCode = "200", description = "Solicitação encontrada.")
    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscar(@PathVariable Integer id) {
        return solicitacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar solicitações por usuário", description = "Retorna todas as solicitações de um cidadão.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Solicitacao>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(solicitacaoService.listarPorUsuario(usuarioId));
    }

    @Operation(summary = "Listar solicitações por setor", description = "Retorna todas as solicitações vinculadas a um setor.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/setor/{setorId}")
    public ResponseEntity<List<Solicitacao>> listarPorSetor(@PathVariable Integer setorId) {
        return ResponseEntity.ok(solicitacaoService.listarPorSetor(setorId));
    }

    @Operation(summary = "Listar solicitações por status", description = "Retorna todas as solicitações com o status informado.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/status")
    public ResponseEntity<List<Solicitacao>> listarPorStatus(@RequestParam StatusRequisicao status) {
        return ResponseEntity.ok(solicitacaoService.listarPorStatus(status));
    }

    @Operation(summary = "Listar logs da solicitação", description = "Retorna o histórico de alterações (logs) de uma solicitação.")
    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}/logs")
    public ResponseEntity<List<Logs>> listarLogs(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitacaoService.listarLogsPorSolicitacao(id));
    }
}
