package com.integraju.requisicao.controller;

import com.integraju.requisicao.dto.ArquivoRequisicaoDTO;
import com.integraju.requisicao.service.ArquivoRequisicaoService;
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

@Tag(name = "Arquivos", description = "Gerenciamento de arquivos anexados às solicitações públicas.")
@RestController
@RequestMapping("/api/arquivos")
@RequiredArgsConstructor
public class ArquivoRequisicaoController {

    private final ArquivoRequisicaoService arquivoService;

    @Operation(
            summary = "Anexar arquivo à requisição",
            description = "Permite que um cidadão ou analista envie um arquivo vinculado a uma requisição.",
            tags = {"Arquivos"}
    )
    @ApiResponse(responseCode = "200", description = "Arquivo salvo com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<ArquivoRequisicaoDTO> salvar(
            @Parameter(description = "Dados do arquivo a ser salvo") @RequestBody @Valid ArquivoRequisicaoDTO dto) {
        return ResponseEntity.ok(arquivoService.salvar(dto));
    }

    @Operation(
            summary = "Listar arquivos de uma requisição",
            description = "Retorna todos os arquivos anexados à requisição informada.",
            tags = {"Arquivos"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de arquivos retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/requisicao/{requisicaoId}")
    public ResponseEntity<List<ArquivoRequisicaoDTO>> listarPorRequisicao(
            @Parameter(description = "ID da requisição", example = "7") @PathVariable Integer requisicaoId) {
        return ResponseEntity.ok(arquivoService.listarPorRequisicao(requisicaoId));
    }
}
