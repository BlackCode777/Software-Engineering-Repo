# 05 - Comandos de Execucao

## Testes

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-monolith\pom.xml -pl app -am clean test
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-hexagonal\pom.xml -pl app -am clean test
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-cqrs\pom.xml -pl app -am clean test
```

## Subir apps

Monolith:

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-monolith\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
```

Hexagonal:

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-hexagonal\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
```

CQRS:

```powershell
.\arch_soft_main_constructor\mvnw.cmd -f archsoft-cqrs\pom.xml -pl app -am spring-boot:run "-Dspring.profiles.active=local"
```

## Health sem headers

```powershell
curl -i http://localhost:8081/health
curl -i http://localhost:8082/health
curl -i http://localhost:8083/health
```

## Health com headers de correlacao

```powershell
curl -i http://localhost:8081/health `
  -H "X-Request-Id: 11111111-1111-1111-1111-111111111111" `
  -H "X-Experiment-Run-Id: 22222222-2222-2222-2222-222222222222" `
  -H "X-Change-Id: change-01" `
  -H "X-Architecture: monolith" `
  -H "X-Constructor-Commit: abc1234"
```

Troque a porta e `X-Architecture` para `hexagonal` ou `cqrs` nos demais apps.
