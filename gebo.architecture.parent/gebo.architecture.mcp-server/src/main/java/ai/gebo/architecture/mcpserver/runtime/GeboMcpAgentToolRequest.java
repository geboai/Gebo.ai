/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.mcpserver.runtime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

/**
 * The input of an MCP tool built from an agent or a network of agents: the
 * request, in natural language, the agent or the network answers to.
 */
@Data
public class GeboMcpAgentToolRequest {
	@JsonProperty(required = true)
	@JsonPropertyDescription("The request, task or question the agent must answer, in natural language")
	private String request;
}
