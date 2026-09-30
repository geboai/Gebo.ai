/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import java.util.List;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.PipelineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Which network of agents a chat is handed to. The first option is the network the
 * chat profile chooses, the second the system default an administrator chose for
 * the pipeline type ({@link ai.gebo.architecture.agents.model.GAgenticChatDefaultNetworkOfAgents}),
 * the third the default of the application configuration
 * ({@link IGConfiguredDefaultChatNetworksOfAgents}). An option naming a network that
 * does not exist, or that cannot be chosen for the pipeline type, is skipped.
 * <p>
 * The whole choice exists only while the agents are enabled by {@value #AGENTS_ENABLED_PROPERTY}
 * (enabled when missing, like the agents configurations it switches on): when they
 * are off no network is choosable and the defaults cannot be changed.
 */
public interface IGAgenticChatDefaultNetworkOfAgentsService {
	/** The central switch of the agents, of the chat networks of agents with them. */
	public static final String AGENTS_ENABLED_PROPERTY = "ai.gebo.agents.standard.enabled";

	/** The defaults of a pipeline type, as the setup shows them. */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AgenticChatDefaultNetworkInfo {
		private PipelineType pipelineType = null;
		/** The default of the application configuration. */
		private String configuredDefaultChatNetworkOfAgents = null;
		/** The default an administrator chose, null when none. */
		private String defaultChatNetworkOfAgents = null;
		/** The network the chats without a profile choice are handed to. */
		private String effectiveChatNetworkOfAgents = null;
	}

	/** @return whether the agents, and so the chat networks of agents, are enabled */
	public boolean isAgenticChatNetworksEnabled();

	/**
	 * @return the networks that can be chosen for the pipeline type, none when the
	 *         agents are disabled
	 */
	public List<GAgentsNetwork> getChoosableNetworksOfAgents(PipelineType pipelineType);

	/**
	 * @return the code of the network a chat of the pipeline type is handed to, null
	 *         when no option names a usable one
	 */
	public String resolveChatNetworkOfAgents(PipelineType pipelineType, String chatProfileNetworkOfAgents);

	/**
	 * @return the network a chat of the pipeline type is handed to, null when no
	 *         option names a usable one
	 */
	public GAgentsNetwork resolveChatNetwork(PipelineType pipelineType, String chatProfileNetworkOfAgents);

	/** @return the defaults of every pipeline type, none when the agents are disabled */
	public List<AgenticChatDefaultNetworkInfo> getAgenticChatDefaultNetworks();

	/**
	 * Sets the system default of the pipeline type.
	 *
	 * @throws IllegalArgumentException when the agents are disabled, or the network
	 *                                  does not exist or cannot be chosen for the
	 *                                  pipeline type
	 */
	public AgenticChatDefaultNetworkInfo setAgenticChatDefaultNetwork(PipelineType pipelineType, String networkCode);

	/**
	 * Removes the system default of the pipeline type: the configured one applies again.
	 *
	 * @throws IllegalArgumentException when the agents are disabled
	 */
	public AgenticChatDefaultNetworkInfo resetAgenticChatDefaultNetwork(PipelineType pipelineType);
}
