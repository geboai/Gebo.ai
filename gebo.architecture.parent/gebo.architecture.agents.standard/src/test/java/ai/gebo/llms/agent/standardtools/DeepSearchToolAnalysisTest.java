/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.standardtools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Test;

/** Pins the reading of the irrelevant fragments a partial analysis lists. */
class DeepSearchToolAnalysisTest {

	@Test
	void anIdRepeatedByTheModelIsDiscardedOnce() {
		Vector<String> discarded = new Vector<>();
		discarded.add("f0");

		String cleaned = DeepSearchToolAnalysis.cumulateDiscardedFragmentsAndCleanOutput(
				"The analysis.\nIRRILEVANT=f1, f2,f1,f1, ,f0,f2\nSATISFACTORY", discarded);

		assertEquals(List.of("f0", "f1", "f2"), discarded);
		assertEquals("The analysis.\n\nSATISFACTORY", cleaned);
	}
}
