package com.integraju.usuario.controller;

import com.integraju.usuario.dto.UsuarioDTO;
import com.integraju.usuario.dto.UsuarioResponseDTO;
import com.integraju.usuario.model.Usuario;
import com.integraju.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Operações de cadastro, atualização e busca de cidadãos.")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastro de cidadão", description = "Realiza o auto-cadastro de um novo usuário do tipo cidadão.", tags = {"Usuários"})
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso.")
    @PostMapping("/cadastro")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UsuarioResponseDTO> autoCadastro(
            @Parameter(description = "Dados para cadastro do cidadão", required = true)
            @RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(dto));
    }

    @Operation(summary = "Listar todos os cidadãos", description = "Retorna todos os usuários cadastrados (acesso restrito a analistas).", tags = {"Usuários"})
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    @GetMapping
    @PreAuthorize("hasAuthority('ANALISTA')")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Operation(summary = "Buscar cidadão por ID", description = "Retorna os dados de um cidadão pelo ID. Requer ser o próprio usuário ou um analista.", tags = {"Usuários"})
    @ApiResponse(responseCode = "200", description = "Usuário encontrado.")
    @ApiResponse(responseCode = "403", description = "Acesso negado.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CIDADAO', 'ANALISTA')")
    public ResponseEntity<UsuarioResponseDTO> buscar(
            @Parameter(description = "ID do usuário a ser buscado", example = "1")
            @PathVariable Integer id) {
        if (!usuarioOuAnalistaPodeAcessar(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Atualizar dados do cidadão", description = "Permite que o cidadão atualize seus próprios dados.", tags = {"Usuários"})
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Acesso negado.")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CIDADAO')")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Novos dados para atualização", required = true)
            @RequestBody @Valid UsuarioDTO dto) {
        if (!usuarioPertenceAoLogado(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        UsuarioResponseDTO atualizado = usuarioService.atualizar(id, dto);
        if (atualizado == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deletar conta de cidadão", description = "Permite que o cidadão exclua sua própria conta.", tags = {"Usuários"})
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Acesso negado.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CIDADAO')")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário a ser deletado", example = "1")
            @PathVariable Integer id) {
        if (!usuarioPertenceAoLogado(id)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private boolean usuarioPertenceAoLogado(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = auth.getName();
        try {
            Usuario logado = usuarioService.buscarPorEmail(emailLogado);
            return logado.getId().equals(id);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean usuarioOuAnalistaPodeAcessar(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogado = auth.getName();
        try {
            Usuario logado = usuarioService.buscarPorEmail(emailLogado);
            return logado.getId().equals(id) || auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ANALISTA"));
        } catch (Exception e) {
            return auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ANALISTA"));
        }
    }
}
