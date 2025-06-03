package com.integraju;

import com.integraju.model.*;
import com.integraju.repository.AdministradorRepository;
import com.integraju.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IntegrajuApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void deveRetornarUnauthorizedComCredenciaisInvalidas() throws Exception {
        String json = """
            {
              "email": "invalido@naoexiste.com",
              "senha": "errada",
              "tipo": "COORDENADOR"
            }
            """;

        mockMvc.perform(post("/auth/login/admin")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deveAutenticarAdministradorValidoERetornarToken() throws Exception {
        if (administradorRepository.findByEmail("admin@seplog").isEmpty()) {
            Administrador admin = Administrador.builder()
                    .email("admin@seplog")
                    .senha(encoder.encode("123456"))
                    .perfil(PerfilAdmin.COORDENADOR)
                    .build();
            administradorRepository.save(admin);
        }

        String json = """
            {
              "email": "admin@seplog",
              "senha": "123456",
              "tipo": "COORDENADOR"
            }
            """;

        mockMvc.perform(post("/auth/login/admin")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    public void deveAutenticarSolicitanteValidoERetornarToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Silva");
        usuario.setEmail("maria@email.com");
        usuario.setCpfOuCnpj("12345678901");
        usuario.setTipoPessoa("FISICA");
        usuario.setTelefone("(79) 99999-9999");
        usuario.setSenha(encoder.encode("123456"));
        usuario.setPerfil("SOLICITANTE");

        Endereco endereco = new Endereco();
        endereco.setCep("49000-000");
        endereco.setLogradouro("Rua das Flores");
        endereco.setNumero("123");
        endereco.setBairro("Centro");
        endereco.setCidade("Aracaju");
        endereco.setUf("SE");
        endereco.setComplemento("Casa");
        usuario.setEndereco(endereco);

        usuarioRepository.save(usuario);

        String jsonLogin = """
        {
          "login": "maria@email.com",
          "senha": "123456"
        }
        """;

        mockMvc.perform(post("/auth/login/usuario")
                        .contentType("application/json")
                        .content(jsonLogin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }
}
