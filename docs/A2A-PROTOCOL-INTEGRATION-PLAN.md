# A2A Protocol Integration Plan

> **Architecture Decision Record — Draft for review.**
> A plan to export Gebo agents & agent-networks over the Agent2Agent (A2A) protocol, and to
> register external A2A agents as first-class network participants — persisted in MongoDB, built
> entirely on patterns the codebase already uses.
>
> Scope: `gebo.architecture.parent` · 2 new modules · 0 core-contract changes ·
> precedent: `mcp-server` / `mcp-clients` · grounded in the `develop` tree as of 2026-08-28.
> **Nothing here is implemented yet** — this document exists to be corrected before a line is written.

An HTML rendering of this plan is also published as an artifact for easier reading.

## Decisions locked (2026-08-28)

1. **Protocol layer:** use the **official A2A Java SDK** for the wire model and JSON-RPC dispatch
   rather than a hand-rolled model (see Open Decision 1 → resolved, and the Spring-MVC compatibility
   note there).
2. **First cut:** **both directions** (export + integrate) — *but* with an admin-gated,
   secure-by-default posture: **no agent and no network of agents is externally visible or callable
   by default.** An admin must explicitly configure and enable each exported agent/network, and each
   imported remote agent. Absent an enabled config, nothing is published and nothing is reachable.
3. **Task lifecycle:** **SSE-first** — synchronous + `message/stream`. No persisted task state in the
   first cut; `tasks/resubscribe` + push-notification webhooks are a follow-up.
4. **Module layout:** `gebo.architecture.a2a-server` / `gebo.architecture.a2a-clients`, parallel to
   the mcp modules.

---

## Thesis

The agent layer is already a network-of-agents runtime with a native capability model and a live
streaming lifecycle. A2A is the same shape of problem the MCP modules already solve — export
internal capability, integrate external capability — over a different wire format. This plan reuses
those two precedents almost line-for-line.

- `IGGenericAgentService.getAgentCapabilities()` is already an "agent card" in all but wire format.
- `IGNetworkAgentService.onMessage()` + the reactive `Flux`/`Sinks` path is already a task lifecycle.
- `IGDynamicAgentServiceSupplier` is a clean seam for injecting remote agents at runtime.

A2A needs a **projection** of these onto JSON-RPC + Agent Cards, not new agent machinery.

---

## Part 1 — What already exists

### The agent runtime — `gebo.architecture.agents.abstraction.layer`

- **Capability model.** `IGGenericAgentService.getAgentCapabilities(GAgentConfig)` returns
  `AgentCapabilities` — a `summary` plus lists of `capabilities`, `catalogs`, `resources`, `tools`,
  each an `AgentCapabilityResource{code, name, description}`. Its own Javadoc says it is rendered
  into "the shared network-of-agents description used by coordinating agents to reason about their
  reachable peers."
- **Network agent contract.** `IGNetworkAgentService<I,O>` handles an `AgentsExchangeMessage<I>`
  and returns `List<AgentsExchangeMessage<O>>`, driven inside a `GAgentsNetwork` (participants,
  `CommunicationPolicy` ALLOW/DENY lists, input/output nodes, `maxLoopIteration`). Progress streams
  through `INotificationSink`; the reactive variant emits `IGPartialOperation<O>` onto a `Flux` via
  `Sinks.Many`.
- **Runtime aggregation.** `AgentServiceRuntimeDaoImpl` gathers every `IGGenericAgentService` bean
  *plus* every `IGDynamicAgentServiceSupplier.get()`. That supplier is how the `standard` module
  already injects runtime agents (e.g. `externalSourcesAgentServicesSupplier()`).
- **Persistence & CRUD.** `GAgentConfig` / `GAgentsNetwork` extend `GBaseObject` (Mongo `@Id code`),
  stored via `IGBaseMongoDBRepository<T>`, surfaced through `IGRuntimeConfigurationDao`, mutated
  through an `IGAgentsNetworkCrudService` returning `OperationStatus<T>` + `GUserMessage`s, all
  ACL-secured via `IGObjectWithSecurity`/`IAclGrantedResource` (`accessibleToAll/Users/Groups`,
  `aclAliases`).

### Chat — `gebo.architecture.chat.abstraction.layer`

A pipeline/step engine (routing, RAG, tool-using, deep-search steps) with reactive streaming output
(`ISinkUIEmitter`) and Mongo-persisted session state. It consumes the agent network through
`gebo.architecture.agents.standard` — `GReactiveChatAgentsNetworkService` extends
`GAbstractReactiveOutputAgentsNetworkService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope>`
and exposes `getFlux()`. **A2A does not touch chat directly; it plugs in one layer below, at the
agent runtime.**

### The precedent to copy — the MCP modules

You have already built this exact "export / integrate" pair twice.

| Concern | `mcp-server` (export) | `mcp-clients` (integrate) |
|---|---|---|
| Config doc (Mongo) | `GeboMCPServerConfig` | `MCPClientConfig` |
| Live endpoint | `GeboMcpServerRegistry` holds a `volatile` composite `RouterFunction`; one delegating bean in `GeboMcpDispatcherConfig` routes to it, so endpoints hot-reload with no restart | `McpClientConnector` builds & initializes a client per transport |
| Build / connect | `GeboMcpServerBuilder` wires transport + capabilities, wraps router in a 403 access filter | transport switch: `STREAMABLE_HTTP` / `SSE_LEGACY` / `STDIO` |
| CRUD keeps runtime in sync | `GMCPServerConfigManagerServiceImpl`: `insert/update` → `registry.reload(code)`, `delete` → `registry.remove(code)` | `McpClientManagementService.testAndDiscovery()` probes & diffs remote features |
| Auth | Gebo identity via security context capture | `McpAuthMode` resolved to an `Authorization` header via `IGeboSecretsAccessService` / `IGOauth2AccessTokenService` |
| Admin API | `GeboMCPServerAdminController` (`hasRole('ADMIN')`) | `McpClientConfigController` (`hasRole('ADMIN')`) |

**Greenfield.** No A2A code exists today — a repo-wide grep for `a2a` / `AgentCard` /
`.well-known/agent` is clean, and there is no `spring-ai` A2A artifact in the local `.m2`. This is a
new build, not a retrofit.

---

## Part 2 — How A2A maps onto Gebo

Every A2A concept has an existing home. The work is projection and transport, not new domain
modelling.

| A2A concept | Existing Gebo concept |
|---|---|
| Agent Card at `/.well-known/agent-card.json` | `AgentCapabilities` projected from `GAgentConfig` / `GAgentsNetwork` |
| Skill (id, name, description, tags, i/o modes) | `AgentCapabilityResource` + the `capabilities` list |
| JSON-RPC `message/send` · `message/stream` (SSE) | `IGNetworkAgentService.onMessage()` + reactive `Flux`/`Sinks.Many` |
| Task lifecycle `submitted→working→completed/failed/input-required` | `AgentsCollaborationSessionContext` + `IGPartialOperation` / `PartialOperationStatus` + `INotificationSink` |
| Message parts (Text / File / Data) | `AgentsExchangeMessage` payload + rich-response fragments |
| Security schemes (OAuth2 / API-key / Bearer) | `McpAuthMode` pattern + secrets / oauth2 services (reused verbatim) |
| Remote server registration + discovery | `MCPClientConfig` + `testAndDiscovery()` + connector |
| Live publish / unpublish of an endpoint | `GeboMcpServerRegistry` composite-router hot-reload |

---

## Part 3 — Module A: export Gebo over A2A

**New module:** `gebo.architecture.parent/gebo.architecture.a2a-server`. Mirrors `mcp-server`.
Publishes selected agents & networks as an A2A server: an Agent Card plus a JSON-RPC endpoint that
turns inbound tasks into network runs and streams status back.

| Class | Kind | Role |
|---|---|---|
| `A2AServerConfig` | new | Mongo doc, `extends GBaseObject implements IGObjectWithSecurity, IAclGrantedResource`. Fields: `exportedRelativeUrl`, `enabled` (**defaults off**), `List<A2AExportedAgent>` (agentConfigCode / networkCode / skillName), exported networks, selected `securityScheme`, ACL fields. Direct analog of `GeboMCPServerConfig`. **Secure by default:** an agent or network is published *only* when an admin creates a config, adds it to the export list, and sets `enabled=true`; the ACL fields still gate who may call the published endpoint. |
| `A2AServerConfigRepository` | new | `extends IGBaseMongoDBRepository<A2AServerConfig>` with `findByExportedRelativeUrl(...)`, like `GeboMCPServerConfigRepository`. |
| `A2AAgentCardBuilder` | new | Projects each exported agent's `AgentCapabilities` (summary→description, capabilities/tools/catalogs→A2A `skills`) into an `AgentCard`, served at `<url>/.well-known/agent-card.json`. |
| `A2AServerRegistry` | new | Holds the `volatile` composite `RouterFunction`; `reload(code)` / `remove(code)` / `reloadAll()`. Copies `GeboMcpServerRegistry` structure. |
| `A2ADispatcherConfig` | new | One delegating `@Bean RouterFunction` → `registry.currentComposite().route(request)`. Copies `GeboMcpDispatcherConfig` so endpoints change without restart. |
| `A2AServerBuilder` | new | Builds the per-config router: the well-known card route + the JSON-RPC route (`message/send`, `message/stream` SSE, `tasks/get`, `tasks/cancel`), wrapped in an access filter that 403s callers not granted on the config — mirrors `GeboMcpServerBuilder`'s filter. |
| `A2ATaskBridge` | new | **Heart of the module.** Inbound task → build an `AgentsExchangeMessage` inside an `AgentsCollaborationSessionContext` → invoke the target `IGNetworkAgentService` / network under the caller's `ReactiveIdentityUtil` → map `INotificationSink` / `IGPartialOperation` events onto A2A task-status SSE frames and the terminal `PartialOperationStatus` onto the final artifact. |
| `A2AServerConfigManagerService` (+Impl) | new | CRUD through `IGPersistentObjectManager`; validates the relative URL; applies ACLs via `IGSecurityService`; after every mutation calls `registry.reload/remove`. Line-for-line `GMCPServerConfigManagerServiceImpl`. |
| `GeboA2AServerAdminController` | new | `@RestController @PreAuthorize("hasRole('ADMIN')")`, returns `OperationStatus<A2AServerConfig>`. Mirrors `GeboMCPServerAdminController`. |

> **Caveat:** the MCP-server side of "export an agent as a callable" (`GeboMCPAgentAsToolProvider.buildTools()`)
> currently returns `List.of()` — it is still a stub. So the export half of A2A is genuinely new
> ground; only the *scaffolding* (registry, dispatcher, builder, manager) is proven.

---

## Part 4 — Module B: integrate external A2A agents

**New module:** `gebo.architecture.parent/gebo.architecture.a2a-clients`. Mirrors `mcp-clients`.
Registers a remote A2A agent, discovers its skills, and exposes it as an `IGNetworkAgentService` so
it drops into any `GAgentsNetwork` exactly like an internal agent.

| Class | Kind | Role |
|---|---|---|
| `A2ARemoteAgentConfig` | new | Mongo doc, `extends GBaseObject`. Fields: `baseUrl`, `agentCardPath`, `transportType`, `authMode`, `secretCode` / `oauth2AuthenticatorCode`, `exportingPrefix`, `enabled` (**defaults off**), cached discovered `skills`, ACL fields. Shaped like `MCPClientConfig`. **Admin-gated:** a remote agent is only mounted as a network participant when an admin has registered it and set `enabled=true`; the supplier skips disabled configs. |
| `A2AAuthMode` | reuse pattern | Same enum shape as `McpAuthMode` (`NONE, API_KEY, STATIC_BEARER_TOKEN, OAUTH2_CLIENT_CREDENTIALS, OAUTH2_AUTHORIZATION_CODE_PER_USER, USER_TOKEN_RELAY`). |
| `A2AClientConnector` | new | Fetches the Agent Card; resolves the `Authorization` header via `IGeboSecretsAccessService` / `IGOauth2AccessTokenService` / `IGOauth2RuntimeConfigurationDao` — the credential-resolution logic lifts almost verbatim from `McpClientConnector.resolveAuthorizationHeader()`. Speaks JSON-RPC `message/send` + `message/stream`. |
| `A2AClientManagementService` (+Impl) | new | `testAndDiscovery()` fetches the card and diffs skills against the stored config (added / removed flags) — same contract & `OperationStatus` returns as `McpClientManagementService`. Plus insert/update/delete/list. |
| `RemoteA2ANetworkAgentService` | new | **The integration seam.** `implements IGNetworkAgentService<I,O>`; its `onMessage()` translates the exchange message into an A2A task call and streams the reply back through the `INotificationSink`. Exports `getAgentCapabilities()` built from the discovered skills, so the remote agent shows real capabilities in the network composer. |
| `A2ADynamicAgentServiceSupplier` | reuse seam | `implements IGDynamicAgentServiceSupplier` — returns one `RemoteA2ANetworkAgentService` per enabled `A2ARemoteAgentConfig`. **This is the hook:** `AgentServiceRuntimeDaoImpl` already ingests these suppliers, so remote agents become selectable network participants with zero core changes. |
| `A2AClientConfigController` | new | `@PreAuthorize("hasRole('ADMIN')")`; testAndDiscovery / insert / update / delete / list. Mirrors `McpClientConfigController`. |

> **Optional:** remote A2A skills can *additionally* be surfaced as chat `ToolCallback`s, the way
> discovered MCP tools already are through `MCPToolsExporter` — a follow-on, not part of the core cut.

---

## Part 5 — Shared wire model

**Decision: use the official A2A Java SDK** rather than hand-rolling these POJOs. The SDK supplies
the wire vocabulary (`AgentCard`, `AgentSkill`, capabilities, security schemes; `Message` + `Part`
Text/File/Data; `Task`, `TaskStatus`, `TaskState`, `Artifact`; the JSON-RPC envelopes) and the
request/response dispatch, so server and client stay spec-conformant by construction and we own less
protocol code. The Gebo-side work becomes: (a) map `AgentCapabilities` ⇄ the SDK's `AgentCard`, and
(b) bridge the SDK's task handler ⇄ `IGNetworkAgentService` / the streaming lifecycle.

**Open sub-question (needs your input):** the exact SDK artifact + version, and its transport fit.
The canonical implementation is `a2a-java` (github.com/a2aproject/a2a-java, `io.a2a.sdk:*`
coordinates), but it is not in the local `.m2` yet and its **server** transports are
Jakarta/Quarkus-oriented; this repo serves MCP over **Spring MVC `RouterFunction`s** with a
hot-reload registry. So:

- the **client** side (`a2a-clients`) can use the SDK client cleanly;
- the **server** side (`a2a-server`) needs verification that the SDK offers a Spring-MVC-compatible
  server transport (or that we drive its request handler from our own `RouterFunction`, keeping the
  hot-reload registry). If no clean Spring fit exists, the fallback is to use the SDK **types** for
  the wire model but keep our own Spring MVC JSON-RPC/SSE handlers.

I will confirm coordinates and Spring compatibility against Maven Central before writing module code.

---

## Part 6 — Request / response lifecycle

Both directions ride the streaming machinery the agent runtime already has — no new concurrency model.

**Inbound — a remote caller invokes an exported Gebo agent:**

1. **JSON-RPC in** — `message/send` or `message/stream` hits the config's route.
2. **Access filter** — ACL check on `A2AServerConfig`; 403 if not granted.
3. **Build message** — Task → `AgentsExchangeMessage` in a collaboration session.
4. **Run network** — `onMessage()` under the caller's `ReactiveIdentityUtil`.
5. **Stream status** — Sink / `IGPartialOperation` → SSE task-status frames.
6. **Final artifact** — `PartialOperationStatus` → `completed` / `failed`.

**Outbound — a Gebo network calls a registered remote A2A agent:**

1. **`onMessage()`** — `RemoteA2ANetworkAgentService` receives an exchange message.
2. **Resolve auth** — `Authorization` header via secrets / oauth2 services.
3. **`message/stream`** — JSON-RPC task sent to the remote base URL.
4. **Consume SSE** — remote task-status events read back.
5. **Bridge to sink** — progress → `INotificationSink` notifications.
6. **Return outputs** — final artifact → `List<AgentsExchangeMessage<O>>`.

---

## Part 7 — What is reused, unchanged

| Capability | Existing type reused |
|---|---|
| Persistence | `GBaseObject`, `IGBaseMongoDBRepository`, `IGPersistentObjectManager` |
| Validated CRUD + user messages | `OperationStatus<T>`, `GUserMessage`, `PartialOperationStatus` |
| Security & ACLs | `IGObjectWithSecurity`, `IAclGrantedResource`, `IGSecurityService`, `ReactiveIdentityUtil` |
| Credentials | `IGeboSecretsAccessService`, `IGOauth2AccessTokenService`, `IGOauth2RuntimeConfigurationDao` |
| Live endpoint hot-reload | registry + delegating `RouterFunction` (from mcp-server) |
| Runtime agent injection | `IGDynamicAgentServiceSupplier` → `AgentServiceRuntimeDaoImpl` |
| Capability & streaming lifecycle | `AgentCapabilities`, `IGNetworkAgentService`, `INotificationSink`, `IGPartialOperation` |

**Core-contract impact: none.** Everything attaches through existing extension points. No change to
`IGNetworkAgentService`, the network executor, or the repository/CRUD contracts.

---

## Part 8 — Build order

A genuine sequence — each step compiles and is testable before the next.

1. **Shared wire model (`a2a-common`)** — AgentCard, Skill, Message/Part, Task/TaskStatus, JSON-RPC
   envelopes. No dependencies; unit-testable against sample A2A payloads.
2. **Module B — `a2a-clients`** — config + repo + connector + testAndDiscovery +
   `RemoteA2ANetworkAgentService` + supplier + controller. Lands remote agents in the network
   composer first — the higher-value half.
3. **Module A — `a2a-server`** — config + repo + card builder + registry + dispatcher + builder +
   task bridge + manager + controller.
4. **Wire into the build** — register both modules in `gebo.architecture.parent/pom.xml` and the
   monolith starter (and `brain.gebo.ai` if agents run there), following the mcp modules' dependency
   sets.
5. **Angular admin UI** — two admin screens mirroring the existing MCP client/server screens.
   Follow-on.

---

## Part 9 — Decisions (resolved 2026-08-28)

1. **A2A SDK vs. hand-rolled.** → **Official A2A Java SDK.** Exact artifact/version + Spring-MVC
   server-transport fit to be confirmed against Maven Central before coding (see Part 5). If the
   SDK's server transport does not fit Spring MVC cleanly, fall back to SDK *types* + our own Spring
   MVC handlers.
2. **Scope of the first cut.** → **Both directions**, secure by default: no agent and no network is
   externally visible or callable unless an admin has configured and enabled it (`enabled` defaults
   off on both `A2AServerConfig` and `A2ARemoteAgentConfig`; ACLs still gate callers).
3. **Durable tasks & resubscription.** → **SSE-first** (synchronous + `message/stream`). No persisted
   `A2ATaskState` in the first cut; `tasks/resubscribe` + push-notification webhooks are a follow-up.
4. **Module naming & placement.** → **`gebo.architecture.a2a-server` / `gebo.architecture.a2a-clients`**
   under `gebo.architecture.parent`, parallel to the mcp modules.

---

## Verification notes

- The A2A wire details (`/.well-known/agent-card.json`, JSON-RPC methods `message/send`,
  `message/stream`, `tasks/get`, `tasks/cancel`, the task states) are mapped from the A2A spec, not
  from this repo. Sanity-check against the exact A2A spec version being targeted; the well-known path
  moved from `agent.json` → `agent-card.json` at one point.
- All Gebo class and method names above reflect the code as read on the `develop` branch, 2026-08-28
  (post `1.0.3.0-SNAPSHOT` version bump).
