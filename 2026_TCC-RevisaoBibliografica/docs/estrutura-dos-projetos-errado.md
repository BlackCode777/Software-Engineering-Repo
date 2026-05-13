# Estrutura dos Projetos Errado

Este documento registra a estrutura dos tres projetos no estado atual. A observacao principal e que `archsoft-monolith`, `archsoft-hexagonal` e `archsoft-cqrs` estao estruturalmente iguais: todos possuem os mesmos modulos `common` e `app`, os mesmos pacotes internos equivalentes e as mesmas classes, mudando apenas o nome da arquitetura no pacote base.

Isso ainda nao representa corretamente as arquiteturas esperadas:

- `archsoft-monolith` deveria ter uma organizacao monolitica propria.
- `archsoft-hexagonal` deveria evidenciar separacao por adapters, application/core e ports.
- `archsoft-cqrs` deveria evidenciar separacao entre command side, query side, eventos/projecoes e handlers.

## Resumo do Problema

Estrutura atual dos tres:

```text
<repo>
├── app
│   └── src/main/java/br/com/archsoft/<arch>
│       ├── <Arch>Application.java
│       ├── api
│       │   ├── CorrelationHeaderFilter.java
│       │   ├── GlobalExceptionHandler.java
│       │   └── HealthController.java
│       └── config
│           └── OtelResourceConfig.java
└── common
    └── src/main/java/br/com/archsoft/<arch>/common
        ├── api_contract
        ├── domain
        ├── error
        ├── observability
        └── ports
```

Ou seja, neste momento a diferenca entre os projetos e nominal, nao arquitetural.

## archsoft-monolith

```text
archsoft-monolith
├── .gitignore
├── pom.xml
├── app
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java/br/com/archsoft/monolith
│       │   │   ├── MonolithApplication.java
│       │   │   ├── api
│       │   │   │   ├── CorrelationHeaderFilter.java
│       │   │   │   ├── GlobalExceptionHandler.java
│       │   │   │   └── HealthController.java
│       │   │   └── config
│       │   │       └── OtelResourceConfig.java
│       │   └── resources
│       │       └── application.yml
│       └── test/java/br/com/archsoft/monolith
│           ├── CorrelationHeaderFilterTests.java
│           └── MonolithApplicationTests.java
└── common
    ├── pom.xml
    └── src/main/java/br/com/archsoft/monolith/common
        ├── api_contract
        │   ├── CreateOrderRequest.java
        │   ├── OrderItemDTO.java
        │   └── OrderResponse.java
        ├── domain
        │   ├── Order.java
        │   ├── OrderItem.java
        │   ├── OrderStatus.java
        │   └── event
        │       └── OrderCreatedEvent.java
        ├── error
        │   ├── ErrorDetail.java
        │   └── ErrorResponse.java
        ├── observability
        │   ├── CorrelationContext.java
        │   ├── CorrelationHeaders.java
        │   └── TelemetryAttributes.java
        └── ports
            ├── in
            │   ├── CreateOrderUseCase.java
            │   ├── GetOrderByIdUseCase.java
            │   └── ListOrdersUseCase.java
            └── out
                ├── OrderEventPublisherPort.java
                └── OrderRepositoryPort.java
```

## archsoft-hexagonal

```text
archsoft-hexagonal
├── .gitignore
├── pom.xml
├── app
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java/br/com/archsoft/hexagonal
│       │   │   ├── HexagonalApplication.java
│       │   │   ├── api
│       │   │   │   ├── CorrelationHeaderFilter.java
│       │   │   │   ├── GlobalExceptionHandler.java
│       │   │   │   └── HealthController.java
│       │   │   └── config
│       │   │       └── OtelResourceConfig.java
│       │   └── resources
│       │       └── application.yml
│       └── test/java/br/com/archsoft/hexagonal
│           ├── CorrelationHeaderFilterTests.java
│           └── HexagonalApplicationTests.java
└── common
    ├── pom.xml
    └── src/main/java/br/com/archsoft/hexagonal/common
        ├── api_contract
        │   ├── CreateOrderRequest.java
        │   ├── OrderItemDTO.java
        │   └── OrderResponse.java
        ├── domain
        │   ├── Order.java
        │   ├── OrderItem.java
        │   ├── OrderStatus.java
        │   └── event
        │       └── OrderCreatedEvent.java
        ├── error
        │   ├── ErrorDetail.java
        │   └── ErrorResponse.java
        ├── observability
        │   ├── CorrelationContext.java
        │   ├── CorrelationHeaders.java
        │   └── TelemetryAttributes.java
        └── ports
            ├── in
            │   ├── CreateOrderUseCase.java
            │   ├── GetOrderByIdUseCase.java
            │   └── ListOrdersUseCase.java
            └── out
                ├── OrderEventPublisherPort.java
                └── OrderRepositoryPort.java
```

## archsoft-cqrs

```text
archsoft-cqrs
├── .gitignore
├── pom.xml
├── app
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java/br/com/archsoft/cqrs
│       │   │   ├── CqrsApplication.java
│       │   │   ├── api
│       │   │   │   ├── CorrelationHeaderFilter.java
│       │   │   │   ├── GlobalExceptionHandler.java
│       │   │   │   └── HealthController.java
│       │   │   └── config
│       │   │       └── OtelResourceConfig.java
│       │   └── resources
│       │       └── application.yml
│       └── test/java/br/com/archsoft/cqrs
│           ├── CorrelationHeaderFilterTests.java
│           └── CqrsApplicationTests.java
└── common
    ├── pom.xml
    └── src/main/java/br/com/archsoft/cqrs/common
        ├── api_contract
        │   ├── CreateOrderRequest.java
        │   ├── OrderItemDTO.java
        │   └── OrderResponse.java
        ├── domain
        │   ├── Order.java
        │   ├── OrderItem.java
        │   ├── OrderStatus.java
        │   └── event
        │       └── OrderCreatedEvent.java
        ├── error
        │   ├── ErrorDetail.java
        │   └── ErrorResponse.java
        ├── observability
        │   ├── CorrelationContext.java
        │   ├── CorrelationHeaders.java
        │   └── TelemetryAttributes.java
        └── ports
            ├── in
            │   ├── CreateOrderUseCase.java
            │   ├── GetOrderByIdUseCase.java
            │   └── ListOrdersUseCase.java
            └── out
                ├── OrderEventPublisherPort.java
                └── OrderRepositoryPort.java
```

## Conclusao

No estado atual, os tres projetos sao skeletons equivalentes, com troca de nomes:

- `monolith`
- `hexagonal`
- `cqrs`

A estrutura ainda precisa ser corrigida para que cada projeto represente a arquitetura correspondente antes de iniciar a comparacao arquitetural do experimento.

## Arvores Desejadas

### ALVO 1 - archsoft-monolith

```text
app/src/main/java/br/com/archsoft/monolith/
- ArchsoftMonolithApplication.java
- controller/            (futuro: OrderController)
- service/               (futuro: OrderService)
- repository/            (futuro: JpaOrderRepository)
- config/                (OtelResourceConfig e configs)
- api/                   (HealthController, CorrelationHeaderFilter, GlobalExceptionHandler)
```

### ALVO 2 - archsoft-hexagonal

```text
app/src/main/java/br/com/archsoft/hexagonal/
- ArchsoftHexagonalApplication.java
- application/           (implementacoes dos use cases)
- adapters/
  - in/
    - web/               (controllers REST)
  - out/
    - persistence/       (implementa OrderRepositoryPort)
    - messaging/         (implementa OrderEventPublisherPort)
- config/
- api/                   (HealthController, CorrelationHeaderFilter, GlobalExceptionHandler)
```

### ALVO 3 - archsoft-cqrs

```text
app/src/main/java/br/com/archsoft/cqrs/
- ArchsoftCqrsApplication.java
- command/
  - api/
  - handler/
  - persistence/
- query/
  - api/
  - handler/
  - projection/
  - persistence/
- config/
- api/                   (HealthController, CorrelationHeaderFilter, GlobalExceptionHandler)
```

## Common Neutro Desejado

O `common` deve ser igual nos tres repositórios para o Motor sincronizar sem depender de arquitetura:

```text
common/src/main/java/br/com/archsoft/common/**
```

Package correto:

```text
br.com.archsoft.common.*
```

Packages atuais errados:

```text
br.com.archsoft.monolith.common.*
br.com.archsoft.hexagonal.common.*
br.com.archsoft.cqrs.common.*
```

## Riscos

- Imports do `app` podem continuar apontando para `br.com.archsoft.<arch>.common.*` depois do move.
- Classes `Application` podem ficar com nome de arquivo diferente do nome da classe publica.
- Testes de headers podem quebrar se o filtro perder acesso a `CorrelationHeaders`.
- `spring-boot:run -pl app -am` pode tentar executar modulo agregador se o skip do plugin estiver incorreto.
- Mudanca de portas para `8081/8082/8083` pode colidir com processos locais em execucao.

## Plano de Validacao

Para cada repositorio:

```powershell
mvn -pl app -am clean test
mvn -pl app -am spring-boot:run -Dspring.profiles.active=local
curl -i http://localhost:<porta>/health
```

Validar:

- `/health` retorna `status`, `arch` e `service`.
- `X-Request-Id` e gerado quando ausente.
- `X-Request-Id`, `X-Experiment-Run-Id` e `X-Change-Id` sao ecoados quando recebidos.
- `ProblemDetail` mantem `requestId`, `runId`, `changeId`, `architecture` e `constructorCommit`.
- Resource attributes OTel continuam incluindo `service.name`, `archsoft.architecture`, `archsoft.repo`, `archsoft.scenario` e `deployment.environment`.
