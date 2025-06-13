
#  Guia de Implantação do Projeto **IntegrAju**

Este guia fornece todas as instruções necessárias para implantar e testar o projeto **IntegrAju**, desde a clonagem até a execução e testes completos via Swagger.

---

##  Pré-requisitos

Antes de iniciar, certifique-se de que o ambiente tenha:

-  **Java 21**
-  **Maven 3.8+**
-  **PostgreSQL 14+**
-  **Git**
-  **Editor** como VS Code ou IntelliJ
-  **Navegador moderno** (Chrome, Firefox, Edge)
- **Sistema Operacional:** Windows, Linux ou macOS
---

##  1. Clonar o Repositório

```bash
git clone https://github.com/HenriqueOrdep/squad-22-seplog.git
cd squad-22-seplog
```

---

##  2. Configurar o Banco de Dados

### Crie o banco e o usuário:

```sql
CREATE DATABASE seplog_db;
CREATE USER postgres WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE seplog_db TO postgres;
```

> ️ O projeto usa o banco `seplog_db` e autentica como `postgres:1234`.

###  Verifique `BackEnd/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seplog_db
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
```

>  O projeto aplica automaticamente os dados iniciais (`data.sql`), criando setores e serviços prontos para uso.

---

##  3. Rodar o Backend

```bash
cd BackEnd
mvn clean install
mvn spring-boot:run
```

Acesse a aplicação em:  
 `http://localhost:8080`

> ℹ Um **analista padrão** será criado automaticamente:
> - **Email:** `analista@seplog`
> - **Senha:** `123456`

---

##  4. Executar o Frontend

O frontend é um HTML estático. Você pode:

-  Abrir diretamente o arquivo `frontend/index.html`
-  Ou usar o plugin **Live Server** do VS Code

---

##  Autenticação (JWT)

A autenticação é feita por **JWT Token**, retornado após login.

### Perfis disponíveis:

| Perfil    | Atribuições                                            |
|-----------|--------------------------------------------------------|
| `CIDADAO` | Cadastro, login, criação de solicitações               |
| `ANALISTA`| Triagem, devolutiva e atualização de status das solicitações |

### Envio do token:

```http
Authorization: Bearer <seu-token>
```

---

##  Documentação Swagger

A API está documentada e testável via Swagger:

 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

##  Exemplos de Uso da API

###  Cadastro de Usuário

```http
POST /api/usuarios/cadastro
```

```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "cpfOuCnpj": "12345678900",
  "tipoPessoa": "FISICA",
  "telefone": "79999999999",
  "senha": "senha123",
  "endereco": {
    "cep": "49000000",
    "logradouro": "Rua Exemplo",
    "numero": "123",
    "complemento": "Apto 101",
    "bairro": "Centro",
    "cidade": "Aracaju",
    "uf": "SE"
  }
}
```

---

###  Login de Usuário

```http
POST /api/usuarios/login
```

```json
{
  "login": "12345678900",   // Ou e-mail 
  "senha": "senha123"
}
```

---

###  Login de Analista

```http
POST /api/analistas/login
```

```json
{
  "email": "analista@seplog",
  "senha": "123456"
}
```

---

###  Criar Solicitação

```http
POST /api/solicitacoes
Authorization: Bearer <token_cidadao>
```

```json
{
  "servicoId": 1,
  "descricao": "Gostaria de agendar uma consulta médica."
}
```

> ️ Certifique-se de que o serviço com `ID = 1` está ativo (inserido via `data.sql`)

---

##  Observação Importante

Para funcionar corretamente, certifique-se de que:

-  O banco **não esteja configurado com `ddl-auto=create`** (isso apagaria as alterações).
-  A tabela `logs_solicitacoes` tenha o campo `analista_id` como **nullable**:
---

##  Suporte

Para dúvidas ou suporte:

- [Pedro Henrique, desenvolvedor principal)](https://github.com/HenriqueOrdep)
- [Luiz Henrique desenvolvedor front e designer)](https://github.com/Lhzinxx)
