package com.integraju.controller;

import com.integraju.dto.FormularioTemplateDTO;
import com.integraju.model.FormularioTemplate;
import com.integraju.service.FormularioTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/formularios-template")
@RequiredArgsConstructor
public class FormularioTemplateController {

    private final FormularioTemplateService formularioTemplateService;

    @PreAuthorize("hasAuthority('COORDENADOR') or hasAuthority('ANALISTA')")
    @GetMapping
    public ResponseEntity<List<FormularioTemplate>> listar() {
        return ResponseEntity.ok(formularioTemplateService.listarTodos());
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PostMapping
    public ResponseEntity<FormularioTemplate> criar(@RequestBody @Valid FormularioTemplateDTO dto) {
        return ResponseEntity.ok(formularioTemplateService.salvar(dto));
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<FormularioTemplate> atualizar(@PathVariable Integer id, @RequestBody @Valid FormularioTemplateDTO dto) {
        FormularioTemplate atualizado = formularioTemplateService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @PreAuthorize("hasAuthority('COORDENADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        formularioTemplateService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

