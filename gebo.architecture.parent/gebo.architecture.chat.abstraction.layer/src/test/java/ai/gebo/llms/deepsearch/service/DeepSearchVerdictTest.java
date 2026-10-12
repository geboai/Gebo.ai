/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

/**
 * Pins the verdict line ending a consolidated deep search report: read, removed from
 * the report, and removed from a streamed report without holding the stream back.
 */
class DeepSearchVerdictTest {

	@Test
	void aCompleteVerdictIsReadAndRemoved() {
		DeepSearchVerdict verdict = DeepSearchVerdict.of("## Report\r\nThe answer.\r\n<DEEP-SEARCH-VERDICT complete=\"true\"/>\r\n");

		assertTrue(verdict.complete());
		assertNull(verdict.missing());
		assertEquals("## Report\r\nThe answer.", verdict.report());
	}

	@Test
	void anIncompleteVerdictSaysWhatIsMissing() {
		DeepSearchVerdict verdict = DeepSearchVerdict
				.of("The answer.\n<deep-search-verdict complete=\"false\" missing=\"prices; delivery times\" />");

		assertFalse(verdict.complete());
		assertEquals("prices; delivery times", verdict.missing());
		assertEquals("The answer.", verdict.report());
	}

	@Test
	void noVerdictIsNotComplete() {
		DeepSearchVerdict verdict = DeepSearchVerdict.of("The answer, from a customized prompt.");

		assertFalse(verdict.complete());
		assertEquals("The answer, from a customized prompt.", verdict.report());
		assertFalse(DeepSearchVerdict.of(null).complete());
	}

	@Test
	void theLastOfRepeatedVerdictsCountsAndAllAreRemoved() {
		DeepSearchVerdict verdict = DeepSearchVerdict.of(
				"A <DEEP-SEARCH-VERDICT complete=\"true\"/> B\n<DEEP-SEARCH-VERDICT complete=\"false\" missing=\"x\"/>");

		assertFalse(verdict.complete());
		assertEquals("A  B", verdict.report());
	}

	@Test
	void aStreamedReportLosesItsVerdictEvenSplitAcrossChunks() {
		List<String> chunks = List.of("The answer <b>in bold</b>", " goes on.\n<DEEP-", "SEARCH-VER",
				"DICT complete=\"tr", "ue\"/>", "\n");
		List<String> out = new ArrayList<>();

		DeepSearchVerdict.withoutVerdict(Flux.fromIterable(chunks)).doOnNext(out::add).blockLast();

		String streamed = String.join("", out);
		assertEquals("The answer <b>in bold</b> goes on.\n", streamed.replace("\n\n", "\n"));
		assertEquals("The answer <b>in bold</b>", out.get(0), "the text before a possible verdict is not held back");
	}

	@Test
	void aStreamedReportWithoutVerdictIsPassedWhole() {
		List<String> out = new ArrayList<>();

		DeepSearchVerdict.withoutVerdict(Flux.just("a < b", " and c", " ends with <")).doOnNext(out::add).blockLast();

		assertEquals("a < b and c ends with <", String.join("", out));
	}
}
