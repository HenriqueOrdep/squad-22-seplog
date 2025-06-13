package com.integraju.solicitacao.controller;

import com.integraju.solicitacao.dto.ArquivoAnexoDTO;
import com.integraju.solicitacao.service.ArquivoAnexoService;
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
public class ArquivoAnexoController {

    private final ArquivoAnexoService arquivoService;

    @Operation(
            summary = "Anexar arquivo à solicitação",
            description = "Permite que um cidadão ou analista envie um arquivo vinculado a uma solicitação.",
            tags = {"Arquivos"}
    )
    @ApiResponse(responseCode = "200", description = "Arquivo salvo com sucesso.")
    @PreAuthorize("hasAuthority('CIDADAO') or hasAuthority('ANALISTA')")
    @PostMapping
    public ResponseEntity<ArquivoAnexoDTO> salvar(
            @Parameter(description = "Dados do arquivo a ser salvo") @RequestBody @Valid ArquivoAnexoDTO dto) {
        return ResponseEntity.ok(arquivoService.salvar(dto));
    }

    @Operation(
            summary = "Listar arquivos de uma solicitação",
            description = "Retorna todos os arquivos anexados à solicitação informada.",
            tags = {"Arquivos"}
    )
    @ApiResponse(responseCode = "200", description = "Lista de arquivos retornada com sucesso.")
    @PreAuthorize("hasAuthority('ANALISTA')")
    @GetMapping("/solicitacao/{solicitacaoId}")
    public ResponseEntity<List<ArquivoAnexoDTO>> listarPorSolicitacao(
            @Parameter(description = "ID da solicitação", example = "7") @PathVariable Integer solicitacaoId) {
        return ResponseEntity.ok(arquivoService.listarPorSolicitacao(solicitacaoId));
    }
}
