# 08 - Padrao Correlacao Headers

Este padrao correlaciona Motor pipeline e apps runtime nos traces, logs, respostas HTTP e erros. A finalidade e permitir que os relatorios do TCC comparem monolith, hexagonal e CQRS por execucao, mudanca aplicada e request individual.

## Headers

- `X-Request-Id`: UUID por request, gerado pelo Motor. Se ausente, o app gera um UUID.
- `X-Experiment-Run-Id`: UUID unico por execucao do Motor pipeline. Se ausente, o app mantem `null`.
- `X-Change-Id`: identificador da mudanca testada, como `change-01`.
- `X-Architecture`: `monolith`, `hexagonal` ou `cqrs`. Se ausente, o app usa a arquitetura configurada.
- `X-Constructor-Commit`: hash do commit do Constructor sincronizado para `/common`.

## Propagacao

O app sempre ecoa:

- `X-Request-Id`
- `X-Experiment-Run-Id`, quando recebido
- `X-Change-Id`, quando recebido

O filtro tambem coloca no MDC:

- `requestId`
- `runId`
- `changeId`

## OTel

Quando houver valor, o span corrente recebe:

- `archsoft.experiment.run_id`
- `archsoft.change_id`
- `archsoft.request_id`
- `archsoft.constructor.commit`

## Exemplo de curl

```powershell
curl -i http://localhost:8081/health `
  -H "X-Request-Id: 11111111-1111-1111-1111-111111111111" `
  -H "X-Experiment-Run-Id: 22222222-2222-2222-2222-222222222222" `
  -H "X-Change-Id: change-01" `
  -H "X-Architecture: monolith" `
  -H "X-Constructor-Commit: abc1234"
```

## Exemplo de resposta

Headers esperados:

```text
X-Request-Id: 11111111-1111-1111-1111-111111111111
X-Experiment-Run-Id: 22222222-2222-2222-2222-222222222222
X-Change-Id: change-01
```

Body esperado:

```json
{
  "status": "UP",
  "arch": "monolith",
  "service": "archsoft-monolith"
}
```

## Exemplo de ProblemDetail

```json
{
  "type": "https://archsoft.local/problems/internal-error",
  "title": "Internal Server Error",
  "status": 500,
  "detail": "Erro inesperado",
  "code": "ARCHSOFT_INTERNAL_ERROR",
  "requestId": "11111111-1111-1111-1111-111111111111",
  "runId": "22222222-2222-2222-2222-222222222222",
  "changeId": "change-01",
  "architecture": "monolith",
  "constructorCommit": "abc1234"
}
```

## Padrao para o Motor

Quando o Motor executar smoke tests ou mini-load, ele deve gerar:

- um `X-Experiment-Run-Id` unico no inicio da execucao
- um `X-Request-Id` novo para cada chamada HTTP
- o `X-Change-Id` correspondente ao cenario em execucao
- o `X-Architecture` do alvo testado
- o `X-Constructor-Commit` sincronizado para `/common`

Isso garante que cada dado coletado ja venha carimbado com `run_id`, `change_id`, arquitetura e commit do Constructor.
