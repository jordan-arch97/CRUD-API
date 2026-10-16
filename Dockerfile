# ---------- Stage 1: build the JAR ----------
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# Copy the Maven wrapper, the pom, and the source code
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src/ src/

# sed strips Windows line endings (CRLF) that would break ./mvnw on Linux;
# chmod makes the wrapper executable; then Maven builds the JAR.
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw && ./mvnw -B clean package -DskipTests

# ---------- Stage 2: run it on a smaller JRE image ----------
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

# Copy only the finished JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# No port is hardcoded: server.port=${PORT:8080} in application.properties
# makes the app listen on whatever port Render assigns.
# No secrets here: SPRING_DATASOURCE_URL is set in Render's Environment settings.
ENTRYPOINT ["java", "-jar", "app.jar"]