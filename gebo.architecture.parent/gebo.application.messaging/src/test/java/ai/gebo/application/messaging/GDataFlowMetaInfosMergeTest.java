/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.application.messaging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.model.GDataFlowMetaInfos;

/**
 * Pins that the name a report is shown with survives the merge of two reports
 * published under the same messaging identity: the first one that has a name.
 */
class GDataFlowMetaInfosMergeTest {

	private static GDataFlowMetaInfos named(String description) {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setDescription(description);
		return flow;
	}

	@Test
	void theFirstNameWins() {
		assertEquals("first", GDataFlowMetaInfos.merge(named("first"), named("second")).getDescription());
	}

	@Test
	void aReportWithoutANameTakesTheOtherOne() {
		assertEquals("second", GDataFlowMetaInfos.merge(named(null), named("second")).getDescription());
		assertEquals("second", GDataFlowMetaInfos.merge(named(" "), named("second")).getDescription());
	}
}
