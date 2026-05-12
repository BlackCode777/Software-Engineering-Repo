# ArchSoft MAIN Constructor

Projeto-base do experimento ArchSoft. Este repositório define o contrato HTTP, domínio, casos de uso, ports e padrão de observabilidade para o MVP de pedidos. Ele não implementa monólito, hexagonal ou CQRS completos.

## Stack

- Java 21
- Spring Boot 3.x
- Spring MVC + Bean Validation
- Springdoc OpenAPI
- Micrometer Tracing + OpenTelemetry OTLP
- SQL Server e RabbitMQ no lab local, sem persistência/mensageria real neste Constructor
- OTel Collector, Tempo, Prometheus e Grafana via Podman Compose

## Estrutura

- `api`: controllers, DTOs, mappers e tratamento de erros
- `application`: implementações dos use cases e stubs em memória do Constructor
- `domain`: entidades, regras, status e eventos de domínio, sem Spring/JPA/Rabbit
- `ports.in`: contratos de entrada dos use cases
- `ports.out`: contratos de persistência e mensageria
- `config`: OpenAPI e configuração Spring
- `observability`: nomes de spans, atributos e helper de tracing

## Endpoints

- `POST /orders`
- `GET /orders/{id}`
- `GET /orders?page=0&size=20`

Swagger UI:

```powershell
http://localhost:8080/swagger-ui.html
```

## Rodar a infra local

Crie o `.env` a partir do exemplo:

```powershell
Copy-Item .env.example .env
```

Suba o lab:

```powershell
podman compose -f infra/compose.yml --env-file .env up -d
```

Serviços:

- SQL Server: `localhost:1433`
- RabbitMQ Management: `http://localhost:15672` (`archsoft` / `archsoft`)
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000` (`admin` / `admin`)
- Tempo API: `http://localhost:3200`
- OTel Collector OTLP HTTP: `http://localhost:4318`
- OTel Collector OTLP gRPC: `localhost:4317`

## Rodar o app

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

O app usa um `OrderRepositoryPort` em memória e um `OrderEventPublisherPort` no-op. Esses stubs existem só para o Constructor expor contrato e comportamento executável; persistência SQL Server e publicação RabbitMQ ficam para os repositórios de arquitetura.

## Exemplo de request

```powershell
Invoke-RestMethod -Method Post http://localhost:8080/orders `
  -ContentType "application/json" `
  -Body '{
    "customerId": "11111111-1111-1111-1111-111111111111",
    "items": [
      {
        "productId": "22222222-2222-2222-2222-222222222222",
        "quantity": 2,
        "unitPrice": 10.50
      }
    ]
  }'
```

## Observabilidade

Tags padrão deste Constructor:

- `service.name = archsoft-constructor`
- `archsoft.architecture = constructor`
- `archsoft.scenario = orders-mvp`
- `archsoft.repo = archsoft-main-constructor`

Tags futuras para comparação entre repositórios:

- `archsoft.architecture = monolith | hexagonal | cqrs`
- `archsoft.repo = archsoft-monolith | archsoft-hexagonal | archsoft-cqrs`

Spans manuais padronizados:

- `orders.create`
- `orders.getById`
- `orders.list`
- `orders.repository.save`
- `orders.repository.findById`
- `orders.repository.findAll`
- `orders.event.publish`

Atributos usados:

- `order.id`
- `order.status`
- `order.total`
- `http.route`

Para validar traces:

1. Suba a infra.
2. Rode o app com perfil `local`.
3. Faça uma chamada `POST /orders`.
4. Abra o Grafana em `http://localhost:3000`.
5. Acesse Explore, selecione `Tempo`, filtre por `service.name=archsoft-constructor`.

## Validação

```powershell
.\mvnw.cmd test
```
