---
name: document-microservices-controllers
description: Build every Gebo microservice with swagger-on, stand up the full docker-compose stack, and regenerate docs/MICROSERVICES-CONTROLLERS.md from each service's live OpenAPI spec. Use whenever controller/module wiring changed under gebo.apps.parent/gebo.microservices.apps.parent, gebo.microservices.architecture.parent, or any module a microservice depends on, and the controllers doc needs to reflect it - or when asked to "regenerate the controllers doc", "check exposed controllers via swagger", or "repeat the document procedure".
user-invocable: true
allowed-tools:
  - Read
  - Edit
  - Glob
  - Grep
  - Bash
---

# /document-microservices-controllers — Rebuild, redeploy, and document every microservice's controllers

Produces `docs/MICROSERVICES-CONTROLLERS.md`: a live, per-service map of every
`@RestController` (grouped by springdoc tag) reachable on the microservices stack, plus
a summary table. It is generated, not hand-maintained — always regenerate it from a
freshly built and deployed stack rather than hand-editing stale entries.

Work through the steps in order. Each `mvn`/`docker` step in this procedure has already
bitten once in this codebase's history (see the gotchas inline) — don't skip straight to
"regenerate the doc" without actually rebuilding, or you'll document stale controllers.

## 0. Preconditions

- Docker daemon running (`docker version`).
- Check host memory before starting: `free -h`. Building 19 Spring Boot modules while
  ~20+ containers are already running from a previous stack can OOM-kill the Maven
  process outright (seen in this codebase — `mvn clean install` was silently `kill`ed
  mid-reactor with no error, just a `status: killed` on the background task). If
  `available` is under ~10Gi, run `docker compose -f dockers/gebo.microservices/docker-compose.yml down`
  **first** to free memory, then rebuild.

## 1. Root build

```bash
mvn clean install -DskipTests
```

Confirm `BUILD SUCCESS` before continuing — a red root build means the images you're
about to bake will contain last-known-good jars, not your changes, and you'll draw the
wrong conclusions from the doc.

## 2. Rebuild every microservice image — with `clean package`, not a bare goal

```bash
mvn -f gebo.apps.parent/gebo.microservices.apps.parent/pom.xml -P docker,swagger-on clean package jib:buildTar -DskipTests
```

**The `clean package` prefix is load-bearing — do not drop it.** `gebo.apps.parent/gebo.microservices.apps.parent`
is deliberately excluded from the root reactor (see its parent's `pom.xml` comment), so
invoking `jib:buildTar` as a bare goal does **not** trigger `resources:resources` or
`compile` for these modules — Jib just packages whatever is already sitting in each
module's `target/classes`, however stale. This was diagnosed the hard way: an
`application.yml` edit was invisible in three consecutive rebuilds because `jib:buildTar`
alone never re-ran the resources phase; adding `clean package` before it fixed it
immediately. `-P swagger-on` is equally load-bearing — without it, `gebo.architecture.swagger`
(springdoc) is off the classpath, `/v3/api-docs` serves nothing, and every service in the
doc below would show 0 controllers regardless of what's actually mapped.

Expect ~5 minutes for all 19 modules.

## 3. Load images and bring the stack up

```bash
for f in $(find gebo.apps.parent/gebo.microservices.apps.parent -name "jib-image.tar"); do
  docker load -i "$f"
done
cd dockers/gebo.microservices
docker compose down   # clears any stale/crash-looping containers from a prior run
docker compose up -d
cd -
```

## 4. Poll every service's api-docs URL — patiently

Two URL shapes exist depending on the checked-out branch:

- **No context-path** (most branches): `http://localhost:<port>/v3/api-docs`
- **Context-path branch** (`feature/microservices-contexts` and anything merged from
  it): `http://localhost:<port>/<short-name>/v3/api-docs` — e.g. `/brain/v3/api-docs`.
  Every backend's `server.servlet.context-path` is its short context name
  (`GeboMicroservice.getContextName()`, the Eureka discovery id minus `-gebo-ai`).

If unsure which shape applies, try root first and fall back to the prefixed form (the
doc-generation script in step 6 does this automatically — you don't need to pick by
hand, just be aware of it if you're spot-checking with `curl` yourself).

Port → service map (from `dockers/gebo.microservices/docker-compose.yml`):

```
13000 gateway     13007 uploads       13014 mcpclient
13001 brain       13008 userspace     13015 integration
13002 vectorizator13009 sharepoint    13016 fulltextor
13003 graphicator 13010 confluence    13017 eureka (registry, no api-docs)
13004 chunker     13011 jira          13018 heimdall
13005 git         13012 aws-s3
13006 filesystem  13013 googledrive
```

`gateway` and `eureka` never serve controllers — 0 paths is correct for both, not a
failure (gateway is a pure proxy with no `@RestController`; eureka is the registry, not
a `swagger-on` service). Poll only the other 17.

**Startup is slow under load, not stuck.** With 24 containers cold-starting on one host,
individual JVMs have taken 150–300+ seconds to finish `Started XApplication in N seconds`
in this codebase's dev environment — a short 60–90s polling loop reports every service
"NOT_READY" while they're still legitimately booting, not failing. Poll in a loop with an
8+ minute total budget:

```bash
ports="13001:brain 13002:vectorizator 13003:graphicator 13004:chunker 13005:git 13006:filesystem 13007:uploads 13008:userspace 13009:sharepoint 13010:confluence 13011:jira 13012:aws-s3 13013:googledrive 13014:mcpclient 13015:integration 13016:fulltextor 13018:heimdall"
declare -A ready
for tries in $(seq 1 50); do
  all_ready=1
  for pc in $ports; do
    p=${pc%%:*}; c=${pc##*:}
    if [ "${ready[$p]}" != "1" ]; then
      code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 6 http://localhost:$p/v3/api-docs)
      [ "$code" != "200" ] && code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 6 http://localhost:$p/$c/v3/api-docs)
      if [ "$code" == "200" ]; then ready[$p]=1; echo "READY $c"; else all_ready=0; fi
    fi
  done
  [ "$all_ready" == "1" ] && { echo "ALL_READY"; break; }
  sleep 10
done
```

If a service is still not ready after the full budget, check its actual state before
assuming it's hung — `docker compose logs <service> --tail=40`. Two failure modes seen
in this codebase, both look identical from the outside (connection refused / timeout)
but have opposite fixes:

- **Genuinely still starting** — log shows normal Spring Boot startup progressing
  (`Bootstrapping Spring Data MongoDB repositories`, `Tomcat initialized`, ...). Just
  keep waiting.
- **Crash-looping** — `docker compose ps` shows an uptime that keeps resetting to
  seconds/a couple minutes even though the stack has been up much longer; the log
  shows a startup exception repeating. Read the actual exception, don't just retry.
  Two concrete examples hit in this codebase:
  - `IllegalArgumentException: basePath must start with '/'` from a custom
    `PathPatternRequestMatcher.Builder` bean — Spring Security's `PathPatternRequestMatcher`
    already strips the servlet context path unconditionally before matching
    (`getPathContainer` in `PathPatternRequestMatcher.java`), so a hand-added
    context-path-scoped matcher bean is not just unnecessary but actively wrong (double
    strips). If you see this, the fix is to **remove** such a bean, not tune it.
  - `NoSuchMethodError: Schema.$dynamicRef()` — two different `io.swagger.core.v3`
    artifacts (`swagger-annotations` vs `swagger-annotations-jakarta`) define the same
    `io.swagger.v3.oas.annotations.*` classes; whichever the classpath happens to load
    first wins, and the older non-jakarta one (pulled transitively by vendor LLM SDKs
    like `com.openai:openai-java-core` / `com.anthropic:anthropic-java-core` via
    `spring-ai-openai` / `spring-ai-anthropic`) is missing methods newer springdoc
    calls. Fix: exclude `io.swagger.core.v3:swagger-annotations` on the offending
    `spring-ai-*` dependency in that provider's `pom.xml`.

## 5. Sanity-check a couple of specs before generating the doc

Cross-check that the specs actually reflect your change, not a stale cache — e.g. if you
moved a controller between modules, confirm it left the old service and appeared on the
new one:

```bash
curl -s http://localhost:13002/v3/api-docs | python3 -c "import json,sys; d=json.load(sys.stdin); print(len(d.get('paths',{})), 'paths')"
```

## 6. Regenerate the doc

```bash
python3 .claude/skills/document-microservices-controllers/scripts/generate_controllers_doc.py
```

Writes `docs/MICROSERVICES-CONTROLLERS.md`: a summary table (service/port/context-path if
applicable/controller count/endpoint count) followed by one section per service, grouped
by springdoc tag, with method/path/operationId tables. The script auto-detects the
no-context-path vs context-path URL shape per service (step 4) — you don't need to tell
it which branch you're on.

If this round's change has a story worth telling (a controller moved between modules, a
dependency was added/removed and changed what's exposed where, a bug was found and
fixed along the way), add a short prose note near the top of the generated file
explaining it — see the "LLM controllers-review note" / "Follow-up" paragraphs in the
current doc for the tone and level of detail expected. The generator script doesn't
write these for you; add them by hand after running it, in the same edit that reviews
the generated tables.

## 7. Shut down

```bash
cd dockers/gebo.microservices && docker compose down
```

Leave images loaded (`docker images | grep geboai`) unless asked to prune them — they're
cheap to keep and expensive to rebuild.

## Report

Summarize: root build result, which services' controller surface actually changed (with
before/after counts if you have them), any crash/gotcha hit and how it was fixed, and
confirmation the stack was torn down.
