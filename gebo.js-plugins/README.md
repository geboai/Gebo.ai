# gebo.js-plugins

Plain-JavaScript (not TypeScript/Angular) API client stubs, generated with
swagger-codegen against live OpenAPI specs — same tooling and profile as
`gebo.ui`'s Angular client (`swagger-codegen-maven-plugin`, profile
`generate-rest-api`, phase `generate-sources`), just `language=javascript`
instead of `language=typescript-angular`.

This is a multi-module Maven reactor:

- `gebo.js-plugins/` (this pom) — generates `gebo-ai-js-client/` from the
  **monolith**'s spec (`gebo.apps.parent/gebo.ai.app`).
- `gebo.js-plugins/brain/` — generates `brain-ai-js-client/` from the
  **brain microservice**'s spec. See `brain/README.md` for its own details
  (including how to pass an OAuth2/JWT bearer token on API calls).

## Why building this isn't just `mvn package`

The `generate-rest-api` profile is **not active by default** — it only runs
when you explicitly pass `-P generate-rest-api`, and even then it needs a
**live, reachable OpenAPI spec** to generate against (`swagger.file` in each
module's `pom.xml` points at a `localhost` URL). There is no static/offline
spec file checked into the repo; the backend has to actually be running with
swagger enabled first. That's the two-step anyone hits:

1. Get the target backend running with its spec exposed.
2. Run the codegen against it.

## Building the monolith stub (`gebo-ai-js-client`)

1. Build the bootable+swagger jar (adds springdoc to the classpath):
   ```
   mvn -Pswagger-on package -DskipTests
   ```
   (run in `gebo.apps.parent/gebo.ai.app`)
2. Run that jar (needs `GEBO_HOME`/`GEBO_WORK_DIRECTORY`/`GEBO_LOG_BASE` env
   vars and Mongo/Qdrant/Neo4j/OpenSearch reachable — see `run.bat` at the
   repo root for the full provider-enable flags):
   ```
   java -jar gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-<version>-bootable+swagger.jar
   ```
3. Wait for `http://localhost:12999/v3/api-docs` to return the spec, then
   regenerate (run in `gebo.js-plugins/`):
   ```
   mvn generate-sources -P generate-rest-api
   ```
   This overwrites `gebo-ai-js-client/`.

The `/regen-angular-stubs` skill automates the equivalent flow for the
Angular client (`gebo.ui`) against the same backend/spec — steps 1-3 above
are the same backend setup, just pointed at this module's profile instead.

## Building the brain stub (`brain-ai-js-client`)

See `brain/README.md` — same shape, but against
`gebo.apps.parent/gebo.microservices.apps.parent/brain.gebo.ai`'s own
`swagger-on` build and its live spec at
`http://localhost:13001/brain/v3/api-docs`.

## What you get

Each generated client is a standalone npm package (`npm install` inside it)
exporting an `ApiClient` plus one `*ControllerApi` class per backend
controller and one model class per schema — plain ES modules, no framework
dependency, usable from any Node/browser/bundler setup. `docs/` and `test/`
folders alongside `src/` are also swagger-codegen output.
