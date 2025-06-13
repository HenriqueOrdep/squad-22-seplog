package com.integraju.solicitacao.controller;

import com.integraju.solicitacao.dto.ServicoDTO;
import com.integraju.solicitacao.model.Servico;
import com.integraju.solicitacao.service.ServicoService;
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

@Tag(name = "Serviços", description = "Serviços disponíveis para solicitação.")
@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @Operation(summary = "Listar todos os serviços", description = "Retorna todos os serviços ativos disponíveis no sistema.", tags = {"Serviços"})
    @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @Operation(summary = "Buscar serviço por ID", description = "Retorna os detalhes de um serviço específico pelo ID.", tags = {"Serviços"})
    @ApiResponse(responseCode = "200", description = "Serviço encontrado.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable Integer id) {
        return ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @Operation(summary = "Criar novo serviço", description = "Cadastra um novo serviço no sistema.", tags = {"Serviços"})
    @ApiResponse(responseCode = "200", description = "Serviço criado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<Servico> criar(
            @Parameter(description = "Dados do novo serviço", required = true)
            @RequestBody @Valid ServicoDTO dto) {
        return ResponseEntity.ok(servicoService.salvar(dto));
    }

    @Operation(summary = "Atualizar serviço", description = "Atualiza os dados de um serviço existente.", tags = {"Serviços"})
    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(
            @Parameter(description = "ID do serviço", example = "1") @PathVariable Integer id,
            @Parameter(description = "Novos dados do serviço", required = true)
            @RequestBody @Valid ServicoDTO dto) {
        return ResponseEntity.ok(servicoService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir serviço", description = "Remove um serviço pelo ID.", tags = {"Serviços"})
    @ApiResponse(responseCode = "204", description = "Serviço excluído com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do serviço", example = "1")
            @PathVariable Integer id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
