# 09 - Uso das Pastas Common

## Resposta curta

As pastas `common` **nao devem ser deletadas neste momento**.

Elas sao usadas pelo codigo atual dos tres projetos:

- `archsoft-monolith`
- `archsoft-hexagonal`
- `archsoft-cqrs`

Se qualquer uma dessas pastas `common` for removida agora, o respectivo `app` deixa de compilar.

## Por que elas existem

Cada repositorio e um Maven multi-modulo com esta estrutura:

```text
<repo>
├── common
└── app
```

O modulo `app` depende do modulo `common` no `app/pom.xml`.

Exemplo:

```xml
<dependency>
    <groupId>br.com.archsoft.monolith</groupId>
    <artifactId>common</artifactId>
    <version>${project.version}</version>
</dependency>
```

O mesmo padrao existe em `archsoft-hexagonal` e `archsoft-cqrs`, com seus respectivos `groupId`.

## O que o app importa do common hoje

Os apps importam classes de:

```text
br.com.archsoft.common.observability.*
```

Uso atual:

- `CorrelationHeaderFilter` usa:
  - `CorrelationContext`
  - `CorrelationHeaders`
  - `TelemetryAttributes`
- `GlobalExceptionHandler` usa:
  - `CorrelationContext`
  - `TelemetryAttributes`
- `HealthController` usa:
  - `TelemetryAttributes`
- `OtelResourceConfig` usa:
  - `TelemetryAttributes`
- testes de correlacao usam:
  - `CorrelationHeaders`

Portanto, mesmo que ainda nao exista feature `/orders`, o `common` ja e parte do runtime atual por causa de observabilidade, headers de correlacao e metadados do `/health`.

## O que existe dentro do common

Atualmente, cada repo tem um `common` com o mesmo package neutro:

```text
common/src/main/java/br/com/archsoft/common
├── api_contract
├── domain
├── error
├── observability
└── ports
```

Esse package neutro foi intencional:

```text
br.com.archsoft.common.*
```

Motivo: permitir que o Motor sincronize o mesmo conteudo de contrato/dominio/ports/observability sem depender da arquitetura alvo.

## Por que ainda existem 3 pastas common

Elas existem porque os tres repositorios foram criados como projetos independentes:

```text
archsoft-monolith/common
archsoft-hexagonal/common
archsoft-cqrs/common
```

Cada app compila contra o seu proprio modulo `common`.

Isso simula o estado esperado depois de um sync do Motor: o Motor copiaria o mesmo `common` neutro para cada repositorio arquitetural, e cada arquitetura implementaria sua propria camada `app`.

## Pode deletar?

### Nao pode deletar agora

Nao delete enquanto:

- `app/pom.xml` depender de `<artifactId>common</artifactId>`
- houver imports `br.com.archsoft.common.*` no app
- o Motor ainda for sincronizar `/common` para cada repo
- `TelemetryAttributes`, `CorrelationHeaders` e contratos estiverem no common

### So poderia deletar se a arquitetura mudar

As pastas `common` so poderiam ser removidas se fosse feita uma dessas mudancas:

1. Mover todas as classes de `common` para dentro de `app`.
2. Remover a dependencia Maven do modulo `common`.
3. Atualizar todos os imports do app.
4. Alterar o plano do Motor para nao sincronizar mais `/common`.

Essa mudanca nao e recomendada para o experimento atual, porque quebraria a ideia de uma fonte comum sincronizavel entre arquiteturas.

## Recomendacao

Manter as pastas `common`.

O ponto importante nao e apagar `common`, mas garantir que:

- o package seja neutro: `br.com.archsoft.common.*`
- o conteudo seja equivalente nos tres repos
- cada `app` tenha estrutura arquitetural propria
- nenhuma regra especifica de monolith/hexagonal/cqrs entre no `common`

Assim, o experimento continua comparando arquiteturas diferentes sem duplicar manualmente contratos, dominio, ports e padroes de observabilidade.
