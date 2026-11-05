# Writing tools for Gebo.ai

How to write a tool that Gebo.ai's models and agents can call, following the
`gebo.architecture.ai` tool architecture and the **tools room** design that
keeps every tool result inside the context of the model call that asked for it.

Everything below is how the code works today; class names are given so you can
read the real implementation next to this guide.

---

## 1. The pieces

| Piece | Where | What it is |
|---|---|---|
| `IGToolCallbackSource` | `gebo.architecture.ai` (`ai.gebo.architecture.ai.service`) | A Spring bean contributing one or more tools. |
| `ToolCallbackDeclarationUtil.declare(...)` | same | Turns a Java function into a Spring AI `ToolCallback` (name, description, JSON schema of the parameter). |
| `ToolsCategory`, `ToolReference` | `ai.gebo.architecture.ai.model` | The category a source's tools are listed under, and the name/description shown to administrators. |
| `IGToolCallbackSourceRepositoryPattern` | `gebo.architecture.ai` | Collects every `IGToolCallbackSource` bean; the models resolve their tools here by name. |
| `ToolsTokenBudget` | `gebo.architecture.ai` (`ai.gebo.architecture.ai.service`) | The **room** a model call leaves to its tools' results (section 5). |
| `RunAsToolCallback` | `gebo.architecture.llms.abstraction.layer` | The wrapper every tool runs inside: the user's identity, the tool-call recording, the room accounting. |
| `GAbstractConfigurableChatModel.prepareCall(...)` | same | Builds every model call: declares the tools, sets the room. |
| `GeboToolCalling`, `GeboToolCallingManagerFactory` | same (`...services.toolcalling`) | What the software controlling a call adds to it: tools the call must declare, interfaces the tools call back, the loop's limits (section 9). |

A tool module only needs to depend on **`gebo.architecture.ai`**: that is all the
existing tool modules depend on (standard functions, build systems, MCP clients…).

---

## 2. Declaring a tool

```java
@Service
public class WeatherToolSource implements IGToolCallbackSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherToolSource.class);

	public static class WeatherParam {
		@JsonPropertyDescription("The city, as precise as possible")
		private String city;
		// getters / setters
	}

	@Override
	public String getId() {
		return "weather-tool-source";              // unique among the sources
	}

	@Override
	public ToolsCategory getToolCategory() {
		return ToolsCategory.INTERNET_BROWSING;     // or a category of your own
	}

	@Override
	public List<ToolReference> getFullToolReferences() {
		return getToolCallbacks().stream().map(ToolReference::new).toList();
	}

	@Override
	public List<ToolCallback> getToolCallbacks() {
		BiFunction<WeatherParam, ToolContext, String> forecast = (param, toolContext) -> forecast(param, toolContext);
		return List.of(ToolCallbackDeclarationUtil.declare(forecast, "getWeatherForecast",
				"The weather forecast of a city for the next days", WeatherParam.class, String.class));
	}
}
```

Rules that come from how the code uses these methods:

- **`getToolCallbacks()` is called often.** The repository calls it on every
  source each time a model resolves its tools (every model call with tools).
  Build the callbacks cheaply; never do I/O there. Return an empty list, never
  `null` (a `null` is logged as an error and skipped).
- **The tool name is the key.** Models, agents and administrators enable a tool
  by its name (`ToolDefinition.name()`); keep it stable and unique across all
  sources. The name and the description are what the model reads to decide when
  to call the tool: write the description for the model.
- **Use the `BiFunction<P, ToolContext, R>` form.** The `ToolContext` is how the
  tool reaches the request: its id, the room (section 5), and what an agent
  shares. Spring AI 2.0.1 refuses a call only when a method *requires* a
  `ToolContext` and receives an empty one; the extra keys Gebo.ai puts in it are
  always safe.
- **Describe the parameters** with `@JsonPropertyDescription`: the generated
  JSON schema is what the model fills in. Generic parameter types are supported
  through `declare(function, name, description, Type)`.

### What the model reads

Spring AI converts the value a tool returns to **JSON**
(`DefaultToolCallResultConverter`): an object becomes its JSON, and a `String`
becomes a JSON *string* (quoted, with line ends and quotes escaped). So the text
the model reads, and the tokens it costs, is the JSON of your result, not its
Java `toString()`. Size results on that (section 6).

---

## 3. How a tool reaches a model

A tool is **never mounted by being a bean**: something has to enable it by name.

1. **A chat model's own tools.** A model configuration lists its tools in
   `enabledFunctions` (`GBaseChatModelConfig`). `prepareCall` declares them on
   every call of that model whose prompt requires tools.
2. **An agent's tools.** An agent declares its tools by name; an agent with
   `subscribeAllTools` mounts every registered tool, except the ones in
   `ai.gebo.agents.tools.auto-mounting.excluded-tools` /
   `excluded-tool-sources` (`AgentsToolsAutoMountingConfig`, system-wide) and
   the tools of the sources its own configuration leaves out
   (`GAgentConfig.excludedToolSources`). The tool-calling agent of the
   networks with search agents leaves out the search tool sources this way
   (`StandardAgentsInitialization.SEARCH_TOOL_SOURCES`): there each knowledge
   base search and each search service is an agent of its own, and the
   tool-calling agent operates the other tools (MCP tools, the basic tools).
3. **Tools made for one use of a model.** `cloneWithOptions(...)` with
   `additionalTools` declares tools that are not in the repository (an agent's
   `notifyUser`), when the configuration enables their names.
4. **Tools the controlling software brings to one call.** A call made with a
   `GeboToolCalling` (section 9) declares the container's *mandatory* tools
   whatever the configuration says, even when the prompt does not require
   tools; a configured tool of the same name gives way to the mandatory one.

Whatever the path, the tool is wrapped in **`RunAsToolCallback`**, which:

- runs it **as the user** of the request (`ReactiveIdentityUtil`), so security
  checks inside the tool see the right user;
- records the call (`ToolCallsListener`): name, input and the result **as the
  model got it**;
- takes the result out of the **room** of the model call, cutting it when it
  does not fit (section 5).

Tools coming from external MCP servers are exported the same way
(`MCPToolsExporter`) and run inside the same wrapper; they implement
`IGExternalToolCallback` so Gebo.ai's own MCP server never re-exposes them.

---

## 4. What a tool can rely on

| Need | How |
|---|---|
| The user | The tool runs as the user (`RunAsToolCallback`): use `IGSecurityService` as anywhere else. |
| The request id | `ToolCallbackDeclarationUtil.requestId(toolContext)` (key `geboRequestId`); every model call of the same user request shares it, agents' iterations included. Use it for request-scoped state (e.g. not returning the same document twice). |
| The room | `ToolsTokenBudget.from(toolContext)` (key `geboToolsTokenBudget`), section 5. |
| The chat's knowledge bases | `LLMtInteractionContextThreadLocal.Context.get()`. It is a **thread local** set by the chat services on the request thread and **not** propagated to other threads: a tool running on a reactive worker can find it `null`. Always handle `null` (fall back to what the user can see, or answer that nothing is available). |
| What the controlling software loaded for the call | `GeboToolCallingManagerFactory.control(toolContext, MyInterface.class)` (read from the `GeboToolCalling` under the key `geboToolCalling`), section 9: `null` when the call was not made with a `GeboToolCalling`, or it loaded nothing for that interface. |
| Sharing with the calling agent (agents module only) | `ToolsFoundDocuments.from(toolContext)` (documents that become the answer's sources) and `ToolsProgress.notify(toolContext, ...)` (progress shown to the user), in `gebo.architecture.agents.standard`. |

### Behaving well

- **Never throw for an expected failure.** Answer it as content the model can
  act on: an empty query, nothing found, a source that failed ("go on without
  it"), a user not allowed. The model reads the answer and moves on. What a
  thrown exception does instead (Spring AI 2.0.1,
  `DefaultToolExecutionExceptionProcessor`): a `RuntimeException` reaches the
  model as its raw message (often meaningless to it), and it bypasses
  `RunAsToolCallback`'s recording and room accounting; any other throwable
  fails the whole model call.
- **Respect the user's rights.** Filter by the user's ACL when the platform
  access policy is ACL based (see `InternalKnowledgeBaseSearchToolSource`), and
  check external sources with `IGExternalSearchSecurityService` before searching.
- **Logging.** DEBUG for calls and decisions (what was asked, what was decided,
  sizes), TRACE for contents (pages, analyses, results), WARN for what the
  administrator should see. Never log a page, a document or a result above TRACE.
- **One concern per tool.** A tool that searches and analyses and writes is
  three tools; the model composes them better than a tool does.

---

## 5. The tools room

Every tool result is appended to the messages of the model call that asked for
it, and stays there for the following tool rounds of that call. Without a limit,
one large page or analysis can push the call past the model's context window.
The **room** is the part of the context a model call leaves to its tools'
results, and every tool result is taken out of it.

```
 model context length
 ├── messages (system prompt, history, documents, question)   ┐
 ├── tool definitions (names, descriptions, JSON schemas)     ├ counted by prepareCall
 └── what is left                                             ┘
      ├── 2/3 → ROOM for the tools' results  (TOOLS_ROOM_SHARE)
      └── 1/3 → the answer, the tool calls' arguments, the model's
                messages between the tool rounds
```

### Who sets it

- **Every model call with tools** (`GAbstractConfigurableChatModel.prepareCall`,
  so every kind of call: `textResponse`, `response`, `streamResponse`,
  `streamStringResponse`, both `structuredResponse`s): when the model has a
  **known context length** (from its configuration or its model metadata),
  `withToolsRoom(...)` puts a `ToolsTokenBudget` in a copy of the tools context.
  A model with no known context length gets no room (its tools keep their own
  limits) and is reported once with a WARN.
- **Agents** can share a smaller room through the request context:
  `context.withToolsRoom(tokens)`, with `ToolsTokenBudget.leftForTools(budget,
  params)` (the agent's budget less the placeholders it renders). The agentic
  loop does it on each iteration, the tool-calling agent on each call. The model
  call then **caps** that budget to its own room (`capTo`): the smaller of the
  two applies, and it is the same object every tool of the call sees.

One budget object is shared by every tool call of a model call (across Spring
AI's tool rounds and the calls the model makes in parallel); it is thread-safe.
The next model call (the next agent iteration, another agent) gets a new one.

### Who takes from it

**Only the wrapper.** `RunAsToolCallback` calls `admit(toolName, input, result)`
after every tool:

- the result fits what is left with the call's input → it is returned whole, and
  the input and the result are taken out of the room;
- it does not fit → it is cut to what is left and marked
  (`...(truncated: about N of M tokens shown, …)`), with a WARN;
- less than 64 tokens are left → it is replaced by a message telling the model
  to answer with what it already has.

A tool **never** calls `consume(...)` itself: the wrapper counts the exact text
the model gets, once. A tool that consumed too would be counted twice.

The wrapper is a **safety net**, not the way to size a result: cutting the end
of a JSON object loses whatever was last, and leaves the model a JSON that does
not parse. A tool that returns content sizes it itself (section 6).

---

## 6. Sizing a tool's result

Decide which kind of tool you are writing.

### Small, fixed results — nothing to do

The date, the current user, a notification sent: their results are a few dozen
tokens. The wrapper accounts for them.

### Text content (a page, an analysis, a document)

1. **Check the room before doing expensive work.** When the model call has a
   room and less than `ToolsTokenBudget.MIN_USEFUL_TOKENS` (500) is left, do not
   fetch, search or analyse: answer that no room is left.

   ```java
   if (ToolsTokenBudget.noUsefulRoom(toolContext)) {
       return "No room is left in the context for more contents: answer with the contents already found.";
   }
   ```

2. **The size is never the model's choice.** A tool has no parameter for the
   size of its result (no `maxTokens`): models ask for sizes that only an ideal
   index would fill, and a general purpose installation has imperfect indexing
   and data. The size comes from the room (below), computed by the platform.

3. **Ask for what you want, get what there is.**
   `ToolsTokenBudget.grantFor(toolContext, wanted)` is `wanted` when the model
   call has no room (your own limit applies), never more than the room left
   otherwise.

4. **Prefer producing to size over cutting.** When a model writes the content
   (an analysis, a summary), ask it for a length that fits the granted tokens
   (the deep search tools turn the room into the number of words they ask for,
   `AbstractDeepSearchTool.lengthTarget`): an analysis written short keeps its
   conclusions, a cut one loses them.

5. **Fit what you return.** `ToolsTokenBudget.fitText(text, maxTokens)` returns
   the text whole, or cut and marked with its real size.

6. **Fit the result as the model reads it.** When the text travels inside an
   object, its JSON adds field names, quotes and escapes. Fit, measure the JSON,
   fit again by the overshoot:

   ```java
   static void fitInRoom(UrlCrawlResponse response, String content, int room) {
       int contentRoom = room;
       response.setContent(ToolsTokenBudget.fitText(content, contentRoom));
       for (int attempt = 0; attempt < 3; attempt++) {
           int overshoot = ITokensCountable.stringsTokensSize(JsonParser.toJson(response)) - room;
           if (overshoot <= 0) break;
           contentRoom -= overshoot;
           response.setContent(ToolsTokenBudget.fitText(content, Math.max(0, contentRoom)));
       }
   }
   ```

   (`CrawlFunctionCallbackWrapperSource`, the `readUrl` tool.)

### Lists (search hits, artifacts, users)

Cut **between whole items**, never inside one:

```java
ToolsTokenBudget budget = ToolsTokenBudget.from(toolContext);
if (budget == null) {
    return found;                                  // no room: the tool's own limits
}
List<Item> kept = ToolsTokenBudget.fitItems(found, budget.left(), JsonParser::toJson);
```

`fitItems` keeps the first items, in order, whose renderings fit together, and
logs a WARN with how many were left out. Put the most relevant items first.
(`GArtifactInformationsSearchFunctionsFactory`, `UsersFunctions`.)

### Structured results with contents inside (search results)

Fit the contents to the granted tokens, then measure the whole result's JSON and
fit the contents again by the overshoot, as `SearchToolContentPipeline` does.
Leave the descriptive fields (titles, sources, status, message) whole: they are
what lets the model cite and decide.

### Sharing the room with the other calls

`grantFor` gives a tool everything that is left. When the model is likely to
call several tools in the same round (several searches), take a **share**
instead, so the later calls are not left with nothing: the knowledge base search
takes the room divided by `ai.gebo.llms.knowledge-base-search-room-divisor`
(3 by default, `InternalKnowledgeBaseSearchToolSource.maxResultTokens`), and so do
the search tools of the web and the other systems
(`SearchToolContentPipeline.resultTokens`). The divisor and the formula are
`KnowledgeBaseSearchBudget`'s, the same that caps the knowledge base contents of the
chats with knowledge bases, of the pipelines and of the knowledge base agents of the
networks (`forContext`); `ai.gebo.agents.standard.knowledge-base-search-room-divisor`,
when set, overrides the divisor for the tools only.

### Results you cannot size

A tree, an opaque external result (MCP tools), a binary rendering: return it as
it is and let the wrapper cut it when it does not fit. Say in the tool's
description that the result can be large, so the model asks for less.

---

## 7. Checklist

- [ ] The source is a Spring bean implementing `IGToolCallbackSource`, with a
      unique `getId()` and a `ToolsCategory`.
- [ ] `getToolCallbacks()` is cheap and never returns `null`.
- [ ] Tool names are stable and unique; descriptions and parameter descriptions
      are written for the model.
- [ ] The function takes the `ToolContext`.
- [ ] Expected failures are answered as content, never thrown.
- [ ] The user's rights are checked; the thread-local chat context is treated
      as possibly `null`.
- [ ] A content tool checks `noUsefulRoom` before expensive work, sizes its work
      with `grantFor`, and fits **the JSON the model reads** (`fitText`,
      `fitItems`).
- [ ] The tool never calls `consume(...)`: the wrapper does.
- [ ] DEBUG for decisions, TRACE for contents.
- [ ] A test with a `ToolContext` carrying a `ToolsTokenBudget`: the result as
      the model reads it fits the room, and nothing expensive runs without useful
      room (`ToolsRoomTest`, `ReadUrlRoomTest`, `AbstractDeepSearchToolTest`).

---

## 8. Where the tools are today

| Tool(s) | Source | Sizing |
|---|---|---|
| `searchKnowledgeBase` | `InternalKnowledgeBaseSearchToolSource` | A share of the room (room ÷ divisor, 3 by default), no search under 500 tokens. |
| `searchWeb`, `<productId>Search`, `<productId>NativeSearch` | `WebSearchToolSource`, `StandardSearchesToolsImpl` → `SearchToolContentPipeline` | `min(maxTokens, room left)`, the whole JSON result fitted, no search under 500 tokens. |
| `deepSearchKnowledgeBase`, `deepSearchWeb`, `deepSearch<Product>` | `DeepSearchToolSource` → `AbstractDeepSearchTool` | No analysis under 500 tokens (and no deep search counted); the length asked fits the room; the analysis fitted with its sources. |
| `readUrl` | `CrawlFunctionCallbackWrapperSource` | No read under 500 tokens; the page fitted to the room as JSON; 4 KB without a room. |
| `searchSoftwareArtifact`, `getAllSoftwareArtifactsList`, `getSoftwareArtifactsDependingFrom` | `GArtifactInformationsSearchFunctionsFactory` | Whole artifacts that fit the room. |
| `getSoftwareArtifactFullDependenciesInfos` | same | A tree: the wrapper's safety net. |
| `searchCurrentUsersTeamsColleagues` | `UsersFunctions` | Whole users that fit the room. |
| `getActualUser`, the date tools, `notifyUser` | `UsersFunctions`, `ActualDateFunctions`, agents | Small results: nothing to do. |
| MCP client tools | `MCPToolsExporter` | Opaque: the wrapper's safety net. |

---

## 9. What the controlling software adds to a call: `GeboToolCalling`

The software that makes a model call (an agent, a network, the harness, a
service) can hand the call a **`GeboToolCalling`**: a per-call container built
with `GeboToolCallingManagerFactory.builder()` and passed to the overloads of
`IGConfigurableChatModel` that take it (`textResponse(..., toolCalling)`,
`response`, `answer`, `structuredResponse`, `streamAnswer`, ...). Inside a run
of agents it is required at every layer (see below); the model's plain methods
remain for a caller outside any run. It carries:

- **Mandatory tools** (`mandatoryTool(...)`): declared on the call whatever the
  model configuration says, wrapped in `RunAsToolCallback` like every other
  tool. This is how a tool bound to *this* call (an "ask the user" tool holding
  the caller's state) gets to the model.
- **Interfaces the tools call back** (`provide(MyInterface.class, impl)`): the
  controlling software's own instances, carried by the container in the tools
  context (key `geboToolCalling`). They work as **deferred callbacks**: the tool
  calls the interface, the implementation stores what it was told, the
  controlling software reads it after the call returns. A tool reaches them
  with `GeboToolCallingManagerFactory.control(toolContext, MyInterface.class)`
  (or `controlsOf(toolContext)` for `find`/`has`/`contracts()`), the same way
  whatever software is behind the call, and answers the model that the thing is
  not available here when it gets `null`.
- **Who is calling** (`caller(...)`), the **tool-call recorder** to use instead
  of the request's (`listener(...)`), a **cap on the room** (`toolsBudget(...)`,
  section 5).
- **The loop's limits**: `maxCallsPerTool`, `maxCallsOfTool`, `maxTotalToolCalls`,
  `onLimitExceeded` (`THROW` or `RETURN_ERROR_RESPONSE`). The builder starts
  from the configured defaults `gebo.tools.calling.max-calls-per-tool`,
  `gebo.tools.calling.max-total-calls`, `gebo.tools.calling.on-limit-exceeded`;
  unset, Spring AI's own apply (40 per tool, 150 per call, `THROW`).

The call then runs its **own tool calling loop** (`GeboToolCallingAdvisor`, in
place of the one the chat client registers by default) and, once it returns,
the container tells what happened: `getRounds()`, `isStoppedByTool()`,
`getStoppingTools()`, `getLastCall()`. Each call records on its own, so one
container shared by calls made at the same time is safe; the last call to end is
the one read.

### How it travels through a network of agents

The container is an **explicit, required parameter** at every layer between
the controlling software and the model, the trailing `GeboToolCalling` of one
signature with no form without it: a network given none refuses the run with an
`AgentException`, and so do the runners. The entry point of a run is the one
place that builds it: `GChatEnvironmentRunRequest.toolCallingOfThisRun` and the runners
(`GChatEnvironmentNetworkRunner.run`, `GTextAgentsNetworkRunner.runNetwork` and
`runAgent`), `IGAgentsNetworkService.executeNetwork`,
`IGNetworkAgentService.onMessage`, `IGReactiveAgentService.execute` and the
reactive agents' `createResponse`, the helpers of `BaseLLMSInvokingService`.
**The method that receives it passes it to every call it makes that takes one**:
the network to each agent, an agent to its model, the nested-network adapter to
the inner network, the reactive adapter to `execute`, the report writer to its
extraction calls. An agent chooses one of three things to pass down:

- **as it is** (the common case);
- **one of its own in its place**: nothing of the received one applies to its calls;
- **the two merged**, `GeboToolCalling.merged(mine, received)`: the mandatory
  tools of both (the agent's taking the place of a received one of the same
  name), the instances of both (the agent's found first), the agent's caller,
  budget and limits when it set them else the received ones, the agent's
  listener only.

A container's `listener` is a run-level recorder: the model chains it beside the
recorder of the request context (`ToolCallsListener.both`), so every call of the
run reaches it while each agent keeps recording its own, forwarded to the
request's recorder as before. Leave it unset unless the run wants a record of
its own.

**Every entry point loads a container.** A chat request (`chat:<request id>`,
built by the pipelines executor and the direct chat service, carried by
`ChatPipelineExecutionRuntimeData.getToolCalling()` to the network step and to
the steps calling a model), an MCP tool run (`mcp:<server url>/<tool>`), an A2A
skill run (`a2a:<exported url>/<skill>`) and a harness run (`harness:<code>`)
each name themselves as the caller and carry the platform's loop limits
(`gebo.tools.calling.*`, Spring's own when unset) and no listener. A pipeline
step passes the request's container to its helper calls
(`callLLMRepeatableFieldEntryOutput(..., toolCalling)`, `callLLMReactiveAnswer(...,
toolCalling)`); every request has one, built by its entry point. The services
behind a request pass it on the same way: the LLM
assisted retrieval and the ranker's irrelevance filter, the deep search data
sources' query extraction, the documents search agents' planners, queries and
rankings.

**A model call with nobody above it names itself.** The history consolidation
(`consolidation:<session code>`), the chat title suggestion (`title:<session
code>`), the rule proposal (`rules:<conversation code>`, `rules:check`), the RAG
threshold autotune (`autotune:<vector store>`) and the Enterprise graph analysis
(`graph-analysis:<endpoint code>`) build their own container with the factory at
their entry: every model call of the platform is then made by someone who says
who it is.

**A tool that calls a model of its own** (a deep search's analysis) has no
parameter, only its tool context: `GeboToolCallingManagerFactory.nestedFrom(toolContext, toolName)`
gives it the container it runs in, or one of its own (`tool:<tool name>`) when it
runs outside a governed call, never null, to pass to the model it calls, so what
the controlling software loaded reaches the nested call.
The deep search tools do so: `AbstractDeepSearchTool` reads it and hands it through
`DeepSearchToolAnalysis` to `DeepSearchAnalysis`, whose every model call (the partial
analyses, the folds, the final one) is made with it; the deep search answering a
chat hands the request's to the same analysis. The search tools that only rank what
they found call no chat model: there is nothing to pass.

Below the agents layer the model keeps its plain methods for a caller outside
any run (and whatever stubs or overrides them still answers); inside a run
nothing reaches them, every call carrying the run's container. A test running a
network or an agent builds one for it: `GeboToolCalling.builder().caller("test:<name>").build()`.

A tool that must **end the call** because the work waits for something from
outside the run (the user's answer to a question, an external event) is declared
with `ToolCallbackDeclarationUtil.returningDirect()` as its metadata
(`declare(function, name, description, paramType, returnType, returningDirect())`):
the loop stops at it, its result is the call's answer, and `isStoppedByTool()`
is `true`. It tells **what** the run waits for through `IGWaitExternalCondition`
(`gebo.architecture.agents.abstraction.layer`), the contract every agent loads
in the tool calling of each of its model calls (`GWaitExternalConditionSink`,
one per call): the tool declares an `AgentSuspendedWaitingCondition` (the type
of the value waited for, the statement, the model's explanation, whether it is
mandatory, and what the model chose), and the agent, once the call ended, asks
the network to suspend the run for each condition declared
(`IGNetworkOfAgentsSuspensionHandler.enterSuspensionState`). The run is saved
as a block and respawned when the conditions are resolved; the agent is then
re-executed on the same message, told the answer first (see
`ResolvedConditionText`). Every agent calling a model does this already
(`AgentSuspensionTurn`): a waiting tool only needs mounting on the agent. A
structured model call a waiting tool ended answers null: there is no result to parse.

`AskUserSingleChoiceQuestionTool` and `AskUserMultipleChoiceQuestionTool` (library
type `user-questions-and-clarifications`, mounted only on the agents naming it among
their `libraryTypeIds`) are the tools of the common case: a question the user answers with one of the
options the model chose, or with all those that apply. Each option has a label
and a description, and the user may explain in their own words when the model
allows it.
A tool ending the call is not read by the model, so their descriptions tell it
beforehand that its work stops and starts again from the same message with the
answer; what it wants to keep it writes in the optional `notesForLater`, saved on
the condition, given back with the answer and never shown to the user: the called
functions of a response leave it out. Tell the model to write the values themselves:
told only "write what you need", it notes that it has them.

A prompt with `toolsCalling: NOT_REQUIRED` offers the model no tools at all, library tools
included, whatever the agent mounts; only the tools the controlling software makes mandatory
stay. The agent editor warns of an agent in that situation. To let such an agent use its tools,
set the prompt to `REQUIRED`: a saved copy of a prompt keeps the value it was copied with.

```java
// the tool
private String waitForApproval(ApprovalParam param, ToolContext toolContext) {
    IGWaitExternalCondition wait = GeboToolCallingManagerFactory.control(toolContext,
            IGWaitExternalCondition.class);
    if (wait == null) {
        return "Waiting is not available here: decide by yourself, and say what you assumed.";
    }
    wait.waitFor(new AgentSuspendedWaitingCondition<Approval, ApprovalRequest>(Approval.class,
            param.getWhatToApprove(), param.getWhyNeeded(), null, true, ApprovalRequest.class,
            param.toRequest()));
    return "The approval was requested: the work stops here and goes on when it comes.";
}
```

Where nothing is loaded (a model call outside an agent), the tool answers the
model that waiting is not available and the loop goes on as the model decides.

The test `GeboToolCallingTest` (`gebo.architecture.llms.abstraction.layer`) shows
every piece through a real `ChatClient` loop.
