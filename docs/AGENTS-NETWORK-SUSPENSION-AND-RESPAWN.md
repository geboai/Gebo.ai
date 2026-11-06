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

## Callers outside the chat

`ChatEnvironmentRun.isSuspended()` and `getSuspendedState()` tell A2A, MCP and the
harness what the run waits for; the response's text carries the questions rendered, so
a caller that only reads the answer reads the question. The text runner renders the
questions with the block id. Respawning from those callers goes through the chat
pipeline service with the reaction on the request.

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
