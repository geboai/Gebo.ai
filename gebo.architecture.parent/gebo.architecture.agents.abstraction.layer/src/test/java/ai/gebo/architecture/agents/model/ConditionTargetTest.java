/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.architecture.agents.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class ConditionTargetTest {

	@Test
	void theIdsAreKeptStrippedOnceWithoutTheBlankOnes() {
		final ConditionTarget target = ConditionTarget.of(Arrays.asList(" arossi ", "arossi", "", null, "bverdi"),
				List.of("finance"), null);

		assertEquals(List.of("arossi", "bverdi"), target.getUsers());
		assertEquals(List.of("finance"), target.getGroups());
		assertEquals(List.of(), target.getSystems());
	}

	@Test
	void aTargetListingNothingIsNotSet() {
		assertFalse(new ConditionTarget().isSet());
		assertFalse(ConditionTarget.of(Arrays.asList(" ", null), List.of(), null).isSet());
		final ConditionTarget blanks = new ConditionTarget();
		blanks.getSystems().add("  ");
		assertFalse(blanks.isSet(), "set by hand with blank ids only");
		assertTrue(ConditionTarget.of(null, null, List.of("erp")).isSet());
	}

	@Test
	void itTellsWhoAndWhatItLists() {
		final ConditionTarget target = ConditionTarget.of(List.of("arossi"), List.of("finance", "legal"),
				List.of("erp"));

		assertTrue(target.listsUser("arossi"));
		assertTrue(target.listsUser(" arossi "));
		assertFalse(target.listsUser("bverdi"));
		assertFalse(target.listsUser(null));
		assertTrue(target.listsAnyGroup(List.of("sales", "legal")));
		assertFalse(target.listsAnyGroup(List.of("sales")));
		assertFalse(target.listsAnyGroup(null));
		assertTrue(target.listsSystem("erp"));
		assertFalse(target.listsSystem("crm"));
		assertEquals("users arossi; groups finance, legal; systems erp", target.label());
	}

	@Test
	void aCopyIsItsOwn() {
		final ConditionTarget target = ConditionTarget.of(List.of("arossi"), List.of("finance"), List.of("erp"));
		final ConditionTarget copy = target.copy();

		assertEquals(target, copy);
		assertNotSame(target.getUsers(), copy.getUsers());
		copy.getUsers().add("bverdi");
		assertFalse(target.listsUser("bverdi"));
	}

	@Test
	void itTravelsAsJsonWithItsListsOnly() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final ConditionTarget target = ConditionTarget.of(List.of("arossi"), List.of("finance"), List.of("erp"));

		final String json = mapper.writeValueAsString(target);

		assertFalse(json.contains("\"set\""), json);
		assertEquals(target, mapper.readValue(json, ConditionTarget.class));
	}
}
