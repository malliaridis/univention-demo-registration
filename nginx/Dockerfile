FROM gradle:8.14.3-jdk21 AS webbuild
WORKDIR /workspace

# Node downloaded by Kotlin/Wasm tooling requires libatomic.so.1
RUN apt-get update \
    && apt-get install -y --no-install-recommends libatomic1 \
    && rm -rf /var/lib/apt/lists/*

COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY settings.gradle.kts build.gradle.kts gradle.properties ./
COPY shared shared
COPY composeApp composeApp

RUN ./gradlew :composeApp:wasmJsBrowserDistribution --no-daemon

FROM nginx:1.27-alpine

COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Copy the produced static site (path can vary slightly by Compose/Kotlin versions)
COPY --from=webbuild /workspace/composeApp/build/dist/wasmJs/productionExecutable /usr/share/nginx/html
