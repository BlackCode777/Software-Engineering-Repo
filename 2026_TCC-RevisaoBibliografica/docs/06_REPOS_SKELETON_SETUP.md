# 06 - Repos Skeleton Setup

Foram criados tres skeletons Spring Boot multi-modulo:

- `archsoft-monolith`
- `archsoft-hexagonal`
- `archsoft-cqrs`

Cada repo possui:

- `common`: dominio, ports, observability, api_contract e error
- `app`: aplicacao Spring Boot, `/health`, filtro de correlacao, ProblemDetail e config OTel

Portas locais:

- Monolith: `8081`
- Hexagonal: `8082`
- CQRS: `8083`

Comandos de validacao por repo:

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-monolith\pom.xml -pl app -am clean test
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-hexagonal\pom.xml -pl app -am clean test
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-cqrs\pom.xml -pl app -am clean test
```

Execucao local:

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-monolith\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-hexagonal\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-cqrs\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
```

Os repos nao possuem persistencia SQL Server, RabbitMQ, handlers CQRS ou controllers de negocio nesta etapa.
