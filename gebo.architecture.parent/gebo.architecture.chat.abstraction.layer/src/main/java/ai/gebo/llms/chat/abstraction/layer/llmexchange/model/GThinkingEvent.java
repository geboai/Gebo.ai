/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The reasoning a model writes before its answer, streamed to the user as it comes:
 * each event carries the reasoning written since the previous one; the last one of a
 * reasoning, {@code completed}, tells it ended (the answer follows). Never part of the
 * answer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GThinkingEvent {
	/** The reasoning written since the previous event, empty on the completing one. */
	private String text = "";
	/** Whether the reasoning ended. */
	private boolean completed = false;
}
