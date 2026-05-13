# 04 - Changelog Geral

## 2026-05-12

- Refatorados `archsoft-monolith`, `archsoft-hexagonal` e `archsoft-cqrs` para estruturas de app distintas por arquitetura.
- Movido o `common` dos tres repos para pacote neutro `br.com.archsoft.common.*`, mantendo caminho fisico `common/src/main/java/br/com/archsoft/common/**`.
- Renomeadas as classes principais para `ArchsoftMonolithApplication`, `ArchsoftHexagonalApplication` e `ArchsoftCqrsApplication`.
- Ajustadas portas locais para `8081`, `8082` e `8083`.
- Adicionado `deployment.environment=local` aos resource attributes OTel.
- Motivo: corrigir a diferenca apenas nominal entre os repos e preservar compatibilidade do Motor com um `common` neutro.
- Criados skeletons `archsoft-monolith`, `archsoft-hexagonal` e `archsoft-cqrs`.
- Adicionada estrutura multi-modulo Maven com `common` e `app`.
- Adicionado endpoint unico `/health` em cada app.
- Configuradas portas fixas locais.
- Configurado OTLP HTTP para `http://localhost:4318/v1/traces`.
- Adicionado padrao de `ProblemDetail` com propriedades de correlacao.
- Adicionado `CorrelationHeaderFilter` para aceitar, propagar e enriquecer spans com headers do Motor.
- Atualizado agregador Maven raiz para incluir os tres skeletons.

## Restricoes mantidas

- Sem SQL Server real.
- Sem RabbitMQ real.
- Sem endpoints alem de `/health`.
- Sem handlers CQRS nesta etapa.
