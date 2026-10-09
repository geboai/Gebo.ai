# Answer + thinking streaming, normalized in GAbstractConfigurableChatModel — design (v2)

Status: DRAFT v2 for review (2026-10-09). Nothing implemented.
Grounding: every claim below was checked twice — by an independent from-scratch analysis and by an
adversarial review of v1 — and the load-bearing ones a third time by hand. Evidence is `file:line`
at develop `8b2516a6a` (PRs #269, #270 merged), or `javap -c` on the jars on the classpath (Spring AI
2.0.1, anthropic-java-core 2.52.0), or a quote of the provider docs. v1 was wrong on where the tool
loop runs (§1.2) and its `structuredResponse` change would have dropped Spring's format
instructions (§3.4); both are corrected here.

---

## 1. Current state

### 1.1 The model API

`IGConfigurableChatModel` (gebo.architecture.llms.abstraction.layer), implemented once in
`GAbstractConfigurableChatModel` (every provider handler has an inner class extending it; none
overrides the call methods):

| Method | Interface | Implementation | Returns |
|---|---|---|---|
| `streamResponse` | :82 | :758-780 `.stream().chatResponse()` | raw `Flux<ChatResponse>` |
| `streamStringResponse` | :85 | :795-817 `.stream().content()` | text only; reasoning and finish reason dropped |
| `response` | :97 | :820-838 `.call().chatResponse()` | raw `ChatResponse` |
| `textResponse` | :100 | :907-908 `.call().content()` | `getResult().getOutput().getText()` |
| `structuredResponse` ×2 | :103, :107 | :855, :883 `.call().entity(...)` | entity parsed from `getResult()` text |
| `doWithChatModel` / `doWithChatClient` | :135 / :142 | :925 / :919 | raw model / client (graph extraction only) |

`content()` and `entity()` both read `ChatResponse.getResult()` = `generations.get(0)`
(`DefaultCallResponseSpec.getContentFromChatResponse`: `getResult` → `getOutput` → `getText`;
verified). `entity()` also puts `converter.getFormat()` in the request as `OUTPUT_FORMAT` and may
set `STRUCTURED_OUTPUT_NATIVE` / `_SCHEMA` (verified). Spring's `BeanOutputConverter` already strips
`<think>`, `<thinking>`, `<reasoning>`, ```` ```thinking ````, `<!-- thinking: -->` blocks
(`ThinkingTagCleaner` in its default text cleaner; verified).

### 1.2 The tool loop is an advisor (v1 was wrong)

In Spring AI 2.0.1 no provider `ChatModel` executes tools (no `executeToolCalls` in any of them;
the `ToolCallingManager` Gebo passes only resolves tool definitions). The loop is
`ToolCallingAdvisor`, auto-registered on every ChatClient by
`DefaultChatClientRequestSpec.autoRegisterToolCallingAdvisor()` (opt-out:
`ChatClientAttributes.TOOL_CALLING_ADVISOR_AUTO_REGISTER`). In its streaming path (verified):

- each tool round is appended with `concatWith(...)` and every chunk for which
  `isToolCallResponse` (default `hasToolCalls()`) is true is **filtered out**;
- each recursion does `StreamAdvisorChain.copy(this)` + `nextStream`: **advisors placed inside it
  run again for every model round**.

Gebo's usage advisor already sits inside it (order LOWEST-100) and sees every round's chunks,
tool-call chunks included.

### 1.3 Consumers of model output

Streaming to the user:
- `AbstractChatService.composeFlux` (chat.abstraction.layer, :358-584) via `streamChatClient` :284
  and the cut retry :291 — direct, RAG and pipeline chats (`DefaultStreamingOutput…:54`,
  `DefaultToolUsingStreamingOutput…:57`, `DefaultChatWithFilesStreamingOutput…:84,102`,
  `DefaultRagStreamingOutput…:73`).
- `AgenticLoopReactiveAgentServiceImpl.chunkText` (agents.standard, :1722-1751) — output node of the
  default chat networks (`StandardAgentsConfig` :51, :58; `AgenticLoopPureChat…` extends it).
- plain `ReportWriterReactiveAgentServiceImpl.createResponse` :298 → `callLLMReactive`
  (`BaseLLMSInvokingService` :1124-1126) → `streamStringResponse` (:335, :530) — output node of the
  controller network `DEFAULT_AGENTS_NETWORK` (`StandardAgentsInitialization` :269-276).
- deep search: `FullReactiveDeepsearchWorker` :336, :392, :456; the huge-files branch
  `streamChatWithHugeFiles` :547 → `analysis.analyze` :671 (from
  `DefaultChatWithFilesStreamingOutput…:110`); the second deep-search step
  `DefaultDeepInternalKnowledgeBaseDeepSearchStreamOutputChatPipelineServiceImpl` :54.
  `DeepSearchAnalysis.analyze` :164, :231 returns `Flux<String>` and takes no emitter.
- blocking chat to the user: `AbstractChatService.callChatClient` :226 → :235-240
  (`GChatServiceImpl` :126, `GRagChatServiceImpl` :183): `queryResponse` from raw `getText()`, no
  thinking removal, no `thinkingOutputs`.

Internal (clean text or entity only): routing / rewrite
(`DefaultRoutingChatPipelineStepServiceImpl` :193, :360), ranker (`GRankerServiceImpl` :418), image
prompt (`DefaultImageGenerationStreamingOutputChatPipelineServiceImpl` :241), search planners,
shrinker (:401, :499), chat title (:1277), rule proposals (:106, :170), deep-search partials and
folds (`DeepSearchAnalysis` :190, :288), report evidence (:559), network agents
(`GBaseToolCallingNetworkAgent` :142, :152; `GBaseTaskPerformerNetworkAgentService` :94, :104;
`GBaseRoutingNetworkAgentService` :265), autotune (:914, :1053), graph extraction
(`GraphDataExtractionServiceImpl` :116-142, :535-560). Thinking removal there is inconsistent:
always (title, rules, `BaseLLMSInvokingService` :284), only under the markup flag (the other
`BaseLLMSInvokingService` helpers), never (field-entry parsing, network agents' text).

Agents also get a `notifyUser` tool when a notification sink is present
(`GAbstractGenericalAgentService` :393-398, `createUserMessageTool` :431): the model's deliberate
messages to the user as `ChatNotificationContent`, a channel separate from thinking.

### 1.4 How thinking reaches the user

- Extraction: `ThinkingStream` reads only metadata `reasoningContent` (cumulative; any non-prefix
  value is taken as a new block) and inline tags per chunk (a tag split across chunks leaks).
  Callers: `composeFlux` :409 and `chunkText` :1737 only.
- Events: `GThinkingEvent{text delta, completed}` in `GeboChatMessageEnvelope`; type tag =
  `content.getClass().getSimpleName()` (:112). Agentic loop: side channel `ISinkUIEmitter`
  (`thinkingTo` :1754-1768), merged by time with the main flux (`ChatPipelinesExecutorImpl` :278).
  A2A sinks (`A2ANotificationCollector`) are not `ISinkUIEmitter` and get none.
- UI: `gebo-ai-reusable-chat.component.ts` :1453-1461 appends to `streamingThinking`, clears it on
  `completed` or `lastMessage`; italic "Reasoning:" block (`.html` :92-97). `handleGeboChatResponse`
  replaces the streamed text with the final `queryResponse` when non-empty (:1271-1275). Second
  envelope consumer: `chat-stream-events-display.component.ts` :125-147. `thinkingOutputs` is never
  read by the UI (persisted via `endRequest`, present in the stubs).

### 1.5 Per provider (Spring AI 2.0.1 bytecode)

| Provider | Streaming | Non-streaming | Reaches the UI today |
|---|---|---|---|
| OpenAI + compatible | metadata `reasoningContent`, cumulative per round (`ConcurrentHashMap.merge`); api.openai.com Chat Completions sends none, compatible gateways do | same key | yes (compatible providers that send it) |
| Anthropic | per `thinking_delta` a text-less chunk `{thinking:true}`; `{signature}`, `{data}` chunks; the turn's thinking only on the end-of-turn `message_delta` chunk as `anthropicThinkingContents` (package-private record), **together with the tool calls** | one Generation per thinking block **first**, the answer Generation **last** | no |
| DeepSeek | field `DeepSeekAssistantMessage.getReasoningContent()`, delta | same | no |
| Mistral | metadata `thinking_content` (+`reference_thinking_content`), delta | same | no |
| Ollama | property and metadata `thinking`, delta; content carries no tags | same | no |
| Gemini | one Generation per part with property `isThought`; thought parts dropped when the candidate has a function call; Gebo never sets `includeThoughts` | same shape | no |
| Bedrock Converse | stream handles only toolUse and TEXT deltas (`ConverseChatResponseStream.visitContentBlockDelta`) | package-private `BedrockReasoningContent` | no (Gebo requests none) |

Claude API (docs, build-with-claude/thinking): on Opus/Sonnet/Haiku 5.5, Fable 5.1, Opus 4.7-4.8
"`display` defaults to `"omitted"`"; with omitted "no thinking text is streamed". SDK 2.52.0
`ThinkingConfigAdaptive.Display` has `SUMMARIZED` and `OMITTED`.

### 1.6 Defects (all verified in code)

1. **Claude non-streaming calls read the thinking, not the answer.** Thinking Generations come first
   (`AnthropicChatModel.buildGenerations`, answer added last, offsets 583-598) and `content()` /
   `entity()` read `getResult()` = generation 0 — empty under "omitted". Affects `textResponse`,
   `structuredResponse`, `callChatClient`, `writtenAgainIfCut` and every internal call through
   Claude. Live evidence: query rewriting on Haiku 5.5 "gave none of the field(s)" twice
   (14:12:39, 14:12:41) while those two calls produced 396 and 306 output tokens.
2. **Claude thinking of tool-calling turns never reaches Gebo**: it rides the `message_delta` chunk
   with the tool calls, which `ToolCallingAdvisor` filters out. The final turn's thinking arrives
   after that turn's answer text.
3. **Gemini thinking levels throw**: Spring AI's `THINKING_LEVEL_SUPPORT_BY_MODEL` maps
   `gemini-2.5-flash` and `gemini-2.5-flash-lite` to no level and `gemini-3-pro-preview` to
   `{LOW, HIGH}`. Gebo sends a level for every option but AUTO (`MINIMAL` for NO_THINKING,
   `MEDIUM` for medium) → `IllegalArgumentException` from `validateThinkingLevelForModel`.
4. `ClientChatCallUtil.removeThinking` (:44-83): `</thinking>` cut with the start tag's length
   (stray `>`, :68); only the first block removed and any text before it lost; a closing tag at
   index 0 not cleaned (`> 0`, :56, :67); an unclosed block returned with the thinking.
5. `AbstractChatService` :438-446: once the buffer holds the closing tag the whole chunk is emitted
   as answer text (thinking tail and tag included).
6. No inline-tag handling in the agentic loop, the plain report writer, deep search or
   `callChatClient`: tag-emitting models leak thinking to the user and into saved answers/history.
7. Markup handling on (`isApplyThinkingMarkupHandling()`: Ollama always, generic OpenAI types with
   the flag, e.g. vLLM) with a model that writes no tags: the whole answer is routed to
   `GThinkingEvent` and appears only with the final response; `CutAnswer` counts it as reasoning.

### 1.7 Open point: Claude calls cancelled at 60 s

Two Opus calls inside deep-search tools ended with `InterruptedIOException: timeout` at exactly
60.0 s, while a 115 s call succeeded. The per-request timeouts the SDK actually applies are not
established. Commit "Claude calls get the configured response timeout" (merged in #269) sets the
options timeout to the configured 240 s. To measure: log the SDK's `X-Stainless-Timeout` /
`X-Stainless-Read-Timeout` request headers on a long deep search. `display: "summarized"` (D4)
also changes how silent a thinking phase is.

---

## 2. Rule (decided)

**The output node always streams thinking**; everything else gets clean answer text.
- Agent networks: the reactive output node (exactly one per network;
  `GAbstractReactiveOutputAgentsNetworkServiceFactory` :95-96).
- Chat pipelines: the streaming output step (chats via `composeFlux`; deep search final analysis
  and summary, the huge-files branch, the second deep-search step).
- Blocking chat to the user (`callChatClient`): thinking in `thinkingOutputs`, clean `queryResponse`.
- Controllers, routing and task performers speak to the user only through `notifyUser`; tools,
  internal calls, partials and folds never carry thinking.

---

## 3. Design

### 3.1 Normalization as an advisor inside the tool loop

A Gebo `ThinkingNormalizationAdvisor` (CallAdvisor + StreamAdvisor), registered by
`GAbstractConfigurableChatModel` next to the usage advisor (`initialize` :216-220, clone path
:972-977), ordered inside `ToolCallingAdvisor` so that it runs once per model round (§1.2).

- **Stream, per round** (state created inside `adviseStream`, i.e. per round and per subscription):
  ask the provider hook (§3.3) for the thinking delta and the answer delta of each chunk, run the
  inline-tag splitter (§3.2) on the answer delta, and rewrite the chunk: `getText()` = answer only,
  plus metadata `geboThinkingDelta` (String) and `geboThinkingActive` (Boolean). Every original
  metadata key and the AssistantMessage subclass are kept (OpenAI replays `reasoningContent`,
  Anthropic needs signatures for thinking replay, DeepSeek round-trips its field).
- **Tool-call chunks**: when a chunk carries both thinking and tool calls (Claude's
  `message_delta`), emit a thinking-only chunk before it, so the thinking survives the
  `hasToolCalls` filter (defect 2, tool turns).
- **Call**: reorder or reduce the generations so that `getResult()` is the answer (Claude: the last
  non-thinking Generation; Gemini: the non-thought parts) and put the turn's thinking in metadata
  `geboThinking`. `content()` and `entity()` read `getResult()`, so this fixes defect 1 for every
  blocking consumer with no call-site change, and `entity()` keeps Spring's format instructions and
  native structured output.

Consequence: every `getText()` / `content()` / `entity()` consumer, `streamStringResponse`
included, gets thinking-free text; the internal consumers need no change.

### 3.2 Inline-tag splitter

One stateful parser replacing `isAfterThinking` / `ThinkingStream.inline` / `removeThinking`: both
tag styles, tags split across chunks (hold back a possible tag prefix), several blocks, neither tag
ever emitted, an unclosed block at end of round treated as thinking. `removeThinking` is rewritten
on top of it (defect 4). It runs only when `isApplyThinkingMarkupHandling()` is true, and not for a
round where the provider hook already found field reasoning (defect 7).

### 3.3 Per-provider hook

`protected IGReasoningExtractor reasoningExtractor()` on `GAbstractConfigurableChatModel`, overridden
in each provider handler's inner class:

| Handler | Extractor |
|---|---|
| default (OpenAI, compatible) | metadata `reasoningContent`, cumulative → delta per round |
| DeepSeek | `instanceof DeepSeekAssistantMessage` → `getReasoningContent()`, delta |
| Mistral | metadata `thinking_content`, delta |
| Ollama | property `thinking`, delta; inline splitter only if no field reasoning was seen |
| Anthropic | `{thinking:true}` → active; `anthropicThinkingContents` (reflection: package-private type) → text; call: the thinking Generations. Request side: display per D4 |
| Gemini | Generations with `isThought=true` → thinking (only if `includeThoughts` is set) |
| Bedrock | none (nothing exposed while streaming) |

### 3.4 API on `IGConfigurableChatModel`

- `Flux<GChatAnswerChunk> streamAnswer(prompt, params, ctx)` and `GChatAnswer answer(...)` over the
  normalized responses (`answerDelta`, `thinkingDelta`, `thinkingActive`, `finishReason`, `media`,
  `source`).
- `streamStringResponse` = `streamAnswer` answer deltas, empty ones filtered.
- `structuredResponse` stays on `.entity(...)` (format instructions kept), fed clean text by §3.1.
- `streamResponse` deprecated once `composeFlux` and `chunkText` move (D5), removed later.

### 3.5 Consumers

| Consumer | Change |
|---|---|
| `composeFlux` :358-584 | read `streamAnswer`; `ThinkingStream` receives deltas only; markup/tag logic removed; `thinkingOutputs` from the normalized thinking (today only from inline tags, :540) |
| `callChatClient` :226-240, `writtenAgainIfCut` :626-672 | read `answer()`: clean `queryResponse`, `thinkingOutputs` set |
| `chunkText` :1722-1751 | read `streamAnswer`; side-channel `GThinkingEvent` unchanged |
| plain report writer :298 | output node (§2): `streamAnswer`, `GThinkingEvent` via the sink as the agentic loop does |
| deep search worker, huge-files branch, second deep-search step | output step (§2): `analyze` gains a thinking channel (signature change); the tool callers (`AbstractDeepSearchTool` :340, `DeepSearchToolAnalysis` :81) ignore it |
| `BaseLLMSInvokingService` | conditional `removeThinking` calls become no-ops; `stopWhen` (:766-770) tests answer text only |
| `CutAnswer` accounting | reasoning characters from the normalized thinking |
| usage advisor | reads the normalized thinking (it also sees tool-turn chunks the consumers do not) |

### 3.6 Contracts kept

Envelope JSON `{content, lastMessage, contentObjectType}` with the simple class name; type strings
`String`, `GThinkingEvent`, `GeboChatResponse`, `GUserMessage`, `PipelineRoutingInfos`,
`ChatNotificationContent`, `GInputProcessingEvent`, `FinalMessageContent` (`FINAL_MESSAGE`,
`lastMessage=true`); `GThinkingEvent{text delta, completed}` with `completed` before the first
answer chunk; the UI's JSON-in-`String` heuristic (component :1431-1437); a complete,
thinking-free final `queryResponse` (the UI replaces the streamed text with it) and thinking-free
persisted history (fed back as `AssistantMessage`s); `thinkingOutputs: List<String>` kept, now
filled from the normalized thinking (a change in persisted data, to state in the PR); provider
metadata keys and AssistantMessage subclasses preserved; the SSE endpoints and `stopChatPipeline`.

### 3.7 Limits (library-imposed)

- Claude thinking text cannot stream token by token through Spring AI 2.0.1: only text-less
  `{thinking:true}` signals stream; the text comes per turn on the end-of-turn chunk, **after that
  turn's answer text**. Token-level would need a Gebo SSE tap on the Anthropic HTTP client
  (`AnthropicClientCustomizer` is the existing hook) or an upstream change.
- OpenAI's own API returns no reasoning text in Chat Completions (summaries need the Responses API).
- Bedrock: nothing while streaming without a Gebo Converse stream adapter.
- Gemini: thoughts on function-call turns are dropped inside the model mapping.
- Tool-round boundaries stay implicit for consumers (rounds are concatenated); the advisor sees
  them.

---

## 4. Decisions

- **Requirement** (decided): thinking reaches the UI live, **before the answer**, as in the products
  on the market. The UI already does this (`GThinkingEvent` appended above the answer, cleared when
  the answer starts): no UI change; the work is in the backend.
- **D1** One feature branch, one PR, one commit per step (decided).
- **D2** Order: answer-first fix, splitter, advisor, chats, agentic loop, Anthropic, DeepSeek,
  Mistral, Ollama, Gemini, report writer, deep search, usage tracking (decided).
- **D3** By §2 (decided).
- **D4** Anthropic: request `display: "summarized"` and stream the thinking live through a Gebo tap
  on the Anthropic HTTP client, because Spring AI (2.0.1 and the latest published 2.1.0-M1) keeps the
  `thinking_delta` text and emits it only after the answer (§3.7). The tap copies the
  `thinking_delta` text of a request, identified by a per-request header
  (`AnthropicChatOptions.httpHeaders` → `putAdditionalHeader`), and the extractor attaches it to the
  matching `{thinking}` chunk. Gate: a live Opus call shows thinking before the first answer chunk;
  if the correlation is not reliable the work stops there and is reported.
- **D5** `streamResponse` deprecated, removed in a later release (decided).
- **G1** Gemini thinking levels: the Vertex handler stops sending `thinkingLevel` and the Vertex
  chat model editor no longer offers the thinking setting (provider-level removal, no per-model
  logic) (decided).
- **G2** Gemini thoughts (`includeThoughts`): not enabled, since thinking is no longer configured on
  Vertex (follows from G1).

## 5. Tests

- Unit: one extractor test per provider shape (§1.5); the splitter (split tags, both styles,
  several blocks, unclosed, literal tags in code); `removeThinking` regressions; the advisor in
  stream and call mode (Claude call fixture with thinking Generations first; Claude tool-turn chunk
  carrying thinking + tool calls).
- To rewrite, not only re-run: `CutAnswerStreamingTest` (calls `composeFlux` with
  `REASONING_CONTENT_METADATA`, :82, :92), `AgenticLoopCutAnswerTest` :75 and
  `AgenticLoopReactiveAgentServiceTest` :96 (override `callLLMReactiveResponses`), the seven tests
  mocking `IGConfigurableChatModel`, `StreamedDocumentsCallTest` (stubs `streamStringResponse`).
  `TestKnowledgeExtractionCallResponseSpecWrapper` throws on `entity(StructuredOutputConverter)`.
- Integration through `AbstractGeboMonolithicIntegrationTestsWithFakeLLMS`: `TestChatModel` streams a
  single chunk (`stream` = `Flux.just(call())`, :103); give it a scripted multi-chunk mode (provider
  shapes, a tool round) surviving `cloneWithOptions`; assert per path (direct, blocking, pipeline,
  agentic loop, report writer, deep search, internal title/routing) that no `String` envelope or
  saved answer carries thinking, that `GThinkingEvent`s carry it in order with `completed` first,
  and that `textResponse` returns the answer for the Claude call fixture.
- Live, per vendor: one prompt with thinking on (Anthropic `textResponse`, Ollama `thinking`).

## 6. Phasing (one branch `feature/answer-thinking-streaming`)

1. Answer-first generations (defect 1): call advisor + raw-model decorator, Anthropic marks its
   thinking generations. Live gate: Haiku query rewriting no longer falls back.
2. Inline-thinking splitter; `removeThinking` on top of it (defects 4, 5).
3. Normalization advisor inside the tool loop, extractor hook, `streamAnswer` / `answer`.
4. Chats (`composeFlux`, `callChatClient`, `writtenAgainIfCut`) on the new stream.
5. Agentic loop on the new stream (defect 6 there).
6. Anthropic: display summarized + HTTP tap + extractor (D4) + timeout-header logging (§1.7).
7. DeepSeek, 8. Mistral, 9. Ollama extractors (defect 7).
10. Vertex: no thinking level, no thinking setting in its editor (G1, defect 3).
11. Plain report writer streams thinking (output node).
12. Deep search output step streams thinking.
13. Usage tracking reads the separated thinking.
Then full build, tests, live pass, one PR.
