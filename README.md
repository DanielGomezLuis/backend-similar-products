# backend-similar-products
REST API productos similares con Spring WebFlux.

## Tech Stack
- Java 17
- Spring Boot 3
- WebFlux
- Docker
- k6 (performance testing)

## Run mocks
docker compose up -d simulado influxdb grafana

## Run application
./mvnw spring-boot:run

## Endpoint
GET /product/{productId}/similar

## Run performance test
docker compose run --rm k6 run scripts/test.js
