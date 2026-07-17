---
name: gebo-backend-test
description: Acquire a JWT and drive the running monolith's REST controllers directly (login, token renew, swagger spec, fast LLMS setup, chat/TTS/transcript/embedding/ranker round trips). Use when the user asks to test the backend by calling services directly, exercise the LLMS setup, or verify a controller end to end against a live gebo.ai.app.
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Write
  - Glob
  - Grep
  - Bash
  - PowerShell
---

# Test the Gebo.ai backend directly

Drive the running monolith's REST controllers with a real JWT. The app must be up on
port 12999, built with `-P swagger-on` so `/v3/api-docs` describes every controller.

## 1. Build and run (if not already up)

```
# build with tests skipped, swagger on (compile-dev.bat):
mvn clean install -DskipTests -P angular-ui,swagger-on,under-development
```

Run the bootable+swagger jar. Background shells do NOT inherit `GEBO_HOME` /
`GEBO_WORK_DIRECTORY`, so pass them as `-D` flags:

```
java -Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8 \
  --add-opens=java.base/java.nio.charset=ALL-UNNAMED --enable-native-access=ALL-UNNAMED \
  -DGEBO_HOME='c:\projects\runtimes\Gebo-home' \
  -DGEBO_WORK_DIRECTORY='c:\projects\runtimes\Gebo.ai-workdir' \
  -Dai.gebo.llms.config.openAIEnabled=true -Dai.gebo.llms.config.anthropicEnabled=true \
  -jar gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-1.0.2.0-SNAPSHOT-bootable+swagger.jar
```

Ready when the log prints `Tomcat started on port 12999`. Poll it; don't fixed-sleep.

## 2. Auth — the token lives inside an OperationStatus

`POST /auth/login` with `{username, password}`. The JWT is NOT at the top level; it is at
`result.securityHeaderData.token`. Tokens expire in ~2 minutes — renew with
`GET /api/users/TokenRenewController/renew` (Bearer the current token) rather than
re-logging in each call. `Authorization: Bearer <token>` on every protected call.

The bundled `scripts/gebo.py` `Session` class does login + proactive renew for you:

```python
import sys; sys.path.insert(0, r"<abs path to this skill>/scripts")
from gebo import Session          # edit USER/PWD/BASE at the top if they differ
s = Session()
st, status = s.get("/api/admin/GeboFastLLMSSetupController/getLLMSSetupStatus")
st, res    = s.post("/api/admin/SomeController/someAction", {"field": "value"})
st, raw    = s.post_binary_get_bytes("/api/users/GeboTextToSpeechController/speechText", {"text": "hi"})
st, txt    = s.post_bytes("/api/users/GeboTranscriptController/transcriptText", mp3_bytes)
```

Never hardcode a token in a script — they die in 2 minutes. Never print the raw JWT to
anywhere durable.

## 3. Find any endpoint from the live spec

Fetch once (needs a Bearer token), then grep it — do not guess controller paths:

```
curl -s -H "Authorization: Bearer $TOKEN" http://localhost:12999/v3/api-docs -o apidocs.json
python scripts/find_endpoints.py <substring>   # prints METHOD path params= for matches
```

The spec also carries every request-body schema under `components.schemas`, so read the
exact field names there instead of guessing (e.g. a getter that 500s with
"modelTypeCode cannot be null" wants that field in its POST body).

## 4. Fast LLMS setup surface (`/api/admin/GeboFastLLMSSetupController`)

- `getLLMSSetupStatus` (GET) — per-kind booleans + current default code/provider.
- `verifyVendorCredentialsAndDownloadModels` (POST `{vendorId, secretId, baseUrl}`) — what
  the wizard fires on key selection; returns the provider's live model list.
- `createLLMByAutoconfigure` (POST `LLMAutoconfigureCreationData`) — the easy-mode create:
  `{vendorId, secretId, defaultChatModel, internalServicesModel, embeddingModel,
  rankerModel, transcriptModel, ttsModel, imagesModel}`. Fill each field with the
  library.yml preset default for that vendor. `scripts/easy_run.py <vendorId>` does this
  end to end reading the defaults from library.yml.
- Model-kind lookup: `verifyCredentialsAndDownloadModels` (POST
  `{type, serviceHandler, secretId, baseUrl}`), one kind at a time.

Secret ids live in the `geboSecret` mongo collection (context = vendor id). Read them, do
not invent them.

## 5. Exercise the created models at runtime (the real proof)

Creation succeeding proves little: chat/tts/transcript/image do NOT call the provider at
create time, only embedding does (it probes dimensions to build the vector store). To
prove a model actually works, call it:

- Chat: `POST /api/users/GeboDirectModelChatController/chat`
  `{chatModelCode, query, streamResponse:false}` → answer at `result.queryResponse`.
  `chatModelCode` is the config code (e.g. `chatmodel-openrouter.ai-...-gpt-oss-120b`),
  from the `*ChatModelConfig` mongo collection or `getLLMSSetupStatus`.
- TTS: `POST /api/users/GeboTextToSpeechController/speechText` `{text}` → binary MP3
  (`ID3`/`MPEG ADTS` magic). Uses the default TTS model.
- Transcript: `POST /api/users/GeboTranscriptController/transcriptText`, octet-stream
  body = audio bytes → `{text}`. Feed the TTS MP3 straight back for a round trip.
- Embedding is exercised by creating the model (vector store build) and by any RAG search.

## 6. Deleting a model — use the controller, never a mongo bulk delete

NEVER `deleteMany`/drop mongo collections on the dev DB (see memory
`feedback_no_bulk_db_deletes`). Delete one model at a time through its controller, e.g.
`POST /api/admin/GenericOpenAIAPIChatModelsConfigurationController/deleteGenericOpenAIAPIChatModelConfig`
with a body of `{code, modelTypeCode}`. Find the exact delete path per model kind /
provider module in the swagger spec (`find_endpoints.py <Kind>ModelsConfigurationController`).

## Gotchas

- The token is at `result.securityHeaderData.token`, not `token`/`accessToken`.
- Windows Git Bash has NO outbound network — probe external hosts from the JVM (via a
  controller), not with `curl` in the shell. `curl` to `localhost:12999` is fine.
- Stop the app before any DB-side model change; it caches model configs in memory.
- Same providers.yml `baseUrl` is read with two conventions — see memory
  `project_openai_compat_baseurl`.
