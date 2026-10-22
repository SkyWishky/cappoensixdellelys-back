# ==========================================
# Stage 1: Build Spring Boot Application
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Cache dependencies layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source files and package application
COPY src ./src
RUN mvn clean package -DskipTests -B

# ==========================================
# Stage 2: Production JRE Runtime
# ==========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Security best practice: create and run as non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser:appgroup

# Copy compiled jar from build stage (pom.xml sets finalName to app)
COPY --from=build --chown=appuser:appgroup /app/target/app.jar app.jar

# Default environment configuration
ENV SERVER_PORT=8080 \
    DB_HOST=database \
    DB_PORT=5432 \
    DB_NAME=tamagochi \
    PET_DECAY_INTERVAL=10s

EXPOSE 8080

# Healthcheck probe using Spring Boot Actuator
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
