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
import java.util.Set;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.deepsearch.service.impl.DeepSearchQuotations;
import reactor.core.publisher.Flux;

/**
 * Pins the answer's quotations of a deep search: its summary's words quoted as if they
 * were a document's are not a quotation; a document's words, or a verified quotation of
 * the deep search, are.
 */
class SummaryQuotationGuardTest {

	@Test
	void aSummaryQuotedAsADocumentLosesItsQuotationMarks() {
		final ToolCallsListener calls = new ToolCallsListener();
		calls.addCall("deepSearchKnowledgeBase", "deep search", "{}",
				"{\"status\":\"OK\",\"analysis\":\"Steiner describes the Guardian as a reflection of the individual's own "
						+ "inner struggles. “il Guardiano della Soglia è l'essere che si presenta all'anima” "
						+ "(scienza-occulta.pdf)\",\"quotes\":[{\"doc\":\"#1\",\"title\":\"scienza-occulta.pdf\","
						+ "\"text\":\"il Guardiano della Soglia è l'essere che si presenta all'anima\"}]}");
		calls.addCall("searchKnowledgeBase", "search", "{}",
				"{\"fragments\":[{\"content\":\"Fohat is the steed and Thought is the rider.\"}]}");
		final SummaryQuotationGuard guard = new SummaryQuotationGuard(calls, Set.of("deepSearchKnowledgeBase"));

		final String answer = "Steiner: “Steiner describes the Guardian as a reflection of the individual's own inner "
				+ "struggles”. In his words “il Guardiano della Soglia è l'essere che si presenta all'anima”; "
				+ "Blavatsky: “Fohat is the steed and Thought is the rider”; “a sentence of my own, in quotation marks”.";
		final String guarded = String.join("",
				DeepSearchQuotations.unquotingWhere(Flux.fromIterable(List.of(answer.substring(0, 40), answer.substring(40))),
						guard::keep).collectList().block());

		assertEquals("Steiner: Steiner describes the Guardian as a reflection of the individual's own inner struggles. "
				+ "In his words “il Guardiano della Soglia è l'essere che si presenta all'anima”; Blavatsky: “Fohat is the "
				+ "steed and Thought is the rider”; “a sentence of my own, in quotation marks”.", guarded,
				"only the summary's words lose their marks: the rest is not the deep search's business");
	}
}
