/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline;

/**
 * Shared identifiers of the office-assistant chat pipeline and its agents
 * network. The office pipeline reuses the standard chat/agents architecture; the
 * only things that must stay unique across the reactor are these ids (pipeline
 * code, step ids, agent config codes, network code and the enabling flag).
 */
public final class OfficeAssistantConstants {

	private OfficeAssistantConstants() {
	}

	/** Property that switches the whole office-assistant module on/off. */
	public static final String OFFICE_PLUGIN_ENABLED_PROPERTY = "ai.gebo.officeplugin.enabled";

	/** Pipeline code, selectable through {@code pipelineCode=office-assistant}. */
	public static final String OFFICE_ASSISTANT_PIPELINE = "office-assistant";

	/** Input / router step ids (globally unique in the step repository). */
	public static final String OFFICE_INPUT_STEP = "office-assistant-input-step";
	public static final String OFFICE_ROUTING_STEP = "office-assistant-routing-step";
	public static final String OFFICE_NETWORK_STREAMING_STEP = "office-assistant-network-streaming-step";

	/** Router decision code surfaced on the response. */
	public static final String OFFICE_AGENTIC_ANSWER = "OFFICE_AGENTIC_ANSWER";

	/** Agents network code (also its compliance data-flow endpoint key). */
	public static final String OFFICE_ASSISTANT_AGENTS_NETWORK = "OFFICE_ASSISTANT_AGENTS_NETWORK";

	/** Network data source qualifier. */
	public static final String OFFICE_CHAT_AGENTS_NETWORK_QUALIFIER = "OFFICE_CHAT_AGENTS_NETWORK_QUALIFIER";

	/** Agent config codes (distinct from the default network's). */
	public static final String OFFICE_INPUT_ADAPTER_AGENT = "officeChatRuntimeDataQueryAdapterAgent";
	public static final String OFFICE_CONTROLLER_AGENT_CONFIG = "officeControllerAgent";
	public static final String OFFICE_REPORT_WRITER_AGENT_CONFIG = "officeReportWriterAgent";

	/**
	 * Shared-environment key under which the office input node publishes the list of
	 * {@code AdditionalContent} fragments the user is editing, so the coordinator and
	 * writer prompts can bind a dedicated placeholder.
	 */
	public static final String OFFICE_DOCUMENT_FRAGMENTS = "OFFICE_DOCUMENT_FRAGMENTS";
}
