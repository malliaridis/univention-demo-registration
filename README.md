# univention-demo-registration

A small **Kotlin Multiplatform + Ktor** demo project that implements a simple **user registration flow**:

- **Frontend**: Compose Multiplatform UI running in the browser (Kotlin/Wasm and Kotlin/JS) and on Desktop (JVM).
- **Backend**: Ktor server exposing a small REST API.
- **Database**: PostgreSQL in Docker (H2 is used as a local/dev fallback by the server when no DB env is provided).
- **Reverse proxy / static hosting**: Nginx serves the web build and proxies API calls to the server.

## Project Structure

- `composeApp/` – Compose Multiplatform app (UI + client-side networking)
  - Targets: **Desktop (JVM)**, **Web (JS)**, **Web (Wasm)**
- `server/` – Ktor server (REST API + DB integration)
- `shared/` – Shared Kotlin Multiplatform module (DTOs, validation, constants, etc.)
- `nginx/` – Nginx config + Dockerfile to serve the web build and proxy `/api/*`
- `db/migration/` – SQL migration scripts (generated/used by the server)

## Prerequisites

To run the current project, you will need to have:

- **JDK 21**
- **Docker** + **Docker Compose** (for the full stack)

Additionally, if you are using INtelliJ IDEA you may also want to install some plugins to enhance your development
experience and render previews. For that, the following plugins are recommended:

- **[Kotlin Multiplatform](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)** plugin for IntelliJ IDEA
- **Compose Multiplatform (bundled)** plugin for IntelliJ IDEA

## Run the full stack (recommended)

1. Create an `.env` file (based on `.env.example`) and set the DB password:
   ```properties
   POSTGRES_PASSWORD=<your_password_here>
   ```

2. Start everything:
   ```bash
   docker compose up --build
   ```

3. Open the web app at http://localhost:8080

## Run backend locally (without Docker)

The server can run standalone. If you don’t provide DB environment variables, an in-memory H2 database is used instead.
This is beneficial for development and testing.

- Linux/macOS:
  ```bash
  ./gradlew :server:run
  ```

- Windows:
  ```powershell
  .\gradlew.bat :server:run
  ```

### Use an external database locally (optional)

To run the server against an external database, set these environment variables before running the server:

```properties
DB_JDBC_URL=jdbc:postgresql://<host>:<port>/<db>
DB_USER=<user>
DB_PASSWORD=<password>
DB_DRIVER=org.postgresql.Driver
```

## Run the frontend locally (dev mode)

### Web (Wasm) - modern browsers, fast
- Linux/macOS:
  ```bash
  ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
  ```
- Windows:
  ```powershell
  .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
  ```

### Web (JS) - broader compatibility
- Linux/macOS:
  ```bash
  ./gradlew :composeApp:jsBrowserDevelopmentRun
  ```
- Windows:
  ```powershell
  .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
  ```

### Desktop (JVM)
- Linux/macOS:
  ```bash
  ./gradlew :composeApp:run
  ```
- Windows:
  ```powershell
  .\gradlew.bat :composeApp:run
  ```

For the JVM client you may have to override the API base URL by providing an environment or system variable called
`API_BASE_URL` that overrides the default value of `http://127.0.0.1:3000/api/v1/`. This can be useful when testing 
the JVM client against a docker deployment.

## Useful endpoints

- `POST /api/v1/users` - create/register a user

## Current Project State

This project is intentionally small and demo-focused. Therefore, some areas are not yet implemented:

- **Proper DB migration** – Right now [Exposed](https://github.com/JetBrains/Exposed) is preparing the database, but
  it can generate migration scripts. These scripts are generated in `db/migration/` and could be used to migrate an
  existing DB to continue development over time
- **Missing unit and UI tests**
- **Address fields are incomplete** – A more complete example would capture more information, like country, state and
  an additional address line
- **Address validation is skipped** – It is possible to leave the address fields empty
- **Limited error handling** – The frontend does not interpret the errors returned by the server
- **Only the targets JS, Wasm and JVM are included** – Additional targets like Android and iOS native apps could be
  added in the future
- **No production-ready configuration** – Logging, security, config files, etc. are not production ready
- **Lenient database constraints** – The database schema is very lenient in when it comes to the input values
