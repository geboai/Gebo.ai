/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

/**
 * The kinds of chat pipeline a network of agents can answer for.
 */
public enum PipelineType {
	/** A chat with a chat profile and the knowledge bases it maps. */
	RAG_PIPELINE,
	/** A free chat, without chat profile nor internal knowledge base. */
	PURE_CHAT_PIPELINE
}
