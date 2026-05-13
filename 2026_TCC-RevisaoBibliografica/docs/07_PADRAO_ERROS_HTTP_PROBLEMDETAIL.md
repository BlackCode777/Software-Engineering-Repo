# 07 - Padrao Erros HTTP ProblemDetail

Todos os apps usam `ProblemDetail` do Spring para erros HTTP.

Formato minimo:

```json
{
  "type": "https://archsoft.local/problems/internal-error",
  "title": "Internal Server Error",
  "status": 500,
  "detail": "Mensagem tecnica resumida",
  "code": "ARCHSOFT_INTERNAL_ERROR",
  "requestId": "11111111-1111-1111-1111-111111111111",
  "runId": "22222222-2222-2222-2222-222222222222",
  "changeId": "change-01",
  "architecture": "monolith",
  "constructorCommit": "abc1234"
}
```

Properties obrigatorias:

- `requestId`
- `runId`
- `changeId`
- `architecture`
- `constructorCommit`

Mesmo quando algum header opcional nao vier na entrada, a property permanece presente com valor `null`, exceto `architecture`, que usa o valor padrao do app.
