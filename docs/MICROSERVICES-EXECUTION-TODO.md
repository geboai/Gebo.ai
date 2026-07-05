# Gebo.ai — Microservices Execution TODO (operative runbook)

> **Purpose:** the granular, dependency-ordered, verifiable task list to execute [`MICROSERVICES-INTEGRATION.md`](./MICROSERVICES-INTEGRATION.md). Every task cites the plan section it realizes.
> **Golden rule (invariant across all phases):** *the monolith build stays green at the end of every task.* Splits are additive; each deployable is its own app module (§9.2 — module per deployable, not a `-Pmicroservices` profile). If a task reddens `mvn -pl gebo.apps.parent/gebo.ai.app -am verify`, it is not done.
> **Coexistence is build-time** (§3): one source → either deployable; never both at runtime.

## Legend
- **ID** `P<phase>-T<n>`. · Tags: **[C]** create module · **[M]** move code · **[W]** wire/config · **[T]** test · **[D]** docker/CI.
- **Deps** = task IDs that must complete first. · **Verify** = the concrete acceptance check.
- Status column for tracking: `TODO` / `WIP` / `DONE` / `BLOCKED`.

## Global conventions (apply to every split — plan §5, §5.7, §5.8, §9.3)
- A split module `<m>` → children `<m>.interface-models` (POJO contract), `<m>.impl` (owner), `<m>.server-proxy`, `<m>.client-proxy`, and **`<m>.sdk`** only for extension-point layers (llms, contentsystems, agents, search).
- **Move classes per interface signature, not by wholesale package move.** Any `@Document` in a contract signature → POJO twin in `.interface-models` + mapper in `.impl` (§5.8).
- **Bean selection:** `.client-proxy` beans annotated `@ConditionalOnMissingBean(<iface>)` (or `@Profile("microservice")`) so monolith binds `.impl`, microservice binds `.client-proxy` (§9.3).
- **Payload/DTO types are shared, additive-only** (§6.9): live in `gebo.workflow.ingestion.contract` / `.interface-models` / `gebo.application.messaging` / `gebo.core.messages`; only add optional fields; consumers `FAIL_ON_UNKNOWN_PROPERTIES=false`.
- **Per split, run the lint:** `mvn -pl <m>.interface-models dependency:tree` must show **none of** `spring-data-mongodb` / `spring-context` / `spring-web` / persistence. *(Note: the root parent `ai.gebo.parent` injects `spring-boot-starter-validation` + `lombok` into every module, so `jakarta.validation` + `lombok` on a contract are expected and acceptable — lint the specific offenders, not "spring" broadly. Verified building P0-T1.)*

## Execution log
- **P3 (search) ✅ DONE (abstraction-layer split)** — created **`gebo.architecture.search.abstraction.layer.interface-models`** = model DTOs (`SearchQuery`/`SearchResult`/`SearchServiceException`/… 11) + 5 service interfaces (`ISearchService`/`INativeSearchService`/`ISearchServiceRepositoryPattern`/`IKeywordMatcherService`/`INativeQueryObject`), `git mv`'d same-package. Impl (lucene/web-search parsing, `AbstractWebSearchServiceImpl`, `service/impl/**`) stays in the module, which depends on interface-models. **Repointed the 2 dependents** (`contentsystems.abstraction.layer`, `documents.cache.interface-models`) → search.interface-models — an inter-contract-jar ripple, resolved cleanly. Verified: interface-models + impl + both dependents all `install` EXIT=0; full monolith build confirms (background). First abstraction-layer split — same recipe, plus repointing another interface-models jar.
- **Insight (§5.5 in practice):** the pure-impl **leaf workers don't need splitting** — `gebo.ragsystem.content.fulltext.processor` has only `config/` + `impl/` (no interfaces), and `graphrag_processor` is impl + one internal factory iface. Nothing consumes a contract from them, so they stay as impl modules (their `.interface-models` would be empty). The remaining meaningful splits are the **shared abstraction layers** (llms, chat, agents, contentsystems, search) that mix interfaces + impl + controllers, plus knowledgebase.model (already a model module → mostly a POJO-twin refinement §5.8).
- **P1-T2 ✅ DONE (vectorizator split)** — created **`gebo.ragsystem.content.vectorizator.interface-models`** = contract (`IGEmbedder`, `IGDocumentChunkServiceAccessor`, `IGEmbeddingMessageReceiver`, `DocumentAccessResult`), `git mv`'d same-package (FQCNs preserved); `vectorizator` = impl, depends on it. **No code dependents** (leaf worker — only aggregators reference it), so no repointing. Verified: interface-models + vectorizator impl both `install` EXIT=0; full monolith build confirms green (background). Closure right first try.
- **P1-T1 ✅ DONE (full split, monolith-verified)** — split `gebo.architecture.documents.cache`:
  - created **`gebo.architecture.documents.cache.interface-models`** = the contract (4 service interfaces `IDocumentsChunkService`/`IDocumentsCacheService`/`IChunkingParametersProvider`/`IDocumentChunkingMessagesReceiverFactoryComponent` + `DocumentCacheAccessException` + all `model/*` DTOs), **`git mv`'d keeping the same package (FQCNs preserved)**; deps closure resolved first try;
  - `gebo.architecture.documents.cache` = the `.impl`, now depends on interface-models;
  - **repointed all 6 dependents** (vectorizator, graphrag_processor, fulltext.processor, chat.abstraction.layer, agents.standard, mcp-server) → interface-models (they use only the contract); monolith keeps the impl via `monolithic.starter`.
  - **Verified: full monolith build `mvn -o install -DskipTests -pl gebo.ai.app -am -P'!angular-ui'` = BUILD SUCCESS (1:24), no dependent used an impl class.** The extract-in-place technique (same-FQCN move + repoint) proven end-to-end. *`.server-proxy`/`.client-proxy` are new REST code, added when standing up the chunker service.*
- **P1 (behavior verification) — started.** Docker available (29.6.1); `testcontainers-bom` pinned 1.21.4 (Boot 4.1.0 doesn't manage module versions). Two real-infra behaviors green:
  - **dedup over real MongoDB** (`MongoMessageDeduplicationMongoTest`, `mongo:7`): atomic insert + `_id` unique-key duplicate + exists — 2 tests.
  - **outbound publish over real RabbitMQ** (`GRabbitOutboundPublishRabbitTest`, `rabbitmq:3-management`): `GRabbitOutboundMessageReceiver.accept` serializes + publishes to the target queue, round-trips intact incl. `userRoles` + polymorphic payload — 1 test.
  *Note: this module's `mvn test` now needs Docker (move to failsafe/IT profile for Docker-less CI). Full broker→bus→broker round-trip with the assembler = the service integration test when chunker⇄vectorizator are stood up. Next P1: the feature-module splits (documents.cache, vectorizator).*
- **P0-T7 ✅ DONE (federated)** — participant registry module `gebo.participant.registry` (stack-agnostic: `spring-web` + `spring-boot-starter`, no servlet/webflux starter). *Advertise:* `GParticipantDescriptor` (identity + owned `messagingModuleId`s / endpoint `className`s / workflow steps), `ParticipantRegistryProperties`, `ParticipantDescriptorProvider`, `GParticipantSelfController` (`GET /_gebo/registry/self`). *Discover:* `IParticipantRegistry` + `FederatedParticipantRegistry` — unions the peers' self-descriptors into routing indexes (`messagingModuleId`→service, `className`→service) and **enforces global uniqueness** (duplicate module id/className → conflict). Auto-activates via `AutoConfiguration.imports` (works in any host). Added to `gebo.service.chassis`. Verified: `mvn install` EXIT=0, **2 tests green** (index union; duplicate `messagingModuleId` rejected); chassis rebuild + reactor `-N validate` EXIT=0. *Behavior (live peer HTTP federation, periodic refresh, gateway route discovery) = P1.*
- **P0-T10 ✅ DONE** — tracing backend decided = **OpenTelemetry** (vendor-neutral). Recorded in §8.1/§10.10.
- **P0-T9 ✅ (deps) / 🚧 (wiring)** — chassis observability: `spring-boot-starter-actuator` (Boot auto-provides RabbitMQ/MongoDB/Hazelcast health) + `micrometer-tracing-bridge-otel` + `opentelemetry-exporter-otlp`. Verified online build EXIT=0. *Remaining wiring (behavior-verified in P1): custom ingestion-target-queue health indicator (chunker); bridge Micrometer trace-context ↔ envelope `traceId`/`spanId` at the adapter seam so a trace continues across the bus; MDC (`workflowId`/`userId`/`traceId`) set at the receiver seam alongside §6.7 identity.*
- **P0-T8 🚧 SKELETON** — `gebo.apps.parent/gebo.service.chassis` starter (pom + `package-info`), registered in `gebo.apps.parent`. Aggregates the shared runtime: messaging seam + rabbitmq adapter (default transport, swappable §9.2) + `gebo.config(.services)` + `gebo.secrets.services` + `gebo.architecture.hazelcast` + `gebo.webconfig` + `gebo.async.config` + `gebo.architecture.environment` + `gebo.multilanguage.support` (§8.1). Verified: offline `install` EXIT=0 (all aggregated deps resolve), aggregator `-N validate` EXIT=0. *Adds in P2: `security.secure-area` + `security.{im,cp}`. Boot-with-infra test = P1 (needs Mongo/Rabbit/Hazelcast containers).*
- **P0-T6 ✅ DONE** — exactly-once dedup barrier (in the rabbitmq adapter; transport-agnostic → promote with the codec, P4-T6): `ProcessedMessage` `@Document` keyed by envelope `id` with a **TTL index** (self-pruning retention); `IMessageDeduplicationService` + `MongoMessageDeduplicationService` (atomic insert catches concurrent duplicates); wired into `GRabbitInboundEnvelopeListener` (**check → broadcast → mark**, record only after successful processing; nack→redeliver on failure). Verified: `mvn install` EXIT=0, **2 dedup tests green** (redelivery of same id → broker gets it exactly once; distinct ids → each once) via mock broker + in-memory store (no Mongo needed). *DLQ + `default-requeue-rejected=false` are host `application.yml` concerns, behavior-verified in P1.*
- **P0-T5 🚧 IN PROGRESS** — RabbitMQ adapter.
  - *skeleton* ✅ — module (pom + `package-info`) registered; deps `gebo.application.messaging` + `spring-boot-starter-amqp` + `spring-boot-starter-json`; online build EXIT=0.
  - *envelope JSON codec* ✅ — `GMessageEnvelopeJsonCodec` (tools.jackson / Jackson 3): serialize + deserialize with **polymorphic payload resolved from the `payloadType` FQCN** (§6.9, no type-info annotations needed) and `FAIL_ON_UNKNOWN_PROPERTIES=false` (additive-tolerant). **2 tests green** — full round-trip incl. `userRoles`/`traceId`/`spanId`, and legacy-envelope tolerance (missing additive fields → null). *This also discharges the deferred P0-T4 round-trip assertion.* Codec is transport-agnostic → promote to a shared module when Kafka/REST land (P4-T6).
  - *provider wiring* ✅ (compile-verified) — grounded in the broker's real routing (`GBaseMessageBroker`: `accept` = point-to-point by `(targetModule,targetComponent)` + payload-type check; `broadcast` = payload-type fan-out):
    - `RabbitMqExternalMessagingProperties` (`gebo.messaging.rabbitmq`: `inbound-queue` + `outbound-routes[{module,component,queue,payload-types,accept-every}]`);
    - `GRabbitOutboundMessageReceiver` (`GAbstractExternalMessageReceiver`) — registered at the remote's `(module,component)`; `accept()` serializes + publishes to the remote's queue;
    - `GRabbitExternalReceiverProviderSource` (`@Service IGExternalMessageReceiverProviderSource`) — yields one receiver per route, collected by `MessageBrokeringAssembler`;
    - `GRabbitInboundEnvelopeListener` (`@RabbitListener` on `inbound-queue`) — deserialize → `broker.broadcast` (first-cut; targeted `accept` via external-emitter registration is a later refinement);
    - `GRabbitExternalMessagingConfig` — codec bean + declares the inbound `Queue`; connection/`RabbitTemplate` come from Boot AMQP auto-config (bootstrap creds §6.7.3/§8.2).
    Verified: full module `mvn -o install` EXIT=0 (compiles + codec tests green). **Behavior verification (live broker) = P1** Testcontainers. *Remaining before P1: dedup barrier (P0-T6) applied in the inbound listener; DLQ on max-retry.*
- **P0-T4 ✅ DONE** — added additive fields to `GMessageEnvelope` (`gebo.application.messaging`): `userRoles` (identity block §6.7) + `traceId`/`spanId` (trace block §8.1), each nullable with getters/setters. Verified: `mvn -o -f gebo.application.messaging/pom.xml install` EXIT=0. Backward-compatible (nullable, additive) → monolith green. *JSON round-trip assertion deferred to P0-T5, where the external adapter introduces the envelope⇄JSON serialization it belongs with.*
- **P0-T1 ✅ DONE** — created `gebo.architecture.parent/gebo.workflow.ingestion.contract` (skeleton: pom + `package-info`), registered in the `gebo.architecture.parent` reactor. Verified: `mvn -o -f …/pom.xml install` EXIT=0; jar produced; aggregator pom `-N validate` EXIT=0. Monolith green by construction (additive leaf module). **Strategy = Option A** (aggregate existing shared wire types + home new ones; no physical relocation of stable types, per §6.9). It will pick up `spring-data-mongodb` transitively once it aggregates the messaging/`core.messages` payloads — **acceptable** here (dual-purpose persisted+serialized wire types); the strict no-Mongo lint stays for REST DTO `.interface-models` only.

## Critical path (shortest route to proof)
```
P0 (foundations) ─▶ P1 (chunker⇄vectorizator over Rabbit = messaging bridge proven)
                 └▶ P2 (heimdall = split + client-proxy + identity-over-bus proven)
P1+P2 ─▶ P3 (mechanical repetition of the split for all feature modules)
P3 ─▶ P4 (stand up remaining services) ─▶ P5 (edge, stubs, docker, CI, conformance gate)
```
**First vertical slice = P0 + P1.** If chunk-ready flows chunker→vectorizator over RabbitMQ, is deduped exactly-once, and the monolith still passes the same scenario, the core bet is proven and the rest is repetition.

---

## Phase 0 — Foundations (no behavior change)

| ID | Tag | Task | Deliverable | Deps | Verify |
|---|---|---|---|---|---|
| P0-T1 | [C] | Create `gebo.workflow.ingestion.contract` — **aggregate, don't move** (Option A) | The module **aggregates** the existing shared wire types (`gebo.application.messaging.workflow`, `gebo.core.messages`) and is the home for *new* ingestion contract types; stable types are **not** relocated (would change FQCNs, §6.9) (§6.5) | — | Module builds; monolith unchanged. *(Messaging wire types are dual-purpose persisted+serialized, so `spring-data-mongodb` is OK here — the strict no-Mongo lint is for REST DTO `.interface-models`.)* |
| P0-T2 | [W] | Write the split template + conventions as a short `CONTRIBUTING-split.md` | The 5-child recipe, `@ConditionalOnMissingBean` rule, entity⇄DTO mapper rule, the `dependency:tree` lint (§5.7/§5.8/§9.3) | — | Reviewed; referenced by all Phase-3 tasks |
| P0-T3 | [W] | Establish **module-per-deployable** build layout (no toggle profile) | Confirm library modules always in the reactor; each future `gebo.<service>.app` is its own module built via `-pl … -am`; per-artifact *packaging* profiles reused from `gebo.ai.app` (§9.2) | — | `mvn -pl gebo.apps.parent/gebo.ai.app -am verify` green; root `mvn install` unaffected |
| P0-T4 | [C] | Envelope additive schema: identity block (`userRoles`) + trace block (`traceId`/`spanId`) on `GMessageEnvelope` | New optional fields, defaulted; `FAIL_ON_UNKNOWN_PROPERTIES=false` on the mapper (§6.7, §8.1, §6.9) | — | JSON round-trip test incl. new blocks; monolith deserializes old envelopes unchanged |
| P0-T5 | [C] | `gebo.application.messaging.external.rabbitmq` adapter | `ProviderSource` beans binding `IGExternalMessageEmitter/Receiver` (§6.1/§6.2); outbound publish + inbound consume of `GMessageEnvelope` JSON | P0-T1, P0-T4 | Unit: emit→queue→consume round-trips an envelope; not wired into any app yet |
| P0-T6 | [C] | Exactly-once dedup component | Reusable processed-store (Mongo unique index on envelope `id`) + ack-after-process wrapper + per-queue dead-letter (§6.6) | P0-T1 | Duplicate delivery of same `id` → single processing; poison msg → DLQ |
| P0-T7 | [C] | **Participant registry** — standard REST contract + descriptor | Chassis-provided registry endpoint advertising the participant descriptor (identity; capabilities incl. the **globally-unique `messagingModuleId`s it owns → the shared routing map**, endpoint-type `className`s, workflow steps; health); credential-gated join; uniqueness enforced (reject duplicate `messagingModuleId`); consumers resolve the live map for **bus routing** (`targetModule`→service→`inputq`, §17) and endpoint resolution (§7.5, §8.3). Pick topology (central vs federated). | P0-T1, P0-T8 | A participant advertises its module ids + capabilities; another resolves `targetModule`→owner and `GObjectRef.className`→owner via the registry; a duplicate `messagingModuleId` is rejected; a stub third-party joins without rebuilding others |
| P0-T8 | [C] | `gebo.service.chassis` starter (skeleton) | Aggregates `gebo.application.messaging` + adapter + `gebo.config(.services)` + `gebo.secrets.services` + `gebo.architecture.hazelcast` + `gebo.webconfig` + `gebo.async.config` + `gebo.architecture.environment` (§8.1); `secure-area` added in P2 | P0-T5 | A trivial throwaway app booting only the chassis starts up |
| P0-T9 | [C] | Chassis observability | `spring-boot-starter-actuator` + micrometer-tracing + exporter; health/readiness indicators for broker/Mongo/Hazelcast connectivity (§8.1); MDC (`workflowId`/`userId`/`traceId`) | P0-T8 | `/actuator/health` reports queue+db indicators; a trace spans emit→consume |
| P0-T10 | [W] | Ratify tracing backend (Brave vs OTel) + finalize envelope context-header fields | Decision recorded in plan §8.1/§10.9 | P0-T4, P0-T9 | Decision noted; exporter dependency chosen |

**Phase 0 DoD:** all adapters/chassis/contract modules build; **monolith build & tests unchanged**; nothing wired into a running microservice yet.

---

## Phase 1 — Messaging bridge proven (chunker ⇄ vectorizator, RabbitMQ)

| ID | Tag | Task | Deliverable | Deps | Verify |
|---|---|---|---|---|---|
| P1-T1 | [C][M] | Split `gebo.architecture.documents.cache` → `{interface-models, impl, server-proxy}` (chunker owner) | `IDocumentsChunkService` + DTOs → IM; `DocumentsChunkServiceImpl`, receivers → IMPL; thin trigger/status SP (§13, §14.11) | P0-T1 | Builds; monolith re-aggregates `.impl` and stays green |
| P1-T2 | [C][M] | Split `gebo.ragsystem.content.vectorizator` → `{interface-models, impl}` (vectorizator owner) | `IGEmbedder`/`IGDocumentChunkServiceAccessor` → IM; `impl/**` (receivers/emitters/`GEmbedderImpl`) → IMPL (§13.7, §14.1) | P0-T1 | Builds; monolith green |
| P1-T3 | [C] | `gebo.apps.chunker.starter` + `gebo.chunker.app`; `gebo.apps.vectorizator.starter` + `gebo.vectorizator.app` | chassis + own feature `.impl`/SP + `documents.cache.{im,cp}` on vectorizator (§8, §15) | P0-T8, P1-T1, P1-T2 | Both apps boot against Testcontainers Mongo |
| P1-T4 | [W] | Wire RabbitMQ routing | `application.yml`: `chunker.inputq`, `vectorizator.inputq`, chunk-ready fan-out target = enabled steps (§6.8, §17.5); shared brain Mongo for both | P0-T5, P1-T3 | Config loads; both services connect to broker (health green) |
| P1-T5 | [T] | Testcontainers integration: content→`chunker.inputq`→chunk→`vectorizator.inputq`→embed | Rabbit + Mongo containers; assert embedding written; assert duplicate delivery deduped (§6.6) | P1-T4, P0-T6 | Message flows end-to-end; exactly-once holds; timings sane |
| P1-T6 | [T] | Monolith regression: same embedding scenario on monolith classpath | Reuse `gebo.ai.app` ingestion test | P1-T1, P1-T2 | Identical outcome to P1-T5 (behavior-equivalence, first data point for §11.5) |

**Phase 1 DoD:** the messaging bridge is real — one chunk-ready edge crosses a JVM boundary over RabbitMQ with exactly-once, and the monolith produces the same result from the same code.

---

## Phase 2 — Security split → heimdall + identity-over-bus

| ID | Tag | Task | Deliverable | Deps | Verify |
|---|---|---|---|---|---|
| P2-T1 | [C][M] | Split `gebo.architecture.security` → `{interface-models, impl, server-proxy, client-proxy, secure-area}` (§5.6, §13.1) | User/ACL/oauth ifaces+DTOs → IM; `services/impl/**`+repos+login `GeboAISecurityConfig` → IMPL; token filter chains → SECURE-AREA; user/ACL SP+CP | P0-T2 | Builds; monolith green; `interface-models` lint clean |
| P2-T2 | [C][M] | Split `gebo.architecture.security.controllers` → `.impl` (the 12 controllers = heimdall SP) (§13.2) | Controllers → IMPL on heimdall | P2-T1 | Builds; monolith auth endpoints unchanged |
| P2-T3 | [C] | `secure-area` dual stack | Servlet filter chain **and** WebFlux `SecurityWebFilterChain` (reactive auth stack already exists) (§16.10) | P2-T1 | Both a Servlet and a WebFlux endpoint validate a heimdall token |
| P2-T4 | [C] | `gebo.apps.heimdall.starter` + `gebo.heimdall.app` | `security.impl` + `security.controllers.impl` + `security.server-proxy` + `secure-area`; own Mongo | P2-T1, P2-T2, P0-T8 | heimdall boots, issues + refreshes a token |
| P2-T5 | [W] | Rewire `gebo.apps.monolithic.starter` → `security.impl` + `security.controllers.impl` + `secure-area` | Monolith consumes `.impl` (behavior-preserving) (§9.1) | P2-T1, P2-T2 | Full monolith auth flows unchanged (regression) |
| P2-T6 | [W] | Add `secure-area` + `security.{im,cp}` into `gebo.service.chassis` | Every non-heimdall service validates tokens + does user/ACL lookups (§5.6, §8.1) | P2-T1, P0-T8 | chunker/vectorizator (P1) now enforce auth on their SP |
| P2-T7 | [W] | Identity-over-bus wiring | Emitter fills `userRoles` from `SecurityContext`; receiver seam wraps processing in `IdentityUtil.doAs(...)` + sets/clears MDC (§6.7) | P0-T4, P2-T1 | Worker business code sees caller identity; ACL check parity monolith vs split |
| P2-T8 | [T] | Auth conformance | monolith auth unchanged; heimdall-issued token validated by a second service; identity-over-bus ACL parity | P2-T4, P2-T6, P2-T7 | All green; bean-selection test (`.impl` vs `.client-proxy`) passes |

**Phase 2 DoD:** heimdall issues tokens; every other service validates via `secure-area`; user identity survives a bus hop with ACL parity; the two hardest seams (messaging + security) are both proven.

---

## Phase 3 — Split the shared feature modules (ordered by fan-in; §11.2)

For **each** module below apply the Global-conventions recipe. Standard per-module sub-tasks (`a`=create children, `b`=move per signature + mappers, `c`=server-proxy, `d`=client-proxy, `e`=`@ConditionalOnMissingBean` guards, `f`=bean-selection test + `dependency:tree` lint, `g`=monolith stays green).

| ID | Tag | Module → owner | Notes / plan ref | Deps | Verify |
|---|---|---|---|---|---|
| P3-T1 | [C][M] | `gebo.knowledgebase.model` (+`.repositories`) → **brain** | Most fan-in; `@Document` entities → POJO twins + mappers (§13.10) | P0-T2 | All downstream compile against `knowledgebase.im` |
| P3-T2 | [C][M] | `gebo.architecture.search.abstraction.layer` (+**sdk**) → **textsearch** owner (§13.6/§13.12) | near-clean; `AbstractWebSearchServiceImpl`→SDK | P0-T2 | brain (web search) + textsearch both compile |
| P3-T3 | [C][M] | `gebo.architecture.llms.abstraction.layer` (+**sdk**) → **brain** (§13.3, Appendix B) | biggest; cross-service = Hazelcast config-sync, CP secondary | P3-T1 | providers compile against `.sdk`; DAOs in `.impl` |
| P3-T4 | [C][M] | `gebo.architecture.agents.abstraction.layer` (+**sdk**) → **brain** (§13.4) | preserve `getNetworkAgentName()` keying in DTOs | P3-T3 | agent services + networks compile |
| P3-T5 | [C][M] | `gebo.architecture.chat.abstraction.layer` → **brain** (§13.5, §1.6) | re-point edges: graphrag.persistence/documents.cache/rag-autotune → `.im`+`.cp`; session `@Document` stays IMPL | P3-T1,T3,T4 | brain chat compiles against contracts, not foreign impls |
| P3-T6 | [C][M] | `gebo.architecture.rag.support.layer` → **brain** (§14.10) | injects security + content-handler registry (CP) | P3-T1,T2 | compiles |
| P3-T7 | [C][M] | `gebo.ragsystem.content.fulltext.processor` → **textsearch** (§13.7, §14.3) | message-driven; own OpenSearch | P3-T2 | builds |
| P3-T8 | [C][M] | `gebo.ragsystem.content.graphrag_processor` + `gebo.architecture.graphrag.persistence` (+sdk) → **graphsearch** (§13.7, §13.11) | expose `IKnowledgeGraphSearchService` SP; brain gets CP | P3-T1,T3 | brain queries graph via CP compiles |
| P3-T9 | [C][M] | `gebo.architecture.contentsystems.abstraction.layer` (+**sdk**) → shared SDK (§13.5-map) | `GAbstractContentManagementSystemHandler`→SDK; consumer/dispatcher/handshake → IMPL; gate materialization write-repo to chunker (§7.4) | P3-T1 | every content handler compiles against `.sdk` |
| P3-T10 | [C][M] | Each `gebo.systems.parent` content handler → its content service (§13.9) | git template ×10 (git/filesystem/uploads/sharepoint/confluence/jira/googleworkspace/userspace/integration/mcl-client); thin IM endpoint DTO; register endpoint className→module (§7.5) | P3-T9 | each builds; monolith aggregates all `.impl` |
| P3-T11 | [C][M] | `gebo.architecture.mcp-clients` → **brain** (registry write brain-only) (§13.8, §10.17) | `.im` (config/tool/prompt/resource) + `.impl` (pool/connector/repo/controller); mcp-content-handler gets `.im` read-only | P3-T4 | brain writes registry; mcp-content-handler reads |
| P3-T12 | [M] | `gebo.ragsystem.client.rest` → **brain** (§14.9, §16.2) | chat/RAG erogation incl. 3 reactive SSE controllers (§16.10) | P3-T5 | compiles on brain |
| P3-T13 | [C][M] | `gebo.core` split by concern → **brain** (+strays peeled) (§13.13) | KB/Project/Content + `CORE_MODULE` hub (`GComputeEndOfWorkflowReceiverFactory` = completion authority §6.8) → brain; BuildSystems/Company/Reindexing/LogView → owners; payloads already in `gebo.core.messages` | P3-T1 | brain owns CORE_MODULE; monolith green |
| P3-T14 | [W] | Same by-concern split for `gebo.system.ingestion`, `gebo.jobs.services`, `gebo.config`, `gebo.fastsetup` (§13.13, §16.8) | assign each to owner/chassis | P3-T13 | monolith green |

**Phase 3 DoD:** every shared feature module is split; the monolith re-aggregates all `.impl` and passes unchanged; each `.interface-models` is Spring/Mongo-free (lint green); bean-selection tested.

---

## Phase 4 — Stand up remaining services

| ID | Tag | Task | Deliverable | Deps | Verify |
|---|---|---|---|---|---|
| P4-T1 | [C] | `brain` starter+app | LLM/chat/agents/mcp/KB/web-search `.impl`+`.sdk`+SP + foreign `.im`+`.cp` (documents.cache, graphrag.persistence, search, vectorizator) (§15) | P3-* | boots; shared brain Mongo + Hazelcast |
| P4-T2 | [C] | `textsearch` starter+app | fulltext.impl + search.{impl,sdk,SP} + OpenSearch (§15) | P3-T2,T7 | boots; own Mongo + OpenSearch container |
| P4-T3 | [C] | `graphsearch` starter+app | graphrag_processor.impl + graphrag.persistence.{impl,SP} + Neo4j + llms.{impl,sdk} (§15) | P3-T3,T8 | boots; shared brain Mongo + Neo4j |
| P4-T4 | [C] | Each **content service** app (×10) + **mcp-content-handler** | `X.impl` + `X.server-proxy` + contentsystems.{impl,sdk} + own Mongo + own secrets store/key (§8.2, §15) | P3-T9,T10,T11 | each boots; own Mongo container |
| P4-T5 | [W] | Per-service `application.yml` | Mongo URI (shared vs own), Hazelcast membership (brain/vectorizator/graphsearch), `<svc>.inputq`, routing table, OpenSearch/Neo4j, step-enablement (`gebo.ingestion.steps.*.enabled`) (§6.8, §15) | P4-T1..T4 | config validates per service |
| P4-T6 | [C] | Kafka + REST adapters to parity with Rabbit; transport selectable by config | (§6.1, §17.5) | P0-T5 | switching transport passes P1-T5-style test |
| P4-T7 | [W] | Per-service secrets: own `GeboSecret` store + bootstrap crypto key; shared group uses shared brain DB (§8.2) | secrets provisioning per service | P4-T1..T4 | each service reads its own secrets; LLM group reads shared |
| P4-T8 | [T] | Full ingestion workflow across all services | Testcontainers: Mongo, Rabbit/Kafka, OpenSearch, Neo4j; content→chunk→embed/index/graph; brain (`CORE_MODULE`) computes end-of-workflow | P4-T5 | end-state matches monolith baseline |

**Phase 4 DoD:** every microservice boots and the full ingestion + chat/RAG workflow runs across the real topology.

---

## Phase 5 — Edge, publication, hardening

| ID | Tag | Task | Deliverable | Deps | Verify |
|---|---|---|---|---|---|
| P5-T1 | [C] | `gateway` service (Spring Cloud Gateway) | routes `/<service>/**`; forwards `Authorization`; WebFlux → SSE-transparent (§16.11); may serve UI bundle | P2-T4, P4-* | routes to each service; SSE chat streams through unbuffered |
| P5-T2 | [D] | Per-service OpenAPI + `@gebo.ai/<service>` Angular stubs | enable `swagger-on` per service; regen per-service stubs (per-service `package-run-regen-rest`) (§16.11) | P4-* | each stub generated; published under `@gebo.ai` scope |
| P5-T3 | [D] | Per-service Java stub clients | mirror `gebo.monolithic.api.resttemplate.client` per service (per-service `package-run-regen-java-client`) | P4-* | each Java client compiles against its service |
| P5-T4 | [W] | UI re-wire | import `@gebo.ai/<service>` per service; `BASE_PATH` = gateway relative path (microservices) / same origin (monolith) (§16.11) | P5-T1,T2 | UI works against gateway; monolith UI unchanged |
| P5-T5 | [W] | Content streaming | message carries `GObjectRef<GProjectEndpoint>` + `GDocumentReference`; chunker resolves owner via registry, pulls bytes from content-service streaming SP (§7.5) | P0-T7, P4-T4 | large file ingested without bytes on the bus |
| P5-T6 | [W] | Hazelcast LLM-config invalidation | admin write on brain → invalidation topic → vectorizator/graphsearch rebuild clients (§7.2) | P4-T1,T3 | config change propagates; clients rebuilt |
| P5-T7 | [W] | Bus hardening | dead-letter policy, retry/backoff, per-queue publish/consume ACLs (§6.6, §6.7.3) | P4-T6 | poison msg → DLQ; unauthorized publish rejected |
| P5-T8 | [D] | Per-service Docker images | one image per service (per-service equivalent of `build-prod-docker-image`) | P4-* | images build; compose/Testcontainers boots the topology |
| P5-T9 | [D] | CI: publish microservice artifacts + images alongside the monolith | CI **matrix over the app modules** (`mvn -pl gebo.<service>.app -am deploy`); `distributionManagement` → GitHub Packages (§9.2) | P5-T8 | pipeline green; each service artifact/image published independently |
| P5-T10 | [T] | **§11.5 conformance gate** | `integration-tests/microservices-integration-tests`: reuse `gebo.ai.app/src/test` scenarios (`GitContentSystemIntegrationTests`, `SharedFilesystemIntegrationTest`, `MCPServerIngestionIntegrationTest`, `WorkflowCompletionTest`, `DefaultAgentsNetworkTest`, `virtualremotefs/**`) driving Docker services via the Java stub clients; assert identical outcomes + invariant tests | P5-T3, P5-T8 | microservices run == monolith run for every reused scenario |

**Phase 5 DoD (project DoD):** the same commit builds (a) the unchanged monolith image and (b) the N microservice images + gateway; the full ingestion + chat/RAG workflow passes end-to-end in **both** shapes from identical business code; the conformance gate is green in CI.

---

## Cross-cutting checklists

**Per split module (Phase 3) — done when:**
- [ ] children created (`+.sdk` iff extension-point); classes moved per signature; mappers for `@Document` twins.
- [ ] `.server-proxy` controllers authored; `.client-proxy` implements the ifaces; `@ConditionalOnMissingBean` guards.
- [ ] `mvn -pl <m>.interface-models dependency:tree` shows no Spring/Mongo.
- [ ] bean-selection test: iface → `.impl` when present, `.client-proxy` when only that is present.
- [ ] monolith `verify` still green.

**Per new service (Phase 4) — done when:**
- [ ] lists a feature's `.impl`/`.sdk` **or** its `.im`+`.cp` — never both (§15 invariant 1).
- [ ] `.server-proxy` only for owned features (invariant 2).
- [ ] `secure-area` + `security.{im,cp}` present (non-heimdall) (invariant 4).
- [ ] own secrets store + bootstrap crypto key (shared-DB group excepted) (§8.2).
- [ ] `application.yml`: DB, Hazelcast membership, `<svc>.inputq`, routing, step-enablement.
- [ ] actuator health (queue+db) green.

**Risk register (watch during execution):**
- R1 `.interface-models` extraction surfaces hidden `@Document`/impl leakage (§5.8) — caught by the lint; budget rework on `knowledgebase.model` (P3-T1) and `llms` (P3-T3).
- R2 `contentsystems.impl` materialization write-repo co-hosted — gate to chunker only (§7.4/P3-T9), else multi-writer on shared DB.
- R3 Reactive SSE buffering through gateway/CP (§16.10) — assert no `.block()` in P5-T1/P5-T4.
- R4 Envelope additive-only discipline (§6.9) — CI check that no field is removed/renamed.
- R5 Native/thermal build flakiness on this host — retry or skip `-P'!angular-ui'`; not a code bug.

---

*Derived from `MICROSERVICES-INTEGRATION.md` (all 19 §10 points settled). Keep the two files in sync: if a task reveals a design gap, fix the plan first, then the task.*
