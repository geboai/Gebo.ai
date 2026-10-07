/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.session.model.IChatSessionEntryDocuments;

/**
 * Pins the documents of the chat's earlier answers: read then, they stay valid for the
 * chat, so an answer recalling them cites no unread document (K13), and the ones it
 * names are given with it.
 */
class AgenticLoopEarlierDocumentsTest {

	/** An earlier interaction of the chat and the documents its answer rested on. */
	record Earlier(String user, String assistant, List<GResponseDocumentRef> documentsRef)
			implements IChatSessionEntry, IChatSessionEntryDocuments {
		@Override
		public String getUser() {
			return user;
		}

		@Override
		public String getAssistant() {
			return assistant;
		}

		@Override
		public List<GResponseDocumentRef> getDocumentsRef() {
			return documentsRef;
		}
	}

	private static GResponseDocumentRef ref(String name) {
		final GResponseDocumentRef ref = new GResponseDocumentRef();
		ref.setName(name);
		return ref;
	}

	private static IChatRequestContext chatWithEarlierAnswer(String... names) {
		final IChatRequestContext context = mock(IChatRequestContext.class);
		final List<IChatSessionEntry> interactions = List.of(new Earlier("Which documents speak of Fohat?",
				"Two documents: ...", java.util.Arrays.stream(names).map(AgenticLoopEarlierDocumentsTest::ref).toList()));
		when(context.getInteractions()).thenReturn(interactions);
		return context;
	}

	@Test
	void aRecapOfAnEarlierListingCitesNoUnreadDocument() {
		final IChatRequestContext context = chatWithEarlierAnswer("The-Secret-Doctrine-1-of-4.pdf",
				"Isis-Unveiled.pdf");
		final String recap = "As listed before: The-Secret-Doctrine-1-of-4.pdf and Isis-Unveiled.pdf.";

		assertTrue(AgenticLoopReactiveAgentServiceImpl
				.unreadCitations(recap, AgenticLoopReactiveAgentServiceImpl.chatDocumentNames(context)).isEmpty());
	}

	@Test
	void aDocumentNoAnswerRestedOnIsStillUnread() {
		final IChatRequestContext context = chatWithEarlierAnswer("The-Secret-Doctrine-1-of-4.pdf");

		assertEquals(List.of("Made-Up-Book.pdf"),
				AgenticLoopReactiveAgentServiceImpl.unreadCitations("See Made-Up-Book.pdf.",
						AgenticLoopReactiveAgentServiceImpl.chatDocumentNames(context)));
	}

	@Test
	void theEarlierDocumentsTheAnswerNamesAreGivenWithIt() {
		final IChatRequestContext context = chatWithEarlierAnswer("The-Secret-Doctrine-1-of-4.pdf",
				"Isis-Unveiled.pdf");

		final List<GResponseDocumentRef> given = AgenticLoopReactiveAgentServiceImpl
				.citedEarlierDocuments("The recap: The-Secret-Doctrine-1-of-4.pdf.", context);

		assertEquals(List.of("The-Secret-Doctrine-1-of-4.pdf"), given.stream().map(GResponseDocumentRef::getName).toList());
		assertTrue(AgenticLoopReactiveAgentServiceImpl.citedEarlierDocuments("No document named.", context).isEmpty());
	}

	@Test
	void noEarlierAnswerNoEarlierDocument() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl.earlierAnswersDocuments(null).isEmpty());
		assertTrue(AgenticLoopReactiveAgentServiceImpl.earlierAnswersDocuments(mock(IChatRequestContext.class)).isEmpty());
	}
}
