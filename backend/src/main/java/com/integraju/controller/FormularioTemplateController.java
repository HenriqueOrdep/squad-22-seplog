package com.integraju.controller;

import com.integraju.dto.FormularioTemplateDTO;
import com.integraju.model.FormularioTemplate;
import com.integraju.service.FormularioTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/formularios-template")
@RequiredArgsConstructor
public class FormularioTemplateController {

    private final FormularioTemplateService formularioTemplateService;

    @GetMapping
    public ResponseEntity<List<FormularioTemplate>> listar() {
        return ResponseEntity.ok(formularioTemplateService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<FormularioTemplate> criar(@RequestBody FormularioTemplateDTO dto) {
        return ResponseEntity.ok(formularioTemplateService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormularioTemplate> atualizar(@PathVariable Integer id, @RequestBody FormularioTemplateDTO dto) {
        FormularioTemplate atualizado = formularioTemplateService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        formularioTemplateService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
