/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.Map;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;

/**
 * The request a network of agents runs on, reached from what every agent is
 * given: the session of the run, whose environment carries the
 * {@link ChatPipelineExecutionRuntimeData} under
 * {@link StandardAgentsNetworkEnvironmentEntries#CHAT_RUNTIME_DATA}. The
 * network's input is the question it is asked; the request it is asked in is
 * its environment, and an agent that needs more of it than its text - the
 * resources, the response being filled, the conversation - finds it here.
 */
public final class ChatRuntimeDataOf {
	private ChatRuntimeDataOf() {
	}

	/** The runtime data of the run the session belongs to, null outside a chat environment. */
	public static ChatPipelineExecutionRuntimeData session(AgentsCollaborationSessionContext session) {
		return session != null ? environment(session.getEnvironment()) : null;
	}

	/** The runtime data an environment carries, null when it carries none. */
	public static ChatPipelineExecutionRuntimeData environment(Map<String, Object> environment) {
		final Object carried = environment != null
				? environment.get(StandardAgentsNetworkEnvironmentEntries.CHAT_RUNTIME_DATA)
				: null;
		return carried instanceof ChatPipelineExecutionRuntimeData runtimeData ? runtimeData : null;
	}

	/** The question the request asks, as the network is asked it: the rewritten query when there is one. */
	public static String query(ChatPipelineExecutionRuntimeData runtimeData) {
		final GeboChatRequest request = runtimeData != null && runtimeData.getRequestResources() != null
				? runtimeData.getRequestResources().getCurrentRequest()
				: runtimeData != null && runtimeData.getMinimalChatContext() != null
						? runtimeData.getMinimalChatContext().getCurrentRequest()
						: null;
		final String query = request != null ? GeboChatRequest.actualQuery(request) : null;
		return query != null ? query : "";
	}
}
