/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Which types a message type accepts, as Java assignability has it among the
 * types the agent services and the network service factories exchange: a
 * message of a type in {@link #assignableFrom} can be given to an agent whose
 * input is {@link #type}. The composer takes the answer from here instead of
 * guessing the type hierarchy on the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentTypeCompatibility {
	/** Fully qualified name of the accepting type. */
	private String type;
	/** Fully qualified names of the types assignable to it, itself included. */
	private List<String> assignableFrom;
}
