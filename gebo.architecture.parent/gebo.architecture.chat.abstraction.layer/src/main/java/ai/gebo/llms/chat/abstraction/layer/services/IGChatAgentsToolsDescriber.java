/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.model.AgentToolsInfo;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Tells the agents, with their tools, of the network an agentic chat is handed to.
 * Implemented by the agents layer; absent when the agents are not part of the
 * build, so the chat layer asks for it through an optional provider.
 */
public interface IGChatAgentsToolsDescriber {

	/**
	 * The agents of the network the chats of the profile are handed to, each with the
	 * tools it mounts. A null profile means a free chat, without knowledge bases.
	 *
	 * @return the agents in the network's order, empty when the agentic chats are off
	 *         or no network resolves.
	 */
	public List<AgentToolsInfo> describeAgentsTools(GChatProfileConfiguration profile);
}
