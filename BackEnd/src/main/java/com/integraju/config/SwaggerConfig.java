package com.integraju.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Documentação API Integraju",
                version = "1.0.0",
                description = "Documentação API do projeto Integraju usando Swagger UI e OpenAPI 3.",
                contact = @Contact(
                        name = "Equipe IntegrAju",
                        email = "contato@aju.se.gov.br"
                )
        ),
        tags = {
                @Tag(name = "Autenticação", description = "Login e geração de token JWT para acesso ao sistema."),
                @Tag(name = "Usuários", description = "Operações de cadastro, atualização e busca de cidadãos."),
                @Tag(name = "Analistas e Setores", description = "Gestão de analistas públicos e seus setores vinculados."),
                @Tag(name = "Serviços", description = "Serviços disponíveis para solicitação."),
                @Tag(name = "Requisições", description = "Envio e acompanhamento de solicitações públicas."),
                @Tag(name = "Arquivos", description = "Anexos e documentos enviados nas solicitações."),
                @Tag(name = "Formulários", description = "Templates e formulários preenchidos em solicitações."),
                @Tag(name = "Devolutivas", description = "Respostas e finalização de solicitações."),
                @Tag(name = "Logs de Requisição", description = "Histórico de eventos e atualizações das solicitações públicas.")
        }
)
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
