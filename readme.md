# Guia de Implantação do Projeto IntegrAju

Este guia fornece instruções necessárias para a implantação do **IntegrAju**.

---
## Pré-requisitos

Garanta que seu ambiente atenda aos seguintes requisitos:

- **Sistema Operacional:** Windows, Linux ou macOS
- **Editor de Código:** VS Code, IntelliJ ou equivalente
- **Navegador:** Google Chrome, Firefox ou Edge
- **Java 21**
- **Maven**
- **PostgreSQL**
- **Git**


---

## Como Executar o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/HenriqueOrdep/squad-22-seplog.git
cd squad-22-seplog
```

### 2. Configurar o Banco de Dados

Verifique o arquivo `BackEnd/src/main/resources/application.properties` e crie o banco conforme abaixo:

```sql
CREATE DATABASE seplog_db;
CREATE USER postgres WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE seplog_db TO postgres;
```

---

### 3. Rodar o Backend

```bash
cd BackEnd
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

> ℹ️ Um analista padrão será criado automaticamente na inicialização:
> - **Email:** `analista@seplog`
> - **Senha:** `123456`

---

### 4. Executar o Frontend

Não usa frameworks. Basta abrir o arquivo `frontend/index.html` diretamente no navegador, ou usar a extensão **Live Server** do VScode.

---

##  Autenticação e Perfis

A autenticação é feita via **JWT (JSON Web Token)**. Após o login, o token deve ser enviado nos headers das requisições.

### Perfis Disponíveis

| Perfil    | Atribuições                                                  |
|-----------|--------------------------------------------------------------|
| CIDADAO   | Cadastro, login, envio e acompanhamento de solicitações      |
| ANALISTA  | Acesso ao painel administrativo e análise de solicitações    |

### Cabeçalho de Autenticação

```http
Authorization: Bearer <seu-token>
```

---

## Swagger UI

Acesse a documentação interativa da API em:  
[`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

---

## Exemplos de Uso da API

### 1. Cadastro de Usuário (CIDADAO)

`POST /api/usuarios/cadastro`

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

### 2. Login de Usuário (CIDADAO)

`POST /api/usuarios/login`

```json
{
  "cpfOuEmail": "joao@email.com",
  "senha": "senha123"
}
```

### 3. Login de Analista

`POST /api/analistas/login`

```json
{
  "email": "analista@seplog",
  "senha": "123456"
}
```

---

## Suporte

Para dúvidas, sugestões ou suporte técnico, entre em contato com os desenvolvedores responsáveis:

- [Pedro Henrique](https://github.com/HenriqueOrdep), Desenvolvedor principal
- [Luiz Henrique](https://github.com/Lhzinxx), Desenvolvedor front e designer

---

