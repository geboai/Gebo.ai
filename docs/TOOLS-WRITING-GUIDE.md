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
   `excluded-tool-sources` (`AgentsToolsAutoMountingConfig`). The default
   network keeps the search tool sources out of automatic mounting on purpose
   (`StandardAgentsInitialization.registerDefaultAutoMountExclusions`).
3. **Tools made for one use of a model.** `cloneWithOptions(...)` with
   `additionalTools` declares tools that are not in the repository (an agent's
   `notifyUser`), when the configuration enables their names.

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
takes the room divided by `ai.gebo.agents.standard.knowledge-base-search-room-divisor`
(3 by default, `InternalKnowledgeBaseSearchToolSource.maxResultTokens`), and so do
the search tools of the web and the other systems
(`SearchToolContentPipeline.resultTokens`).

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
