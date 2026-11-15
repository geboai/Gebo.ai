/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What the model chose when it decided to ask the user: the question, the
 * options it put to them, whether the user picks one option or any number of
 * them, and whether they may answer in their own words.
 */
@Data
@NoArgsConstructor
public class ClarificationOptions {
	private String question = null;
	private List<ClarificationOption> options = new ArrayList<ClarificationOption>();
	private boolean multipleChoice = false;
	private boolean customAnswerAllowed = false;

	/** The labels of the options: what an answer may name. */
	public List<String> labels() {
		final List<String> labels = new ArrayList<String>();
		for (ClarificationOption option : options) {
			labels.add(option.getLabel());
		}
		return labels;
	}
}
