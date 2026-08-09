# Microservices client stubs — regeneration & publishing architecture

> **This describes a procedure that has been run end to end.** Sections 1–6 are the
> working regeneration flow (images → live specs → clean → regen → compile). Section 7
> (publishing the Angular libraries to npm/Nexus) is still a *proposal* — nothing there
> is wired up yet.

---

## 1. Current state

`gebo.api.clients/gebo.microservices.clients.parent` holds **two client modules per
microservice** — a Java `resttemplate` client and an Angular/TypeScript library:

```
<name>.gebo.ai.java.client       -> gebo.microservices.api.client.<name>.{api,model,invoker}
<name>.gebo.ai.angular.client    -> projects/gebo-<name>-api/src/lib  (ng-packagr library)
```

Each microservice **app** lives under
`gebo.apps.parent/gebo.microservices.apps.parent/<name>.gebo.ai`. There are **20
services** ↔ 20 Java clients ↔ 20 Angular clients.

### 1.1 Service ↔ port ↔ client map

Every client pom encodes the OpenAPI URL of its service in `swagger.file`. The ports
are the ones each app pins in its own `application.yml`, and
`dockers/gebo.microservices/docker-compose.yml` **publishes every one of them** — so
the `swagger.file` URLs resolve against a plain `docker compose up` with no overlay.

| Service (`<name>.gebo.ai`) | Port | Extra infra it needs |
|---|---|---|
| gateway      | 13000 | eureka |
| brain        | 13001 | eureka, rabbit, mongo, qdrant |
| vectorizator | 13002 | eureka, rabbit, mongo, qdrant |
| graphicator  | 13003 | eureka, rabbit, mongo, neo4j |
| chunker      | 13004 | eureka, rabbit, mongo |
| git          | 13005 | eureka, rabbit, mongo |
| filesystem   | 13006 | eureka, rabbit, mongo |
| uploads      | 13007 | eureka, rabbit, mongo |
| userspace    | 13008 | eureka, rabbit, mongo |
| sharepoint   | 13009 | eureka, rabbit, mongo |
| confluence   | 13010 | eureka, rabbit, mongo |
| jira         | 13011 | eureka, rabbit, mongo |
| aws-s3       | 13012 | eureka, rabbit, mongo |
| googledrive  | 13013 | eureka, rabbit, mongo |
| mcpclient    | 13014 | eureka, rabbit, mongo |
| integration  | 13015 | eureka, rabbit, mongo |
| fulltextor   | 13016 | eureka, rabbit, mongo, opensearch |
| eureka       | 13017 | (registry itself) |
| heimdall     | 13018 | eureka, mongo |
| tyr          | 13019 | eureka, rabbit, mongo |
| webdav       | 13020 | eureka, rabbit, mongo |

The ports are **not contiguous by theme** — `fulltextor` is 13016, not 13005, because it
was added after the original block was allocated. Read them from each app's
`application.yml`, never from the ordering of this table; every client pom's
`swagger.file` already agrees with it (verified for all 38).

**gateway and eureka expose no controllers of their own** (`paths: 0`). Their stubs are
scaffolding — an `ApiClient` and nothing to call. That is correct, not a failed
generation.

### 1.2 The regeneration profiles

Both client flavours carry a **`generate-rest-api`** Maven profile (bound to
`generate-sources`) driving `swagger-codegen-maven-plugin`, with `inputSpec` pointing
at the **live service**:

- **Java client** — `language=java, library=resttemplate`, custom templates at
  `gebo.api.clients/swagger-codegen-templates/java-resttemplate`, output to the module
  basedir (so `src/main/java`, `docs/`, `README.md`).
- **Angular client** — `language=typescript-angular`, custom templates, output to
  `projects/gebo-<name>-api/src/lib`.

The profile is **not active by default**: a normal build only compiles the committed
sources. Regeneration is always explicit.

---

## 2. The one rule that matters: **clean before you regenerate**

swagger-codegen only ever **writes** files. It never deletes one. So when an endpoint
or a model leaves a service's API, regenerating **on top of** the existing stubs leaves
the old files sitting there — compiling, exported from `index.ts`, indistinguishable
from live code. These are *phantom stubs*, and they are worse than a stale stub because
they look current.

This is not hypothetical. Splitting security out into `heimdall.gebo.ai` moved
`SecretsController` off every consumer's classpath. A regen-in-place left **823 phantom
files** across the 16 consumer clients — `secretsController.service.ts`,
`SecretWrapperGebo*`, `GeboSshKeySecretContent`, … — advertising a secrets API those
services no longer serve. Only a clean-then-regenerate removed them.

**What to delete** (everything the generator emits, and nothing else):

| Client | Delete | Keep — these are ours, not the generator's |
|---|---|---|
| Java    | `src/`, `docs/`, `README.md`, `.swagger-codegen/` | `pom.xml`, `.gitignore`, `.swagger-codegen-ignore` |
| Angular | inside `projects/<lib>/src/lib/`: `api/`, `model/`, `*.ts`, `.swagger-codegen/` | `src/public-api.ts`, the project scaffolding (`ng-package.json`, `package.json`, `tsconfig.*`), and `lib/.gitignore` + `lib/.swagger-codegen-ignore` |

Deleting `.swagger-codegen-ignore` would make the generator re-emit the Gradle/SBT/Travis
scaffold (and a binary Gradle wrapper jar) that we deliberately stopped tracking.
`src/public-api.ts` sits *outside* `lib/`, is hand-written, and only re-exports
`./lib/index` — the generator cannot touch it and it needs no post-patch.

After regenerating, `git status` is the audit: every file still showing as **deleted**
is a phantom that is genuinely gone from the API. Read that list before committing — it
is the diff that tells you what the split actually removed.

---

## 3. Booting the services to get their specs

Two prerequisites, and one of them is easy to get wrong:

1. **`/v3/api-docs` requires the `swagger-on` profile.** `gebo.architecture.swagger`
   is a dependency of `gebo.microservices.starter` **inside a `swagger-on` profile that
   is disabled by default** — so a default image serves **no spec at all** and regen
   would silently pull nothing. Images used for regeneration **must** be built with
   `-P docker,swagger-on`. (This is deliberate: production images do not expose an API
   catalogue unless asked to.)

2. **Everything else is already wired.** The compose file publishes every port, sets
   `GEBO_HOME`/`GEBO_WORK_DIRECTORY` in the parent containerization directive, and
   brings up mongo, rabbit, qdrant, neo4j, opensearch, eureka and the gateway. There is
   no `docker-compose.regen.yml` and none is needed.

### Cluster endpoints never appear in a spec — by design

The service-to-service surfaces (`api/cluster/SecretsController`,
`.../SecurityController`, `.../AclController`) are declared with `@ResponseBody` +
`@RequestMapping`, **not** `@RestController`. Two consequences, both wanted: the
`ai.gebo` component scan cannot publish them without their participants guard, and
**springdoc does not document them** — so they can never leak into a generated stub and
out to a browser. If a `/api/cluster/**` path ever shows up in a spec, that is a bug in
the controller's annotations, not in the generator.

---

## 4. The procedure

```powershell
# 1. Build the images WITH the spec (swagger-on is not optional here)
mvn -f gebo.apps.parent/gebo.microservices.apps.parent/pom.xml -P docker,swagger-on jib:buildTar -DskipTests
docker load -i <each>/target/jib-image.tar

# 2. Up the stack; every port 13000-13018 is published already
docker compose -f dockers/gebo.microservices/docker-compose.yml up -d

# 3. Wait until every http://localhost:130xx/v3/api-docs answers 200
#    (a service that is "running" is not yet a service that is serving)

# 4. CLEAN the stubs (see §2) - regen alone leaves phantoms

# 5. Regenerate all 38 modules from the live specs
mvn -f gebo.api.clients/gebo.microservices.clients.parent/pom.xml generate-sources -P generate-rest-api

# 6. Compile: Java clients + ng-packagr/npm-pack for the Angular libs
mvn -f gebo.api.clients/gebo.microservices.clients.parent/pom.xml clean install

# 7. Review `git status` - the remaining deletions are the phantoms (§2) - then commit
docker compose -f dockers/gebo.microservices/docker-compose.yml down
```

Worth automating as a `package-run-regen-microservices` skill; the six stages above are
the contract it has to honour, with **stage 4 the one that is easy to forget**.

---

## 5. Committing the regenerated sources

The generated sources **are tracked** and must be committed; build artifacts are not
(handled by `.gitignore` — `*.tgz`, `node/`, `dist/`, `node_modules/`):

- **Java clients** — regen writes `src/main/java`, `docs/`, `README.md`. Verified by
  `mvn install` on the parent.
- **Angular clients** — regen writes `projects/gebo-<name>-api/src/lib`. Verified by
  `mvn install` (ng-packagr runs `tsc`, so a type error fails the build).
- Commit with the `auto-commit` skill (no Claude signature).

---

## 6. Known gotchas

1. **Regen without a clean leaves phantom stubs.** The whole of §2. This is the one that
   silently ships a lie.
2. **A default image serves no spec.** `swagger-on` is disabled by default (§3.1); build
   regen images with `-P docker,swagger-on` or you will regenerate against nothing.
3. **`encoder.ts` needs no post-patch.** swagger-codegen's stock `typescript-angular`
   template emits `CustomHttpUrlEncodingCodec.encodeKey/encodeValue` *without* the
   `override` keyword, which is a hard error under this project's
   `noImplicitOverride: true`. That is fixed **in our custom template**, so the
   generator now emits correct code in the first place. Do not re-introduce a
   post-regen patch step.
4. **`jib:dockerBuild` hangs on Docker 29.** Use `jib:buildTar` + `docker load`.
5. **Native crash exit codes** (`-1073741819` / `0xC0000005`) during a Maven build are
   known local hardware flakiness, not a regression — just re-run.
6. **"Running" ≠ "serving".** Compose `depends_on` waits for container *start*. Poll
   `/v3/api-docs` until it answers or the regen races the services and pulls empty specs.

---

## 7. Publishing the Angular libraries to npm / Nexus (one by one)

> ⚠️ **Proposal — not implemented.** Everything above this line has been run; nothing
> below it has.

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

## 8. Remaining work

1. Package §4 as a `package-run-regen-microservices` skill (six stages, stage 4 —
   the clean — being the one a human forgets).
2. Add compose `healthcheck`s so `docker compose up --wait` blocks on *readiness*
   rather than start (§6.6), which lets the driver drop its polling loop.
3. Fix the npm **name** + **version** (§7.1), add `.npmrc` + `publish-npm` profile
   (§7.3), dry-run `npm publish --dry-run`, then wire it into the release pipeline.
