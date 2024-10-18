# Usa la imagen base de OpenJDK 17
FROM openjdk:17-alpine

# Crea un directorio para la aplicación
WORKDIR /app

# Copia el archivo JAR generado por Maven/Gradle en el directorio de trabajo
COPY target/spaceship-api.jar /app/spaceship-api.jar

# Expone el puerto en el contenedor
EXPOSE 8080

# Define el comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app/spaceship-api.jar"]
