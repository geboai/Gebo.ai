package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;

/**
 * Carries a condition a suspended network of agents waits for (see
 * {@link AgentSuspendedWaitingCondition}) across the chat's transport, and the
 * reaction to it back: down, the condition becomes the DTO the client reads,
 * its type, its id and the attributes this translator chooses to show; up, the
 * client's reaction fills the condition saved in the block. The block is
 * authoritative, so the way up never rebuilds a condition from the reaction: it
 * is given the saved condition, read back from the block, and fills its
 * instance value with the answer the reaction carries. One translator per kind
 * of condition, found by the repository through {@link #canTranslate} and
 * {@link #canTranslateReaction}.
 */
public interface IAgentSuspendedWaitingConditionTransportTranslator {
	public boolean canTranslate(AgentSuspendedWaitingCondition condition);

	public boolean canTranslateReaction(AgentSuspendedWaitingConditionReactionDto reaction);

	public String getId();

	public AgentSuspendedWaitingConditionDto translate(AgentSuspendedWaitingCondition condition);

	/**
	 * The saved condition resolved with the client's reaction: the same condition,
	 * its id and what the model chose untouched, with the instance value the
	 * reaction's attributes say. The reaction's condition id matches the saved
	 * condition's, the caller made sure of it.
	 */
	public AgentSuspendedWaitingCondition translateReaction(AgentSuspendedWaitingConditionReactionDto reaction,
			AgentSuspendedWaitingCondition savedCondition);
}
