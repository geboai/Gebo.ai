# Routing model wrappers - design analysis

Status: analysis, nothing implemented yet (2026-11-26). Open decision: the routing strategies of the
first version (section 7).

## Goal

For every model kind of the LLM abstraction layer (`gebo.architecture.llms.abstraction.layer`), a
model wrapper that routes each call among configured models of other providers/models, and that is
itself a regular configured model: a class implementing the kind's `IGConfigurable*Model`, created
from a configuration by the kind's `IG*ModelConfigurationSupportService`, exactly as a provider's
model is.

## Verdict

The architecture takes it without changing the core. A router is a pseudo-provider: for each model
kind it needs a configuration class extending the kind's base configuration, a support service with
its own type code (`chat-router`, `embedding-router`, ...) and a wrapper implementing the kind's
`IGConfigurable*Model`. The runtime DAOs, the cluster synchronization, the default models and uses,
the delete protection of referenced objects and the admin UI's editor registry then treat it like a
new provider.

## 1. The path a router reuses

1. Saving a configuration goes through `ModelRuntimeConfigureHandler.insertAndConfigure` to
   `dao.addRuntimeByConfigClustered` (`ModelRuntimeConfigureHandler.java:61`).
2. The chat runtime DAO picks the support service whose `getType().getCode()` equals the
   configuration's `modelTypeCode` (`GChatModelRuntimeConfigurationDaoImpl.java:170`) and calls its
   `create(config)` (`:185`); the model joins the DAO's `staticConfigs`.
3. The clustered DAO (`GAbstractClusteredModelRuntimeConfigurationDao`) then runs `testCall()`,
   associates the provider deal and broadcasts the configuration to the other instances.
4. At startup `initializeRuntimeModels()` rebuilds every saved configuration, in database order.
5. In `gebo.ui` the editor is chosen by configuration class through `GEBO_UI_ENTITY_FORM_TOKEN`
   (`gebo-ai-admin.module.ts:237`).

The six other kinds (embedding, ranker, decision, text-to-speech, transcript, image) follow the same
path, each with its own runtime DAO and `GLlmModelClusterCategory` value.

## 2. Proposed shape

- **Modules:** new `gebo.llms.parent/gebo.llms.router` and `gebo.llms.router.controllers`, added to
  `gebo.llms.starter`. That reaches the monolith and `brain.gebo.ai`, the only microservice whose pom
  holds llms modules, so there is no new cross-service call.
- **Members:** each router configuration holds a `List<GRouterMember>`, each member with a
  `@GObjectReference` model reference, a priority and a weight. The persistence layer already scans
  the references held by list elements (`GPersistentObjectManagerImpl.java:165`), so a member cannot
  be deleted while a router uses it.
- **Strategy:** carried as `choosedModel`. The router's `getModelChoices()` returns one choice per
  strategy, so the existing model combo box works unchanged, and `safeGetModelCode()` shows the
  strategy in logs and usage records.
- **Member resolution:** by code, at call time, through `IGRuntimeBinder` (as
  `ModelRuntimeConfigureHandler` does). This avoids a Spring bean cycle (the DAO needs the support
  services repository, and the router's support service would need the DAO), and copes with the
  startup order and with members being reconfigured or deleted.
- **Checks in `create` / `testCall`:** every member exists, is of the same kind, and no router
  contains itself through another router.
- **Shared core, written once:** member resolution, cycle check, selection strategy SPI, failover
  executor.

| Kind | Wrapper delegates | Note |
|---|---|---|
| Chat | the whole `IGConfigurableChatModel` | see section 3 |
| Embedding | `getEmbeddingModel()` / `getVectorStore()` | see section 4 |
| Ranker, decision, image | `getRankerModel()` / `getDecisionModel()` / `getImageModel()` | no per-call state, simple |
| Text-to-speech | `call(String)` | the members' voices differ |
| Transcript | `call(InputStream)` | the first attempt consumes the stream: failover must buffer the bytes |

## 3. Chat: rules the wrapper must follow

- **Routing per call.** Inputs at hand: the prompt, its parameters, the `IChatRequestContext`
  (attached media included) and the `GeboToolCalling`.
- **Context length:** `getContextLength()` returns the smallest of the members.
  `AbstractChatService.java:729` sizes the prompt and knowledge base budget from it before any member
  is chosen.
- **Media:** `getInputModalities`, `getCarriedMediaTypes` and `acceptsMedia` return what every member
  accepts. A capability strategy could instead accept what any member accepts and send a request
  carrying a file to a member that takes it.
- **Tools and structured output:** supported only if every member supports them, or tool calls go
  only to the members that do.
- **Clones:** `cloneWithOptions` and `withoutTools` are called per agent or request
  (`GAbstractReactiveAgentService.java:177`, `AbstractChatService.java:633`,
  `PipelineStepModelsServiceImpl.java:71`). A router clone keeps the options and clones only the
  member it picks for a call, which costs what a plain model's clone costs today.
- **Failover, blocking calls** (`answer`, `textResponse`, `response`, `structuredResponse`,
  `doWithChatModel` / `doWithChatClient`, the last two used by graph extraction and
  `VectorizatorMediaReader`): on failure, retry with the next member.
- **Failover, streaming:** only before the first chunk. After it, text has been shown and tools may
  have run.
- **`prepareCall`:** used only inside `GAbstractConfigurableChatModel`, so the router hands it to the
  chosen member, without failover.
- **Identity:** the router's own Flux operators (defer, error resume) must keep the identity of who
  asks. The `StreamedCallIdentity` carrier lives in each member's chat client.
- **Usage and pricing:** each member's chat client already records usage under its own configuration
  and real provider (`GAbstractConfigurableChatModel.java:303`), so nothing is counted twice. The
  router itself reports no pricing and no provider.
  - Reporting no provider makes every router save log a "declares no provider" warning
    (`GAbstractClusteredModelRuntimeConfigurationDao.java:255`): a small skip for routers is needed.
  - The LLM usage dashboards show the members, not the router.
- **Prompt context providers and refused files notifier:** attached only to
  `GAbstractConfigurableChatModel` (`GChatModelRuntimeConfigurationDaoImpl.java:81`, `:94`). The
  members already have them, the router needs neither.
- **`testCall`:** the members were tested when they were set. The router's test is the validation of
  section 2, or one call through each member (to decide).

## 4. Embedding: one vector space per collection

Each embedding configuration gets its own collection, named by its code
(`MongoDBAtlasVectorStoreFactory.java:59`, the Redis prefix at `RedisVectorStoreFactory.java:88`, one
local file per code in `LocalVectorStoreFactory`), created in `initialize`
(`GAbstractConfigurableEmbeddingModel.java:141`). A router has a collection of its own, and two modes
are possible:

- **a. The same model on several endpoints or keys:** members with the same model code and
  dimensions, which is checked. One collection, failover or load balancing on every call.
- **b. Different models:** documents embedded by every member into each member's own collection,
  queries sent to whichever member is healthy. Failover across models at N times the embedding cost;
  it needs a fan-out `IGExtendedVectorStore`.

Further constraints:

- The router must not extend `GAbstractConfigurableEmbeddingModel` as it stands: it wraps the model in
  `UsageRecordingEmbeddingModel` (`:126`), and the members already record, so usage would be counted
  twice.
- `forQuery` / `forDocuments` (`IGRoleAwareEmbeddingModel`) are handed to the chosen member.
- Moving a knowledge base to a router means a new collection, so a Publish re-embeds it.

## 5. What needs no change

- Cluster propagation, `handleDefaultModel`, `findByUses`, the delete protection of referenced
  objects and the model types listings work as they are.
- **Access:** no runtime check of a chat model's `accessibleUsers` / `accessibleGroups` was found
  outside the model classes, so a router opens no new access path.
- **HA:** round-robin counters and member health are per instance; to be recorded in
  `docs/HA-GAPS.md`.

## 6. Work list

- **Backend:** the shared core, plus a configuration, a support service and a wrapper for each of the
  7 kinds; seven CRUD controllers extending `Abstract*ModelsConfigurationCRUDController`; the
  provider deal warning skip.
- **UI:** one router editor parametrized by kind, registered for each configuration class, with a
  member picker fed by `getRuntimeConfigured*Models`, plus its i18n resources.
- **Clients:** regenerate the Angular stubs and the brain Java client.
- **Tests:** in `gebo.architecture.integration.tests` with
  `AbstractGeboMonolithicIntegrationTestsWithFakeLLMS`: failover, the self-reference check, member
  deletion blocked, usage recorded on the members.

## 7. Open decision: routing strategies of the first version

1. Priority failover and weighted load balancing for all 7 kinds, plus capability routing for chat
   (media, tools, context length), built on the media checks `IGConfigurableChatModel` already has
   (recommended).
2. Priority failover and weighted load balancing only, for all kinds.
3. Option 1, plus classifier routing for chat: a small chat model picks the member for each request,
   like the pipeline routing step (`DefaultRoutingChatPipelineStepServiceImpl`).
