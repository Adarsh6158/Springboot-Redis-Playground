<h1 align="center">Redis Patterns with Spring Boot</h1>

<p align="center">
  A practical project showing how Redis is used in real backend systems for performance, scalability, and distributed use cases.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Cache-Redis-green" />
  <img src="https://img.shields.io/badge/Backend-SpringBoot-brightgreen" />
  <img src="https://img.shields.io/badge/Rate%20Limiting-Distributed-red" />
  <img src="https://img.shields.io/badge/Messaging-Pub%2FSub-purple" />
</p>


## Core Features

* Caching using Redis with Spring Cache
* Distributed rate limiting using INCR + EXPIRE
* Session management stored directly in Redis
* Pub/Sub messaging for real-time events
* Redis data structures (String, Hash, List, Set, Sorted Set)


## Architecture

<img width="810" height="520" alt="image" src="https://github.com/user-attachments/assets/c89b33d8-55bf-431f-a435-088dd8d4bd7b" />


## Tech Stack

<table>
<tr>
<td>
<b>Backend</b><br/> <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" width="28"/> Java   <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" width="28"/> Spring Boot
</td>
<td>
<b>Data Store</b><br/><img src="https://www.vectorlogo.zone/logos/redis/redis-icon.svg" width="28"/> Redis
</td>
</tr>
</table>

## How to Run the Project

### Start Redis

```bash
redis-server
```
## API Documentation (Swagger)
```
http://localhost:8080/swagger-ui/index.html
```
## Postman Collection

A Postman collection is included with this project for easy API testing (Open Postman -> Click Import -> Select the provided collection file).

[Download Postman Collection](https://raw.githubusercontent.com/Adarsh6158/Springboot-Redis-Playground/main/Postman_Collection.json)


## Design Highlights

* Redis used beyond caching (rate limiting, sessions, messaging)
* Handles distributed scenarios across multiple instances
* Uses atomic operations for consistency
* Data structures used based on use case (not one-size-fits-all)


## Why this project

Built to understand how Redis is used in real systems, how it improves performance, and how it helps in designing scalable backend services.