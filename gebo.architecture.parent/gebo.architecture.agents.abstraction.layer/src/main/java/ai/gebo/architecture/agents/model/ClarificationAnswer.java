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
 * The user's answer to a question: the labels of the options chosen, one for a
 * single choice, and the explanation of their own when they gave one.
 */
@Data
@NoArgsConstructor
public class ClarificationAnswer {
	private List<String> chosenOptions = new ArrayList<String>();
	private String customExplanation = null;

	public static ClarificationAnswer option(String chosenOption) {
		return options(List.of(chosenOption));
	}

	public static ClarificationAnswer options(List<String> chosenOptions) {
		final ClarificationAnswer answer = new ClarificationAnswer();
		answer.setChosenOptions(new ArrayList<String>(chosenOptions));
		return answer;
	}

	public static ClarificationAnswer explanation(String customExplanation) {
		final ClarificationAnswer answer = new ClarificationAnswer();
		answer.setCustomExplanation(customExplanation);
		return answer;
	}
}
