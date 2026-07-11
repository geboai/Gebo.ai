# Microservices client stubs — regeneration & publishing architecture

> Analysis / options document. **Nothing here is wired up yet** — it describes how
> to automate: (1) starting each microservice in a simple environment, (2)
> regenerating its Java + Angular stubs from the live OpenAPI, (3) compiling and
> committing the regenerated sources, and (4) publishing the Angular libraries to
> an npm/Nexus registry one by one.

---

## 1. Current state (what already exists)

`gebo.api.clients/gebo.microservices.clients.parent` holds **two client modules per
microservice** — a Java `resttemplate` client and an Angular/TypeScript library:

```
<name>.gebo.ai.java.client       -> gebo.microservices.api.client.<name>.{api,model,invoker}
<name>.gebo.ai.angular.client    -> projects/gebo-<name>-api/src/lib  (ng-packagr library)
```

Each microservice **app** lives under
`gebo.apps.parent/gebo.microservices.apps.parent/<name>.gebo.ai` (Spring Boot,
Eureka discovery client, `@ComponentScan("ai.gebo")`), and each app has a matching
client pair. There are **18 services** ↔ 18 Java clients ↔ 18 Angular clients.

### 1.1 Service ↔ port ↔ client map

Every client pom already encodes the OpenAPI URL of its service in `swagger.file`.
The port is the intended per-service HTTP port:

| Service (`<name>.gebo.ai`) | Port | Extra infra it needs |
|---|---|---|
| gateway      | 8080 | eureka |
| brain        | 8081 | eureka, rabbit, mongo, qdrant |
| vectorizator | 8082 | eureka, rabbit, mongo, qdrant |
| graphicator  | 8083 | eureka, rabbit, mongo, neo4j |
| chunker      | 8084 | eureka, rabbit, mongo |
| git          | 8085 | eureka, rabbit, mongo |
| filesystem   | 8086 | eureka, rabbit, mongo |
| uploads      | 8087 | eureka, rabbit, mongo |
| userspace    | 8088 | eureka, rabbit, mongo |
| sharepoint   | 8089 | eureka, rabbit, mongo |
| confluence   | 8090 | eureka, rabbit, mongo |
| jira         | 8091 | eureka, rabbit, mongo |
| aws-s3       | 8092 | eureka, rabbit, mongo |
| googledrive  | 8093 | eureka, rabbit, mongo |
| mcpclient    | 8094 | eureka, rabbit, mongo |
| integration  | 8095 | eureka, rabbit, mongo |
| fulltextor   | 8096 | eureka, rabbit, mongo, opensearch |
| eureka       | 8761 | (registry itself) |

### 1.2 The regeneration profiles (already present)

Both client flavours carry a **`generate-rest-api`** Maven profile (bound to
`generate-sources`) driving `swagger-codegen-maven-plugin`:

- **Java client** — `language=java, library=resttemplate`, custom templates at
  `gebo.api.clients/swagger-codegen-templates/java-resttemplate`, output to the
  module basedir (`src/main/java`). Jakarta EE + Jackson-3 date format.
- **Angular client** — `language=typescript-angular`, output to
  `projects/gebo-<name>-api/src/lib`.

These profiles are **not active by default** — the default build only compiles the
already-committed sources (Java) / runs the ng-packagr + `npm pack` build (Angular,
`angular-ui` profile). That is why `mvn install` on the parent is green today even
with **empty stubs**.

### 1.3 Why the stubs are currently empty

The committed `api.ts` is `export {}` and the Java `api` packages are thin: the
stubs were generated once against services that **exposed no controller endpoints**
(or were generated without a live spec). Regenerating against a fully-booted
service **with its controllers loaded** is the whole point of this document.

---

## 2. The core difficulty: booting a service to get its spec

Regeneration is a pull from `http://localhost:<port>/v3/api-docs`. Three
constraints make "just run it" non-trivial:

1. **`/v3/api-docs` only exists with the `swagger-on` profile.** Each app pom
   defines a `swagger-on` profile that adds `gebo.architecture.swagger` and builds
   a `bootable+swagger` fat jar. The default/`docker` images do **not** serve the
   spec.
2. **`GEBO_HOME` is mandatory.** `BrainApplication` (and every sibling) calls
   `System.exit(-1)` if `GEBO_HOME` is unset; `GEBO_WORK_DIRECTORY` is also needed.
   (Same contract as the monolith — see the `package-run-regen-rest` skill.)
3. **Each service needs backing infra + Eureka**, and its HTTP port must be forced.
   The shared config (`dockers/gebo.microservices/config/application.yml`) sets
   `server.port: 8080` for **all** backends — nothing binds the 8081–8096 ports.
   In Docker that is fine (one container each, ports not published); for local
   regen we must pass `SERVER_PORT=<assigned port>` so the URL in `swagger.file`
   resolves and services don't collide.

Existing building blocks we can reuse:

- `dockers/gebo.microservices/docker-compose.yml` — brings up **mongo, rabbit,
  qdrant, neo4j, opensearch, eureka, gateway** and every backend as
  `geboai/<name>.gebo.ai` images (built via `mvn -P docker package`, Jib). It only
  publishes 8080 (gateway) and 8761 (eureka); backends are internal.
- The two monolith skills (`package-run-regen-rest`, `package-run-regen-java-client`)
  already implement the *build → run bootable+swagger jar → poll /v3/api-docs →
  `mvn generate-sources -Pgenerate-rest-api` → patch → compile* loop for a single
  app. The microservices version is "the same loop, 18 times, parametrised by port".

---

## 3. Startup options (pick one)

### Option A — docker-compose up → scrape specs → regen  ✅ recommended

The services are **already packaged as Docker images** (`geboai/<name>.gebo.ai`,
built by `mvn -P docker package` via Jib) and there is already a
`dockers/gebo.microservices/docker-compose.yml` that wires them to their infra.
So the natural loop is: **build images once → `docker compose up` the whole stack
→ pull each service's `/v3/api-docs` → regen → `docker compose down`.**

The full flow (details in §4):

```
mvn -P docker package -DskipTests                   # 1. build images (swagger baked in via the starter)
docker compose -f docker-compose.yml -f docker-compose.regen.yml up -d   # 2. up + publish 808x
# 3. wait for every http://localhost:808x/v3/api-docs to answer
mvn -Pgenerate-rest-api generate-sources            # 4. regen all clients (parent reactor)
# 5. patch encoder.ts, compile Java, pack Angular
docker compose ... down                             # 6. tear down
```

Pros: one command per stage, matches production topology, uses artefacts the
release pipeline already produces (the images), regen can run against all 18 at
once. Cons: heaviest at runtime (18 services + 5 infra containers up together, ~1
GB heap each → needs a machine with enough RAM); one prerequisite (below).

**Prerequisites:**

1. ~~The images must serve `/v3/api-docs`.~~ **Done.** `gebo.architecture.swagger`
   (which brings the classpath-activated `springdoc-openapi-starter-webmvc-ui`) is
   now a dependency of `gebo.microservices.starter`, so it is on the **default**
   runtime classpath of every backend built on that starter (verified:
   `springdoc-openapi-starter-webmvc-ui:3.0.3` resolves transitively into
   `brain.gebo.ai` via `llms.starter → microservices.starter → gebo.architecture.swagger`).
   A plain `mvn -P docker package` image therefore serves `/v3/api-docs` — no
   `swagger-on` needed. In production, gate it off with
   `springdoc.api-docs.enabled=false` / `springdoc.swagger-ui.enabled=false` in the
   shared config if you don't want the spec exposed. (Note: `eureka.gebo.ai` and
   `gateway.gebo.ai` are **not** built on this starter, so their stubs stay empty
   until swagger is added to them explicitly — usually not needed.)
2. **The backends must publish a host port each.** The base compose publishes only
   gateway (8080) and eureka (8761); backends are internal. Add a
   **`docker-compose.regen.yml` override** that maps each backend's container
   `8080` to its assigned host port so the client `swagger.file` URLs resolve:

   ```yaml
   # docker-compose.regen.yml  (regen-only overlay)
   services:
     brain:        { ports: ["8081:8080"] }
     vectorizator: { ports: ["8082:8080"] }
     graphicator:  { ports: ["8083:8080"] }
     chunker:      { ports: ["8084:8080"] }
     git:          { ports: ["8085:8080"] }
     filesystem:   { ports: ["8086:8080"] }
     uploads:      { ports: ["8087:8080"] }
     userspace:    { ports: ["8088:8080"] }
     sharepoint:   { ports: ["8089:8080"] }
     confluence:   { ports: ["8090:8080"] }
     jira:         { ports: ["8091:8080"] }
     aws-s3:       { ports: ["8092:8080"] }
     googledrive:  { ports: ["8093:8080"] }
     mcpclient:    { ports: ["8094:8080"] }
     integration:  { ports: ["8095:8080"] }
     fulltextor:   { ports: ["8096:8080"] }
   # gateway already publishes 8080, eureka already publishes 8761
   ```

   Host ports here match the `swagger.file` map in §1.1, so the existing
   `generate-rest-api` profiles work unchanged. (Alternative to publishing 16
   ports: aggregate the specs through the gateway with springdoc's grouped-openapi
   `lb://` routes and scrape everything from `:8080` — cleaner networking but needs
   gateway config; publishing ports is the zero-config path.)

> **Note on `depends_on`.** Compose `depends_on` waits for container *start*, not
> readiness. Add `healthcheck`s (curl `/v3/api-docs`, or actuator `/health` if
> present) so `docker compose up --wait` blocks until the specs are actually
> serveable — otherwise the regen loop races the services and pulls empty/500s.

### Option B — Sequential local JVM sweep (lighter alternative)

Bring up **only the shared infra**, then start **one service at a time** from its
`bootable+swagger` jar on its assigned port, regen its two clients, stop it.

```
docker compose -f dockers/gebo.microservices/docker-compose.yml \
  up -d mongo rabbit qdrant neo4j opensearch eureka        # infra only
# per service: SERVER_PORT=<port> GEBO_HOME=... SPRING_CONFIG_ADDITIONAL_LOCATION=<local infra override> \
#   java -jar <name>.gebo.ai/target/*-bootable+swagger.jar ; wait /v3/api-docs ; regen ; stop
```

Pros: one JVM + fixed infra at a time → fits a laptop, no image builds, no port
collisions. Cons: sequential (≈18× start/stop), and needs a local config override
mapping the compose hostnames (`mongo`, `rabbit`, …) to `localhost` (see §6 note 2).
This is the closest analogue to the proven monolith `package-run-regen-*` skills.

### Option C — Offline spec generation (no running service)

Generate the OpenAPI at build time with `springdoc-openapi-maven-plugin` (which
itself starts the app on `integration-test`) or a static analyzer. Gebo services
need the full Spring context (Mongo/Rabbit/Hazelcast/security) to build their
controller beans, so a truly "no-infra" offline generation is fragile here.
**Least preferred** — a fallback only if a service can boot with everything
auto-disabled.

**Recommendation:** Option A (docker-compose), since the images already exist and
it is one-command-per-stage. Keep Option B as the low-RAM / no-Docker fallback.

---

## 4. Proposed automation (Option A — docker-compose)

A single driver (script or a `package-run-regen-microservices` skill) that runs
the six stages. Pseudocode of `regen-microservices.ps1`:

```
services = { brain=8081; vectorizator=8082; ...; fulltextor=8096 }   # the §1.1 map
compose  = "-f dockers/gebo.microservices/docker-compose.yml -f dockers/gebo.microservices/docker-compose.regen.yml"

# 1. build images to the local Docker daemon (swagger already on the classpath via the starter)
mvn -f gebo.apps.parent/gebo.microservices.apps.parent/pom.xml -P docker package -DskipTests

# 2. up the full stack (infra + eureka + gateway + all backends), publish 808x
docker compose $compose up -d --wait          # --wait needs healthchecks (see §3 note)

# 3. wait until every spec is serveable
foreach ($name,$port in services) { Wait-For "http://localhost:$port/v3/api-docs" }

# 4. regen every client from the live specs (whole reactor, profile-driven)
mvn -f gebo.api.clients/gebo.microservices.clients.parent/pom.xml generate-sources -Pgenerate-rest-api
#   (each module's generate-rest-api profile already points at its own swagger.file)

# 5. patch + verify + pack
foreach angular client: Patch-EncoderOverride <module>          # see §6 note 3
mvn -f gebo.api.clients/gebo.microservices.clients.parent/pom.xml clean install -DskipTests
#   -> compiles the Java clients, runs ng-packagr + npm pack for the Angular libs

# 6. tear down (keep -v off to preserve infra volumes if you want warm data)
docker compose $compose down
```

Then review the diff and commit the regenerated sources with the `auto-commit`
skill (see §5). This driver is the recommended first implementation; a pure-Maven
variant (Spring Boot `start`/`stop` per module, infra via `fabric8
docker-maven-plugin`) is possible but couples each client to its service jar
coordinate and is harder to reason about than the six explicit stages above.

---

## 5. Commit & compile the regenerated sources

The generated sources **are tracked** and must be committed; the build artifacts
are not (already handled by the new `.gitignore` in this folder — `*.tgz`, `node/`):

- **Java clients** — regen writes `src/main/java/...`. Verify with
  `mvn -pl <java client> compile -DskipTests`, then commit `src/main/java`.
- **Angular clients** — regen writes `projects/gebo-<name>-api/src/lib`. Verify
  with `mvn -pl <angular client> install` (runs ng-packagr + `npm pack`). Commit
  `projects/**/src/lib`. Do **not** commit `dist/`, `node/`, `node_modules/`,
  `*.tgz` (all gitignored).
- Use the `auto-commit` skill (no Claude signature) once the whole sweep is green.

---

## 6. Known gotchas to bake into the automation

1. **Per-service port is not bound by config** — the shared `application.yml` sets
   `server.port: 8080` for every backend. You **must** pass `SERVER_PORT=<port>`
   (or `--server.port`) per the §1.1 map, or all services fight over 8080 and the
   `swagger.file` URLs won't match.
2. **Compose hostnames vs localhost** — `dockers/gebo.microservices/config/application.yml`
   points at `mongo`, `rabbit`, `qdrant`, `neo4j`, `opensearch` (compose service
   names). For a local JVM sweep either (a) run the services as compose containers,
   or (b) supply a local override config mapping those hosts to `localhost` with
   the ports published by the infra compose (publish 27017/5672/6334/7687/9200 in
   a small infra-only compose file).
3. **`encoder.ts` `override` patch** — swagger-codegen `typescript-angular` emits
   `CustomHttpUrlEncodingCodec.encodeKey/encodeValue` **without** the `override`
   keyword, which fails `tsconfig.json`'s `noImplicitOverride: true` (set in every
   angular client). Re-apply the `override` modifiers after regen (idempotent) —
   exactly as `package-run-regen-rest` does for the monolith. (The currently
   committed files already carry `override`; regen will strip it.)
4. **`swagger-on` is required** — without it the `bootable+swagger` jar (and thus
   `/v3/api-docs`) is not produced and regen pulls a stale/absent spec.
5. **Native crash exit codes** (`-1073741819` / `0xC0000005`) during the Maven
   package are the known local hardware flakiness, not a regression — just re-run.
6. **`GEBO_HOME` / `GEBO_WORK_DIRECTORY`** must be set for every service JVM
   (defaults `%USERPROFILE%\gebo\home` / `...\work`, created if missing).

---

## 7. Publishing the Angular libraries to npm / Nexus (one by one)

Each angular client already produces a packed tarball
(`Gebo.ai-<name>-1.0.2.0-SNAPSHOT.tgz`) via `npm run build-lib` (`ng build` +
`npm pack`). Publishing it per-library can be added as a Maven **profile** that
runs `npm publish` in each module.

### 7.1 Two blockers to fix first ⚠️

These currently work for local `npm pack`/file-refs but **will break a real
`npm publish` / registry install**:

- **Package name is not a valid npm name.** `projects/gebo-<name>-api/package.json`
  uses `"name": "@Gebo.ai/<name>"` — npm names must be **lowercase** and the scope
  cannot contain a dot. Rename to e.g. `@geboai/<name>` (or `@gebo-ai/<name>`).
  This also changes the packed filename to `geboai-<name>-...tgz` and the local
  import path in the consuming Angular app.
- **Version is not valid semver.** `"1.0.2.0-SNAPSHOT"` has four numeric segments;
  npm semver is `MAJOR.MINOR.PATCH[-prerelease]`. `npm pack` tolerates it but
  `npm publish` / `npm install` semver resolution will not. Map the Maven version
  to a valid npm version at pack time, e.g. `1.0.2` for releases and
  `1.0.2-SNAPSHOT.<build>` (or `-0`) for snapshots. Easiest: drive it from Maven
  with `maven-resources-plugin` filtering into `package.json`, or an `npm version`
  step, so the npm version is derived from `${project.version}` but normalised.

### 7.2 Registry configuration

Add a project-level `.npmrc` (per module, or one at the clients parent consumed via
`--userconfig`) pointing at Nexus and carrying the auth token from CI secrets:

```
@geboai:registry=https://nexus.example.com/repository/npm-hosted/
//nexus.example.com/repository/npm-hosted/:_authToken=${NPM_TOKEN}
```

Optionally set `"publishConfig": { "registry": "https://nexus.../npm-hosted/" }`
inside each library `package.json` so `npm publish` always targets Nexus.

### 7.3 The Maven `publish-npm` profile (per-module, one by one)

Reuse the `frontend-maven-plugin` already configured in every angular client and add
an extra `npm` execution bound to Maven's `deploy` phase, gated by a profile:

```xml
<profile>
  <id>publish-npm</id>
  <build><plugins>
    <plugin>
      <groupId>com.github.eirslett</groupId>
      <artifactId>frontend-maven-plugin</artifactId>
      <executions>
        <execution>
          <id>npm publish library</id>
          <phase>deploy</phase>            <!-- runs on `mvn deploy` -->
          <goals><goal>npm</goal></goals>
          <configuration>
            <!-- publish the packed tarball produced by build-lib -->
            <arguments>publish ./dist/gebo-<name>-api --access public</arguments>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins></build>
</profile>
```

Then:

```
mvn -P angular-ui,publish-npm deploy                    # publish ALL angular libs
mvn -P angular-ui,publish-npm -pl brain.gebo.ai.angular.client deploy   # ONE library
```

Because the profile lives in each module, the `-pl <module>` form publishes exactly
one library — satisfying "publish one by one". Bind to `deploy` (not `install`) so a
normal build never publishes by accident.

**Alternatives to the frontend-plugin execution:**
- `exec-maven-plugin` running `npm publish <tarball> --registry <nexus>` — simplest,
  uses the system npm instead of the plugin-managed node.
- A thin CI script looping the §1.1 names: `for n in ...; do npm publish
  <module>/dist/gebo-$n-api; done` — good when publishing is a release-pipeline
  concern rather than a per-build one.

### 7.4 Snapshot vs release

Nexus npm-hosted repos are typically **immutable** (no republish of the same
version). For iterative snapshots either enable "redeploy" on the Nexus repo, or
publish under a moving dist-tag with a unique prerelease version
(`1.0.2-SNAPSHOT.<timestamp>` + `npm publish --tag snapshot`). Releases go to a
separate hosted repo with clean `x.y.z` versions.

---

## 8. Suggested order of implementation

1. ~~Make the images serve the spec.~~ Done — `gebo.architecture.swagger` is now a
   `gebo.microservices.starter` dependency, so `mvn -P docker package` images serve
   `/v3/api-docs`. Just confirm once on a running container.
2. Add `dockers/gebo.microservices/docker-compose.regen.yml` publishing 8081–8096
   (§3 prereq 2) and `healthcheck`s so `docker compose up --wait` blocks on
   readiness.
3. Write the 6-stage `regen-microservices.ps1` driver (§4) — optionally packaged as
   a `package-run-regen-microservices` skill. Run it once, review the diff, commit
   the regenerated stubs with `auto-commit`.
4. Fix the npm **name** + **version** (§7.1), add `.npmrc` + `publish-npm` profile
   (§7.3), dry-run `npm publish --dry-run`, then wire it into the release pipeline.

_(Low-RAM / no-Docker fallback: implement Option B instead — an infra-only compose
plus a local `application.yml` mapping the compose hostnames to `localhost`, and a
sequential JVM sweep of the `bootable+swagger` jars.)_
