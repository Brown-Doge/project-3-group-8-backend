# syntax=docker/dockerfile:1

# ---- Build stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

# copy gradle wrapper + build files first to leverage Docker cache
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle gradle
RUN chmod +x gradlew

# copy source
COPY src src

# build the fat jar
RUN ./gradlew bootJar --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# (optional) JVM opts hook
ENV JAVA_OPTS=""

# copy jar from build stage
COPY --from=build /workspace/build/libs/*.jar app.jar

# Heroku provides $PORT; bind Spring to it
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=$PORT -jar app.jar"]
