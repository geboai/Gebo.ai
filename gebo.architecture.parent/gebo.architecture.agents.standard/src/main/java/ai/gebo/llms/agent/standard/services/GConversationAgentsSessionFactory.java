/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsSessionCreationContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.IAgentsCollaborationSessionContextFactory;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemory;
import ai.gebo.llms.agent.standard.memory.GConversationNetworkMemoryRepository;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionRemovalListener;

/**
 * Gives a network that asks for it ({@link GAgentsNetwork#getRememberAcrossTurns()})
 * a memory of the conversation it runs in: when a run ends, what its agents
 * contributed to the shared context is digested and kept for the conversation
 * and the network; when the next run starts in the same conversation, the
 * digest is the first contribution of its shared context, so the agents read
 * it as they read each other. The conversation keeps the history of what was
 * said; this keeps what the agents worked out to say it.
 */
@Service
public class GConversationAgentsSessionFactory
		implements IAgentsCollaborationSessionContextFactory, IGChatSessionRemovalListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(GConversationAgentsSessionFactory.class);
	/** The name the digest is contributed under. */
	static final String MEMORY_AGENT_NAME = "the network's memory";
	/** How many of the last contributions of a run are digested. */
	static final int CONTRIBUTIONS_KEPT = 20;
	/** The longest a contribution is kept, and the longest the whole digest is. */
	static final int CONTRIBUTION_CHARS = 2_000;
	static final int DIGEST_CHARS = 12_000;

	private final GConversationNetworkMemoryRepository memories;

	public GConversationAgentsSessionFactory(GConversationNetworkMemoryRepository memories) {
		this.memories = memories;
	}

	/** Whether this network asked to remember across the turns of a conversation. */
	public static boolean remembers(GAgentsNetwork network) {
		return network != null && Boolean.TRUE.equals(network.getRememberAcrossTurns());
	}

	@Override
	public AgentsCollaborationSessionContext createSessionContext(AgentsSessionCreationContext creation) {
		final String conversation = conversationOf(creation);
		final String networkCode = creation != null && creation.getNetwork() != null ? creation.getNetwork().getCode()
				: null;
		if (conversation == null || networkCode == null) {
			// nothing to remember by: the network builds its own session
			return null;
		}
		final List<AgentProducedSessionContribution> prior = new ArrayList<AgentProducedSessionContribution>();
		try {
			final Optional<GConversationNetworkMemory> remembered = memories
					.findByConversationCodeAndNetworkCode(conversation, networkCode);
			if (remembered.isPresent() && remembered.get().getDigest() != null
					&& !remembered.get().getDigest().isBlank()) {
				prior.add(new AgentProducedSessionContribution<String>(0, MEMORY_AGENT_NAME,
						"What this network of agents worked out earlier in this conversation, the most recent last:\n"
								+ remembered.get().getDigest()));
			}
		} catch (RuntimeException notRead) {
			LOGGER.error("The memory of network " + networkCode + " in conversation " + conversation
					+ " cannot be read: the run starts without it", notRead);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network:" + networkCode + " runs in conversation:" + conversation
					+ (prior.isEmpty() ? " with nothing remembered yet" : " remembering its earlier runs"));
		}
		return new AgentsCollaborationSessionContext(UUID.randomUUID().toString(), prior, 0);
	}

	@Override
	public void sessionCompleted(AgentsCollaborationSessionContext session, AgentsSessionCreationContext creation,
			Object output, Throwable failure) {
		final String conversation = conversationOf(creation);
		final String networkCode = creation != null && creation.getNetwork() != null ? creation.getNetwork().getCode()
				: null;
		if (session == null || conversation == null || networkCode == null) {
			return;
		}
		final String digest = digestOf(session);
		if (digest.isBlank()) {
			return;
		}
		final GConversationNetworkMemory memory = memories
				.findByConversationCodeAndNetworkCode(conversation, networkCode).orElseGet(() -> {
					GConversationNetworkMemory fresh = new GConversationNetworkMemory();
					fresh.setCode(UUID.randomUUID().toString());
					fresh.setConversationCode(conversation);
					fresh.setNetworkCode(networkCode);
					fresh.setDateCreated(new Date());
					return fresh;
				});
		memory.setDigest(digest);
		memory.setRuns(memory.getRuns() + 1);
		memory.setUpdatedAt(new Date());
		memory.setDateModified(memory.getUpdatedAt());
		memories.save(memory);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Network:" + networkCode + " remembers " + digest.length() + " character(s) of run "
					+ memory.getRuns() + " in conversation:" + conversation);
		}
	}

	@Override
	public void chatSessionRemoved(String code) {
		final long dropped = memories.deleteByConversationCode(code);
		if (dropped > 0) {
			LOGGER.debug("Dropped what {} network(s) remembered of conversation {}", dropped, code);
		}
	}

	private static String conversationOf(AgentsSessionCreationContext creation) {
		final String code = creation != null && creation.getChatRequestContext() != null
				? creation.getChatRequestContext().getSessionID()
				: null;
		return code != null && !code.isBlank() ? code : null;
	}

	/**
	 * The last contributions of the run, each cut to size, the whole cut to size
	 * from the start so that the most recent are kept; the memory of an earlier run
	 * is not digested again.
	 */
	static String digestOf(AgentsCollaborationSessionContext session) {
		final List<AgentProducedSessionContribution> all = session.getSampledContributions();
		final List<String> lines = new ArrayList<String>();
		final int from = Math.max(0, all.size() - CONTRIBUTIONS_KEPT);
		for (int i = from; i < all.size(); i++) {
			final AgentProducedSessionContribution contribution = all.get(i);
			if (contribution == null || MEMORY_AGENT_NAME.equals(contribution.getAgentName())) {
				continue;
			}
			final String text = textOf(contribution.getData());
			if (text == null || text.isBlank()) {
				continue;
			}
			final String cut = text.length() > CONTRIBUTION_CHARS ? text.substring(0, CONTRIBUTION_CHARS) + "..."
					: text;
			lines.add(contribution.getAgentName() + ": " + cut.strip());
		}
		StringBuilder digest = new StringBuilder();
		for (int i = lines.size() - 1; i >= 0; i--) {
			final String line = lines.get(i);
			if (digest.length() + line.length() + 1 > DIGEST_CHARS) {
				break;
			}
			digest.insert(0, line + "\n");
		}
		return digest.toString().strip();
	}

	static String textOf(Object data) {
		if (data instanceof String text) {
			return text;
		}
		if (data instanceof GeboChatResponse response) {
			return response.getQueryResponse();
		}
		return data != null ? String.valueOf(data) : null;
	}
}
