# Data-Flow Meta-Information (`getDataFlowMetaInfos()`) — Grounded Analysis

**Status:** Phase 0 (model) and Phase 1 (transport) implemented; Phases 2-5 outstanding — see §7.
**Branch:** `feature/data-tracing`
**Scope:** the new `IGMessagingSystem#getDataFlowMetaInfos()` hook and the
`ai.gebo.application.messaging.model` classes added alongside it, and what it would take to
make every *active element* of the architecture return its real data-source / data-destination /
transformation configuration for a GDPR / NIS2 administrator audit screen.

**Methodology:** every statement below is derived by reading the source in this working tree.
File paths and line numbers are given so each claim can be re-checked. Nothing here is inferred
from documentation alone; where the existing docs
(`docs/MICROSERVICES-MESSAGING-TOPOLOGY.md`, `docs/MICROSERVICES-CONTROLLERS.md`) were used, they
were re-verified against the classes they describe.

---

## 1. What exists today

### 1.1 The new hook

`gebo.architecture.parent/gebo.application.messaging/.../IGMessagingSystem.java:32-34`

```java
public default GDataFlowMetaInfos getDataFlowMetaInfos() {
    return null;
}
```

A `default` returning `null` — so it is *purely additive*: every existing implementor keeps
compiling untouched. That is the right shape, and it is the same technique already used in this
interface for `isLocalSystem()` (line 28) and `getCompleteId()` (line 64).

`IGMessagingSystem` is the root of the whole messaging component hierarchy:

- `IGMessageReceiver` / `IGMessageEmitter` extend it,
- `IGMessageReceiverFactory extends IGMessagingSystem` (`IGMessageReceiverFactory.java:23`),
- `IGMessageBroker extends IGMessageConsumer` which extends it,
- `IGContentManagementSystemHandler extends IGMessageReceiver`
  (`IGContentManagementSystemHandler.java:41-42`).

So the hook is already reachable from every content handler, every pipeline processor, every job
emitter and every disposer in the system. **This was the right place to hang it.**

### 1.2 The new model classes (5 files, currently untracked)

| Class | Fields |
|---|---|
| `GDataFlowMetaInfos` | `List<DataEndpoint> dataEndpoints`, `List<DataTransformationMetaInfo> engines`, `List<DataTransformationInfo> transformations` |
| `DataEndpoint` | `id`, `description`, `product`, `endpoint`, `boolean input`, `boolean output`, `List<MetaEndpointType> types` |
| `DataTransformationMetaInfo` | `id`, `description`, `List<MetaEndpointType> transformInto` |
| `DataTransformationInfo` | `id`, `description`, `DataTransformationMetaInfo transformationInfo`, `dataSourceId`, `dataDestinationId` |
| `MetaEndpointType` | `DOCUMENTS, DATABASE, VECTORIAL_DATABASE, GRAPH_DATABASE, CHUNK` |

The three-part decomposition (endpoints / engines / edges between them) is a sound model for a
flow graph and maps directly onto how the pipeline is actually wired. The gaps are in §3.

### 1.3 There is *no* consumer yet

`grep -rli "dataflow\|data-flow"` over the whole tree (excluding generated `gebo.api.clients`)
returns only `IGMessagingSystem.java` and `GDataFlowMetaInfos.java`. Nothing calls
`getDataFlowMetaInfos()`, nothing aggregates it, nothing exposes it over REST. That is the work.

### 1.4 Two collection mechanisms already exist and can be reused verbatim

**(A) The messaging-topology route — already end-to-end, cluster-wide.**

```
IGMessagingSystem beans
  └─ MessageBrokeringAssembler.onApplicationEvent()          impl/MessageBrokeringAssembler.java:118-183
       └─ broker.addSystemComponent(...)                     GBaseMessageBroker.java:91-112
            └─ broker.getSystemsInfo()                       GBaseMessageBroker.java:241-243
                 └─ ComponentsTreeUtil.componentsTree(...)   ComponentsTreeUtil.java:37-63
                      → GModuleMetaInfo → ComponentMetaInfo
  └─ InternalMessagingTopologyController.getLocalTopology()  gebo.microservices.internal-topology.controllers, lines 61-79
       (filters to localSystem == true, i.e. drops the RabbitMQ proxies)
  └─ GGlobalInternalTopologyServiceImpl.refresh()            gebo.microservices.global-internal-topology, lines 64-93
       (polls every declared microservice on a schedule, caches List<MicroserviceMetaInfo>)
  └─ GlobalInternalTopologyController.getGlobalTopology()    lines 47-50
```

This pipeline **already** carries a per-component DTO (`ComponentMetaInfo`) from every microservice
to tyr and out over an ADMIN-secured REST endpoint. If `ComponentMetaInfo` carried the
`GDataFlowMetaInfos`, the entire cluster-wide GDPR view falls out with **zero new transport, zero new
polling, zero new security configuration**.

**(B) The `IGRuntimeModuleComponent` route — the precedent for "every bean reports its config".**

`gebo.architecture.parent/gebo.architecture.patterns/.../IGRuntimeModuleComponent.java` declares
`List<GModuleUseInfo> getModuleUseInfo()`, and
`impl/GRuntimeModuleComponentsDaoImpl.java:43` collects it by autowiring
`@Autowired(required=false) List<IGRuntimeModuleComponent> staticConfigs` and looping
(`lines 67-69`). Consumed by
`gebo.architecture.fastsetup/.../GeboAdvancedSetupStatusService.java:82,123`.

Critically, `GAbstractContentManagementSystemHandler` **already implements this interface**
(`line 81`) and its `getModuleUseInfo()` (`lines 739-772`) already walks exactly the two DAOs a
data-flow report needs — `getConfigurations()` (the `GContentManagementSystem` list) and
`endpointsDao.getConfigurations()` (the `GProjectEndpoint` list). The data-flow implementation for
all 12 content handlers is a near-clone of a method that already exists and works.

### 1.5 What is *not* covered by `IGMessagingSystem`

These hold real data-flow configuration but are **not** messaging components:

| Element | Where | Why it matters |
|---|---|---|
| Web-search providers | `ISearchService` impls: Bing, Brave, Google, SearXNG, SerpApi, Tavily (`gebo.systems.parent/gebo.*search.handler`) | User queries leave the installation to a third-party engine |
| LLM runtime config DAOs | `IGChatModelRuntimeConfigurationDao`, `IGEmbeddingModelRuntimeConfigurationDao`, ranker / transcript / TTS / image | `GBaseModelConfig.baseUrl` (`GBaseModelConfig.java:50`) is *the* Art. 44 third-country transfer datum |
| Vector store provider | `IGVectorStoreConfigurationProvider` / `GVectorStoreFactoryProviderImpl` | Where embeddings are retained |
| Mongo | `MongoConfig` (`ai.gebo.mongodb`, `MongoConfig.java:30-33`) | Primary retention store |
| OpenSearch | `OpenSearchConfig` (`ai.gebo.opensearch`, `OpenSearchConfig.java:8-23`) | Full-text retention |
| Neo4j | `spring.neo4j.uri` (`dockers/gebo.microservices/config/application.yml:46-50`) | Graph retention |
| RabbitMQ | `GeboRabbitMqMessagingProperties` | Data in transit between services (NIS2) |
| MCP client / MCP server | `gebo.architecture.mcp-clients`, `gebo.architecture.mcp-server` | Data in and out over MCP |

Note `ISearchService` **already declares** `getMessagingModuleId()`, `getMessagingSystemId()`,
`getProductId()`, `getId()`, `getDescription()` (`ISearchService.java:47-57`) — the same identity
vocabulary as `IGMessagingSystem`, just not through that interface. It is one step away.

---

## 2. Where the model classes should live (blocking decision)

The model currently sits in `ai.gebo.application.messaging.model`. The Maven graph constrains who
can implement against it:

```
gebo.application.messaging  →  gebo.base.model, gebo.architecture.patterns, gebo.architecture.multithreading
```

| Module that should report a flow | depends on `gebo.application.messaging`? | depends on `gebo.architecture.patterns`? |
|---|---|---|
| `gebo.architecture.llms.abstraction.layer` | **yes** | yes |
| `gebo.ragsystem.vectorstores` | **yes** | (transitively) |
| `gebo.ragsystem.content.vectorizator` | **yes** | yes |
| `gebo.ragsystem.content.fulltext.processor` | **yes** (+ `gebo.architecture.opensearch`) | yes |
| `gebo.ragsystem.content.graphrag_processor` | **yes** (+ `gebo.architecture.graphrag.persistence`) | yes |
| `gebo.architecture.contentsystems.abstraction.layer` | yes | yes |
| **`gebo.architecture.search.abstraction.layer`** | **no** | **yes** |
| `gebo.architecture.opensearch` | no | no |
| `gebo.architecture.neo4j` | no | no |
| `gebo.architecture.persistence` | no | yes |

Two useful facts fall out of that table:

1. **The three RAG processors already have both halves.** `gebo.ragsystem.content.fulltext.processor`
   depends on `gebo.application.messaging` *and* `gebo.architecture.opensearch`;
   `gebo.ragsystem.content.graphrag_processor` on messaging *and* `gebo.architecture.graphrag.persistence`;
   `gebo.ragsystem.content.vectorizator` on messaging *and* `gebo.architecture.llms.abstraction.layer`.
   So the components that write into OpenSearch / Neo4j / the vector store can each report their own
   destination **without a single new Maven dependency**. The `gebo.architecture.opensearch` and
   `gebo.architecture.neo4j` modules do not need to implement anything.

2. **Web search is the one real blocker.** `gebo.architecture.search.abstraction.layer` depends on
   `gebo.architecture.patterns` but *not* on `gebo.application.messaging`.

> **Decision taken: keep the model in `ai.gebo.application.messaging.model`.** The package is
> unchanged. The consequence is deferred to Phase 4: when the six web-search providers implement the
> hook, `gebo.architecture.search.abstraction.layer` gains a `gebo.application.messaging` dependency.
> The recommendation below is retained as the rationale for that trade, not as an open question.

**Recommendation (not taken):** move the five model classes down to `ai.gebo.architecture.patterns.model` —
the package that already hosts `GModuleUseInfo`, the exact analogue of this model. Then:

- `IGMessagingSystem#getDataFlowMetaInfos()` keeps working (messaging → patterns already exists),
- `ISearchService` implementations and the LLM DAOs can implement it with no new dependency,
- the model sits next to the "component reports its own configuration" precedent rather than
  inside the transport layer, which is conceptually where it belongs.

The files are untracked, so this is a free move now and expensive later. `lombok` and
`spring-boot-starter-validation` are declared in the root `pom.xml:66-78` `<dependencies>` block and
therefore inherited by every module, so `@Data` / `@NotNull` compile in `gebo.architecture.patterns`
unchanged.

*Alternative if you prefer to keep the package:* leave the model in
`ai.gebo.application.messaging.model` and add `gebo.application.messaging` to
`gebo.architecture.search.abstraction.layer`'s pom. That module is small (three deps), so the cost
is low, but it makes a search abstraction depend on a message-broker module for no runtime reason.

---

## 3. Gaps in the model, each with its grounding

### 3.1 `DataTransformationInfo` has no accessors — it will serialize as `{}`

`DataTransformationInfo.java` is the only one of the five without `@Data`; its five fields are
`private` with no getters. Every other class in the set has it. Over the
`InternalMessagingTopologyController` → `InternalTopologyPollClient` (Jackson) hop this object
becomes an empty JSON object and the whole transformation graph is lost.
**Fix: add `@Data`.** (Confirmed defect, not a design question.)

### 3.2 `MetaEndpointType` is missing the endpoint kinds this architecture actually has

Present: `DOCUMENTS, DATABASE, VECTORIAL_DATABASE, GRAPH_DATABASE, CHUNK`. Missing, each with a
concrete referent in the tree:

| Missing kind | Grounded in |
|---|---|
| Full-text index | `OpenSearchConfig` + `OpenSearchIndexBootstrapConfig.ensureKbChunksIndex()` — an OpenSearch index is neither `DATABASE` nor `VECTORIAL_DATABASE` |
| LLM / inference endpoint | `GBaseModelConfig.baseUrl`, `apiSecretCode`, `choosedModel` (`GBaseModelConfig.java:29-55`) |
| Object storage | `gebo.aws-s3.content.handler` (`aws-s3-module`) |
| Message broker / queue | `RabbitMqExternalMessageEmitter`/`Receiver`, `GeboRabbitMqMessagingProperties` |
| Web search | `AbstractWebSearchServiceImpl` + its 6 subclasses |
| Local filesystem / cache | `IGContentManagementSystemHandler.isContentsOnLocalFilesystem()` (`line 161`) explicitly models "this handler caches files on an accessible filesystem" — that is a retention location |
| Chat session / conversation store | `GChatSessionLifeCycleServiceImpl`, `SessionShrinkMessagesReceiver` (`core-module`) — holds user prompts, i.e. personal data |

### 3.3 No credential-safety contract on `DataEndpoint.endpoint` — **highest-risk gap**

The obvious implementations leak secrets into an admin screen:

- `MongoConfig.getConnectionString()` returns
  `mongodb://mongoroot:mongopwd@mongo:27017/?authSource=admin`
  (`dockers/gebo.microservices/config/application.yml:92`) — username **and** password inline.
- `QdrantConfig` carries `apiKey` (`QdrantConfig.java`), populated at
  `application.yml:100`.
- `OpenSearchConfig` carries `username` / `password` (`OpenSearchConfig.java:21-22`),
  populated at `application.yml:107-108`.
- `GBaseModelConfig.apiSecretCode` (`line 39`).
- `spring.neo4j.authentication.password` (`application.yml:49-50`).

The model needs an explicit rule and a shape that makes the safe thing the easy thing:

- `endpoint` must be a **sanitized** locator: scheme + host + port + path/index/collection, never
  userinfo, never a key. A single shared helper (one place to get right) rather than each of ~30
  call sites doing its own redaction.
- add `secretReference` — the *code* of the secret, resolvable against the existing
  `GeboSecret` / `IGeboSecretsAccessService` model (`gebo.secrets.services`), which is precisely how
  `TavilySearchServiceImpl.resolveApiKey()` (`lines 101-115`) already indirects to credentials via
  `configs.get(0).getSecretCode()`. An auditor needs to know *which* credential guards an endpoint,
  never its value.

### 3.4 Endpoint ids are not globally unique, so cross-component flows cannot be expressed

`DataTransformationInfo.dataSourceId` / `dataDestinationId` point at `DataEndpoint.id`, but each
component returns its own `GDataFlowMetaInfos` with its own local id space. The flow the screen has
to draw is inherently cross-component:

```
git-module.Content.Handler.…GIT…        (DOCUMENTS in)
  → git-module.module-ioc-dispatcher-component
  → tokenizer-module.tokenizer-component            (DOCUMENTS → CHUNK, retained in Mongo)
  → vectorizator-module.vectorization-component     (CHUNK → embeddings → Qdrant)
  → fulltext-module.fulltext-indexing-component     (→ OpenSearch)
  → knowledge-graph-module.knowledge-graph-component(→ Neo4j)
```

That chain spans five modules on five different microservices. A qualified-id convention is needed,
and the codebase already has two precedents for exactly this:

- `IGMessagingSystem.getCompleteId()` → `moduleId + "." + systemId` (`lines 64-66`),
- `ISearchService.SYSTEM_TYPE_CODE_CONFIG_CODE_SEPARATOR = "<->"` (`ISearchService.java:21`),
  used as `module.system<->configCode` (`lines 27-30`).

Proposal: endpoint ids are qualified as `getCompleteId() + "<->" + localId` when referenced from a
`DataTransformationInfo`, with a helper on `GDataFlowMetaInfos` so components keep writing local ids.

### 3.5 `DataTransformationMetaInfo` declares output but not input

`transformInto` says what an engine produces; there is no `transformFrom`. Without it the screen
cannot tell that the tokenizer consumes `DOCUMENTS` and the vectorizator consumes `CHUNK`, which is
half of what makes the graph readable. `DataTransformationInfo` does give `dataSourceId`, but that
is the concrete edge, not the engine's declared capability.

### 3.6 No retention / erasure datum, although the erasure paths all exist as components

The architecture already has a complete deletion surface, one component per store:

| Component | Class | Store it purges |
|---|---|---|
| `core-module.mongo-dispose-documents-component` | `GDisposeMongoContentsMessageReceiverFactoryImpl` | Mongo |
| `vectorizator-module.vectorization-dispose-component` | `VectorizatorDisposerMessageReceiverImpl` | vector store |
| `tokenizer-module.dispose-chunking-session-for-jobs` | `ChunkingSessionDisposerReceiverFactory` | chunk cache |
| `<handler>-module.resources-dispose-component` | `GAbstractResourcesDisposerFactory` subclasses | handler-local cached resources |
| `core-module.session-shrinker` | `SessionShrinkMessagesReceiver` | chat sessions |

GDPR Art. 17 (erasure) is answerable directly from this: for each `DataEndpoint`, *is there a
disposer wired for it, and which component is it?* A `disposerComponentId` (or a
`boolean erasureSupported` + component ref) on `DataEndpoint` turns the existing wiring into a
compliance answer. Note `docs/MICROSERVICES-MESSAGING-TOPOLOGY.md` Discrepancy #3 records that
`resources-dispose-component` is *declared* for 5 handlers with no implementing class — a data-flow
report grounded in live beans (not in `GeboStandardMicroservices.DEFAULTS`) would surface that gap
automatically, which is itself a strong argument for building it.

### 3.7 No "leaves the installation" flag — the single most important GDPR field

Nothing in the model distinguishes `http://ollama:11434` from `https://api.openai.com`. Both are
`GBaseModelConfig.baseUrl`. For Art. 44/46 (third-country transfers) and for the NIS2 supply-chain
question the screen has to answer "does customer content leave this deployment, and to whom", the
model needs at minimum a tri-state on `DataEndpoint` — e.g. `LOCAL_DEPLOYMENT` / `SAME_NETWORK` /
`EXTERNAL_PROVIDER` — set by the reporting component, which is the only thing that knows.

Concrete cases already in the tree that would be classified `EXTERNAL_PROVIDER`:
`gebo.llms.openai`, `gebo.llms.anthropic3`, `gebo.llms.aws-bedrock`, `gebo.llms.google_vertex`,
`gebo.llms.mistral`, `gebo.llms.deepseek`, and all six web-search handlers.
`gebo.llms.ollama` and `gebo.llms.onxx-embeddings` are the local counterexamples.

### 3.8 No personal-data classification, and no back-reference to the owner

- **Classification.** At least a flag for "this endpoint carries personal data". Grounded example:
  `AbstractLLMSUsageCrudService.enqueueUsage()` (`lines 53-70`) puts
  `payload.setUsername(usage.getUsername())` into an `LLMUsageDetailPayload` that crosses a service
  boundary into tyr's Mongo. That is personal data in transit and at rest, and the current model has
  no way to say so.
- **Back-reference.** `DataEndpoint` has no `messagingModuleId` / `messagingSystemId` /
  `microserviceId`. If the transport is route (A) below, the aggregation supplies all three from the
  enclosing `MicroserviceMetaInfo → GModuleMetaInfo → ComponentMetaInfo`, so it does *not* need to be
  on `DataEndpoint`. If route (B) is also used, it does. **Decide the transport first, then this.**

### 3.9 `GDataFlowMetaInfos` carries no provenance

No component id, no collection timestamp, no schema version. `MicroserviceMetaInfo`/`GModuleMetaInfo`
supply the first via route (A); a timestamp still matters because
`GGlobalInternalTopologyServiceImpl` *caches* (`line 41`, `getGlobalTopology()` returns
`cachedTopology`) and deliberately keeps the previous snapshot when any service is down
(`lines 82-86`). An audit screen must never present a stale snapshot as current.

---

## 4. Transport: two routes, and one gap that blocks the monolith

> **Decisions taken:** route A (piggyback on `ComponentMetaInfo`), with the controller hosted in
> `gebo.core` — option 1 of the two listed below. Both are implemented; see §8.

### Route A — piggyback on `ComponentMetaInfo` (recommended for messaging components)

Add `GDataFlowMetaInfos dataFlowMetaInfos` to `ComponentMetaInfo`. Three propagation sites, all in
`ComponentsTreeUtil.java`, and all three are required or the field silently disappears:

- `componentsTree(...)` receiver branch — `lines 43-48`
- `componentsTree(...)` emitter branch — `lines 53-58`
- `joinModules(...)` — `lines 125-137`, which **constructs a brand-new `ComponentMetaInfo`** when a
  component is both emitter and receiver (`lines 130-133`). This is easy to miss and would drop the
  payload for exactly the dual-role components (e.g.
  `GWorkflowsConcentratorMessagesReceiverFactory`, which is a receiver factory *and*
  `implements IGMessageEmitter` — `GWorkflowsConcentratorMessagesReceiverFactory.java:57`).

`GBaseMessageBroker.getSystemsInfo()` (`line 242`) and everything downstream then carries it for
free, cluster-wide, over the already-ADMIN-secured endpoints.

**Blocker: the endpoint does not exist on the monolith.** `InternalMessagingTopologyController` lives
in `gebo.microservices.internal-topology.controllers`, and
`gebo.apps.parent/gebo.apps.monolithic.starter/pom.xml` does **not** list that module — its
dependency list is `gebo.architecture.security.apikey, gebo.acl.mongo, gebo.security.directory.mongo,
gebo.knowledgebase.hierarchy.local, gebo.ragsystem.starter, … gebo.architecture.contentsystems.abstraction.layer`
with no `gebo.microservices.*` entry. The class's own javadoc says it is "added only via
`gebo.microservices.starter`".

So the audit screen has no backend on the monolith today. Options, in order of preference:

1. Put the new data-flow controller in a module both deployments already have — e.g.
   `gebo.core` (in `gebo.apps.monolithic.starter`, and reached by every microservice through
   `gebo.ragsystem.starter` / `gebo.contentsystems.starter`) — and have it read the local
   `IGMessageBroker` exactly as `InternalMessagingTopologyController` does. On the monolith that one
   endpoint *is* the whole answer, because every component lives in one broker in one JVM.
2. Add `gebo.microservices.internal-topology.controllers` to the monolithic starter. Smaller diff,
   but pulls a module named "microservices" into the monolith.

### Route B — a `List<T>` collector for the non-messaging elements

Mirror `GRuntimeModuleComponentsDaoImpl` exactly (`line 43`: `@Autowired(required=false)
List<IGRuntimeModuleComponent> staticConfigs`; `lines 67-69`: loop and concatenate). Declare the
provider interface — say `IGDataFlowMetaInfoProvider` with the same
`GDataFlowMetaInfos getDataFlowMetaInfos()` signature — next to the model, let `IGMessagingSystem`
extend it so the two routes share one contract, and let `ISearchService` implementations and the LLM
DAOs implement it directly.

One merge service then unions route A (broker components, per microservice, remote) and route B
(local Spring beans) and hands the screen a single graph. Both must be de-duplicated by
`getCompleteId()` since a bean can arrive via both.

---

## 5. Per-element implementation map

Everything below is a *specific* class that exists, with the *specific* configuration object it
would read. Items marked **base-class win** mean one implementation covers many components.

### 5.1 Content handlers — inputs (12 modules, **base-class win**)

Implement once on `GAbstractContentManagementSystemHandler`; all 12 handlers inherit it.

- Available data — already gathered by the sibling `getModuleUseInfo()` (`lines 739-772`):
  - `getConfigurations()` → `List<? extends GContentManagementSystem>`, each with `baseUri`
    (`GContentManagementSystem.java:46`), `contentManagementSystemType` (`line 36`), `readonly`
    (`line 41`), `usedCapabilities` (`line 51`).
  - `endpointsDao.getConfigurations()` → `List<? extends GProjectEndpoint>`, each with `code`,
    `description`, `parentProjectCode`, `published`, `synchPeriodically`, `objectSpaceType`,
    `vectorizeOnlyExtensions` (`GProjectEndpoint.java:42-55`).
  - `getHandledSystemType().getCode()`, which is also what
    `getMessagingSystemId()` is built from (`line 676`).
  - `isContentsOnLocalFilesystem()` (`IGContentManagementSystemHandler.java:161`) → whether to emit a
    second, local-retention endpoint.
- Would report: one `DataEndpoint` per configured `GProjectEndpoint`, `input=true`,
  `types=[DOCUMENTS]`, `product` = the handled system type code, `endpoint` = sanitized `baseUri` +
  endpoint code.
- Concrete handlers: git, filesystem, uploads, userspace, sharepoint, confluence, jira, aws-s3,
  googledrive, mcp-client, integration, webdav-cms (`gebo.systems.parent/*`).

### 5.2 `GIOCModuleContentsDispatcher` — the fan-out edge (**base-class win**, 12 modules)

`module-ioc-dispatcher-component` under each handler's module (`GIOCModuleContentsDispatcher.java:168`).
It already asks the broker which downstream pipelines are live:

```java
final boolean indexingServiceOnline = broker.checkReceivingComponentPresent(
    GStandardModulesConstraints.FULLTEXT_MODULE,
    GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT);   // lines 250-251
```

That is exactly the "which transformations are actually configured *right now*" question, answered
from the live broker rather than from a static declaration. Its `getEmittedPayloadTypes()`
(`lines 148-153`) enumerates what it forwards.

### 5.3 Chunker — `DOCUMENTS → CHUNK`

`DocumentChunkingMessagesReceiverFactoryComponent` (`tokenizer-module.tokenizer-component`).
Retention: Mongo, via `ChunkingSessionRepository`, `DocumentCacheEntryRepository`,
`DocumentChunkOperationRepository` (`gebo.architecture.documents.cache.impl/.../repository/`).
Engine parameters: `ChunkingParams` / `TextChunkingSpecs` / `ChunkingPolicy` via
`IChunkingParametersProvider`.
Disposer: `ChunkingSessionDisposerReceiverFactory`.

### 5.4 Vectorizator — `CHUNK → embeddings → vector store` (**the GDPR-critical one**)

`GContentVectorizationMessagesReceiverFactoryComponent` (`vectorizator-module.vectorization-component`,
`lines 85-99`). Its module already depends on `gebo.architecture.llms.abstraction.layer`, so both
halves are reachable:

- **Destination:** `IGVectorStoreConfigurationProvider.get()` → `VectorStoreRuntimeConfiguration`
  → `getProduct()` (`MONGO | QDRANT | REDIS | TEST`, `VectorStoreProduct.java`) and
  `getConfiguration()` → e.g. `QdrantConfig.host/port/tls` (**never `apiKey`**).
- **Engine:** the embedding model — `GBaseEmbeddingModelConfig` extends `GBaseModelConfig`, giving
  `baseUrl`, `modelTypeCode`, `choosedModel` (`GBaseModelConfig.java:29-55`). *This is where the
  customer's document text is sent to a third party.*
- Disposer: `VectorizatorDisposerMessageReceiverImpl` (`vectorization-dispose-component`).
- Also `GContentVectorizationEmitterComponent` (`vectorization-emitter-component`).

### 5.5 Full-text — `→ OpenSearch`

`GContentFullTextMessagesReceiverFactoryComponent` (`fulltext-module.fulltext-indexing-component`,
`lines 86-89`), `@ConditionalOnProperty(prefix="ai.gebo.opensearch", name="enabled", havingValue="true")`
(`line 37`) — so its very presence is the "is full-text indexing configured" answer.
Its module depends on `gebo.architecture.opensearch`, so it can read `OpenSearchConfig`
(`protocol`, `host`, `port` — **never `username`/`password`**) and the index name established by
`OpenSearchIndexBootstrapConfig.ensureKbChunksIndex()`.

### 5.6 Graph extraction — `→ Neo4j`

`GraphextractionProcessorMessagesReceiverFactoryComponent`
(`knowledge-graph-module.knowledge-graph-component`, `lines 52-61`). Its module depends on
`gebo.architecture.graphrag.persistence` (which pulls `spring-data-neo4j`), so the destination is
`spring.neo4j.uri` (`application.yml:46-50`, host+port only). The extraction engine is an LLM —
same `GBaseChatModelConfig.baseUrl` third-country consideration as §5.4.

### 5.7 LLM usage tracking — personal data crossing a service boundary

`AbstractLLMSUsageCrudService` (`LLMS-USAGE-MONITOR` target /
`USAGE-CONCENTRATOR` system id, `lines 73-80`) emits `LLMUsageDetailPayload` carrying `username`,
`model`, `providerId`, token counts (`lines 53-70`) to tyr, which persists it.
Receiver: `LLMUsageConcentratorReceiverFactory` (`gebo.architecture.compute.workflow`).
Both ends should report this as a personal-data flow.

### 5.8 Chat sessions — user prompts at rest

`GChatSessionLifeCycleServiceImpl` (`core-module.sessionLifeCycleService`) and
`SessionShrinkMessagesReceiver` (`core-module.session-shrinker`), both in
`gebo.architecture.chat.abstraction.layer`. Retention store: Mongo. The shrinker *is* the retention
policy, so its configuration is a directly reportable retention rule.

### 5.9 Job status / workflow replication

`AbstractJobLaunchManager`, `AbstractJobStatusEmitter`, `AbstractJobStatusReplicatorService` /
`GJobStatusReplicatorService` (**base-class win** across all 12 handlers plus brain), and on tyr
`GJobStatusReplicatorReceiverService` (`async-publishing-job-module.job-status-replication-receiver`),
`GWorkflowsConcentratorMessagesReceiverFactory` (`jobs-master-module.user-messages-concentrator-component`),
`GComputeEndOfWorkflowReceiverFactory`, `WorkflowStatusEmitter`. These move job/workflow metadata
into tyr's Mongo — a cross-service replication flow worth showing.

### 5.10 Web search — queries leaving the installation (needs §2's model move)

Six `AbstractWebSearchServiceImpl` subclasses: `BingSearchServiceImpl`, `BraveSearchServiceImpl`,
`GoogleSearchServiceImpl`, `SearxngSearchServiceImpl`, `SerpapiSearchServiceImpl`,
`TavilySearchServiceImpl`. Each already exposes `getMessagingModuleId()`, `getId()`,
`getDescription()`, `getProductId()`, `getSearchableSystems()`, and — decisively —
`isEnabled()` (e.g. `TavilySearchServiceImpl.isEnabled()` returns `repository.count() > 0`,
`lines 71-73`), i.e. *is this provider actually configured*. `output=true`, `input=true`
(results come back and are ingested), `EXTERNAL_PROVIDER`, secret via `getSecretCode()`
(`lines 101-115`) — never the token.
SearXNG is the local-deployment counterexample and should classify differently.

### 5.11 Cross-service transit (NIS2)

`RabbitMqExternalMessageEmitter` / `RabbitMqExternalMessageReceiver` implement `IGExternalInterface`
and therefore report `localSystem == false`, which means
`InternalMessagingTopologyController.getLocalTopology()` **filters them out** (`lines 72-73`).
That is correct for a per-node topology but means the RabbitMQ hop is invisible to route (A).
If the broker itself must appear on the audit screen (it is where in-flight message payloads —
including document fragments — transit), it needs to be reported separately, e.g. by
`GeboRabbitMqMessagingProperties` through route (B).

### 5.12 Explicitly *not* worth implementing

- `MessageReceiverRunner`, `ThreadMessageReceiverMultiplexer`, `GNestedMessageReceiver`,
  `GBatchAggregatorMessageReceiver` — orchestration plumbing with no configuration of their own;
  they would return `null` and be skipped.
- `GBaseMessageBroker` itself — it is the aggregator, not a flow participant.
- The generated `gebo.api.clients/**` — generated code, never hand-edited.

---

## 6. Risks and decisions to settle before writing code

1. **Secret leakage (highest).** §3.3. A single shared sanitizer, applied at the model boundary, and
   a rule that `DataEndpoint.endpoint` is a locator not a connection string. Worth a test.
2. **Who may see this.** `InternalMessagingTopologyController` and `GlobalInternalTopologyController`
   are `@PreAuthorize("hasAnyRole('ADMIN','APPLICATION')")`. The data-flow endpoint must be at least
   as strict — this is a complete map of every data store and credential-guarded endpoint in the
   installation, i.e. a reconnaissance document. ADMIN only, and probably worth a
   `SecurityAuditTaxonomy` event when read (the taxonomy already has the vocabulary —
   `EventType.INTEGRATION_CONFIGURATION`, `Category`, `Action`).
3. **Cost per call.** `GAbstractContentManagementSystemHandler.getModuleUseInfo()` calls
   `getSystem(...)` per endpoint inside a loop (`lines 753-767`) — a DAO hit per endpoint. A data-flow
   implementation on the same base class inherits that cost, and
   `GGlobalInternalTopologyServiceImpl.scheduledRefresh()` runs every 120 s by default
   (`poll-interval-ms:120000`, `line 58`). Either keep the data-flow collection off the topology poll
   or cache it.
4. **Payload size.** Route (A) makes every topology poll carry the full flow model for every
   component of every microservice. If it grows, split it onto its own endpoint rather than
   inflating the existing one.
5. **Freshness.** §3.9 — the global topology cache is deliberately sticky when a service is down.
   The screen must render "as of <timestamp>, N services unreachable", not a silent stale graph.
6. **Monolith vs microservices.** §4 — pick a home module for the controller before writing it.
7. **Reported vs. declared.** This report is grounded in *live beans*, whereas
   `GeboStandardMicroservices.DEFAULTS` is a *declaration*. They already disagree — see
   `docs/MICROSERVICES-MESSAGING-TOPOLOGY.md` Discrepancies #1, #3, #4, #5. That is a feature: the
   audit view will show what is actually running. Do not source it from `DEFAULTS`.
8. **This complements, does not replace, the audit log.** `IGSecurityAuditLoggerService` +
   `SecurityAuditTaxonomy` + the Wazuh integration (`docs/wazuh-integration.md`) record *events*
   (`INTEGRATION_DATA_ACCESS`, `LLM_INVOCATION`, …). `GDataFlowMetaInfos` records *configuration*.
   NIS2/GDPR audits need both: "what is wired" and "what actually happened".

---

## 7. Phasing

**Phase 0 — model, no behaviour. DONE (§8).** Fix `@Data` on `DataTransformationInfo`; extend
`MetaEndpointType`; add `transformFrom`, the endpoint classification tri-state, `secretReference`,
the disposer reference, the personal-data flag, and the qualified-id helper. Add the shared endpoint
sanitizer + its test.

**Phase 1 — transport. DONE (§8).** Add `dataFlowMetaInfos` to `ComponentMetaInfo`; propagate at all
three `ComponentsTreeUtil` sites; add the ADMIN-only controller in a module both deployments have
(§4 blocker). At this point the plumbing is end-to-end with every component still returning `null`.

**Phase 2 — the base-class wins.** `GAbstractContentManagementSystemHandler` (12 handlers),
`GIOCModuleContentsDispatcher` (12 dispatchers), `AbstractJobStatusEmitter` /
`AbstractJobLaunchManager` / `AbstractJobStatusReplicatorService`. Largest coverage per line of code,
and each mirrors a `getModuleUseInfo()` that already works.

**Phase 3 — the pipeline destinations.** Vectorizator (+ vector store + embedding model), full-text
(+ OpenSearch), graph (+ Neo4j), chunker (+ Mongo), and the four disposers. This is where the screen
gets its "which databases retain data" answer.

**Phase 4 — route B and the non-messaging elements.** The `IGDataFlowMetaInfoProvider` collector,
the six web-search providers, the LLM runtime DAOs, MCP client/server, RabbitMQ.

**Phase 5 — the screen.** Regenerate the Angular stubs (`regen-angular-stubs`), then the admin view:
endpoints grouped by classification (local / same-network / external), the flow graph, and per-store
retention + erasure capability.

---

## 8. What is implemented (Phases 0-1)

### Model — `gebo.application.messaging/.../model/`

| File | Change |
|---|---|
| `DataEndpointLocator.java` | **new.** The credential sanitizer of §3.3. String surgery rather than `java.net.URI` parsing, because a MongoDB replica-set string is not a conformant URI and a compliance report must never fail to render on an unparseable config value. Drops fragment, query and userinfo (split on the **last** `@`, since a password may contain one); keeps scheme, host, port and path. Falls back to `[redacted]`, never to the raw input. |
| `DataEndpoint.java` | `locality`, `secretReference`, `personalData`, `disposer` added. `setEndpoint(String)` is **hand-written** so Lombok does not generate it and the sanitization cannot be bypassed; a `setEndpoint(scheme, host, port, path)` overload covers the stores that keep host and port apart. |
| `DataEndpointLocality.java` | **new.** `LOCAL_DEPLOYMENT` / `SAME_NETWORK` / `EXTERNAL_PROVIDER` — §3.7. |
| `MetaEndpointType.java` | `FULLTEXT_INDEX`, `LLM_ENDPOINT`, `OBJECT_STORAGE`, `MESSAGE_BROKER`, `WEB_SEARCH`, `LOCAL_FILESYSTEM`, `CHAT_SESSION` added; each javadoc names the class it is reported from. Existing five constants unchanged. |
| `DataTransformationMetaInfo.java` | `transformFrom` added — §3.5. |
| `DataTransformationInfo.java` | `@Data` added — §3.1, the serialization defect. |
| `GDataFlowMetaInfos.java` | `component` (`GeboComponentInfo`, reused rather than a new type), `qualifiedId(...)` using the existing `<->` convention — §3.4 — and `merge(...)`, see below. |
| `GDataFlowReport.java` | **new.** Per-node response: `nodeId`, `collectedAt`, `modules`. The timestamp sits here, not on the per-component model, because staleness is a property of the collection — §3.9. |

### Transport

| File | Change |
|---|---|
| `ComponentMetaInfo.java` | `dataFlowMetaInfos` field + accessors, in the class's existing hand-written style. |
| `ComponentsTreeUtil.java` | Propagation at all three sites: receiver branch, emitter branch, and `joinModules(...)`. |
| `gebo.core/.../controllers/DataFlowMetaInfoController.java` | **new.** `GET api/admin/DataFlowMetaInfoController/getLocalDataFlow`, `@PreAuthorize("hasRole('ADMIN')")`. |

Two points worth flagging, both about silent data loss:

- **`joinModules` merges rather than picks a side.** It builds a brand-new `ComponentMetaInfo` for
  components that are both emitter and receiver, and one messaging identity can be served by *two
  distinct beans* — `jobs-master-module.end-of-workflow-compute-service` is
  `GComputeEndOfWorkflowReceiverFactory` on the receiver side and
  `GWorkflowsConcentratorMessagesEmitterImpl` on the emitter side. Copying from either side alone
  would drop the other's report without trace. `GDataFlowMetaInfos.merge(...)` concatenates and
  de-duplicates by id, which also handles the ordinary case of one instance registered in both maps.
- **The controller is ADMIN-only**, deliberately stricter than the `ADMIN,APPLICATION` of the two
  topology controllers (§6.2). It also filters out `localSystem == false` components: those are the
  RabbitMQ bridge proxies (`GAbstractExternalMessageEmitter:81-84`,
  `GAbstractExternalMessageReceiver:116-118` hard-return `false`), which stand for another node's
  component — reporting them would double-count every remote endpoint once per node that can reach it.

### Verification

- `DataEndpointLocatorTest` — 15 tests, all passing. Inputs are the real shapes from
  `dockers/gebo.microservices/config/application.yml`, not invented ones: the Mongo case asserts that
  `mongoroot` and `mongopwd` do not survive. Covers the last-`@` split, query and fragment removal,
  multi-host replica-set strings, idempotency across the topology hop, trailing-slash normalization,
  the `[redacted]` fallback, and that `DataEndpoint.setEndpoint` sanitizes.
  (`gebo.application.messaging` had no `src/test` before; junit + `spring-boot-starter-test` added to
  its pom at test scope, matching `gebo.architecture.crypting`.)
- Full reactor build with `-DskipTests`.

Every component still returns `null` from `getDataFlowMetaInfos()`, so the endpoint currently
returns an empty `modules` list on both deployments. That is the intended end of Phase 1: the
plumbing is in place and provably compiles cluster-wide before any component starts reporting.
