# --- ETAPA 1: Construcción (Builder) ---
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Copiamos primero el pom.xml y descargamos dependencias (mejora el caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos saltando las pruebas para hacerlo más rápido
COPY src ./src
RUN mvn clean package -DskipTests

# --- ETAPA 2: Ejecución (Run) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos SOLO el archivo .jar generado en la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto de tu aplicación (Spring Boot usa 8080 por defecto)
EXPOSE 8080

# Comando para encender la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]