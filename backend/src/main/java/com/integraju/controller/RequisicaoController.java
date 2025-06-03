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

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<Requisicao>> listar() {
        return ResponseEntity.ok(requisicaoService.listarTodos());
    }

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Requisicao> buscar(@PathVariable Integer id) {
        return requisicaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('SOLICITANTE') or hasAuthority('COORDENADOR')")
    @PostMapping
    public ResponseEntity<Requisicao> criar(@RequestBody @Valid RequisicaoDTO dto) {
        return ResponseEntity.ok(requisicaoService.salvar(dto));
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Requisicao> atualizar(@PathVariable Integer id, @RequestBody @Valid RequisicaoDTO dto) {
        Requisicao atualizado = requisicaoService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        requisicaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PutMapping("/{id}/encaminhar")
    public ResponseEntity<Requisicao> encaminhar(@PathVariable Integer id, @RequestParam Integer usuarioResponsavel) {
        Requisicao requisicaoEncaminhada = requisicaoService.encaminharRequisicao(id, usuarioResponsavel);
        return ResponseEntity.ok(requisicaoEncaminhada);
    }

    @PreAuthorize("hasAuthority('ANALISTA')")
    @PostMapping("/devolutiva")
    public ResponseEntity<Requisicao> enviarDevolutiva(@RequestBody @Valid DevolutivaDTO dto) {
        return ResponseEntity.ok(requisicaoService.enviarDevolutiva(dto));
    }

    @PreAuthorize("hasAuthority('SOLICITANTE') or hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping("/usuario")
    public ResponseEntity<List<Requisicao>> listarPorUsuario(
            @RequestParam Integer usuarioId,
            @RequestParam(required = false) StatusRequisicao status) {
        List<Requisicao> lista = requisicaoService.listarPorUsuarioEStatus(usuarioId, status);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}/logs")
    public ResponseEntity<List<LogsRequisicao>> listarLogs(@PathVariable Integer id) {
        List<LogsRequisicao> logs = requisicaoService.listarLogsPorRequisicao(id);
        return ResponseEntity.ok(logs);
    }
}


