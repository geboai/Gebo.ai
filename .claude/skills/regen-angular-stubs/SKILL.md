---
name: regen-angular-stubs
description: Build the gebo.ai.app bootable+swagger backend, run it, and regenerate the gebo-ai-rest-api Angular stubs from its live OpenAPI spec. Use when the user asks to regenerate/refresh the Angular REST stubs, the swagger client, or the gebo-ai-rest-api library after backend controller/model changes.
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Glob
  - Grep
  - Bash
  - PowerShell
---

# /regen-angular-stubs — Regenerate the Angular REST stubs from the live backend spec

Regenerates `gebo.ui/projects/gebo-ai-rest-api/src/lib` from the monolith's **live**
OpenAPI spec. The codegen (`gebo.ui` maven profile `generate-rest-api`) scrapes
`http://localhost:12999/v3/api-docs`, so the backend must be **running with swagger
and every provider enabled** first, otherwise controllers/models are missing from the
spec and their stubs get orphaned (barrels lose the exports → downstream build breaks).

Work through the steps in order. Stop and report if a step fails; don't silently skip.
Use the scratchpad dir for the isolated GEBO home/work/log so nothing of the user's is
polluted.

## 0. Preconditions — infra must be up

The monolith needs these reachable before it will start:
MongoDB `27017` (creds `mongoroot`/`mongopwd`), Neo4j `7687`, Qdrant `6334`,
OpenSearch/Elasticsearch `9200`. Probe them:

```bash
for p in 27017 6334 7687 9200; do timeout 3 bash -c "</dev/tcp/127.0.0.1/$p" 2>/dev/null && echo "$p OPEN" || echo "$p CLOSED"; done
```

If any are closed, ask the user to bring the infra up (the standard stack is
`dockers/gebo.ai/windows/docker-compose.yml`, services `mongo qdrant neo4j opensearch`).
Do **not** stop/remove containers you didn't create — that needs the user's consent.
Docker Desktop must be running (`docker version`); if not, start it and wait for the daemon.

## 1. Reflect current source in the jar

The jar is repackaged from `.m2` artifacts. If backend modules changed in this session,
install them first so the spec reflects the changes, e.g. for a single module:

```
mvn -o install -DskipTests   (run in the changed module dir, e.g. gebo.llms.parent/gebo.llms.setup)
```

## 2. Build the bootable+swagger jar

```
mvn -o -Pswagger-on package -DskipTests   (in gebo.apps.parent/gebo.ai.app)
```

Produces `target/gebo.ai.app-<version>-bootable+swagger.jar` (the `swagger-on` profile
adds the `gebo.architecture.swagger` = springdoc dependency and the `bootable+swagger`
classifier).

## 3. Run the backend (background) with ALL providers enabled

Read `./run.bat` (repo root) and reuse its `-Dai.gebo...Enabled=true` provider flags —
**drop the `-agentlib:jdwp` debug flag**. These flags register every provider's
controllers; without them a disabled provider (e.g. GoogleVertex) is absent from the
spec and its stubs get orphaned. Also:

- Set `GEBO_HOME`, `GEBO_WORK_DIRECTORY`, `GEBO_LOG_BASE` (Main aborts without `GEBO_HOME`) — point them at fresh scratchpad dirs.
- Use a **fresh empty Mongo DB** via `SPRING_APPLICATION_JSON` to avoid a startup crash: with existing model data, `GTranscriptModelRuntimeConfigurationDaoimpl.onApplicationEvent` throws `LLMConfigException: Cannot find embedding model with type=null` and the context aborts. The DB is chosen by `ai.gebo.mongodb.databaseName` (NOT the connection-string path).

Note: the `-Pswagger-on` spring-boot plugin `<jvmArguments>` are hardcoded, so
`-Dspring-boot.run.jvmArguments` is ignored — that's why we run the **jar directly**
(like run.bat), which lets the `-D` flags through.

Run it in the background (fill in `<VER>` and the actual run.bat flags):

```bash
SCRATCH="<scratchpad-dir>"; mkdir -p "$SCRATCH/gebo-home" "$SCRATCH/gebo-work" "$SCRATCH/gebo-logs"
export GEBO_HOME="$SCRATCH/gebo-home" GEBO_WORK_DIRECTORY="$SCRATCH/gebo-work" GEBO_LOG_BASE="$SCRATCH/gebo-logs"
export SPRING_APPLICATION_JSON='{"ai.gebo.mongodb.databaseName":"gebo-ai-swaggergen","ai.gebo.mongodb.connectionString":"mongodb://mongoroot:mongopwd@localhost:27017/gebo-ai-swaggergen?authSource=admin"}'
java -Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED \
  -Dai.gebo.llms.config.mistralAIEnabled=true -Dai.gebo.llms.config.openAIEnabled=true -Dai.gebo.deepsearch.externalSourcesEnabled=true \
  -Dai.gebo.deepsearch.deepSearchUIAllowChooseSources=true -Dai.gebo.llms.config.ollamaEnabled=true -Dai.gebo.multilanguage.config.liveRecording=true \
  -Dai.gebo.llms.config.googleVertexEnabled=true -Dai.gebo.llms.config.anthropicEnabled=true -Dai.gebo.llms.config.huggingfaceEnabled=true \
  -Dai.gebo.llms.config.deepseekEnabled=true \
  -jar "<repo>/gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-<VER>-bootable+swagger.jar"
```

## 4. Wait for the spec, then sanity-check it

Poll `http://localhost:12999/v3/api-docs` until HTTP 200 (startup ~10-20s after infra is
warm; watch the background log for `Started Main in` / `APPLICATION FAILED TO START`).
Then fetch the spec and confirm it's complete before regenerating:

- Providers present (schema names): `GoogleVertex`, `Anthropic`, `Mistral`, `Deepseek`, `Ollama`, `Bedrock`, `OpenAI`, and `ONNXTransformers` (this is the HuggingFace/local-embedding provider — it will NOT appear as "HuggingFace").
- Any new fields/endpoints you changed this session are present.

If a provider you expect is missing, its enable flag wasn't set — fix step 3 and re-run.

## 5. Regenerate the stubs

```
mvn generate-sources -P generate-rest-api   (in gebo.ui)
```

Overwrites `projects/gebo-ai-rest-api/src/lib` from the live spec. (It does not delete
files for models no longer in the spec — that's why a complete spec in step 4 matters.)

## 6. Post-regen fixups

- `git diff` the barrels — `model/models.ts` and `api.module.ts` should have **no removed exports** (removals = a provider was missing from the spec → go back to step 3).
- swagger-codegen mis-generates `model/pipelineEnvironment.ts` as `export interface PipelineEnvironment extends null<String, any>` (invalid TS). Restore the committed version: `git checkout -- gebo.ui/projects/gebo-ai-rest-api/src/lib/model/pipelineEnvironment.ts`. Also scan for other `extends null` artifacts and fix the same way.
- `encoder.ts` is handled by the shared template override at `gebo.api.clients/swagger-codegen-templates/typescript-angular` — leave it.
- Expect ~80+ files with real changes (mostly property-order reshuffles) plus large LF/CRLF EOL churn; that's normal.

## 7. Verify the build

The libs resolve each other from `dist` (each `tsconfig.lib.json` overrides
`@Gebo.ai/*` → `../../dist/*`). So:

```bash
cd gebo.ui && node_modules/.bin/ng build gebo-ai-rest-api      # refresh dist
node_modules/.bin/tsc --noEmit -p projects/gebo-ai-admin-ui/tsconfig.lib.json   # type-check consumers (exit 0 expected)
```

Do **NOT** run `npm run build-libs` to "fix" things — `reusable-ui`/`chat-ui` don't build
from source reliably in this environment (their `dist` are prebuilt artifacts); a failed
standalone build deletes a working `dist` and blocks the admin-ui build.

## 8. Cleanup

- Stop the backend you started (free port 12999): find the PID listening on 12999 and kill it (it's your process).
- Drop the throwaway Mongo DB: `docker exec <mongo-container> mongosh -u mongoroot -p mongopwd --authenticationDatabase admin --quiet --eval 'db.getSiblingDB("gebo-ai-swaggergen").dropDatabase()'`.
- Leave the user's infra containers running and untouched.

## Report

Summarize: providers verified in spec, count of regenerated files, any fixups applied
(pipelineEnvironment restore), build/type-check result, and that the backend was stopped
and the throwaway DB dropped.
