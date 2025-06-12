package com.integraju.security;

import com.integraju.usuario.dto.LoginCidadaoDTO;
import com.integraju.analista.dto.LoginAnalistaDTO;
import com.integraju.analista.service.AnalistaService;
import com.integraju.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Login e geração de token JWT para acesso ao sistema.")
public class ControleAuth {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final AnalistaService analistaService;

    @Operation(
            summary = "Login de cidadão",
            description = "Autentica o cidadão com CPF ou e-mail e senha, gerando token JWT.",
            tags = {"Autenticação"}
    )
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    @PostMapping("/login/usuario")
    public ResponseEntity<?> loginCidadao(@Valid @RequestBody LoginCidadaoDTO login) {
        try {
            usuarioService.autenticarCidadao(login.getLogin(), login.getSenha());
            String token = jwtService.generateToken(login.getLogin(), "CIDADAO");
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }

    @Operation(
            summary = "Login de analista",
            description = "Autentica o analista com e-mail, senha e perfil, gerando token JWT.",
            tags = {"Autenticação"}
    )
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    @PostMapping("/login/analista")
    public ResponseEntity<?> loginAnalista(@Valid @RequestBody LoginAnalistaDTO login) {
        try {
            analistaService.autenticarAnalista(login.getEmail(), login.getSenha());
            String token = jwtService.generateToken(login.getEmail(), "ANALISTA");
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }
}
