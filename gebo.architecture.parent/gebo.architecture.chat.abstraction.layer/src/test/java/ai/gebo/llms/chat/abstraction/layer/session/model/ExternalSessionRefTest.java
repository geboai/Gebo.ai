/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.chat.abstraction.layer.session.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Pins how another system's own name of a dialogue is read: both parts, stripped, or nothing. */
class ExternalSessionRefTest {

	@Test
	void bothPartsStrippedNameTheDialogue() {
		final ExternalSessionRef ref = ExternalSessionRef.of(" ERP ", " order-7 ");

		assertEquals("ERP", ref.getExternalSystem());
		assertEquals("order-7", ref.getExternalSessionKey());
		assertTrue(ref.isGiven());
		assertEquals("ERP/order-7", ref.label());
		assertEquals(ref, ExternalSessionRef.of("ERP", "order-7"), "the same dialogue is the same reference");
	}

	@Test
	void eitherPartMissingNamesNothing() {
		assertNull(ExternalSessionRef.of(null, "order-7"));
		assertNull(ExternalSessionRef.of("ERP", " "));
		assertNull(ExternalSessionRef.of(null, null));
		final ExternalSessionRef half = new ExternalSessionRef();
		half.setExternalSystem("ERP");
		assertFalse(half.isGiven());
		assertEquals("ERP/?", half.label());
	}
}
