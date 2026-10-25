/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.a2aserver.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import lombok.AllArgsConstructor;

/**
 * What A2A adds to a run in a chat environment: nothing but the reading of its
 * own message.
 * <p>
 * Running a network of agents in the environment a chat gives it is not an A2A
 * matter - a timer, a call or anything else outside a chat wants the same
 * thing - so the running itself lives in
 * {@link GChatEnvironmentNetworkRunner}. This turns what arrived over A2A into
 * what that runner takes.
 */
@Service
@AllArgsConstructor
public class A2AChatEnvironmentRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(A2AChatEnvironmentRunner.class);

	private final GChatEnvironmentNetworkRunner runner;

	/** The request a chat would have built for what arrived over A2A. */
	public GeboChatRequest requestOf(String text, String chatProfileCode, String chatContextCode) {
		return runner.requestOf(text, chatProfileCode, chatContextCode);
	}

	/**
	 * Runs the exported network in the environment of the chat profile the export
	 * states, with what the caller sent attached to the request.
	 */
	public ChatEnvironmentRun run(String networkCode, GeboChatRequest request, A2AInboundResources inbound,
			INotificationSink sink) throws AgentException {
		final ChatRunAttachments attachments = inbound == null ? ChatRunAttachments.none()
				: new ChatRunAttachments(inbound.getFiles(), inbound.getKnowledgeBases(), inbound.getDocuments());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("An A2A caller runs network:" + networkCode + " with " + attachments.getFiles().size()
					+ " file(s) attached");
		}
		return runner.run(networkCode, request, attachments, sink);
	}
}
