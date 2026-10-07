/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.model.DocumentMetaInfos;
import reactor.core.publisher.Flux;

/**
 * Pins the deep search quotations: the partial analyses' quotations checked against
 * their fragments, only verified ones rendered as quotations, the standard way, with
 * no fragment id; a broken pattern gives fewer quotations, never an invented one.
 */
class DeepSearchQuotationsTest {

	private static final String STEINER = "Il Guardiano della Soglia è l'essere che si presenta all'anima "
			+ "quando essa varca il confine del mondo spirituale.";

	private static Document fragment(String id, String text, String fileName) {
		final Map<String, Object> metadata = new HashMap<>();
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, fileName);
		metadata.put(DocumentMetaInfos.CONTENT_CODE, "kb/" + fileName);
		return Document.builder().id(id).text(text).metadata(metadata).build();
	}

	private static DeepSearchBatchTrace.NumberedBatch batch() {
		return DeepSearchBatchTrace.numbered(List.of(fragment("uuid-a", STEINER, "scienza-occulta.pdf"),
				fragment("uuid-b", "Fohat is the steed and Thought is the rider.", "secret-doctrine.pdf")));
	}

	@Test
	void aQuotationItsFragmentHasIsKeptAndRenderedTheStandardWay() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		final String partial = quotations.keepVerified(
				"Steiner: ⟦q:1|il Guardiano della Soglia è l'essere che si presenta all'anima⟧.", batch());

		assertEquals("Steiner: ⟦Q1|il Guardiano della Soglia è l'essere che si presenta all'anima⟧.", partial);
		assertEquals(1, quotations.quotes().size());
		assertEquals("uuid-a", quotations.quotes().get(0).fragmentId(), "the batch number is resolved to the fragment");
		// the consolidation may alter the words: the checked ones are given
		assertEquals("Steiner: “il Guardiano della Soglia è l'essere che si presenta all'anima” (scienza-occulta.pdf).",
				quotations.render("Steiner: ⟦Q1|il Guardiano della soglia, essere⟧."));
	}

	@Test
	void aQuotationItsFragmentDoesNotHaveIsPlainText() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();

		assertEquals("Steiner: il Guardiano rappresenta le paure dell'individuo.",
				quotations.keepVerified("Steiner: ⟦q:1|il Guardiano rappresenta le paure dell'individuo⟧.", batch()));
		assertEquals("Blavatsky: Fohat rides the steed.",
				quotations.keepVerified("Blavatsky: ⟦q:9|Fohat rides the steed⟧.", batch()),
				"a fragment number made up, words no fragment has");
		assertTrue(quotations.quotes().isEmpty());
	}

	@Test
	void aQuotationWrittenForAnotherFragmentOfTheBatchIsKeptWithItsRealFragment() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();

		assertEquals("Blavatsky: ⟦Q1|Fohat is the steed⟧.",
				quotations.keepVerified("Blavatsky: ⟦q:1|Fohat is the steed⟧.", batch()),
				"the number of another fragment: the fragment that has the words");
		assertEquals("uuid-b", quotations.quotes().get(0).fragmentId());
		assertEquals("secret-doctrine.pdf", quotations.quotes().get(0).title());
		assertEquals("Blavatsky: ⟦Q2|Fohat is the steed⟧.",
				quotations.keepVerified("Blavatsky: ⟦q:9|Fohat is the steed⟧.", batch()),
				"a number made up: the fragment that has the words");
	}

	@Test
	void piecesJoinedByAnEllipsisAndAccentsDoNotMatter() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.keepVerified("⟦q:1|Il Guardiano della Soglia … varca il confine del mondo spirituale⟧", batch());
		quotations.keepVerified("⟦q:1|il guardiano della soglia e l'essere⟧", batch());
		quotations.keepVerified("⟦q:1|varca il confine … Il Guardiano della Soglia⟧", batch());

		assertEquals(2, quotations.quotes().size(), "pieces out of order are not the fragment's words");
	}

	@Test
	void quotationMarksAroundWordsNoFragmentHasAreTakenAway() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		final String partial = quotations.keepVerified("Steiner writes “il Guardiano rappresenta le paure dell'individuo” "
				+ "and “il Guardiano della Soglia è l'essere che si presenta all'anima”, a “short term”.", batch());

		assertEquals("Steiner writes il Guardiano rappresenta le paure dell'individuo and “il Guardiano della Soglia è "
				+ "l'essere che si presenta all'anima”, a “short term”.", partial, "a short quoted term is not a quotation");
	}

	@Test
	void whateverTheModelBrokeIsCleanedUp() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.keepVerified("⟦q:2|Fohat is the steed and Thought is the rider⟧", batch());

		assertEquals("Fohat is the steed and Thought is the rider (secret-doctrine.pdf)? no: made up, gone.",
				quotations.render("⟦Q7|Fohat is the steed and Thought is the rider⟧ (secret-doctrine.pdf)? no: made up, gone."),
				"an unknown key keeps its words, without quotation marks");
		assertEquals("left over words and a dangling one", quotations.render("left over ⟦q:3|words⟧ and a dangling ⟦one"));
	}

	@Test
	void theStreamedRenderingHoldsAQuotationUntilItCloses() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.keepVerified("⟦q:2|Fohat is the steed and Thought is the rider⟧", batch());
		final String whole = "Blavatsky: ⟦Q1|Fohat is the steed and Thought is the rider⟧. Then “an invented sentence "
				+ "nobody ever wrote down”.\nEnd.";
		final List<String> pieces = List.of("Blavatsky: ⟦Q", "1|Fohat is the steed and ", "Thought is the rider⟧. Then “an ",
				"invented sentence nobody ever wrote down”.\nEnd.");

		final String streamed = String.join("", Flux.fromIterable(pieces).transform(quotations::render).collectList().block());

		assertEquals(quotations.render(whole), streamed);
		assertTrue(streamed.startsWith("Blavatsky: “Fohat is the steed and Thought is the rider” (secret-doctrine.pdf)."));
		assertFalse(streamed.contains("“an invented"), "an invented quotation loses its marks");
	}

	@Test
	void aDocumentReadDirectlyMayBeQuoted() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.addSources(List.of(fragment("uuid-c", STEINER, "scienza-occulta.pdf")));

		assertEquals("“il Guardiano della Soglia è l'essere che si presenta all'anima”",
				quotations.render("“il Guardiano della Soglia è l'essere che si presenta all'anima”"));
	}

	@Test
	void anAnswerQuotesTheDocumentsItReadsByTheirFragmentId() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.addSources(List.of(fragment("kb/scienza-occulta.pdf#12", STEINER, "scienza-occulta.pdf"),
				fragment("kb/secret-doctrine.pdf#3", "Fohat is the steed and Thought is the rider.", "secret-doctrine.pdf")));

		assertEquals("Steiner: “il Guardiano della Soglia è l'essere che si presenta all'anima” (scienza-occulta.pdf).",
				quotations.render("Steiner: ⟦q:kb/scienza-occulta.pdf#12|il Guardiano della Soglia è l'essere che si "
						+ "presenta all'anima⟧."));
		assertEquals("Blavatsky: “Fohat is the steed” (secret-doctrine.pdf).",
				quotations.render("Blavatsky: ⟦q:kb/scienza-occulta.pdf#12|Fohat is the steed⟧."),
				"a miscopied id: the document that has the words is named");
		assertEquals("Steiner: il Guardiano rappresenta le paure dell'individuo.",
				quotations.render("Steiner: ⟦q:kb/scienza-occulta.pdf#12|il Guardiano rappresenta le paure dell'individuo⟧."),
				"words no document has are plain text");
		assertEquals(2, quotations.quotes().size());
		assertEquals("kb/secret-doctrine.pdf#3", quotations.quotes().get(1).fragmentId());
	}

	@Test
	void codeIsNeverChanged() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.addSources(List.of(fragment("uuid-c", STEINER, "scienza-occulta.pdf")));
		final String answer = "Set it with `config.put(\"a long key nobody ever wrote down\")`, then:\n"
				+ "```json\n{\"description\": \"a long value no document has ever written\"}\n```\n"
				+ "and “a long sentence no document has ever written”.";

		assertEquals("Set it with `config.put(\"a long key nobody ever wrote down\")`, then:\n"
				+ "```json\n{\"description\": \"a long value no document has ever written\"}\n```\n"
				+ "and a long sentence no document has ever written.", quotations.render(answer));
	}

	@Test
	void theStreamedRenderingGivesCodeAsItArrives() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.addSources(List.of(fragment("uuid-c", STEINER, "scienza-occulta.pdf")));
		final String whole = "Code:\n```java\nString s = \"a long string literal nobody ever wrote\";\n```\nSteiner: "
				+ "⟦q:uuid-c|il Guardiano della Soglia è l'essere⟧, not “a long sentence no document has ever written”.";
		final List<String> pieces = List.of("Code:\n`", "``java\nString s = \"a long string ", "literal nobody ever wrote\";\n`",
				"``\nSteiner: ⟦q:uu", "id-c|il Guardiano della Soglia è l'essere⟧, not “a long sentence no ",
				"document has ever written”.");
		final DeepSearchQuotations.Streaming streaming = quotations.streaming();
		final StringBuilder streamed = new StringBuilder();
		for (String piece : pieces) {
			streamed.append(streaming.next(piece));
		}
		streamed.append(streaming.rest());

		assertEquals("Code:\n```java\nString s = \"a long string literal nobody ever wrote\";\n```\nSteiner: “il "
				+ "Guardiano della Soglia è l'essere” (scienza-occulta.pdf), not a long sentence no document has ever "
				+ "written.", streamed.toString());
		assertEquals(streamed.toString(), new DeepSearchQuotations() {
			{
				addSources(List.of(fragment("uuid-c", STEINER, "scienza-occulta.pdf")));
			}
		}.render(whole), "streamed or whole, the same text");
	}

	@Test
	void anAnswerWithNoSourceKeepsItsQuotationMarks() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();

		assertEquals("Hamlet: “To be, or not to be, that is the question”.",
				quotations.render("Hamlet: “To be, or not to be, that is the question”."));
	}

	@Test
	void theToolsResultsMayBeQuoted() {
		final DeepSearchQuotations quotations = new DeepSearchQuotations();
		quotations.addSources(List.of(fragment("uuid-c", STEINER, "scienza-occulta.pdf")));
		final ToolCallsListener calls = new ToolCallsListener();
		quotations.addToolResults(calls);
		calls.addCall("searchWeb", "web search", "{}",
				"{\"results\":[{\"snippet\":\"Fohat is the steed and Thought is the rider.\"}]}");

		assertEquals("Blavatsky: “Fohat is the steed and Thought is the rider”, not an invented sentence of the model.",
				quotations.render("Blavatsky: “Fohat is the steed and Thought is the rider”, not “an invented sentence "
						+ "of the model”."));
	}
}
