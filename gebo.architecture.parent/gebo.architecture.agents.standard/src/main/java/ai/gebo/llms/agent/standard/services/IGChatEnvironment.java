/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;

/**
 * The one road a network of agents is run on from outside a chat: in a
 * conversation, with a current request, as a chat would run it. A harness, an
 * A2A skill, an MCP tool and a content robot all come here, so that a network
 * is given the same context wherever it is run from (see
 * {@link GChatEnvironmentNetworkRunner}, which is what this is).
 */
public interface IGChatEnvironment {

	/**
	 * The request a chat would have built for a run.
	 *
	 * @param text            what the caller asked
	 * @param chatProfileCode the profile to work with, or null for none
	 * @param chatContextCode the conversation to go on with, or null for a new one
	 */
	public GeboChatRequest requestOf(String text, String chatProfileCode, String chatContextCode);

	/**
	 * Runs the network as the identity in the current security context, which a
	 * caller with nobody behind it must have taken on before calling.
	 *
	 * @throws GChatEnvironmentRunException when the run fails, naming the
	 *                                      conversation it failed in
	 * @throws AgentException               when the run cannot be prepared at all
	 */
	public ChatEnvironmentRun run(GChatEnvironmentRunRequest run) throws AgentException;
}
