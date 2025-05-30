package com.integraju.controller;

import com.integraju.dto.DevolutivaDTO;
import com.integraju.dto.RequisicaoDTO;
import com.integraju.model.Requisicao;
import com.integraju.model.StatusRequisicao;
import com.integraju.service.RequisicaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.integraju.model.LogsRequisicao;
import java.util.List;


@RestController
@RequestMapping("/api/requisicoes")
@RequiredArgsConstructor
public class RequisicaoController {

    private final RequisicaoService requisicaoService;

    @GetMapping
    public ResponseEntity<List<Requisicao>> listar() {
        return ResponseEntity.ok(requisicaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> buscar(@PathVariable Integer id) {
        return requisicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Requisicao> criar(@RequestBody @Valid RequisicaoDTO dto) {
        return ResponseEntity.ok(requisicaoService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Requisicao> atualizar(@PathVariable Integer id, @RequestBody @Valid RequisicaoDTO dto) {
        Requisicao atualizado = requisicaoService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        requisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/encaminhar")
    @PreAuthorize("hasAuthority('COORDENADOR')")
    public ResponseEntity<Requisicao> encaminhar(@PathVariable Integer id, @RequestParam Integer usuarioResponsavel) {
        Requisicao requisicaoEncaminhada = requisicaoService.encaminharRequisicao(id, usuarioResponsavel);
        return ResponseEntity.ok(requisicaoEncaminhada);
    }

    @PostMapping("/devolutiva")
    @PreAuthorize("hasAuthority('ANALISTA')")
    public ResponseEntity<Requisicao> enviarDevolutiva(@RequestBody @Valid DevolutivaDTO dto) {
        return ResponseEntity.ok(requisicaoService.enviarDevolutiva(dto));
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<Requisicao>> listarPorUsuario(
            @RequestParam Integer usuarioId,
            @RequestParam(required = false) StatusRequisicao status) {
        List<Requisicao> lista = requisicaoService.listarPorUsuarioEStatus(usuarioId, status);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<LogsRequisicao>> listarLogs(@PathVariable Integer id) {
        List<LogsRequisicao> logs = requisicaoService.listarLogsPorRequisicao(id);
        return ResponseEntity.ok(logs);
    }
}
