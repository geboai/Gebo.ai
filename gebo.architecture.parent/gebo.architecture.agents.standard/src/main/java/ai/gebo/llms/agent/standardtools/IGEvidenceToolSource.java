/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import ai.gebo.architecture.ai.service.IGToolCallbackSource;

/**
 * A source of tools returning what the sources contain, besides the standard ones:
 * a call to one of its tools is the evidence an answer rests on, as a call to the
 * standard search tools is (see the agentic loop's source gate and coverage). A
 * product searching its knowledge bases its own way declares its tools so, the deep
 * searches and the knowledge base tools among them told by name.
 */
public interface IGEvidenceToolSource extends IGToolCallbackSource {

	/** Whether the tool of this source is a deep search (an analysis of the documents found). */
	public default boolean isDeepSearchTool(String toolName) {
		return false;
	}

	/** Whether the tool of this source reaches the internal knowledge bases. */
	public default boolean isKnowledgeBaseTool(String toolName) {
		return false;
	}
}
