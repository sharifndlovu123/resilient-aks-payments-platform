# Demo: payments API (Spring Boot)

**Framework:** Spring Boot 3.5 on Java 21, with Maven. **Moving parts locally:** none. Payments live in memory, so they vanish on restart. There is no database and no queue.

This is the best app to start learning Spring Boot on: one controller, one test.

## Prerequisites

Java 21 and Maven (`java -version`, `mvn -v`).

## Run it

```bash
cd app
mvn test                  # first run downloads dependencies, which takes a few minutes
mvn spring-boot:run       # http://localhost:8080
```

## Try it

```bash
curl -X POST localhost:8080/payments -H 'Content-Type: application/json' \
     -d '{"amountCents":1500,"currency":"ZAR"}'
# {"id":"<uuid>","amountCents":1500,"currency":"ZAR","status":"PAID"}

curl localhost:8080/payments/<uuid>
curl localhost:8080/actuator/health        # what Front Door probes
```

## Read the code in this order

1. `PaymentsApplication`: `@SpringBootApplication` starts the server.
2. `PaymentController`: `@RestController` plus `@PostMapping` / `@GetMapping`. The `record` types are the JSON shapes.
3. `PaymentControllerTest`: `@SpringBootTest` boots the real app, `MockMvc` sends fake requests.

## Docker

```bash
docker build -t payments . && docker run -p 8080:8080 payments
```

## Later (cloud work)

A queue worker that KEDA scales on queue depth is not in the code yet. Add it when that part of the project needs it.
