# Build stage
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/myemployee-0.0.1-SNAPSHOT.jar app.jar

# Install X11 dependencies for Swing GUI
RUN apt-get update && apt-get install -y \
    libxrender1 \
    libxtst6 \
    libxi6 \
    && rm -rf /var/lib/apt/lists/*

# Set display environment variable
ENV DISPLAY=:0

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
