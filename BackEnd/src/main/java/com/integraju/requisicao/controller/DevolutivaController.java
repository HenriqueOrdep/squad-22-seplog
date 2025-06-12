package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.DevolutivaDTO;
import com.integraju.requisicao.service.DevolutivaService;
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
            summary = "Registrar devolutiva de uma requisição",
            description = "Permite que o analista registre uma devolutiva para uma requisição.",
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
            summary = "Listar devolutivas por requisição",
            description = "Retorna todas as devolutivas associadas a uma requisição. Disponível para cidadão ou analista.",
            tags = {"Devolutivas"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de devolutivas retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA') or hasAuthority('CIDADAO')")
    @GetMapping("/requisicao/{requisicaoId}")
    public ResponseEntity<List<DevolutivaDTO>> listarPorRequisicao(
            @Parameter(description = "ID da requisição", example = "7")
            @PathVariable Integer requisicaoId) {
        return ResponseEntity.ok(devolutivaService.listarPorRequisicao(requisicaoId));
    }
}
