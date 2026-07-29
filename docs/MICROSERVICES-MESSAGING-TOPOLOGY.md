# Microservices — Messaging Topology (messagingModuleId / messagingSystemId)

This catalogs the **message-broker identities** each microservice actually owns under
microservices deployment: every concrete class implementing `IGMessageEmitter` and/or
`IGMessageReceiver` (directly, or via an abstract base such as `AbstractJobLaunchManager`,
`AbstractJobStatusEmitter`, `AbstractCentralSchedulingService`), grouped by the
`messagingModuleId`/`messagingSystemId` pair it registers under in
`GeboMicroservicesTopology`/`IGMessageBroker`.

This is **not** the REST/`@RestController` surface — see `MICROSERVICES-CONTROLLERS.md`
for that. A messaging identity is what lets `RabbitMqExternalMessageEmitterProviderSource`/
`RabbitMqExternalMessageReceiverProviderSource` build a cross-service remote proxy for a
component; a `messagingModuleId` belongs to exactly one microservice
(`GeboMicroservicesTopology` enforces this), and a REST controller has nothing to do with
that routing.

**Methodology:** derived by reading source (every concrete `IGMessageEmitter`/
`IGMessageReceiver` implementation, its literal `messagingModuleId`/`messagingSystemId`,
its conditional annotation, and the Maven dependency chain from its own module to each
`*.gebo.ai` app module), then cross-checked against the declarative registry —
`gebo.microservices.architecture.parent/gebo.microservices.topology/.../GeboStandardMicroservices.java`
(`DEFAULTS`) — and against the live deployment config
(`dockers/gebo.microservices/config/application.yml`, which carries **no**
`gebo.microservices.topology.*` overrides, so `DEFAULTS` is the actual runtime topology
for this deployment, not just a fallback). This is a source-code audit, not a live
`/v3/api-docs`-style introspection — there is no REST endpoint that lists messaging
identities to poll.

**Explicitly out of scope — the "topology controller" family.** `InternalMessagingTopologyController`
and `GlobalInternalTopologyController` (plus their backing `GGlobalInternalTopologyServiceImpl`/
`InternalTopologyPollClient`) are plain `@RestController`s that let tyr poll every other
microservice's local `IGMessageBroker#getSystemsInfo()` snapshot over HTTP/`WebClient` and
aggregate it into a global view. Confirmed by reading the classes: none of them implement
`IGMessageEmitter`/`IGMessageReceiver`, and none has its own `messagingModuleId`/
`messagingSystemId`. This is a separate, REST-based topology-*discovery* mechanism,
unrelated to the RabbitMQ message broker these tables document.

## Summary

| Microservice | messagingModuleId(s) | Notes |
|---|---|---|
| `brain_gebo_ai` | `core-module`, `brain-module` | Also an undeclared `async-publishing-job-module` emitter registration (§ Discrepancies #5) |
| `heimdall_gebo_ai` | _none_ | AuthN/AuthZ edge, REST-only |
| `chunker_gebo_ai` | `tokenizer-module` | |
| `vectorizator_gebo_ai` | `vectorizator-module` | |
| `fulltextor_gebo_ai` | `fulltext-module` | Conditional on `ai.gebo.opensearch.enabled=true` |
| `graphicator_gebo_ai` | `knowledge-graph-module` | |
| `tyr_gebo_ai` | `async-publishing-job-module`, `jobs-master-module`, `scheduler-module`, `LLMS-USAGE-MONITOR` | Also 1 remaining undeclared identity (§ Discrepancies #1) |
| `git_gebo_ai` | `git-module` | |
| `filesystem_gebo_ai` | `shared-filesystem-module` | |
| `uploads_gebo_ai` | `uploads-module` | |
| `userspace_gebo_ai` | `userspace-module` | |
| `sharepoint_gebo_ai` | `sharepoint-module` | |
| `confluence_gebo_ai` | `confluence-module` | |
| `jira_gebo_ai` | `jira-module` | |
| `aws_s3_gebo_ai` | `aws-s3-module` | |
| `googledrive_gebo_ai` | `google-drive-module` | |
| `mcpclient_gebo_ai` | `mcp-client-module` | |
| `integration_gebo_ai` | `integration-module` | |
| `gateway_gebo_ai` | _none_ | Routing edge, deliberately absent from the topology (see `GeboStandardMicroservices.GATEWAY_MICROSERVICE_ID`) |

`LLMS-USAGE-MONITOR`/`USAGE-CONCENTRATOR` (receiver on tyr, emitters on brain/vectorizator/
graphicator) was found undeclared during this audit and has since been added to tyr's
entry in `DEFAULTS` (2026-07-28) — tyr ownership was confirmed intentional by design; only
the topology declaration itself was missing, which would have silently broken the
targeted cross-service send. See the former Discrepancies #2, now resolved, below.

## Content-handler microservices (11 services, identical shape)

Each is its own `gebo.systems.parent/gebo.<x>.content.handler` module, depending on
`gebo.architecture.contentsystems.abstraction.layer`. All 11 host the same five recurring
component kinds under their own module id:

| messagingSystemId | Base class | Purpose | Conditional |
|---|---|---|---|
| `async-publishing-job-component` | `AbstractJobLaunchManager` subclass (`<Handler>JobLaunchManager`) | Launches the handler's ingestion job when tyr's central scheduler dispatches a due publish | `@ConditionalOnMicroservices` |
| `job-status-notifier` | `AbstractJobStatusEmitter` subclass (`<Handler>JobStatusEmitter`) | Acks job completion back to tyr | `@ConditionalOnMicroservices` |
| `system-settings-controller-component` | `GAbstractSystemsArchitectureController.ControllerNestedEmitter` (nested in the handler's own `@RestController`) | Endpoint delete/settings broadcast | unconditional |
| `module-ioc-dispatcher-component` | `GIOCModuleContentsDispatcher` (`@Bean` in `G<X>ContentsDispatcherConfig`) | Dispatches ingested content into the processing pipeline (chunker/vectorizator/fulltextor/graphicator) | unconditional |
| `job-status-replicator` | `GJobStatusReplicatorService` | Replicates `GJobStatus` into tyr's Mongo (module id from `ai.gebo.jobs.replicator.module-id`, set per-handler in its own `application.yml`) | unconditional |

Plus, only where a `GAbstractResourcesDisposerFactory` subclass actually exists —
`resources-dispose-component` (endpoint-deletion cleanup receiver).

| Microservice | messagingModuleId | Static `Content.Handler.*` code | Has `resources-dispose-component`? |
|---|---|---|---|
| `git_gebo_ai` | `git-module` | `Content.Handler.DEFAULT.GIT.CONTENT.HANDLER` | yes |
| `filesystem_gebo_ai` | `shared-filesystem-module` | `Content.Handler.DEFAULT.FILESYSTEM.CONTENT.HANDLER` | yes |
| `uploads_gebo_ai` | `uploads-module` | `Content.Handler.DEFAULT.UPLOADS.CONTENT.HANDLER` | yes |
| `userspace_gebo_ai` | `userspace-module` | `Content.Handler.USERSPACE-CONTENTSYSTEM` | **no** (declared in `DEFAULTS`, no implementing class — Discrepancies #3) |
| `sharepoint_gebo_ai` | `sharepoint-module` | _dynamic per endpoint only_ | yes |
| `confluence_gebo_ai` | `confluence-module` | _dynamic per endpoint only_ | **no** (Discrepancies #3) |
| `jira_gebo_ai` | `jira-module` | _dynamic per endpoint only_ | **no** (Discrepancies #3) |
| `aws_s3_gebo_ai` | `aws-s3-module` | _dynamic per endpoint only_ | yes |
| `googledrive_gebo_ai` | `google-drive-module` | _dynamic per endpoint only_ | yes |
| `mcpclient_gebo_ai` | `mcp-client-module` | _dynamic per endpoint only_ | **no** (Discrepancies #3) |
| `integration_gebo_ai` | `integration-module` | _dynamic per endpoint only_ | **no** (Discrepancies #3) |

(Dynamic per-endpoint `Content.Handler.<code>` systems are registered at runtime per
configured endpoint, not as a static default — see `GeboStandardMicroservices`'s own
"System-id notes" javadoc.)

## `brain_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class | Conditional |
|---|---|---|---|
| `core-module` | `mongo-dispose-documents-component` | `GDisposeMongoContentsMessageReceiverFactoryImpl` (`gebo.core`) | unconditional |
| `core-module` | `session-shrinker` | `SessionShrinkMessagesReceiver` (`gebo.architecture.chat.abstraction.layer`) | unconditional |
| `core-module` | `sessionLifeCycleService` | `GChatSessionLifeCycleServiceImpl` (`gebo.architecture.chat.abstraction.layer`) | unconditional |
| `core-module` | `user-messages-concentrator-component` | **declared in `DEFAULTS`, no implementing class on brain** — Discrepancies #4 | — |
| `brain-module` | `async-publishing-job-component` | `BrainJobLaunchManager` (`brain.gebo.ai`) — brain gets `AbstractJobLaunchManager` transitively via `gebo.core` → `gebo.architecture.contentsystems.abstraction.layer`, despite not being a content handler | `@ConditionalOnMicroservices` |
| `brain-module` | `job-status-notifier` | `BrainJobStatusEmitter` (`brain.gebo.ai`) | `@ConditionalOnMicroservices` |
| _undeclared_ | `system-settings-controller-component` | `GCoreMessagesEmitterImpl` (`gebo.core`) — unconditional, live on brain, not listed under brain's `core-module` entry in `DEFAULTS` | unconditional |
| `LLMS-USAGE-MONITOR` | `USAGE-CONCENTRATOR` | `LLMSUsageCrudServiceImpl` (`gebo.architecture.llms.abstraction.layer`, via `gebo.microservices.llms.starter`) — emitter, targets tyr | unconditional — see Discrepancies #2 (resolved) |
| _(excluded, same-service loopback)_ | `rag-threashold-autotune-component` (module `rag-threashold-autotune-module`) | `RagThreasholdAutotuneFinishedWorkflowReceiver` — deliberately excluded per its own comment in `GeboStandardMicroservices`, reacts to a same-service broadcast only | unconditional |

## `tyr_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class | Conditional |
|---|---|---|---|
| `async-publishing-job-module` | `job-status-replication-receiver` | `GJobStatusReplicatorReceiverService` | unconditional |
| `jobs-master-module` | `user-messages-concentrator-component` | `GWorkflowsConcentratorMessagesReceiverFactory` — receives `GContentsProcessingStatusUpdatePayload` from every content-processing microservice | unconditional |
| `jobs-master-module` | `end-of-workflow-compute-service` | `GComputeEndOfWorkflowReceiverFactory` (receiver) / `GWorkflowsConcentratorMessagesEmitterImpl` (same-service loopback emitter) | unconditional |
| `scheduler-module` | `scheduler-component` | `ClusteredCentralSchedulingService` | `@ConditionalOnMicroservices` + `@Conditional(SchedulingAuthorityCondition.class)` (restricts to `spring.application.name == tyr_gebo_ai`, since `gebo.architecture.scheduling` is not exclusive to tyr's classpath — brain reaches it transitively too) |
| `LLMS-USAGE-MONITOR` | `USAGE-CONCENTRATOR` | `LLMUsageConcentratorReceiverFactory` (receiver) | unconditional — added to `DEFAULTS` 2026-07-28, previously undeclared (see Discrepancies #2, resolved) |
| _undeclared_ | `job-status-notifier` (module `workflow-status-module`) | `WorkflowStatusEmitter` — broadcasts job-finished status; absence from `DEFAULTS` may be intentional (`broadcast()` doesn't need topology-based routing) but isn't documented as such | unconditional — Discrepancies #1 |

## `vectorizator_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class |
|---|---|---|
| `vectorizator-module` | `vectorization-emitter-component` | `GContentVectorizationEmitterComponent` |
| `vectorizator-module` | `vectorization-component` | `GContentVectorizationMessagesReceiverFactoryComponent` |
| `vectorizator-module` | `vectorization-dispose-component` | `VectorizatorDisposerMessageReceiverImpl` |
| `LLMS-USAGE-MONITOR` | `USAGE-CONCENTRATOR` | `LLMSUsageCrudServiceImpl` (emitter, targets tyr) — see Discrepancies #2 (resolved) |

## `graphicator_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class |
|---|---|---|
| `knowledge-graph-module` | `knowledge-graph-component` | `GraphextractionProcessorMessagesReceiverFactoryComponent` |
| `LLMS-USAGE-MONITOR` | `USAGE-CONCENTRATOR` | `LLMSUsageCrudServiceImpl` (emitter, targets tyr) — see Discrepancies #2 (resolved) |

## `fulltextor_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class | Conditional |
|---|---|---|---|
| `fulltext-module` | `fulltext-indexing-component` | `GContentFullTextEmitterComponent` / `GContentFullTextMessagesReceiverFactoryComponent` | `@ConditionalOnProperty(prefix="ai.gebo.opensearch", name="enabled", havingValue="true")` |

## `chunker_gebo_ai`

| messagingModuleId | messagingSystemId | Implementing class |
|---|---|---|
| `tokenizer-module` | `tokenizer-component` | `DocumentChunkingMessagesReceiverFactoryComponent` |
| `tokenizer-module` | `dispose-chunking-session-for-jobs` | `ChunkingSessionDisposerReceiverFactory` |

## `heimdall_gebo_ai`

No messaging identities. Confirmed via `pom.xml`: pulls only security/secrets/fastsetup
modules, none of which touch `gebo.core`, `gebo.architecture.contentsystems.abstraction.layer`,
or any other messaging-bearing module — matches `GeboStandardMicroservices`'s empty
`.build()` entry.

## Infrastructure, not a per-service "owned" identity

`RabbitMqExternalMessageEmitter`/`RabbitMqExternalMessageReceiver`
(`gebo.architecture.messages.rabbitmq`) are the transport every microservice uses to reach
every other microservice's owned identity above. They're dynamic, config-driven proxies —
one instance per remote system the topology resolves — not a class-level constant
identity, so they aren't listed as a per-microservice row.

## Discrepancies found (registry vs. actual code, this audit)

Confirmed against `dockers/gebo.microservices/config/application.yml`, which carries no
`gebo.microservices.topology.*` override — so these are real gaps in what ships, not
patched elsewhere for this deployment.

1. **`workflow-status-module.job-status-notifier` (tyr) missing from `DEFAULTS` entirely.**
   `WorkflowStatusEmitter` is unconditional, live only on tyr, invoked from
   `GWorkflowStatusDeamonServiceImpl.broadcastEnded()`. It's a `broadcast()`, not a
   targeted `accept()`, so the gap may be harmless — but unlike the `rag-threashold-autotune`
   exclusion (which has an explicit comment explaining why it's left out), nothing says so
   here.
2. **RESOLVED (2026-07-28).** `LLMS-USAGE-MONITOR`/`USAGE-CONCENTRATOR` was missing from
   `DEFAULTS` entirely, on both ends. `LLMUsageConcentratorReceiverFactory` (receiver, tyr)
   and `LLMSUsageCrudServiceImpl` (emitter, live on brain/vectorizator/graphicator via
   `gebo.microservices.llms.starter`) both use this identity via a **targeted**
   `envelope.setTargetModule(...)` send — meaning the sender's local RabbitMQ bridge
   needed the topology to know this module belongs to tyr in order to build a remote proxy
   at all. Confirmed with the project owner: tyr ownership is intentional by design: only
   the topology declaration itself was missing. Fixed by adding
   `.module("LLMS-USAGE-MONITOR", "USAGE-CONCENTRATOR")` to tyr's entry in
   `GeboStandardMicroservices.DEFAULTS`.
   Note: the gateway's own separate static topology copy
   (`gateway.gebo.ai/src/main/resources/application.yml`, `include-defaults: false`) is
   still missing this entry (and `scheduler-module`) under `tyr_gebo_ai` — left as-is since
   gateway only needs *a* module present per service to derive its HTTP route, so this
   looks cosmetic, not functional, but is worth cleaning up for consistency.
3. **`resources-dispose-component` declared for 5 handlers with no implementing class.**
   `DEFAULTS` lists it under `userspace-module`, `mcp-client-module`, `integration-module`,
   `jira-module`, and `confluence-module`, but no `GAbstractResourcesDisposerFactory`
   subclass exists in any of those 5 handler modules (only git/filesystem/uploads/sharepoint/
   aws-s3/googledrive have one). `GCoreMessagesEmitterImpl.deleteEndpoint()`'s dynamic
   lookup degrades gracefully when none is registered, so likely harmless at runtime, but
   the declared topology doesn't match the implementation for these 5.
4. **`core-module.user-messages-concentrator-component` under brain doesn't match any
   class's self-declared identity.** No class in `gebo.core` declares this identity — it's
   actually `jobs-master-module.user-messages-concentrator-component`, owned by tyr's
   `GWorkflowsConcentratorMessagesReceiverFactory`. Brain only ever *sends* to it (via
   `GIOCModuleContentsDispatcher`), it doesn't own it. Looks like a stale/mistaken line in
   `DEFAULTS` worth double-checking with whoever wrote it.
5. **Brain's `GJobStatusReplicatorService` has no module-id override.** Unlike all 11
   content handlers (each sets `ai.gebo.jobs.replicator.module-id` in its own
   `application.yml`), brain's doesn't, so it silently defaults to
   `async-publishing-job-module`/`job-status-replicator` — an identity not declared for
   brain anywhere in `DEFAULTS`. Low severity (`replicate()` no-ops unless replication
   routing actually targets a `GJobStatus` from brain), but a real emitter registration
   under an undeclared identity.
