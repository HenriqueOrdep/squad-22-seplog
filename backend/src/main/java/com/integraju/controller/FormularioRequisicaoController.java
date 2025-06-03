package com.integraju.controller;

import com.integraju.dto.FormularioRequisicaoDTO;
import com.integraju.model.FormularioRequisicao;
import com.integraju.service.FormularioRequisicaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/formularios-requisicao")
@RequiredArgsConstructor
public class FormularioRequisicaoController {

    private final FormularioRequisicaoService formularioRequisicaoService;

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<FormularioRequisicao>> listar() {
        return ResponseEntity.ok(formularioRequisicaoService.listarTodos());
    }

    @PreAuthorize("hasAuthority('SOLICITANTE')")
    @PostMapping
    public ResponseEntity<FormularioRequisicao> criar(@RequestBody @Valid FormularioRequisicaoDTO dto) {
        return ResponseEntity.ok(formularioRequisicaoService.salvar(dto));
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        formularioRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

