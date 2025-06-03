package com.integraju.auth;

import com.integraju.model.PerfilAdmin;
import com.integraju.security.JwtService;
import com.integraju.service.AdministradorService;
import com.integraju.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ControleAuth {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final AdministradorService administradorService;

    @PostMapping("/login/usuario")
    public ResponseEntity<?> loginSolicitante(@RequestBody LoginSolicitante login) {
        String loginId = login.getLogin();
        String senha = login.getSenha();

        usuarioService.autenticarSolicitante(loginId, senha);
        String token = jwtService.generateToken(loginId, "SOLICITANTE");

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> loginAdministrador(@RequestBody LoginAdministrador login) {
        String email = login.getEmail();
        String senha = login.getSenha();
        PerfilAdmin tipo = login.getTipo();

        administradorService.autenticarAdministradorPorPerfil(email, senha, tipo);
        String token = jwtService.generateToken(email, tipo.name());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
