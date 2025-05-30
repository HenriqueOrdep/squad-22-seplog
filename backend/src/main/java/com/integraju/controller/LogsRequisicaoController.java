package com.integraju.controller;

import com.integraju.dto.LogsRequisicaoDTO;
import com.integraju.model.LogsRequisicao;
import com.integraju.service.LogsRequisicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/logs-requisicoes")
@RequiredArgsConstructor
public class LogsRequisicaoController {

    private final LogsRequisicaoService logsRequisicaoService;

    @GetMapping
    public ResponseEntity<List<LogsRequisicao>> listarTodos() {
        return ResponseEntity.ok(logsRequisicaoService.listarTodos());
    }

    @GetMapping("/requisicao/{id}")
    public ResponseEntity<List<LogsRequisicao>> listarPorRequisicao(@PathVariable Integer id) {
        return ResponseEntity.ok(logsRequisicaoService.listarPorRequisicao(id));
    }

    @PostMapping
    public ResponseEntity<LogsRequisicao> criar(@RequestBody LogsRequisicaoDTO dto) {
        return ResponseEntity.ok(logsRequisicaoService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogsRequisicao> atualizar(@PathVariable Integer id, @RequestBody LogsRequisicaoDTO dto) {
        LogsRequisicao atualizado = logsRequisicaoService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        logsRequisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
