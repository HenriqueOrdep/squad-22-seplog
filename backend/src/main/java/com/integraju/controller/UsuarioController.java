package com.integraju.controller;

import com.integraju.dto.UsuarioDTO;
import com.integraju.dto.UsuarioResponseDTO;
import com.integraju.model.Usuario;
import com.integraju.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UsuarioResponseDTO> autoCadastro(@RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(dto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COORDENADOR')")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO') or hasAuthority('COORDENADOR')")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable Integer id) {
        if (!podeAcessar(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO') or hasAuthority('COORDENADOR')")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid UsuarioDTO dto) {
        if (!podeAcessar(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        UsuarioResponseDTO atualizado = usuarioService.atualizar(id, dto);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIO') or hasAuthority('COORDENADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!podeAcessar(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private boolean podeAcessar(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = auth.getName();
        try {
            Usuario logado = usuarioService.buscarPorEmail(emailLogado);
            return logado.getId().equals(id) || auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("COORDENADOR"));
        } catch (Exception e) {

            return auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("COORDENADOR"));
        }
    }
}
