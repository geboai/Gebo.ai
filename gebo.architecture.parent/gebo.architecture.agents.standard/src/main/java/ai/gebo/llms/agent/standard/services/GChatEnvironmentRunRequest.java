/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import java.util.List;
import java.util.Map;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import lombok.Builder;
import lombok.Getter;

/**
 * Everything a run of a network of agents in a chat environment is about, for
 * {@link IGChatEnvironment#run(GChatEnvironmentRunRequest)}: a caller says here
 * what it wants of the run, and what is not said has the default a run from
 * outside a chat wants.
 */
@Getter
@Builder(toBuilder = true)
public class GChatEnvironmentRunRequest {
	/** The network to run, resolved by the caller and adjusted for this run when it is. */
	private final GAgentsNetwork network;
	/** The request a chat would have built (see {@link IGChatEnvironment#requestOf}). */
	private final GeboChatRequest request;
	/** What the caller sends along with its question. */
	@Builder.Default
	private final ChatRunAttachments attachments = ChatRunAttachments.none();
	/** Rules this run follows on top of the conversation's own, in every model call. */
	@Builder.Default
	private final List<String> rulesOfThisRun = List.of();
	/** Values the tools of this run read from their tool context. */
	@Builder.Default
	private final Map<String, Object> toolsContextOfThisRun = Map.of();
	/** Entries the caller adds to the environment of the network, last. */
	@Builder.Default
	private final Map<String, Object> environmentOfThisRun = Map.of();
	/** Which knowledge bases the run works on. */
	@Builder.Default
	private final GKnowledgeBaseScope knowledgeBases = GKnowledgeBaseScope.PROFILE_OR_ALL_VISIBLE;
	/**
	 * Whether the request is understood as the chat pipelines understand one -
	 * rewritten against the history, its intent and search flags set - at the cost
	 * of a service-model call. Worth it for a person's question, not for a
	 * machine-written wake-up.
	 */
	private final boolean understood;
	/**
	 * Whether the conversation lives only as long as the run: opened for it, never
	 * listed, dropped when it is over. For a caller that keeps no conversation of
	 * its own, like an MCP tool.
	 */
	private final boolean transientConversation;
	/** Where the run comes from ({@code HARNESS}, {@code A2A}, {@code MCP}...), kept on the conversation. */
	private final String origin;
	/** The people who work in the conversation beside its owner, each when its turn comes. */
	@Builder.Default
	private final List<String> sharedWithUsers = List.of();
	/** The groups whose members work in the conversation beside its owner. */
	@Builder.Default
	private final List<String> sharedWithGroups = List.of();
	/** Where the run tells what it is doing, null for nowhere. */
	private final INotificationSink sink;
}
