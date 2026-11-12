# Spring Boot 4.2 and Spring AI 2.1 update: how to complete it

**Branch:** `feature/spring-platforms-upgrade`
**Written against:** commit `7d2feef9e`, 12 November 2026
**Audience:** whoever picks up the branch, with or without having followed the upgrade so far

## 1. In short

Gebo.ai has been moved to the release candidates of Spring Boot 4.2, Spring AI 2.1 and
Spring Cloud 2026.0. The branch compiles, its 2800 tests pass on the CI, and two
features of the new Spring AI have been adopted: the reasoning of every model is read
in the new uniform way (message parts), and a new kind of model, the **decision
model**, has been added to the product with three providers and its admin screens.

The branch is **not to be merged** until the three final (GA) releases ship, expected
in November 2026. Everything that concerns the new platforms is done on this branch;
no side branches.

This document lists what is still to do, in enough detail to start working without
re-discovering the code. Each item says why it matters, where the code stands, what to
build, how to test it and when it is done. The order of execution is suggested at the
end (section 14).

### Status at a glance

| Item | State | Effort |
|---|---|---|
| Version bumps, obsolete pins, RabbitMQ starter rename | done, CI green | — |
| Reasoning read as message parts, Anthropic special-casing removed | done, CI green | — |
| Decision model kind: abstraction, OpenAI, Ollama, generic OpenAI-compatible, admin UI, usage, compliance register | done, CI green, **not run live yet** | — |
| MCP Java SDK aligned with Spring AI 2.1 (2.0.0 → 2.2.0-M2) | done, CI green, **not run live yet** | — |
| 2. Boot smoke test of the application | to do | small |
| 3. Live check of a decision model | to do | small |
| 4. Unit tests of the decision model providers | to do | small |
| 5. REST stubs regenerated from the live API | to do | small, needs 2 |
| 6. Setup wizard support of the decision kind | to do | medium |
| 7. Tool-call confirmation (human in the loop) | to design and build | large |
| 8. Streamed tool-call arguments in the chat | to build | medium, after 7 |
| 9. Spring AI's own OpenAI Responses model | to evaluate, then maybe switch | medium |
| 10. Session memory | spike only | small |
| 11. Deprecated APIs cleanup | to do | medium (large for `RestTemplate`) |
| 12. Spring Security 7.2 | small change, one check | small |
| 13. Documentation | to do | small |
| 14. GA bump and merge | when the releases ship | small |

## 2. The new concepts, in plain words

A few terms used throughout. Readers who know Spring AI 2.1 can skip this.

- **Message parts.** Spring AI 2.1 represents what a model answers as an ordered list
  of parts: text, reasoning (the model's "thinking"), tool calls, media. Before 2.1
  every provider exposed its reasoning differently (Anthropic as separate generations,
  OpenAI-compatible APIs as a metadata field). Gebo now reads the reasoning from the
  parts for every provider, through `IGReasoningExtractor.PARTS`, and keeps the old
  metadata reading as a fallback for its own OpenAI Responses model.
- **Decision model.** A small, fast model that does not write text: it answers yes/no,
  multiple-choice and score questions about an input, with probabilities. Spring AI 2.1
  calls it `DecisionModel`. OpenAI serves one through its Decisions API; Ollama serves
  the `d1-omni` family through a "System One" endpoint. Gebo uses it to judge whether a
  retrieved fragment is relevant to the user's question, a job the chat model used to
  do with a prompt.
- **The ranker irrelevance filter.** After the reranker orders the retrieved fragments,
  a second stage drops the ones useless for the question. With a decision model
  configured as default, that stage asks the decision model one question per fragment;
  without one, it asks the internal services chat model as before.
- **Tool-call confirmation.** Spring AI 2.1 lets a tool be marked as needing a person's
  approval before it runs. The model's reply then pauses, the person allows or denies,
  and the tool loop resumes.
- **The harness.** Gebo's networks of agents, which already know how to pause for a
  person (a "waiting condition" persisted in Mongo) and resume later.
- **REST stubs.** The Angular and Java clients generated from the backend's OpenAPI
  description. The ones for the decision models were written by hand in the generator's
  shape and must be regenerated.
- **Opaque token introspection.** When a single sign-on provider issues tokens that are
  not JWTs, the backend asks the provider whether a token is valid ("introspects" it).
  Spring Security 7.2 deprecated the way Gebo creates that client.
- **Data-flow register.** Gebo's compliance feature that lists where data goes (models,
  stores, external services). Every model kind reports itself there; the decision model
  does too.

## 3. Where the branch stands, commit by commit

| Commit | What it did | CI run |
|---|---|---|
| `1886b3f38` | Boot 4.2.0-RC2, Spring AI 2.1.0-RC2, Cloud 2026.0.0-RC2. Dropped the pins Boot 4.2 made obsolete (mongodb, hazelcast, opentelemetry, rabbit client). The RabbitMQ module moved to `spring-boot-starter-rabbitmq`, the only compile break of Boot 4.2. | 43112013486, green, 2782 tests |
| `6e0dbc8c4` | Reasoning read as message parts. The Anthropic HTTP "thinking tap", the thinking-generations reordering and the per-vendor reasoning readings were deleted. | same run |
| `f01ba77a7` | The decision model kind: abstraction layer, OpenAI and Ollama providers, the ranker filter using it, the compliance register, the admin UI, hand-written stubs. | failed: javac 22 refused a type inference javac 27 accepted |
| `8a9d11e83`, `b5b7197a6` | The javac 22 fix. The MCP Java SDK BOM raised from 2.0.0 to 2.2.0-M2, the version Spring AI 2.1 is built against (the old pin would have failed at the first MCP call, not at compile time). | 43160505090, green |
| `6a28e6081`, `8e038c902` | DECISION in the usage enums of the stubs. Decision models of the OpenAI-compatible providers. | 43160728615 and 43163859331, green |
| `35e1b7190`, `7d2feef9e` | This document. | — |

What the CI proves: everything compiles on JDK 22 and every unit and Testcontainers
suite passes. What it does not prove: that the application starts, that a decision
model answers over the network, that the new admin screens render, that MCP works on
the new SDK at runtime. Items 2 and 3 close those gaps.

---

## 4. Item 2: boot smoke test of the application

### Why

Nothing has started since the Boot 4.2 and MCP SDK changes. Boot's auto-configuration
(the AMQP starter split, the Micrometer changes) and the MCP SDK jump (2.0.0 to
2.2.0-M2, a binary API change) can only fail at startup or at the first call, which no
unit test reaches.

### Steps

1. Start the infrastructure: `dockers/docker-compose-deploy/docker-compose.yml`,
   services `mongo qdrant neo4j opensearch`. The stack was last seen stopped; start it,
   never recreate the data services.
2. Build the jar from fresh artifacts. A partial build (`-pl`) ships stale jars from
   the local repository, so run the full `mvn -o install -DskipTests` first, then
   `mvn -o -Pswagger-on package -DskipTests` in `gebo.apps.parent/gebo.ai.app`.
3. Run it on port 13999 (12999 belongs to the docker monolith), with
   `-DGEBO_HOME=<scratch home>` placed before `-jar`, every provider enabled. The real
   log is `<home>/logs/ai.gebo.monolithic.app.log`; the console redirect is not it.
4. Read the log for:
   - `BeanCreationException` (wiring),
   - `NoSuchMethodError` or `NoClassDefFoundError` (the MCP SDK),
   - configuration binding errors (`ai.gebo.llms.generic.config` now has a
     `decisionModelProviders` list),
   - the "Begin/End initializing ... models dinamically" pairs, now four of them: the
     decision models one comes from `GDecisionModelRuntimeConfigurationDaoImpl`.
5. With the backend test procedure (`.claude/skills/gebo-backend-test`): log in, read
   `/v3/api-docs` and check the four new controllers are there
   (`DecisionModelsController`, `OpenAIDecisionModelsConfigurationController`,
   `OllamaDecisionModelsConfigurationController`,
   `GenericOpenAIDecisionModelsConfigurationController`). Then call
   `GET /api/admin/DecisionModelsController/getDecisionModelTypes`: it must list
   `decision-OpenAI`, `decision-ollama` and `decision-generic-openai`.
6. MCP at runtime: with an MCP server configured in the admin, list its tools through
   the MCP server user controller and make one client call through the MCP clients
   browsing controller. Both go through the SDK classes that changed
   (`McpSyncServer`, `McpServerFeatures.Sync*Specification`, `McpJsonDefaults`,
   `McpTransportContext`).

### Done when

The application starts without an error, the four controllers are in the API
description, one MCP round trip works.

## 5. Item 3: live check of a decision model

### Why

The eight tests of `GRankerServiceImplTest` prove the filter logic on a fake decision
model. No request has reached a real one yet. The cheapest real target is Ollama.

### Steps

1. Install Ollama 0.40.4 or later and pull the model: `ollama pull d1-omni:600m`.
   Check that `ollama show d1-omni:600m` lists `decision` among the capabilities. The
   Ollama lookup (`OllamaModelsLookupService.getDecisionModels`) offers only models with
   that capability; a server too old to report capabilities lists nothing, on purpose
   (the chat and embedding lookups fall back to the model names, this one does not).
2. In the LLMs admin panel: "New decision model", provider Ollama, the server url; the
   model list must show `d1-omni:600m`; save. Saving runs a test call (one yes/no
   question, `GAbstractConfigurableDecisionModel.testCall`): an unreachable or wrong
   endpoint is refused right there with the provider's message. Mark it as default.
3. Ask a question in a chat that uses knowledge bases. The filter runs when it is
   enabled (`ai.gebo.ragsearch.config.rankerIrrelevanceFilterEnabled`, default true)
   and at least five fragments were ranked (`rankerIrrelevanceFilterMinDocuments`).
   With the logger `ai.gebo.llms.chat.abstraction.layer.services.impl.GRankerServiceImpl`
   at DEBUG, the log must say "Irrelevance filter judges N of M ranked fragment(s) with
   the decision model code=..."; at TRACE it prints one line per fragment with the
   probability and the verdict.
4. Check the usage accounting: the admin usage view must show rows of model type
   `DECISION`, one call per judged fragment, with the tokens the provider reports.
5. Check the compliance register: the data flow of the standard chat pipeline must show
   the `decision-model-<code>` endpoint and a "relevance" transformation per chat
   profile; the agents network flow must show the "search-relevance" transformation.
6. If an OpenAI key with Decisions API access is available, repeat step 2 with the
   OpenAI provider (it offers the models endpoint ids that look like decision models
   plus `gpt-6-luna`, Spring AI's default); and with the generic provider against any
   server exposing `/v1/decisions`.

### What to look at before changing anything

- **Latency.** The filter asks one question per fragment, sequentially (in
  `GRankerServiceImpl.discardedDocumentIndices` with the decision model). The old
  chat-model path batched fragments. If a remote provider makes this too slow, the next
  step is a bounded parallel map that keeps the order, not a batched question (decision
  models answer one question set per input).
- **The threshold.** A fragment is kept when the yes probability is at least 0.5
  (Spring AI's `YesNoAnswer.isYes()` default). The TRACE lines show the distribution
  on real knowledge bases; decide from them whether the small model is too strict or
  too lenient before making any threshold configurable.
- **The question wording.** Spring AI's documentation says the 600m model relies
  heavily on the option descriptions. The texts live in `GRankerServiceImpl.RELEVANCE`
  (`whenYes` / `whenNo`).

### Done when

A chat answer whose log shows the decision model judging, the discarded fragments
consistent with their content, the usage and the register rows present.

## 6. Item 4: unit tests of the decision model providers

The three provider modules have no tests of their decision support services yet.
Write them before the live run, they are cheap and pin the lookup rules:

- `OpenAIDecisionModelConfigurationSupportServiceTest`: the `isDecisionModel` naming
  rule; `getModelChoices` with a mocked `IGOpenAIApiUtil`: a listed decision model is
  kept, a chat model dropped, `gpt-6-luna` appended once, a lookup error becomes a user
  message, a missing secret becomes an "api key" message.
- `OllamaModelsLookupServiceTest`: `getDecisionModels` keeps only the models whose
  `api/show` capabilities contain `decision`; a server not reporting capabilities lists
  none.
- `GenericOpenAIAPIDecisionModelConfigurationSupportServiceTest`: the configured base
  url wins over the provider's; anonymous calls only when the provider declares
  `optionalAuthentication`; the provider's declared default model is always offered;
  the models-list-provider path versus the models endpoint path.
- `GAbstractConfigurableDecisionModelTest`: `testCall` turns a provider
  `RuntimeException` into an `LLMConfigException` naming the model.

## 7. Item 5: REST stubs regenerated from the live API

### Why

The decision stubs under `gebo.ui/projects/gebo-ai-rest-api/src/lib` (16 model
files, 4 services, the `DECISION` enum values) were written by hand in the shape the
generator produces. They must be replaced by generated ones, otherwise the next
regeneration can diverge from them silently.

### Steps

Follow `.claude/skills/regen-angular-stubs` in order:

1. The preconditions of item 2: infrastructure up, fresh artifacts, the
   bootable+swagger jar on port 13999 with `-Dswagger.file`, every provider enabled;
   restore the `basePath` afterwards.
2. Run the code generation (`gebo.ui` Maven profile `generate-rest-api`).
3. Look at `git diff --stat gebo.ui/projects/gebo-ai-rest-api`. The hand-written files
   must come out identical or differ only in what the generator always rewrites
   (comment headers, property order). Any other difference reveals a mismatch between
   backend and stubs: understand it, do not patch the stubs by hand.
4. Build: `npx ng build gebo-ai-rest-api`, `npx ng build gebo-ai-admin-ui`, then the
   whole application.
5. The brain microservice exposes the same controllers (it depends on
   `gebo.architecture.llms.abstraction.layer.controllers` and
   `gebo.llms.controllers.starter`). Regenerate its Angular and Java clients under
   `gebo.api.clients/gebo.microservices.clients.parent` with the
   `.claude/skills/document-microservices-controllers` procedure, which also refreshes
   `docs/MICROSERVICES-CONTROLLERS.md`.
6. The monolithic Java client (`gebo.api.clients/gebo.monolithic.api.resttemplate.client`)
   comes from the same description: regenerate it in the same pass.

### Done when

Generated stubs committed, both UI libraries build, no hand-written stub left.

## 8. Item 6: setup wizard support of the decision kind

### Why

Today a decision model is configured from the LLMs admin panel only. The fast-setup
wizard knows six kinds (chat, embedding, ranking, images, text to speech,
transcription) and should know the seventh. It is an optional kind, like image
generation: the wizard must never require it and must create one only when a vendor
preset says so.

### Backend, module `gebo.llms.parent/gebo.llms.setup`

1. `config/ModelType`: add `DECISION`.
2. `model/ComponentLLMSStatus`: add `decisionModelSetup`, `decisionModelCode`,
   `decisionModelProviderId`.
3. `model/LLMSSetupConfigurationData`: add `decisionModelExists`.
4. `model/LLMAutoconfigureCreationData`: add `decisionModel`.
5. `services/GeboLLMSSetupService`: inject the decision runtime DAO and the decision
   support services repository; add the decision fields to `ModelKindPresence` and the
   loop that fills it; map them in the status and the configuration data; add a
   `case DECISION` to the five `switch` blocks (the existing configurations listing,
   the credentials creation, the autoconfigure, the models lookup, the models
   creation), each a copy of the `RANKING` case using the decision support service.
6. Presets in `src/main/resources/llms-fast-setup-library/library.yml`: a
   `- type: DECISION` block under the vendors that serve one. Today that is OpenAI
   (`serviceHandler: decision-OpenAI`, `doModelsLookup: false` because the models
   endpoint does not enumerate decision models reliably, choice `gpt-6-luna`) and
   Ollama (`serviceHandler: decision-ollama`, `doModelsLookup: true`, choice
   `d1-omni:600m`). No OpenAI-compatible vendor documents a Decisions API yet.
7. Tests. The setup service has none; add two: the presence computation with mocked
   DAOs (a decision model marked default gives `decisionModelSetup=true` with its code
   and provider resolved through the library), and the autoconfigure creating a
   decision model from a preset (present in the lookup: created; absent: reported as
   unresolved with the available choices, as for the other kinds).

### UI, folder `gebo.ui/projects/gebo-ai-admin-ui/src/lib/setup-wizard`

1. `llms-easy-wizard/model-classes.ts`: `"DECISION"` in `ModelClassId`, a class entry
   (`required: false`, reading `decisionModelSetup`, `decisionModelCode`,
   `decisionModelProviderId` from the status) and its texts.
2. `llms-setup-components/easy-vendor-configuration.component.{ts,html}`: the
   `decisionModel` form control, the preset block and the default choice, copied from
   the images block.
3. `llms-setup-components/llms-vendor-modeltype.component.{ts,html}`: the decision
   presets, the show flag, the looked-up models, the expert-tab field.
4. Stubs: the `TypeEnum` of `lLMCreateModelData.ts`, `lLMCredentialsCreationData.ts`
   and `lLMExistingConfiguration.ts` get `DECISION`; the status and configuration data
   models get the new fields (by hand until item 5 runs again).
5. Multilanguage keys through `.claude/skills/i18n-component-resources`, under the
   module each component declares.
6. Verification is live (the wizard has no spec files): Setup, LLMs easy wizard, OpenAI
   and Ollama show the decision class as optional; creating one lands in the LLMs
   panel's "Configured decision models".

### Done when

The wizard status shows the decision slot; autoconfigure with a decision preset
creates the model and marks it default; installations without a preset see no change.

---

## 9. Item 7: tool-call confirmation (human in the loop)

### What Spring AI 2.1 offers

A tool can be marked as needing a person's approval, either with
`@Tool(requiresConfirmation = true)` or with a `ToolCallConfirmationPolicy` given to
the tool calling manager (`fromToolMetadata()` is the default, `forTools(names...)`
exists, policies compose with `or`). When the model asks for such a tool, the manager
throws `ToolCallConfirmationRequiredException` before any tool of that batch runs. The
`ToolCallingAdvisor` catches it, stops the loop and returns the model's reply with a
metadata entry (`ToolCallDecisions.AWAITING_DECISION_METADATA_KEY`) listing the calls
that wait. The application later resumes with
`executeToolCalls(prompt, response, ToolCallDecisions)`, each decision an allow or a
deny with an optional message; a denied tool does not run and the model receives the
message as the tool's result.

### What Gebo already has

- `GeboToolCallingManager` wraps Spring AI's manager (which is final) and already
  anticipates the decisions overload: it delegates it and must apply the "ending
  controls" to its result as it does for the two-argument call.
- The harness pauses for a person through tools. `GAbstractAskUserChoiceQuestionTool`
  declares an `AgentSuspendedWaitingCondition` to the loaded `IGWaitExternalCondition`;
  the condition ends the model call; the network persists a
  `GNetworkOfAgentsSuspension` (Mongo handler) and resumes from it once the conditions
  are answered (`IGNetworkOfAgentsSuspensionHandler.respawnFrom`). The conditions reach
  the users through `WaitingConditionsController` and the chat UI.

### Design

A confirmation is a waiting condition of a new type: "the model wants to run tool X
with these arguments, allow or deny?". The person's answer resumes the batch through
the decisions overload.

1. **The policy.** `GToolCallbackRepositoryPatternImpl.createToolCallingManager()`
   builds Spring AI's default manager; give it a confirmation policy that is
   `fromToolMetadata()` or a Gebo policy reading the tool source's declaration. Add a
   `requiresConfirmation` flag to Gebo's tool declaration model so an admin can mark a
   tool of any source, MCP tools included, as needing a person.
2. **The manager.** `GeboToolCallingManager` overrides the decisions overload to
   delegate and apply its ending controls; the confirmation exception passes through.
3. **The pause.** Where the agentic loop (`AgenticLoopReactiveAgentServiceImpl` and
   every network agent calling a model with tools through `GeboToolCalling`) reads a
   reply, detect `ToolCallDecisions.isAwaitingDecision(chatResponse)`. For each waiting
   call build an `AgentSuspendedWaitingCondition` of a new answer type (call id, tool
   name, arguments as JSON, the decision), marked as human in the loop, and enter the
   suspension state as the ask-user tools do. The prompt and the paused reply must be
   persisted with the suspension, verbatim, because the resume needs them exactly:
   serialize them with Spring AI's message JSON into the agent's private context.
4. **The resume.** On respawn, build the `ToolCallDecisions` from the answers and call
   `executeToolCalls(prompt, pausedResponse, decisions)`; feed the resulting
   conversation history back into the loop as the next prompt, the way
   `ToolCallingAdvisor` does internally (read its `adviseCall`: the history replaces the
   prompt messages and the model is called again unless the result is "return direct").
5. **Streaming.** The paused reply is the last chunk of the stream, carrying the
   metadata. The chat must show "waiting for your confirmation" instead of an answer:
   a new envelope content type (a `ToolCallConfirmationRequest`) rendered by the UI as
   the existing waiting-condition card.
6. **UI.** The waiting conditions panel gets the new type: tool name, arguments shown
   as formatted JSON, allow and deny buttons, an optional message to the model on deny.

### Tests

- `GeboToolCallingManagerTest` (exists): the decisions overload applies the ending
  controls; the exception passes through.
- A policy test: a tool marked by the admin is required, an unmarked one is not, a
  marked MCP tool is required.
- `ChatNetworkStepSuspensionTest` and `ToolCallingAgentSuspensionTest` (exist): a reply
  awaiting decisions suspends the network with one condition per call; resuming with
  allow runs the tool and continues; with deny the model receives the deny message
  (assert on the next prompt).
- A streaming test on `AbstractChatService.composeFlux`: the paused reply produces the
  confirmation envelope and the final envelope, never an empty answer.
- Live: an agentic chat with one tool marked as needing confirmation; deny, then allow.

### Risks

The continuation of a paused batch is new code in Spring AI, still a release
candidate. Before relying on its metadata contract, read the two `catch` blocks of
`ToolCallingAdvisor` (around lines 216 and 419 of the 2.1 sources) and the streaming
pause (around line 523), and pin the behaviour with the tests above so that a change
at GA is noticed.

## 10. Item 8: streamed tool-call arguments in the chat

### What Spring AI 2.1 offers

Anthropic, OpenAI (Chat Completions and Responses), Bedrock Converse and DeepSeek
stream each piece of a tool call's arguments as a partial `ToolCallPart`; the
complete call still arrives once, as before. Partial parts are not tool calls:
`getToolCalls()` skips them, the message aggregator skips them, the `ChatClient`
passes the chunks through, and `.stream().content()` is unaffected. A UI can therefore
show "the model is calling tool X with ..." while the arguments are being written.

### What Gebo has

`GChatAnswerChunk.of(ChatResponse)` reads the text, the media, the finish reason and
the thinking metadata; `AbstractChatService.composeFlux` turns chunks into answer
envelopes and thinking events, which the chat UI renders.

### What to build

1. `GChatAnswerChunk`: a list of tool calls in progress (tool name, call id or index,
   arguments so far), read from the partial `ToolCallPart`s of each generation's
   message and from the complete parts (the call about to run). See
   `OpenAiChatModel.toolCallDelta` in the Spring AI sources for how the part index is
   carried.
2. `AbstractChatService.composeFlux`: a new envelope content type, say
   `ToolCallProgress { name, argumentsSoFar, complete }`, emitted for each chunk that
   carries a delta, and once with `complete = true` when the complete part arrives.
   The answer envelopes stay as they are.
3. Chat UI (`gebo-ai-chat-ui`): render the progress like the thinking block, a
   collapsed "calling <tool>..." line that fills with the arguments and closes when
   complete. Multilanguage keys through the i18n skill.
4. The networks of agents (`AgenticLoopReactiveAgentServiceImpl`,
   `ReportWriterReactiveAgentServiceImpl`) read `GChatAnswerChunk` too: they must keep
   reading only the answer text. Verify they do not re-read the parts.

### Tests

- `GChatAnswerChunkTest` (new): two partial parts at index 0 plus a text part give the
  concatenated arguments and the text; a complete part gives `complete = true`; a chunk
  with none gives an empty list.
- Alongside `CutAnswerStreamingTest` and `ThinkingStreamTest`: `composeFlux` emits the
  progress envelopes in order with the answer ones and never an empty answer envelope
  for a delta-only chunk.
- Live: an agentic chat on an OpenAI or Anthropic model with a tool taking a long
  argument (a search query): the UI shows the arguments filling in.

Note: Ollama, Mistral and Google GenAI send the call whole. The UI must cope with
`complete` arriving without any delta.

## 11. Item 9: Spring AI's own OpenAI Responses model

### Where things stand

Gebo has its own chat model over OpenAI's Responses API
(`gebo.llms.parent/gebo.llms.openai/.../services/OpenAiResponsesChatModel.java`, 601
lines, five tests). `OpenAIChatModelConfigurationSupportService` uses it for the
models of version 6 and later (`usesResponsesApi`). It is stateless, runs no tool loop,
maps the conversation to Responses items, and carries tool calls, reasoning and usage.

Spring AI 2.1 now ships `org.springframework.ai.openai.responses.OpenAiResponsesChatModel`
with the same design, and `OpenAiResponsesChatOptions` (model, reasoning effort and
summary, verbosity, max output tokens, temperature and top-p, response format, tool
choice, parallel tool calls, hosted tools, service tier, prompt cache key, safety
identifier, metadata, extra body, output schema). Its known limits: no audio, no `n`,
no stop sequences, no penalties, no seed, no logit bias.

Replacing 601 tested lines with Spring AI's class is attractive, but only after
proving the native model does everything Gebo's does on a real model.

### Steps

1. **Compare on the real path, before any code.** List what `configureModel` sets on
   the Chat Completions options today (model, temperature, top-p, tools with parallel
   calls, max completion tokens, reasoning effort, user, timeout, api key) and map each
   to the Responses options. List what Gebo's class does that Spring AI's may not,
   reading its documentation comment and its five tests: the items mapping, the cut
   answer reporting `length`, the reasoning sent back on the next turn, the usage.
2. **Build Spring AI's model** in `configureModel`, behind the same `usesResponsesApi`
   branch, with the OpenAI client from `OpenAiSetup.setupSyncClient`, the options, the
   tool calling manager, the observation registry and the HTTP client customizer.
   Check whether the Responses builder's `toolCallingManager` is deprecated like the
   Chat Completions one; if so the manager belongs to the `ToolCallingAdvisor` that
   `GAbstractConfigurableChatModel` builds.
3. **Reasoning.** Spring AI's model emits reasoning parts, which `PARTS` reads; Gebo's
   emits the legacy metadata. Add to the thinking advisor tests a Responses-shaped
   stream (one reasoning part per summary delta, then text) proving the thinking
   reaches the UI once, before the answer.
4. **Reasoning continuity across turns.** Spring AI notes that no chat memory persists
   message parts yet. Gebo rebuilds the prompt from its own session, so the encrypted
   reasoning items of a previous turn would not be sent back; Gebo's class sends them.
   Confirm on a two-turn tool-calling conversation (gpt-5 or later) that Spring AI's
   model does not fail without them. OpenAI requires those items only within one
   tool-calling turn, which the advisor loop keeps in memory.
5. **Live run** on the prompts of `OpenAiResponsesChatModelTest`, from the live test
   harness in `gebo.llms.tests.services`, on a gpt-5 or later model: a blocking call, a
   streamed answer with thinking, a tool-calling round, a cut answer reporting
   `length`, the usage recorded.
6. Only when 1 to 5 are green: delete Gebo's class and its test, keep
   `usesResponsesApi` as the routing rule.

Keep Gebo's class otherwise. It is tested and in production shape.

## 12. Item 10: session memory

Spring AI 2.1 deprecates its chat memory classes (`ChatMemory`,
`MessageWindowChatMemory`, `MessageChatMemoryAdvisor`, the repositories) for removal in
3.0, replaced by **session memory**: an append-only log of events per session
(`SessionService`, `SessionRepository`), advisors for the `ChatClient`, event filters,
and "turn-aware compaction" that trims the history on turn boundaries, so the model
never sees a tool call without its result or an answer without its question.

Gebo uses none of the deprecated classes; its conversation state is its own
(`GUserChatSession`, the session shrink, the agents' private contexts, the network
suspensions). Nothing breaks at 3.0.

What a short spike should answer:

1. Would the agentic loop's context management (the shrink, the cut answers, the
   earlier-documents handling) be simpler on a `SessionService` with a window
   compaction, keeping Mongo as the store? There is no Mongo session repository in 2.1
   (planned before 3.0); Gebo would write one over `IGPersistentObjectManager`.
2. Does the turn-aware window fix a real problem today? Check whether the session
   shrink can cut inside a turn (a tool call kept without its result). If it can,
   write the failing test and fix it locally first.

Recommendation: no adoption on this branch; a design note after the spike. The only
code worth writing now is the test of point 2.

## 13. Item 11: deprecated APIs flagged by the build

The compile logs of the branch flag these APIs as deprecated for removal. Counts are
occurrences in the logs, not call sites.

| Deprecated for removal | Where | Count | Replacement |
|---|---|---|---|
| `RestTemplate` (with `RequestCallback`, `ResponseExtractor`) | `gebo.atlassian.jira.cloud.client` (17), `gebo.monolithic.api.resttemplate.client` (17, generated), `gebo.architecture.integration.tests` (16), `gebo.openrouter.ai.client` (12), `gebo.restintegration.abstraction.layer` (32), `gebo.webconfig` (7), `gebo.googlesearch.handler` (6), `gebo.atlassian.jira.handler` (6), `gebo.openai.integration.client` (4) | about 117 | `RestClient` (Spring Framework 7.1 deprecated `RestTemplate` for removal) |
| `MappingJackson2HttpMessageConverter` | `gebo.architecture.integration.tests` | 6 | `JacksonJsonHttpMessageConverter` (Jackson 3) |
| `HttpEntity(MultiValueMap)` | `gebo.llms.deepseek` | 2 | `HttpEntity(body, headers)` |
| Spring AI `JsonParser` (`toJson`, `fromJson`) | `gebo.architecture.agents.standard` (26), `gebo.architecture.llms.abstraction.layer` (12), `gebo.llms.standard.functions` (8, tests included) | about 46 | Spring AI's `JacksonUtils` or Gebo's `JsonHelper` |
| `toolCallingManager(...)` on the chat model builders | `gebo.llms.openai`, `gebo.llms.mistral`, `gebo.llms.generic-openai-compatible`, `gebo.llms.aws-bedrock`, `gebo.llms.anthropic3` | 10 | the manager belongs to the `ToolCallingAdvisor` of the `ChatClient` since 2.0 |
| `toolCallbacks(...)` on the `ChatClient` request | tests of `gebo.architecture.llms.abstraction.layer` and `gebo.llms.tests.services` | 18 | `.tools(...)` or a `ToolCallbackProvider` |
| `ChatModel.getDefaultOptions()` | `gebo.architecture.llms.abstraction.layer` (`AnswerFirstGenerations.Model`, the usage wrappers) | 8 | `getOptions()`; drop the override when the interface loses the method |
| `new Integer(int)`, `new Long(long)` | `gebo.llms.generic-openai-compatible`, `gebo.architecture.graphrag.persistence`, `gebo.architecture.compute.workflow` | 8 | `Integer.valueOf`, `Long.valueOf` |
| The opaque token introspector constructors | security modules | 3 | item 12 |

### Order, lowest risk first

1. **`JsonParser`.** Mechanical replacement; covered by the tool argument parsing tests
   of the agents module and by `ReadUrlRoomTest`.
2. **`toolCallingManager` and `toolCallbacks`.** Remove the builder calls (the
   `ChatClient` advisor already carries the manager: verify in
   `GAbstractConfigurableChatModel` where the advisor is built with
   `GeboToolCallingManager`); move the tests to `.tools(...)`. Run
   `GeboToolCallingTest`, `GeboToolCallingManagerTest`, `ThinkingNormalizationAdvisorTest`.
3. **`getDefaultOptions`.** Delete the overrides when the interface default goes (at
   GA at the earliest); until then they are harmless.
4. **Converter, `HttpEntity`, boxed constructors.** Mechanical.
5. **`RestTemplate` to `RestClient`.** Not mechanical. The seam is
   `RestTemplateWrapperService` in `gebo.restintegration.abstraction.layer`, used by
   the Ollama lookup and the integration clients: give it a `RestClient`-backed
   implementation with the same methods (`getForEntity`, `postForEntity`, `toMessage`),
   keeping the interceptors and the retry (`IGLlmsServiceClientsProvider.getRestClientBuilder()`
   already exists), then switch the callers module by module. The generated
   `gebo.monolithic.api.resttemplate.client` goes last: regenerate it with a
   `RestClient` or `WebClient` code generation library, or keep it until the Framework
   removes the class. Tests: the integration tests module exercises the wrapper against
   live services; the Ollama lookup and the Jira and Google handlers need fakes for the
   error mapping.

## 14. Item 12: Spring Security 7.2

Boot 4.2.0-RC2 manages Spring Security 7.2.0-RC1. The security modules
(`gebo.architecture.security`, `.security.controllers`, `.security.apikey`,
`.security.sessions`, `gebo.security.directory.mongo`, `gebo.webconfig`) compile on it
without a warning in their configuration (`GeboAISecurityConfig`, `GeboSecurityConfig`,
the JWT decoder caches, the dynamic client registrations, the login policy). One API is
deprecated for removal: the three-argument constructor of the opaque token
introspectors, used in three places.

### What to change

| Where | Today | Replacement |
|---|---|---|
| `SingleOauth2ConfigOpaqueTokenAuthenticationManager` line 39, the servlet resource-server manager of one SSO configuration | `new SpringOpaqueTokenIntrospector(uri, clientId, secret)` | `RestClientOpaqueTokenIntrospector.withIntrospectionUri(uri).clientId(..).clientSecret(..).build()`. `SpringOpaqueTokenIntrospector` rests on `RestTemplate`, itself deprecated for removal, so its own builder is only a stop-gap. |
| `SecurityHeaderDataCompletionController` line 175, which introspects a raw token to find which SSO configuration issued it | same constructor | same; build one introspector per configuration and cache it, next to `JwtDecoderCache`, instead of one per request |
| `GReactiveHttpRequestAuthenticationManagerResolverImpl` line 155, the reactive manager of the microservices | `new SpringReactiveOpaqueTokenIntrospector(uri, clientId, secret)` | `SpringReactiveOpaqueTokenIntrospector.withIntrospectionUri(uri).clientId(..).clientSecret(..).build()`, WebClient based, not deprecated |

### What to verify

Spring Security 7.2 makes both introspectors send
`Content-Type: application/x-www-form-urlencoded`, as RFC 7662 requires. The providers
Gebo introspects against are the generic OIDC configurations with an `introspectionUri`
(`ai.gebo.security.oauth2configs[].providerConfig`, KeyCloak for instance). A provider
that accepted the previous request keeps working; one that rejected the old content
type starts working. One live login per configured provider confirms it.

### Not affected

No `SecurityJackson2Modules` (the Jackson 3 migration of Security 7.0 does not apply),
no one-time-token login (7.2 made its account status checks opt-in), no
Content-Security-Policy configured (7.2 adds nonce-based CSP; a hardening item, see
`GEBO-HARDENING-MANUAL.md`, not an upgrade one).

### Tests to write

In `gebo.architecture.security/src/test/.../authmanagers`:

- `OpaqueTokenIntrospectionTest`: a local HTTP server playing the introspection
  endpoint and recording the request; assert the content type, the client's basic
  authentication, the `token=` form body, and the principal mapped from an active
  answer through the servlet and the reactive converters; an inactive answer is
  rejected. Run for both managers.
- `SecurityHeaderDataCompletionControllerTest`: two configurations, the token accepted
  by the second only gives the OAuth2 type with that registration id; the introspector
  cache is reused across calls.

### Open until the GA

Boot 4.2's security auto-configuration notes are not published yet ("full release
notes when 4.2 is released"): re-read them at the GA bump for the management
endpoints security, the OAuth2 client properties and the CORS defaults. Spring
Security's "preparing for 8.0" guide is still empty; the deprecation above is the
whole list for this code base.

## 15. Item 13: documentation

1. `docs/APPLICATION-YML-ADMIN-MANUAL.md`. Section 8 (LLM providers) gets a paragraph
   on the decision model kind: what it is, which providers, the
   `decision-generic-openai` entry and the `decisionModelProviders` list of
   `providers.yml`. Section 18 gets the ranker filter keys, none of which is documented
   today, with the defaults of `GeboRagSearchConfig`:
   `ai.gebo.ragsearch.config.rankerIrrelevanceFilterEnabled`,
   `rankerIrrelevanceFilterContextFraction`, `rankerIrrelevanceFilterMaxFragmentsPerBatch`,
   `rankerIrrelevanceFilterMinDocuments`, `rankerIrrelevanceFilterWarnRate`,
   `rankerIrrelevanceFilterProtectedTopFragments`, `rankerIrrelevanceFilterUsesDecisionModel`,
   `hybridSearchEnabled`, `hybridFullTextShare`.
2. `docs/LLM-ANSWER-THINKING-STREAMING-DESIGN.md`: a section on the message parts of
   Spring AI 2.1, the `PARTS` reading, its legacy fallback, and why the Anthropic tap
   is gone.
3. The user and admin manual (`.claude/skills/document-gebo-ai-ui-manual`): the "New
   decision model" option of the LLMs panel and the three editors, with screenshots
   from a live instance, after item 3.
4. `docs/MICROSERVICES-CONTROLLERS.md`: regenerated with the stubs (item 5).
5. The pin comments of the root `pom.xml` record why each version is pinned; keep
   them current at the GA bump.

## 16. Item 14: GA bump and merge

1. When Spring Boot 4.2.0, Spring AI 2.1.0 and Spring Cloud 2026.0.0 are on Maven
   Central: `spring-boot-starter-parent` in the root and in
   `microservices-integration-tests/pom.xml`; `spring.ai.version` in the root;
   `spring-cloud.version` in the 16 poms (`gebo.apps.parent/gebo.microservices.apps.parent`
   and the 15 modules under `gebo.microservices.architecture.parent`).
2. Re-check the pins against the GA BOMs: `derby.version` (drop if Boot manages
   10.17.1.0 or later), `spring-retry.version` (drop if Boot manages it), the MCP
   `mcp-bom` (set to what `spring-ai-mcp` 2.1.0 declares, or drop if the Spring AI BOM
   manages it), springdoc (move to the release that claims Boot 4.2).
3. A full `mvn clean install` of this repository, then clean builds of the downstream
   editions (partial builds ship stale jars). Fix what changed between the release
   candidates and the GA, listed in the upgrade notes.
4. CI green; repeat the boot smoke test (item 2) and the live decision check (item 3)
   on the GA jars.
5. Merge into `develop` with a merge commit, keeping the branch history.

## 17. Suggested order

1. Items 2, 3 and 4: boot smoke test, live decision check, provider unit tests. They
   validate what is already written and are cheap.
2. Item 5, stubs regeneration (needs the running application of item 2).
3. Item 13, points 1 and 2 (the manuals).
4. Item 11, steps 1 to 4, and item 12: mechanical, and they keep the warning noise
   down for the rest.
5. Item 6, the setup wizard.
6. Item 9, the native Responses model (needs live tests on a gpt-5 or later model).
7. Item 7, tool-call confirmation, then item 8, streamed tool arguments (both touch the
   chat UI and the harness; 8 is simpler once 7's envelope type exists).
8. Item 11, step 5, the `RestTemplate` migration.
9. Item 10, the session memory spike, design note only.
10. Item 14, at the GA releases.
