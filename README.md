# Aluno API

API RESTful desenvolvida com Spring Boot 4.x para gerenciamento de alunos.

## Tecnologias Utilizadas

- **Spring Boot** 4.0.7
- **Java** 25
- **Maven** (gerenciamento de dependências)
- **Jakarta Persistence API (JPA)** 3.2.0
- **Hibernate ORM** 7.2.19.Final
- **H2 Database** (banco embutido para desenvolvimento)
- **Lombok** 1.18.46

## Pré-requisitos

- Java Development Kit (JDK) 25+
- Apache Maven 3.9+

## Configuração

Clone o repositório e navegue até a pasta do projeto:

```bash
git clone <url-do-repositorio>
cd aluno-api
```

O arquivo de configuração está em `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:alunoDB
    username: sa
    password:
    driver-class-name: org.h2.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8080
```

## Execução

### Com Maven:

```bash
mvn spring-boot:run
```

### Ou compilando e executando o JAR:

```bash
mvn clean package -DskipTests
java -jar target/aluno-api-0.0.1-SNAPSHOT.jar
```

A API estará disponível em: **http://localhost:8080**

## H2 Console

O console do H2 está habilitado para desenvolvimento. Acesse em:

**URL:** http://localhost:8080/h2-console

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:alunoDB`
- Usuário: `sa`
- Senha: (vazia)

## Endpoints da API

### GET /api/alunos

Lista todos os alunos.

```bash
curl -X GET http://localhost:8080/api/alunos
```

**Resposta:**

```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "idade": 25,
    "sexo": "M",
    "matricula": "MAT-2024-001"
  }
]
```

---

### GET /api/alunos/{id}

Busca um aluno pelo ID.

```bash
curl -X GET http://localhost:8080/api/alunos/1
```

**Resposta:**

```json
{
  "id": 1,
  "nome": "João Silva",
  "idade": 25,
  "sexo": "M",
  "matricula": "MAT-2024-001"
}
```

---

### GET /api/alunos/matricula/{matricula}

Busca um aluno pela matrícula.

```bash
curl -X GET http://localhost:8080/api/alunos/matricula/MAT-2024-001
```

---

### POST /api/alunos

Cria um novo aluno.

**Requisição:**

```bash
curl -X POST http://localhost:8080/api/alunos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "idade": 22,
    "sexo": "F",
    "matricula": "MAT-2024-002"
  }'
```

**Resposta (Status 201 Created):**

```json
{
  "id": 2,
  "nome": "Maria Santos",
  "idade": 22,
  "sexo": "F",
  "matricula": "MAT-2024-002"
}
```

---

### PUT /api/alunos/{id}

Atualiza um aluno existente.

**Requisição:**

```bash
curl -X PUT http://localhost:8080/api/alunos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Atualizado",
    "idade": 26,
    "sexo": "M",
    "matricula": "MAT-2024-001"
  }'
```

---

### DELETE /api/alunos/{id}

Remove um aluno pelo ID.

```bash
curl -X DELETE http://localhost:8080/api/alunos/1
```

**Resposta:** Status `204 No Content` (sucesso) ou `404 Not Found` (não encontrado).

---

## Estrutura de Pacotes

```
br.com.rafaellbarros/
├── AlunoApiApplication.java          # Classe principal (@SpringBootApplication)
├── config/CorsConfig.java            # Configuração CORS
├── controller/AlunoController.java   # REST Controller (camada de apresentação)
├── entity/AlunoEntity.java           # Entidade JPA (mapeamento banco)
├── model/Aluno.java                  # Model com Lombok (DTO interno)
├── repository/AlunoRepository.java   # Spring Data JPA Repository
└── service/AlunoService.java         # Camada de negócio
```

### Responsabilidades por camada:

| Pacote | Responsabilidade |
|--------|------------------|
| `config` | Configurações da aplicação (CORS, beans, etc.) |
| `controller` | Recebe requisições HTTP e retorna respostas |
| `service` | Lógica de negócio |
| `repository` | Acesso ao banco de dados |
| `entity` | Mapeamento objeto-relacional (ORM) |
| `model` | Modelos de dados com Lombok |

---

## Arquitetura

```
┌─────────────┐     ┌──────────────┐     ┌─────────────┐     ┌──────────┐
│   Cliente    │────▶│  Controller  │────▶│   Service   │────▶│ Repository│
│  (HTTP)      │◀────│  (REST API)  │◀────│  (Business) │◀────│   (DB)   │
└─────────────┘     └──────────────┘     └─────────────┘     └──────────┘
                              │                                      │
                              ▼                                      ▼
                        Spring Boot                           H2 Database
```

---

## Licença

Este projeto está licenciado sob a MIT License.
