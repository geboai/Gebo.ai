/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.services.config.KnowledgeBaseSearchBudgetConfig;

/**
 * The one capping formula of the knowledge base searches: the room a model call's
 * messages leave of its context, two thirds of it to contents, divided by the room
 * divisor, never more than the room.
 */
class KnowledgeBaseSearchBudgetTest {

	@AfterEach
	void defaultDivisor() {
		KnowledgeBaseSearchBudget.setRoomDivisor(KnowledgeBaseSearchBudget.DEFAULT_ROOM_DIVISOR);
	}

	@Test
	void theRoomIsTwoThirdsOfWhatTheMessagesLeave() {
		assertEquals(84000, KnowledgeBaseSearchBudget.roomOfContext(128000, 2000));
		assertEquals(0, KnowledgeBaseSearchBudget.roomOfContext(8000, 9000), "nothing left, no room");
		assertEquals(4000, KnowledgeBaseSearchBudget.roomOfContext(6000, -5), "no negative usage");
	}

	@Test
	void theContentsTakeTheRoomDividedByTheDivisorNeverMoreThanIt() {
		assertEquals(28000, KnowledgeBaseSearchBudget.forContext(128000, 2000), "a third of the room by default");
		assertEquals(10000, KnowledgeBaseSearchBudget.shareOfRoom(30000, 3.0d));
		assertEquals(1000, KnowledgeBaseSearchBudget.shareOfRoom(1000, 0.5d), "never more than the room");
		assertEquals(0, KnowledgeBaseSearchBudget.shareOfRoom(-10, 3.0d));
	}

	@Test
	void theConfiguredDivisorApplies() {
		KnowledgeBaseSearchBudgetConfig config = new KnowledgeBaseSearchBudgetConfig();
		config.setKnowledgeBaseSearchRoomDivisor(6.0d);
		config.apply();

		assertEquals(6.0d, KnowledgeBaseSearchBudget.roomDivisor());
		assertEquals(14000, KnowledgeBaseSearchBudget.forContext(128000, 2000));
	}

	@Test
	void aDivisorNotPositiveLeavesTheDefault() {
		for (double invalid : new double[] { 0d, -2d, Double.NaN, Double.POSITIVE_INFINITY }) {
			KnowledgeBaseSearchBudget.setRoomDivisor(invalid);
			assertEquals(KnowledgeBaseSearchBudget.DEFAULT_ROOM_DIVISOR, KnowledgeBaseSearchBudget.roomDivisor(),
					"divisor " + invalid);
		}
	}
}
