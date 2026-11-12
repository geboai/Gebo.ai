# Spring Boot 4.2 / Spring AI 2.1 update — completion plan

Branch: `feature/spring-platforms-upgrade`. Merge condition: the three GA releases
(Spring Boot 4.2.0, Spring AI 2.1.0, Spring Cloud 2026.0.0, due November 2026) bumped on
the branch and a green "Maven Compile & Test" run. Until then every adoption of the new
platforms is done here, not on side branches.

This document is the technical plan of what is left: for each point, where the code
stands, the code to write, the tests to write, how it is verified, and what can go
wrong. It is written against the code as of commit `8e038c902`.

## 0. Where the branch stands

| Commit | Content | CI |
|---|---|---|
| `1886b3f38` | Boot 4.2.0-RC2, Spring AI 2.1.0-RC2, Cloud 2026.0.0-RC2; obsolete pins dropped; `spring-boot-starter-rabbitmq` | green (run 43112013486, 2782 tests) |
| `6e0dbc8c4` | reasoning read as Spring AI 2.1 message parts (`IGReasoningExtractor.PARTS`), Anthropic thinking tap and generations reordering deleted | same run |
| `f01ba77a7` | decision model kind (abstraction layer, OpenAI, Ollama, admin UI, ranker filter consumer, data-flow register) | failed on javac 22 inference, fixed next |
| `8a9d11e83`, `b5b7197a6` | javac 22 cast; MCP Java SDK BOM 2.0.0 → 2.2.0-M2 | green (run 43160505090) |
| `6a28e6081`, `8e038c902` | DECISION in the usage enum stubs; decision models of the OpenAI compatible providers | green (runs 43160728615, 43163859331) |

What CI proves: the whole reactor compiles on JDK 22 and every unit and Testcontainers
suite passes. What CI does not prove: the application boots, a decision model answers
over the wire, the admin UI renders the new editors, the MCP server and clients work on
SDK 2.2.0-M2 at runtime.

## 1. Verify what is already written

### 1.1 Boot smoke test of the monolith

Nothing has booted since the Boot 4.2 and MCP SDK bumps. The autoconfiguration of
Boot 4.2 (the AMQP starter split, the Micrometer changes, the `RestTemplate`
deprecation) and the MCP SDK 2.2.0-M2 (binary mismatch risk on `McpSyncServer`,
`McpServerFeatures.Sync*Specification`, `McpJsonDefaults`, `McpTransportContext`) can
only fail at startup or at the first call.

Steps:
1. Bring the infra up: `dockers/docker-compose-deploy/docker-compose.yml`, services
   `mongo qdrant neo4j opensearch` (the stack was found exited; never recreate the data
   services, only start them).
2. Build the bootable jar from fresh `.m2` artifacts: full `mvn -o install -DskipTests`
   first (a `-pl` build ships stale jars), then `mvn -o -Pswagger-on package -DskipTests`
   in `gebo.apps.parent/gebo.ai.app`.
3. Run it on port 13999 (12999 is the docker monolith's) with `-DGEBO_HOME=<scratch home>`
   before `-jar`, every provider enabled; the real log is
   `<home>/logs/ai.gebo.monolithic.app.log`, not the stdout redirect.
4. Read the log for: `BeanCreationException`, `NoSuchMethodError`/`NoClassDefFoundError`
   (MCP SDK), `ConfigurationProperties` binding errors (`ai.gebo.llms.generic.config`
   now has `decisionModelProviders`), the three "Begin/End initializing ... models
   dinamically" pairs plus the new "decision models" one from
   `GDecisionModelRuntimeConfigurationDaoImpl`.
5. With the `/gebo-backend-test` procedure: login, `GET /v3/api-docs` and check the four
   new controllers are in the spec (`DecisionModelsController`,
   `OpenAIDecisionModelsConfigurationController`,
   `OllamaDecisionModelsConfigurationController`,
   `GenericOpenAIDecisionModelsConfigurationController`), then
   `GET /api/admin/DecisionModelsController/getDecisionModelTypes` must list
   `decision-OpenAI`, `decision-ollama` and `decision-generic-openai`.
6. MCP at runtime: with an MCP server configured in the admin, run one tools listing
   through `GeboMcpServerUserController` and one client call through the MCP clients
   browsing controller; both exercise the SDK classes that changed between 2.0.0 and
   2.2.0-M2.

Acceptance: no startup error, the four controllers in the spec, one MCP round trip.

### 1.2 Live check of the decision model path

The unit tests (`GRankerServiceImplTest`, 8 tests) prove the filter logic on a fake
`DecisionModel`; nothing has crossed the wire. Cheapest real target: Ollama.

Steps:
1. Ollama 0.40.4 or later, `ollama pull d1-omni:600m`. Check
   `ollama show d1-omni:600m` lists `decision` among the capabilities: that is what
   `OllamaModelsLookupService.getDecisionModels` filters on, a server not reporting
   capabilities lists nothing (by design, unlike chat and embeddings).
2. In the LLMs admin panel: "New decision model" → Ollama → base url → the model list
   must show `d1-omni:600m` → save. The save goes through
   `GAbstractClusteredModelRuntimeConfigurationDao.addRuntimeByConfigClustered`, which
   runs `GAbstractConfigurableDecisionModel.testCall` (one `YesNoQuestion`): a failing
   endpoint is refused here with the provider's message. Set it as default.
3. Ask a question in a chat with knowledge bases and
   `ai.gebo.ragsearch.config.rankerIrrelevanceFilterEnabled=true` (the default) and at
   least 5 ranked fragments (`rankerIrrelevanceFilterMinDocuments`). With
   `ai.gebo.llms.chat.abstraction.layer.services.impl.GRankerServiceImpl` at DEBUG the
   log must say "Irrelevance filter judges N of M ranked fragment(s) with the decision
   model code=...", at TRACE one line per fragment with its probability.
4. Check the usage: `LlmsUsageAdminLevelController` aggregation must show rows with
   model type `DECISION` (one call per judged fragment, the tokens the provider
   reports in `DecisionResponseMetadata.getUsage()`).
5. Check the compliance register: the data-flow of the standard chat pipeline must show
   the `decision-model-<code>` endpoint and the "relevance" transformation per profile
   (`GStandardChatPipelineDataFlowComponent`), the agents network one the
   "search-relevance" transformation (`GAgentsNetworkDataFlowComponent.reportRanking`).
6. Repeat 2 with the OpenAI provider if a key with Decisions API access is available
   (`OpenAIDecisionModelConfigurationSupportService` lists the models endpoint ids
   containing `luna`/`decision`, plus `gpt-6-luna`); and with the generic provider
   against any server exposing `/v1/decisions`.

Things to watch and decide after seeing them (not to pre-empt):
- latency: one call per fragment, sequential, in `discardedDocumentIndices(.., IGConfigurableDecisionModel)`.
  The chat-model path batched fragments by context budget. If the per-fragment round
  trip is too slow on a remote provider, the next step is a bounded parallel map
  (`Flux.fromIterable(...).flatMapSequential(..., concurrency)`) keeping the order, not
  a batch question (decision models answer one question set per input);
- the threshold: `YesNoAnswer.isYes()` is the Spring AI default 0.5; the TRACE lines
  give the probability distribution to judge whether the small model is too strict or
  too lenient on the test knowledge bases before any threshold becomes configurable;
- the question wording (`GRankerServiceImpl.RELEVANCE`): the Spring AI docs say the
  600m model leans heavily on the option descriptions; the `whenYes`/`whenNo` texts are
  the knobs.

Acceptance: a chat answer whose log shows the decision model judging, discarded
fragments consistent with their content, usage and data-flow rows present.

### 1.3 CI

Every commit of the branch after the javac 22 fix is green on "Maven Compile & Test".
The generic provider module has no unit tests of its own yet (see 3.5).

## 2. REST stubs regeneration

The decision stubs in `gebo.ui/projects/gebo-ai-rest-api/src/lib` (16 model files, 4
services, the `DECISION` enum values) are hand-written in the generator's shape. They
must be replaced by the generated ones so that the next regeneration does not diverge
from them silently.

Steps (the `/regen-angular-stubs` skill, in order):
1. Preconditions of 1.1 (infra, fresh `.m2`, bootable+swagger jar on 13999 with
   `-Dswagger.file` and every provider enabled, `basePath` restored afterwards).
2. Run the codegen (`gebo.ui` Maven profile `generate-rest-api`).
3. `git diff --stat gebo.ui/projects/gebo-ai-rest-api`: the hand-written files must come
   out byte-identical or with generator-only differences (comment headers, property
   order). Any other difference is a backend/stub mismatch to understand, not to patch
   by hand.
4. `npx ng build gebo-ai-rest-api && npx ng build gebo-ai-admin-ui`, then the full
   `ng build` of the app.
5. The brain microservice exposes the same controllers (`brain.gebo.ai` depends on
   `gebo.architecture.llms.abstraction.layer.controllers` and
   `gebo.llms.controllers.starter`): regenerate its Angular and Java clients under
   `gebo.api.clients/gebo.microservices.clients.parent` with the
   `/document-microservices-controllers` procedure (full stack, `clean package` of the
   images, specs read from each service), which also refreshes
   `docs/MICROSERVICES-CONTROLLERS.md`.
6. The monolithic Java client `gebo.api.clients/gebo.monolithic.api.resttemplate.client`
   is generated from the same spec: regenerate it in the same pass.

Acceptance: generated stubs committed, both UIs build, the hand-written files replaced.

## 3. LLM setup wizard support for the decision kind

Today the kind is configurable from the LLMs admin panel only. The fast-setup wizard
knows CHAT, EMBEDDING, RANKING, IMAGESGEN, TTS, TRANSCRIPT. Adding DECISION is mechanical
but touches every layer; it is optional for the users (like IMAGESGEN) and must stay
optional in the wizard (never required, never auto-created unless a preset says so).

### 3.1 Backend (`gebo.llms.parent/gebo.llms.setup`)

1. `config/ModelType`: add `DECISION`.
2. `model/ComponentLLMSStatus`: `decisionModelSetup`, `decisionModelCode`,
   `decisionModelProviderId`.
3. `model/LLMSSetupConfigurationData`: `decisionModelExists`.
4. `model/LLMAutoconfigureCreationData`: `decisionModel`.
5. `services/GeboLLMSSetupService`: inject `IGDecisionModelRuntimeConfigurationDao` and
   `IGDecisionModelConfigurationSupportServiceRepositoryPattern`; add the `decision`
   fields to `ModelKindPresence` and the loop in `computeModelKindPresence`; map them in
   `getLLMSSetupConfigurationData` and `getLLMSSetupStatus`; add the `case DECISION`
   to the five switches (the existing configurations listing at ~line 340, the
   credentials creation at ~570, the autoconfigure at ~730, the models lookup at ~890,
   the models creation at ~1118), each a copy of the `RANKING` case on the decision
   support service.
6. Presets in `src/main/resources/llms-fast-setup-library/library.yml`: a
   `- type: DECISION` block under the vendors that serve one. As of today only OpenAI
   (`serviceHandler: decision-OpenAI`, `doModelsLookup: false` because the models
   endpoint does not enumerate decision models reliably, choice `gpt-6-luna` as the
   Spring AI default, `defaultChoice: true`) and Ollama (`serviceHandler:
   decision-ollama`, `doModelsLookup: true`, choice `d1-omni:600m`). No OpenAI
   compatible vendor documents a Decisions API yet: no preset for them.
7. Tests: `GeboLLMSSetupService` has no unit test; add one for
   `computeModelKindPresence` with mocked DAOs (one decision model marked default →
   `decisionModelSetup=true`, code and provider id resolved through the library) and one
   for the autoconfigure switch creating a decision model from a preset through
   `configureModelValidating` (the model code present in the lookup → created, absent
   → unresolved with the choices, as the other kinds).

### 3.2 UI (`gebo.ui/projects/gebo-ai-admin-ui/src/lib/setup-wizard`)

1. `llms-easy-wizard/model-classes.ts`: `ModelClassId` gets `"DECISION"`, a class entry
   `{ id: "DECISION", type: "DECISION", required: false, fieldId: "ClassDecision",
   statusSetup: s => s?.decisionModelSetup, ... }` and its texts.
2. `llms-setup-components/easy-vendor-configuration.component.{ts,html}`: the
   `decisionModel` form control, the preset block (`preset.type==='DECISION'`) and the
   default choice resolution, copied from the `IMAGESGEN` block (optional kind).
3. `llms-setup-components/llms-vendor-modeltype.component.{ts,html}`: `decisionPresets`,
   `showDecision`, `lookedUpDecisionModels`, the expert-tab field.
4. Stubs: `lLMCreateModelData.ts`, `lLMCredentialsCreationData.ts`,
   `lLMExistingConfiguration.ts` enums get `DECISION` (hand-written until 2 runs again),
   `componentLLMSStatus.ts`, `lLMSSetupConfigurationData.ts`,
   `lLMAutoconfigureCreationData.ts` get the new fields.
5. i18n: the new ids through `/i18n-component-resources` (module `SetupWizardPanelModule`
   or whatever the two components declare; check `GEBO_AI_MODULE` on each).
6. Test: the wizard has no spec files; verify live: Setup → LLMs easy wizard → OpenAI
   and Ollama show the decision class as optional, creating one lands in the LLMs
   panel's "Configured decision models".

### 3.3 Microservices

`brain.gebo.ai` embeds `gebo.llms.setup`; the status DTO change propagates through the
brain clients regenerated in 2.5.

### 3.4 Acceptance

Wizard status shows the decision slot; autoconfigure with a decision preset creates the
model and marks it default; without a preset nothing changes for existing installations.

### 3.5 Missing unit tests of the new support services

Add before any live run, in the three vendor modules:
- `OpenAIDecisionModelConfigurationSupportServiceTest`: `isDecisionModel` cases,
  `getModelChoices` with a mocked `IGOpenAIApiUtil` (listed decision model kept, chat
  model dropped, `gpt-6-luna` appended once, lookup error → messages, missing secret →
  "api key" message).
- `OllamaModelsLookupServiceTest` (exists for chat? check): `getDecisionModels` keeps
  only models whose `api/show` capabilities contain `decision`, a server not reporting
  capabilities lists none.
- `GenericOpenAIAPIDecisionModelConfigurationSupportServiceTest`: base url precedence
  (config over provider), anonymous allowed only when `optionalAuthentication`, the
  declared `defaultModel` always offered, models list provider path vs models endpoint.
- `GAbstractConfigurableDecisionModelTest`: `testCall` wraps a provider
  `RuntimeException` into `LLMConfigException` with the model code.

## 4. Spring AI 2.1 tool-call confirmation in the harness

### 4.1 What Spring AI 2.1 offers

- `@Tool(requiresConfirmation = true)` or a `ToolCallConfirmationPolicy` on
  `DefaultToolCallingManager.builder().toolCallConfirmationPolicy(...)`:
  `fromToolMetadata()` (default), `forTools(names...)`, composable with `or`.
- `DefaultToolCallingManager.executeToolCalls(prompt, response)` throws
  `ToolCallConfirmationRequiredException(toolCallIds)` before any call of a batch runs
  when one needs confirmation; `ToolCallingAdvisor` catches it and ends the loop
  returning the model's reply with the metadata
  `ToolCallDecisions.AWAITING_DECISION_METADATA_KEY` (the keys of the calls waiting).
- The caller resumes with `executeToolCalls(prompt, response, ToolCallDecisions)`
  (`ToolCallDecision.allow/deny(id, message)`); a denied call does not run and the
  model receives the deny message as the tool result.

### 4.2 What Gebo has

- `GeboToolCallingManager` wraps the default manager (final in Spring AI) and already
  documents the 2.1 decisions overload: it delegates it (interface default) and must read
  the ending controls on its result the same way as the two-argument one.
- The harness suspends on a person through tools: `GAbstractAskUserChoiceQuestionTool`
  declares an `AgentSuspendedWaitingCondition` to the loaded `IGWaitExternalCondition`,
  the condition ends the call (an `IToolCallEndingControl`), the network persists a
  `GNetworkOfAgentsSuspension` (`GMongoNetworkOfAgentsSuspensionHandler`) and respawns
  from it with the resolved conditions (`IGNetworkOfAgentsSuspensionHandler.respawnFrom`).
  The waiting conditions reach the users through `WaitingConditionsController` and the
  chat UI.

### 4.3 Design

Confirmation is a waiting condition of a new type: "the model wants to run tool X with
arguments Y, allow or deny". The person's answer resumes the batch through the
decisions overload. The pieces:

1. `IGToolCallbackSourceRepositoryPattern.createToolCallingManager()`
   (`GToolCallbackRepositoryPatternImpl`): build the default manager with a
   `ToolCallConfirmationPolicy` = `fromToolMetadata()` OR a Gebo policy reading the tool
   source's declaration. Gebo tools are `ToolCallback`s produced by the sources (`IGToolCallbackSource`);
   add a `requiresConfirmation` flag to the Gebo tool declaration model (the admin
   descriptions in `ToolAdminDescriptionsTest` show where tools are described) so an
   admin can mark a tool of any source, including MCP tools, as needing a person.
2. `GeboToolCallingManager`: override `executeToolCalls(prompt, response, decisions)`
   to delegate and apply `endedByAControl`; let `ToolCallConfirmationRequiredException`
   pass through.
3. The agentic loop (`AgenticLoopReactiveAgentServiceImpl`, and every network agent
   calling a model with tools through `GeboToolCalling`): where the call result is read,
   detect `ToolCallDecisions.isAwaitingDecision(chatResponse)`; for each key build an
   `AgentSuspendedWaitingCondition<ToolCallDecisionAnswer, ToolCallDescription>` (new
   small types: the call id, the tool name, the arguments JSON, the chosen decision)
   with `humanInTheLoopProcessTreatment=true`, and enter the suspension state as the
   ask-user tools do. The prompt and the paused `ChatResponse` must be persisted with
   the suspension (`GNetworkOfAgentsSuspension` carries the agents' private contexts:
   add the pending prompt messages and the reply there, serialized through the Spring AI
   message JSON, since the resume needs them verbatim).
4. On respawn: with the answers, build `ToolCallDecisions` and call
   `toolCallingManager.executeToolCalls(prompt, pausedResponse, decisions)`; feed the
   `ToolExecutionResult.conversationHistory()` back into the loop as the next prompt, as
   `ToolCallingAdvisor` does internally (read `ToolCallingAdvisor.adviseCall` in the
   2.1 sources for the exact continuation: the history replaces the prompt messages and
   the loop calls the model again unless `returnDirect`).
5. Streaming: the paused reply is the last chunk of the stream with the metadata; the
   chat UI must show "waiting for your confirmation" instead of an answer. The chat
   envelope needs a new content type (`GeboChatMessageEnvelope<ToolCallConfirmationRequest>`)
   the UI renders as the existing waiting-condition card.
6. UI: the waiting conditions panel gets the tool call type: tool name, arguments
   (pretty JSON), allow/deny buttons, optional message to the model on deny.

### 4.4 Tests

- `GeboToolCallingManagerTest` (exists): the decisions overload applies the ending
  controls; the exception passes through.
- A policy test: a tool marked by the admin is required, an unmarked one is not, an MCP
  tool marked is required.
- `ChatNetworkStepSuspensionTest` / `ToolCallingAgentSuspensionTest` (exist): a model
  reply awaiting a decision suspends the network with one condition per call; respawn
  with allow runs the tool and continues; with deny the model receives the deny message
  (assert on the prompt of the next model call).
- A streaming test on `AbstractChatService.composeFlux`: the paused reply produces the
  confirmation envelope and the FINAL envelope, no empty answer.
- Live: an agentic chat with one tool marked as needing confirmation, deny then allow.

### 4.5 Risks

The 2.1 continuation of a paused batch is new code in Spring AI (RC); read
`ToolCallingAdvisor` lines ~216 and ~419 (the two catch blocks) and ~523 (streaming
pause) before relying on its metadata contract, and pin the behaviour with the tests
above so a change at GA shows up.

## 5. Streamed tool-call argument parts in the chat UI

### 5.1 What Spring AI 2.1 offers

Anthropic, OpenAI (Chat Completions and Responses), Bedrock Converse and DeepSeek stream
each tool-call argument delta as a partial `ToolCallPart` (`StreamingParts.partial`);
the complete call arrives once as before. Partial parts are not tool calls
(`getToolCalls()` skips them), `MessageAggregator` skips them, `ChatClient` passes the
chunks through, `.stream().content()` is unaffected.

### 5.2 What Gebo has

`GChatAnswerChunk.of(ChatResponse)` reads text, media, finish reason and the thinking
metadata the `ThinkingNormalizationAdvisor` sets; `AbstractChatService.composeFlux`
turns chunks into `GeboChatMessageEnvelope<String>` (answer) and the thinking events
(`ThinkingStream`), the chat UI renders them.

### 5.3 Code

1. `GChatAnswerChunk`: a `toolCallsInProgress` list (tool name, call id or index,
   arguments so far) read from the partial `ToolCallPart`s of each generation's message
   (`AssistantMessage.getParts()` filtered on `ToolCallPart` whose `StreamingParts`
   index marks them partial; see `OpenAiChatModel.toolCallDelta` for how the index is
   carried) and the complete parts (the call that is about to run).
2. `AbstractChatService.composeFlux`: a new envelope content type
   `ToolCallProgress { name, argumentsSoFar, complete }` emitted per chunk that carries
   a delta; one `complete=true` when the complete part arrives. Keep the answer
   envelopes untouched.
3. Chat UI (`gebo-ai-chat-ui`): render the progress like the thinking block, a collapsed
   "calling <tool>…" line that fills with the arguments, closed when complete; i18n
   keys through the skill.
4. The agents networks (`AgenticLoopReactiveAgentServiceImpl`,
   `ReportWriterReactiveAgentServiceImpl`) consume `GChatAnswerChunk` too: they must
   ignore the progress (they read the answer text) — verify they do not re-read parts.

### 5.4 Tests

- `GChatAnswerChunkTest` (new): a chunk with two partial `ToolCallPart`s at index 0 and a
  text part → progress carries the concatenated arguments and the text; a complete part
  → `complete=true`; a chunk with none → empty list.
- `CutAnswerStreamingTest`/`ThinkingStreamTest` siblings: `composeFlux` emits the
  progress envelopes in order with the answer ones and never an empty answer envelope
  for a delta-only chunk.
- Live: an agentic chat on an OpenAI or Anthropic model with a tool that takes a long
  argument (a search query): the UI shows the arguments filling.

Note: only the providers above stream deltas; Ollama, Mistral and Google GenAI send the
call whole, the UI must cope with `complete` arriving without any delta.

## 6. Spring AI's native OpenAI Responses chat model

### 6.1 Current state

`gebo.llms.parent/gebo.llms.openai/.../services/OpenAiResponsesChatModel.java` (601
lines, 5 tests in `OpenAiResponsesChatModelTest`): Gebo's own `ChatModel` over
`/v1/responses` through the `openai-java` SDK, used by
`OpenAIChatModelConfigurationSupportService.configureModel` for the models
`OpenAiResponsesChatModel.usesResponsesApi(model)` (version 6 and later). Stateless, no
tool loop, maps the conversation to items, carries tool calls, reasoning (as the legacy
`reasoningContent` metadata `IGReasoningExtractor.PARTS` falls back to) and usage.

Spring AI 2.1 ships `org.springframework.ai.openai.responses.OpenAiResponsesChatModel`
with the same design (stateless, no loop, `ToolCallingAdvisor` owns the rounds) and
`OpenAiResponsesChatOptions` (model, reasoningEffort, reasoningSummary, verbosity,
maxOutputTokens, temperature/topP through the common options, responseFormat,
toolChoice, parallelToolCalls, maxToolCalls, hostedTools, include, truncation,
serviceTier, promptCacheKey, safetyIdentifier, metadata, extraBody, outputSchema).
Known limits: no audio, `n`, stop sequences, penalties, seed, logit_bias.

### 6.2 Steps

1. Feature comparison on the real path, before any code: list what
   `OpenAIChatModelConfigurationSupportService.configureModel` sets on
   `OpenAiChatOptions` (model, temperature, topP, tools + `parallelToolCalls`,
   `maxCompletionTokens`, `reasoningEffort`, user, timeout, api key) and map each to
   `OpenAiResponsesChatOptions`; list what Gebo's class does that Spring AI's does not
   (read its javadoc and the five tests: the items mapping, the cut answer saying
   `length`, the reasoning going back on the next turn, the usage).
2. Build Spring AI's model in `configureModel` behind the same `usesResponsesApi`
   branch: `OpenAiResponsesChatModel.builder().openAiClient(OpenAiSetup.setupSyncClient(...))
   .options(...).toolCallingManager(toolCallingManager).observationRegistry(...)
   .httpClientBuilderCustomizer(OpenAiClientCustomizer.from(...)).build()`.
   (`toolCallingManager` on the Chat Completions builder is deprecated since 2.0; check
   whether the Responses builder's one is, and if so where the manager goes: the
   `ToolCallingAdvisor` of the ChatClient, which `GAbstractConfigurableChatModel` builds.)
3. Reasoning: Spring AI's model emits `ReasoningPart`s, read by `PARTS`; Gebo's emits
   the legacy metadata. The `ThinkingNormalizationAdvisor` tests
   (`ThinkingNormalizationAdvisorTest`, `InlineThinkingStreamingTest`) run on fakes: add a
   case with parts from a Responses-shaped stream (a `ReasoningPart` per summary delta,
   then text) to prove the thinking reaches the UI before the answer once.
4. Reasoning continuity across turns: Spring AI notes that no chat memory persists
   message parts yet; Gebo rebuilds the prompt from its own session
   (`GUserChatSession`), so the encrypted reasoning items of the previous turn are not
   sent back. Gebo's class sends them back ("its reasoning goes back" test): confirm
   with a two-turn tool-calling conversation on gpt-5 or later that Spring AI's model
   does not fail without them (OpenAI requires the reasoning items only inside one
   tool-calling turn, which the advisor loop keeps in memory).
5. Switch with a live run on the same prompts as `OpenAiResponsesChatModelTest`
   (`gebo.llms.tests.services` holds the live test harness) against a gpt-5 or later
   model: a blocking call, a streamed answer with thinking, a tool-calling round, a cut
   answer (`maxGeneratedTokens` small) reporting `length`, usage recorded.
6. Only when 1–5 are green: delete Gebo's class and its test, keep
   `usesResponsesApi` as the routing predicate.

Keep Gebo's class otherwise; it is tested and in production shape.

## 7. Session memory and the ChatMemory deprecation

Spring AI 2.1 deprecates `ChatMemory`, `MessageWindowChatMemory`,
`MessageChatMemoryAdvisor` and the repositories for removal in 3.0, replaced by
Session memory (`spring-ai-session`, `spring-ai-session-advisor`: `SessionService`,
`SessionRepository`, `SessionMemoryAdvisor`/`SessionTurnAdvisor`, `EventFilter`, turn-aware
compaction with triggers such as `TurnCountTrigger` and strategies such as
`WindowCompactionStrategy` or `ChatModelSummarizer`).

Gebo uses none of the deprecated classes (verified at the upgrade: no `ChatMemory`
import in the reactor). Its conversation state is its own: `GUserChatSession`, the
session shrink (`SessionShrinkIdentityTest`), the agents' private contexts and the
network suspensions. So nothing breaks at 3.0.

What a spike would decide:
1. whether the agentic loop's context management (the shrink, the cut answers, the
   earlier-documents handling) would be simpler on a `SessionService` with
   `WindowCompactionStrategy`, keeping Mongo as the store: there is no Mongo
   `SessionRepository` in 2.1 (planned before 3.0); Gebo would write one over
   `IGPersistentObjectManager`;
2. whether the turn-aware window solves a real problem today (a tool call seen without
   its result after a shrink): grep the shrink for how it cuts, write the failing test if
   it can cut inside a turn, fix it locally first.

Recommendation: no adoption on this branch; a design note after the spike. The only
code worth writing now is the test of point 2.

## 8. Deprecations flagged by the build

Counted from the compile logs of the branch (occurrences, not call sites):

| Deprecated for removal | Where | Count | Replacement |
|---|---|---|---|
| `org.springframework.web.client.RestTemplate` (+ `RequestCallback`, `ResponseExtractor`) | `gebo.integrations.clients/gebo.atlassian.jira.cloud.client` (17), `gebo.api.clients/gebo.monolithic.api.resttemplate.client` (17, generated), `gebo.architecture.integration.tests` (16), `gebo.integrations.clients/gebo.openrouter.ai.client` (12), `gebo.architecture.parent/gebo.restintegration.abstraction.layer` (12+20), `gebo.webconfig` (7), `gebo.systems.parent/gebo.googlesearch.handler` (6), `gebo.atlassian.jira.handler` (6), `gebo.openai.integration.client` (4) | ~117 | `RestClient` (Framework 7.1 deprecates `RestTemplate` for removal) |
| `MappingJackson2HttpMessageConverter` | `gebo.architecture.integration.tests` | 6 | `JacksonJsonHttpMessageConverter` (Jackson 3) |
| `HttpEntity(MultiValueMap)` | `gebo.llms.deepseek` | 2 | `HttpEntity(T, MultiValueMap)` / `HttpHeaders` |
| Spring AI `JsonParser` (`toJson`, `fromJson`) | `gebo.architecture.agents.standard` (26), `gebo.architecture.llms.abstraction.layer` (12), `gebo.llms.standard.functions` (8 incl. tests) | ~46 | `org.springframework.ai.util.json.JacksonUtils` / Gebo's own `JsonHelper` |
| `XxxChatModel.Builder.toolCallingManager(...)` | `gebo.llms.openai`, `gebo.llms.mistral`, `gebo.llms.generic-openai-compatible`, `gebo.llms.aws-bedrock`, `gebo.llms.anthropic3` | 10 | the manager belongs to the `ToolCallingAdvisor` of the ChatClient since 2.0 (`GAbstractConfigurableChatModel` builds the client) |
| `ChatClient...toolCallbacks(...)` | tests in `gebo.architecture.llms.abstraction.layer`, `gebo.llms.tests.services` | 18 | `.tools(...)` / `ToolCallbackProvider` on the advisor |
| `ChatModel.getDefaultOptions()` | `gebo.architecture.llms.abstraction.layer` (`AnswerFirstGenerations.Model`, the usage wrappers) | 8 | `getOptions()`; drop the override when the interface loses it |
| `new Integer(int)`, `new Long(long)` | `gebo.llms.generic-openai-compatible`, `gebo.architecture.graphrag.persistence`, `gebo.architecture.compute.workflow` | 8 | `Integer.valueOf` |
| `SpringOpaqueTokenIntrospector(uri, clientId, secret)` and the reactive one | `gebo.architecture.security` (`SingleOauth2ConfigOpaqueTokenAuthenticationManager`, `GReactiveHttpRequestAuthenticationManagerResolverImpl`), `gebo.architecture.security.controllers` (`SecurityHeaderDataCompletionController`) | 3 | see section 8b |

Steps, in this order (lowest risk first):
1. `JsonParser` → `JacksonUtils`/`JsonHelper`: mechanical, covered by the existing
   tests of the agents standard module (the tool argument parsing tests) and
   `ReadUrlRoomTest`.
2. `toolCallingManager` on the model builders and `toolCallbacks` in tests: remove the
   builder call (the ChatClient advisor already has the manager: verify in
   `GAbstractConfigurableChatModel` where `ToolCallingAdvisor` is built with the
   `GeboToolCallingManager`), move the tests to `.tools(...)`; run
   `GeboToolCallingTest`, `GeboToolCallingManagerTest`, `ThinkingNormalizationAdvisorTest`.
3. `getDefaultOptions`: delete the override in `AnswerFirstGenerations.Model` and the
   usage wrappers once the interface default is gone (at GA; until then the override is
   harmless).
4. `MappingJackson2HttpMessageConverter`, `HttpEntity`, boxed constructors: mechanical.
5. `RestTemplate` → `RestClient`: not mechanical. `gebo.restintegration.abstraction.layer`
   (`RestTemplateWrapperService`, used by `OllamaModelsLookupService` and the clients)
   is the seam: give it a `RestClient`-backed implementation with the same signatures
   (`getForEntity`, `postForEntity`, `toMessage`), keep the interceptors and the retry
   (`IGLlmsServiceClientsProvider.getRestClientBuilder()` already exists), switch the
   callers module by module, the generated `gebo.monolithic.api.resttemplate.client`
   last (regenerate it with a `RestClient`/`WebClient` codegen library or keep it until
   Framework removes the class). Tests: the integration tests module exercises the
   wrapper against the live services; `OllamaModelsLookupService` and the Jira/Google
   handlers have fakes to add for the error mapping (`toMessage`).

## 8b. Spring Security 7.2 (Boot 4.2.0-RC2 manages 7.2.0-RC1)

What the upgrade changed for the security modules (`gebo.architecture.security`,
`.security.controllers`, `.security.apikey`, `.security.sessions`,
`gebo.security.directory.mongo`, `gebo.webconfig`): the whole configuration DSL
(`GeboAISecurityConfig`, `GeboSecurityConfig`, the JWT decoder caches, the dynamic
client registration repositories, the OAuth2 login policy) compiles on 7.2.0-RC1
without a warning; the only deprecated-for-removal API left is the three-argument
constructor of the opaque token introspectors, in three places:

| Site | Today | Replacement |
|---|---|---|
| `SingleOauth2ConfigOpaqueTokenAuthenticationManager:39` (servlet, the resource-server manager of one SSO configuration) | `new SpringOpaqueTokenIntrospector(introspectionUri, clientId, secret)` | `RestClientOpaqueTokenIntrospector.withIntrospectionUri(uri).clientId(..).clientSecret(..).build()`: `SpringOpaqueTokenIntrospector` rests on `RestTemplate`, deprecated for removal by Framework 7.1, so its builder (`SpringOpaqueTokenIntrospector.withIntrospectionUri`) is only a stop-gap |
| `SecurityHeaderDataCompletionController:175` (introspects a raw token to say which SSO configuration it belongs to) | same constructor | same; build one introspector per configuration once and cache it next to `JwtDecoderCache`, instead of one per request |
| `GReactiveHttpRequestAuthenticationManagerResolverImpl:155` (the reactive manager used by the microservices) | `new SpringReactiveOpaqueTokenIntrospector(uri, clientId, secret)` | `SpringReactiveOpaqueTokenIntrospector.withIntrospectionUri(uri).clientId(..).clientSecret(..).build()` (WebClient based, not deprecated) |

Behaviour change to verify (7.2.0-RC1, gh-19850): both introspectors now send
`Content-Type: application/x-www-form-urlencoded` on the introspection request, as RFC
7662 requires. The providers Gebo introspects against are the `oauth2_generic` ones
with an `introspectionUri` (`ai.gebo.security.oauth2configs[].providerConfig`,
KeyCloak and the generic OIDC servers): a provider that accepted the previous request
keeps working, one that rejected a wrong content type starts working. Nothing to
change, one live login per configured provider to confirm.

Not affected: no `SecurityJackson2Modules` (the Jackson 3 migration of 7.0 does not
apply), no one-time-token login (7.2 made its account status checks opt-in), no
Content-Security-Policy configured (7.2 adds nonce-based CSP; a hardening item of its
own, see `GEBO-HARDENING-MANUAL.md`, not an upgrade item).

Tests to write, in `gebo.architecture.security/src/test/.../authmanagers`:
- `OpaqueTokenIntrospectionTest`: a local `HttpServer` introspection endpoint
  recording the request; assert the `Content-Type`, the basic auth of the client, the
  `token=` form body, and the principal mapped from `{"active":true,"sub":..}` through
  the `GReactiveOpaqueTokenAuthenticationConverter` / the servlet converter; an
  `{"active":false}` answer rejected; run for both the servlet manager and the reactive
  one.
- `SecurityHeaderDataCompletionControllerTest`: two configurations, the token accepted
  by the second one only → `XAuthType.OAUTH2` with its registration id; the introspector
  cache reused across calls.

Boot 4.2's own security auto-configuration notes are not published yet ("full release
notes when 4.2 is released"): re-read them at the GA bump (section 10) for the
management endpoints security, the OAuth2 client properties and the CORS defaults.
Spring Security's "preparing for 8.0" guide is empty as of 7.2.0-RC1; the deprecations
above are the whole list for this code base.

## 9. Documentation

1. `docs/APPLICATION-YML-ADMIN-MANUAL.md`: section 8 (LLM providers) gets a paragraph on
   the decision model kind (what it is, which providers, the `decision-generic-openai`
   entry and `decisionModelProviders` of `providers.yml`); section 18 gets the ranker
   filter keys, none of which is documented today: `ai.gebo.ragsearch.config.`
   `rankerIrrelevanceFilterEnabled`, `rankerIrrelevanceFilterContextFraction`,
   `rankerIrrelevanceFilterMaxFragmentsPerBatch`, `rankerIrrelevanceFilterMinDocuments`,
   `rankerIrrelevanceFilterWarnRate`, `rankerIrrelevanceFilterProtectedTopFragments`,
   `rankerIrrelevanceFilterUsesDecisionModel`, `hybridSearchEnabled`,
   `hybridFullTextShare`, with the defaults of `GeboRagSearchConfig`.
2. `docs/LLM-ANSWER-THINKING-STREAMING-DESIGN.md`: a section on Spring AI 2.1 message
   parts (`IGReasoningExtractor.PARTS`, the legacy fallback, the Anthropic tap removal).
3. The user/admin manual (`/document-gebo-ai-ui-manual`): the "New decision model"
   option of the LLMs panel and the three editors, with screenshots from a live
   instance (after 1.2).
4. `docs/MICROSERVICES-CONTROLLERS.md`: regenerated in 2.5.
5. The pin comments in the root `pom.xml` are the record of why each version is
   pinned; keep them current at the GA bump.

## 10. GA bump and merge

1. When Spring Boot 4.2.0, Spring AI 2.1.0 and Spring Cloud 2026.0.0 are on Maven
   Central: `spring-boot-starter-parent` in the root and
   `microservices-integration-tests/pom.xml`, `spring.ai.version` in the root,
   `spring-cloud.version` in the 16 poms (`gebo.apps.parent/gebo.microservices.apps.parent`
   and the 15 under `gebo.microservices.architecture.parent`).
2. Re-check the pins against the GA BOMs: `derby.version` (drop if Boot manages
   10.17.1.0+), `spring-retry.version` (drop if Boot manages it), the MCP `mcp-bom` (set
   to what `spring-ai-mcp` 2.1.0 declares, or drop if the Spring AI BOM manages it),
   springdoc (move to the release that claims Boot 4.2).
3. Full `mvn clean install` of OSS, then the downstream editions with clean builds
   (`-pl` builds ship stale jars); fix what the GA changed from the RC (the upgrade
   notes list the breaking changes between RC2 and GA).
4. CI green, the boot smoke test (1.1) and the live decision check (1.2) repeated on the
   GA jars.
5. Merge into `develop` with a merge commit; the branch history stays.

## 11. Suggested order

1. 1.1 boot smoke, 1.2 live decision check, 3.5 unit tests of the support services:
   they validate what is written and are cheap.
2. 2 stubs regeneration (needs the running app of 1.1).
3. 9.1 and 9.2 documentation.
4. 8.1–8.4 deprecations and 8b (the opaque token introspectors): mechanical, keeps
   the warning noise down for the rest.
5. 3 setup wizard.
6. 6 native Responses model (live tests on a gpt-5 or later model needed).
7. 4 tool-call confirmation, then 5 streamed tool parts (both need the chat UI and
   the harness, and 5 is simpler once 4's envelope type exists).
8. 8.5 `RestTemplate` migration.
9. 7 session memory spike, design note only.
10. 10 at the GAs.
