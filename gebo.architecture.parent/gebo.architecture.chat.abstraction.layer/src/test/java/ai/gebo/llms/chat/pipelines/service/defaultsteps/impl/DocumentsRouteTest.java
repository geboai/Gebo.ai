/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl.answeredOverTheDocuments;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Which turns carrying documents are answered over them, and which go to the network of agents. */
class DocumentsRouteTest {

	@Test
	void aTurnWithoutDocumentsIsNeverPinnedToThem() {
		assertFalse(answeredOverTheDocuments(false, false, null, true));
		assertFalse(answeredOverTheDocuments(false, true, "CHAT_WITH_FILES", false));
	}

	@Test
	void documentsAreAnsweredOverUnlessAgenticChatIsChosenWithANetworkOfAgents() {
		// the UI's own switch on upload: Chat with file(s)
		assertTrue(answeredOverTheDocuments(true, false, "CHAT_WITH_FILES", true));
		// Agentic chat chosen again, no network of agents: the LLM router would not get them
		assertTrue(answeredOverTheDocuments(true, false, null, false));
		// Agentic chat chosen again with a network of agents: the agents get the documents
		assertFalse(answeredOverTheDocuments(true, false, null, true));
		assertFalse(answeredOverTheDocuments(true, false, "  ", true));
		// another route of the menu keeps the documents' route
		assertTrue(answeredOverTheDocuments(true, false, "DEEP_SEARCH_RESPONSE", true));
	}

	@Test
	void heavyDocumentsAreAlwaysAnsweredOver() {
		assertTrue(answeredOverTheDocuments(true, true, null, true));
		assertTrue(answeredOverTheDocuments(true, true, "", true));
	}
}
