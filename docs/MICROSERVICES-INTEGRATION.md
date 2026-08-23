# Gebo.ai — Microservices Integration Plan

> **Status:** Design / execution plan (living document)
> **Goal:** Add a **microservices artifact publication** to the existing project **without giving up the monolithic deploy**. One codebase, two deployment shapes.
> **Constraint (hard):** The monolithic and the microservices architectures MUST coexist and build from the same source tree. No fork, no divergent branch.

---

## 0. How to read this document

1. **§1–§3** establish the *as-is* facts this plan is anchored to (modules, messaging, data, security). These are extracted from the current reactor, not invented.
2. **§4–§9** are the *to-be* design: topology, the module-split pattern, the messaging bridge, data ownership, per-service composition, and how one codebase yields both deploys.
3. **§10** is the **gap analysis / open architectural points** — the decisions still owed before or during execution.
4. **§11** is the **refactoring TO-DO list**, phased and ordered, with the concrete "commands" (module creations, moves, wirings).
5. **§12** covers sequencing and risk.

Terminology used throughout:
- **`<module>`** — an existing Maven module that is *shared* between an implementing side and a consuming side (e.g. `gebo.architecture.llms.abstraction.layer`).
- **`.interface-models` / `.impl` / `.server-proxy` / `.client-proxy` / `.secure-area`** — the child modules produced by the split (§5).
- **Internal broker** — the in-JVM multithreaded message broker (`gebo.application.messaging`) present in every deployable, monolith or microservice.
- **External adapter** — a binding of `IGExternalMessageEmitter` / `IGExternalMessageReceiver` to a transport (RabbitMQ / Kafka / async REST).

---

## 1. As-is architecture (facts this plan relies on)

### 1.1 Reactor shape

Root: `ai.gebo:ai.gebo.parent:1.0.2.0-SNAPSHOT` (Spring Boot 4.1.0, Java 21). Aggregated module groups:

| Group POM | Contains (relevant subset) |
|---|---|
| `gebo.architecture.parent` | `gebo.application.messaging`, `gebo.architecture.security`, `gebo.architecture.security.controllers`, `gebo.architecture.llms.abstraction.layer`, `gebo.architecture.contentsystems.abstraction.layer`, `gebo.architecture.documents.cache`, `gebo.architecture.fulltext`, `gebo.architecture.opensearch`, `gebo.architecture.graphrag.extraction`, `gebo.architecture.graphrag.persistence`, `gebo.architecture.neo4j`, `gebo.architecture.rag-threasholds-autotune`, `gebo.architecture.rag.support.layer`, `gebo.architecture.hazelcast`, `gebo.architecture.agents.abstraction.layer`, `gebo.architecture.agents.standard`, `gebo.architecture.patterns`, `gebo.architecture.persistence`, `gebo.webconfig`, `gebo.secrets.services`, `gebo.restintegration.abstraction.layer`, `gebo.architecture.mcp-clients`, `gebo.architecture.mcp-server`, … |
| `gebo.core.parent` | `gebo.base.model`, `gebo.core`, `gebo.knowledgebase.model` (**GKnowledgeBase, GProject, GProjectEndpoint, GCentralizedProjectEndpoint, GDocumentReference, GVirtualFolder**), `gebo.knowledgebase.repositories`, `gebo.core.messages`, `gebo.core.contents.security` |
| `gebo.ragsystem.parent` | `gebo.ragsystem.content.vectorizator`, `gebo.ragsystem.content.graphrag_processor`, `gebo.ragsystem.content.fulltext.processor`, `gebo.ragsystem.client.rest`, `gebo.ragsystem.vectorstores`, `gebo.ragsystem.starter` |
| `gebo.systems.parent` | Content handlers: `gebo.git.content.handler`, `gebo.filesystem.content.handler`, `gebo.uploads.content.handler`, `gebo.sharepoint.handler`, `gebo.atlassian.confluence.handler`, `gebo.atlassian.jira.handler`, `gebo.googleworkspace.handlers`, `gebo.integration.content.handler`, `gebo.userspace.handler`, `gebo.mcl-client.content.handler`, plus search handlers `gebo.googlesearch.handler`, `gebo.bingsearch.handler`, and `gebo.contentsystems.starter` |
| `gebo.llms.parent` | Provider modules (`gebo.llms.openai`, `.anthropic3`, `.mistral`, `.ollama`, `.deepseek`, `.google_vertex`, `.generic-openai-compatible`, `.onxx-embeddings`), `gebo.llms.standard.functions`, `gebo.llms.starter` |
| `gebo.apps.parent` | **`gebo.apps.monolithic.starter`** (aggregates everything), **`gebo.ai.app`** (bootable jar, `mainClass=ai.gebo.monolithic.app.Main`) |
| `gebo.api.clients` | `gebo.monolithic.api.resttemplate.client` (generated OpenAPI client) |

**Key coexistence anchor:** `gebo.apps.monolithic.starter` is the single aggregation module. It already depends on `gebo.ragsystem.starter`, `gebo.contentsystems.starter`, `gebo.llms.starter`, `gebo.architecture.security(.controllers)`, `gebo.webconfig`, etc. `gebo.ai.app` wraps it into the bootable jar. **This is the object we preserve for the monolith and mirror per-service for microservices.**

### 1.2 Messaging (`gebo.application.messaging`) — the load-bearing abstraction

The whole microservices story rides on this module because *modules already never call each other's workflow logic directly — they emit/receive typed messages through an in-memory broker.*

- `IGMessageBroker extends IGMessageConsumer` — registers `IGMessagingSystem` components, `broadcast(GMessageEnvelope)`, delivers to every receiver whose accepted payload types match.
- `IGMessageEmitter` / `IGMessageReceiver` — components declare `getMessagingSystemId()`, `getMessagingModuleId()`, `getComponentType()`, and **payload-type contracts** (`getEmittedPayloadTypes()` / `getAcceptedPayloadTypes()` / `isAcceptEveryPayloadType()`).
- `GMessageEnvelope<PayloadType>` — carries `sourceComponent/sourceModule`, `targetComponent/targetModule`, `payload` + `payloadType` (FQCN), `userId`, workflow fields (`workflowType/workflowId/workflowStepId`), `onProcessForwardDestinations` (forward-chaining), retry/delivered/processed. It is already `@Document` / `Serializable` — **wire-ready**.
- `MultiThreadedMessagesOrchestrator` provides the internal threaded delivery.
- **External seam (already present, currently unbound):**
  - `IGExternalMessageEmitter` / `IGExternalMessageReceiver` (marker composition of `IGExternalInterface` + `IGMessageEmitter/Receiver`, `isLocalSystem()==false`).
  - `GAbstractExternalMessageEmitter` / `GAbstractExternalMessageReceiver` — base classes driven purely by config (`ExternalEmitterIfaceData` = emitted payload types + ids; `ExternalReceiverIfaceData` = accepted payload types + ids). The receiver base exposes an abstract `accept(GMessageEnvelope)`.
  - `IGExternalMessageEmitterProvider` / `…ReceiverProvider` → grouped by `…ProviderSource` → collected via `…ProviderSourceRepositoryPattern`.
  - `MessageBrokeringAssembler` (on `ContextRefreshedEvent`) registers internal emitters/receivers **and** iterates every external provider source, registering each external emitter/receiver into the broker.

> **Consequence:** To make two microservices talk, we do **not** touch business modules. We provide, in a new adapter module, a `ProviderSource` that yields external emitters (publish outbound payloads to a transport) and external receivers (subscribe to a transport, rebuild the `GMessageEnvelope`, call `broker.broadcast`/`accept`). The assembler wires them automatically. This is the cleanest possible integration point and is the backbone of §6.

### 1.3 Security

- `gebo.architecture.security` — Spring Security bootstrap and services: `GeboAISecurityConfig`, CORS, OAuth2 (dynamic client registration, opaque + JWT auth managers, SPA login support), users/groups model + repositories, ACL (`AclGrantedAccessorServiceImpl`), `RunAs*` identity helpers, `IdentityUtil`/`ReactiveIdentityUtil`.
- `gebo.architecture.security.controllers` — 12 REST controllers: `AuthController`, `AuthProvidersController`, `OAuth2AdminController`, `Oauth2ModuleStatusController`, `Oauth2StartLoginAttemptController`, `Oauth2SPAAuthorizationDeliveryController`, `TokenRenewController`, `UserController`, `UsersAdminController`, `UserWorkflowsController`, `GeneratedAdminApiKeyController`, `GeneratedUserApiKeyController`.

This maps to **`heimdall.gebo.ai`**, and additionally to the **`.secure-area`** module every service needs (§5.6).

### 1.4 Data & runtime state

- **LLM runtime config:** `IGRuntimeModelConfigurationDao<IFacetype, ModelConfig>` (extends `IGRuntimeConfigurationDao`) keeps **memory-resident configured model clients** (chat/embedding/etc.) backed by Mongo config repos. Multiple services (`brain`, `vectorizator`, `graphsearch`) must run the **same** LLM configuration and stay in sync when an admin controller mutates it → **Hazelcast-backed cache with cross-node invalidation** (module `gebo.architecture.hazelcast` already exists).
- **Knowledge entities** (`GKnowledgeBase`, `GProject`, `GCentralizedProjectEndpoint`, `GDocumentReference`, `GVirtualFolder`) live in `gebo.knowledgebase.model`; owned by **brain**.

### 1.5 Anatomy of a "feature slice" module (critical finding — drives the split design)

The modules named `*.abstraction.layer` are **not** clean contract jars. Each is a full vertical slice mixing, *in one artifact*, up to **seven** code categories. Verified across `gebo.architecture.llms.abstraction.layer` (153 files), `gebo.architecture.chat.abstraction.layer` (204 files), `gebo.architecture.agents.abstraction.layer`, `gebo.architecture.contentsystems.abstraction.layer`, and every `gebo.systems.parent` content handler:

| # | Category | Examples (from real code) | Where it must land after split |
|---|---|---|---|
| 1 | **Service interfaces** (`IG*`, `I*`) | `IGChatService`, `IGAgentsNetworkService`, `IGConfigurableChatModel`, `IGRuntimeModelConfigurationDao`, `IGContentManagementSystemHandler`, `ISearchService` | `.interface-models` (contract) |
| 2 | **DTO / value models / enums / exceptions** | `GeboChatRequest/Response`, `SemanticSearchRequest/Response`, `GBaseModelConfig`, `G*ModelType`, `SearchQuery/SearchResult`, `LLMConfigException` | `.interface-models` (contract) — **but see §5.8: strip `@Document`** |
| 3 | **`@Document` persistence entities** | `ChatFullSessionState`, `GUserChatSession`, `GAgentConfig`, `GAgentsNetwork`, `ContentHandshakeData`, session `CSS*` state | `.impl` (owner only) + a mapped POJO twin in `.interface-models` if it crosses a boundary |
| 4 | **Mongo repositories** | `ChatProfilesRepository`, `AgentConfigRepository`, `AgentsNetworkRepository`, `GitEndpointRepository`, `SampledSystemCataloguesRepository` | `.impl` (owner only) |
| 5 | **Concrete services / DAOs / message components** | `services/impl/**`, `GRuntimeChatProfileChatModelDaoImpl`, `GContentVectorizationEmitterComponent`, `SessionShrinkMessagesReceiver`, `GLlmsServiceClientsProviderFactoryImpl` | `.impl` (owner only) |
| 6 | **Admin / erogation controllers** | `ChatModelsController`, `EmbeddingModelsControllers`, `GeboAgentAdminController`, `GeboAgentsNetworkAdminController`, `GITSystemsController`, `ContentsResetController` | `.impl` or fold into `.server-proxy` (owner only) |
| 7 | **SDK abstract base classes (extension points)** | `GAbstractConfigurableChatModel`, `GAbstractConfigurableEmbeddingModel`, `BaseLLMSInvokingAndProvidingService`, `GAbstractAgentService`, `GAbstractContentManagementSystemHandler`, `GAbstractRemoteVirtualFilesystemContentManagementSystemHandler` | **`.sdk`** (new 5th child — see §5.7) |

**The two consumer kinds** (this is the design pivot):

1. **Same-JVM *extenders*** — provider/handler modules that `extends`/`implements` the category-7 SDK base classes at compile time. E.g. every `gebo.llms.*` provider extends `GAbstractConfigurableChatModel`; every content handler extends `GAbstractContentManagementSystemHandler` (confirmed: `gebo.git.content.handler` has `impl/GAbstractGitContentManagementSystemHandler extends` the layer base, plus its own `controllers/GITSystemsController`, `repositories/*`, and a runtime-only `GIOCGitContentsModuleDispatcherConfig`). These extenders are always **co-located** with the feature `.impl` in the same service, and need the Spring/Mongo-aware **`.sdk`**, not a pure contract.
2. **Cross-service *RPC callers*** — a different service that only calls the feature's interface (e.g. a content service asking brain for `IGDocumentTranslator`, or brain asking graphsearch for a graph query). These need only the **pure-POJO `.interface-models`** + a **`.client-proxy`**.

> **Consequence:** the directive's 4-child split (`interface-models`/`impl`/`server-proxy`/`client-proxy`) is correct for RPC callers but **misses the extender path**. We add a **fifth child `.sdk`** (§5.7) for the category-7 base classes, and a **contract-vs-entity mapping rule** (§5.8) for category-2/3. Without these two additions the split will not compile.

### 1.6 Confirmed cross-service compile edges that must be re-pointed

`gebo.architecture.chat.abstraction.layer` (→ brain) compile-depends today on modules owned by *other* target services. Each edge must be re-pointed from the whole module to a `.interface-models` (+ `.client-proxy` where a live call is needed):

| chat depends on | Target owner | Re-point to | Live call at runtime? |
|---|---|---|---|
| `gebo.architecture.llms.abstraction.layer` | brain-local | keep `.impl` + `.sdk` (co-located) | no (local) |
| `gebo.architecture.agents.abstraction.layer` | brain-local | keep `.impl` + `.sdk` (co-located) | no (local) |
| `gebo.architecture.graphrag.persistence` | **graphsearch** | `.interface-models` + `.client-proxy` | yes — GraphRAG query |
| `gebo.architecture.graphrag.extraction` | **graphsearch** | `.interface-models` (likely drop at runtime; extraction is build-side) | no |
| `gebo.architecture.documents.cache` | **chunker** | `.interface-models` + `.client-proxy` (or messaging) | maybe |
| `gebo.architecture.rag-threasholds-autotune` | **vectorizator** | `.interface-models` (read thresholds; see §10.12) | read-only |
| `gebo.knowledgebase.model`, `gebo.core.contents.security`, `gebo.architecture.rag.support.layer` | brain-local | keep | no |

This table is the template; the same edge-analysis must be run for every module promoted to a service boundary (mechanically: `mvn dependency:tree` per `.impl`, then classify each edge as *co-located* vs *cross-service*).

---

## 2. Target microservices

| Service | Primary responsibility | Main feature modules (impl) it hosts |
|---|---|---|
| **heimdall.gebo.ai** | AuthN/AuthZ, OAuth2 integration. **Also the keeper of the secret store** — though not of the crypting keys, which are shared: it moves ciphertext and never decrypts on a caller's behalf (§2.1). | `gebo.architecture.security.impl`, `gebo.architecture.security.controllers.impl` (+ `.server-proxy`); `gebo.secrets.impl` + `gebo.microservices.secrets.controller` |
| **brain.gebo.ai** | LLMs / chat / embedding orchestration / RAG query / agents. Owns `GKnowledgeBase`, `GProject`, `GCentralizedProjectEndpoint`, `GDocumentReference`, `GVirtualFolder`. Hosts web + bing search handlers, `gebo.ragsystem.client.rest`, and MCP (client + server, since MCP surfaces agents/networks as tools). | `llms.abstraction.layer.{impl,sdk}` + all `gebo.llms.*` providers + `gebo.llms.standard.functions`, `chat.abstraction.layer.{impl,sdk}`, `agents.abstraction.layer.{impl,sdk}` + `gebo.architecture.agents.standard`, `gebo.architecture.mcp-clients`, `gebo.architecture.mcp-server`, `gebo.ragsystem.client.rest`, `gebo.knowledgebase.*.impl+server-proxy`, `gebo.googlesearch.handler`, `gebo.bingsearch.handler` |
| **vectorizator.gebo.ai** | Consume "chunk ready" messages, embed chunks for semantic search, host rag-autotune | `gebo.ragsystem.content.vectorizator.impl`, `gebo.ragsystem.vectorstores`, `gebo.architecture.rag-threasholds-autotune`, `gebo.architecture.llms.abstraction.layer.impl` (embeddings) |
| **textsearch.gebo.ai** | Consume "chunk ready", full-text index via OpenSearch | `gebo.ragsystem.content.fulltext.processor.impl`, `gebo.architecture.fulltext`, `gebo.architecture.opensearch` |
| **graphsearch.gebo.ai** | Consume "chunk ready", build GraphRAG representation | `gebo.ragsystem.content.graphrag_processor.impl`, `gebo.architecture.graphrag.extraction/persistence`, `gebo.architecture.neo4j`, `gebo.architecture.llms.abstraction.layer.impl` |
| **chunker.gebo.ai** | Document caching on request + chunk preparation; emits "chunk ready" into the workflow | `gebo.architecture.documents.cache.impl` |
| **content services** (`*.gebo.ai`) | One per child of `gebo.systems.parent` **except** `gebo.googlesearch.handler` & `gebo.bingsearch.handler` (those go to brain). Each streams remote content into the ingestion workflow. | e.g. `gebo.git.content.handler.impl`, `gebo.filesystem.content.handler.impl`, `gebo.sharepoint.handler.impl`, `gebo.atlassian.confluence.handler.impl`, `gebo.atlassian.jira.handler.impl`, `gebo.googleworkspace.handlers.impl`, `gebo.uploads.content.handler.impl`, `gebo.userspace.handler.impl`, `gebo.integration.content.handler.impl`, `gebo.mcl-client.content.handler.impl` |

**Ingestion workflow (microservice view):**
```
content-service ──(stream file bytes)──▶ chunker ──(chunk-ready msg)──┐
                                                                       ├─▶ vectorizator ─▶ (writes to shared brain Mongo)
                                                                       ├─▶ textsearch   ─▶ (OpenSearch, own store)
                                                                       └─▶ graphsearch  ─▶ (Neo4j + shared brain Mongo)
```

### 2.1 The secrets edge (implemented)

Every service needs secrets — the LLM providers, the MCP connectors, the OAuth2
services and the content handlers all call `IGeboSecretsAccessService`, with
`getSecretContentById` alone having ~20 call sites. Centralising them in heimdall
means that call has to survive becoming a network hop **without any consumer
changing**. It does: consumers keep depending on the interface and get a different
implementation of it.

| Module | Goes on | Provides |
|---|---|---|
| `gebo.secrets.impl` | heimdall only | the real `GeboSecretsAccessServiceImpl` + the existing ADMIN `api/admin/SecretsController` |
| `gebo.microservices.secrets.controller` | heimdall only | `api/cluster/SecretsController` — the **whole** interface, decrypted content included, for service-to-service use |
| `gebo.microservices.secrets.client` | every other service | `IGeboSecretsAccessService` over REST against that cluster surface |

Bean selection is automatic: the client is `@ConditionalOnMissingBean`, so heimdall
(which owns the real impl) keeps its in-process one and everyone else transparently
gets the remote one. **A service takes one module or the other, never both.**

**Two surfaces, on purpose.** `api/admin/SecretsController` is unchanged and stays
the UI surface: metadata plus create/delete, `@PreAuthorize("hasRole('ADMIN')")`, and
it never returns secret content. The cluster surface exists because the interface
needs the five things the admin one deliberately does not expose — above all
`getSecretContentById`. Decrypted secret material crosses the network only there.

**Who may call the cluster surface — dynamic membership, fail-closed.** Not a
configured allow-list: the guard asks the `DiscoveryClient` for the instances
currently registered for each topology microservice and admits only a caller whose
connecting address is one of them. A service that leaves the registry loses access at
the next refresh. If discovery reports nothing, *nobody* is admitted — it fails
closed, so secrets stop flowing rather than flowing to anyone.

Three things about that guard are load-bearing and easy to undo by accident:

- **The gateway is not in the participants allow-list.** A caller is identified by the
  address it connects from, so admitting the gateway admits everything the gateway
  forwards — a browser's request included, since it arrives from the gateway's own
  registered address. The gateway correspondingly routes only
  `/heimdall_gebo_ai/api/admin/**`; a blanket `/heimdall_gebo_ai/**` would publish the
  cluster surface at the edge. Either change alone reopens the hole.
- **The guard reads the socket peer, not `X-Forwarded-For`.** Forwarded headers are set
  by the caller, so trusting one would let anybody claim a participant address.
- **`SecretsClusterController` is not a `@RestController`.** Every app component-scans
  `ai.gebo`, and a `@Component` would be picked up by that scan *independently* of the
  auto-configuration that installs the guard — publishing the endpoints unguarded.
  Type-level `@RequestMapping` + `@ResponseBody` behaves identically but is invisible
  to scanning, so the endpoints can only enter the container together with their guard.

**Identity — and the system user (§2.2).** The client forwards the *caller's* own
token, so heimdall authorises against the identity that originated the request. When
there is no caller token it does **not** fall back to a shared static credential; it
mints one for the platform's own identity. See §2.2 — that seam is subtle enough to
deserve its own section.

**Wire format — the ciphertext travels, not the secret.** The cluster surface hands back
the secret content **exactly as stored: still encrypted**, and a write arrives already
encrypted. The caller decrypts and encrypts locally with its own `IGeboCryptingService` —
every service has one, and the key material is shared (bundled keystore, or one pointed at
by configuration). So **no secret is ever in the clear on the network**, not even between
two services inside the cluster, and heimdall never decrypts on anyone's behalf. The guard
bounds *who may fetch a ciphertext*; the keys bound *who can read one* — two independent
controls. It also makes custom secrets round-trip losslessly for free: the decrypted JSON
is the stored JSON, so a caller-defined `GeboCustomSecretContent` subclass keeps every
field, with no passthrough trickery. The `GeboSecretType` still travels beside it, That base class has no Jackson type
information and **must not gain any**: the implementation encrypts
`writeValueAsString(content)` directly, so changing that representation would make
every already-stored secret unreadable. Custom secrets are read back through an
any-setter passthrough so a caller-defined `GeboCustomSecretContent` subclass (e.g.
`UserWorkflowSecret`, with its own `ticket`/`email`) round-trips intact — heimdall has
never heard of those classes, so it reproduces the stored JSON verbatim and lets the
caller pick the type.

**Status.** heimdall exists, carries `gebo.architecture.security` (so it has a real
filter chain and working method security) and hosts the secrets. No consumer has been
switched over yet: every other service still carries `gebo.secrets.impl` and reads
secrets from its own Mongo. Cutting one over = swapping that module for the client in
its pom. Nothing else — the system identity below needs no per-service setup.

### 2.2 The system user — who the platform is when no user is there

A great deal of work runs on **no user's thread**: LLM clients are built at startup
and on model replication, MCP connectors reconnect in the background, schedulers run
jobs. Such a thread has no `SecurityContext` — or, under `IdentityUtil.doAs`, one
holding a `UsernamePasswordAuthenticationToken` that was synthesized locally with
**null credentials** (`IdentityUtil.java:19-27`). So when that code calls a remote
service, **there is no token to forward**. This is not an inconvenience to engineer
around; there is genuinely nothing to propagate.

Anything reaching heimdall unauthenticated is refused (`anyRequest().authenticated()`),
so those calls would simply fail. The platform therefore does not *forward* a token in
that case — it **creates** one:

| | |
|---|---|
| **Who** | `heimdall@bifrost.gebo.ai`, roles `SYSTEM` + `ADMIN` (configurable: `ai.gebo.security.system-user`) |
| **Service** | `IGeboSystemUserService` — owns the identity and is the only thing allowed to mint its token |
| **Token** | An ordinary short-lived `LOCAL_JWT`, signed with the same `ai.gebo.security.auth.tokenSecret` and validated on the same path as a human's |

The important property: this is **not a bypass**. The fallback performs authentication
rather than skipping it, and the call arrives *attributable* to the system identity
instead of anonymous. It is also not a shared static bearer — the rejected alternative,
which would have been one long-lived credential, in shared config, unlocking every
secret in the platform, and a secret needed in order to reach the secret service.

**It is a virtual user.** It has no Mongo document; it is resolved from configuration
at the two points a user is normally loaded — `CustomUserDetailsService`
(which is how a validated system JWT becomes a principal) and
`GSecurityServiceImpl.getCurrentUser()` (which is how ACL, groups, `isCurrentUserAdmin`
and the `filter*` methods see it, since every one of them reads the current user
through that single method). Consequently it:

- **cannot log in** — its password is a random value whose plaintext is discarded at
  construction, so no presented password can ever match. There is no secret to leak,
  because none was ever chosen;
- **cannot be created** — `GUsersAdminServiceImpl.insertUser` refuses it, and since
  `createUserIfNotExists` delegates there, that one guard closes both admin creation
  *and* OAuth2 auto-provisioning;
- **is `LOCAL_JWT` only** — never federated.

**No controller changes were needed, and that is deliberate.** The platform runs
`GrantedAuthorityDefaults("")` — no `ROLE_` prefix — so `hasRole('ADMIN')` tests for an
authority named exactly `ADMIN`. Granting the system identity the `ADMIN` role makes all
~90 `@PreAuthorize("hasRole('ADMIN')")` endpoints callable by it without touching one of
them. Widening each to `hasAnyRole('ADMIN','SYSTEM')` would have been a 91-file diff that
bought nothing.

---

## 3. The coexistence principle (the rule that makes one codebase serve both)

> **Coexistence is build-time, not runtime.** One source tree builds *either* the monolith bootable jar *or* the N microservice images; **a given installation is exactly one shape — never both running together.** "Coexist" means the two shapes are produced from the same code and stay behavior-equivalent (guarded in CI, §11.5), not that they run side by side.

**In the monolith:** every module is on one classpath; the internal broker delivers all messages in-process; direct interface calls resolve to local Spring beans.

**In microservices:** the *same modules* are partitioned across JVMs. Two mechanisms replace the "everything on one classpath" assumption:

1. **Feature interfaces used across a service boundary** are satisfied by a **REST client bean** (`.client-proxy`) instead of the local `.impl` bean. Selection is by which modules are on that service's classpath — *not* by code changes in the caller.
2. **Message passing that crossed a module boundary in-process** now crosses a JVM boundary through an **external adapter** (RabbitMQ/Kafka/REST) bound to the pre-existing `IGExternalMessageEmitter/Receiver` seam. The internal broker still exists inside each service.

Neither mechanism requires the consuming business code to know which mode it runs in. That is the whole point of §5 and §6.

---

## 4. Naming & artifact conventions

- Maven `groupId` unchanged (`ai.gebo.*`). New child artifacts follow `<module>.interface-models`, `<module>.impl`, `<module>.server-proxy`, `<module>.client-proxy`, `<module>.secure-area`.
- Each microservice gets a **starter** + **app** pair mirroring `gebo.apps.monolithic.starter` / `gebo.ai.app`:
  - `gebo.apps.<service>.starter` (aggregates that service's `.impl` + `.server-proxy` for its own features, and `.interface-models` + `.client-proxy` for every foreign feature it consumes).
  - `gebo.<service>.app` (bootable jar; own `Main`; own `application.yml`).
- **Queue / input naming:** each service consumes from `<service-name>.inputq` (e.g. `vectorizator.inputq`, `chunker.inputq`, `textsearch.inputq`). Broadcast/topic naming and payload-type routing keys are defined in §6.4.

---

## 5. The module-split pattern (per shared `<module>`)

For every `<module>` that is *shared* between an implementing side and a consuming side across a future service boundary, replace it with a `<module>.parent` POM aggregating:

### 5.1 `<module>.interface-models`
- All interfaces that the consuming side programs against **plus** all DTO/model classes that appear in those interfaces' signatures.
- **No Spring, no implementation, minimal dependencies.** This is the contract jar shared by both the implementer and every consumer.
- Goes into the dependency list of **every service that uses the feature** (in place of today's dependency on the whole `<module>`).

### 5.2 `<module>.impl`
- All concrete implementations, DAOs, internal beans, and the **admin/erogation controllers** (which keep their `implements <interface>` bodies).
- Depends on `<module>.interface-models`.
- The monolith and the **owning** microservice both include `.impl`.

### 5.3 `<module>.server-proxy`
- One REST controller per implemented service interface, exposing the feature over HTTP (synchronous and/or reactive endpoints).
- Depends on `.impl` (delegates to the local bean) + `.interface-models`.
- Included **only** in the owning microservice (and optionally the monolith if we want the monolith to also serve as a callable node — off by default).

### 5.4 `<module>.client-proxy`
- A REST client that **implements the `.interface-models` interfaces** and calls the `.server-proxy` endpoints; optional local cache (Caffeine/Hazelcast) where the interface semantics allow.
- Depends on `.interface-models` only (+ an HTTP client).
- Included in every microservice that *consumes* the feature but does not host it. Its beans are wired **in place of** `.impl` beans by classpath composition (guarded by `@ConditionalOnMissingBean` / profile, see §9.3).

### 5.5 Split selection rule (which modules actually get split)
A `<module>` is split **iff** there exists a pair of services where one hosts its `.impl` and another needs its interfaces. Modules that are pure libraries pulled into a single service, or pure contract/model modules already, are **not** split — they stay as-is or are absorbed into an `.interface-models`. Candidate split list is enumerated in §11.2.

A `<module>` additionally needs a **`.sdk`** child (§5.7) **iff** it exposes category-7 abstract base classes (§1.5) that provider/handler modules extend. In this codebase that is: `llms.abstraction.layer` (LLM providers), `contentsystems.abstraction.layer` (content handlers), `agents.abstraction.layer` (agent services), and `search.abstraction.layer` (`AbstractWebSearchServiceImpl`).

### 5.7 `<module>.sdk` — the fifth child (extension point)
- Contains the **category-7 abstract base classes** and the repository-pattern scaffolding that same-service *extenders* compile against (`GAbstractConfigurableChatModel`, `GAbstractContentManagementSystemHandler`, `GAbstractAgentService`, `BaseLLMSInvokingAndProvidingService`, the `GAbstract*RepositoryPattern` helpers).
- **May depend on Spring and the abstract persistence layer** (unlike `.interface-models`, which stays POJO). Depends on `.interface-models`.
- Included in the **owning service** and in **any service that hosts extenders of the feature** (e.g. brain hosts `llms.sdk` + all `gebo.llms.*` providers; each content service hosts `contentsystems.sdk` + its handler).
- **Not** shipped to pure RPC-caller services — they get `.interface-models` + `.client-proxy` only.
- Rationale: the SDK base classes are Spring/Mongo-aware and cannot live in the pure-contract jar, but pulling the whole `.impl` into every extender would re-introduce the coupling we are removing.

### 5.8 Contract models vs `@Document` entities (category-2/3 rule)
- `.interface-models` MUST be free of persistence and framework annotations. Any category-2 model that today carries `@Document`/`@Id`/Spring Data annotations, and appears in an interface signature that crosses a service boundary, is split into:
  - a **plain POJO DTO** in `.interface-models` (the wire/contract type), and
  - the **`@Document` entity** staying in `.impl`, with a **mapper** (`.impl`) translating entity ⇄ DTO.
- Entities that never cross a boundary (e.g. `ChatFullSessionState`, session `CSS*` state — internal to brain) stay wholly in `.impl`; no DTO twin needed.
- `GMessageEnvelope` is the deliberate exception: it is already a serializable wire type and is shared as-is by the messaging bridge (§6).
- **This is the single largest source of hidden work.** Extraction must be done per-interface by reading signatures, not by moving packages wholesale (see §11 Phase-3 method).

### 5.6 Security special case — the extra `.secure-area` child
`gebo.architecture.security` and `gebo.architecture.security.controllers` split as above **plus** a `<module>.secure-area` child that:
- Implements the Spring Security initialization and the strict per-request rules currently in `GeboAISecurityConfig` (filter chain, resolver `IGHttpRequestAuthenticationManagerResolver`, JWT/opaque token validation, CORS).
- Validates tokens issued by **heimdall** (JWKS / introspection) — it does **not** re-implement login.
- Is a dependency of **every** microservice, alongside `gebo.architecture.security.interface-models` and `gebo.architecture.security.client-proxy` (for user/ACL lookups).

So each non-heimdall service carries: `security.interface-models` + `security.client-proxy` + `security.secure-area`. **heimdall** carries `security.impl` + `security.controllers.impl` + `security.server-proxy` + `security.secure-area`.

---

## 6. Messaging bridge: internal broker → external transports

### 6.1 What we build
Three **new generic adapter modules**, each providing external-emitter/receiver `ProviderSource` beans that bind the `IGExternalMessageEmitter/Receiver` seam (§1.2) to a transport. They are **transport bindings, not business logic**, and are identical in shape:

| Module | Transport |
|---|---|
| `gebo.application.messaging.external.rabbitmq` | RabbitMQ (Spring AMQP) |
| `gebo.application.messaging.external.kafka` | Kafka (Spring for Apache Kafka) |
| `gebo.application.messaging.external.rest` | Async REST controllers + `WebClient` calls |

### 6.2 Adapter responsibilities (common contract)

**Outbound (emitter side):**
- Read, from `application.yml`, the routing table: *for each locally-emitted payload type or forward-destination that must leave this JVM, which transport + queue/topic.*
- Register a `GAbstractExternalMessageEmitter` per outbound route (config = emitted payload types). When the internal broker delivers an outbound envelope to it, the adapter **serializes `GMessageEnvelope`** (JSON; envelope is already `Serializable`/`@Document`) and publishes to `<target-service>.inputq`.

**Inbound (receiver side):**
- Subscribe to this service's `<service-name>.inputq`.
- On message: **deserialize** into `GMessageEnvelope`, then hand to a `GAbstractExternalMessageReceiver.accept(...)` whose config declares the payload types this service consumes; the base delegates into the local broker (`broadcast`/targeted delivery). From there it is indistinguishable from an in-process message.

**Cross-cutting concerns the adapters own:** serialization/versioning of payload types, retry/`retry` field honoring, dead-letter routing, idempotency (envelope `id`), `userId`/security-context propagation (see §10.4), and workflow correlation (`workflowId`/`workflowStepId`) passthrough.

### 6.3 Why this is low-risk
The business modules already emit/consume via payload-type contracts and never name a peer directly. Adapters only need the **routing table** (payload type → destination queue) — which is configuration, defaulted per service and overridable via `application.yml`. No business module changes for a given message to start crossing a JVM boundary.

### 6.4 Routing model (to finalize — see §10.2)
- Default topology: **point-to-point** to `<service>.inputq`, plus a **fan-out** for "chunk-ready" (one producer → vectorizator + textsearch + graphsearch). Kafka: topic per destination with a consumer group per service; Rabbit: direct/topic exchange with per-service queues; REST: the producer POSTs to each subscriber's `/inbox` (or a broker service).
- `GMessageEnvelope.onProcessForwardDestinations` already models forward-chaining and must be honored by adapters so multi-step workflows keep working across services.

### 6.5 The ingestion-workflow / handshake protocol (a real cross-service contract)

The RAG workers are **pure message-driven components** — verified structure is nearly identical across the three:

| Worker | Emitter | Receiver factory | Workflow-step enabler | Batch receiver |
|---|---|---|---|---|
| vectorizator | `GContentVectorizationEmitterComponent` | `GContentVectorizationMessagesReceiverFactoryComponent` | `EmbeddingStandardWorkflowIngestionStepEnabledHandler` | `GEmbeddingMessageReceiverImpl`, `VectorizatorDisposerMessageReceiverImpl` |
| fulltext | `GContentFullTextEmitterComponent` | `GContentFullTextMessagesReceiverFactoryComponent` | `FullTextStandardWorkflowIngestionStepEnabledHandler` | `FullTextIndexingBatchMessageReceiver` |
| graphrag | (status updater) | `GraphextractionProcessorMessagesReceiverFactoryComponent` | `GraphextractionStandardWorkflowIngestionStepEnablerHandler` | `GraphextractionProcessorBatchReceiver` |

Two shared abstractions bind them into the workflow and **must be extracted into a contract module** consumed by chunker + all three workers + brain:

1. **`*StandardWorkflowIngestionStepEnabled/EnablerHandler`** — each worker declares itself an optional step of the "standard ingestion workflow". Backed in the abstraction layer by `AbstractWorkflowStatusHandler` / `GStandardWorkflowStatusHandlerImpl`. **In the monolith the set of enabled steps is discovered in-process; across services it must become a declared, config-driven topology** (which services subscribe to `chunk-ready`, in what order, with what fan-out). This is the microservices translation of "step enabled" and is an **open point (§10.7/§10.11)**.
2. **`ContentHandshakeData`** (+ `ContentHandshakeDataRepository`) — the handshake state between a content source and the workflow. It is a category-3 `@Document`; if it crosses services it needs the §5.8 DTO treatment. Ownership: whichever service originates the handshake (chunker) writes it; others read via shared brain Mongo (§7) or via a status query interface.

→ **New shared module:** `gebo.workflow.ingestion.contract`. **It aggregates (does not physically relocate) the existing shared ingestion wire types** — the step-enabler interface + `GStandardWorkflowStep` (in `gebo.application.messaging.workflow`), the chunk-ready / disposer / handshake payloads (in `gebo.core.messages` and `gebo.architecture.contentsystems.abstraction.layer`) — and is the **home for any *new* ingestion contract types**. Physically moving the stable types is rejected: it would change their FQCN, against §6.9 (one canonical FQCN per wire type). Every worker + chunker + brain depend on this one module; the workers keep their `.impl` (receiver/emitter components) private. **Phase-0 prerequisite** because it names the wire contract the bridge (§6.2) serializes.
- **Lint nuance (verified during P0-T1):** unlike a *REST DTO* `.interface-models` (which must be free of `spring-data-mongodb`, §5.8), the **messaging/ingestion wire types are legitimately dual-purpose — serialized to the bus *and* persisted (`@Document`)** in the dedup/message store (§6.6). `GMessageEnvelope` is the acknowledged exception (§6.9). So `spring-data-mongodb` is **acceptable** on this messaging contract; the strict no-persistence lint targets the feature REST `.interface-models`, not the wire contract.

### 6.6 Delivery semantics — **exactly-once** (settled, closes §10.2)

**Decision: exactly-once delivery for all queued messages.** Because no broker gives true end-to-end exactly-once across heterogeneous sinks (Mongo / OpenSearch / Neo4j / external LLM APIs), we realize it as the industry-standard **at-least-once transport + idempotent, deduplicating consumers** — i.e. *exactly-once **effect** on persisted state*. The codebase already carries everything needed on `GMessageEnvelope`: a unique `id` (UUID), plus `retry` / `delivered` / `processed` flags.

Mechanism (implemented once in the external adapter §6.2, uniform across Rabbit/Kafka/REST):
1. **Producer** stamps each envelope with its stable `id` (already done by `GMessageEnvelope.newMessageFrom`) — and for a workflow step, the `(workflowId, workflowStepId)` pair, so a redelivered step is recognizable.
2. **Consumer dedup barrier.** Each service keeps a **processed-message store** — a Mongo collection with a **unique index on `id`** (or `(id, targetComponent)`), written in the *same* logical unit as the business effect where possible. On receipt: if `id` already processed → **ack and drop** (the duplicate); else process, then record `id`. This makes redelivery a no-op.
3. **Idempotent sinks** for the effects that can't share a transaction with the dedup store: doc-refs/vfolders use *ensure-present* upsert (§7.3); embeddings/index/graph writes are upserts keyed by chunk/document id; so even a crash between "effect done" and "mark processed" replays safely.
4. **At-least-once transport config:** manual ack **after** processing, `retry` honored with backoff, and a **dead-letter queue** per `<service>.inputq` for messages exceeding max `retry` (poison-message isolation). Kafka: consumer-group offsets committed after processing; Rabbit: `basic.ack` post-process; REST: idempotency-key header = envelope `id`.

**Guarantee boundary (honest):** state persisted by the platform is exactly-once; a *non-idempotent external side effect* (e.g. a paid LLM call) is exactly-once only if the dedup barrier is checked **before** the call or the call is naturally idempotent — embedding/extraction are deterministic and their writes are upserts, so the pipeline is safe. This also **subsumes the partial-failure reconciliation** of §10.7: a failed fan-out branch simply redelivers and the dedup+upsert make the replay exact.

### 6.7 Security-context propagation over the bus — **identity-in-envelope + run-as-and-restore** (settled, closes §10.4)

**Decision: the message carries the actual identity; the receiver rebuilds the Spring Security context on the processing thread, does the business work, then restores the original.** This maps 1:1 onto existing code — `ai.gebo.security.services.IdentityUtil`:

```java
// receiver side, inside the external-adapter's onMessage / the broker delivery:
IdentityUtil.create(envelope.getUserId(), envelope.getUserRoles())
            .doAs(() -> processMessage(envelope));   // or doRunAsWithReturn / doAsWithException…
```

`IdentityUtil.doAs(...)` already does exactly the described lifecycle: it **saves** `SecurityContextHolder.getContext()`, installs a fresh context with a `UsernamePasswordAuthenticationToken(userName, null, roles→GrantedAuthorities)`, runs the business logic (so `SecurityContextHolder`, `IdentityUtil`, ACL checks, `IGSecurityService` all see the caller), and **restores** the saved context in `finally`. The `RunAs*` functional variants (`RunAsWithReturn`, `RunAsWithException`, …) cover return values and checked exceptions. For WebFlux receivers use `ReactiveIdentityUtil` (Reactor-context, not thread-local).

**Concrete requirements this imposes:**
1. **Envelope must carry the identity, not just `userId`.** `GMessageEnvelope` has `userId` but **not** roles → add an identity block (`userRoles: List<String>`, plus any tenant/company scope the ACL needs) to the envelope (or the ingestion payloads). This is a wire-schema addition → coordinate with versioning (§10.9). The emitter fills it from the current `SecurityContext` at publish time.
2. **Where the wrap happens:** in the messaging bridge / receiver seam (§6.2), *once*, so every worker's business code stays identical to the monolith (it just reads `SecurityContextHolder`). The `security.secure-area` (§5.6) provides `IdentityUtil`/`RunAs` on every service's classpath.
3. **Trust model — the bus is covered by credentials (settled).** The communication queue is **authenticated at the transport layer**: each service connects with its own broker credentials (RabbitMQ user/vhost, Kafka SASL principal, or REST mutual-auth/API-key), provisioned as **bootstrap secrets from the deployment platform** (§8.2), not from the app's own secret store. So **only credentialed platform services can publish to or consume from `<service>.inputq`** — the external/rogue-publisher vector that made in-band identity risky is removed at the transport, and the identity block in the envelope is trusted because its origin is an authenticated peer. This can be tightened with **per-queue authorization** (e.g. only chunker may publish to `vectorizator.inputq`; only content services to `chunker.inputq`) via broker permissions/ACLs. *Optional defense-in-depth* (against a *compromised* peer, not merely an external one): additionally **sign the identity block** (heimdall-minted HMAC/short-lived JWT) and verify before `doAs`. Not required for the baseline, since the credentialed bus already bounds who can emit.

### 6.8 Ingestion pipeline topology & step enablement — **already declared; make the enabled-check config-resolved** (settled, closes §10.13)

**The cross-service topology does not need inventing — it already exists as data** in `GStandardWorkflowStep` (`gebo.application.messaging.workflow`). Each step declares its `targetComponent` (`GMessagingComponentRef(module, component, workflow, stepId)`), an `onProcessedForwardComponents` function (the next steps), and `workflowStartStep` / `mandatoryStep` flags:

```
DOCUMENT_DISCOVERY (start, mandatory)  ──▶ TOKENIZATION (mandatory) ──▶ fan-out { EMBEDDING, GRAPHEXTRACTION, FULLTEXT_INDEXING } (optional, terminal)
```

This pipeline is the **default** workflow (`GStandardWorkflow.INGESTION`), and the model is **extensible by design**: each step carries a `workflow` field and the enablement lookup is keyed by `(workflowType, workflowId, stepId)`, so the architecture already admits **additional workflows and steps** — a third-party participant can register new steps (or a whole new workflow) via the participant registry (§8.3) without altering the default. The `module` names in `GStandardModulesConstraints` map **directly** to microservice input queues:

| Step | `targetComponent.module` | Microservice → queue |
|---|---|---|
| `DOCUMENT_DISCOVERY` | `generical-endpoint-module` | content services (per-handler dispatcher) |
| `TOKENIZATION` | `TOKENIZER_MODULE` | **chunker** → `chunker.inputq` |
| `EMBEDDING` | `VECTORIZATOR_MODULE` | **vectorizator** → `vectorizator.inputq` |
| `GRAPHEXTRACTION` | `KNOWLEDGE_GRAPH_MODULE` | **graphsearch** → `graphsearch.inputq` |
| `FULLTEXT_INDEXING` | `FULLTEXT_MODULE` | **textsearch** → `textsearch.inputq` |

**Enablement is already a strategy** — `GStandardWorkflowStep.verifyEnabledModules(...)` computes TOKENIZATION's fan-out: **mandatory** steps always included; **optional** steps included iff their `IWorkflowStepEnabledHandler.isEnabled(workflowId, stepId, WorkflowContext)` returns true (resolved via `IWorkflowStepEnabledHandlerRepositoryPattern`). Each worker registers its handler (e.g. `EmbeddingStandardWorkflowIngestionStepEnabledHandler(GStandardWorkflowStep.EMBEDDING)`).

**The microservices gap (the actual #13 decision):** the enabled-check runs on the **emitter/router side** (chunker, computing TOKENIZATION's forwards) but the `IWorkflowStepEnabledHandler` beans live in the **worker** services. In-process the repository-pattern aggregates them on one classpath; across services the chunker cannot see worker beans. **Resolution: make step-enablement a declared deployment config resolved at the router.** On chunker, back `IWorkflowStepEnabledHandlerRepositoryPattern` with `application.yml` — `gebo.ingestion.steps.{embedding,graphextraction,fulltext_indexing}.enabled` — instead of classpath beans; workers keep their local handler for the monolith build (bean-selection §9.3). `WorkflowContext` still allows per-workflow overrides (e.g. a KB that disables graph). So:
- **who subscribes to `chunk-ready`** = the enabled optional steps in the chunker's config;
- **ordering** = the enum's forward graph (mandatory `TOKENIZATION` before the fan-out);
- **fan-out** = the filtered target set → each enabled target `module`'s `<service>.inputq` (via `GMessageEnvelope.onProcessForwardDestinations`).
- Config must match deployment reality (don't enable `graphextraction` if graphsearch isn't deployed) — the chassis health-check (§10.10) verifies each enabled target queue is reachable at boot.

**Two-level dedup (clarifies §6.6 vs content handshake):**
- `GMessageEnvelope.id` dedups **message redelivery** (§6.6);
- **`ContentHandshakeData`** (`contentCode` + `hash` + `processed`, built from `GContentEmbeddingHandshakePayload`) dedups **unchanged content across re-ingestions** — skip re-embedding when the hash is unchanged. It is embedding-scoped → **owned by vectorizator**; in the shared brain DB it is a single-writer collection under §7.4.

**Completion tracking / disposal (closes the §10.7 residual) — split across two owners, per the existing code:**
- **brain (`CORE_MODULE`) owns end-of-workflow computation + the finished broadcast.** Workers emit status (`GContentsProcessingStatusUpdatePayload`) to `CORE_MODULE` / `USER_MESSAGES_CONCENTRATOR_COMPONENT` (→ `brain.inputq`); `GComputeEndOfWorkflowReceiverFactory` (in `gebo.core`, §13.13) calls `IWorkflowStatusHandler.computeWorkflowStatus(...)` and, when finished, broadcasts `GFinishedWorkflowPayload`. So brain is the **workflow-completion authority** — it aggregates the enabled steps' status.
- **chunker owns only its lower-level `ChunkingSessionDisposer`** — releasing cached chunk data after its own step completes.

(This corrects an earlier draft that placed completion tracking on chunker: the code puts workflow-end aggregation in `CORE_MODULE` → brain; chunker just cleans up its chunk cache.)

### 6.9 Wire payload & contract versioning — **shared contract, additive evolution** (settled, closes §10.9)

**Decision: wire types are a shared, co-versioned dependency — not independently-versioned per service.** The payload types live in shared modules (`gebo.workflow.ingestion.contract` for ingestion payloads, the feature `.interface-models` jars for RPC DTOs, and `gebo.application.messaging` for `GMessageEnvelope` itself), and **every microservice builds in the *same* payload types from the *same* jar.** So there is **one canonical class (one FQCN) per wire type across all services.**

**What this resolves:** the FQCN-relocation landmine disappears. Because producer and every consumer use the *same* class from the *same* shared jar, `GMessageEnvelope.payloadType` (the FQCN) always matches, and (de)serialization is symmetric — the same class on both sides, exactly like the monolith's in-JVM objects. No logical-type-name (`@JsonTypeName`) indirection is required; the shared jar *is* the schema.

**What remains — the one rule: additive-only evolution.** Independent deployment still yields a rolling-upgrade skew window where service A runs shared-contract `v2` and B runs `v1`. To keep that window safe: **only add optional fields with defaults; never remove, rename, or re-type.** Consumers deserialize with `FAIL_ON_UNKNOWN_PROPERTIES=false` — a `v1` consumer ignores `v2`'s new fields; a `v2` consumer defaults the fields a `v1` producer omitted. Field removal uses parallel-change (add new → migrate all services → retire old). This is precisely how the envelope's own recent additions must land — the identity block (§6.7) and trace/correlation block (§8.1) are **append-only** on `GMessageEnvelope`.

**Trade-off (deliberate) & jar versioning:** a shared co-versioned contract couples the contract's *release* (a genuinely breaking change would touch all services), in exchange for eliminating cross-service schema negotiation entirely. Additive-only keeps even that coupling loose: MINOR bumps (additive) roll out independently in any order; a MAJOR bump (non-additive) is the only case needing a coordinated rollout. A `schemaVersion`/build-info field on the envelope is optional (useful for diagnostics/dead-letter triage), not required for correctness given the single canonical types. Persisted `@Document` envelopes in the dedup/message stores stay readable because old fields are never removed.

**REST/OpenAPI parallel:** the `.interface-models` jar is the shared source both `.server-proxy` and `.client-proxy` compile against, so REST DTOs get the same single-canonical-class guarantee as bus payloads; the per-service OpenAPI + `@gebo.ai/<service>` stubs (§16.11) evolve by the same additive rule — additive changes keep old stubs working, a breaking change forces a stub regen + UI update.

---

## 7. Data & persistence strategy

### 7.1 MongoDB topology
- **Shared "brain" database:** `brain`, `vectorizator`, `graphsearch`, `chunker` connect to the **same** Mongo database. Rationale: the ingestion workflow writes chunk/embedding/graph artifacts that must land in the brain-owned store, and vectorizator/graphsearch persist there directly.
  - **Open point (§10.1, partially closed by §7.4):** shared DB across 4 services weakens service autonomy. We keep it per the directive; write-exclusivity per collection is now **structurally enforced by repository placement** (§7.4) — the remaining work is to *enumerate* the collection→owner map and gate the write-repos co-hosted via `contentsystems.impl`.
- **Per-service database:** every other microservice (heimdall, each content service, textsearch) owns its **own** Mongo database. textsearch additionally owns its OpenSearch cluster; graphsearch additionally owns Neo4j.
- **MCP config exception (`mcp-content-handler`):** the `gebo.mcl-client.content.handler` content service depends directly on `gebo.architecture.mcp-clients` and reuses its **`MCPClientConfig` registry** (`McpClientConfigRepository`) — the same registry brain uses to surface MCP servers as agent/chat tools. So the MCP-client content service is **not** fully DB-isolated: the MCP server registry is shared state with brain. Options (decision in §10.17): (a) the MCP content service **shares the brain Mongo's MCP-config collections** (read-only; brain owns writes via `McpClientConfigController`) — joining the shared-brain-DB group like the RAG workers; or (b) it reads the registry via `mcp-clients.client-proxy` (REST to brain) + a local Hazelcast cache. What is **not** shared is the live `McpClientPool`/connectors — each service opens its own connections from the shared config. Recommendation: (a) shared read of the config collections, given the data is low-volume and both sides already couple on `gebo.architecture.mcp-clients`.

### 7.2 LLM runtime configuration sync (Hazelcast)
- `brain`, `vectorizator`, `graphsearch` all instantiate memory-resident LLM clients via `IGRuntimeModelConfigurationDao` from the same Mongo config collections.
- Admin mutations (through heimdall-guarded admin controllers on **brain**) must **invalidate/rebuild** the in-memory clients on every node. Implement a **Hazelcast-backed distributed cache + topic**: on config change, publish an invalidation event; each node's DAO rebuilds affected clients. Reuse `gebo.architecture.hazelcast`.
- **Open point (§10.3):** where do admin write endpoints live? Recommended: LLM config **writes** on brain (owner), reads replicated via Hazelcast to vectorizator/graphsearch; those two never expose LLM-config write endpoints.

### 7.3 Knowledge entities access
- `GKnowledgeBase` / `GProject` are **owned by brain**. Other services that need them use `gebo.knowledgebase.model.interface-models` + a `client-proxy` with **local cache** (preferred over local Mongo copies to avoid dual-write). Caching invalidation via the same messaging bus or Hazelcast.
- `GCentralizedProjectEndpoint` + `GDocumentReference` + `GVirtualFolder` (the "virtual drives" / remotely-ingested content) follow a **funnel-through-messaging** materialization — confirmed against `GIOCContentConsumer` (`documentConsumer.accept()` forwards to the next step, `batchSentToNextStep`) and `GContentConsumerFactoryImpl.save()`:
  1. **Content-handler microservice (own Mongo):** `GContentConsumerFactoryImpl` writes the doc-refs/vfolders to the **content service's own local Mongo** (staging + dedup by `lastestJobId` + deletion-marking); `GIOCContentConsumer` **forwards** those refs via **messages to the chunker** (the workflow "next step").
  2. **chunker (shared brain Mongo):** receives the forwarded refs and **writes them to its Mongo — which *is* the shared brain DB**. Chunker is therefore the **single canonical writer** of these collections into the shared DB (message-fed, not a competing repository).
  3. **Other shared-DB modules (vectorizator / graphsearch / brain):** **write only if the entry is missing** — idempotent *ensure-present* (insert-if-absent), never overwrite. They fill a gap for a ref they need that chunker hasn't materialized yet; they do not own the write.
  4. **Deletion** flows as messages (`GInternalDeletionMessagePayload` → vectorizator dispose component) plus brain's lifecycle disposal (`GDisposeMongoContentsMessageReceiverFactoryImpl`, KB/project/endpoint removal).
  So these entities are *authored per-content-service locally*, *funneled to and canonically materialized on chunker (shared DB)*, *ensured-present idempotently by other workers*, and *disposed by brain*.

### 7.4 Write-exclusivity on the shared DB is a **structural** guarantee (partially closes §10.1)
The split makes "who writes a shared collection" a *property of where the code lives*, not a rule people must remember:

- **A collection is writable only by a service whose `.impl` binds a Spring Data repository (or `MongoTemplate` write) to it.** Consuming services receive only `.interface-models` (POJOs) + `.client-proxy` (remote calls) — **never the repository** — so they *physically cannot* write a collection they don't own. Write-ownership is thus decided by **where the write-repository and its admin/erogation controller live**, and enforced by construction. → *This is the point raised: whoever owns the admin controllers owns the writes, and non-owners lack the write path at all.*
- **Two write planes, each single-writer by the same mechanism:**
  - **Config/admin plane** — the service hosting the admin controllers owns writes: brain owns LLM-model config, vector-store config, `GKnowledgeBase`/`GProject` entities, and the MCP registry (`McpClientConfigController` write-only on brain, §10.17). No other service ships those controllers/repositories → cannot write.
  - **Data plane** — ingestion collections are written by the worker whose `.impl` binds the repo: e.g. vectorizator writes embeddings (`VectorizedContentRepository`), graphsearch writes graph projections, chunker writes the chunk/handshake + `GDocumentReference`/`GVirtualFolder` materialization.
- **What this closes:** the collection-ownership map (§10.1) drops from "a policy to trust" to "a property to enumerate + **lint**." A CI check asserts no two `.impl` modules bind a write path to the same `(database, collection)`; because non-owners lack the repository, single-writer holds at classpath/compile time.
- **The `GDocumentReference`/`GVirtualFolder` case — resolved by design (§7.3), not a co-hosted-repo collision.** These are *not* written by competing repositories on the shared DB. The content consumer running in a content service writes only that service's **own** Mongo and **forwards** the refs by message to chunker; **chunker is the single canonical writer** into the shared brain DB (message-fed). Other shared-DB modules only *ensure-present* (idempotent insert-if-absent), and brain owns deletion/disposal. So the write path funnels to one owner per operation:
  - shared-DB **insert/update** owner = **chunker** (message-fed materialization) — single writer;
  - shared-DB **delete** owner = **brain** (`GDisposeMongoContentsMessageReceiverFactoryImpl` + KB admin);
  - all other shared-DB touches = **idempotent ensure-present** (write only if the entry misses) — conflict-free by construction, no ownership claim.

  Implementation guard: keep the *materializing* consumer's write-repo active only on chunker (profile / `@ConditionalOnProperty`); on vectorizator/graphsearch the `contentsystems` code, if present at all, runs in *ensure-present* mode (insert-if-absent via a unique key on `projectEndpoint`+code, e.g. Mongo upsert `$setOnInsert`). This removes the earlier "co-hosted multi-writer" concern: content-service consumers write their own DBs; exactly one shared-DB service inserts; secondary touches are idempotent.
- **Defense in depth (residual gap):** a service could still issue a *raw* driver write to any collection since it shares the connection. Close this at the infra layer with **per-service Mongo users/roles scoped to owned collections** (write on owned, read on shared-read). Code-structure (no repository) + CI lint + DB roles = three independent layers, so "no one else writes" is *assured*, not hoped.
- **Reads stay open on purpose:** any shared-DB service may *read* any shared collection (that is the point of sharing); only writes are fenced. Read staleness/caching per §7.3.

### 7.5 Cross-service endpoint references — `GObjectRef` + a shared class-name→service map (settled, refines §10.6)

Each content service's concrete endpoint type (`GGitProjectEndpoint`, `GSharepointProjectEndpoint`, …) lives **only** in that service's `.impl` (§13.9), so a receiver (chunker/brain/vectorizator) does not have the class on its classpath and cannot deserialize the concrete object. Resolution — no new base type needed:

- **The sender transmits a shared `GObjectRef<? extends GProjectEndpoint>`, not its own concrete endpoint.** `ai.gebo.model.base.GObjectRef` (in the shared `gebo.base.model`, on every classpath) captures just `{className, code, description}` from any `GBaseObject` via `GObjectRef.of(endpoint)`, with `key() = className + "|" + code` (both `@HashIndexed`). Because `GObjectRef` is a shared, concrete, class-agnostic type, **it (de)serializes on every service — transport is granted** regardless of who owns the concrete endpoint class. (This is already the idiom the LLM layer uses — `GObjectRef<? extends GBaseModelConfig>` in `IGRuntimeModelConfigurationDao.findByModelReference` — so it generalizes to *any* cross-service reference, not just endpoints.)
- **A dynamic, discoverable `className → participant` registry — exposed via a well-known REST contract that *every participant* implements — NOT static config.** Each participant (microservice) advertises, through a **standard registry endpoint that is part of the architecture contract**, the endpoint-type `className`s it owns plus its address/queue; the map is shared architecture-wide so any receiver can resolve `GObjectRef.className` → owning participant **at runtime**. **This openness is deliberate and required: a third-party microservice that respects the architecture** (implements the shared contracts + the registry endpoint + advertises its endpoint types) **can join the running architecture without rebuilding — or even restarting — any existing service.** A static compile-time/config map would make the system closed and forbid third-party participants, so it is explicitly rejected. This registry backs **`GCentralizedProjectEndpoint`** (§7.3) — brain's central endpoint representation is a `GObjectRef`-keyed record + the live registry, **not** a copy of every participant's concrete class — and it also feeds the **gateway routing** (§16.11), which discovers routes from the registry rather than hard-coding them.
  - Endpoint resolution is one use of the general **participant registry (§8.3)** — the standard REST contract every participant implements to advertise its capabilities and enable dynamic third-party membership; registry topology (central vs federated) and capability-claim validation are decided there.
- **This is the transport for §10.6 streaming:** the ingestion message carries `GObjectRef<GProjectEndpoint>` + `GDocumentReference` (both shared types); the receiver uses the map to locate the owning content service and **pulls bytes from that service's streaming `server-proxy`** — neither the concrete endpoint class nor the file bytes ever cross as an unresolvable/oversized payload.

---

## 8. Per-microservice composition recipe

General rule for any service **S** hosting feature module **F**:

```
S.starter dependencies:
  ── F.impl                         (its own feature implementation)
  ── F.server-proxy                 (expose F over REST)
  ── security.secure-area           (validate heimdall tokens)
  ── security.interface-models + security.client-proxy   (user/ACL lookups)
  ── for each foreign feature G that S consumes:
        G.interface-models + G.client-proxy
  ── gebo.application.messaging (+ chosen external adapter module)
  ── gebo.webconfig, config, secrets, hazelcast (as needed)
```

Concrete highlights:
- **heimdall:** `security.impl`, `security.controllers.impl`, `security.server-proxy`, `security.secure-area`. No LLM/RAG modules. Own Mongo.
- **brain:** LLM abstraction `.impl`, chat, agents `.impl`, `ragsystem.client.rest`, `knowledgebase.*` (owner → `.impl` + `.server-proxy`), `googlesearch.handler`, `bingsearch.handler`; consumes chunker/vectorizator/graph/text only via messaging (workflow) + their `client-proxy` where synchronous. Shared brain Mongo + Hazelcast.
- **vectorizator:** `ragsystem.content.vectorizator.impl`, `vectorstores`, `rag-threasholds-autotune`, LLM embeddings `.impl`; consumes `knowledgebase client-proxy`. Shared brain Mongo + Hazelcast. Inbound `vectorizator.inputq`.
- **textsearch:** `fulltext.processor.impl`, `opensearch`; own Mongo + OpenSearch. Inbound `textsearch.inputq`.
- **graphsearch:** `graphrag_processor.impl`, `graphrag.extraction/persistence`, `neo4j`, LLM `.impl`; shared brain Mongo + Neo4j + Hazelcast. Inbound `graphsearch.inputq`.
- **chunker:** `documents.cache.impl`; shared brain Mongo. Inbound `chunker.inputq`; emits chunk-ready fan-out.
- **content service X:** `X.impl` + `X.server-proxy` (admin/config of the source) + `contentsystems.abstraction.layer.interface-models`; own Mongo; streams bytes into the workflow (chunker). Inbound `X.inputq` (e.g. re-crawl commands).

### 8.1 The `gebo.service.chassis` starter & observability (settled, closes §10.10)

Every `<service>.app` depends on one new Spring Boot starter, **`gebo.service.chassis`**, that packages the common runtime the monolith starter already aggregates — so a microservice is "chassis + its feature modules."

**Reused as-is (already in the reactor):** `gebo.application.messaging` + the chosen external adapter (§6.1); `security.{secure-area,interface-models,client-proxy}` (§5.6); `gebo.config` (+`gebo.config.services`); `gebo.secrets.services` (`IGeboSecretsAccessService`); `gebo.architecture.hazelcast` (where the service is in the LLM/KB cache group); `gebo.webconfig` (CORS, Jackson, WebFlux/RestTemplate config); `gebo.async.config` (thread pools); `gebo.architecture.environment` (`EnvironmentHolder` — `GEBO_HOME`/work-dir); `gebo.multilanguage.support`.

**Config & secrets layering:** per-service `application.yml` + `spring.config.additional-location` (the packaging already uses `/etc/gebo-ai/`, §1 deb/rpm/msi profiles). Two tiers (§8.2): **bootstrap secrets** (broker + Mongo + Hazelcast + crypto master key) come from the **deployment platform** (env/mounted/`/etc/gebo-ai/`/vault) — this is what makes the bus credentialed (§6.7.3) and per-service DB roles enforceable (§7.4); **application secrets** (API keys, OAuth, source creds) are managed by the service's own embedded `gebo.secrets.services` store.

**Observability — greenfield (must be *added*; nothing exists today):** a repo-wide grep finds **no actuator, no micrometer, no tracing**. The chassis adds:
1. `spring-boot-starter-actuator` with **custom health/readiness indicators for queue connectivity** (broker reachable), Mongo, Hazelcast, and — on chunker — the *enabled* ingestion target queues (§6.8); the gateway (§16.11) and orchestrator gate on these.
2. **micrometer-tracing** (Brave or OpenTelemetry) + an exporter (OTLP/Zipkin). *Residual sub-decision:* which backend.
3. **Correlation reuses the workflow identity already on the envelope:** propagate a **trace/correlation block** (`traceId`/`spanId`, alongside the reused `workflowId`/`id`) in `GMessageEnvelope` so one trace spans content→chunker→workers across the bus. This is another envelope field → **coordinate with §10.9 versioning** (it joins the identity block of §6.7 as the envelope's "context header").
4. **Structured logging with MDC**: the receiver seam sets `workflowId`/`userId`/`traceId` into MDC when it establishes identity (§6.7 `IdentityUtil.doAs`) and clears it symmetrically on restore — so every log line in a worker is correlatable.

*Settled:* tracing backend = **OpenTelemetry** (micrometer-tracing-bridge-otel + OTLP exporter) — vendor-neutral. The envelope context-header fields are the `userRoles`/`traceId`/`spanId` already added additively (P0-T4). *Remaining wiring:* bridge the Micrometer trace context ↔ the envelope `traceId`/`spanId` at the adapter seam so a trace continues across the bus.

### 8.2 Secrets placement — each microservice handles & stores its own (settled)

`gebo.secrets.services` is an **encrypted-at-rest secret store**, not a vault wrapper: `GeboSecret` is a Mongo `@Document` whose `secretContent` is encrypted via `GeboCryptingServiceImpl`, keyed by `code` + **`contextCode`** (the owning context), decrypted on read through `IGeboSecretsAccessService`. It holds SSH keys / username-password / tokens / OAuth / Google-JSON — **application integration secrets** consumed by content handlers (source creds), LLM providers (API keys, via `GModelApiAccessReadUtilsimpl`), and OAuth/security.

**Placement: fully embedded per service — no central secrets microservice, no secret ever on the wire.**
- Each service carries `gebo.secrets.services` in its **chassis (§8.1)** and owns its **own `GeboSecret` collection in its own Mongo**, its **own `SecretsController`** (for its `contextCode` secrets), and its **own crypto master key** — so a compromised service can only decrypt *its own* store. No `secrets.client-proxy`, no cross-service fetch. This maps naturally: secrets are already `contextCode`-scoped to their consumer (git creds where git runs, OAuth secrets in heimdall, SharePoint token in the SharePoint service).
- **The shared-brain-DB group (brain/vectorizator/graphsearch/chunker) shares the encrypted secret collection in the shared Mongo — and that is fine precisely because secrets are encrypted at rest and the group is one trust domain (shared DB + shared crypto key).** So the LLM-provider secrets that brain/vectorizator/graphsearch all need are simply written by brain (owner, §7.4) and read by the others from the same encrypted collection — no replication, no secret in Hazelcast, no coupling problem. Every *other* service (heimdall, textsearch, each content service) is fully isolated with its own store + own key. (With local ONNX embeddings, even the LLM group needs no shared provider secret.)

**Two tiers (bootstrap vs application) — the root-of-trust distinction:**
- **Bootstrap secrets** — the **crypto master key**, **Mongo credential**, **broker credential**, Hazelcast join secret — come from the **deployment platform** (env vars / mounted files / `/etc/gebo-ai/` / k8s secrets / external vault). They *cannot* come from the Mongo-backed secret store (chicken-and-egg: you need the DB password to reach the DB where secrets live).
- **Application secrets** — external API keys, OAuth client secrets, source creds — are what `gebo.secrets.services` manages (encrypted in Mongo, decrypted with the bootstrap crypto key).

### 8.3 Participant registry & third-party extensibility (generalizes §7.5)

**Concept:** the participant registry is the **cross-service generalization of the monolith's in-classpath repository-pattern aggregation.** In the monolith, pluggable capabilities — content handlers, LLM providers, workflow-step enablers (`IWorkflowStepEnabledHandler`), search/agent services — are discovered by `GAbstractImplementationsRepositoryPattern` / `IG*RepositoryPattern` beans scanning **one classpath**. Across services that scan can't reach other JVMs, so the registry replaces *"aggregate beans on one classpath"* with *"aggregate capability advertisements across participants."* It is the project's own lightweight **service-discovery + capability-advertisement** layer.

**Every participant exposes a standard registry REST contract (provided by the chassis §8.1)** advertising a *participant descriptor*:
- **identity** — service name, REST base URL, `<service>.inputq`;
- **capabilities** (not just endpoint types):
  - the set of **`messagingModuleId`s it owns** — each **globally unique** (present *exactly once* across the whole architecture), forming the shared **`messagingModuleId → microservice` map** that makes bus routing **deterministic architecture-wide** (§6.2/§17): an envelope addressed to `targetModule` resolves to the owning service and its `<service>.inputq`, with no per-service hand-wiring. Uniqueness is an **enforced invariant** — a second participant claiming an already-owned `messagingModuleId` is rejected at registration (the routing analogue of the collection single-writer rule §7.4);
  - endpoint-type `className`s it owns (§7.5, for `GObjectRef` resolution);
  - **workflows and workflow steps it implements** (`GStandardWorkflowStep` participation — the default `GStandardWorkflow.INGESTION` is *extensible*: third parties may register additional workflows and steps, §6.8);
  - content-system types it handles; and any other `IG*RepositoryPattern`-style capability.
  - **LLM providers are the deliberate exception — not advertised per-participant** (see note below);
- **health/liveness** (from actuator §8.1).

**Join / live / leave lifecycle:**
- **Join** — on boot the participant authenticates (heimdall token / credentialed bus §6.7.3) and publishes its descriptor. **Membership is credential-gated**, not open to anyone.
- **Live** — chassis health/heartbeat keeps it in the live set; consumers read the live map: gateway routing (§16.11), endpoint resolution (§7.5), workflow fan-out targets (§6.8).
- **Leave** — graceful deregister or health-timeout removes it; routes/resolutions drop it.

**Third-party extensibility (the requirement driving this):** any service — including a **third-party microservice** — that (a) depends on the shared contract jars, (b) implements the relevant SDK/interface contracts, and (c) exposes this registry endpoint advertising its capabilities, **joins the running architecture with no rebuild or restart of existing services.** This lifts the project's existing pluggability (content handlers, workflow steps are already runtime-pluggable *modules*) from *"drop a module on the classpath"* to *"bring up a service that respects the contract."*

**LLM providers — the deliberate centralization exception.** Unlike content-system types and workflow steps, **LLM providers/types/vendors are *not* a registry-discovered cross-service capability — they are always assembled in `brain`.** brain is the single **LLM authority**: all `gebo.llms.*` provider modules + `llms.abstraction.layer.{impl,sdk}` are assembled there, and brain owns all LLM **config/admin** for every type and vendor (chat + embedding), written by brain and Hazelcast-synced (§7.2). **Config authority is central; embedding *execution* is co-located** — the embedding workers (vectorizator/graphsearch) additionally assemble the embedding provider subset and **embed in-process** (Hazelcast-config-synced from brain §7.2; LLM secrets read from the shared brain DB §8.2), so embedding throughput scales with the workers rather than routing every chunk through brain. This is a build-time assembly fact, not runtime discovery — hence not a registry capability.

*Sub-decisions owed:*
- **Topology** — a **central discovery service** (a Gebo-native registry, or off-the-shelf Consul/Eureka) all participants publish to and query, **vs.** a **federated** model (each serves its own contribution, unioned by the gateway/discovery layer). The **per-participant REST contract is identical** either way — that contract is what makes third-party membership possible.
- **Security of claims** — the join path is authenticated (heimdall); capability claims must be **validated/authorized** (a participant may only advertise endpoint types / steps it is entitled to) so a rogue participant can't hijack routing or workflow fan-out.

---

## 9. Building both shapes from one codebase

### 9.1 Reactor additions
- Keep every existing module building as today (monolith unaffected while splits are introduced incrementally — see §12 strangler approach).
- Add a `gebo.apps.parent` sibling group (or subtree) for microservice starters+apps: `gebo.apps.<service>.starter` and `gebo.<service>.app` for each service in §2.
- The monolith’s `gebo.apps.monolithic.starter` is **rewired** to depend on the `.impl` modules (per the directive: "instead of integrating each `<module>` it will integrate each `<module>.impl`"). Because `.impl` depends transitively on `.interface-models`, the monolith classpath is unchanged in behavior.

### 9.2 Build activation — a module per deployable (not a `-Pmicroservices` profile)
**The assembly boundary is the Maven module, not a profile flag.** Each deployable — the monolith `gebo.ai.app` plus one `gebo.<service>.app` per microservice — is its **own module** in the reactor, with its own dependency set and its own `spring-boot:repackage`/Docker assembly. This is the idiomatic multi-module shape and is preferred over a `-Pmicroservices` profile, because a profile that toggles modules in/out of the reactor is a known Maven anti-pattern (it makes the reactor's identity profile-dependent, confusing IDEs, `-pl` targeting, and "what does this build produce?").
- **All library modules** (the split children `.interface-models`/`.impl`/`.sdk`/`.server-proxy`/`.client-proxy`) are **always** in the reactor.
- **Build one deployable:** `mvn -pl gebo.apps.parent/gebo.brain.app -am package`. **Build all:** root `mvn install`. **CI:** a matrix over the app modules (each independently buildable/publishable), or a single reactor build with `-T` parallelism.
- **Profiles stay for what they're good at** — per-artifact *packaging variants* of a single deployable, exactly as `gebo.ai.app` already uses them (`swagger-on`, `bootables`, `package-unix-deb/rpm`, `package-windows`). Each `gebo.<service>.app` reuses that pattern for its own bootable/deb/rpm/docker outputs. **No module-toggling profile.**
- Monolith-dev ergonomics ("don't rebuild every service app on a root build") are handled the idiomatic way (`-pl <app> -am`, IDE build-module, or CI job scoping) — not by hiding modules behind a profile.
- Angular UI: served by the gateway (§16.11) or the monolith; each service ships its own OpenAPI + stub (§16.11).

### 9.3 Runtime bean selection (impl vs client-proxy)
- `.client-proxy` beans are annotated `@ConditionalOnMissingBean(<interface>)` (or profile-gated `@Profile("microservice")`), so:
  - **Monolith:** `.impl` beans present → client-proxies back off → all local calls. ✅
  - **Microservice consuming F:** only `F.client-proxy` on classpath → its bean implements the interface → remote calls. ✅
  - **Microservice owning F:** `F.impl` present → local calls; `F.server-proxy` also present to serve others. ✅
- This is the single most important invariant to enforce with tests (§11.5): *the exact same caller code binds to a local or remote implementation purely by classpath composition.*

---

## 10. Gap analysis / open architectural points

> These are decisions/risks not fully determined by the current code or the directive. Each needs an explicit ruling before the phase that touches it.

1. **Shared brain Mongo across 4 services.** *Largely closed (§7.4 + §7.3):* write-exclusivity is **structurally enforced by repository placement** (a service without the write-repo/admin-controller cannot write), and the one collection with multiple potential writers — `GDocumentReference`/`GVirtualFolder` — is resolved by the **funnel-through-messaging** design: content services write their own DBs and forward refs by message; **chunker is the single canonical shared-DB writer**; other workers only *ensure-present* idempotently (insert-if-absent); brain owns deletes. *Still owed (mechanical):* (a) enumerate the full collection→owner map and set the `contentsystems` write-repo to chunker-only vs ensure-present mode on other shared-DB services; (b) add the CI lint (no two `.impl` bind a non-idempotent write to the same `(db, collection)`) + per-service Mongo roles as defense in depth.
2. **Message routing model & delivery semantics.** *Settled (§6.6): **exactly-once***, realized as at-least-once transport + idempotent deduplicating consumers (dedup on `GMessageEnvelope.id` via a unique-indexed processed-store; idempotent/upsert sinks; per-queue dead-letter after max `retry`). Exactly-once *effect on persisted state*; non-idempotent external side effects guarded by checking the dedup barrier first. *Still owed (minor):* per-payload-type ordering needs (most steps are order-independent; note any that aren't).
3. **LLM config write ownership & Hazelcast cluster boundary.** *Write-ownership closed (§7.4):* LLM-config admin controllers live only in brain's `.impl`, so brain is the sole writer by construction; vectorizator/graphsearch host no LLM-config write-repo and read replicated state via Hazelcast. *Still owed:* Hazelcast membership (brain+vectorizator+graphsearch only), whether Hazelcast also backs the knowledge-entity cache, and securing cluster join across pods.
4. **Security-context propagation over the bus.** *Settled (§6.7): **identity-in-envelope + run-as-and-restore***. The message carries the actual identity (`userId` + `userRoles` added to `GMessageEnvelope`); the receiver wraps processing in `IdentityUtil.create(userId, roles).doAs(...)`, which saves the current `SecurityContext`, installs the caller's `Authentication`, runs the business logic, and restores the original in `finally` (existing `RunAs*` helpers). Trust is provided by the **credentialed bus** (§6.7.3): each service authenticates to the broker (per-service credentials via `gebo.secrets.services`), so only platform services can emit/consume — signing the identity block is optional defense-in-depth. *Still owed (minor):* the envelope identity-block schema addition (`userRoles` + scope; ties to §10.9 versioning) and optional per-queue ACLs.
5. **API gateway / UI edge.** *Settled (§16.11):* a **Spring Boot-integrated gateway** (Spring Cloud Gateway, WebFlux → SSE-transparent) is the ingress, routing `/<service>/**`; token validation stays in each service's `secure-area`. Each microservice publishes its **own OpenAPI** and a per-service Angular stub `@gebo.ai/<service>` (same swagger-codegen toolchain as the monolith's single stub). The UI imports the per-service stubs; the **only** monolith↔microservices difference is each stub's `BASE_PATH` (same origin in the monolith, gateway relative path `/<service>` in microservices). Regen flows become per-service.
6. **Content streaming protocol.** *Settled (§7.5):* the message carries **shared reference types only** — `GObjectRef<? extends GProjectEndpoint>` (`{className, code}`, transport-safe since the concrete `G<X>ProjectEndpoint` lives only in its content service) + `GDocumentReference`; a **shared `className → microservice` map** resolves the owning content service, and the receiver (chunker) **pulls the bytes** from that service's streaming `server-proxy`. So neither the concrete endpoint class nor file bytes cross the bus. This also backs `GCentralizedProjectEndpoint` (brain = `GObjectRef` + registry, not concrete-class copies).
7. **Transactional/consistency boundaries in the workflow.** *Settled (§6.6 + §6.8):* partial failure is handled by exactly-once redelivery + idempotent replays keyed by envelope `id` + `workflowStepId`. **Completion authority = brain (`CORE_MODULE`)** — `GComputeEndOfWorkflowReceiverFactory` + `IWorkflowStatusHandler` aggregate the enabled steps' status and broadcast `GFinishedWorkflowPayload`; **chunker owns only its `ChunkingSessionDisposer`** (chunk-cache cleanup).
8. **`.interface-models` extraction fidelity.** *Settled (adopts recommendation, §5.8):* contract models are **plain POJO twins distinct from the `@Document` entities**, with mapping layers in `.impl`; `.interface-models` carries no persistence annotations. The mechanical extraction is per-interface-signature (§11 Phase-3), lint-checked (`dependency:tree`) so no `.interface-models` transitively drags **`spring-data-mongodb` / `spring-context` / `spring-web` / persistence**. *(Note, verified during P0-T1: the root parent injects `spring-boot-starter-validation` + `lombok` into every module, so `jakarta.validation` + `lombok` on a contract are expected — lint the specific offenders, not "spring" broadly.)*
9. **Versioning & compatibility.** *Settled (§6.9): shared co-versioned contract + additive-only evolution.* Payload/DTO types live in shared dependencies (`gebo.workflow.ingestion.contract`, the `.interface-models` jars, `gebo.application.messaging`) and every service builds in the **same** classes → one canonical FQCN per wire type, symmetric (de)serialization (no FQCN-relocation mismatch, no `@JsonTypeName` indirection needed). The rolling-deploy skew window is handled by **additive-only** evolution (add optional fields with defaults; `FAIL_ON_UNKNOWN_PROPERTIES=false`; removals via parallel-change) — this is how the §6.7 identity and §8.1 trace blocks append to `GMessageEnvelope`. *Still owed (minor):* SemVer discipline on the shared jars (MAJOR = coordinated rollout) and an optional `schemaVersion` diagnostic field.
10. **Config, secrets, observability per service.** *Settled (§8.1):* a new **`gebo.service.chassis`** starter packages the common runtime the monolith already aggregates (messaging + adapter, `secure-area`, config, secrets, hazelcast, webconfig, async, environment). Config/secrets layer via `application.yml` + `/etc/gebo-ai/`; broker/DB creds from `gebo.secrets.services`. **Observability is greenfield** (no actuator/micrometer/tracing exists today) → chassis adds actuator health/readiness (incl. queue-connectivity indicators), micrometer-tracing, and a trace/correlation block on `GMessageEnvelope` reusing `workflowId`. *Settled:* tracing backend = **OpenTelemetry** (OTLP exporter); envelope context-header fields added (P0-T4). *Remaining wiring:* Micrometer-context ↔ envelope-trace bridging at the adapter seam.
11. **Testing the coexistence invariant.** *Settled (§11.5): a CI conformance gate, not a runtime feature.* Coexistence is **build-time** — one source tree builds *either* the monolith *or* the microservices; a given installation is exactly one shape (§3). The guard: on the **microservices part of the build**, reuse the **same integration-test scenarios** that validate the monolith — the harness in `gebo.architecture.integration.tests` (`AbstractGeboMonolithicIntegrationTests`), **the concrete suite under `gebo.ai.app/src/test`** (`GitContentSystemIntegrationTests`, `SharedFilesystemIntegrationTest`, `MCPServerIngestionIntegrationTest`, `WorkflowCompletionTest`, `DefaultAgentsNetworkTest`, the `virtualremotefs/**` harness), and `integration-tests/*` — but deploy the services as **Docker containers** (Testcontainers, per-service images) and drive them through the **per-service generated Java stub clients** (the microservices analog of `gebo.monolithic.api.resttemplate.client`, via the gateway `BASE_PATH`, §16.11), asserting the **same outcomes** (`runAndWaitDoneCheckingResults`). Same scenarios + same assertions against a real Dockerized topology = behavior-equivalence with the monolith.
12. **`.sdk` boundary & where extenders live (from §1.5).** *Settled (adopts recommendation):* a **separate `.sdk`** child for the four extension-point layers (`llms`, `contentsystems`, `agents`, `search`) that expose `GAbstract*` base classes; trivial cases may fold into `.interface-models`. Every provider/handler is co-located with the feature `.impl`; `.sdk` ships only to services hosting extenders (§5.7).
13. **Workflow step-enablement topology.** *Settled (§6.8):* the topology is **already declared as data** in `GStandardWorkflowStep` (targets, forward-graph, mandatory flags), and `GStandardModulesConstraints` module names map directly to `<service>.inputq`. The only change: the step-enabled check runs on the emitter (chunker) but the handler beans live in workers → back the chunker's `IWorkflowStepEnabledHandlerRepositoryPattern` with **`application.yml` config** (`gebo.ingestion.steps.*.enabled`) instead of classpath beans; `WorkflowContext` allows per-workflow overrides. `ContentHandshakeData` (content-hash dedup) is **owned by vectorizator**; **workflow-end/completion is owned by brain (`CORE_MODULE`)** (`GComputeEndOfWorkflowReceiverFactory` + `IWorkflowStatusHandler`), while chunker owns only chunk-session disposal (§6.8, §13.13).
14. **RAG-autotune threshold sharing (from §1.6).** *Settled (adopts recommendation):* thresholds are read by brain via **shared brain Mongo** (read-only; brain and vectorizator are already on the same DB, §7.1); autotune **compute** stays on vectorizator (single-writer per §7.4). No `.client-proxy` or Hazelcast needed.
15. **GraphRAG query path (from §1.6).** *Settled (adopts recommendation):* brain queries the graph via **`graphrag.persistence.client-proxy`** (REST) to graphsearch's `IKnowledgeGraphSearchService` server-proxy (§13.11). Brain never reads Neo4j directly (would break ownership).
16. **`gebo.contentsystems.starter` / dispatcher-config semantics.** *Settled (adopts recommendation):* each content service **self-registers its `GIOC*ModuleDispatcherConfig`** on boot; the `contentsystems.starter` aggregation works with a single handler on the classpath (one dispatcher config per service). Each endpoint type also self-registers into the shared `className → microservice` map (§7.5). *To verify in Phase 1* that boot registration is order-independent.
17. **MCP config registry sharing (from clarification).** **Settled:** the `MCPClientConfig` registry is **written only by brain** (via `McpClientConfigController`); the `mcp-content-handler` service is a **read-only** consumer of the registry, and each service maintains its **own** `McpClientPool`/connectors from that config. Split `gebo.architecture.mcp-clients` into `.interface-models` (`MCPClientConfig`/`MCPTool`/`MCPPrompt`/`MCPResource`/transport enums + `McpClientManagementService` iface) / `.impl` (`McpClientPool`, `McpClientConnector`, repository, `McpClientConfigController`), with the write path (repository + controller) landing **only in brain**. *Remaining decision owed:* the read mechanism for the content service — shared brain Mongo registry collection (recommended) vs `mcp-clients.client-proxy` + Hazelcast cache. Note: `gebo.architecture.mcp-server` (agents/networks-as-tools) stays brain-only and is **not** shared with the content service.
18. **`gebo.core` (and other cross-cutting modules) splitting.** *Settled (§13.13):* `gebo.core` is the `CORE_MODULE` = brain's own domain + orchestration core (not a shared feature module), so it **collapses into brain** by concern — KB/Project/Content erogation + the `CORE_MODULE` hub (`GComputeEndOfWorkflowReceiverFactory` end-of-workflow authority, `USER_MESSAGES_CONCENTRATOR`, content disposal) → brain; strays (`BuildSystems`/`Company`/`Reindexing`/`LogView`) peeled to owners. Shared wire payloads already live in `gebo.core.messages` (§6.9 contract jar). Same by-concern lens for `gebo.system.ingestion`, `gebo.jobs.services`, `gebo.config`, `gebo.fastsetup`.
19. **Angular UI + generated client re-targeting.** *Settled (§16.11):* per-service OpenAPI → per-service Angular stubs `@gebo.ai/<service>` (+ optional per-service Java clients); the UI imports the per-service stubs behind the Spring gateway; the sole config difference is each stub's `BASE_PATH` (same origin monolith vs gateway relative path microservices); SSE preserved by the reactive gateway. Regen flows become per-service under the `@gebo.ai` npm scope.

---

## 11. Refactoring TO-DO list (phased, ordered)

Legend: **[C]** create module · **[M]** move code · **[W]** wire/config · **[T]** test.

### Phase 0 — Foundations (no behavior change)
- **[C]** `gebo.application.messaging.external.rabbitmq`, `.kafka`, `.rest` adapter modules (empty `ProviderSource` scaffolds binding the seam in §6). Build them but don't include anywhere yet.
- **[C]** `gebo.service.chassis` starter (common: messaging + chosen adapter + secure-area + config + secrets + hazelcast + actuator/health). *(Resolves §10.10 partially.)*
- **[C]** `gebo.workflow.ingestion.contract` (`.interface-models`): the step-enabler interface, `chunk-ready`/disposer payload types, `ContentHandshakeData` DTO (§6.5). **Prerequisite** — defines the wire payloads the bridge serializes.
- **[W]** Establish the **split conventions** once, as a documented template: the 5 children (`interface-models`/`sdk`/`impl`/`server-proxy`/`client-proxy`), the `@ConditionalOnMissingBean`/`@Profile` bean-selection rule (§9.3), and the entity⇄DTO mapper rule (§5.8). Apply mechanically thereafter.
- **[W]** Establish the **module-per-deployable** build layout (§9.2): library modules always in the reactor, each `gebo.<service>.app` its own module built via `-pl … -am`; ensure the monolith build is unchanged. (No module-toggling profile.)
- **[T]** Serialization round-trip test for `GMessageEnvelope` (JSON) incl. workflow + forward-destination fields, and for the new ingestion payload types.
- **[W]** Ratify the §10 decisions that block later phases (routing table, security-context propagation, streaming protocol, contract-model-vs-`@Document`, `.sdk` boundary, workflow topology).

### Phase 1 — Messaging bridge proven on one edge (strangler start)
- **[C/W]** Implement RabbitMQ adapter fully (outbound emitter + inbound receiver + routing table from `application.yml`).
- **[T]** Stand up **chunker** ⇄ **vectorizator** as the first two split-out services sharing brain Mongo; prove a chunk-ready message flows chunker→vectorizator over Rabbit and is processed identically to monolith. Extend `gebo.architecture.integration.tests` with a Rabbit Testcontainer.
- **[W]** Keep the monolith building and passing all existing tests throughout.

### Phase 2 — Security split → heimdall
- **[C]** `gebo.architecture.security.parent` → `.interface-models` **[M]** (user/ACL/oauth model + service interfaces `IGUsersAdminService`, `IAclGrantedAccessorService`, `IGOauth2*Service`, `IdentityUtil` contracts), `.impl` **[M]** (all `services/impl/**`, repositories, `GeboAISecurityConfig` login side), `.server-proxy` **[C]**, `.client-proxy` **[C]**, `.secure-area` **[C]** (filter chain + token validation only).
- **[C]** `gebo.architecture.security.controllers.parent` → `.impl` **[M]** (the 12 controllers), `.server-proxy` (thin, or controllers act as server-proxy directly), `.interface-models`/`.client-proxy` as needed for cross-service user lookups.
- **[C]** `gebo.apps.heimdall.starter` + `gebo.heimdall.app`.
- **[W]** Rewire `gebo.apps.monolithic.starter`: `security` → `security.impl` + `security.controllers.impl` + `security.secure-area`.
- **[T]** Monolith auth flows unchanged; heimdall issues tokens; a second service validates them via `.secure-area`.

### Phase 3 — Split the shared feature modules
Split each of the following per §5 (4 children, **+`.sdk`** where marked ★, per §5.7). Order by fan-in / dependency depth (deepest contract first, so downstream splits compile against ready contracts):

**§11.2 Split candidate list (ordered; ★ = needs `.sdk`):**
1. `gebo.knowledgebase.model` (+ `gebo.knowledgebase.repositories`) — deepest fan-in → owner **brain**. Mostly category-2 models → watch §5.8 for `@Document` entities (`GKnowledgeBase`, `GProject`, `GDocumentReference`, `GVirtualFolder`, `GCentralizedProjectEndpoint`).
2. `gebo.architecture.search.abstraction.layer` ★ — near-clean already (`model/**` + `service/ISearchService`, `INativeSearchService`, small `impl`). Owner of the query contract; **textsearch** hosts a native `.impl`, brain/others call via `.client-proxy`.
3. `gebo.architecture.llms.abstraction.layer` ★ — most complex (153 files). Owner **brain**. See Appendix B for the class-bucket map. Cross-service use is mostly **config-sync via Hazelcast** (§7.2), not RPC — `.client-proxy` is secondary (for non-hosting callers wanting `IGDocumentTranslator` etc.).
4. `gebo.architecture.agents.abstraction.layer` ★ — owner **brain**. Category-3 (`GAgentConfig`, `GAgentsNetwork`) + controllers + `GAbstract*` SDK. (Note: agents network cache is keyed by `getNetworkAgentName()`, not `GAgentConfig.getCode()` — preserve this in any DTO mapping.)
5. `gebo.architecture.chat.abstraction.layer` — owner **brain**. Re-point its cross-service edges per §1.6 before splitting. Session `@Document` state stays wholly in `.impl` (never crosses).
6. `gebo.architecture.documents.cache` — owner **chunker**.
7. `gebo.ragsystem.content.vectorizator` — owner **vectorizator** (message-driven; §6.5). Minimal `.server-proxy` (query counts); ingestion all via messaging.
8. `gebo.ragsystem.content.fulltext.processor` — owner **textsearch**.
9. `gebo.ragsystem.content.graphrag_processor` — owner **graphsearch**.
10. `gebo.architecture.contentsystems.abstraction.layer` ★ — the content-handler SDK: `GAbstractContentManagementSystemHandler` & friends → `.sdk`; `IG*Handler`/`IGVirtualFilesystemBrowsingService`/DTOs → `.interface-models`; `impl/**`, controllers, `ContentHandshakeDataRepository` → `.impl`.
11. Each `gebo.systems.parent` content handler (`git`, `filesystem`, `uploads`, `sharepoint`, `confluence`, `jira`, `googleworkspace`, `integration`, `userspace`, `mcl-client`) — owner = its own content service. Template = `gebo.git.content.handler` (§1.5): handler `impl/**` + `controllers/*` + `repositories/*` + entities (`GGit*`) + runtime `GIOC*DispatcherConfig` → `.impl`; only the endpoint-config DTO brain needs → `.interface-models`. **Special case `mcl-client` (MCP content handler):** depends on `gebo.architecture.mcp-clients` and shares the `MCPClientConfig` registry with brain — split `mcp-clients` (item 13) first, and resolve registry sharing per §10.17.
12. `gebo.ragsystem.client.rest` — owner **brain**.
13. `gebo.architecture.mcp-clients` — owner **brain**; consumed read-only by the `mcp-content-handler` service. Split `.interface-models` (`MCPClientConfig`, `MCPTool/Prompt/Resource`, `MCPTransportType`/`McpAuthMode`, `McpClientManagementService`) / `.impl` (`McpClientPool`, `McpClientConnector`, `McpClientConfigRepository`, `McpClientConfigController`, `MCPToolsExporterImpl`). **Registry write path (`McpClientConfigRepository` + `McpClientConfigController`) lands only in brain**; both services host the pool/connector `.impl` (own pools) and read the one brain-owned registry. `gebo.architecture.mcp-server` is **not** split — brain-only.

For **each** candidate: **[C]** parent + children (4, or 5 with `.sdk`), **[M]** classes into their buckets *per-interface signature analysis, not wholesale package moves* (§5.8), **[C]** entity⇄DTO mappers where a `@Document` crosses, **[C]** server-proxy controllers, **[C]** client-proxy clients, **[W]** `@ConditionalOnMissingBean`/`@Profile` guards, **[T]** local-vs-remote binding test + `mvn dependency:tree` check that no `.interface-models` leaks Spring/Mongo.

### Phase 4 — Stand up remaining services
- **[C]** starter+app for **brain, textsearch, graphsearch, chunker**, and each **content service**.
- **[W]** Per-service `application.yml`: Mongo URI (shared brain DB vs own), Hazelcast membership, `<service>.inputq`, routing table, OpenSearch/Neo4j where applicable.
- **[W]** Implement Kafka and REST adapters to parity with Rabbit; make transport selectable by config.
- **[T]** Full ingestion workflow across all services (Testcontainers: Mongo, Rabbit/Kafka, OpenSearch, Neo4j) vs. monolith baseline.

### Phase 5 — Edge, publication, hardening
- **[C]** Gateway/BFF decision (§10.5); per-service OpenAPI + client generation strategy for `gebo.api.clients`.
- **[W]** Hazelcast LLM-config invalidation event on admin write (brain) → rebuild on vectorizator/graphsearch.
- **[W]** Content streaming: `GDocumentReference`-by-message + pull-bytes-via-server-proxy (§10.6).
- **[W]** Dead-letter, retry, idempotency, security-context propagation across the bus (§10.2/§10.4/§10.7).
- **[W]** CI: publish microservice artifacts (jars + Docker images) alongside the existing monolith image via a **matrix over the app modules** (`mvn -pl gebo.<service>.app -am deploy`); `distributionManagement` already points at GitHub Packages.
- **[T]** Coexistence conformance suite (§11.5).

### §11.5 Coexistence conformance tests — reuse monolith scenarios against a Dockerized split (settled §10.11)
**Coexistence is build-time**: the same commit builds *either* deployable; a running installation is monolith **xor** microservices. The invariant to protect is that *the same business code behaves identically in both builds*. Design:
- **New module on the microservices build** (e.g. `integration-tests/microservices-integration-tests`) that **reuses the existing scenarios and base classes**:
  - the harness in `gebo.architecture.integration.tests` (`AbstractGeboMonolithicIntegrationTests`, `runAndWaitDoneCheckingResults` overloads, fake-LLM);
  - **the concrete end-to-end suite under `gebo.ai.app/src/test`** — `GitContentSystemIntegrationTests`, `SharedFilesystemIntegrationTest`, `MCPServerIngestionIntegrationTest`, `WorkflowCompletionTest`, `DefaultAgentsNetworkTest`, `PromptsLibraryTest`, and the `virtualremotefs/**` fake-content-handler architecture harness (`TestVirtualFilesystemContentHandlerImpl`, `TestVirtualFilesystemRemoteConsumerServiceImpl`, `TestVirtualRemoteProjectEndpoint`, dispatcher config, …);
  - the `integration-tests/*` suites (`full-setup-and-use-integration-tests`) and the local-inference `ollama-integration-tests/*` tree.
  These are *the same scenarios and assertions the monolith already runs* — lifted to target the Dockerized topology. The `virtualremotefs` harness is especially apt: it's already a fake remote content system with its own `GProjectEndpoint`/consumer, so it exercises the §7.5 `GObjectRef` + registry path and the content→chunker funnel across services. (`WorkflowCompletionTest` directly guards the §6.8 completion authority.)
- **Topology via Docker/Testcontainers:** spin up each microservice's image + the gateway + infra (Mongo, Rabbit/Kafka, OpenSearch, Neo4j) — the same infra containers the monolith harness uses, plus the service images from the per-service Docker build.
- **Drive via per-service Java stub clients** (microservices analog of `gebo.monolithic.api.resttemplate.client`), pointed at the gateway (`BASE_PATH` per §16.11) — so the test calls the deployed services exactly as a real client would.
- **Assert identical outcomes** to the monolith run (ingestion end-state, RAG/chat results, KB state).
- **Plus targeted invariant tests:** bean-selection (`.impl` present → local; only `.client-proxy` → remote); `GMessageEnvelope` JSON round-trip incl. identity (§6.7) + trace (§8.1) + context blocks; exactly-once dedup on duplicate delivery (§6.6); doc-ref/vfolder funnel materialization single-writer (§7.3); identity-over-the-bus ACL parity (§6.7).

---

## 12. Sequencing, strategy, and risk

- **Strangler, not big-bang.** Every phase leaves the monolith green. Splits are additive: introducing `<module>.parent` with `.impl` re-aggregated into the monolith starter is behavior-preserving; microservice starters are opt-in via profile.
- **Prove the two hardest seams first** (Phases 1–2): the messaging bridge and the security split. If those hold, the rest is mechanical repetition of §5.
- **Biggest risks:** (1) hidden coupling surfacing during `.interface-models` extraction (§10.8); (2) delivery-semantics gap between in-process broker and external transports (§10.2); (3) security-context loss across the bus (§10.4); (4) shared-Mongo write contention (§10.1). Each has an owning open point above and must be ruled on before its phase.
- **Definition of done for the whole effort:** the same commit builds (a) the existing monolith bootable jar/Docker image unchanged, and (b) N microservice bootable jars/images; the full ingestion + chat/RAG workflow passes end-to-end in both shapes from identical business code.

---

## 13. Per-module split Bill of Materials (class → target child module)

This section is the executable mapping for Phase 2–3. Legend for target child: **IM** = `.interface-models` (POJO contract) · **SDK** = `.sdk` (Spring/Mongo-aware base classes) · **IMPL** = `.impl` (owner) · **SP** = `.server-proxy` · **CP** = `.client-proxy`. "Provide" = new code to author (not a move).

> **Mechanical classification rule** used below (holds across this codebase): `IG*`/`I*` interface → **IM**; `GAbstract*` / `Base*` extension base + `*RepositoryPattern` scaffolding → **SDK**; `*Impl`/`*DaoImpl`/`*Component`/`*Repository`/`@Document` entity/`*Controller` → **IMPL**; plain `model/**` DTO/enum/exception → **IM** (strip `@Document`, §5.8). Verify per file; the heuristic is a starting point, not a substitute for reading signatures.

### 13.1 `gebo.architecture.security` → `security.{interface-models,impl,server-proxy,client-proxy,secure-area}`
| Source (package) | → child |
|---|---|
| `services/IGUsersAdminService`, `IGUserWorkflowService`, `IGOauth2ConfigurationService`, `IGOauth2InitializationService`, `IGOauth2RuntimeConfigurationDao`, `IGOauth2ProvidersLibraryDao`, `IAclGrantedAccessorService`, `IGBackendOauth2LoginSPASupportService`, `IGHttpRequestAuthenticationManagerResolver`, `IdentityUtil`, `ReactiveIdentityUtil`, `RunAs*` | IM |
| `model/**` (`User`, `UsersGroup`, `CurrentUser`, `UserPrincipal`, `AuthProvider*`, `oauth2/**`, `EditableUser`, `UserWorkflow*`) | IM (POJO; `@Document` twins stay IMPL per §5.8) |
| `repository/**` (`UserRepository`, `UsersGroupRepository`, `Oauth2*Repository`) | IMPL |
| `services/impl/**` (`GUsersAdminServiceImpl`, `GOauth2ConfigurationServiceImpl`, `CustomUserDetailsService`, `AclGrantedAccessorServiceImpl`, `authmanagers/**`, `GJwtAuthenticationConverter`, `GOAuth2UserService`, …) | IMPL |
| `config/GeboAISecurityConfig`, `GeboAICorsFilter`, `WebMvcConfig`, `LdapConfiguration`, token filter chain + `IGHttpRequestAuthenticationManagerResolver` impl | **SECURE-AREA** (validation side) / IMPL (login side stays heimdall) |
| **Provide:** SP controllers wrapping `IGUsersAdminService`/ACL for cross-service user & ACL lookups; CP impls of those interfaces | SP / CP |

#### 13.1.1 Execution plan (measured from source — supersedes the outline above where they differ)

**The surface is far smaller than the module.** Measured across every consumer outside
`gebo.architecture.security*`:

| Consumed by other modules | Count | What it really is |
|---|---|---|
| `IGSecurityService` | 88 imports | the hot path |
| `ReactiveIdentityUtil` / `IdentityUtil` / `RunAs*` | 44 | **pure local helpers — no remote call, ever** |
| `UserRepository.UserInfos` | 28 | a **type**, unfortunately nested inside a Mongo repository |
| `UserRepository` / `UsersGroupRepository` **as an injected bean** | **4 files** | the only real DB coupling to break |
| `IAclGrantedAccessorService`, `IGUsersAdminService`, `IGOauth2RuntimeConfigurationDao` | 4 | genuine service calls |

**A client-proxy for `IGSecurityService` is impossible as a REST mirror.** 9 of its 16
methods take the *caller's own domain objects* — `filterCanDoAction(Collection<T extends
IGObjectWithSecurity & IAclGrantedResource>)`, `isCanAccess(IGObjectWithSecurity)`,
`checkBeingCreator(GBaseObject)`, `setAclAliases(List<T>)`. Those entity graphs cannot go
on the wire, and heimdall must not learn every domain model.

**But they don't need to.** Those 9 methods run `AclAccessCheck` (a static in
`gebo.base.model`) over local objects, and the *only* inputs they need from outside are the
current user's **identity, roles, groups** and the **ACL aliases**. Read the two impls and
the seam is tiny:

- `AclGrantedAccessorServiceImpl` injects exactly **`IAclAliasesDao` + `UsersGroupRepository`**.
- `GSecurityServiceImpl` touches the repositories for exactly three things: *look up a user*,
  *look up a user's groups*, *check a password*. Everything else is local computation.

**So do not duplicate the algorithm — extract the directory.** One small SPI, and the *same*
impl classes serve both worlds:

```java
interface IGSecurityDirectory {                 // security.interface-models
    UserInfos          findUserByUsername(String username);
    List<UsersGroup>   findGroupsOfUser(String username);
    List<UsersGroup>   findAllGroups();
    boolean            checkPassword(String username, String rawPassword);
}
```

| Implementation | Where | Backed by |
|---|---|---|
| `MongoSecurityDirectory` (module `gebo.security.directory.mongo`) | heimdall + monolith | `UserRepository`, `UsersGroupRepository`, `PasswordEncoder` |
| `RestSecurityDirectory` (module `gebo.microservices.security.client`) | every other microservice | heimdall SP, **request-scoped cache** |

**The two implementations live in two modules, and a service depends on exactly one.**
No property, no profile: which directory a service uses is decided by what it
*packages*, stated in its pom. That is the same shape as `gebo.secrets.impl` vs
`gebo.microservices.secrets.client`, and it is what makes the choice impossible to get
wrong at runtime. Critically, `MongoSecurityDirectory` must **not** sit in
`gebo.architecture.security`: that module is a transitive dependency of nearly
everything, so a directory inside it would be present everywhere, would always win the
`@ConditionalOnMissingBean` race, and the remote one could never activate — precisely
how `gebo.secrets.impl` used to defeat the secrets client. The server-proxy in turn
depends on the *local* directory, so a service can only **serve** the directory if it
**owns** it.

`GSecurityServiceImpl` and `AclGrantedAccessorServiceImpl` then depend on
`IGSecurityDirectory` instead of the repositories, and **ship unchanged to every service**.
The ACL algorithm keeps running locally on local objects; only the directory is remote. Without
the request-scoped cache every `filterCanDoAction` becomes a network round-trip — the cache is
not an optimization, it is what makes this viable.

**ACL aliases are replicated, not fetched.** `IAclAliasesDao` stays a *local* read-replica in
every service (owner writes + `IGClusterMessageBus` broadcast — the same Hazelcast bus the LLM
models replication already uses, §1.4). One consequence is load-bearing: `AclAliasesDaoImpl`
allocates alias integers from a **Mongo sequence**, and `aliasForEveryone()` will `addAcl(...)`
if an entry is missing. **Alias allocation must stay authoritative in the owner** — a replica
that allocates its own integers will collide with another service's. On a non-owner the DAO is
read-only; the everyone-aliases bootstrap is the owner's job.

**Two prerequisites, before any module moves:**

1. **Promote `UserInfos` out of `UserRepository`.** It is nested inside a `MongoRepository`, so
   it cannot live in `interface-models` where it belongs, yet 17 files import it purely as a
   type. Move it to a top-level type (keep a deprecated alias to stage the change).
2. **Fix the 4 files that inject the repositories directly** — `UsersFunctions` (an LLM tool,
   ships to brain), `GeboFastInstallationSetupService`, `SystemInitializationAdminService`,
   and the integration-test base. Convert to `IGUsersAdminService` / `IGSecurityService` calls.
   Behaviour-preserving, and it means the split never has to special-case them.

**One lever makes the `secure-area` split cheap.** `GeboAISecurityConfig` *already* gates the
two halves with `oauth2LoginEnabled` / `oauth2ResourceServerEnabled`. "Everyone is a resource
server; only heimdall does login and the OAuth2 handshake" is therefore largely a
**configuration** change today — the code split of the filter chain can follow the wiring, not
lead it.

**Phases** (each independently verifiable):

| # | Step | Done when |
|---|---|---|
| P0 | ~~Promote `UserInfos`~~ **DONE** (now `ai.gebo.security.model.UserInfos`, 36 imports rewritten; it can no longer drag a Mongo repository into a module that only wanted to name a user). Still to do: de-repository the 4 files that inject `UserRepository`/`UsersGroupRepository` | monolith green, no behaviour change |
| P1 | Extract `IGSecurityDirectory`; rewire `GSecurityServiceImpl` + `AclGrantedAccessorServiceImpl` onto it; `MongoSecurityDirectory` | monolith green — pure seam, no split yet |
| P2 | `gebo.microservices.security.controller` (SP, heimdall) + `gebo.microservices.security.client` (CP: `RestSecurityDirectory`, request-cached) | a service with the CP resolves users/groups/ACL through heimdall |
| P3 | ~~ACL alias replication~~ **SUPERSEDED - the ACL module split replaced it.** ACL is now the same owner/consumer pair as secrets and the directory: `gebo.acl.services` (contract: `IAclAliasesDao`, `AclAccessCheck`, `IAclGrantedResource`, models - extracted from `gebo.base.model` with the package name unchanged, so **not one import moved**), `gebo.acl.mongo` (the owner's Mongo DAO - **heimdall + monolith**), and `gebo.microservices.acl.client` (a REST client onto heimdall with a **local TTL cache** - everyone else). **No Hazelcast replication of the alias map is needed:** alias data is small, read on every decision and written almost never, so per-service caching of what is actually asked about beats every service maintaining a full replica. The Hazelcast cluster therefore stays as it is (LLM models only) and `ModelsReplicationClusterParticipationCondition` does **not** need widening. Alias *allocation* stays with the owner (`addAcl()` draws from a Mongo sequence; a second allocator would hand out colliding integers - a silent authorization bug), and the `EVERYONE` presets are bootstrapped only by the owner at startup. **Trade-off, explicitly:** a change made elsewhere - including a *revocation* - is visible to a consumer within `ai.gebo.acl.client.cache-ttl` (default 60s); a write made *through* a client invalidates its own cache at once. Shorten the TTL if that window is unacceptable. Discovery-based Hazelcast seeding (`DiscoveryClientClusterTopologyProvider`) was still worth keeping for the models cluster: it sees replicas and real addresses where the DNS-name seeding could not | a consumer sees an ACL change made on the owner within the TTL |
| P4 | Module split proper: `security.{interface-models, impl, secure-area}`; `security.controllers` → heimdall-only | every new module in `gebo.apps.monolithic.starter`; monolith identical |
| P5 | Per-service wiring: non-heimdall services take IM + secure-area + CP; heimdall takes impl + controllers + SP | non-heimdall service boots with no user/group Mongo access |

**Module → service map** (the monolithic starter depends on **all** of them, so the monolith is
unchanged; `.impl` present means the CP's `@ConditionalOnMissingBean` backs off, exactly as with
secrets §2.1):

| Module | heimdall | other microservices | monolith |
|---|---|---|---|
| `security.interface-models` | ✔ | ✔ | ✔ |
| `security.secure-area` (filter chain, resource-server, CORS, url rules) | ✔ | ✔ | ✔ |
| `security.impl` (repos, services/impl, login + oauth2 handshake, `LocalJwtTokenProvider`, system user) | ✔ | — | ✔ |
| `security.controllers` (the 12 controllers) | ✔ | — | ✔ |
| `gebo.microservices.security.controller` (SP) | ✔ | — | — |
| `gebo.microservices.security.client` (CP) | — | ✔ | — |

#### 13.1.2 Splitting `gebo.architecture.security` itself (P4) — measured plan

Everything so far split a *store* out from under the module. This splits the module.

**Only 8 of its ~45 classes touch a Mongo store.** That is the whole finding, and it
makes the carve far smaller than it looks:

| Touches a repository | Everything else |
|---|---|
| `CustomUserDetailsService`, `GUsersAdminServiceImpl`, `GUserWorkflowServiceImpl`, `UserRepositoryImpl`, ~~`GGeneratedApiKeyServiceImpl`~~ (extracted to `gebo.architecture.security.apikey`), `GBackendOauth2LoginSPASupportServiceImpl`, `GOauth2RuntimeConfigurationDaoImpl`, **`GeboAISecurityConfig`** | the ~37 classes of the request-authentication path, the token machinery, and local authorization |

**Target modules** (package names unchanged, so the carve itself rewrites **zero
imports** — the same trick that made the `ai.gebo.acl` extraction free):

| Module | Contents | Goes on |
|---|---|---|
| `security.interface-models` | `model/**`, `services/I*.java`, `exception/**`, `IdentityUtil`/`ReactiveIdentityUtil`/`RunAs*`, `SecurityHeaderUtil`, `CookieUtils`. No beans, no repositories | **everyone** |
| `security.secure-area` | the request-auth path: `GeboAISecurityConfig`, `GHttpRequestAuthenticationManagerResolverImpl`, `authmanagers/**`, the two converters, `JwtDecoderCache`, `LocalJwtTokenProvider`, `GPasswordEncoder`, `SecurityUtils`, the entry points, `GeboAICorsFilter`, `WebMvcConfig` — plus local authorization: `GSecurityServiceImpl`, `AclGrantedAccessorServiceImpl`, `GeboSystemUserServiceImpl` | **everyone** |
| `security.impl` | `repository/**`, `UserRepositoryImpl`, `GUsersAdminServiceImpl`, `GUserWorkflow*`, the whole OAuth2 **login/handshake** stack, the Mongo `CustomUserDetailsService`, `GOauth2RuntimeConfigurationDaoImpl` | **heimdall + monolith** |
| `gebo.architecture.security.apikey` **(done)** | generated API keys, whole: model + repository + service + both controllers | **heimdall + monolith** |
| `security.controllers` (exists) | the 12 controllers | **heimdall + monolith** |

**Two things every service needs that only the owner can currently answer.** These are
the real work; the module carve is code motion.

1. **`UserDetailsService`.** Every service turns a validated token into a principal
   (`LocalJwtAuthenticationManager` → `loadUserByUsername`), so this cannot be
   owner-only. But `CustomUserDetailsService` reads Mongo. Split it like the directory:
   the owner keeps the Mongo one (it has the password, so password login works);
   consumers get a **directory-backed** one built from `IGSecurityDirectory`
   (`UserInfos`: username, roles, disabled) with **no password**. That is not a
   limitation — **login only ever happens on heimdall** — and it means a password hash
   cannot leave the owner even by accident.

2. **`IGOauth2RuntimeConfigurationDao` — the one genuinely new pair, and it is a RUNTIME
   DAO, not a cached REST client.**
   `GHttpRequestAuthenticationManagerResolverImpl` needs it on *every* service to build
   the OAuth2 JWT/opaque-token managers, i.e. **to validate a user's provider token at
   all**; `GeboAISecurityConfig` reaches the same data through the Mongo repository
   directly. Consumers only ever **read** it (`findByProvider*`); `insert`/`delete` are
   admin operations on heimdall. Miss this and non-heimdall services silently cannot
   authenticate OAuth2 users. (`gebo.architecture.mcp-clients` reads it too.)

   **Would this make token validation a per-request call? YES, if implemented naively —
   and that is the trap.** `GAbstractRuntimeConfigurationDao` does **not** cache:
   `getDynamicConfigs()` calls the source every time, and
   `Oauth2RuntimeConfigurationSource.getConfigurations()` is `repo.findAll()`. Since
   `GHttpRequestAuthenticationManagerResolverImpl.resolve(...)` runs **per request**, the
   monolith today performs a full Mongo `findAll()` on **every OAuth2-authenticated
   request** (a pre-existing inefficiency). A REST-backed DAO would amplify that into a
   network round-trip to heimdall per request. Unacceptable.

   **So the consumer's DAO must be memory-resident.** Hold the resolved configurations in
   memory and refresh them on a **TTL of minutes** (`ai.gebo.security.client.oauth2-config-ttl`,
   suggested 5m). Per request: zero network — the resolver reads the in-memory config, JWT
   needs only `issuerUri` (and `JwtDecoderCache` already caches the decoder per issuer),
   opaque reads clientId/secret from that same object. (Opaque still introspects against
   the IdP per request; inherent to opaque tokens, unchanged from the monolith.)

   **A TTL is sufficient here — no cluster event bus.** Changing an OAuth2 provider
   registration is a rare administrative act, and a few minutes of propagation across the
   cluster is acceptable. The window means: a newly added provider is recognised within the
   TTL; a removed one is still trusted for up to the TTL; a rotated client secret makes
   opaque introspection fail (fail-closed) until it refreshes. All bounded and acceptable
   for an operation that happens rarely. This is a deliberate simplification over an
   event-driven invalidation, which would be more machinery than the change rate justifies.

   **The SP must strip the secret.** `Oauth2RuntimeConfiguration` is a `@Document` whose
   `client` field (a `GeboOauth2SecretContent`, secret included) is persisted **inline** —
   so naively serialising the config would ship the plaintext OAuth2 client secret to every
   microservice. The SP sends `clientSecretId` only; the consumer resolves it through the
   secrets client **at load time**, filling `client`. Once per refresh, never per request.

   (Contrast the secrets client, which caches by id and is safe doing so: a secret's content
   is **immutable under its id** — the admin surface creates and deletes, it never updates —
   so rotating a key produces a NEW id, i.e. a new cache key. The one in-place mutation is
   `GOauth2ConfigurationServiceImpl` rewriting the OAuth2 client secret under the same id on
   heimdall; that propagates to consumers within the secrets TTL, which the same rarity
   argument makes acceptable.)

**API keys are NOT a third pair, and are already extracted (DONE).** A generated key
*is* a `LOCAL_JWT`, so a service validating one needs **no lookup** — it verifies the
signature like any other token. The key store is read only when a key is minted, listed
or revoked, all administrative acts. So the whole vertical slice — model, repository,
service, and its two controllers — now lives in **`gebo.architecture.security.apikey`**, which ships
to the **monolith and heimdall only**. That removes one of the eight repository-touching
classes from the module about to be split, and takes a Mongo collection off every other
service's classpath. Packages stayed `ai.gebo.security.*`, so the move rewrote no import.

**Phases.** The risk is entirely in P4.1–P4.3; P4.5 is mechanical.

| # | Step | Done when |
|---|---|---|
| P4.1 | Rewire `GeboAISecurityConfig` off `Oauth2RuntimeConfigurationRepository` onto `IGOauth2RuntimeConfigurationDao` (no split yet) | monolith context test green |
| P4.2 | `UserDetailsService` owner/consumer pair (Mongo vs directory-backed) | monolith green; a consumer resolves a principal from a token with no user DB |
| P4.3 | `IGOauth2RuntimeConfigurationDao` pair: SP endpoint on heimdall + cached REST client | a consumer validates an OAuth2 user token with no user DB |
| P4.4 | The 4 files that still inject the repositories directly (`UsersFunctions`, 2× fastsetup, the integration-test base) | monolith green |
| P4.5 | Carve `security.{interface-models, secure-area, impl}` — pure code motion, packages unchanged, **no import rewrites** | full reactor + monolith context tests green |
| P4.6 | Per-service wiring: consumers take IM + secure-area + the clients; set `oauth2LoginEnabled: false` on them | a non-heimdall service boots with **no Mongo access to users/groups/oauth2** |
| P4.7 | heimdall takes `security.impl` + `security.controllers` (P2-T2/T4) | heimdall issues and refreshes a human token |

**Verification that actually means something:** the monolith's Spring context tests
(`ai.gebo.ai.app.tests.*`) are what caught the one real defect of the previous phase —
a constructor touching the crypting service before it was up, which took the whole
context down while every compile-only build stayed green. Run them at every phase.

### 13.2 `gebo.architecture.security.controllers` → mostly IMPL (the 12 controllers), acting as heimdall's SP
Verified constructor injections (→ these are the SP/IMPL wiring): `OAuth2AdminController(IGOauth2ConfigurationService)`; `AuthController(IGHttpRequestAuthenticationManagerResolver, UserRepository, PasswordEncoder, LocalJwtTokenProvider, IGBackendOauth2LoginSPASupportService)`; `AuthProvidersController(IGOauth2ConfigurationService, IOauth2DynamicClientRegistrationRepository, GeboSecurityConfig)`. All 12 → **IMPL** on heimdall; they *are* the server-proxy surface.

### 13.3 `gebo.architecture.llms.abstraction.layer` → `llms.{interface-models,sdk,impl,server-proxy,client-proxy}`
See Appendix B for the full class-bucket map. Summary: `IG*` service/DAO ifaces + `GBase*Config/Choice` + `G*ModelType` + `functions/model/**` + `vectorstores/{IGVectorStore*,model/**}` → **IM**; `GAbstractConfigurable*Model`, `BaseLLMSInvokingAndProvidingService`, `*RepositoryPattern` bases → **SDK**; `services/impl/**`, `vectorstores/impl/**`, `functions/**`, entities/repos → **IMPL**; `controllers/**` → **SP**. **Provide:** CP impls of `IGDocumentTranslator` + model-list/config-read for non-hosting callers.

### 13.4 `gebo.architecture.agents.abstraction.layer` → `agents.{interface-models,sdk,impl,server-proxy,client-proxy}`
| Source | → child |
|---|---|
| `services/IGAgentService`, `IGAgentsNetworkService(+Factory)`, `IGNetworkAgentService`, `IGReactiveAgentService`, `IGAgentsNetworkCrudService`, `IGAgentsNetworkCallerProxy(+Factory)`, `IAgentConfigDao`, `IAgentsNetworkDao`, `IGAgentServiceRuntimeDao`, `IGRoutingNetworkAgentService`, `IGDocumentsSearchNetworkAgentService` | IM |
| `model/**` (`GAgentConfig`, `GAgentsNetwork`, `GAgentRole`, `AgentsExchangeMessage*`, `AgentCapabilit*`, `TargetAgentEnvelope`, `AgentServiceDescriptor`) | IM (POJO; keep `getNetworkAgentName()` keying, §11.2-4) |
| `repository/**` (`AgentConfigRepository`, `AgentsNetworkRepository`) | IMPL |
| `services/GAbstractAgentService`, `GAbstract*NetworkService(+Factory)`, `GBase*NetworkAgentService`, `AbstractReactiveAgentServiceNetworkAdapter` | SDK |
| `services/impl/**`, `controllers/GeboAgent*AdminController`, `config/**` | IMPL (controllers → SP) |

### 13.5 `gebo.architecture.contentsystems.abstraction.layer` → `contentsystems.{interface-models,sdk,impl,server-proxy,client-proxy}`
| Source | → child |
|---|---|
| `IGContentManagementSystemHandler`, `IGVirtualFilesystem*Handler`, `IGWriteableContentManagementSystemHandler`, `IGRemoteVirtualFilesystemConsumingService`, `IGKnowledgeBaseBrowsingService`, `IGServerVirtualFilesystemBrowsingService`, `IG*ConfigurationDao`, `IGContentDispatchingEvaluator`, `IGIOCModuleContentsDispatcher*` | IM |
| `model/**` (`ContentHandshakeData`→POJO twin, `KnowledgeBaseContext`, `ServerFileSystemContext`, `SampledSystemCatalogues`, `AbstractNavigationCoordinates`, `StreamingPurpose`) | IM (§5.8) |
| `GAbstractContentManagementSystemHandler`, `GAbstractRemoteVirtualFilesystem*`, `GAbstractResourcesDisposerFactory`, `VirtualFilesystemNavigationLogic` | **SDK** (every content handler extends these) |
| `impl/**` (`GContentConsumerFactoryImpl`, `GContentDispatchingEvaluatorImpl`, `GServerVirtualFilesystemBrowsingServiceImpl`, `GDocumentReferenceEnricherMapFactoryImpl`, `GStandardWorkflowStatusHandlerImpl`, `repository/ContentHandshakeDataRepository`, `SampledSystemCataloguesRepository`), `controllers/**` | IMPL (controllers → SP) |

### 13.6 `gebo.architecture.search.abstraction.layer` → `search.{interface-models,sdk,impl,server-proxy,client-proxy}`
`model/**` (`SearchQuery/Result*`, `WebSearch*`, `SearchableSystemMetaData`) + `service/{ISearchService,INativeSearchService,ISearchServiceRepositoryPattern,IKeywordMatcherService,INativeQueryObject}` → **IM**; `AbstractWebSearchServiceImpl` → **SDK**; `service/impl/**` (`KeywordMatcherServiceImpl`, `SearchServiceRepositoryPatternImpl`) → **IMPL**. **Provide:** textsearch SP for `INativeSearchService`; CP for brain.

### 13.7 RAG workers (owner services; mostly IMPL + shared payloads to `workflow.ingestion.contract`)
| Module → owner | IM (to `workflow.ingestion.contract` or local) | IMPL |
|---|---|---|
| `gebo.ragsystem.content.vectorizator` → **vectorizator** | `IGEmbedder`, `IGDocumentChunkServiceAccessor`, `IGEmbeddingMessageReceiver`, `DocumentAccessResult` | `impl/**` (all `*ReceiverImpl`, `*EmitterComponent`, `*FactoryComponent`, `GEmbedderImpl`, `EmbeddingStandardWorkflowIngestionStepEnabledHandler`) |
| `gebo.ragsystem.content.fulltext.processor` → **textsearch** | step + payload types | `impl/**` |
| `gebo.ragsystem.content.graphrag_processor` → **graphsearch** | `IGraphRagProcessorMessagesReceiverFactoryComponent`, step + payload types | `impl/**` |

### 13.8 `gebo.architecture.mcp-clients` → `mcp-clients.{interface-models,impl,server-proxy,client-proxy}` (registry read-only shared)
`model/**` (`MCPClientConfig`, `MCPTool/Prompt/Resource`, `MCPTransportType`, `McpAuthMode`, `BaseMCPObject`) + `service/McpClientManagementService`, `MCPToolsExporter` → **IM**; `service/impl/**` (`McpClientManagementServiceImpl`, `McpClientConnector`, `McpClientPool`, `McpRemoteToolCallback`, `MCPToolsExporterImpl`) → **IMPL** (hosted by brain **and** mcp-content-handler, own pools); `repository/McpClientConfigRepository` + `controllers/McpClientConfigController` → **IMPL, brain only** (write path). **Provide:** optional CP if a service reads registry over REST instead of shared DB.

### 13.9 Content handlers — per-module split map (all `gebo.systems.parent.*`)
Content services split into **only two children**: a thin **IM** (the endpoint descriptor the platform needs to represent the source centrally as a `GCentralizedProjectEndpoint`, plus the handler's public interface) and a fat **IMPL** (everything else — the handler is otherwise self-contained). No `.sdk` (they *consume* `contentsystems.sdk`, they don't expose one). Server-proxy = the existing admin/browsing controllers. **Common bucketing:** `@Document` system/endpoint entities, `config/**`, `controllers/**`, `repositories/**`, `impl/**` (handler impl extending `contentsystems.sdk`, connection/client factories, DAOs, dynamic sources, navigation/search utils, `impl/model/**`, `search/model/**`), and the runtime `GIOC*ModuleDispatcherConfig` → **IMPL**. Only the endpoint-descriptor DTO + public `IG*Handler` interface → **IM**.

| Module → service | `@Document` entities (IMPL) | Public interface(s) (IM) | Repositories (IMPL) | Dispatcher config (IMPL, runtime) | Cross-service edge |
|---|---|---|---|---|---|
| `git` | `GGitContentManagementSystem`, `GGitProjectEndpoint` | `IGBaseGitContentManagementSystemHandler` | `GitEndpointRepository`, `GitSystemsRepository` | `GIOCGitContentsModuleDispatcherConfig` | — |
| `filesystem` | `GFilesystemContentManagementSystem`, `GFilesystemProjectEndpoint`, `GFileSystemShareReference` | `IGFilesystemContentManagementSystemHandler`, `IGFileSystemShareReferenceRuntimeDao` | `FilesystemProjectEndpointRepository`, `FilesystemShareReferenceRepository` | `GFilesystemContentsDispatcherConfig` | — |
| `uploads` | `GUploadsContentManagementSystem`, `GUploadsProjectEndpoint`, `TmpUploadedContents` | `IGUploadsContentManagementSystemHandler` | `TmpUploadedContentsRepository`, `UploadsProjectEndpointRepository` | `GUploadsModuleContentsDispatcherConfig` | — |
| `sharepoint` | `GSharepointContentManagementSystem`, `GSharepointProjectEndpoint` | `IGSharepointContentManagementSystemHandler`, `IGMicrosoftGraphVirtualFilesystem{Browsing,Consuming}Service` | `SharepointContentManagementSystemRepository`, `SharepointProjectEndpointRepository` | `GSharepointModuleContentsDispatcherConfig` | **`IGOauth2AccessTokenService` → heimdall** |
| `confluence` | `GConfluenceSystem`, `GConfluenceProjectEndpoint` | `IGConfluenceContentManagementHandler`, `IGConfluenceVirtualFilesystemConsumingService` | `ConfluenceSystemRepository`, `ConfluenceProjectEndpointRepository` | `GConfluenceContentsDispatcherConfig` | — |
| `jira` | `GJiraSystem`, `GJiraProjectEndpoint` | `IGJiraContentManagementHandler`, `IGJiraVirtualFilesystemConsumingService` | `JiraSystemRepository`, `JiraProjectEndpointRepository` | `GJiraContentsDispatcherConfig` | — |
| `googleworkspace` | `GGoogleDriveSystem`, `GGoogleDriveProjectEndpoint`, `GeboGoogleWorkspaceAccessSecret` | `IGGoogleDriveSystemContentHandler`, `IGGoogleDriveVirtualFilesystem{Browser,ConsumingService}` | `GoogleDriveSystemRepository`, `GoogleDriveProjectEndpointRepository` | `GGoogleDriveContentsDispatcherConfig` | Google OAuth via secrets + `GoogleWorkspaceAccessHandshakeController` |
| `userspace` | `GUserspaceContentManagementSystem`, `GUserspaceProjectEndpoint`, `GUserspaceFile` (+ `dto/**`) | `IGUserspaceContentManagementSystemHandler` | `UserspaceFileRepository`, `UserspaceProjectEndpointRepository` | `GUserspaceModuleContentsDispatcherConfig` | — |
| `integration` | `GIntegrationContentSystem`, `GIntegrationProjectEndpoint` (+ `model/{IntegrationDocumentEnvelop,JobTicket}`) | `IGIntegrationSystemContentHandler` | `IntegrationProjectEndpointRepository` | `GIntegrationContentsDispatcherConfig` | **`IGSecurityService` → heimdall**; `IntegrationInputController` = inbound push API |
| `mcl-client` | `GMCPClientSystem`, `MCPClientProjectEndpoint` | `IGMCPClientContentManagementHandler`, `IGMCPClientVirtualFilesystemConsumingService` | `MCPClientProjectEndpointRepository` | `GMCPClientContentsDispatcherConfig` | **`mcp-clients.interface-models` registry** (read-only, §10.17) |

**Provide per handler:** (1) the IM endpoint-descriptor DTO mapped from the `@Document` `G*ProjectEndpoint` (feeds brain's `GCentralizedProjectEndpoint`, §7.3); (2) the messaging emitter/receiver registration so the handler streams content into the ingestion workflow (already present as `*NestedEmitter`/dispatcher on several — e.g. `googleworkspace`'s `GoogleDriveSystemsNestedEmitter`, `userspace`'s `UserspaceControllerEmitter`); (3) keep admin/browsing controllers as the service's SP. Note: `sharepoint`, `confluence`, `jira`, `googleworkspace` carry their own `search/model/**` + a query translator (`SharePointKqlTranslator`/`ConfluenceCqlTranslator`/`JiraJsqlUtil`) — all IMPL, no cross-service impact.

### 13.9b `gebo.git.content.handler` full IMPL inventory (worked example)
`impl/**`: `GDefaultGitContentManagementSystemHandler`, `GAbstractGitContentManagementSystemHandler`, `GitClientService`, `GitEndpointConfigDao`, `GitEndpointDynamicSource`, `GitSystemDynamicSource`, `GitSystemsRuntimeConfiguratoinDao`, `GGitResourcesDisposerFactoryImpl`, `SShTransportConfigCallbackImpl`, `GIOCGitContentsModuleDispatcherConfig`; `config/**`: `GitDataSourcesConfig`, `GitSystemsConfig`; `controllers/GITSystemsController`. Injection (verified): `GitClientService` → `IGeboSecretsAccessService` [L] only. This is the reference shape every other handler mirrors.

### 13.10 `gebo.knowledgebase.model` (+ `.repositories`) → `knowledgebase.{interface-models,impl,server-proxy,client-proxy}`
Entities `GKnowledgeBase`, `GProject`, `GProjectEndpoint`, `GCentralizedProjectEndpoint`, `GDocumentReference`, `GVirtualFolder` → **IM as POJO twins** + **IMPL `@Document` entities** (§5.8, heavy — these are the most-shared types). `repositories/**` (`KnowledgeBaseRepository`, `ProjectRepository`, `DocumentReferenceRepository`, `VirtualFolderRepository`) → **IMPL, brain**. **Provide:** SP CRUD on brain; CP with local cache for workers/content services (or shared brain DB read for the workflow group, §7.3).

### 13.11 `gebo.architecture.graphrag.persistence` → `graphrag.persistence.{interface-models,sdk,impl,server-proxy,client-proxy}` (owner **graphsearch**)
| Source (package) | → child |
|---|---|
| `services/{IKnowledgeGraphSearchService,IKnowledgeGraphPersistenceService}` | **IM** (the graph query/persistence contract brain calls) |
| `persistence/model/**` query-result POJOs: `KnowledgeGraphSearchResult`, `ScoredChunk`, `ChunkHitRow`, `ChunkNeighborRow`, `ChunkMeta`, `KnowledgeExtractionData`, `KnowledgeExtractionEvent`, `GraphExtractionMatching`, `HitType` | **IM** (brain-consumed results; POJO, §5.8) |
| `persistence/model/**` Neo4j `@Node` entities: `GraphEntityObject`, `GraphEventObject`, `GraphRelationObject`, `GraphDocumentChunk`, `GraphDocumentReference`, `GraphEntityAliasObject`, `GraphEventAliasObject`, `Graph*InDocumentChunk`, abstract bases `AbstractGraphObject`/`AbstractGraphAliasObject`/`AbstractInDocumentChunkObject` | **IMPL** (Neo4j, graphsearch-local) |
| `persistence/repositories/**` (`GraphDocumentReferenceRepository`, `GraphDocumentChunkRepository`, `GraphEntity/Event/RelationObjectRepository`, `Graph*AliasObjectRepository`, `Graph*InDocumentChunkRepository`, `AbstractGraphObjectRepository`, `AbstractInDocumentChunkObjectRepository`) | **IMPL** |
| `services/impl/**` (`KnowledgeGraphSearchServiceImpl`, `KnowledgeGraphPersistenceServiceImpl`, `Graph{Entity,Event,Relation,EntityAlias,EventAlias}ObjectDao`, `AbstractGraphPersistenceService`, `AbstractNeo4jKnowledgeGraphObjectDao`, `GraphObjectReference`, `Neo4jDdlRunner`), `services/FastHashUtil` | **IMPL** (`Abstract*` are graphsearch-internal SDK, reused by graphrag_processor — same service, keep in IMPL/`.sdk` if graphrag_processor is a separate artifact) |
| **Provide:** graphsearch **SP** exposing `IKnowledgeGraphSearchService`; **CP** for brain (GraphRAG chat query, §1.6/§10.15). Persistence side (`IKnowledgeGraphPersistenceService`) is written by the graphrag_processor worker in-service — no cross-service CP needed for writes. |

Injection (verified, all graphsearch-local): `KnowledgeGraphSearchServiceImpl`/`KnowledgeGraphPersistenceServiceImpl` → the graph repositories + `Graph*ObjectDao` + `IGraphDataExtractionService` [from `graphrag.extraction`, co-located]; `Neo4jDdlRunner` → `Neo4jClient` [L]. **No cross-service injection** — brain reaches this module only through the `IKnowledgeGraphSearchService` client-proxy.

### 13.12 Note — `search.abstraction.layer` (§13.6) is class-complete
`model/**` (`SearchQuery`, `SearchResult`, `SearchResultReference`, `SearchResultAnalisysOutcome`, `SearchWithResults`, `WebSearchQueryObject`, `WebSearchResultsExtractionData`, `SearchableSystemMetaData`, `CatalogueSample`, `BaseSearchResultsExtractionDataType`, `SearchServiceException`) + `service/{ISearchService,INativeSearchService,ISearchServiceRepositoryPattern,IKeywordMatcherService,INativeQueryObject}` → **IM**; `AbstractWebSearchServiceImpl`, `CleanQueryUtil`, `KeywordListBuilder`, `LinkTypeGuesser` → **SDK**; `service/impl/{KeywordMatcherServiceImpl,SearchServiceRepositoryPatternImpl}` → **IMPL** (verified: no injected deps). Owner of native full-text search = **textsearch** (SP); web-search impls (`googlesearch`/`bingsearch`) live in **brain**; both call the same `ISearchService` contract.

### 13.13 `gebo.core` & cross-cutting modules — split by concern, mostly into brain (settled §10.18)
`gebo.core` is **not** a shared feature module (nothing consumes it via an interface) — it is the `CORE_MODULE` from `GStandardModulesConstraints`, i.e. **brain's own domain + orchestration core** plus a few strays. It therefore does **not** get the 5-child split; it decomposes by concern:
| Concern group in `gebo.core` | Classes | → destination |
|---|---|---|
| **KB/Project/Content domain erogation** | `KnowledgeBaseController`, `ProjectsController`, `ContentController`, `ContentMetaInfosController`, `UserKnowledgeBaseBrowsingController` | **brain** (with `knowledgebase.*`) |
| **`CORE_MODULE` workflow-orchestration hub** | `GComputeEndOfWorkflowReceiverFactory` (+ `model/ComputeWorkflowEndPayload`), `GCoreUserMessagesReceiverFactory` (`USER_MESSAGES_CONCENTRATOR_COMPONENT`), `GCoreMessagesEmitterImpl`, `GDisposeMongoContentsMessageReceiverFactoryImpl` (content disposal) | **brain** — the workflow-completion authority (§6.8) + user-message concentrator + doc-ref/vfolder **delete** owner (§7.4) |
| **Genuinely-separate strays** | `BuildSystemsController` → buildsystems owner; `CompanySystemsController` / `ReindexingFrequencyOptionsController` → brain admin (or config); `LogViewController` → ops/per-service (actuator loggers) or gateway | peeled to owners |

**Shared wire payloads are already externalized:** `GFinishedWorkflowPayload`, `GContentEmbeddingHandshakePayload`, etc. live in the separate **`gebo.core.messages`** module — the §6.9 shared-contract jar. Other services depend on `gebo.core.messages` (contract), never on `gebo.core` (impl). No split needed there.

**Same lens for the other cross-cutting modules:** `gebo.system.ingestion` → chunker/brain by concern; `gebo.jobs.services` → a scheduler/jobs owner; `gebo.config`/`gebo.config.services` → chassis (§8.1); `gebo.fastsetup` → brain setup (its `GeboFast*SetupController` family per §16.8). Each is split by concern, not shipped whole to every service.

---

## 14. Injected-interface Bill of Materials per implementation class (verified from source)

Extracted directly from the code (constructor params + `@Autowired` fields). **Owner tag** classifies each injected dependency: `[L]` local infra in every service · `[brain]`/`[chunker]`/… cross-service (→ `client-proxy` or shared-DB) · `[svc-local]` local to the owning service. This is the wiring that must resolve after the split.

> **Reproduce/extend for any module** (Git Bash): the injection map was generated with
> `rg -U --multiline-dotall '@Autowired\s*\n\s*(private|protected)?\s*[A-Za-z0-9_<>.]+\s+\w+\s*;' <module>/src/main/java` for field injection, and
> `rg -U '(public|protected)\s+[A-Z]\w+\s*\([^;]*?\)\s*\{' <module>` for constructor injection. Run per remaining module to complete this table.

### 14.1 vectorizator (`gebo.ragsystem.content.vectorizator.impl`) — owner **vectorizator**
| Impl class | Injected (interface / repo → owner) |
|---|---|
| `GEmbeddingMessageReceiverImpl` | `KnowledgeBaseRepository` [brain, shared-DB], `IGEmbeddingModelRuntimeConfigurationDao` [svc-local, Hazelcast-synced], `IGDocumentChunkServiceAccessor` [svc-local], `IGRuntimeBinder` [L] |
| `GEmbedderImpl` | `IGRuntimeBinder` [L], `VectorizedContentRepository` [svc-local, brain-DB] |
| `GDocumentChunkServiceAccessorImpl` | `IDocumentsChunkService` [**chunker** → CP/messaging] |
| `GContentVectorizationEmitterComponent` | `IGMessageBroker` [L] (ctor) |
| `GContentVectorizationMessagesReceiverFactoryComponent` | `IGRuntimeBinder` [L] (+ `GeboVectorizatorConfig` ctor) |
| `DocumentsVectorizationCountProvider` | `VectorizedContentRepository` [svc-local], `IGEmbeddingModelRuntimeConfigurationDao` [svc-local] |
| `VectorizatorDisposerMessageReceiverImpl` | `BeanFactory` [L] |

### 14.2 graphrag (`gebo.ragsystem.content.graphrag_processor.impl`) — owner **graphsearch**
| Impl class | Injected |
|---|---|
| `GraphextractionProcessorBatchReceiver` | `IDocumentsChunkService` [**chunker** → CP/messaging], `GeboGraphRagProcessorConfig` [svc-local], `IWorkflowRouter` [L/workflow-contract], `IGraphRagProcessorMessagesReceiverFactoryComponent` [svc-local], `IGMessageBroker` [L] |

### 14.3 fulltext (`gebo.ragsystem.content.fulltext.processor.impl`) — owner **textsearch**
| Impl class | Injected |
|---|---|
| `GContentFullTextMessagesReceiverFactoryComponent` | `IGRuntimeBinder` [L] |

### 14.4 llms.abstraction.layer (`services/impl`) — owner **brain** (hosted also by vectorizator/graphsearch)
| Impl class | Injected |
|---|---|
| `GChatModelRuntimeConfigurationDaoImpl` | `IGChatModelConfigurationSupportServiceRepositoryPattern` [svc-local], `IGPersistentObjectManager` [L] |
| `GEmbeddingModelRuntimeConfigurationDaoImpl` | `IGEmbeddingModelConfigurationSupportServiceRepositoryPattern` [svc-local], `IGPersistentObjectManager` [L] |
| `GImageModelRuntimeConfigurationDaoImpl` / `GTranscript…` / `GTextToSpeech…` | respective `IG*SupportServiceRepositoryPattern` [svc-local], `IGPersistentObjectManager` [L] |
| `GModelChoiceMetaInfoEnricherServiceImpl` | `IGModelsLibraryDao` [svc-local] |
| `GModelApiAccessReadUtilsimpl` | `IGeboSecretsAccessService` [L, secrets] |
| `GDefaultLlmsServiceClientsProviderImpl` | `GeboDefaultLlmsServiceClientsProviderConfig` [svc-local] |

*Note:* the `IG*RuntimeConfigurationDao` beans are the Hazelcast-synced config holders (§7.2); their `*SupportServiceRepositoryPattern` collaborators aggregate the provider modules' contributions — co-located with `.sdk` + providers.

### 14.5 contentsystems.abstraction.layer (`impl`) — owner **content services / brain** (KB writes)
| Impl class | Injected |
|---|---|
| `GContentConsumerFactoryImpl` | `IGPersistentObjectManager` [L]; `DocumentReferenceRepository`, `VirtualFolderRepository`, `ProjectRepository`, `KnowledgeBaseRepository`, `SoftwareArtifactsRepository`, `DependencyTreeRepository` [all **brain KB** → shared-DB for workflow group / CP otherwise] |
| `GContentDispatchingEvaluatorImpl` | `ContentHandshakeDataRepository` [svc-local/shared], `IGDocumentReferenceIngestionHandler` [svc-local], `IGDocumentsHashingService` [svc-local] |
| `GDocumentReferenceEnricherMapFactoryImpl` | `KnowledgeBaseRepository`, `ProjectRepository` [**brain KB** → shared-DB/CP] |
| `GLocalPersistentFolderDiscoveryServiceImpl` | `LocalEndpointMirrorRepository` [svc-local], `IGGeboConfigService` [L, config] |

### 14.6 security (`services/impl`) + controllers — owner **heimdall**
| Class | Injected |
|---|---|
| `CustomUserDetailsService` | `UserRepository` [svc-local heimdall] |
| `GOauth2ConfigurationServiceImpl` | `IGeboSecretsAccessService` [L], `IGOauth2RuntimeConfigurationDao` [svc-local], `IGOauth2ProvidersLibraryDao` [svc-local] |
| `AuthController` | `IGHttpRequestAuthenticationManagerResolver`, `UserRepository`, `PasswordEncoder`, `LocalJwtTokenProvider`, `IGBackendOauth2LoginSPASupportService` [all heimdall-local] |
| `AuthProvidersController` | `IGOauth2ConfigurationService`, `IOauth2DynamicClientRegistrationRepository`, `GeboSecurityConfig` [heimdall-local] |

### 14.7 chat.abstraction.layer (`services/impl`) — owner **brain** (constructor injection)
| Impl class | Injected |
|---|---|
| `GChatServiceImpl` | `IGChatModelRuntimeConfigurationDao` [svc-local], `IGToolCallbackSourceRepositoryPattern` [svc-local, agents/mcp tools], `IGPersistentObjectManager` [L], `IGPromptConfigDao` [svc-local], `InteractionsContextService` [svc-local], `IGSecurityService` [**heimdall** → CP], `IGChatResponseParsingFixerServiceRepository` [svc-local], `IGChatStorageAreaService` [svc-local], `LLMGeneratedResourceRepository` [svc-local], `IGKnowledgebaseVisibilityService` [svc-local, sec], `IGChatSessionLifeCycleService` [svc-local] |
| `GRagChatServiceImpl` | (`GChatServiceImpl` set) + `IGDocumentsSearchService` [**RAG query fan-out** → vectorizator/textsearch/graphsearch], `ChatProfilesRepository` [svc-local], `IGRuntimeChatProfileChatModelDao` [svc-local] |
| `GChatSessionStateShrinkerServiceImpl` | `IGChatModelRuntimeConfigurationDao`, `IGEmbeddingModelRuntimeConfigurationDao` [svc-local], `GeboChatConfigs`, `IGPromptConfigDao`, `ShrinkedChatSessionStateRepository`, `ChatFullSessionStateRepository`, `MinimalChatContextCacheItemRepository` [svc-local] |
| `SessionShrinkMessagesReceiver` | `GeboChatSessionLifeCycleConfig`, `IGChatSessionStateShrinkerService` [svc-local] (messaging receiver → external-adapter bridged) |
| `GRuntimeChatProfileChatModelDaoImpl` | `IGChatModelConfigurationSupportServiceRepositoryPattern`, `IGChatModelRuntimeConfigurationDao`, `IGPersistentObjectManager` [svc-local/L] |

### 14.8 agents.abstraction.layer (`services/impl`) — owner **brain** (constructor injection)
| Impl class | Injected |
|---|---|
| `TextProcessingTaskPerformerAgentService`, `TextProcessingRoutingNetworkAgentService` | `IGChatModelRuntimeConfigurationDao`, `IGToolCallbackSourceRepositoryPattern`, `IGPromptConfigDao`, `IGSecurityService` [**heimdall** → CP], `IAgentRoleDao`, `IGRuntimeBinder` [L], `IGDocumentContentRendererProvider` |
| `DefaultAgentsNetworkServiceReturnLastOutputValue` | `IGAgentServiceRuntimeDao`, `IAgentRoleDao`, `IGeboThreadManager` [L], `GAgentsNetwork`, `INotificationSink`, `ReactiveIdentityUtil` [sec], `IGAgentsNetworkRuntimeDao` |
| `AgentServiceRuntimeDaoImpl` | `List<IGGenericAgentService>`, `List<IGDynamicAgentServiceSupplier>` (aggregates provider beans → §5.7 SDK extenders) |
| `GAgentRoleDaoImpl` | `AgentRolesConfig` |

### 14.9 ragsystem.client.rest (`controllers`) — owner **brain** (chat erogation = SP surface)
| Controller | Injected |
|---|---|
| `GeboChatController` | `IGChatService` [svc-local] |
| `GeboRagChatController` | `IGRagChatService` [svc-local] |
| `GeboUserKnowledgeBaseSemanticSearchController` | `IGPersistentObjectManager` [L], `IGEmbeddingModelRuntimeConfigurationDao` [svc-local] |
| `PromptTemplateWizardController` / `PromptTemplatesController` / admin | `IGChatModelRuntimeConfigurationDao`, `IGPromptConfigDao`, `IGPersistentObjectManager` [svc-local/L] |

### 14.10 rag.support.layer (`services/impl`) — owner **brain**
| Impl class | Injected |
|---|---|
| `GSemanticSearchDocumentsCachedDaoImpl` | `IGGeboConfigService` [L], `IGPersistentObjectManager` [L], `IGContentManagementSystemHandlerRepositoryPattern` [content handlers → mixed/CP], `RagDocumentCacheItemRepository`, `IGDocumentReferenceIngestionHandler`, `DocumentReferenceSnapshotRepository`, `AIDocumentsCacheService`, `SimilaritySearchService` [svc-local], `IAclGrantedAccessorService` [**heimdall** → CP], `IGSecurityService` [**heimdall** → CP] |
| `SimilaritySearchService` | `DocumentReferenceSnapshotRepository` [svc-local] |

### 14.11 documents.cache (`service/impl`) — owner **chunker**
| Impl class | Injected |
|---|---|
| `DocumentsChunkServiceImpl` (the `IDocumentsChunkService` bean vectorizator/graphrag consume) | `IDocumentsCacheService`, `IGGeboConfigService` [L], `DocumentChunkOperationRepository`, `IGAIDocumentMetaDataEnricher`, `IGDocumentReferenceIngestionHandler`, `IGDocumentReferenceFactory`, `IGeboThreadManager` [L], `IGPersistentObjectManager` [L], `GeboDocumentsCacheConfig`, `ChunkingSessionRepository`, `IKeywordMatcherService` [search contract] |
| `DocumentChunkingMessagesReceiverFactoryComponent`, `ChunkingSessionDisposerReceiverFactory` | `IGRuntimeBinder` [L] (+ `GeboDocumentsCacheConfig`) — messaging receivers |

### 14.12 mcp-clients / mcp-server — owner **brain** (mcp-clients registry shared read-only)
| Class | Injected |
|---|---|
| `McpClientManagementServiceImpl` (Lombok `@AllArgsConstructor`) | `McpClientConfigRepository` [brain — **write path**], `IGSecurityService` [**heimdall** → CP], `McpClientConnector` [svc-local] |
| `GeboMcpServerRegistry` | `GeboMCPServerConfigRepository` [svc-local], `GeboMcpServerBuilder` [svc-local] |
| `GeboMCPServerAdminController` | `IGMCPServerConfigManagerService` [svc-local] |

### 14.13 Content handlers (`gebo.systems.parent.*`) — owner: each content service (all verified)
Common shape: `IGeboSecretsAccessService` [L] + own `config` + own endpoint/system **repositories** + `contentsystems.sdk` interfaces (`IGServerVirtualFilesystemBrowsingService`, `IGLocalPersistentFolderDiscoveryService`, `IGDocumentReferenceFactory`). Per-module specifics from source:
| Handler → service | Representative impl injections | Cross-service edge |
|---|---|---|
| `git` | `GitClientService` → `IGeboSecretsAccessService` [L] | — |
| `filesystem` | `FileSystemsManagementService` → `IGPersistentObjectManager` [L], `IGFileSystemShareReferenceRuntimeDao`; controllers → `IGServerVirtualFilesystemBrowsingService`, `GFilesystemConfigurationDao`, `GFilesystemChangesHandlingService` | — |
| `uploads` | `UploadsSystemsManagementServiceImpl` → `IGPersistentObjectManager`, `TmpUploadedContentsRepository`, `IGGeboConfigService` [L], `IGLocalPersistentFolderDiscoveryService`; controller → `IGReadableContentsFormatHandlerRepositoryPattern` | — |
| `sharepoint` | `GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl` → `IGeboSecretsAccessService`, `IGPersistentObjectManager`, `IGDocumentReferenceFactory`, `GMicrosoftGraphClientFactory`; `SharepointSystemsTestService` → `IGOauth2AccessTokenService` | **`IGOauth2AccessTokenService` → heimdall** (MS Graph OAuth) |
| `confluence` | `ConfluenceConnectionFactory` → `IGeboSecretsAccessService`, `RestTemplateWrapperService`; `ConfluenceBrowsingService` → `ConfluenceSystemRepository`, `ConfluenceConnectionFactory` | — |
| `jira` | `JiraApiClientFactory` → `IGeboSecretsAccessService`, `JiraSystemRepository`; `JiraBrowsingService` → `JiraApiClientFactory`, `RestTemplateWrapperService` | — |
| `googleworkspace` | `GeboGoogleWorkspaceCredentialsService` → `IGeboSecretsAccessService`, `GoogleDriveSystemRepository`, `IGPersistentObjectManager`; factories → `IGGeboConfigService` [L] | — |
| `userspace` | `UserspaceManagementServiceImpl` → `IGPersistentObjectManager`, `IGLocalPersistentFolderDiscoveryService`, `IGUserspaceContentManagementSystemHandler`, `UserspaceFileRepository` | — |
| `integration` | `IntegrationService` → `IGSecurityService`, `IntegrationProjectEndpointRepository`, `IGLocalPersistentFolderDiscoveryService`, `IGIntegrationSystemContentHandler` | **`IGSecurityService` → heimdall** |
| `mcl-client` | (content-handler shape) + `mcp-clients` registry | **`mcp-clients.interface-models` registry** (read-only, §10.17) |

Takeaway: content handlers are self-contained (secrets + own repos + local filesystem/discovery). The only cross-service edges are **heimdall** (`IGOauth2AccessTokenService` on sharepoint, `IGSecurityService` on integration — both covered by the chassis `security.client-proxy`) and **KB writes** (shared-DB/CP). No handler injects another handler or a worker.

### 14.14 graphrag.persistence / search.abstraction.layer — owner **graphsearch** / **textsearch**
| Impl class | Injected |
|---|---|
| `KnowledgeGraphSearchServiceImpl` / `KnowledgeGraphPersistenceServiceImpl` | `GraphDocumentReferenceRepository`, `GraphDocumentChunkRepository`, `GraphEntity/Event/RelationObjectDao` (+ their Neo4j repos), `Graph*InDocumentChunkRepository`, `GraphEntity/EventAliasObjectDao`, `IGraphDataExtractionService` [from graphrag.extraction, svc-local] — **all graphsearch-local (Neo4j)** |
| `Graph*ObjectDao` (entity/event/relation/alias) | their `Graph*Repository` (+ peer DAO) [svc-local] |
| `Neo4jDdlRunner` | `Neo4jClient` [L] |

`search.abstraction.layer.service.impl` (`KeywordMatcherServiceImpl`, `SearchServiceRepositoryPatternImpl`, `AbstractWebSearchServiceImpl`) have **no injected dependencies** (stateless/aggregating beans). **No cross-service injection in either module** — `graphrag.persistence` is queried by brain solely via `graphrag.persistence.client-proxy` (§1.6/§10.15); its `KnowledgeGraphSearchService` interface is the SP surface graphsearch exposes.

**Cross-service takeaways** (from the full sweep above): the injected types that actually cross a service boundary are a **small, closed set**:
1. **Brain KB repositories** (`KnowledgeBaseRepository`/`ProjectRepository`/`DocumentReferenceRepository`/`VirtualFolderRepository`) — pulled by the workflow group (vectorizator, contentsystems, chunker) → validates **shared brain DB** (§7.1/§7.3).
2. **`IDocumentsChunkService`** — pulled by vectorizator + graphrag → **chunker** boundary (CP or messaging).
3. **`IGSecurityService` / `IAclGrantedAccessorService` / `ReactiveIdentityUtil` / `IGOauth2AccessTokenService`** — pulled by chat, agents, rag.support, mcp-clients, **and content handlers** (`integration` → `IGSecurityService`; `sharepoint` → `IGOauth2AccessTokenService` for MS-Graph OAuth) → **heimdall** boundary (`security.client-proxy` + `secure-area`; §5.6). This is the most widespread cross edge and the reason `security.{interface-models,client-proxy,secure-area}` ships in every service.
4. **`IGDocumentsSearchService`** — pulled by `GRagChatServiceImpl` → the **RAG query fan-out** (brain aggregates vectorizator/textsearch/graphsearch); this is the one interface whose CP is itself a scatter-gather (§10.15 / open).
5. **`IGContentManagementSystemHandlerRepositoryPattern`** — pulled by rag.support → the set of content handlers; in microservices this becomes a registry of remote content services (CP-per-handler or a discovery service).

Everything else is local infra (`IGPersistentObjectManager`, `IGRuntimeBinder`, `IGeboThreadManager`, `IGGeboConfigService`, `IGeboSecretsAccessService`, `IGMessageBroker`) or co-located (LLM runtime DAOs, per-feature repos). **Net:** the synchronous cross-service surface is five interfaces, not a mesh — three resolve to heimdall/security, one to chunker, one is the RAG fan-out. That bounds the `.client-proxy` work sharply.

---

## 15. Per-microservice artifact Maven dependency BOM

Each `gebo.<service>.app` depends on `gebo.apps.<service>.starter`, which declares the modules below (all `${project.version}` unless external). Every service also inherits the **chassis** (§11 Phase 0): `gebo.application.messaging` + one external adapter (`…external.{rabbitmq|kafka|rest}`) + `security.interface-models` + `security.client-proxy` + `security.secure-area` + `gebo.config` + `gebo.secrets.services` + `gebo.webconfig` + `gebo.architecture.hazelcast` + `spring-boot-starter-web`/`webflux`. Only the **feature-specific** deps are listed per service.

| Service | Own feature (IMPL/SDK/SP) | Foreign contracts (IM + CP) | Data infra |
|---|---|---|---|
| **heimdall** | `security.impl`, `security.controllers.impl`, `security.server-proxy` | — | own Mongo |
| **brain** | `llms.{impl,sdk,server-proxy}` + all `gebo.llms.*` providers + `gebo.llms.standard.functions`; `chat.abstraction.layer.{impl,sdk}`; `agents.{impl,sdk,server-proxy}` + `gebo.architecture.agents.standard`; `knowledgebase.{impl,server-proxy}`; `search.{impl-web?,server-proxy}` hosting `gebo.googlesearch.handler`+`gebo.bingsearch.handler`; `gebo.ragsystem.client.rest`; `mcp-clients.impl` (+registry write), `gebo.architecture.mcp-server`; `rag.support.layer` | `documents.cache.{im,cp}` [chunker]; `graphrag.persistence.{im,cp}` [graphsearch]; `search.{im,cp}` for textsearch; `vectorizator.{im,cp}` (query counts); `workflow.ingestion.contract` | **shared brain Mongo** + Hazelcast; Neo4j? (no — via graphsearch) |
| **vectorizator** | `vectorizator.impl`, `gebo.ragsystem.vectorstores`, `rag-threasholds-autotune`, `llms.{impl,sdk}` + embedding provider(s) (`onxx-embeddings`, openai…) | `documents.cache.{im,cp}` [chunker]; `knowledgebase.im` (shared-DB read); `workflow.ingestion.contract` | **shared brain Mongo** + Hazelcast |
| **textsearch** | `fulltext.processor.impl`, `search.{impl,sdk,server-proxy}`, `gebo.architecture.fulltext`, `gebo.architecture.opensearch` | `documents.cache.{im,cp}` [chunker]; `workflow.ingestion.contract` | own Mongo + **OpenSearch** |
| **graphsearch** | `graphrag_processor.impl`, `graphrag.extraction`, `graphrag.persistence.{impl,server-proxy}`, `gebo.architecture.neo4j`, `llms.{impl,sdk}` + provider | `documents.cache.{im,cp}` [chunker]; `knowledgebase.im` (shared-DB read); `workflow.ingestion.contract` | **shared brain Mongo** + **Neo4j** + Hazelcast |
| **chunker** | `documents.cache.{impl,server-proxy}`, `contentsystems.{impl,sdk}` (dispatch/handshake side) | `knowledgebase.im` (shared-DB); `workflow.ingestion.contract` | **shared brain Mongo** |
| **content svc `X`** (git, filesystem, sharepoint, confluence, jira, googleworkspace, uploads, userspace, integration) | `X.impl` + `X.server-proxy`, `contentsystems.{impl,sdk}` | `knowledgebase.im` (KB write via shared-DB or brain CP); `workflow.ingestion.contract` | own Mongo |
| **mcp-content-handler** | `gebo.mcl-client.content.handler.impl`, `contentsystems.{impl,sdk}`, `mcp-clients.impl` (pool/connector, **no** registry write) | `mcp-clients.im` (registry, read-only); `knowledgebase.im`; `workflow.ingestion.contract` | own Mongo + **read brain MCP registry** (§10.17) |
| **gateway** (§16.11) | Spring Cloud Gateway routes `/<service>/**`; may serve the Angular UI static bundle | `security.secure-area` (edge CORS/optional pre-auth; per-service `secure-area` still validates) | none (stateless) |

**Rules encoded in the table (invariants to lint in CI):**
1. A service lists a feature's `.impl`/`.sdk` **or** its `.interface-models`+`.client-proxy` — **never both** (that would shadow the remote bean; §9.3).
2. `.server-proxy` appears only in the feature's **owning** service.
3. `.interface-models` never transitively drags Spring/Mongo (checked via `mvn dependency:tree`); if it does, a `@Document` leaked (§5.8).
4. `security.secure-area` + `security.interface-models` + `security.client-proxy` appear in **every** non-heimdall service; `security.impl`/`controllers.impl`/`server-proxy` appear **only** in heimdall.
5. Shared-brain-DB services (brain, vectorizator, graphsearch, chunker, + mcp-registry read) get per-service Mongo credentials scoped to their owned collections (§10.1).

---

## 16. Service erogation map — REST controllers per microservice

Two erogation channels, cleanly separated:
- **REST (this section)** = synchronous request/response — admin/config CRUD, user-facing queries (chat, browsing, search), and the cross-service `server-proxy` endpoints for the 5 boundary interfaces (§14 takeaways).
- **Queues (§17)** = asynchronous ingestion workflow only.

Legend: **[E]** existing controller (moves with its module's `.impl`) · **[SP]** new `.server-proxy` controller to author for cross-service erogation · **[chassis]** shared infra/setup controller assigned by concern (or hosted on the edge/BFF, §10.5) · **[R]** erogates **reactive** signatures (`Flux`/SSE `text/event-stream`) — requires WebFlux on that service's chassis and a streaming-preserving client-proxy (§16.10).

### 16.1 heimdall
| Controller | Kind | Serves |
|---|---|---|
| `AuthController`, `AuthProvidersController`, `TokenRenewController` | E | login / token issue+refresh / provider discovery |
| `OAuth2AdminController`, `Oauth2ModuleStatusController`, `Oauth2StartLoginAttemptController`, `Oauth2SPAAuthorizationDeliveryController` | E | OAuth2 admin + SPA login flow |
| `UserController`, `UsersAdminController`, `UserWorkflowsController` | E | self-service + user administration |
| `GeneratedAdminApiKeyController`, `GeneratedUserApiKeyController` | E | API-key issuance |
| `UsersLookup` / `AclCheck` | **SP** | cross-service user & ACL resolution (backs every service's `security.client-proxy`) |

### 16.2 brain
| Controller | Kind |
|---|---|
| `ChatModelsController`, `EmbeddingModelsControllers`, the `Base*/Abstract*` model CRUD bases + **every provider CRUD** (`OpenAI/Anthropic/Mistral/Ollama/Deepseek/GoogleVertex/GenericOpenAI/ONNX …ChatModels/EmbeddingModels/RankerConfigurationController`) | E — LLM model config (Hazelcast-synced, §7.2) |
| `GeboVectorStoreConfigurationController` | E — vector-store config (brain-admin, shared DB) |
| `GeboAgentAdminController`, `GeboAgentsNetworkAdminController` | E — agents/networks admin |
| `GeboChatController` **[E,R]**, `GeboRagChatController` **[E,R]**, `GeboUserChatsController`, `GeboUserChatUploadsController`, `GeboDeepSearchController`, `GeboLLMGeneratedResourceController` | E — user chat / RAG / deep-search erogation (`GeboChatController.streamResponse`, `GeboRagChatController.streamRagResponse` return `Flux<ServerSentEvent<String>>` over SSE, alongside blocking endpoints) |
| `GeboChatPipelinesController` **[E,R]** (`streamDefaultChatPipeline`/`streamChatPipeline` → `Flux<GeboChatMessageEnvelope>` SSE), `GeboAdminChatProfilesConfigurationController`, `GeboChatProfileLookupController`, `PromptTemplatesController`, `PromptTemplateWizardController`, `GeboAdminPromptsController`, `GeboAdminPromptUseInfoController`, `GeboDeepSearchAdminController`, `GeboAdminRagAutotuneController` | E — chat/prompt/pipeline/autotune admin |
| `GeboUserKnowledgeBaseSemanticSearchController` | E — semantic search entry (scatter-gathers workers, §16.9) |
| `KnowledgeBaseController`, `ProjectsController`, `UserKnowledgeBaseBrowsingController`, `ContentController`, `ContentMetaInfosController` | E — KB/project (brain owns entities) |
| `McpClientConfigController` | E — MCP registry **write** (brain only, §10.17) |
| `GeboMCPServerAdminController`, `GeboMCPServerUserController` | E — MCP-server (agents-as-tools) admin/user |
| `GoogleSearchController`, `GoogleSearchConfigurationController` (+ bing) | E — web search |
| `KnowledgeBase/Project CRUD` | **SP** — `knowledgebase.server-proxy` for workers/content svcs |
| `DocumentsSearch` (semantic RAG query) | **SP** — the fan-out aggregation endpoint `IGDocumentsSearchService` |

### 16.3 vectorizator
| Controller | Kind |
|---|---|
| `VectorizationStatus` (counts from `DocumentsVectorizationCountProvider`) | **SP** |
| `IGEmbedder` on-demand embed (optional) | **SP** — for callers needing embeddings without hosting clients |
| *(rag-autotune admin stays on brain `GeboAdminRagAutotuneController`; compute runs here, §10.14)* | — |

### 16.4 textsearch
| Controller | Kind |
|---|---|
| `FullTextSearch` (`INativeSearchService` query) | **SP** |
| `FullTextIndexStatus` | **SP** |

### 16.5 graphsearch
| Controller | Kind |
|---|---|
| `GraphSearch` (`IKnowledgeGraphSearchService` query) | **SP** — GraphRAG query for brain (§10.15) |
| `GraphRagConfigurationController` | E — extraction config |
| `GeboNeo4jModuleSetupController` | E — Neo4j setup |

### 16.6 chunker
| Controller | Kind |
|---|---|
| `DocumentChunking` (trigger/status of `IDocumentsChunkService`) | **SP** — mostly messaging-driven (§17); thin REST |
| `IngestionFileTypesLibraryController` | E — ingestion file-type config |

### 16.7 content services (one per handler)
| Service | Controllers (all E) |
|---|---|
| git | `GITSystemsController` |
| filesystem | `FileSystemsController`, `FileSystemsBrowsingController`, `FileSystemSharesSettingController` |
| uploads | `FileUploadController`, `FileUploadsController` |
| sharepoint | `SharepointSystemsController`, `SharepointBrowsingController` |
| confluence | `ConfluenceSystemsController`, `ConfluenceBrowsingController` |
| jira | `JiraSystemsController`, `JiraBrowsingController` |
| googleworkspace | `GoogleDriveSystemsController`, `GoogleDriveBrowsingController`, `GoogleWorkspaceAccessHandshakeController` |
| userspace | `UserspaceController`, `UserspaceUploadController` |
| integration | `IntegrationSystemsController`, `IntegrationInputController` (inbound push API) |
| mcp-content-handler | `MCPClientSystemsController`, `MCPClientBrowsingController` |
| **all** | `ContentsResetController` (from `contentsystems.abstraction.layer`, per-service) |

### 16.8 Shared chassis / setup controllers (assigned by concern, or on the edge/BFF §10.5)
`GeboModulesConfigController` (config), `SecretsController` (secrets), `LanguageResourcesController`/`UITextResourcesController` (i18n), `GeboAngularFormGroupMetaInfoController` (Angular metadata), `JobLauncherController` (jobs), `LogViewController`, `BuildSystemsController`, `CompanySystemsController`, `ReindexingFrequencyOptionsController`, and the `GeboFast*SetupController` family (`Installation/ChatProfile/KnowledgeBase/VectorStore/WorkFolder/LLMS/AdvancedSetupStatus`). These are cross-cutting: split by concern (LLM/chat/KB setup → brain; vector-store/work-folder setup → brain admin over shared DB; module config → per service) or centralized behind the gateway/BFF. **Decision owed = §10.5.**

### 16.9 Erogation invariant
A service exposes **[SP]** only for interfaces it **owns**; it **consumes** foreign services through its `.client-proxy` beans (which call those services' `[SP]`), never by importing a controller. The one aggregation exception is brain's `DocumentsSearch` **[SP]**, which scatter-gathers vectorizator/textsearch/graphsearch and merges — it is a composition endpoint, not a passthrough.

### 16.10 Reactive erogation (dual-stack — Servlet + WebFlux)
The codebase is intentionally dual-stack (`spring-boot-starter-web` **and** `spring-boot-starter-webflux` in the monolith starter). **Verified reactive erogation surface = 3 controllers, all in brain**, all streaming chat tokens over Server-Sent Events:

| Controller (brain) | Reactive endpoint(s) | Signature |
|---|---|---|
| `GeboChatController` | `POST streamResponse` | `Flux<ServerSentEvent<String>>`, `produces=text/event-stream` |
| `GeboRagChatController` | `POST streamRagResponse` | `Flux<ServerSentEvent<String>>`, SSE |
| `GeboChatPipelinesController` | `POST streamDefaultChatPipeline`, `POST streamChatPipeline` | `Flux<GeboChatMessageEnvelope>`, SSE |

These sit **beside** blocking endpoints in the same controllers (e.g. `GeboChatController` also has non-stream POSTs), so brain must run the full dual stack. Backing services are the reactive layer already present: `IGReactiveAgentService`, `IGReactiveOutputAgentsNetworkService`, `ClientChatCallUtil` streaming.

**Consequences for the split:**
1. **Brain chassis must include WebFlux** (§15 already lists it); no other service erogates reactive signatures today, so vectorizator/textsearch/graphsearch/chunker/content services can run Servlet-only unless they later stream.
2. **Streaming must be preserved end-to-end.** If the UI/gateway reaches chat through a `.client-proxy` or gateway hop, that hop must use a **reactive `WebClient` that passes the `Flux`/SSE through** — never `.block()` or buffer the full response, or token-by-token streaming is lost. Flag chat CP/gateway routes as **no-buffer, SSE-transparent**.
3. **heimdall's `secure-area` must validate tokens reactively on brain's WebFlux endpoints** — the reactive auth stack already exists (`ReactiveIdentityUtil`, `GReactiveOauth2AuthorizedClientService`, `ReactiveGOAuth2UserService`, `Oauth2DynamicReactiveRegistrationRepository`), so `security.secure-area` ships both the Servlet filter chain and the WebFlux `SecurityWebFilterChain`.
4. **These endpoints stay REST, not queues** (§17.6) — SSE *is* the streaming response channel; the ingestion queues are unrelated.

### 16.11 API gateway & per-service UI stubs — **Spring gateway + `@gebo.ai/<service>` stubs, `BASE_PATH`-only difference** (settled, closes §10.5 + §10.19)

**Edge:** a **Spring Boot-integrated gateway** (Spring Cloud Gateway) is the single ingress. It routes `/<service>/**` → the owning microservice — **discovering routes from the participant registry (§7.5)** rather than hard-coding them, so a third-party participant that joins the registry becomes routable without a gateway rebuild — and, being WebFlux/reactive, **passes SSE streaming through transparently** (§16.10) with no buffering. Cross-cutting edge concerns (CORS, rate-limiting, routing) live at the gateway; **per-request token validation stays in each service's `secure-area`** (§5.6) — heimdall issues tokens, the gateway forwards the `Authorization` header, each service validates. The gateway can also host/serve the Angular UI static bundle.

**Per-service OpenAPI + stub libraries:** each microservice enables the `swagger-on` profile (as the monolith does via `gebo.architecture.swagger`) and publishes its **own OpenAPI**. From each we generate — with the *same* swagger-codegen toolchain used today — a **per-service Angular stub library published as `@gebo.ai/<service-name>`** (and, where a backend caller needs it, a per-service Java resttemplate client mirroring `gebo.monolithic.api.resttemplate.client`). So the single monolithic stub becomes N per-service stubs, each generated exactly like the current one.

**UI wiring:** the Angular UI imports `@gebo.ai/<service>` per service instead of the one monolithic stub. **All other UI/business code is unchanged.**

**The only monolith ↔ microservices difference is each stub's `BASE_PATH`** (the swagger-codegen `BASE_PATH` DI token per generated module, set from the Angular environment):
| Deploy | Each `@gebo.ai/<service>` stub's `BASE_PATH` |
|---|---|
| **Monolith** | the **same** origin (all controllers in one app) |
| **Microservices** | the service's **gateway relative path** `/<service>` (gateway routes to the service) |

This is the UI-layer expression of the coexistence principle (§3): *identical code, config-only difference.* It closes **#19** — per-service OpenAPI + per-service generated clients, aggregated behind one gateway, SSE preserved by the reactive gateway.

**Build/CI implication:** the existing regen flows (`package-run-regen-rest`, `package-run-regen-java-client`) become **per-service** — regenerate each `@gebo.ai/<service>` stub against that service's running OpenAPI, publish under the `@gebo.ai` npm scope. In the monolith build, the aggregate OpenAPI still yields the current single stub (unchanged), so both shapes keep working.

---

## 17. Queue & topic map — the ingestion workflow

### 17.1 Model
- Every microservice runs its **internal in-memory broker** (unchanged from the monolith) **plus** an **external ingress topic**: `**<service>.inputq**`. It is a **topic** (pub/sub) — any service *may* publish to it, but in practice **only the ingestion workflow uses the queues**; all other cross-service interaction is synchronous REST (§16).
- The external adapter (§6, RabbitMQ/Kafka/REST) bridges **only** envelopes whose target is another service; intra-service messages stay in-JVM. Inbound on `<service>.inputq`, the adapter deserializes the `GMessageEnvelope` and hands it to the local broker — indistinguishable from an in-process message thereafter.
- **Delivery is exactly-once (§6.6):** every consumer dedups on `GMessageEnvelope.id` (unique-indexed processed-store) and writes idempotent/upsert sinks; transport runs at-least-once with post-process ack + per-queue dead-letter. So redelivery/fan-out replays are no-ops.
- **The bus is credentialed (§6.7.3):** every `<service>.inputq` is behind broker authentication — each service connects with its own credentials (RabbitMQ user/vhost, Kafka SASL, or REST mutual-auth), managed via `gebo.secrets.services`, optionally with per-queue publish/consume ACLs. This is what makes the in-envelope identity (§6.7) trustworthy: only platform services can emit.
- **Canonical topology = `GStandardWorkflowStep` (§6.8):** the step graph (`DOCUMENT_DISCOVERY → TOKENIZATION → fan-out {EMBEDDING, GRAPHEXTRACTION, FULLTEXT_INDEXING}`) is the authoritative flow; step enablement is config-resolved on chunker, completion tracked by brain (§6.8).
- **Routing is registry-resolved, not hand-wired (§8.3):** each `messagingModuleId` (from `GStandardModulesConstraints`) is **owned by exactly one microservice**, and every participant publishes the module ids it owns into the shared **`messagingModuleId → microservice` map**. So an envelope with `targetModule = M` routes deterministically to the owner of `M` and its `<service>.inputq` — architecture-wide, no per-service route tables. (The RabbitMQ adapter's explicit `outbound-routes` config is the bootstrap form until the registry is live, P0-T7.)

### 17.2 Queue inventory
| Topic | Owner | Primary producers | Primary consumers | Payload types (from code) |
|---|---|---|---|---|
| `chunker.inputq` | chunker | **content services** (on sync; `GIOCContentConsumer` forwards refs) | `DocumentChunkingMessagesReceiverFactoryComponent` | `DocumentReferenceForwarded` / `VirtualFolderForwarded` (chunker materializes into shared brain DB, §7.3), `ContentSyncedForChunking` (`GDocumentReference` / `ContentHandshakeData`) |
| `vectorizator.inputq` | vectorizator | **chunker** (chunk-ready), self (disposer) | `GEmbeddingMessageReceiverImpl`, `VectorizatorDisposerMessageReceiverImpl` | `ChunkReady`, `ChunkingSessionDisposer` |
| `textsearch.inputq` *(disable-able)* | textsearch | **chunker** (chunk-ready) | `FullTextIndexingBatchMessageReceiver` | `ChunkReady` |
| `graphsearch.inputq` *(disable-able)* | graphsearch | **chunker** (chunk-ready) | `GraphextractionProcessorBatchReceiver` | `ChunkReady` |
| `brain.inputq` | brain | workers (step-done), self (session shrink) | `SessionShrinkMessagesReceiver`, workflow-completion listener | `SessionShrinkRequest`, `IngestionStepDone` |
| `<content-service>.inputq` | each content svc | brain admin / scheduler | handler re-sync receiver | `ResyncCommand`, `ReindexCommand` |
| `heimdall.inputq` | heimdall | user-workflow events | mail/ticket receiver | `UserWorkflowEvent` (mostly REST; queue optional) |

### 17.3 Ingestion flow (the algorithm that uses the queues)
```
 content-service                chunker                    workers (fan-out; last two optional)
 ───────────────                ───────                    ────────────────────────────────────
 sync/crawl content
   │  ContentSyncedForChunking
   └───────────────────────────▶ chunker.inputq
                                   │  DocumentsChunkService.chunk()
                                   │  (documents.cache, shared brain DB)
                                   │  on complete → ChunkReady
                                   │      fan-out to ENABLED steps only
                                   ├────────────────────────▶ vectorizator.inputq   (embed → shared brain Mongo)
                                   ├───(if enabled)─────────▶ textsearch.inputq      (index → OpenSearch)
                                   └───(if enabled)─────────▶ graphsearch.inputq     (graph → Neo4j + brain Mongo)
                                                                     │ each worker on done:
                                                                     │   IngestionStepDone ─▶ brain.inputq
                                   ◀── ChunkingSessionDisposer ──────┘ (release cached chunks once all enabled steps ack)
```

1. **Content sync → chunker.** A content handler, on sync, publishes `ContentSyncedForChunking` (carrying the `GDocumentReference`; bytes are pulled by chunker from the content service's streaming `[SP]`, not sent over the bus — §10.6) to **`chunker.inputq`**.
2. **Chunk → fan-out.** The chunker runs `DocumentsChunkService`; on completion it publishes `ChunkReady` to **the enabled subset** of `{vectorizator, textsearch, graphsearch}.inputq`. Fan-out targets are populated via `GMessageEnvelope.onProcessForwardDestinations`.
3. **Workers process.** vectorizator embeds → shared brain Mongo; textsearch indexes → OpenSearch; graphsearch builds graph → Neo4j (+ brain Mongo projection). Each emits `IngestionStepDone` to `brain.inputq` for completion tracking (§10.7).
4. **Disposal.** Once all *enabled* steps ack, a `ChunkingSessionDisposer` releases the cached chunk data (chunker + vectorizator disposer receivers).

### 17.4 Enabling / disabling steps
The fan-out target set = the **enabled** `StandardWorkflowIngestionStep`s (§6.5). **vectorizator is mandatory; textsearch and graphsearch are optional** and toggled per environment via `application.yml` (e.g. `gebo.ingestion.steps.textsearch.enabled=false`). Disabled steps are simply omitted from the chunker's fan-out — no code change, and the `IngestionStepDone` completion set adjusts accordingly. This is the microservices form of the in-process "step enabled handler" (§10.13).

### 17.5 Transport binding (per §6)
| Transport | `<service>.inputq` realized as | Fan-out realized as |
|---|---|---|
| **RabbitMQ** | a durable queue bound to a topic exchange with routing key `<service>.inputq` | chunker publishes once per enabled target routing key |
| **Kafka** | a topic `<service>.inputq`, one consumer group per service | chunker produces to each enabled target topic |
| **REST** | the service's `POST /_inbox` endpoint | chunker `WebClient`-POSTs to each enabled target's `/_inbox` |
Selected per deployment via `application.yml`; the business modules never see the transport (§6.3).

### 17.6 What does NOT go on the queues
Chat, agent calls, KB/project lookups, security/ACL checks, GraphRAG/semantic/full-text **queries**, and MCP registry reads are **synchronous REST** (§16) — request/response with a return value. The queues carry only the fire-and-forward ingestion pipeline, where the producer needs no immediate answer and fan-out/optionality/retry matter.

---

## Appendix A — Module → target service quick map

| Existing module | Split? | Owner service | Consumed-by (client-proxy) |
|---|---|---|---|
| `gebo.architecture.security(.controllers)` | yes (+secure-area) | heimdall | all |
| `gebo.knowledgebase.model` / `.repositories` | yes | brain | vectorizator, graphsearch, chunker, content svcs |
| `gebo.architecture.llms.abstraction.layer` | yes | brain (write) | vectorizator, graphsearch (replicated read via Hazelcast) |
| `gebo.architecture.documents.cache` | yes | chunker | brain (trigger) |
| `gebo.ragsystem.content.vectorizator` | yes | vectorizator | brain (query) |
| `gebo.ragsystem.content.fulltext.processor` | yes | textsearch | brain (query) |
| `gebo.ragsystem.content.graphrag_processor` | yes | graphsearch | brain (query) |
| `gebo.ragsystem.client.rest`, chat/agents | yes | brain | — |
| `gebo.googlesearch.handler`, `gebo.bingsearch.handler` | no (stay in brain) | brain | — |
| each other `gebo.systems.parent` handler | yes | its content service | brain (orchestration), chunker (pull) |
| `gebo.application.messaging` | no (in every service) | — | — |
| `gebo.architecture.search.abstraction.layer` | yes (+sdk) | textsearch (native impl) | brain (query) |
| `gebo.architecture.mcp-clients` | yes (registry) | brain (registry writes) | mcp-content-handler (shared `MCPClientConfig` registry; §10.17) |
| `gebo.architecture.mcp-server` | no (stay in brain) | brain | — |
| `gebo.mcl-client.content.handler` (MCP content service) | yes | mcp-content-handler | brain (orchestration) — shares MCP config DB/registry with brain |
| `gebo.workflow.ingestion.contract` | **new** | shared contract | chunker, vectorizator, textsearch, graphsearch, brain |
| `gebo.application.messaging` | no (in every service) | — | — |
| `gebo.application.messaging.external.{rabbitmq,kafka,rest}` | **new** | — (chassis) | — |

---

## Appendix B — Granular class-bucket map for the pivotal split (`gebo.architecture.llms.abstraction.layer`)

This is the worked example of §5's split applied to the hardest module (153 files), to be used as the reference pattern for the other feature slices. Buckets map to §1.5 categories.

| Bucket → child | Contents (representative) |
|---|---|
| **`.interface-models`** (POJO contract) | Service interfaces: `IGConfigurable{Chat,Embedding,Image,Ranker,TextToSpeech,Transcript}Model`, `IGChatModelRuntimeConfigurationDao` + siblings, `IGRuntimeModelConfigurationDao`, `IG{Chat,Embedding,…}ModelConfigurationSupportService`(+`RepositoryPattern`), `IGLlmsServiceClientsProviderFactory`, `IGModelsListProvider`, `IGModelApiAccessReadUtils`, `IGDocumentTranslator`, `IGVectorStoreFactory`/`Builder`/`Provider`/`ConfigurationProvider`, `IGVectorSearchRestrictingFilterExpressionFactory`, `ILLMTypeFiltrer`. Models/enums: `GBase{Model,Chat,Embedding,Image,Ranker,TextToSpeech,Transcript}{Config,Choice}`, `G*ModelType`, `ChatModelFeatures`, `ChatModelsUses`, `IChatRequestContext`, `IChatSessionEntry`, `functions/model/**` (`SemanticSearchRequest/Response`, `RestrictedUserInfos`, …), `vectorstores/model/**` (`GBaseVectorStoreConfig`, `VectorStoreRuntimeConfiguration`, `EmbeddingTrafficInfo`), `controllers/model/ConfigurationEntry`, `LLMConfigException`. **§5.8:** any `GBase*Config` carrying `@Document` → POJO twin here, entity in `.impl`. |
| **`.sdk`** (Spring/Mongo-aware extension point) | `GAbstractConfigurable{Chat,Embedding,Image,TextToSpeech,Transcript}Model`, `GAbstractRankerModelConfigurationSupportService`, `BaseLLMSInvokingAndProvidingService`, `ClientChatCallUtil`, the `*RepositoryPattern` base wiring providers plug into. **Consumed by every `gebo.llms.*` provider module.** |
| **`.impl`** (owner: brain) | `services/impl/**` (`GLlmsServiceClientsProviderFactoryImpl`, `G{Chat,Embedding,…}ModelConfigurationSupportServiceRepositoryPatternImpl`, `GDocumentTranslatorImpl`, `LLMTypeFiltrer*Impl`, `GModelChoiceMetaInfoEnricherServiceImpl`), `vectorstores/impl/**`, `functions/**` (`ContentSearchingFunctions`, `UsersFunctions`, `ActualDateFunctions`), `@Document` config entities + their Mongo repos, entity⇄DTO mappers. |
| **`.server-proxy`** (owner: brain) | REST controllers from `controllers/**` (`ChatModelsController`, `EmbeddingModelsControllers`, `Base{Image,Ranker,TextToSpeech,Transcript}ModelsConfigurationCRUDController`) — expose model-list, embedding, and config CRUD over HTTP. |
| **`.client-proxy`** (secondary) | REST impls of the contract interfaces for callers that must invoke an LLM feature (e.g. `IGDocumentTranslator`) without hosting model clients. **Not** the primary cross-service path — brain/vectorizator/graphsearch each host `.impl`+`.sdk` and embed locally; they synchronize *configuration* via Hazelcast (§7.2), not via this proxy. |

**Key nuance the drill-down revealed:** for LLMs the cross-service concern is **shared configuration state**, not remote invocation. vectorizator and graphsearch must instantiate the *same* memory-resident model clients as brain (`IGRuntimeModelConfigurationDao`) and rebuild them on admin change → Hazelcast cache + invalidation topic, **not** a `.client-proxy` round-trip per embedding. The `.server-proxy`/`.client-proxy` pair exists only for the minority of callers that want a one-off LLM operation without hosting clients.

---

*This is a plan, not an implementation. §10 lists the decisions still owed; nothing in §11 should start before the §10 items feeding that phase are ruled on.*
