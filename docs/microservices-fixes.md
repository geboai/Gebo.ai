# Gebo.ai — Microservices Cluster Integration: Findings & Fixes

> **Status:** Completed — full RAG ingestion + multi-turn chat pipeline confirmed working end-to-end against a 21-container `docker-compose` microservices cluster.
> **Branch:** [`fix/microservices-cluster-integration-bugs`](https://github.com/geboai/Gebo.ai/tree/fix/microservices-cluster-integration-bugs)
> **PR:** [#131](https://github.com/geboai/Gebo.ai/pull/131) (left open per request)
> **Scope:** `microservices-integration-tests` (`SetupUseMicroservicesClusterAgenticChatIT`, `SetupUseMicroservicesClusterPipelineIT`) run against `dockers/gebo.microservices/docker-compose.yml`.

## 0. How to read this document

This was an iterative debugging effort: each fix unblocked the pipeline far enough to hit the *next* previously-hidden bug. The findings below are grouped by commit, in the order they were actually found and fixed — later sections depend on earlier ones being fixed first (e.g. auth couldn't be diagnosed until DNS resolution worked; vectorization couldn't be diagnosed until chunking worked). §9 gives the final, cluster-wide verification and a single table of every file touched.

Twenty-six distinct root causes were found and fixed across six commits. None were fixed by loosening a check, adding a workaround flag, or retrying blindly — every fix traces to a concrete root cause confirmed via logs, `mongosh` queries against the running cluster's databases, `jstack` thread dumps, or a `jcmd`/`curl` probe from inside a container.

---

## 1. Commit [`d0ef4cd5f`](https://github.com/geboai/Gebo.ai/commit/d0ef4cd5f8459ad84ca69aad04138cae68414ed5) — repair microservices cluster wiring and integration test suite

The starting state: the integration test suite could not even complete cluster setup. Each fix below was necessary to get past authentication and basic cross-service reachability.

### 1.1 JWT authentication unusable on every service except heimdall

**Symptom:** requests authenticated fine against heimdall (the identity-owning service) but failed with 401/403 against every other microservice.

**Root cause:** `CustomUserDetailsService` looked users up in a local, per-service Mongo `UserRepository` — empty on every service except heimdall, which owns the user store. Every other service had no way to resolve the principal behind an incoming JWT.

**Fix:** added `DirectoryBackedUserDetailsService`, which resolves users through `IGSecurityDirectory` (the existing cross-service security-directory abstraction, backed by heimdall) instead of a local repository, and wired it into `LocalJwtAuthenticationManager`.

**Files:**
- `gebo.architecture.parent/gebo.architecture.security/src/main/java/ai/gebo/security/services/impl/DirectoryBackedUserDetailsService.java` (new)
- `gebo.architecture.parent/gebo.architecture.security/src/main/java/ai/gebo/security/services/impl/CustomUserDetailsService.java`
- `gebo.architecture.parent/gebo.architecture.security/src/main/java/ai/gebo/security/config/GeboAISecurityConfig.java`
- `gebo.architecture.parent/gebo.architecture.security/src/main/java/ai/gebo/security/model/UserPrincipal.java`

### 1.2 Cross-service REST clients couldn't resolve Eureka service-ids

**Symptom:** `UnknownHostException` calling a peer service by its Eureka discovery id (e.g. `heimdall-gebo-ai`).

**Root cause:** the security/secrets/acl microservice REST clients used a bare `WebClient.builder()`. A bare `WebClient` performs literal DNS resolution; a Eureka discovery-service-id is not a real DNS name — only a `@LoadBalanced`-qualified client, backed by Spring Cloud LoadBalancer, can resolve it to a live instance address. This exact pattern (bare `WebClient` → `@LoadBalanced` builder + BOM/dependency) recurred **five more times** later in the effort (§3.1, §3.2, §4.3, §4.4) — it is the single most repeated bug class in this whole investigation.

**Fix:** added `@LoadBalanced WebClient.Builder` beans and the `spring-cloud-starter-loadbalancer` dependency (plus a `spring-cloud-dependencies` BOM import) to each client module.

**Files:**
- `gebo.microservices.architecture.parent/gebo.microservices.security.client/pom.xml`, `.../config/GeboSecurityMicroserviceClientAutoConfiguration.java`
- `gebo.microservices.architecture.parent/gebo.microservices.secrets.client/pom.xml`, `.../config/GeboSecretsMicroserviceClientAutoConfiguration.java`
- `gebo.microservices.architecture.parent/gebo.microservices.acl.client/pom.xml`, `.../config/GeboAclMicroserviceClientAutoConfiguration.java`

### 1.3 Cluster controllers unreachable regardless of authentication

**Symptom:** every call to the security/secrets/acl cluster-internal endpoints 404'd, independent of whether the caller was authenticated or authorized.

**Root cause:** these controllers were hand-registered `@Bean`s (not `@RestController`s), created via a separate auto-configuration guarded by a matching interceptor bean. `RequestMappingHandlerMapping` builds its handler map exactly once, early in context refresh; the hand-registered controller bean, reachable only through a deep `@ConditionalOnBean` chain, was consistently *not yet present* when that one-time scan ran — confirmed live via `/actuator/beans` (the bean existed) vs. `/actuator/mappings` (zero routes registered for it). No amount of authentication or authorization could ever reach code that was never mapped.

**Fix:** replaced the hand-registered `@Bean` + separate interceptor + separate auto-configuration/properties classes with plain `@RestController` classes (ordinary, `@ComponentScan`-discovered beans — not subject to the timing problem) that call `ClusterParticipantsGuard.check(...)` inline, as the first line of every handler method. This sidesteps the ordering problem entirely instead of working around it, and removes ~370 lines of now-unneeded interceptor/auto-configuration/properties plumbing.

**Files (each of the three cluster surfaces got the same treatment):**
- `gebo.microservices.architecture.parent/gebo.microservices.cluster.commons/.../cluster/ClusterParticipantsGuard.java` (new — replaces `ClusterParticipantsOnlyInterceptor.java`, removed)
- `gebo.microservices.architecture.parent/gebo.microservices.security.client/.../controller/SecurityDirectoryClusterController.java`
- `gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../controller/SecretsClusterController.java`
- `gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../controller/AclAliasesClusterController.java`
- Removed: `GeboAclClusterControllerAutoConfiguration.java`, `GeboAclClusterControllerProperties.java`, `GeboSecretsClusterControllerAutoConfiguration.java`, `GeboSecretsClusterControllerProperties.java`, `GeboSecurityClusterControllerAutoConfiguration.java`, `GeboSecurityClusterControllerProperties.java`, and the three corresponding `spring.factories`/`AutoConfiguration.imports` entries

### 1.4 `NoSuchMethodError` during embedding-model / vector-store initialization

**Root cause:** Maven's nearest-wins dependency mediation resolved `com.squareup.okio:okio-jvm` to `3.6.0` (pulled transitively by `okhttp:4.12.0`) on the runtime classpath, but the OpenTelemetry `okhttp` exporter requires `3.16.4`.

**Fix:** pinned `com.squareup.okio:okio-jvm:3.16.4` in the root POM's dependency management.

**Files:** `pom.xml` (root)

### 1.5 Stack overflow from re-entrant `ContextRefreshedEvent`

**Root cause:** once §1.2's `@LoadBalanced` clients were introduced, `ApplicationListener<ContextRefreshedEvent>` implementations that react by calling one of those clients began recursing: Spring Cloud LoadBalancer creates a short-lived *child* `ApplicationContext` per target service-id on first use; that child context's own refresh republishes `ContextRefreshedEvent` up to the *parent's* listeners — which call the client again, creating another child context, forever, until the stack overflowed.

**Fix:** every affected listener now checks that the event's `ApplicationContext` is its own (top-level) context before reacting, ignoring events bubbling up from a LoadBalancer child context. Two independent listeners needed this in this commit (a third was needed later, §2.5).

**Files:**
- `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../cluster/GAbstractClusteredModelRuntimeConfigurationDao.java` (the shared base later touched again in §9.2)
- `gebo.systems.parent/.../impl/GFilesystemChangesHandlingService.java` (its existing `started` guard was widened to cover the whole method, not just `watcher.start()`)
- Five `*ModelRuntimeConfigurationDaoImpl` classes (chat, embedding, image, ranker, text-to-speech, transcript) updated to use the corrected base-class hook

### 1.6 Filesystem test fixture not visible to the containerized service

**Fix:** enabled `ai.gebo.filesystem.allowFilesystemSharesUI` for the filesystem service and bind-mounted `/tmp` into its container, so the cluster's file-share admin flow can see the test's host-created shared folder.

**Files:** `gebo.apps.parent/gebo.microservices.apps.parent/filesystem.gebo.ai/pom.xml`, `dockers/gebo.microservices/docker-compose.yml`

### 1.7 Test harness bugs

- The integration test passed a **generated client stub's class name** to a server-side `Class.forName` lookup instead of the server's own domain class name — fixed in `AbstractMicroservicesClusterSetupUseChatTest.java`.
- Assorted harness fixes: a stale image tag in `dockers/gebo.microservices/.env`, a broken `docker.compose.cmd` Maven property, a test profile that never actually deactivated, missing per-service context-paths, and a Jackson 2/3 mismatch in the test's `RestTemplate` (fixed via `microservices-integration-tests/pom.xml` dependency adjustments).

---

## 2. Commit [`023313d54`](https://github.com/geboai/Gebo.ai/commit/023313d54da5706164ab8051c8bddd19577af0e6) — GProject/GKnowledgeBase remote lookup, job-status replication, and RabbitMQ bridge wiring

With auth and basic reachability fixed, the pipeline reached ingestion — and immediately hit a class of "local-only assumption" bugs: code written assuming direct database access to entities that, in a microservices deployment, live on a *different* service's database entirely.

### 2.1 Cross-service GProject/GKnowledgeBase lookups crashed ingestion

**Symptom:** `"GProject does not exist"` (or `GKnowledgeBase`) for any content-handler other than brain (which owns those Mongo collections).

**Root cause:** the content-handler abstraction layer, `GDocumentReferenceEnricherMapFactoryImpl`, and `GContentConsumerFactoryImpl` all read `GProject`/`GKnowledgeBase` via direct repository access — correct on the monolith and on brain (which is the local owner), but every other content-handler (filesystem, git, jira, confluence, …) has no such local collection.

**Fix:** introduced `IGKnowledgeBaseHierarchyLookupService` with two implementations selected by classpath/topology:
- `LocalKnowledgeBaseHierarchyLookupService` (new module `gebo.knowledgebase.hierarchy.local`) — direct repository access, used by brain/monolith, with its own TTL cache (`GeboKnowledgeBaseLocalCacheProperties`).
- `RestKnowledgeBaseHierarchyLookupService` (new module `gebo.microservices.knowledgebase.client`) — REST call to brain, used by every other microservice, resolved through the topology (same `@LoadBalanced` pattern as §1.2).

Every direct-repository call site in the abstraction layer was replaced with a call through this interface.

**Files:**
- `gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../IGKnowledgeBaseHierarchyLookupService.java` (new interface)
- `gebo.core.parent/gebo.knowledgebase.hierarchy.local/**` (new module: `LocalKnowledgeBaseHierarchyLookupService.java`, `GeboKnowledgeBaseLocalCacheProperties.java`, auto-configuration, `pom.xml`)
- `gebo.microservices.architecture.parent/gebo.microservices.knowledgebase.client/**` (new module: `RestKnowledgeBaseHierarchyLookupService.java`, `GeboKnowledgeBaseClientProperties.java`, `GeboKnowledgeBaseMicroserviceClientAutoConfiguration.java`, `pom.xml`)
- `.../GAbstractContentManagementSystemHandler.java`, `.../impl/GContentConsumerFactoryImpl.java`, `.../impl/GDocumentReferenceEnricherMapFactoryImpl.java` — call sites updated

### 2.2 `GKnowledgeBaseBrowsingServiceImpl` extracted to its own module

Split out of the (now leaner) abstraction layer into `gebo.architecture.knowledgebase.browsing`, since it is only ever needed by brain/monolith and previously forced that dependency onto every microservice that pulled in the abstraction layer.

**Files:** `gebo.architecture.parent/gebo.architecture.knowledgebase.browsing/**` (new module: `IGKnowledgeBaseBrowsingService.java`, `GKnowledgeBaseBrowsingServiceImpl.java`, `GKnowledgeBaseBrowsingServiceSelectedReferences.java`, `KnowledgeBaseContext.java`)

### 2.3 `GJobStatus` invisible to tyr; replicator rejected replicated entities

**Symptom:** a job launched on a content handler (e.g. filesystem) was invisible to tyr's own `JobStatusController` — tyr has its own, separate Mongo database.

**Root cause:** no replication mechanism was wired for `GJobStatus` at all. Once wired (via the existing but previously-unused `gebo.architecture.replicator` framework), a second bug surfaced: `GAbstractReplicatorReceiverService.accept()` branched between `insert()` (new entity) and `update()` (existing entity) based on whether the entity already existed locally — but a *replicated* entity always carries a pre-assigned code from its source service, and `insert()` throws `GeboPersistenceException` for any entity that already has a code. Every replication attempt failed on its very first write.

**Fix:** added `GJobStatusReplicatorService` (source side, on every content-handler) and `GJobStatusReplicatorReceiverService` (receiver side, on tyr), wired through `gebo.architecture.replicator`. Fixed `GAbstractReplicatorReceiverService.accept()` to always call `update()` (a Mongo upsert) for the non-deleted case, since the code is always pre-assigned for a replicated entity.

**Files:**
- `gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GJobStatusReplicatorService.java` (new), `.../impl/GJobStatusReplicatorReceiverService.java` (new)
- `gebo.architecture.parent/gebo.architecture.replicator/.../service/GAbstractReplicatorReceiverService.java`
- `.../jobs/services/impl/GGeboIngestionJobQueueServiceImpl.java`, `.../jobs/services/impl/GeboIngestionManager.java` — wiring call sites

### 2.4 RabbitMQ inbound bridge never declared an inbound queue — anywhere

**Symptom:** `"Job summary cannot be null"` — no cross-service message ever arrived, on any service, even after the topology and replication fixes above.

**Root cause:** `GeboRabbitMqTopologyDeclarer.declareIfEnabled()` and `RabbitMqInboundBridge.start()` both read `ai.gebo.messaging.rabbitmq.local-microservice-id` directly, with **no fallback** to `spring.application.name` when that property was unset — and it was never set anywhere. Every service's RabbitMQ topology declarer silently no-op'd, so **no microservice ever declared its own inbound queue**, cluster-wide. The correct fallback helper (`RabbitMqTopologyBridgeSupport.resolveLocalMicroserviceId`) already existed and was already used correctly by the message *emitter* side — just not by the *declarer*/*inbound bridge* side.

**Fix:** `RabbitMqInboundBridge.start()` now resolves the local microservice id via `RabbitMqTopologyBridgeSupport.resolveLocalMicroserviceId(properties, currentMicroservice)` before declaring, and passes that id through to `GeboRabbitMqTopologyDeclarer.declareIfEnabled(microserviceId, queueName)`.

**Files:**
- `gebo.architecture.parent/gebo.architecture.messages.rabbitmq/.../config/GeboRabbitMqMessagingProperties.java` — `effectiveInboundQueue()` now takes the resolved id as a parameter
- `.../external/RabbitMqTopologyBridgeSupport.java` — made `public` (was package-private)
- `.../inbound/GeboRabbitMqTopologyDeclarer.java` — `declareIfEnabled(microserviceId, queueName)`, no longer reads the property itself
- `.../inbound/RabbitMqInboundBridge.java` — resolves and passes the id

### 2.5 Topology ownership errors: duplicate-registration crashes

**Symptom:** `IllegalStateException: scheduler-module.scheduler-component already registered` (and the same pattern for `async-publishing-job-module`), crashing services on startup once §2.4's RabbitMQ bridge started actually running.

**Root cause:** `GeboStandardMicroservices` falsely declared tyr as owning `scheduler-module.scheduler-component` and `async-publishing-job-module.*` — these are shared-library components every service instantiates *locally* (a local self-loop, never a cross-service target), not something any one service uniquely owns. The topology's job is to record exactly one owner per `messagingModuleId`; tyr's false claim collided with every other service's own local instance of the same shared component.

**Fix:** removed the false claims from tyr's and vectorizator's (`rag-threashold-autotune-module`, genuinely owned only by brain) topology declarations, and added tyr's **genuine** concentrator identity, `jobs-master-module.user-messages-concentrator-component` (+ `end-of-workflow-compute-service`) — needed so every content-processing microservice can report per-batch status back to tyr (see §9.1 for why this specific module mattered so much later).

**Files:** `gebo.microservices.architecture.parent/gebo.microservices.topology/.../topology/GeboStandardMicroservices.java`

### 2.6 `MessageBrokeringAssembler` re-entrant event guard

Same class of bug as §1.5, newly exposed once the RabbitMQ bridge started actually running end-to-end.

**Files:** `gebo.architecture.parent/gebo.application.messaging/.../messaging/impl/MessageBrokeringAssembler.java`

### 2.7 Workflow-status daemon was dead code with a logic bug

**Symptom:** jobs never transitioned to `finished=true` through the normal path.

**Root cause:** `GWorkflowStatusDeamonServiceImpl` (the *only* code that ever sets `GJobStatus.finished=true`) was missing `@Component` — never a live Spring bean, so its `@Scheduled` method never ran at all. Once added back, a second bug surfaced: an age-gate condition — `(status.getStartDateTime().getTime() + CHECK_JOB_STATUS_PERIOD) > now` — prevented the daemon from re-checking any job older than one scheduling period (2 minutes), even though ingestion routinely takes longer than that.

**Fix:** added `@Component`; removed the age-gate, keeping only the null-check (a still-processing job is re-evaluated on *every* scheduled run for as long as it stays in "processing" state — this is correct, since both the real completion check and the `EXECUTION_TIMEOUT` fallback need to keep running for the job's whole lifetime, not just its first two minutes).

**Files:** `gebo.architecture.parent/gebo.architecture.compute.workflow/.../impl/GWorkflowStatusDeamonServiceImpl.java`

### 2.8 Test token renewal never reached the API clients

**Root cause:** `JobStatusControllerApi` and `GeboChatPipelinesControllerApi` were built once, before the test's polling loop; `renew()`'s updates to the auth header never reached the already-built client instances, so long-running polls eventually hit `401 JWT expired`.

**Fix:** rebuild both clients on every loop iteration instead of once before the loop.

**Files:** `microservices-integration-tests/.../AbstractMicroservicesClusterSetupUseChatTest.java`

---

## 3. Commit [`f944d8814`](https://github.com/geboai/Gebo.ai/commit/f944d88149b5bf67e7bf7926b1e267471c0e0915) — document-content-streaming path auth/DNS/date-format bugs and chunk/cache client base-path slash bug

With RabbitMQ and job-status wired, the pipeline reached document chunking — which needs to *stream a document's bytes* from the content-handler that owns it, through the chunker. This path had four layered bugs, each masking the next, plus a fifth in a sibling client.

### 3.1 `UnknownHostException` resolving the owning content-handler

Same root cause as §1.2 — `GMicroserviceDocumentContentStreamerClient`'s `WebClient` was not `@LoadBalanced`.

**Fix:** added the `@LoadBalanced` builder bean + `spring-cloud-starter-loadbalancer` dependency.

**Files:** `gebo.microservices.architecture.parent/gebo.architecture.microservices.documents.access/pom.xml`, `.../config/MicroserviceDocumentsAccessClientConfiguration.java`

### 3.2 `401 Unauthorized` streaming document content

**Root cause:** `DocumentContentStreamerController` requires an authenticated caller, but `GMicroserviceDocumentContentStreamerClient` sent no bearer token at all.

**Fix:** wired `IGeboCallerTokenPropagator` (the cluster's established caller-identity-propagation abstraction — forwards the caller's own token when on a request thread, or mints a fresh system-identity token otherwise) and added `.headers(this::applyCallerToken)` to the call chain. This exact pattern recurs in §4.5.

**Files:** `.../GMicroserviceDocumentContentStreamerClient.java`, `.../config/MicroserviceDocumentsAccessClientConfiguration.java`

### 3.3 `400 Bad Request`, "Unparseable date"

**Root cause:** a bare `WebClient.builder()`'s default Jackson settings serialize/parse `java.util.Date` differently than the cluster's own `ai.gebo.webconfig.JacksonConfig` bean, which has a `MultiFormatDateDeserializer` on the receiving end that rejects the default shape.

**Fix:** explicitly wired the app's own `JsonMapper` bean into the WebClient's `JacksonJsonEncoder`/`JacksonJsonDecoder` via `.codecs(...)`. This exact pattern recurs identically in §4.4.

**Files:** `.../config/MicroserviceDocumentsAccessClientConfiguration.java`

### 3.4 `500 Internal Server Error`, `SpelEvaluationException`

**Root cause:** `@PreAuthorize("hasRole('ADMIN','USER','APPLICATION')")` — `hasRole()` takes exactly one argument; multi-role OR logic requires `hasAnyRole(...)`. This is a Spring Security SpEL evaluation-time error (500), not a compile/startup-time one, so it only surfaced once real traffic reached the endpoint.

**Fix:** `hasRole(...)` → `hasAnyRole(...)`.

**Files:** `gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../controllers/DocumentContentStreamerController.java`

### 3.5 Optional metadata-enrichment failure aborted the whole chunk batch

**Symptom:** `GeboPersistenceException: Class not found: ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint` — and the entire chunking batch failed with it.

**Root cause:** `GPersistentObjectManagerImpl.findByReference()` does a local `Class.forName(reference.getClassName())` reflection lookup on the reference's stored *concrete* class name. Chunker is content-handler-agnostic — it never has `gebo.filesystem.content.handler` on its classpath — so this lookup fails whenever it's asked to resolve a reference originating from filesystem (or any other specific content-handler). This is expected and unavoidable; the bug was that `DocumentsChunkServiceImpl`'s metadata-enrichment step treated the failure as fatal, when the enrichment is purely optional (already-established downstream code handles `metaDataHeader == null` gracefully).

**Fix:** the enrichment failure now logs a `WARN` and continues with `metaDataHeader = null`, instead of aborting the batch. Confirmed live: "Splitting up document into 3 chunks" immediately followed the warning.

**Files:** `gebo.architecture.parent/gebo.architecture.documents.cache.impl/.../service/impl/DocumentsChunkServiceImpl.java`

### 3.6 `Connection refused: localhost:13004`

**Root cause:** `DocumentsCacheMicroserviceClientConfiguration`'s `WebClient` bean used a hardcoded `http://localhost:13004` default instead of resolving chunker's address through the topology — unlike its sibling bean in the same class, which already did this correctly.

**Fix:** mirrored the sibling bean's topology-resolution approach, and proactively applied the same `@LoadBalanced` + `JsonMapper` fixes as §3.1/§3.3 (this module hadn't hit those specific symptoms yet, but the same root causes were clearly present).

**Files:** `gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/pom.xml`, `.../client/config/DocumentsCacheMicroserviceClientConfiguration.java`, `.../client/DocumentsCacheServiceRestClient.java`, `.../client/DocumentsChunkServiceRestClient.java` (one-line `BASE_PATH` fix in these last two, see §4.1 for the full story)

---

## 4. Commit [`b7565c575`](https://github.com/geboai/Gebo.ai/commit/b7565c575b30b573b1d8f42f6308b8fe629a13f5) — chunker client auth headers and workflow completion for mandatory steps

### 4.1 `404 Not Found` from `.../chunkerapi/DocumentsChunkServiceController/...`

**Symptom:** a URL with a mysteriously fused path segment — `chunkerapi` instead of `chunker/api`.

**Root cause:** `GeboMicroserviceUrlResolver.baseUrlForMicroserviceId()` returns a base URL with **no trailing slash** by explicit contract (documented in its own Javadoc: "callers append a path starting with `/`"). `DocumentsChunkServiceRestClient.BASE_PATH` was `"api/DocumentsChunkServiceController/"` — **no leading slash**. String-concatenating `"http://.../chunker"` (base) + `"api/..."` (path) with neither side contributing a separator produced `.../chunkerapi/...`. `DocumentsCacheServiceRestClient` had the identical bug.

**Fix:** added the missing leading `/` to both `BASE_PATH` constants.

**Files:** `.../client/DocumentsChunkServiceRestClient.java`, `.../client/DocumentsCacheServiceRestClient.java`

### 4.2 A one-off transient Eureka registry-propagation race

Not a code bug: after a fresh `docker compose down/up`, `ClusterParticipantsGuard` briefly denied a legitimate caller (403) because the target service's local Eureka `DiscoveryClient` cache hadn't yet pulled a registry snapshot including the caller's very recent registration. Confirmed self-resolving: the identical call, retried a few minutes later, returned `401` (guard passes, falls through to the next layer) instead of `403`. No fix needed; noted here because it consumed real investigation time and could recur.

### 4.3 / 4.4 Chunk-service clients sent **zero** authentication

**Root cause:** once §4.1's 404 was fixed, the next error was `401 Unauthorized` — `DocumentsChunkServiceRestClient`, `DocumentsCacheServiceRestClient`, and `DocumentContentStreamerWithCacheRestClient` (three separate REST clients in the same module) attached **no bearer token at all** to any call, while chunker's controllers require authentication (`anyRequest().authenticated()`).

**Fix:** applied the same `IGeboCallerTokenPropagator` pattern as §3.2 to all three clients: each gained a constructor parameter, an `applyCallerToken(HttpHeaders)` helper, and `.headers(this::applyCallerToken)` on every call.

**Files:**
- `.../client/DocumentsChunkServiceRestClient.java`, `.../client/DocumentsCacheServiceRestClient.java`, `.../client/DocumentContentStreamerWithCacheRestClient.java`
- `.../client/config/DocumentsCacheMicroserviceClientConfiguration.java` — wires `IGeboCallerTokenPropagator` into all three bean-creation methods
- `.../pom.xml` — added `gebo.microservices.cluster.commons` dependency (provides `IGeboCallerTokenPropagator`)

### 4.5 Jobs could only ever complete via the 2-hour timeout, never the real path

**Symptom:** confirmed via direct `mongosh` inspection of tyr's `gJobStatus` collection — a job that did complete had `startDateTime` and `endDateTime` exactly **2 hours and 2 seconds** apart, proving it finished via `GWorkflowStatusDeamonServiceImpl`'s `EXECUTION_TIMEOUT` fallback, never via real batch-completion detection. Every job, in every prior test run this whole session, had been "succeeding" this way — silently.

**Root cause:** `AbstractWorkflowStatusHandler.checkEnabledNodes()` looked up an `IWorkflowStepEnabledHandler` for every workflow step, including the two *mandatory* steps (`DOCUMENT_DISCOVERY`, `TOKENIZATION`). On tyr, only the three *optional* steps (embedding, graphextraction, fulltext_indexing) have a registered handler — confirmed via tyr's boot logs, which show exactly three `IWorkflowStepEnabledHandlerRepositoryPatternImpl` bindings. `checkEnabledNodes()` treated "no handler found" as `enabled = false`, disabling the workflow's root step — which meant the per-step aggregated batch data (`ContentsBatchProcessed`) was never populated for the completion check, so `completed` never became `true` through the real path. This is inconsistent with `GStandardWorkflowStep.verifyEnabledModules()`, which already special-cases mandatory steps by skipping the enablement check entirely (`if (step.isMandatoryStep()) return true;`) — `checkEnabledNodes()` was simply missing the same bypass.

**Fix:** `handler == null || handler.isEnabled(...)` instead of `handler != null && handler.isEnabled(...)` — a missing handler now means "not gated" (enabled by default), matching the existing mandatory-step semantics elsewhere in the same codebase.

**Verification:** after redeploying only tyr with this fix, two jobs that had been stuck at `finished=false` for 8+ and 18+ minutes both flipped to `finished=true` within **10 seconds** of tyr's restart — not 2 hours.

**Files:** `gebo.architecture.parent/gebo.architecture.compute.workflow/.../service/AbstractWorkflowStatusHandler.java`

---

## 5. Commit [`16b2b0aa3`](https://github.com/geboai/Gebo.ai/commit/16b2b0aa3df077b4aa8f05398a55c12cb1da2f81) — Hazelcast models-replication cluster split and embedding-model cache miss

This was the deepest and most subtle bug of the whole effort: vectorization would silently receive its input, do nothing, and never log a single error.

### 5.1 Diagnosis: a genuinely idle, not blocked, vectorizator

A `jcmd`/`jstack` thread dump of the vectorizator container showed its worker threads (`gebo.ai-tpool-1`/`-2`) sitting in `Object.wait()` inside the normal message-poll loop (`MessageReceiverRunner.run():162`) — **not** blocked on any network call. 0.26% CPU. RabbitMQ queue depth: 0 (nothing backed up). `mongosh` against tyr's `contentsBatchProcessed` collection showed the `embedding` workflow step's aggregated `batchDocumentsInput=6` but `batchDocumentsProcessed=0`, forever. Vectorizator had *received* the six documents-ready-for-embedding messages and then done nothing further with them — silently.

### 5.2 Root cause: two disjoint Hazelcast clusters that could never merge

`GEmbeddingModelRuntimeConfigurationDaoImpl` loads all persisted embedding-model configs **once**, at its own startup (`initializeRuntimeModels()`, correctly deferred to `ContextRefreshedEvent`). A model created *after* that point (e.g. the one this test creates fresh, every run) is expected to reach every other instance via `GLlmModelClusterSynchronizer`'s Hazelcast pub/sub broadcast. That broadcast never arrived, because brain and vectorizator/graphicator had formed **two separate Hazelcast clusters**:

```
brain:         "Using TCP/IP discovery"   Members {size:1}  (alone)
vectorizator:  "Using Multicast discovery" Members {size:2}  (itself + graphicator)
```

`DiscoveryClientClusterTopologyProvider.getModelsReplicationClusterTopology()` — the bean that seeds Hazelcast's TCP/IP member list — is invoked once, synchronously, as part of an ordinary `@Bean` factory method during Spring context refresh. This happens **before** `EurekaAutoServiceRegistration` fires (which itself only runs after the web server binds) — i.e. structurally too early for *any* service, including itself, to be visible in Eureka yet. Confirmed directly: after extending the retry window to 240 seconds, all three participants logged an **empty** discovery snapshot on every single attempt for the full 240 seconds. No amount of waiting inside that bean helps — the ordering is fundamentally backwards. When TCP/IP seeding finds no members at all, Hazelcast falls back to its default multicast join; whichever members *also* fell back to multicast (vectorizator + graphicator, this time) find each other, but never brain, which had already committed to TCP/IP with a stale/incomplete list from whenever *it* happened to query. Restarting the isolated members together does not reliably fix this either (confirmed by direct test): if they restart at the same moment, none of their own fresh registrations have propagated to each other's discovery cache yet, so they can end up isolated again, simultaneously — this was observed to actually make things *worse* on one attempt (3 isolated singletons instead of a 2+1 split).

A secondary, compounding bug: the query was also **unscoped** — it iterated all ~15 topology members (`GeboMicroservicesTopology.microservices()`) instead of the three actual Hazelcast participants (`GeboModelsReplicationParticipants`), making convergence depend on the registration timing of a dozen unrelated services that have nothing to do with this cache.

**Fix (two parts):**
1. `DiscoveryClientClusterTopologyProvider` now queries only `GeboModelsReplicationParticipants` (not the full topology), and retries until the snapshot is **non-empty and unchanged across a retry interval** (not just non-empty on a single read — a single non-empty read can still be *stably wrong*, missing a slower peer), up to 24 attempts × 10s = 240s total.
2. As defense in depth, independent of whether Hazelcast ever glitches again: `GEmbeddingModelRuntimeConfigurationDaoImpl.findByPredicate`/`findListByPredicate` now fall back to a fresh database read (`syncMissingFromPersistence()`, reusing the exact same source `initializeRuntimeModels()` reads) whenever the in-memory cache comes up empty, adopting any model not yet seen.

**Verification:** after this fix, a full stack redeploy showed `Members {size:3, ver:3}` on brain, vectorizator, *and* graphicator simultaneously — reproduced on two separate full redeploys.

**Files:**
- `gebo.microservices.architecture.parent/gebo.microservices.models.replication.cluster/.../DiscoveryClientClusterTopologyProvider.java`
- `.../config/ModelsReplicationClusterAutoConfiguration.java` — injects `GeboModelsReplicationParticipants` instead of `GeboMicroservicesTopology` for this specific bean
- `gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GEmbeddingModelRuntimeConfigurationDaoImpl.java`

---

## 6. Commit [`eeabb09c1`](https://github.com/geboai/Gebo.ai/commit/eeabb09c134db732b4b0e2f8bc25e17906e77a44) — `GeboTemplatedChatRequest.streamResponse` rejects the null every OpenAPI client sends

With ingestion fully solved, both integration test classes progressed all the way to the chat pipeline — and immediately hit a bare `400 Bad Request` with **no application-level log line at all**.

**Root cause:** `GeboTemplatedChatRequest.streamResponse` was declared as a primitive `boolean`. Every OpenAPI-generated client (the test uses `gebo.microservices.api.client.brain`) leaves this field untyped/nullable and, when the caller doesn't explicitly set it, serializes it as an explicit JSON `"streamResponse": null`. Jackson's strict primitive-type deserialization rejects `null` for a primitive outright — `HttpMessageNotReadableException: Cannot map \`null\` into type \`boolean\`` — which Spring turns into a bare 400 *before* the request object, or any application code, is ever constructed. This is why nothing was logged: the failure happens entirely inside HTTP message conversion, upstream of the controller method.

**Fix:** widened the field to `Boolean` (still defaulting to `false`). Confirmed via a full-codebase search that no production code reads it as a primitive (`.isStreamResponse()` had zero call sites) — Lombok's `@Data` generates `isX()` only for primitive `boolean`; for the `Boolean` wrapper it generates `getX()`, so this is a compile-time-safe change.

**Files:** `gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/.../llmexchange/model/GeboTemplatedChatRequest.java`

---

## 7. Fix-class summary

Several distinct symptoms across this effort trace back to the *same* small set of underlying mistakes, repeated in different modules. Recognizing the pattern made later instances much faster to diagnose:

| Pattern | Symptom | Instances |
|---|---|---|
| Bare `WebClient.builder()` instead of `@LoadBalanced` | `UnknownHostException` for a Eureka service-id | §1.2 (×3), §3.1, §3.6 |
| No caller token attached to an outbound call | `401 Unauthorized` against an authenticated controller | §3.2, §4.3/4.4 (×3) |
| Default Jackson `JsonMapper` instead of the cluster's own | `400`, "Unparseable date" | §3.3, §3.6 |
| `GeboMicroserviceUrlResolver`'s no-trailing-slash contract vs. a path missing its leading slash | `404`, URL segments fused together | §4.1 (×2) |
| Re-entrant `ContextRefreshedEvent` from a `@LoadBalanced` client's child context | `StackOverflowError` | §1.5 (×2), §2.6 |
| A dead-simple `null`/`isEnabled` gate flipped the wrong way for a service missing an otherwise-optional dependency | Silent, total feature failure with no error logged | §4.5, §5.2 |

## 8. Explicitly out of scope

`SetupUseMicroservicesClusterAgenticChatIT` still fails one assertion: `verifyRoutingDecision` expected `DELEGATED_AGENT` but the LLM-based router chose `RAG_LLM_RESPONSE` for one specific query. This is a semantic routing-behavior question for the agentic-network feature — not a cluster/infrastructure integration bug — and was left as a separate, likely non-deterministic concern for whoever owns that feature.

## 9. Final verification

`SetupUseMicroservicesClusterPipelineIT` passed completely: full ingestion (chunking → tokenization → embedding → graph extraction → full-text indexing) followed by **5 real multi-turn chat exchanges** with genuine LLM-generated RAG answers (grounded in the uploaded Gnostic-text PDFs, correctly citing when a query fell outside the knowledge base). Both ingestion jobs in the final run completed in **~2.5–3 minutes** via the real completion path — not the 2-hour timeout fallback that had silently masked §4.5's bug in every prior run.

### 9.1 Complete list of files changed

<details>
<summary>39 files — commit d0ef4cd5f</summary>

```
dockers/gebo.microservices/.env
dockers/gebo.microservices/docker-compose.yml
gebo.apps.parent/gebo.microservices.apps.parent/brain.gebo.ai/pom.xml
gebo.apps.parent/gebo.microservices.apps.parent/filesystem.gebo.ai/pom.xml
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../cluster/GAbstractClusteredModelRuntimeConfigurationDao.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GChatModelRuntimeConfigurationDaoImpl.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GEmbeddingModelRuntimeConfigurationDaoImpl.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GImageModelRuntimeConfigurationDaoImpl.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GRankerModelRuntimeConfigurationDaoImpl.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GTextToSpeechModelRuntimeConfigurationDaoimpl.java
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GTranscriptModelRuntimeConfigurationDaoimpl.java
gebo.architecture.parent/gebo.architecture.security/.../config/GeboAISecurityConfig.java
gebo.architecture.parent/gebo.architecture.security/.../model/UserPrincipal.java
gebo.architecture.parent/gebo.architecture.security/.../services/impl/CustomUserDetailsService.java
gebo.architecture.parent/gebo.architecture.security/.../services/impl/DirectoryBackedUserDetailsService.java (new)
gebo.microservices.architecture.parent/gebo.microservices.acl.client/pom.xml
gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../config/GeboAclMicroserviceClientAutoConfiguration.java
gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../config/GeboAclClusterControllerAutoConfiguration.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../config/GeboAclClusterControllerProperties.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../controller/AclAliasesClusterController.java
gebo.microservices.architecture.parent/gebo.microservices.acl.client/.../org.springframework.boot.autoconfigure.AutoConfiguration.imports
gebo.microservices.architecture.parent/gebo.microservices.cluster.commons/.../cluster/ClusterParticipantsGuard.java (new)
gebo.microservices.architecture.parent/gebo.microservices.cluster.commons/.../cluster/ClusterParticipantsOnlyInterceptor.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/pom.xml
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../config/GeboSecretsMicroserviceClientAutoConfiguration.java
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../config/GeboSecretsClusterControllerAutoConfiguration.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../config/GeboSecretsClusterControllerProperties.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../controller/SecretsClusterController.java
gebo.microservices.architecture.parent/gebo.microservices.secrets.client/.../org.springframework.boot.autoconfigure.AutoConfiguration.imports
gebo.microservices.architecture.parent/gebo.microservices.security.client/pom.xml
gebo.microservices.architecture.parent/gebo.microservices.security.client/.../config/GeboSecurityMicroserviceClientAutoConfiguration.java
gebo.microservices.architecture.parent/gebo.microservices.security.client/.../config/GeboSecurityClusterControllerAutoConfiguration.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.security.client/.../config/GeboSecurityClusterControllerProperties.java (removed)
gebo.microservices.architecture.parent/gebo.microservices.security.client/.../controller/SecurityDirectoryClusterController.java
gebo.microservices.architecture.parent/gebo.microservices.security.client/.../org.springframework.boot.autoconfigure.AutoConfiguration.imports
gebo.systems.parent/.../impl/GFilesystemChangesHandlingService.java
microservices-integration-tests/pom.xml
microservices-integration-tests/.../AbstractMicroservicesClusterSetupUseChatTest.java
pom.xml
```
</details>

<details>
<summary>59 files — commit 023313d54</summary>

```
dockers/gebo.microservices/config/application.yml
gebo.apps.parent/gebo.apps.monolithic.starter/pom.xml
gebo.apps.parent/gebo.microservices.apps.parent/aws-s3.gebo.ai/pom.xml, .../application.yml
gebo.apps.parent/gebo.microservices.apps.parent/brain.gebo.ai/pom.xml
gebo.apps.parent/gebo.microservices.apps.parent/confluence.gebo.ai/pom.xml, .../application.yml
gebo.apps.parent/gebo.microservices.apps.parent/{filesystem,gateway,git,googledrive,integration,jira,mcpclient,sharepoint,userspace}.gebo.ai/.../application.yml (+ pom.xml where noted)
gebo.apps.parent/gebo.microservices.apps.parent/integration.gebo.ai/pom.xml
gebo.apps.parent/gebo.microservices.apps.parent/jira.gebo.ai/pom.xml
gebo.apps.parent/gebo.microservices.apps.parent/tyr.gebo.ai/.../application.yml
gebo.architecture.parent/gebo.application.messaging/.../messaging/impl/MessageBrokeringAssembler.java
gebo.architecture.parent/gebo.application.messaging/.../model/GStandardModulesConstraints.java
gebo.architecture.parent/gebo.architecture.compute.workflow/pom.xml
gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GJobStatusReplicatorReceiverService.java (new)
gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GJobStatusReplicatorService.java (new)
gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GWorkflowStatusDeamonServiceImpl.java
gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GGeboIngestionJobQueueServiceImpl.java
gebo.architecture.parent/gebo.architecture.compute.workflow/.../jobs/services/impl/GeboIngestionManager.java
gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../GAbstractContentManagementSystemHandler.java
gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../IGKnowledgeBaseHierarchyLookupService.java (new)
gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../impl/GContentConsumerFactoryImpl.java
gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../impl/GDocumentReferenceEnricherMapFactoryImpl.java
gebo.architecture.parent/gebo.architecture.knowledgebase.browsing/** (new module, 4 files)
gebo.architecture.parent/gebo.architecture.replicator/.../service/GAbstractReplicatorReceiverService.java
gebo.architecture.parent/gebo.architecture.messages.rabbitmq/.../config/GeboRabbitMqMessagingProperties.java
gebo.architecture.parent/gebo.architecture.messages.rabbitmq/.../external/RabbitMqTopologyBridgeSupport.java
gebo.architecture.parent/gebo.architecture.messages.rabbitmq/.../inbound/GeboRabbitMqTopologyDeclarer.java
gebo.architecture.parent/gebo.architecture.messages.rabbitmq/.../inbound/RabbitMqInboundBridge.java
gebo.architecture.parent/pom.xml
gebo.core.parent/gebo.core/pom.xml
gebo.core.parent/gebo.knowledgebase.hierarchy.local/** (new module, 5 files)
gebo.microservices.architecture.parent/gebo.microservices.knowledgebase.client/** (new module, 5 files)
gebo.microservices.architecture.parent/gebo.microservices.topology/.../topology/GeboStandardMicroservices.java
gebo.microservices.architecture.parent/pom.xml
microservices-integration-tests/.../AbstractMicroservicesClusterSetupUseChatTest.java
```
</details>

<details>
<summary>9 files — commit f944d8814</summary>

```
gebo.architecture.parent/gebo.architecture.contentsystems.abstraction.layer/.../controllers/DocumentContentStreamerController.java
gebo.architecture.parent/gebo.architecture.documents.cache.impl/.../service/impl/DocumentsChunkServiceImpl.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/pom.xml
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/DocumentsCacheServiceRestClient.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/DocumentsChunkServiceRestClient.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/config/DocumentsCacheMicroserviceClientConfiguration.java
gebo.microservices.architecture.parent/gebo.architecture.microservices.documents.access/pom.xml
gebo.microservices.architecture.parent/gebo.architecture.microservices.documents.access/.../GMicroserviceDocumentContentStreamerClient.java
gebo.microservices.architecture.parent/gebo.architecture.microservices.documents.access/.../config/MicroserviceDocumentsAccessClientConfiguration.java
```
</details>

<details>
<summary>6 files — commit b7565c575</summary>

```
gebo.architecture.parent/gebo.architecture.compute.workflow/.../service/AbstractWorkflowStatusHandler.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/pom.xml
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/DocumentContentStreamerWithCacheRestClient.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/DocumentsCacheServiceRestClient.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/DocumentsChunkServiceRestClient.java
gebo.microservices.architecture.parent/gebo.architecture.documents.cache.microservice.client/.../client/config/DocumentsCacheMicroserviceClientConfiguration.java
```
</details>

<details>
<summary>3 files — commit 16b2b0aa3</summary>

```
gebo.architecture.parent/gebo.architecture.llms.abstraction.layer/.../impl/GEmbeddingModelRuntimeConfigurationDaoImpl.java
gebo.microservices.architecture.parent/gebo.microservices.models.replication.cluster/.../DiscoveryClientClusterTopologyProvider.java
gebo.microservices.architecture.parent/gebo.microservices.models.replication.cluster/.../config/ModelsReplicationClusterAutoConfiguration.java
```
</details>

<details>
<summary>1 file — commit eeabb09c1</summary>

```
gebo.architecture.parent/gebo.architecture.chat.abstraction.layer/.../llmexchange/model/GeboTemplatedChatRequest.java
```
</details>

### 9.2 References

- Branch: [`fix/microservices-cluster-integration-bugs`](https://github.com/geboai/Gebo.ai/tree/fix/microservices-cluster-integration-bugs)
- Pull request: [#131](https://github.com/geboai/Gebo.ai/pull/131) (open)
- Commits: [`d0ef4cd5f`](https://github.com/geboai/Gebo.ai/commit/d0ef4cd5f8459ad84ca69aad04138cae68414ed5) · [`023313d54`](https://github.com/geboai/Gebo.ai/commit/023313d54da5706164ab8051c8bddd19577af0e6) · [`f944d8814`](https://github.com/geboai/Gebo.ai/commit/f944d88149b5bf67e7bf7926b1e267471c0e0915) · [`b7565c575`](https://github.com/geboai/Gebo.ai/commit/b7565c575b30b573b1d8f42f6308b8fe629a13f5) · [`16b2b0aa3`](https://github.com/geboai/Gebo.ai/commit/16b2b0aa3df077b4aa8f05398a55c12cb1da2f81) · [`eeabb09c1`](https://github.com/geboai/Gebo.ai/commit/eeabb09c134db732b4b0e2f8bc25e17906e77a44)
- Tests exercised: `microservices-integration-tests/setup-use-microservices-tests` — `SetupUseMicroservicesClusterAgenticChatIT`, `SetupUseMicroservicesClusterPipelineIT`
- Deployment exercised: `dockers/gebo.microservices/docker-compose.yml` (21 containers: eureka, gateway, heimdall, brain, vectorizator, graphicator, chunker, tyr, git, filesystem, uploads, userspace, sharepoint, confluence, jira, aws-s3, googledrive, mcpclient, integration, fulltextor, plus mongo/rabbit/neo4j/opensearch/qdrant/grafana/prometheus/tempo/otel-collector infra)
- Related design docs: [`docs/MICROSERVICES-INTEGRATION.md`](./MICROSERVICES-INTEGRATION.md), [`docs/MICROSERVICES-CONTROLLERS.md`](./MICROSERVICES-CONTROLLERS.md), [`docs/microservices-integration-chat.md`](./microservices-integration-chat.md)
