package com.integraju;

import com.integraju.model.Administrador;
import com.integraju.model.PerfilAdmin;
import com.integraju.repository.AdministradorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IntegrajuApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrajuApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(AdministradorRepository administradorRepository,
                                       BCryptPasswordEncoder encoder) {
        return args -> {
            if (administradorRepository.count() == 0) {
                Administrador admin = Administrador.builder()
                        .email("admin@seplog")
                        .senha(encoder.encode("123456"))
                        .perfil(PerfilAdmin.COORDENADOR)
                        .build();
                administradorRepository.save(admin);
                System.out.println(">>> Coordenador admin@seplog criado com senha 123456");
            }
        };
    }
}
