/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A document a search found that gives nothing to the answer, and why: not loaded
 * (not answering in time, failed, unreadable, its site skipped), not read by the
 * analysis, or read and judged not relevant. Told to the model, never one of the
 * answer's documents.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentNotRead {
	private String title;
	/** Url or location of the document, when known. */
	private String source;
	private String reason;
}
