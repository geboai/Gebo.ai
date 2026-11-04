package ai.gebo.architecture.agents.services;

import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;

public interface IGAgentsNetworkService<InputType, OutputType> {
	String getId();

	String getDescription();

	/**
	 * Runs the network on the input, in the environment, with what the software
	 * controlling the run adds to every model call of it (see
	 * {@link GeboToolCalling}): the entry point of the run builds it and every
	 * agent of the network receives it. Required: a run with no tool calling is
	 * refused with an {@link AgentException}.
	 */
	NetworkOfAgentsExecutionResult<OutputType> executeNetwork(IChatRequestContext chatRequestContext, InputType input,
			Map<String, Object> environment, GeboToolCalling toolCalling) throws AgentException, LLMConfigException;

	/**
	 * Respawns a suspended run from the block it was saved as (see
	 * {@link GNetworkOfAgentsSuspension}) with the conditions resolved for it, each
	 * matched to the one saved by its id and carrying the value waited for. The
	 * service must be created for the block, by the factory of its network: a
	 * service whose agents already ran is refused. The collaboration goes on from the cut:
	 * the agents whose condition is resolved are re-executed with the message each
	 * was executing, what the run had not delivered is delivered, and the run ends
	 * with an output or suspends again, into a new block, when an agent asks or a
	 * condition is still unresolved. The request context is the respawning
	 * caller's, not the one of the suspended run. Required tool calling, as for
	 * {@link #executeNetwork}.
	 */
	NetworkOfAgentsExecutionResult<OutputType> respawnNetworkExecution(IChatRequestContext chatRequestContext,
			GNetworkOfAgentsSuspension suspension, List<AgentSuspendedWaitingCondition<?, ?>> resolvedConditions,
			GeboToolCalling toolCalling) throws AgentException, LLMConfigException;

	Class<OutputType> getOutputType();

	Class<InputType> getInputType();

	void dispose();
}
