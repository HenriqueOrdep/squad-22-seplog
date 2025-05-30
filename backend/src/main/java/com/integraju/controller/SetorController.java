package com.integraju.controller;

import com.integraju.dto.SetorDTO;
import com.integraju.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @GetMapping
    public ResponseEntity<List<SetorDTO>> listarTodos() {
        return ResponseEntity.ok(setorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(setorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criar(@RequestBody SetorDTO dto) {
        SetorDTO criado = setorService.salvar(dto);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable Integer id, @RequestBody SetorDTO dto) {
        SetorDTO atualizado = setorService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        setorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
