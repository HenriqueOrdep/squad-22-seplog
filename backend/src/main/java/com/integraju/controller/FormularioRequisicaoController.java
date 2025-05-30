package com.integraju.controller;

import com.integraju.dto.FormularioRequisicaoDTO;
import com.integraju.model.FormularioRequisicao;
import com.integraju.service.FormularioRequisicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/formularios-requisicao")
@RequiredArgsConstructor
public class FormularioRequisicaoController {

    private final FormularioRequisicaoService formularioRequisicaoService;

    @GetMapping
    public ResponseEntity<List<FormularioRequisicao>> listar() {
        return ResponseEntity.ok(formularioRequisicaoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<FormularioRequisicao> criar(@RequestBody FormularioRequisicaoDTO dto) {
        return ResponseEntity.ok(formularioRequisicaoService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        formularioRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
