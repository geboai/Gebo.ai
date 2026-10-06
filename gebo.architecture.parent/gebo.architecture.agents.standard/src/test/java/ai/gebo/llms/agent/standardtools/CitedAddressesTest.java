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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;

/**
 * Pins the check of the addresses a text cites: an address no one gave is removed,
 * a link keeping its text, whatever the way the text arrives.
 */
class CitedAddressesTest {
	private static final Set<String> KNOWN = CitedAddresses
			.addressesIn("{\"source\":\"https://www.postgresql.org/docs/release/\",\"x\":\"http://Example.ORG/a%20b?q=1\"}");

	private static boolean known(String address) {
		return CitedAddresses.isKnown(address, KNOWN);
	}

	@Test
	void addressesAreComparedNormalized() {
		assertTrue(known("https://WWW.PostgreSQL.org/docs/release"));
		assertTrue(known("https://www.postgresql.org/docs/release/#v18"));
		assertTrue(known("http://example.org/a b"), "decoded, without the query");
		assertFalse(known("https://www.postgresql.org/download/"));
		assertFalse(known("https://www.postgresql.org/docs/release/18"));
	}

	@Test
	void aMadeUpAddressIsRemovedALinkKeepingItsText() {
		List<String> removed = new ArrayList<>();

		String checked = CitedAddresses.withoutUnknown("Source: PostgreSQL Download, web page, "
				+ "https://www.postgresql.org/download/. See [the release notes](https://www.postgresql.org/docs/release/) "
				+ "and [the download page](https://www.postgresql.org/download/), or <https://made.up/page>.",
				CitedAddressesTest::known, removed::add);

		assertEquals("Source: PostgreSQL Download, web page, . See [the release notes](https://www.postgresql.org/docs/release/) "
				+ "and the download page, or .", checked);
		assertEquals(List.of("https://www.postgresql.org/download/", "https://made.up/page",
				"https://www.postgresql.org/download/"), removed);
	}

	@Test
	void aTextWithoutAddressesIsLeftAsItIs() {
		String text = "No address [here] (none) http is just a word.";
		assertEquals(text, CitedAddresses.withoutUnknown(text, address -> false, address -> {
		}));
	}

	@Test
	void theDocumentsNotReadOfAToolResultAreNotKnown() {
		String result = "{\"sources\":[{\"source\":\"https://a.example/read\"}],\"documentsNotRead\":["
				+ "{\"title\":\"x\",\"source\":\"https://b.example/failed\",\"reason\":\"not loaded within 60 s\"}],"
				+ "\"fragments\":[]}";

		Set<String> known = CitedAddresses.addressesIn(CitedAddresses.withoutDocumentsNotRead(result));

		assertTrue(CitedAddresses.isKnown("https://a.example/read", known));
		assertFalse(CitedAddresses.isKnown("https://b.example/failed", known));
	}

	@Test
	void theStreamedAnswerIsCheckedWhereverItsPiecesCutTheAddresses() {
		List<String> removed = new ArrayList<>();
		List<String> pieces = List.of("La versione è la 18. Fonte: [PostgreSQL ", "Download](htt", "ps://www.postgre",
				"sql.org/download/) e le note: https://www.postgresql.org/do", "cs/release/ . Altro: https://made.up",
				"/x", "");

		List<String> out = CitedAddresses.guarded(Flux.fromIterable(pieces), CitedAddressesTest::known, removed::add)
				.collectList().block();

		assertEquals("La versione è la 18. Fonte: PostgreSQL Download e le note: https://www.postgresql.org/docs/release/ "
				+ ". Altro: ", String.join("", out));
		assertEquals(List.of("https://www.postgresql.org/download/", "https://made.up/x"), removed);
		assertEquals("La versione è la 18. Fonte: ", out.get(0), "the text before a link goes through at once");
	}

	@Test
	void aBracketThatIsNoLinkIsNotHeldBack() {
		assertEquals("A [1] note".length(), CitedAddresses.safeEnd("A [1] note"));
		assertEquals(2, CitedAddresses.safeEnd("A [partial link text"));
		assertEquals(4, CitedAddresses.safeEnd("See https"));
		assertEquals(4, CitedAddresses.safeEnd("See <https://a.example/x"));
		assertEquals("See https://a.example/x done".length(), CitedAddresses.safeEnd("See https://a.example/x done"));
	}
}
