/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.llms.agent.chat.service.impl.ReactiveChatAgentsNetworkStreamingOutputChatPipelineService.RunPlan;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.IAgentSuspendedWaitingConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;

/**
 * Plans the respawn of the suspended network of agents of a conversation, for
 * any pipeline's respawner step: the open block of the conversation is loaded,
 * the reaction the request carries (see
 * {@link GeboChatRequest#getNetworkOfAgentsSuspendedStateReaction()}) resolves
 * the saved conditions through their translators, every mandatory one answered
 * or the respawn refused, and the plan is the block's network respawned with
 * them instead of run. A pipeline with an environment of its own (the office
 * assistant, the open chat) plans its respawn through this and keeps its
 * environment, since the plan says only which network and how it is launched.
 */
@Service
public class NetworkRespawnPlanner {
	static final String NOTHING_TO_RESPAWN = "The conversation has no suspended network of agents to respawn";
	static final String ANOTHER_BLOCK = "The reaction is to another suspension than the one the conversation waits for: ";
	static final String MANDATORY_UNANSWERED = "A mandatory condition of the suspended network has no reaction: ";
	static final String REACTION_REFUSED = "The reaction to a condition of the suspended network is refused: ";
	static final String NO_TRANSLATOR = "No translator reads the reaction to condition: ";
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkRespawnPlanner.class);
	private final GNetworkOfAgentsSuspensionRepository suspensions;
	private final NetworkOfAgentsSuspensionTransport suspensionTransport;

	public NetworkRespawnPlanner(@Autowired(required = false) GNetworkOfAgentsSuspensionRepository suspensions,
			@Autowired(required = false) NetworkOfAgentsSuspensionTransport suspensionTransport) {
		this.suspensions = suspensions;
		this.suspensionTransport = suspensionTransport != null ? suspensionTransport
				: NetworkOfAgentsSuspensionTransport.plain();
	}

	/** The plan of the respawn: the block's network, respawned with the resolved conditions. */
	public RunPlan planRespawn(ChatPipelineExecutionRuntimeData runtimeData) throws ChatPipelineException {
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		final GNetworkOfAgentsSuspension block = openBlockOf(request);
		final List<AgentSuspendedWaitingCondition<?, ?>> resolved = resolve(block,
				request != null ? request.getNetworkOfAgentsSuspendedStateReaction() : null);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Respawning suspension:" + block.getCode() + " of network:" + block.getNetworkCode()
					+ " in conversation:" + (request != null ? request.getUserChatContextCode() : null) + " with "
					+ resolved.size() + " resolved condition(s)");
		}
		return new RunPlan(block.getNetwork(), (runtimeNetwork, data, question, environment) -> runtimeNetwork
				.respawnNetworkExecution(data.getRequestResources().createChatRequestContext(), block, resolved,
						environment, data.getToolCalling()));
	}

	/** The open block of the request's conversation, the one its reaction names when it names one. */
	public GNetworkOfAgentsSuspension openBlockOf(GeboChatRequest request) throws ChatPipelineException {
		final String conversation = request != null ? request.getUserChatContextCode() : null;
		final Optional<GNetworkOfAgentsSuspension> open = suspensions != null && conversation != null
				? suspensions.findOpenByConversationCode(conversation)
				: Optional.empty();
		if (open.isEmpty() || open.get().getNetwork() == null) {
			throw new ChatPipelineException(NOTHING_TO_RESPAWN + (conversation != null ? ": " + conversation : ""));
		}
		final NetworkOfAgentsSuspendedStateReaction reaction = request.getNetworkOfAgentsSuspendedStateReaction();
		if (reaction != null && reaction.getNetworkOfAgentsBlockId() != null
				&& !reaction.getNetworkOfAgentsBlockId().equals(open.get().getCode())) {
			throw new ChatPipelineException(ANOTHER_BLOCK + reaction.getNetworkOfAgentsBlockId() + ", open: "
					+ open.get().getCode());
		}
		return open.get();
	}

	/**
	 * The saved conditions the reaction answers, each read back from the block and
	 * resolved by its translator; a mandatory condition without a reaction refuses
	 * the respawn, one that is not mandatory stays waiting.
	 */
	public List<AgentSuspendedWaitingCondition<?, ?>> resolve(GNetworkOfAgentsSuspension block,
			NetworkOfAgentsSuspendedStateReaction reaction) throws ChatPipelineException {
		final List<AgentSuspendedWaitingCondition<?, ?>> resolved = new ArrayList<AgentSuspendedWaitingCondition<?, ?>>();
		for (WaitingCondition waiting : block.conditions()) {
			final AgentSuspendedWaitingCondition<?, ?> saved = waiting.toCondition();
			final AgentSuspendedWaitingConditionReactionDto answer = reactionTo(reaction, saved.getSuspendedConditionId());
			if (answer == null) {
				if (saved.isMandatory()) {
					throw new ChatPipelineException(MANDATORY_UNANSWERED + saved.getWaitingConditionStatement());
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Condition:" + saved.getSuspendedConditionId() + " has no reaction and is not mandatory:"
							+ " it stays waiting");
				}
				continue;
			}
			final IAgentSuspendedWaitingConditionTransportTranslator translator = suspensionTransport.translatorOf(saved);
			if (!translator.canTranslateReaction(answer)) {
				throw new ChatPipelineException(NO_TRANSLATOR + saved.getSuspendedConditionId() + " of type "
						+ answer.getConditionType());
			}
			try {
				resolved.add(translator.translateReaction(answer, saved));
			} catch (IllegalArgumentException refused) {
				throw new ChatPipelineException(REACTION_REFUSED + refused.getMessage(), refused);
			}
		}
		return resolved;
	}

	private static AgentSuspendedWaitingConditionReactionDto reactionTo(NetworkOfAgentsSuspendedStateReaction reaction,
			String conditionId) {
		if (reaction == null || reaction.getReactions() == null || conditionId == null) {
			return null;
		}
		for (AgentSuspendedWaitingConditionReactionDto candidate : reaction.getReactions()) {
			if (candidate != null && conditionId.equals(candidate.getConditionId())) {
				return candidate;
			}
		}
		return null;
	}
}
