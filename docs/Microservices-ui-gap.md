# Microservices ↔ UI — gap log

What actually broke, or nearly broke, while moving the Angular UI off the
monolithic REST stub and onto the 21 generated per-microservice clients. Each
entry says how it was found, what the evidence was, and whether it is closed.

This is a record of *gaps between the monolith and the cluster*, not a migration
plan — `MICROSERVICES-UI-MIGRATION.md` is the plan, and where the two disagree
this file is the newer one.

Status legend: **FIXED** (merged), **DONE-UNMERGED** (committed, not merged),
**OPEN** (decided, not written), **UNDECIDED** (needs a call).

---

## 0. The one mechanism everything depends on

`gebo.api.clients/gebo.microservices.clients.parent/gebo.microservices.clients.angular.module`
(`@Gebo.ai/microservices-clients`) is what makes a single UI build serve both
deployment shapes. `MicroservicesClientsModule.forRoot({baseUrl})` imports all 21
generated `ApiModule`s and an app initializer reads
`GET <baseUrl>/public/ClientsTopologyProviderController`, handing each library the
web context that installation publishes for it.

Why the same build works against the monolith, verified in
`gebo-clients-topology.service.ts`: `monolithicFallback()` returns a single entry
with `relativeContextUrl: ''` under `GEBO_DEFAULT_SERVICE_ID`, and
`relativeContextUrlFor` applies that as the default. So `basePathFor(anyService)`
collapses to the bare base url. **Every gap below should be judged against that
rule: a change is correct only if it also degrades correctly on a monolith.**

Live check against the running cluster:

```
$ curl -s http://localhost:13000/public/ClientsTopologyProviderController
{"architectureType":"MICROSERVICES","services":[
  {"serviceId":"brain_gebo_ai","relativeContextUrl":"/brain"},
  {"serviceId":"heimdall_gebo_ai","relativeContextUrl":"/heimdall"},
  ... 20 entries ...
  {"serviceId":"gateway_gebo_ai","relativeContextUrl":""}]}
```

---

## 1. Controllers that exist per-service, and what that breaks

The deepest structural difference. A controller shipped by a shared starter is
**replicated into every microservice that includes that starter**, and each copy
answers only for the things that service owns. The monolith has exactly one copy
that sees everything.

Six service classes are replicated:

| Service class | In N stubs |
|---|---|
| `GeboAngularFormGroupMetaInfoControllerService` | 19 |
| `InternalMessagingTopologyControllerService` | 19 |
| `ContentsResetControllerService` | 13 |
| `DocumentContentStreamerControllerService` | 13 |
| `GenericalPublisherControllerService` | 13 |
| `JobLauncherControllerService` | 13 |

The method signatures are identical across copies (verified by hashing the
public method sets), so *which* copy you inject is semantically meaningful and
cannot be deduped: `ContentsResetControllerService` from `@Gebo.ai/jira` resets
Jira contents.

### 1.1 `GeboAngularFormGroupMetaInfoController` — three different problems
**FIXED (DONE-UNMERGED)**

The UI used it three ways, each of which breaks differently in a cluster.

**(a) `getFormGroupsMetaInfos()` — needed a union.** `GeboFormGroupsService`
called it once, cached the result statically, then looked entities up by
`entityName`. In a cluster each copy knows only its own entities, so pointing it
at one package silently gives partial metadata: editors for entities owned
elsewhere get nothing, with no error.

Resolved by removing the runtime call entirely. The controller reflected each
Java entity's getters (`GeboAngularFormGroupMetaInfoService`: `entityName =
type.getSimpleName()`, `className = type.getName()`, controls from `get*`
methods); the generated Angular DTO mirrors those same getters, so the answer is
knowable at compile time. 326 missing `FormControl`s were computed by diffing
each editor's `FormGroup` literal against its DTO and written into the literal.

> Without those controls the property is dropped on save — that is what the call
> was for, not cosmetics.

**(b) `className` came from the same call.** Easy to miss: the response also fed
`backendClassName`, used by `createBackendObjectReference()` and
`checkBackendProcessing()`. Removing the call without replacing it would have
broken backend object references. 59 editors now declare their entity's Java FQCN
statically.

**(c) `checkDeletableBySimpleObjectRef` — a real runtime call.** Used by
`BaseEntityEditingComponentAutoDeleteCheck`, shared by 23 editors. Only the
service owning the entity can answer. Each editor now supplies its own client;
the base class declares the need as a structural interface so the library does
not pin itself to one package.

**(d) `logs-view` needed the union too**, to list project-endpoint types. It now
reads them from the pluggable-modules registry, which already carries the same
fully-qualified class names *and* is narrowed to the modules this installation
enabled — closer to the intent than the original call.

### 1.2 Orphan controllers with no client at all
**FIXED** (PR #241)

Four controllers the UI used had no generated client on any microservice, which
blocked ~14 files from leaving the monolith stub:

| Controller | Now served by |
|---|---|
| `GeboModulesConfigController` | tyr |
| `UITextResourcesController` | brain |
| `LanguageResourcesController` | brain |
| `GeboAngularFormGroupMetaInfoController` | all 19 |

`MICROSERVICES-UI-MIGRATION.md` §3.3 still lists these as **blocking**; that
section is stale.

> Casing trap: the class is `UITextResourcesController` (capital UI) but the
> generated Angular service is `UiTextResourcesControllerService`. Greps for one
> spelling find nothing.

---

## 2. Stale generated artifacts

### 2.1 The monolith stub's paged models are months out of date
**FIXED (DONE-UNMERGED)**

The monolith stub ships `PagedModel*` types dated `2026-06-29` ("adapted to
openapi 3.0 swagger & codegen"). The regenerated microservice stubs ship
`Page*`. The shapes differ:

```ts
// monolith stub — nested metadata
export interface PagedModelGUserChatInfo { content?: Array<GUserChatInfo>; page?: PageMetadata; }

// microservice stub — Spring Data Page, flat
export interface PageGUserChatInfo { totalPages?: number; totalElements?: number; content?: ...; }
```

**`PagedModel` appears nowhere in the Java sources** — only in generated clients.
The controllers return `Page<T>` (e.g. `GeboUserChatsController.getMyChatsPaged`).
So the monolith stub had drifted from its own backend, and the UI's paged
metadata was already wrong against the current monolith. Migrating fixed a latent
bug rather than introducing one.

Impact was small because almost every consumer reads `.content`, which both
shapes have. Six places read the nested metadata (`?.page?.totalElements`), one
in TypeScript and five in templates — all flattened to `?.totalElements`.

> Trap: the rename must be applied at use sites and **in `.html` templates**, not
> just in imports. Type-checking templates is what caught the last five.

### 2.2 The installed `.tgz` packages lagged the regenerated sources
**FIXED (DONE-UNMERGED)**

`gebo.ui` consumes the 22 clients as `file:dependencies/*.tgz`, not as workspace
projects. After `chore(stubs): regenerate all microservices client stubs`
(`64300f65a`) the **sources** had the new controllers but the committed tarballs
did not, so the build failed with:

```
Module '"@Gebo.ai/tyr"' has no exported member 'GeboModulesConfigControllerService'
Module '"@Gebo.ai/brain"' has no exported member 'UiTextResourcesControllerService'
```

This is a standing hazard: **regenerating stub sources is not enough**, you must
also run `gebo.ui/build-local-clients.sh` and commit the rebuilt tarballs.

> Lock hazard: the versions are Maven-style (`1.0.4.0-SNAPSHOT`) and not valid
> semver, so a full `package-lock.json` regeneration fails with
> `Invalid Version: 1.0.4.0-SNAPSHOT`. The lock's `integrity` hashes must be
> patched in place (recompute sha512 per tarball) — 19 entries needed it.

---

## 3. Addressing: the single `BASE_PATH` token is gone

**FIXED (DONE-UNMERGED)**

Each generated library declares its **own** `BASE_PATH` `InjectionToken`. They
share the description string `'basePath'` but are 21 distinct object identities —
which is what lets them coexist. Importing the wrong one silently configures the
wrong service.

Anything that built a url by hand had to say which service serves it. 16 files
injected the monolith token; they split three ways:

| Kind | Count | Resolution |
|---|---|---|
| Never actually read it | 5 | parameter removed |
| Installation-wide | 1 | `topology.baseUrl` |
| Service-specific | 9 | `topology.basePathFor(<owner>)` |

Ownership as resolved, from the controller each url addresses: **brain** for
chat/SSE, content viewer, LLM-generated resources and MCP exports; **heimdall**
for Spring Security's `/oauth2/**` and `/login/oauth2/**`; **uploads** for the
uploads browser; **userspace** for the upload target.

`GeboAIBuildUrlService.buildUrl` now takes the service id as its first argument
rather than silently assuming one address.

`GeboBackendListService` — the auth interceptor's matcher — keeps the plain
`baseUrl`: its test is `url.startsWith(x)` and every service answers below that
url, so one entry still covers all 21.

> **Trap that bit us:** "unused injection" cannot be decided from `.ts` alone.
> Two of the five apparently-dead injections were live — one used only in the
> component's `.html`, one through a bare (not `this.`-qualified) reference. Both
> were caught by the build, but only because Angular type-checks templates.

---

## 4. Runtime gaps found with the cluster up

### 4.1 Chunk metadata enrichment silently degrades
**OPEN**

1641 warnings in one chunker startup:

```
WARN  DocumentsChunkServiceImpl:292 - Could not resolve metadata enrichment context
      for document <...>; proceeding without it
  ai.gebo.architecture.persistence.GeboPersistenceException:
      Class not found:ai.gebo.uploads.content.handler.GUploadsProjectEndpoint
```

**Where the reference comes from:** not the message — from MongoDB. A
`GDocumentReference` carries a persisted `projectEndpointReference` (a
`GObjectRef` holding the FQCN *as a string*, written by the uploads service at
ingestion). `GPersistentObjectManagerImpl.findByReference` then does
`Class.forName(reference.getClassName())`. Handler-specific entity classes ship
only in their own service, so chunker cannot load it — deliberately, and the
catch block says so.

The failure is **handled**: chunking completes. But the metadata header is
prepended to each chunk's text *before embedding*
(`GAIDocumentCatalogingEnricherImpl.enrich`), so affected chunks are embedded
without their cataloging context. That is a retrieval-quality difference between
monolith and cluster, not an error.

**The avoidable part.** In `DocumentsChunkServiceImpl.getChunkSet` the `try`
spans all three lookups:

```java
GProject project        = ...findById(...);          // resolves
GKnowledgeBase kb       = ...findById(...);          // resolves
GProjectEndpoint endpoint = ...findByReference(...); // throws
metaDataHeader = createMetaDataHeader(..., kb, project, endpoint);  // never reached
```

So a failure on the endpoint alone discards the **whole** header, including the
project and knowledge-base lines that resolved fine. `createMetaDataHeader` is
fully null-tolerant (`if (endpoint != null)`, `if (knowledgeBase != null)`,
`if (project != null)`), so narrowing the `try` to the endpoint lookup would
recover four of the five header lines:

| Header line | Source | Lost today |
|---|---|---|
| `Category organization criteria:` | endpoint | genuinely unavailable |
| `Category:` | `reference.getRelativePath()` | unnecessarily |
| `knowledge base:` | knowledgeBase | unnecessarily |
| `project/item:` | project | unnecessarily |
| title / subtitle | chunk metadata | unnecessarily |

### 4.2 Raw JSON rendered into the chat
**OPEN** — pre-existing, made constant by 4.3

`GeboAIBaseStreamingService.internalStreamChat` checks only `response.body`,
never `response.ok`. Any non-2xx body is read as if it were an SSE stream, and
when `JSON.parse` fails on a line the fallback emits the **raw string** to the
chat:

```ts
} catch (e) {
    if (json) { onMessage(json); }   // raw text straight into the UI
}
```

Two independent producers observed:

- a `401` body once the token expires (see 4.3);
- `com.openai.errors.OpenAIIoException: Stream failed` from the LLM provider,
  which also surfaces as `MessageAggregator - Aggregation Error` and
  `Agent ReportWriterNetworkAgentService failed; continuing network`.

Not introduced by the UI migration — commit `d6d89820c` ("try fixing: ui shows
raw json in the chat", May) already touched this file.

### 4.3 The cluster's JWT lives 5 minutes, the monolith's 30
**UNDECIDED**

```
ExpiredJwtException: JWT expired at 2026-09-24T10:37:03Z. Current time: 10:42:58Z
```

| Config | `tokenExpirationMsec` |
|---|---|
| `dockers/gebo.ai/config/application.yml:35` (monolith) | 1800000 — 30 min |
| `dockers/gebo.microservices/config/application.yml:177` | **300000 — 5 min** |
| `deploy/helm/gebo-microservices/templates/configmap-shared.yaml:60` | 300000 — 5 min |

So in the cluster a session dies after five minutes and every later call 401s —
which, through 4.2, is what puts JSON on screen.

**Needs a decision, not just a bump:** heimdall ships `TokenRenewController`. If
the UI were meant to renew, a short TTL is correct and the real defect is the
missing refresh. Whether the UI ever calls renew has not been checked.

### 4.4 Gateway routing
**FIXED**

The gateway originally routed only `/heimdall/api/admin/**`, so the browser
surface 404'd; a `heimdall_browser` route was added for
`/heimdall/auth/**,/heimdall/public/**,/heimdall/api/users/**`, deliberately
excluding `/heimdall/api/cluster/**`.

The controllers added by PR #241 were a suspected second routing gap. Verified
against the running cluster — all reachable (`401`/`405` mean routed and
reached):

```
/tyr/api/users/GeboModulesConfigController/getAllModules                401
/tyr/api/admin/GeboAngularFormGroupMetaInfoController/...               401
/brain/public/UITextResourcesController/getUiTextResourcesModule        200
/heimdall/auth/login                                                    405
/uploads/api/admin/UploadsBrowsingController/serveUploadsEndpointFile   401
```

---

## 5. Build, generation and CI

### 5.1 operationId collisions
**FIXED**

Microservice deployables ship `api/cluster/*` controllers the monolith does not,
whose method names collide with the admin controllers. springdoc derives
operationId from the Java method name and de-duplicates with numeric suffixes
whose assignment depends on scan order — so the generated client method names
were unstable.

Fixed deterministically by renaming the Java methods
(`infrastructure<OriginalName>`, and `<method>A2AClientConfig` /
`<method>A2AServer` for the two A2A admin controllers) while leaving
`@…Mapping(value=…)` untouched, so urls and runtime callers are unaffected.

### 5.2 The rename missed `src/test`
**FIXED** (PR #242)

`gebo.microservices.secrets.controller` failed `testCompile` with 14
`cannot find symbol`, taking 6 downstream modules down as SKIPPED.
`SecretsClusterControllerCiphertextTest` still called the old names.

> Subtlety: the same names appear in that file as `case` labels of a `Proxy`
> `InvocationHandler` standing in for `IGeboSecretsAccessService`, and as a
> `staticDao` call. Neither was renamed, so "fixing" those would have turned a
> compile error into a silent `UnsupportedOperationException`.

### 5.3 CI never gated any of this
**OPEN**

`develop` was red from 2026-09-23 10:17 until PR #242. The cause was 5.2 — CI was
right. What failed is the gate:

| PR #241 checks started | 06:55:49Z |
|---|---|
| PR #241 **merged** | 06:57:06Z |
| checks cancelled (branch deleted) | 06:57:22Z |

The checks got **93 seconds** of the ~40 minutes a full build needs. Same pattern
on #240. They were not overridden — they were merged out from under.

Contributing: `.github/workflows/maven.yml` runs the full integration suite
(Neo4j, Mongo, embedding) on **every push to every branch** plus every PR, which
is what makes waiting impractical. It also pins **JDK 22** while the project
targets Java 21.

Suggested shape: a fast `mvn -B test-compile` job as the required check (it would
have caught 5.2 in minutes), with the full suite post-merge or nightly.

> Tooling note: `gh run view --log` truncates at ~25k lines and will hide the
> real `BUILD FAILURE`. Use `gh api .../actions/runs/<id>/logs` for the raw
> archive — the truncated view cost one wrong diagnosis here.

---

## 6. Naming and packaging traps

- **`@Gebo.ai/awss3`** has no hyphen, though the directory is
  `aws-s3.gebo.ai.angular.client` and the project is `gebo-aws-s3-api`.
- **Never `export *` from two stubs into one barrel.** All 21 export `ApiModule`,
  `Configuration`, `ConfigurationParameters`, `BASE_PATH`, `COLLECTION_FORMATS`,
  `CustomHttpUrlEncodingCodec` under those same names. The aggregator's
  `public-api.ts` states this rule explicitly and re-exports none of them.
- **Shared DTOs are copied per stub and are byte-identical**, so TypeScript's
  structural typing makes them interchangeable. Convenient, but it means a model
  imported from the "wrong" package compiles silently.
- `eureka`'s stub is intentionally empty (`APIS = []`); gateway exposes exactly
  one controller. Not failed generation.
- Regeneration needs each service **running** with `swagger-on`; the
  `generate-rest-api` profile is not active by default.

---

## 7. Still open

| # | Item | Kind |
|---|---|---|
| 4.1 | Narrow the `try` in `DocumentsChunkServiceImpl.getChunkSet` | backend Java |
| 4.2 | `response.ok` check in `base-streaming.service.ts` | UI |
| 4.3 | Cluster JWT TTL — bump, or make the UI renew | deployment config + decision |
| 5.3 | Split CI into a fast required gate + slow suite | CI |
| — | `MICROSERVICES-UI-MIGRATION.md` §3.3 and §9.1 are stale | docs |
| — | Runtime gate: no screen has been exercised against the cluster yet | testing |

The UI work itself compiles clean — all three libraries and the host app build
with no errors against the microservice stubs — but **compiling is not the same
as working**. Nothing here has been confirmed screen by screen against a running
cluster, and the per-service url ownership in §3 is the part most likely to be
wrong in a way only a runtime check reveals.
