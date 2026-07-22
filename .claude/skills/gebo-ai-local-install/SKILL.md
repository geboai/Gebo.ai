---
name: gebo-ai-local-install
description: Build the gebo.ai monolithic Docker image from a fresh bootable jar, bring up the full local stack (Mongo/Qdrant/Neo4j/OpenSearch/gebo.ai) via docker compose, register the local admin account through the browser setup wizard, and check the container logs for startup failures. Use when asked to build/run the gebo.ai docker image locally, stand up the monolithic docker-compose stack, initialize the local admin account, or check the docker logs of the local gebo.ai install for errors.
user-invocable: true
allowed-tools:
  - Read
  - Glob
  - Grep
  - Bash
---

# /gebo-ai-local-install — Build, run, and register the local gebo.ai Docker stack

End-to-end procedure to go from source to a logged-in local admin account on the
monolithic `geboai/gebo.ai` Docker image, using `dockers/gebo.ai/create-image.bat`
(translated to shell — this is the canonical recipe, don't use the stale
`dockers/gebo.ai/create-image.sh`, which skips the artifact copy and tags the image
differently) and `dockers/gebo.ai/windows/docker-compose.yml`.

## 0. Preconditions

- Docker daemon running (`docker version`).
- Check host ports before starting: `27017` (mongo), `6333`/`6334` (qdrant), `7474`/`7687`
  (neo4j), `9200`/`9600` (opensearch), `12999` (gebo.ai). `ss -ltn | grep :<port>`.
- **Port 27017 conflicts are common** — this repo also ships
  `dockers/mongo-development-config/docker-compose.yml`, a separate dev Mongo/mongo-express
  stack that binds the same host port. Don't route around it with a port override unless
  asked; ask the user first, since stopping someone else's running stack is exactly the
  kind of shared-state action that needs confirmation (`cd
  dockers/mongo-development-config && docker compose down`).
- Check `free -h` — the image build alone reads/writes a >700MB jar, and the compose stack
  runs 5 JVM/heavy containers concurrently; be mindful on constrained hosts.

## 1. Build the bootable jar

The `angular-ui` profile is `activeByDefault`, but activating any other profile via `-P`
turns off default-active ones unless re-listed — so pass both explicitly:

```bash
mvn -pl gebo.apps.parent/gebo.ai.app -am -P angular-ui,bootables package -DskipTests
```

This is a full reactor build (~135+ modules) that also bundles the Angular UI — expect a
few minutes. The Angular build prints a wall of red-colored `[ERROR]`-prefixed lines that
are actually `WARNING`-level (budget/deprecation/template) noise from `gebo-ai-reusable-ui`;
don't treat those as failures — check the actual Maven exit code and the final
`BUILD SUCCESS`/`BUILD FAILURE` line instead of grepping raw `[ERROR]` colorization.

Confirms success by checking the artifacts exist:

```bash
ls gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-<version>-bootable.jar
ls gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json
```

`<version>` comes from the current parent pom (`grep '<version>' gebo.apps.parent/pom.xml`
— e.g. `1.0.2.1-SNAPSHOT`); `create-image.bat` and the `Dockerfile`'s `COPY` line both
hardcode this version string, so if it doesn't match what you just built, the `docker
build` step below will silently `COPY` a stale/missing file.

## 2. Build the Docker image (translating `create-image.bat`)

```bash
cd dockers/gebo.ai
cp ../../gebo.apps.parent/gebo.ai.app/target/gebo.ai.app-<version>-bootable.jar .
cp ../../gebo.apps.parent/gebo.ai.app/target/classes/META-INF/sbom/application.cdx.json .
rm -f gebo.ai.app-<old-version>-bootable.jar   # remove any stale jar of a different version
docker image rm geboai/gebo.ai --force          # ignore "no such image"
docker build --build-arg JAVA_EXTRA_SECURITY_DIR=/opt/gebo.ai \
  -t geboai/gebo.ai -t geboai/gebo.ai:<version> .
```

Verify both tags landed on the new image:
`docker images --format '{{.Repository}}:{{.Tag}}\t{{.CreatedSince}}' | grep geboai/gebo.ai:`

## 3. Bring up the stack

```bash
cd dockers/gebo.ai/windows
docker compose up -d
```

**Known gotcha — `opensearch` has a fixed `container_name: opensearch`.** A stopped/orphaned
container from an earlier run (possibly with no compose-project labels, i.e. not tracked by
any active stack) will block it: `Error response from daemon: Conflict. The container name
"/opensearch" is already in use...`. Check with `docker ps -a --filter name=opensearch`; if
it's `Exited` and unrelated to anything currently running, ask the user before `docker rm
opensearch` — removing any container, even a stopped one, needs explicit confirmation.

Confirm all 5 containers are `Up`: `docker compose ps`.

## 4. Wait for the app, then sanity-check with curl

```bash
for i in $(seq 1 40); do
  code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 3 http://localhost:12999/)
  [ "$code" != "000" ] && break
  sleep 10
done
```

Cold start (5 containers + full Spring context) took ~40s in this codebase's dev
environment. Cross-check `docker compose logs gebo.ai --tail=60 | grep -iE
"started|error|exception"` for `Started Main in <N> seconds` before moving on.

## 5. Register the admin account via browser

Use the `claude-in-chrome` skill. Navigate to `http://localhost:12999/`.

**Gotcha — wrong/no browser connected.** `list_connected_browsers` may show a browser on a
different OS/machine (`isLocal:false`) than the one actually running this Bash session (or
none at all). Curl succeeding from the shell while the browser gets an error page loading
the same URL is the tell — it means the paired Chrome instance isn't on this machine. Ask
the user to open Chrome with the extension **on this machine** and connect it, then re-run
`list_connected_browsers` and `select_browser` with the new (`isLocal:true`) entry before
navigating again. Per the tool's own instructions, always present every connected browser
via `AskUserQuestion` (plus the "let me pick from inside Chrome" / `switch_browser` option)
rather than silently choosing one.

The first load is `/ui/setup` — "Setup your gebo.ai installation": E-mail, Default system
language (a custom dropdown — click to open, then click an option, e.g. "English"),
Password, Confirm password, and a license-acceptance checkbox, then "Create system setup".
A successful submit redirects to `/ui/admin-setup` (the LLM setup wizard) with an
authenticated top nav (Chat/Setup/Admin/API Keys/edit profile/logout) — that redirect +
nav is the confirmation the admin account now exists. Configuring an actual LLM vendor
there is a separate, optional step needing real provider credentials — don't do it unless
asked.

## 6. Check the container logs for failures

```bash
docker compose logs gebo.ai > /tmp/gebo-ai-container.log 2>&1
grep -n "ERROR" /tmp/gebo-ai-container.log
grep -oE "[A-Za-z.]+Exception" /tmp/gebo-ai-container.log | sort -u
```

Known-benign noise, not real findings:
- Logback startup `WARN`s about `ConsoleAppender`/`SizeAndTimeBasedFNATP` deprecations —
  cosmetic logging-config chatter, unrelated to app behavior.
- `io.jsonwebtoken.ExpiredJwtException` / `ERROR ... LocalJwtTokenProvider - Expired JWT
  token` — if the browser profile used in step 5 has an old cached JWT in localStorage from
  a previous session (possibly days old), the frontend's background renew/health-check will
  keep resubmitting it and logging this at ERROR level. Harmless; not something this
  procedure introduces or needs to fix.

Anything else — especially a repeating startup exception, or `Not authenticated` /
`Error ensuring the default chat profile exists` from `DefaultChatProfileInitializationService`
(a `@Scheduled` task that needs an authenticated `SecurityContext`; see the system-user +
`IdentityUtil` impersonation pattern if this regresses) — is a real finding worth
investigating before declaring the install healthy.

## Report

Summarize: image tag/version built, containers up, HTTP check result, whether the admin
account was created (redirect to `/ui/admin-setup` observed), and the log scan result
(any real errors found vs. only the known-benign noise above).
