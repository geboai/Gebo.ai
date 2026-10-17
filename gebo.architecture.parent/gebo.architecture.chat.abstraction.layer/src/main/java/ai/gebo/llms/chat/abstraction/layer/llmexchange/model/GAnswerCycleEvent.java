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
 * An agent working in cycles starts a new one: the answer it wrote in the previous
 * cycle, shown to the user as it streamed and then given whole as a
 * {@link GeboChatResponse}, is written again, improved, by the cycle that starts. The
 * chat shows the new answer in place of the previous one as it streams: this event
 * tells it to clear the shown text before the new cycle's text arrives. Never part of
 * the answer.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GAnswerCycleEvent {
	/** The cycle that starts, from 1. */
	private int cycle = 1;
	/** The cycles the agent may run at most. */
	private int maxCycles = 1;
}
