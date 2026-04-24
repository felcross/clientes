# ─── STAGE 1: Build ───────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copia os arquivos de configuração do Gradle primeiro (melhor uso de cache)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle* .

# Dá permissão de execução ao wrapper
RUN chmod +x gradlew

# Baixa dependências antecipadamente (camada cacheada enquanto build.gradle não mudar)
RUN ./gradlew dependencies --no-daemon || true

# Copia o código-fonte e compila
COPY src src
RUN ./gradlew bootJar --no-daemon -x test

# ─── STAGE 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]