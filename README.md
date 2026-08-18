# Country Service — Technical Challenge

Spring Boot REST service that aggregates **country data and exchange rates** from external APIs, computes statistics, and caches responses with **Redis** to minimize latency and third-party calls. Fully containerized with docker-compose.

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

## What it does

1. Fetches country information (currencies, languages, regional blocs) from a public countries API
2. Enriches it with current **exchange rates**
3. Computes aggregate statistics over the combined dataset
4. **Caches** results in Redis so repeated queries skip external calls

## Design

```
controller/   ChallengeController — REST endpoints
service/      ChallengeService — orchestration & statistics
factory/      ResultInfoFactory — response assembly
repositories/ RedisRepository, ResultInfoRepository — cache access
dto/          Country, Currency, ExchangeRate, Statistics DTOs
config/       RedisConfiguration
```

Patterns shown: **cache-aside with Redis**, **factory pattern** for response building, DTO isolation of external API contracts.

## Run it

```bash
git clone https://github.com/andrsleo/Challenge.git
cd Challenge

# Everything (app + Redis) with Docker
docker-compose up --build

# Or locally (requires Redis on :6379)
./mvnw spring-boot:run
```

## Testing

```bash
./mvnw test
```

---
Built by [Andrés Vargas](https://github.com/andrsleo)
