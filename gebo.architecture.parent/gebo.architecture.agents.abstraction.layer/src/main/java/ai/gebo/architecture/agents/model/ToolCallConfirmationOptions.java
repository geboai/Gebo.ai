/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A call to a tool an agent may run only once a person allows it, put to them as a
 * question with two options, {@value #ALLOW} and {@value #DENY}, and room for a message
 * to the model: the tool called and the arguments the model gave, as it gave them. Shown
 * and answered as any question of the agents (it is {@link ClarificationOptions}), the
 * answer a {@link ClarificationAnswer}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ToolCallConfirmationOptions extends ClarificationOptions {
	/** The option allowing the call. */
	public static final String ALLOW = "Allow";
	/** The option refusing it. */
	public static final String DENY = "Deny";

	/** The tool the model called. */
	private String toolName = null;
	/** The arguments of the call, the JSON the model wrote. */
	private String arguments = null;
}
