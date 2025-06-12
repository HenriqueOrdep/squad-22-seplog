package com.integraju;

import com.integraju.analista.model.Analista;
import com.integraju.analista.repository.AnalistaRepository;
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
    public CommandLineRunner initAnalista(AnalistaRepository analistaRepository,
                                          BCryptPasswordEncoder encoder) {
        return args -> {
            if (analistaRepository.count() == 0) {
                Analista analista = Analista.builder()
                        .nome("Analista01")
                        .email("analista@seplog")
                        .senha(encoder.encode("123456"))
                        .build();
                analistaRepository.save(analista);
                System.out.println(">>> Analista analista@seplog criado com senha 123456");
            }
        };
    }
}
