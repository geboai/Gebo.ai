# Networks of agents: suspension and respawn

A network of agents can stop in the middle of a run because an agent needs something
from outside the run: the user's answer to a question, an external event. The run is
saved as it is, the conversation is told what is waited for, and when the answer comes
the run goes on from where it stopped. This document describes the pieces, from the
tool an agent calls to the chat UI, and the rules they follow.

The algorithm is a version of the Chandy-Lamport cut on a distributed system: the
network's delivery loop is the single coordinator, the agents of a parallel group are
waited for whole, and everything not yet delivered is frozen in its place.

## The pieces

| Layer | Class | Role |
|---|---|---|
| agents abstraction | `AgentSuspendedWaitingCondition<T, O>` | What an agent waits for: the value type `T`, the statement, the model's explanation, whether it is **mandatory**, and what the model chose (`O`). Its id is what a reaction is matched to. |
| agents abstraction | `IGNetworkOfAgentsSuspensionHandler` | The handler of one run: agents ask it to suspend (`enterSuspensionState`), the network tells it the cut. |
| agents abstraction | `GMongoNetworkOfAgentsSuspensionHandler` | The handler that consolidates the cut in memory and writes it as one block when the run is declared finished. A service without a repository hands out a never-suspending handler. |
| agents abstraction | `GNetworkOfAgentsSuspension` | The block: network copy and input, conversation and request, suspended agents (one entry each, with its turn and all its conditions), frozen deliveries, every agent's runtime, the shared state. |
| agents abstraction | `IGAgentsNetworkService.respawnNetworkExecution` | Respawns a block with the resolved conditions on a service **created for the block** by the factory. |
| agents abstraction | `IGWaitExternalCondition`, `GWaitExternalConditionSink` | The contract a tool declares a condition through; the sink the agent loads for each model call. |
| agents abstraction | `AgentSuspensionTurn` | What an agent able to wait does around its model calls in one turn: loads the sink, tells the model the answers on a respawn, asks the suspension once the calls ended. Used by the reactive adapter and by the tool-calling, task performer, routing and documents search agents. An agent only suspends when one of its tools declares a condition: in practice, when its configuration names the user questions library. A search agent is told the answers in its search command. |
| agents abstraction | `AskUserSingleChoiceQuestionTool`, `AskUserMultipleChoiceQuestionTool` | The tools of the common case: a question with one answer, or with several, among options with a label and a description, an explanation of the user's own when allowed. |
| agents standard | `UserQuestionsLibrarySource` | The library bundling those tools, "User questions and clarifications tool" (type `user-questions-and-clarifications`). It holds no resources, and its instance keeps nothing. Mounted only on the agents that name the library type among their `libraryTypeIds`: the agentic loop agents. |
| chat abstraction | `NetworkOfAgentsSuspendedState`, `AgentSuspendedWaitingConditionDto` | What the client reads: the block id and one DTO per condition. |
| chat abstraction | `NetworkOfAgentsSuspendedStateReaction`, `AgentSuspendedWaitingConditionReactionDto` | What the client sends back on the respawn. |
| chat abstraction | `IAgentSuspendedWaitingConditionTransportTranslator` | One per kind of condition: condition to DTO, reaction into the saved condition. `ClarificationConditionTransportTranslator`, `DefaultConditionTransportTranslator` (the fallback). |
| chat abstraction | `NetworkOfAgentsSuspensionTransport` | Builds the state from a run's result or a block, renders it as text. |
| chat abstraction | `GeboChatSuspendedException` | A normal interaction refused because the conversation waits for a mandatory answer. |
| standard agents | `ReactiveChatAgentsNetworkStreamingOutputChatPipelineService` | The chat's network step: carries the state on the last message. `planRun` is the hook a respawner overrides. |
| standard agents | `NetworkRespawnPlanner`, `ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService` | Plans and runs the respawn of the conversation's open block. The open chat and the office assistant have respawners of their own on the same planner. |
| REST | `GeboChatPipelinesController` | `streamRespawnAgenticChat`, `executeRespawnAgenticChat` and their default-pipeline variants; `pendingNetworkOfAgentsSuspension`; a 409 with the state through `GeboChatSuspendedAdvice`. |
| UI | `GeboAIChatSuspensionAnswerComponent` | The questions above the input; answers through the respawn; reinstated on history load and on a 409. |

## How a run suspends

1. The agent loads a `GWaitExternalConditionSink` in the tool calling of its model call
   (`GeboToolCalling.merged(own, received)`), beside what the run carries. Both the
   tool-calling agent (`GBaseToolCallingNetworkAgent`) and the adapter of the reactive
   agents (`AbstractReactiveAgentServiceNetworkAdapter`) do it for every call.
2. A mounted waiting tool, declared with `returningDirect()`, finds the contract in its
   tool context and declares the condition (`waitFor`). The loop stops at it.
3. The agent, seeing the sink filled, calls `enterSuspensionState` for each condition
   and returns no messages. The agent never calls the handler otherwise. An agent has
   one entry in the block, however many conditions it declared, for example a model
   calling two question tools in one turn. Should it ask in a further turn of a
   parallel group, that turn's message is frozen and delivered after its re-execution.
4. The network's delivery loop checks `isSuspensionState()` before every group of every
   level. What is still to be delivered is frozen with the producing agent
   (`setSuspendingMessagesNotYetDelivered`); the suspending agents' turns
   (`setSuspendedAgentTurn`), every agent's runtime (`setSuspendingAgentsRuntimeState`)
   and the shared state are kept last. `networkExecutionFinished` writes the block.
5. The result of the run is `SUSPENDED`, with the block id and the conditions. The
   chat step puts the state on the response and renders the questions as the answer
   text; the response is saved with the interaction.

## The conversation while a block is open

- A conversation holds **one open block at most**: writing a block drops the older open
  ones of the conversation. Open means neither respawned nor dropped.
- A **mandatory** condition holds the conversation: `chat` and `streamingChat` refuse a
  normal interaction with `GeboChatSuspendedException` (HTTP 409, the state in the body).
  Only the respawn with the reaction is possible, and the user's text goes into the
  reaction's own properties, never as a chat message.
- A block with no mandatory condition leaves the conversation free: a normal interaction
  drops the block and runs. The UI offers a skip for such a block.
- `pendingNetworkOfAgentsSuspension` gives the open block of a conversation, so the UI
  shows a pending question again when the conversation is opened.

## How a run respawns

1. The respawner step loads the conversation's open block, refuses a reaction naming
   another block, and resolves the reactions: each saved condition is read back from
   the block with its id (`toCondition`) and its translator fills the instance value
   from the reaction. A mandatory condition without a reaction refuses the respawn; a
   non-mandatory one stays waiting, or is declined by the reaction (`declined: true`),
   which resolves it with a null value so the agent goes on without it.
2. A network service is created from the factory for the block's network; a service
   whose agents already ran is refused.
3. The base rebuilds the session from the shared state, the caller's environment on
   top of what the block kept of it (the block keeps only texts, numbers, booleans,
   dates, enums and collections or maps of them: the runtime objects a chat puts in the
   environment are given again by the respawning step), restores every agent's
   counters and private turns, and re-executes the suspended agents whose conditions
   are resolved, each once, with the message it was executing, every answer told first
   (`ResolvedConditionText`) on that turn only (`takeResolvedConditionsOf` hands them
   over once: a later turn of the agent works on another message, and finds them in its private session, `AgentPrivateSessionContext.getSolvedConditions`, kept in the block and not counted as a turn, so no cycle of a coordinator is spent; the other agents read them in the shared session as a contribution of the asker, `ResolvedConditionText.renderForPeers`, without the notes; search agents read no shared session by design, the coordinator puts what they need in their command), followed by the notes the agent wrote to itself when it
   stopped, if any (`notesForLater` on the condition, saved in the block, never sent
   to the client and left out of the recorded tool calls by `ToolCallsListener`). An
   agent whose model reads the request from the context, as the agentic loop does, is
   given a context asking the same told text (`AgentSuspensionTurn.toldContext`): what
   the user wrote last is only the answer. An agent goes on when at least one of its conditions is
   resolved and no mandatory one is left, those left counting as declined; with a
   mandatory one left it keeps waiting for all of them. The frozen deliveries follow in
   their order, then the normal loop.
4. The run ends with an answer, or suspends again into a new block linked to the first;
   the consumed block is marked respawned.

## Writing a waiting tool

See the tools guide, section on a tool that must end the call. In short: declare the
tool with `returningDirect()`, read `IGWaitExternalCondition` from the tool context, call
`waitFor` with an `AgentSuspendedWaitingCondition`, answer the model that the work
waits. Where nothing is loaded, tell the model to decide by itself. Mount the tool
through a source that is `isMountedOnlyWhenAsked()`, and name the source on the agents
that may wait. Add a translator when the client should see the condition in a form of
its own; the default translator carries any condition by its types.

## The type of a condition and its typed attributes

The **type** of a condition is the id of the translator that shows it to the client and
reads the answers to it (`clarification`, `default`, or a custom one). It is kept with the
condition (`AgentSuspendedWaitingCondition.conditionType`, `WaitingCondition.conditionType`
in the block) when the run suspends: the suspension handler asks the
`IWaitingConditionTypeResolver` the network service factories hand it, which the chat layer
implements with the translator the transport picks. A waiting tool may set a type of its
own, which is kept as it is.

`NetworkOfAgentsSuspensionTransport.translatorOf` reads a condition with the translator of
its type, so the answer is read by the translator the question was shown with, whatever
translators are registered later and in whatever order. A condition with no type (a block
kept before the type was) or with a type no translator has any more (logged as a warning)
is read by its kind, as before: the last registered translator that takes it, else the
default one.

The attributes travel as a map, and the built-in types also travel **typed**, beside it:

| Type | Condition (`AgentSuspendedWaitingConditionDto`) | Answer (reaction, `ConditionEvent`) |
|---|---|---|
| `clarification` | `clarificationAttributes`: `ClarificationConditionAttributes` | `clarificationAttributes`: `ClarificationReactionAttributes` |
| `default` | `defaultAttributes`: `DefaultConditionAttributes` | `defaultAttributes`: `DefaultReactionAttributes` |

The condition's map is always filled, so the clients that read it keep working; the typed
attributes are made of it. An answer may come typed, with the map or without it: the typed
answer stands for the attributes it covers (`COVERED`), the map's are dropped, the others are
kept for the custom extensions. It is merged into the map where it comes in (the chat's
respawn, before the recorder; the events' `deliver`, before the event is kept; and the
translators, again, which changes nothing), so whatever reads the attributes reads it. A
custom type answers with the map only.

## Expiry, the waiting conditions panel, going on without and abandoning

A condition may stop waiting. Its **expiry** (`ConditionExpiry`: when, and whether the run
goes on then) is software's, never the model's: the waiting tool's own code may set it
(`GAbstractAskUserChoiceQuestionTool.expiryOf`, null by default), else the agent's
configuration decides (`GAgentConfig.conditionsExpiry`: `expiryMinutes`, `expiryUnblocks`),
else the platform's default (`ai.gebo.agents.conditions.defaultExpiryMinutes`, `0` and off
unless configured, and `defaultExpiryUnblocks`, `false`). The suspension handler asks the
`IWaitingConditionExpiryPolicy` the network service factories hand it, as it does for the
type, and keeps the expiry with the condition (`WaitingCondition.expiresAt`,
`expiryUnblocks`) and the earliest one on the block (`earliestExpiryAt`, indexed). The block
also says whether the run suspended **unwatched** (`unwatched`: its request said nobody
attends it, as a respawn by events, an A2A or MCP call), and why it was dropped
(`droppedReason`: `NEW_INTERACTION`, `SUSPENDED_AGAIN`, `ABANDONED`, and `droppedBy` when a
person chose it).

The sweep of the condition events tends the waiting conditions first
(`IWaitingConditionsService`, `WaitingConditionsServiceImpl`):

- an expired condition whose expiry **unblocks** is resolved with no value by an event of
  the platform's own (source system `GEBO`, `unanswered = EXPIRED`), accepted for the block
  as any event is: the dispatcher respawns the block as the owner once nothing mandatory is
  left, and the agent reads `No answer came before <moment>: go on without it, saying what
  you assumed.` (`UnansweredConditionText`);
- an expired condition whose expiry does **not** unblock keeps waiting, and the owner is told
  once, a personal system message (`WaitingConditionsNotifier`, source
  `AGENTS-WAITING-CONDITIONS`, key `expired:<block>`, warning);
- the owner of an **unwatched** run is told once it suspended (key `unwatched:<block>`,
  info); both messages are taken back when the block closes.

The **waiting conditions panel** lists the open blocks (`WaitingBlockView`, with every
condition as `WaitingConditionView`: solved and by whom, expired, its expiry) and offers two
actions, to the owner on `api/users/WaitingConditionsController` (`waitingConditions`,
`goOnWithout`, `abandon`) and to the administrators on
`api/admin/WaitingConditionsAdminController` (`allWaitingConditions`, and the same actions on
any block):

- **go on without**: the named conditions, or every one still waiting, are resolved with no
  value, mandatory or not, by events marked `unanswered = GONE_ON_WITHOUT` whose source is
  the person who chose: the agent reads `The user chose to go on without an answer` for the
  owner, `The administrator <name> (GEBO) chose ...` for an administrator; the respawn still
  runs as the owner;
- **abandon**: the block is dropped where it stands (`ABANDONED`, `droppedBy`), the
  conversation is free; refused while a respawn of the block is running.

In the UI (`gebo.ui`): the panel is `GeboAIWaitingConditionsPanelComponent`
(`gebo-ai-waiting-conditions-panel`, reusable-ui, `GeboAIWaitingConditionsPanelModule`), with
`administrator` for the administrators' view; the user's page is `GeboAIWaitingConditionsModule`,
routed by the applications at `ui/waiting-conditions` (menu entry "Agents waiting"), and the
administrators' view sits under the "Agents/Networks of agents" admin tab. The chat's suspension
form offers, per condition, "No answer: let the agents go on without it", and for the block "Go on
without the missing answers" and "Abandon the run". The agent editor sets `conditionsExpiry` in the
"Large language model & usable tools settings" tab.

From the chat, the owner asks to go on without a condition with the attribute
`goOnWithout: true` on its answer: the chat's recorder reads it for the conversation's own
user and marks the event, so a reaction asking it is always recorded. Nothing else may ask
it: a delivered event or a reaction carrying the attribute is refused by the translators
(`GO_ON_WITHOUT_NOT_HERE`), and `unanswered` is the platform's own mark, `@JsonIgnore` on the
DTOs and taken from a delivered event only on the `CHAT` channel.

A block is written whole by the run that respawns it, so the marks the sweep sets on an open
block (`expiryNotifiedAt`, `unwatchedNotifiedAt`) may be lost: every step does nothing when
done again (an event already solving the condition is kept, a message republished unchanged
keeps its revision and its dismissals), the marks only spare the repeat, and the retraction
is keyed on `unwatched` and `earliestExpiryAt`, which are written with the block.

## The additional rules slot

A prompt can forbid what a tool needs: a coordinator told that its only output is the
routing plan never calls a question tool. A library says what must be told in
`AIToolsAndResourcesLibraryType.agentRules`, and `{additionalRulesSlot}` is where a prompt
takes it. The text comes from the libraries the turn really holds
(`AgentAdditionalRules.of`), is set by `promptOfTheTurn` as each turn begins and is filled
by `createAgentTemplateParams`, taken from the turn's budget; with no rules the slot is an
empty text. A suspension does not keep it: a respawned agent reclaims its libraries and
gets it again.

The shipped agent prompts name the slot where it counts, a coordinator at the very end, its
output contract naming the call of a tool as the one way out of the plan. A saved prompt that does not name it gets it at the end of the system template
of the turn's copy, only when there are rules; what is saved stays as it is, and search
prompts take this way too. The evidence extractor of the report writer has no slot: its
calls have no tools. The rules are for the model, so the REST API leaves them out.

## Callers outside the chat

`ChatEnvironmentRun.isSuspended()` and `getSuspendedState()` tell A2A, MCP and the
harness what the run waits for; the response's text carries the questions rendered, so
a caller that only reads the answer reads the question. The text runner renders the
questions with the block id. Respawning from those callers goes through the chat
pipeline service with the reaction on the request.

## Conditions solved from outside: condition events

A condition does not have to be answered by the user in the chat. Any system can solve it
by sending an **event** that names the condition; the events layer keeps the event, matches
it to the open block, and respawns the network when the block is solved. The layer sits on
top of the chat and the agents: the block (`GNetworkOfAgentsSuspension`), the network and
the agents services are unchanged, and the respawn goes through the same chat pipeline
service as the chat's.

### The correlation id

The **condition id** (`AgentSuspendedWaitingConditionDto.id`, as `pendingNetworkOfAgentsSuspension`
or the suspended state gives it) is the correlation id, and the **session code** says which
conversation it belongs to. An event names the condition in either form, or both:

- the composed id, `<sessionCode>:<conditionId>` (`ConditionCorrelationId`, split at the last
  `:`), kept as the unique id of the event;
- the pair `sessionCode` + `conditionId`, kept as well, with a unique index.

### The event

`ConditionEvent` solves the condition as a reaction does
(`AgentSuspendedWaitingConditionReactionDto`): `conditionType` as the pending state names it
(`clarification`, `default`...) and the `attributes` its translator reads (`chosenOption`,
`value`, `declined`...), or the answer typed for the built-in types (see "The type of a
condition and its typed attributes"), kept as its attributes. Once a block waits for the condition, the values are checked with
its translator on a copy of the saved condition: an option that was not offered is
`REFUSED`, and a later event for the same condition may replace it. Optional:

- `text`, what the source says, for the conversation's history; when no event of a respawn
  carries one, the text is made from the solved conditions (`IConditionEventTextRenderer`);
- `sourceSystem`, `sourceEventId` (the same id again is a `DUPLICATE`), `sourceTimestamp`
  (of two values of the same condition the later wins, `SUPERSEDED` for the older).

The delivery answers a `ConditionEventAck`: `ACCEPTED` (matched to the open block),
`PENDING` (no block waits for it yet: the run that asks has not ended, it is matched when
the block is written), `DUPLICATE`, `SUPERSEDED`, `REFUSED` (the reason says why) or `STALE`
(already used, or its block is closed).

### The channels and who may deliver

| Channel | Way in | Caller |
|---|---|---|
| REST | `POST api/users/NetworkOfAgentsConditionEventsController/deliverConditionEvent`; `GET .../conditionEventStatus?correlationId=` (or `sessionCode` + `conditionId`) | the authenticated account: a user's personal API key, a session, an application account |
| Message broker | `IConditionEventsPublisher.publish(event, onBehalfOf)`, which sends a `ConditionEventPayload` from `ConditionEventsPublisher` to the receiver of `ConditionEventsReceiverFactory` (module `AGENTS-CONDITION-EVENTS`) | the account named, the channel trusted as infrastructure |

The session's owner may deliver to its own sessions, an account with the application
authority (`APPLICATION` by default) to any, and the broker to any: it is reached only by
code running in the platform (a workflow step, a harness, a robot), never exposed as is to
the outside, where REST with the caller's credentials is the way in. Whoever delivers, **the
respawn runs as the session's owner**. The event keeps the actor and the channel; with no
`sourceSystem` of its own, a broker event takes the sending component's
(`AGENTS-CONDITION-EVENTS.CONDITION-EVENTS-PUBLISHER`). The publisher refuses with an
`IllegalStateException` when no receiver is reachable, instead of leaving the event to the
broker's log. The delivery is asynchronous on both channels: the outcome is read with the
status.

### The source

An event may say who or what solved the condition with `source` (`ConditionSource`): its
`kind` (`PERSON`, `AGENT`, `SYSTEM`), `system`, `id`, `displayName`, the `sessionCode` of a
person or an agent in its own system, an agent's `agentCode` and `networkCode`, and any
other `details`. What it says is kept as told; `ConditionSources` decides whether it is
`verified`:

- the chat's own answers are built on the server, as the conversation's user: verified;
- a claimed source is verified when it is the delivering account itself, when it came
  through the broker, or when the caller holds the application authority; otherwise it is
  kept, marked unverified;
- an event claiming none is told by its deliverer (its account, with `sourceSystem` or the
  sending component as the system).

The `channel` is always the one it came through, and `actor` still records the delivering
account. The source travels with the reaction to the respawn (on
`AgentSuspendedWaitingConditionReactionDto.source`, never read from or written to a client)
and is saved on the condition (`solvedBy`, in the block and in an agent's solved conditions).
The agents are told who answered when it is not the conversation's user ("You asked: Which
year?\nAnna Rossi (ERP) chose: 2024", "It came from Anna Rossi (ERP), unverified: yes"); the
user's own answers, and those saved before sources, read as before. The history line of an
event names a source that says what it is, and the answer form shows "Answered (you)" or the
source.

### From the event to the respawn

1. `deliver` keeps the event (`GConditionEvent`) and matches it to the session's open block.
2. The block is respawned when its events solve it: every mandatory condition solved, as the
   respawn of the chat requires them all in one reaction. A block with no mandatory
   condition waits `optionalSettleMillis` after its first event for the others, then goes
   with what it has.
3. A respawn is claimed by inserting a `GConditionDispatch` keyed by the block code, so one
   instance runs it; the claim lapses after `dispatchLeaseMillis`. It runs
   `streamingRespawnAgenticChat` on the session's pipeline, as the owner, with the reaction
   built from the events and the text as the request.
4. The events become `DISPATCHED`, then `APPLIED` when the run ends or suspends again (a new
   block, answered by new events). A failed respawn is tried again after
   `retryBackoffMillis`, doubling, up to `maxAttempts`, then `FAILED`. An event whose block
   was respawned or dropped meanwhile becomes `STALE`; one no block ever waited for becomes
   `EXPIRED` after `pendingTimeToLiveMillis`.

The sweeper (`ConditionEventsSweeper`) expires, retries and evaluates again the sessions
holding unused events.

### Configuration

`ai.gebo.agents.conditionevents.*`: `enabled` (the sweeper; deliveries are taken either
way, default `true`), `pendingTimeToLiveMillis` (24 h), `retryBackoffMillis` (30 s),
`maxAttempts` (5), `optionalSettleMillis` (60 s), `dispatchLeaseMillis` (1 h),
`applicationAuthority` (`APPLICATION`), `sweepPeriodMillis` (30 s),
`sweepInitialDelayMillis` (60 s). The modules `gebo.architecture.agents.conditionevents`
and `gebo.architecture.agents.conditionevents.controllers` are in the monolithic starter and
in the brain. The expiry of the conditions: `ai.gebo.agents.conditions.defaultExpiryMinutes`
(`0`, off) and `defaultExpiryUnblocks` (`false`).

## Tests

`NetworkSuspensionAndRespawnTest`, `MockedAgentsNetworkRespawnTest` (the base, with
mocked agents and a parallel group), `GMongoNetworkOfAgentsSuspensionHandlerTest`,
`ResolvedConditionTextTest`,
`AskUserSingleChoiceQuestionToolTest`, `AskUserMultipleChoiceQuestionToolTest`,
`ToolCallingAgentSuspensionTest`,
`ReactiveAgentAdapterSuspensionTest`, `ConditionTransportTranslatorsTest`,
`NetworkOfAgentsSuspensionTransportTest`, `ChatPipelineServiceOpenSuspensionTest`,
`ChatNetworkStepSuspensionTest`, `ChatNetworkRespawnStepTest`,
`GChatEnvironmentNetworkRunnerTest`.

Condition events: `ConditionCorrelationIdTest`, `ConditionEventsServiceImplTest`,
`ConditionEventsPublisherTest`; the expiry and the panel: `ConditionExpiryTest`,
`ConditionExpiryKeptTest`, `WaitingConditionsNotifierTest`, `WaitingConditionsServiceImplTest`;
end to end in gebo.ai.app with the scripted model,
`ConditionEventRespawnEndToEndTest` (REST with a personal API key, the broker through
the publisher, the panel going on without and abandoning, an expiry that unblocks); with real models, `ConditionEventsWithRealLlmsIntegrationTest` in
`integration-tests/condition-events-integration-tests` (needs `FullSetupSecret`; skips when
the model never asks, unless `ai.gebo.tests.conditionevents.strictModelBehaviour=true`).
