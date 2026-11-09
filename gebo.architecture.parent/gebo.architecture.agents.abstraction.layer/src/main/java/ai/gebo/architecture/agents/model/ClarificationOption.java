/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * One option of a question put to the user: the label the user chooses, which is
 * also what the answer names, and what choosing it means. The model writes it
 * as a tool parameter, so it travels with its descriptions.
 */
@Data
@NoArgsConstructor
@JsonClassDescription("One option the user may choose")
public class ClarificationOption {
	@JsonPropertyDescription("The short label the user chooses, as it will be shown")
	private String label = null;
	@JsonPropertyDescription("What choosing it means, in one sentence; leave it empty when the label says it all")
	private String description = null;

	public static ClarificationOption of(String label) {
		return of(label, null);
	}

	public static ClarificationOption of(String label, String description) {
		final ClarificationOption option = new ClarificationOption();
		option.setLabel(label);
		option.setDescription(description);
		return option;
	}
}
