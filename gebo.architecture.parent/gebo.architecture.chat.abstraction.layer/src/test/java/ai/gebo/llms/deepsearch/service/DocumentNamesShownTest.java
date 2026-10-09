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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.llms.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the document names the user is told while a search or an analysis works:
 * the ones of the knowledge base and of an external source alike, the first three
 * in the order found, then how many more.
 */
class DocumentNamesShownTest {

	private static Document fragment(String key, String value) {
		final Map<String, Object> metadata = new HashMap<>();
		if (key != null) {
			metadata.put(key, value);
		}
		return new Document("text of " + value, metadata);
	}

	@Test
	void theFirstThreeDocumentsAreNamedInTheOrderFoundTheOthersCounted() {
		final List<Document> fragments = new ArrayList<>();
		for (String name : List.of("La Scienza Occulta.pdf", "Pistis Sophia.pdf", "La Scienza Occulta.pdf",
				"Kybalion.epub", "Zohar.pdf", "Corpus Hermeticum.pdf")) {
			fragments.add(fragment(DocumentMetaInfos.GEBO_FILE_NAME, name));
		}

		assertEquals("\"La Scienza Occulta.pdf\", \"Pistis Sophia.pdf\", \"Kybalion.epub\" and 2 more",
				DocumentNamesShown.ofFragments(fragments), "each document once, three named");
	}

	private static SearchResult result(String uri, String name, String title) {
		final SearchResult result = new SearchResult();
		result.setResultReference(new SearchResultReference());
		result.getResultReference().setUri(uri);
		result.getResultReference().setName(name);
		result.getResultReference().setTitle(title);
		return result;
	}

	@Test
	void aWebPageIsNamedByItsTitleElseByItsCompleteAddressWithoutParameters() {
		// a web search gives the host as name and the page title
		assertEquals("Astral body - Anthro Wiki", DocumentNamesShown
				.nameOf(result("https://en.anthro.wiki/Astral_body?x=1", "en.anthro.wiki", "Astral body - Anthro Wiki")));
		assertEquals("https://www.sophiainstitute.us/blog/the-four-temperaments",
				DocumentNamesShown.nameOf(result("https://www.sophiainstitute.us/blog/the-four-temperaments?utm=x#top",
						"sophiainstitute.us", null)),
				"no title: the complete address, not the host");
	}

	@Test
	void aResultOfAnotherSourceKeepsItsName() {
		// a SharePoint or Confluence result names its file
		assertEquals("Budget 2026.xlsx", DocumentNamesShown
				.nameOf(result("https://contoso.sharepoint.com/sites/x/Budget%202026.xlsx", "Budget 2026.xlsx", null)));
	}

	@Test
	void anExternalFragmentIsNamedByTheSearchResultItWasLoadedFrom() throws Exception {
		final Map<String, Object> metadata = new HashMap<>();
		// as the chunker stores it: the host as file name, the whole search result
		metadata.put(DocumentMetaInfos.GEBO_FILE_NAME, "rsarchive.org");
		metadata.put(DocumentMetaInfos.GEBO_EXTERNAL_SEARCH_RESULT_JSON, new ObjectMapper()
				.writeValueAsString(result("https://rsarchive.org/Lectures/GA182/English/Singles/19181009p01.html",
						"rsarchive.org", "The Work of the Angel in Our Astral Body")));

		assertEquals("\"The Work of the Angel in Our Astral Body\"",
				DocumentNamesShown.ofFragments(List.of(new Document("text", metadata))));
	}

	@Test
	void aDocumentWithNoFileNameIsNamedByItsAddressElseItsCode() {
		assertEquals("\"https://en.anthro.wiki/Astral_body\"", DocumentNamesShown.ofFragments(
				List.of(fragment(DocumentMetaInfos.CONTENT_ORIGINAL_URL, "https://en.anthro.wiki/Astral_body?q=1"))));
		assertEquals("\"page-42\"",
				DocumentNamesShown.ofFragments(List.of(fragment(DocumentMetaInfos.CONTENT_CODE, "page-42"))));
	}

	@Test
	void namesAreOnOneLineAndShownWhole() {
		final String longName = "x".repeat(100);
		assertEquals("\"A title on two lines\", \"" + longName + "\"",
				DocumentNamesShown.shown(List.of("A  title\non two lines", longName)));
	}

	@Test
	void noNameShownWhenNoDocumentHasOne() {
		assertNull(DocumentNamesShown.ofFragments(List.of(fragment(null, null))));
		assertNull(DocumentNamesShown.shown(List.of(" ", "")));
		assertNull(DocumentNamesShown.ofFragments(null));
	}

	@Test
	void aBatchIsDescribedByItsCountWhenNoNamesAreGiven() {
		final TokensLimitCompute<Document> counting = (list, budget) -> false;

		assertEquals("2 documents", counting.describe(List.of(fragment(null, null), fragment(null, null))));
	}
}
