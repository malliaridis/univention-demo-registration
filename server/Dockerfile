FROM gradle:8.14.3-jdk21 AS build
WORKDIR /workspace

COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY shared shared
COPY server server

RUN ./gradlew :server:installDist --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/server/build/install/server /app/server

ENV PORT=3000
EXPOSE 3000

CMD ["/app/server/bin/server"]
