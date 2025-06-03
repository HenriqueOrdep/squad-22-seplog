package com.integraju.controller;

import com.integraju.dto.ServicoDTO;
import com.integraju.model.Servico;
import com.integraju.service.ServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscar(@PathVariable Integer id) {
        return servicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PostMapping
    public ResponseEntity<Servico> criar(@RequestBody @Valid ServicoDTO dto) {
        return ResponseEntity.ok(servicoService.salvar(dto));
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoDTO dto) {
        Servico atualizado = servicoService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

