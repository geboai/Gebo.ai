# Jobs & Cron Analysis — `@Scheduled` inventory + messages routed to `core-module`

Ground-truth analysis of every timer-driven method in the codebase and every message the
internal broker actually routes to `core-module` (the hub hosted on `brain.gebo.ai`).
Written to inform whether a dedicated jobs/coordinator/monitoring microservice is worth
building — see "Why this matters" at the end.

Methodology: `grep -rn "@Scheduled"` for part 1; `grep -rn "CORE_MODULE\b"` (the
`GStandardModulesConstraints.CORE_MODULE = "core-module"` constant) for part 2, then read
every call site to classify it as an emitter (`setTargetModule(CORE_MODULE)`) or a
receiver (`getMessagingModuleId()`/`getModuleId()` returning `CORE_MODULE`). Cross-checked
against a live `docker compose` boot log, which prints every `IGMessageEmitter`/
`IGMessageReceiver` binding and its accepted/sent payload types at startup.

---

## Part 1 — `@Scheduled` inventory

`@EnableScheduling` is active on all 17 microservice apps and the monolith
(`gebo.ai.app`'s `Main.java`), so a method only actually *fires* wherever its owning
module is on that deployable's classpath — declaring `@EnableScheduling` is necessary but
not sufficient.

| # | Class.method | Schedule | Owning module | Fires on |
|---|---|---|---|---|
| 1 | `TimeoutCallbackHandler.checkTimeouts()` | initialDelay 10s, fixedRate **20s** | `gebo.application.messaging` | monolith, **every** microservice (brain, vectorizator, graphicator, chunker, gateway, heimdall, all 10 content-handlers) |
| 2 | `GPromptsParametersCacheServiceImpl.scheduledEviction()` | initialDelay 60s, fixedRate **5 min** | `gebo.architecture.chat.abstraction.layer` | monolith only |
| 3 | `DataSourcesCatalogsServiceImpl.onTickWarmCatalogues()` | initialDelay 1s, fixedRate **4 hr** | `gebo.architecture.chat.abstraction.layer` | monolith only |
| 4 | `GeboWorkflowsStatsServiceImpl.computeAndStoreWorkflowsStats()` | initialDelay 30s, fixedRate **15 min** | `gebo.architecture.compute.workflow` | monolith, brain, vectorizator, graphicator, all 10 content-handlers |
| 5 | `GWorkflowStatusDeamonServiceImpl.checkJobsStatus()` | initialDelay 10s, fixedRate **2 min** | `gebo.architecture.compute.workflow` | monolith, brain, vectorizator, graphicator, all 10 content-handlers |
| 6 | `SystemInitializationAdminService.onTick()` | initialDelay 20s, one-shot | `gebo.architecture.fastsetup.system` | monolith, heimdall |
| 7 | `LLMSUsageCrudServiceImpl.consolidateTick()` | initialDelay 5s, fixedRate **10 min** | `gebo.architecture.llms.abstraction.layer` | monolith, brain, vectorizator, graphicator |
| 8 | `RagThreasholdAutotuneServiceImpl.onTick()` | initialDelay 10s, fixedRate **4 hr** | `gebo.architecture.rag-threasholds-autotune` | monolith, brain |
| 9 | `GSchedulingTimeServiceImpl.scheduleTick()` | initialDelay 20s, fixedRate **1 min** | `gebo.architecture.scheduling` | monolith, brain, vectorizator, graphicator, all 10 content-handlers |
| 10 | `DefaultChatProfileInitializationService.onTick()` | initialDelay 20s, one-shot | `gebo.llms.setup` | monolith, brain |
| 11 | `SystemInitializationLLMService.onTick()` | initialDelay 20s, one-shot | `gebo.llms.setup` | monolith, brain |
| — | `AbstractCacheEntryCleanupService.checkExpirationTick()` — **inactive**, `@Scheduled` commented out | initialDelay 10s, fixedRate 2 min (dormant) | `gebo.architecture.documents.cache.impl` | on classpath of monolith, chunker — never fires either way |

**Duplication already in production today**: #1, #4, #5, #7, #9 each run independently on
every deployable that carries their module — #4/#5/#9 alone fire on **13 services
simultaneously** (brain + vectorizator + graphicator + 10 content-handlers), each computing
the same workflow-stats/job-status/scheduling tick against what is, for
brain/vectorizator/graphicator, literally the same shared `brain-gebo` Mongo database.

---

## Part 2 — Messages routed to `core-module`

`core-module` is the hub hosted on **brain.gebo.ai** (see the gateway's topology map:
`brain_gebo_ai: core-module: [user-messages-concentrator-component,
mongo-dispose-documents-component, session-shrinker, sessionLifeCycleService]`, plus
`end-of-workflow-compute-service` registered by `GComputeEndOfWorkflowReceiverFactory`).
It is the **only** module every other module can address by the shared
`GStandardModulesConstraints.CORE_MODULE` constant rather than a per-service topology
lookup — the single addressing exception in the whole topology.

### 2.1 Inbound — who sends what, to which component

| Sender (owning module) | Payload | Target component | Purpose |
|---|---|---|---|
| `GEmbedderImpl` (`vectorizator-module`) — 4 call sites | `GContentsProcessingStatusUpdatePayload`, `GUserMessagePayload` | `user-messages-concentrator-component` | embedding progress/error status per document |
| `FullTextIndexingBatchMessageReceiver` (`fulltext-module`) | `GContentsProcessingStatusUpdatePayload` | `user-messages-concentrator-component` | full-text indexing batch status |
| `GraphextractionProcessingStatusUpdater` (`knowledge-graph-module`) | `GContentsProcessingStatusUpdatePayload` | `user-messages-concentrator-component` | graph extraction status |
| `GraphextractionProcessorBatchReceiver` (`knowledge-graph-module`) | `GContentsProcessingStatusUpdatePayload` | `user-messages-concentrator-component` | graph extraction batch status |
| `DocumentChunkingBatchReceiver` (`tokenizer-module`, chunker) | `GContentsProcessingStatusUpdatePayload` | `user-messages-concentrator-component` | chunking batch status |
| `GIOCContentConsumer` (shared content-handler base — all 10 content services) — 2 call sites | `GContentsProcessingStatusUpdatePayload` | `user-messages-concentrator-component` | per-content-item ingestion status |
| `GIOCModuleContentsDispatcher` (shared content-handler base) | `GUserMessagePayload` | `user-messages-concentrator-component` | user-facing message raised during dispatch |
| `GCoreUserMessagesReceiverFactory` (core-module itself, self-relay) | `ComputeWorkflowEndPayload` | `end-of-workflow-compute-service` | re-emitted once the concentrator infers a workflow may be complete |
| `GAbstractSystemsArchitectureController` (content-handler admin base, all 10 services) | `GDeletedProjectEndpointPayload` | `mongo-dispose-documents-component` | endpoint deletion cascade |
| `GCoreMessagesEmitterImpl.sendDeletingPayloadToCoreMongoDocuments` (core-module itself) | `GDeletedProjectPayload`, `GDeletedKnowledgeBasePayload` | `mongo-dispose-documents-component` | project/KB deletion cascade |
| `GChatSessionLifeCycleServiceImpl` (`core-module`, self-targeted) | `SessionShrinkRequestPayload` | `session-shrinker` (`SessionShrinkMessagesReceiver`) | trims an over-budget chat session's token history |

Every processing-status path funnels through **one** component — `user-messages-concentrator-component` — regardless of which of the 5 content/RAG pipelines (embedding, fulltext, graph, chunking, generic content ingestion) raised it. It is the single aggregation point for "is this workflow done yet."

### 2.2 What each `core-module` component does with what it receives

- **`user-messages-concentrator-component`** (`GCoreUserMessagesReceiverFactory`) — accepts `GUserMessagePayload` and `GContentsProcessingStatusUpdatePayload`. Concentrates per-item status updates from every processing pipeline; when its bookkeeping says a workflow's inputs are all accounted for, it builds a `ComputeWorkflowEndPayload` and re-sends it to `end-of-workflow-compute-service` (the one self-relay row in the table above).
- **`end-of-workflow-compute-service`** (`GComputeEndOfWorkflowReceiverFactory`, `END_OF_WORKFLOW_COMPUTE_SERVICE` constant) — accepts only `ComputeWorkflowEndPayload`. Looks up the matching `IWorkflowStatusHandler` by workflow type/id, computes the real completion status, and — only if genuinely finished with a non-empty input batch — **broadcasts** (`broker.broadcast`, not a targeted send) a `GFinishedWorkflowPayload`. This is the terminal event of the whole pipeline; broadcast means any module with a registered receiver for that payload type gets it, not just one hardcoded subscriber.
- **`mongo-dispose-documents-component`** (`GDisposeMongoContentsMessageReceiverFactoryImpl`) — accepts `GDeletedProjectEndpointPayload`, `GInternalDeletionMessagePayload`, `GDeletedProjectPayload`, `GDeletedKnowledgeBasePayload`. Cascades a deletion (project, knowledge base, endpoint, or a raw list of document codes) into the shared Mongo store core-module owns.
- **`session-shrinker`** (`SessionShrinkMessagesReceiver`) — accepts `SessionShrinkRequestPayload` only, sent by `GChatSessionLifeCycleServiceImpl` to itself when a chat session's token budget is exceeded.
- **`sessionLifeCycleService`** — an *emitter*-only component (no inbound payload type of its own); it's the thing that sends the `session-shrinker` request above.

### 2.3 The one confirmed downstream subscriber of the broadcast

`GFinishedWorkflowPayload` (broadcast, §2.2) is consumed by
`RagThreasholdAutotuneFinishedWorkflowReceiver` — the receiver half of
`RagThreasholdAutotuneServiceImpl` (row 8 in Part 1). That class is therefore **both**
timer-driven (`@Scheduled onTick()`, every 4h) **and** event-driven (reacts to every
finished workflow) — the only method in this analysis that is both. `async-publishing-job`
components (`JobStatusEmitter`/`JobLaunchManagerImpl`, in
`gebo.architecture.contentsystems.abstraction.layer`) also emit
`GStartedWorkflowPayload`/`GFinishedWorkflowPayload` around job execution, confirmed live
in a brain boot log, but were not traced further for this pass.

---

## Why this matters (context for the jobs/coordinator microservice discussion)

Every row in §2.1 that targets `user-messages-concentrator-component` — i.e. every
processing-status update, from every one of the 5 pipelines, across every service that
raises one — already flows through a single logical hub (`core-module` on brain). The
plumbing for "one place aggregates cluster-wide state" **already exists** for content
processing status; it just isn't exposed as its own deployable and doesn't yet own the
Part-1 duplicated ticks (workflow stats, job status, scheduling tick) that currently run
redundantly on 13 services. Promoting `core-module` — or a new service standing in front
of the same `user-messages-concentrator-component` / `end-of-workflow-compute-service` /
`mongo-dispose-documents-component` contract — to its own microservice would mean: keep
every sender in §2.1 unchanged (they already emit to a module id, not a fixed host), move
only the *receiving* implementations and the Part-1 duplicated ticks onto the new service,
and delete those ticks from brain/vectorizator/graphicator/the 10 content-handlers. The
message contracts in this document are the exact interface such a service would need to
honor.
