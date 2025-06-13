package com.integraju.solicitacao.controller;

import com.integraju.solicitacao.dto.DevolutivaDTO;
import com.integraju.solicitacao.service.DevolutivaService;
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

@Tag(name = "Devolutivas", description = "Respostas e finalização de solicitações.")
@RestController
@RequestMapping("/api/devolutivas")
@RequiredArgsConstructor
public class DevolutivaController {

    private final DevolutivaService devolutivaService;

    @Operation(
            summary = "Registrar devolutiva de uma solicitação",
            description = "Permite que o analista registre uma devolutiva para uma solicitação.",
            tags = {"Devolutivas"}
    )
    @ApiResponse(responseCode = "200", description = "Devolutiva registrada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<DevolutivaDTO> salvar(
            @Parameter(description = "Dados da devolutiva", required = true)
            @RequestBody @Valid DevolutivaDTO dto) {
        return ResponseEntity.ok(devolutivaService.salvar(dto));
    }

    @Operation(
            summary = "Listar devolutivas por solicitação",
            description = "Retorna todas as devolutivas associadas a uma solicitação. Disponível para cidadão ou analista.",
            tags = {"Devolutivas"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de devolutivas retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA') or hasAuthority('CIDADAO')")
    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<DevolutivaDTO>> listarPorSolicitacao(
            @Parameter(description = "ID da solicitação", example = "7")
            @PathVariable Integer solicitacaoId) {
        return ResponseEntity.ok(devolutivaService.listarPorSolicitacao(solicitacaoId));
    }
}