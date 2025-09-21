# Etapa de build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas os arquivos de configuração primeiro (para cache eficiente)
COPY pom.xml .
COPY src ./src

# Build da aplicação (sem rodar testes)
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia apenas o jar gerado
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 (Render precisa disso)
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
