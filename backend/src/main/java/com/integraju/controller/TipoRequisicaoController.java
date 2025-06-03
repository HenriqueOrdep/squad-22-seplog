package com.integraju.controller;

import com.integraju.dto.TipoRequisicaoDTO;
import com.integraju.model.TipoRequisicao;
import com.integraju.service.TipoRequisicaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tipos-requisicao")
@RequiredArgsConstructor
public class TipoRequisicaoController {

    private final TipoRequisicaoService tipoRequisicaoService;

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<TipoRequisicao>> listar() {
        return ResponseEntity.ok(tipoRequisicaoService.listarTodos());
    }

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<TipoRequisicao> buscar(@PathVariable Integer id) {
        return tipoRequisicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PostMapping
    public ResponseEntity<TipoRequisicao> criar(@RequestBody @Valid TipoRequisicaoDTO dto) {
        return ResponseEntity.ok(tipoRequisicaoService.salvar(dto));
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<TipoRequisicao> atualizar(@PathVariable Integer id, @RequestBody @Valid TipoRequisicaoDTO dto) {
        TipoRequisicao atualizado = tipoRequisicaoService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        tipoRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

