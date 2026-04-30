# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install curl for healthchecks
RUN apk add --no-cache curl

# Security: Create a non-root user
RUN addgroup -S vaultgroup && adduser -S vaultuser -G vaultgroup
USER vaultuser

COPY --from=build /app/target/*.jar vault-app.jar

# Java 21 Optimizations: Using Generational ZGC for ultra-low latency encryption
ENV JAVA_OPTS="-XX:+UseZGC -XX:+ZGenerational -Xms512m -Xmx512m"

EXPOSE 8086

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar vault-app.jar"]