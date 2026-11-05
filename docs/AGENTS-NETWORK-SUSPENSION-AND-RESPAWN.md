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
| agents abstraction | `GNetworkOfAgentsSuspension` | The block: network copy and input, conversation and request, suspended agents with their turn and condition, frozen deliveries, every agent's runtime, the shared state. |
| agents abstraction | `IGAgentsNetworkService.respawnNetworkExecution` | Respawns a block with the resolved conditions on a service **created for the block** by the factory. |
| agents abstraction | `IGWaitExternalCondition`, `GWaitExternalConditionSink` | The contract a tool declares a condition through; the sink the agent loads for each model call. |
| agents abstraction | `AskUserClarificationTool`, `AskUserClarificationToolSource` | The tool of the common case: a single choice among options, an explanation of the user's own when allowed. Mounted only on the agents that name its source. |
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
   and returns no messages. The agent never calls the handler otherwise.
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
3. The base rebuilds the session from the shared state, restores every agent's
   counters and private turns, and re-executes the suspended agents whose condition is
   resolved with the message each was executing, the answer told first
   (`ResolvedConditionText`). The frozen deliveries follow in their order, then the
   normal loop.
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
`AskUserClarificationToolTest`, `ToolCallingAgentSuspensionTest`,
`ReactiveAgentAdapterSuspensionTest`, `ConditionTransportTranslatorsTest`,
`NetworkOfAgentsSuspensionTransportTest`, `ChatPipelineServiceOpenSuspensionTest`,
`ChatNetworkStepSuspensionTest`, `ChatNetworkRespawnStepTest`,
`GChatEnvironmentNetworkRunnerTest`.
