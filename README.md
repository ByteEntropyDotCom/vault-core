# Vault-Core

`vault-core` is a high-security microservice designed to handle sensitive data through **Tokenization** and **Authenticated Encryption**. It ensures that PII (Personally Identifiable Information) and PCI data never leak into downstream services.

## 🛡️ Security Architecture
The service utilizes **AES-256-GCM** (Galois/Counter Mode). Unlike standard AES, GCM provides:
1. **Confidentiality**: Encrypts the data.
2. **Integrity**: Detects if the ciphertext has been tampered with using an Authentication Tag.
3. **Randomness**: A unique 12-byte Initialization Vector (IV) is generated for every request, ensuring the same input results in different ciphertext.

## 🚀 Tech Stack
- **Runtime**: Java 21 (Eclipse Temurin)
- **Framework**: Spring Boot 3.2.5
- **Concurrency**: Virtual Threads (Project Loom) enabled for high-throughput crypto ops.
- **Garbage Collector**: Generational ZGC (Low-latency).
- **Observability**: Micrometer + Prometheus Actuator.

## 🛠️ Getting Started

### Prerequisites
- Java 21
- Maven 3.9+
- Docker (Optional)

### Run Locally
```bash
mvn spring-boot:run
```

### Run Docker
```
docker build -t byteentropy/vault-core .
docker run -p 8086:8086 byteentropy/vault-core
```

## 📡 API Specification

Tokenize Data

```Endpoint: POST /api/v1/vault/tokenize```

Description: Encrypts raw data, stores it, and returns a non-sensitive token.

### 
Request:

```
JSON
{
  "data": "4111-2222-3333-4444",
  "label": "credit-card"
}

```

### Response:

```
JSON
{
  "token": "tkn-69d97a66-88a1-4ca1-b368-52ffe0ee7e18",
  "maskedData": "****4444"
}
```

## 📊 Observability

### Metrics are exposed at: 

http://localhost:8086/actuator/prometheus


### Key metrics to watch:
1. http_server_requests_seconds_count: Monitor tokenization throughput.
2. jvm_threads_live_threads: Verify Virtual Thread efficiency.

### 🧠 How it Works (The "ByteEntropy" Logic)

1. **Isolation**: By running this on port `8086`, you create a physical barrier. Only authorized services can talk to the Vault.
2. **Zero-Trust**: The `Dockerfile` runs as `vaultuser`. Even if someone hacks the app, they cannot access the root filesystem of the container.
3. **Performance**: In payment rails, milliseconds matter. 
   - **Generational ZGC** keeps GC pauses under 1ms.
   - **Virtual Threads** allow the CPU to stay busy with encryption math while waiting for the database to save the token.