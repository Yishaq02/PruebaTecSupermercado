FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=build/libs/PruebaTecSupermercado-0.0.1.jar
COPY ${JAR_FILE} app_pruebaTecSupermercado.jar
EXPOSE ${APP_PORT}
ENTRYPOINT ["java", "-jar", "app_pruebaTecSupermercado.jar"]