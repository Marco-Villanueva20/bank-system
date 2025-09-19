# Imagen base con Java 17 (LTS)
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el contenido del proyecto
COPY . .

# Dar permisos al wrapper de Maven
RUN chmod +x mvnw

# Construir la app sin correr tests
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8080 (Render usará $PORT automáticamente)
EXPOSE 8080

# Ejecutar el .jar generado
CMD ["java", "-jar", "target/bank-system-0.0.1-SNAPSHOT.jar"]
