/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * What a deep search analysis tells besides its text, for the coverage of the deep
 * search: what the consolidation reports as missing, and the fragments the analysis
 * never read because it stopped before their batch (it judged it had enough, or ran
 * out of room). Those fragments are also among the discarded ones, but unlike the
 * fragments judged irrelevant nobody read them.
 */
public class DeepSearchAnalysisOutcome {
	private volatile String notCovered = null;
	private final Set<String> unreadFragmentIds = ConcurrentHashMap.newKeySet();

	/** What the last consolidation reports as missing; null when complete or unknown. */
	public String getNotCovered() {
		return notCovered;
	}

	public void setNotCovered(String notCovered) {
		this.notCovered = notCovered;
	}

	/** The fragments left unread by the analysis. */
	public Set<String> getUnreadFragmentIds() {
		return unreadFragmentIds;
	}
}
