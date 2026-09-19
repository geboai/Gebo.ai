/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline;

/**
 * Shared identifiers of the open-chat pipeline and its KB-free agents network.
 * <p>
 * The open-chat pipeline gives a "pure chat" experience: it never retrieves the
 * internal company knowledge base (no RAG, no {@code IKB_SYSTEM}), while still
 * letting the assistant search external systems and chat over uploaded / picked
 * files. It reuses the standard chat/agents architecture; the only things that must
 * stay unique across the reactor are the ids below.
 * <p>
 * See {@code docs/CHAT-PIPELINE-ROUTING-ARCHITECTURE.md}. The two default step-ids
 * re-declared here ({@link #DEFAULT_CHAT_WITH_DOCS_STREAMING},
 * {@link #DEEP_SEARCHED_SYSTEMS}) mirror package-private constants of the default
 * pipeline steps; they are part of the documented route contract and are duplicated
 * only because this is a separate module.
 */
public final class OpenChatConstants {

	private OpenChatConstants() {
	}

	/** Property that switches the whole open-chat module on/off. */
	public static final String OPEN_CHAT_ENABLED_PROPERTY = "ai.gebo.openchat.enabled";

	/** Pipeline code, selectable through {@code pipelineCode=open-chat}. */
	public static final String OPEN_CHAT_PIPELINE = "open-chat";

	/** Router / streaming step ids (globally unique in the step repository). */
	public static final String OPEN_CHAT_ROUTING_STEP = "open-chat-routing-step";
	public static final String OPEN_CHAT_NETWORK_STREAMING_STEP = "open-chat-network-streaming-step";

	/** Router decision code surfaced on the response for the agentic (network) answer. */
	public static final String OPEN_CHAT_AGENTIC_ANSWER = "OPEN_CHAT_AGENTIC_ANSWER";

	/** Agents network code (also its compliance data-flow endpoint key). */
	public static final String OPEN_CHAT_AGENTS_NETWORK = "OPEN_CHAT_AGENTS_NETWORK";

	/** Network data source qualifier. */
	public static final String OPEN_CHAT_AGENTS_NETWORK_QUALIFIER = "OPEN_CHAT_AGENTS_NETWORK_QUALIFIER";

	/** Agent config codes (distinct from the default network's). */
	public static final String OPEN_CHAT_CONTROLLER_AGENT_CONFIG = "openChatControllerAgent";
	public static final String OPEN_CHAT_ANSWER_WRITER_AGENT_CONFIG = "openChatAnswerWriterAgent";

	/**
	 * Default pipeline output step-ids this router dispatches the menu-driven routes
	 * to. The public ones are referenced from their owning class; these two are
	 * package-private there, so they are mirrored here as the documented contract.
	 */
	public static final String DEFAULT_CHAT_WITH_DOCS_STREAMING = "default-chat-with-docs-service";

	/**
	 * Shared-environment / pipeline-param key holding the deep-search data-source ids.
	 * Mirrors {@code DefaultRoutingChatPipelineStepServiceImpl.DEEP_SEARCHED_SYSTEMS}.
	 * The open-chat router guarantees this never contains {@code IKB_SYSTEM} and is
	 * never empty when a deep-search route is chosen (an empty list would make the
	 * shared deep-search handler fall back to the internal knowledge base).
	 */
	public static final String DEEP_SEARCHED_SYSTEMS = "deepSearchedSystems";
}
