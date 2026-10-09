/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Pins the reading of the request understanding's userRequiredLanguage field: the language
 * the user explicitly asks the answer in, which wins over the one the message is
 * written in; none when the user asks for no language.
 */
class AnswerLanguageTest {

	private static String of(String value) {
		return DefaultRoutingChatPipelineStepServiceImpl.userRequiredLanguage(value == null ? null : List.of(value));
	}

	@Test
	void anAskedLanguageIsNamedCapitalized() {
		assertEquals("English", of("English"));
		assertEquals("English", of(" english "));
		assertEquals("Italian", of("Italian."));
		assertEquals("English", of("English (asked in the latest question)"), "the name, not the comment");
	}

	@Test
	void noAskedLanguageIsNone() {
		assertNull(of(null));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.userRequiredLanguage(List.of()));
		assertNull(of(""));
		assertNull(of("none"));
		assertNull(of("None."));
		assertNull(of("no"));
		assertNull(of("not asked"));
		assertNull(of("null"));
		assertNull(of("n/a"));
	}

	@Test
	void aLanguageNamedInTheMessageIsAsked() {
		assertTrue(AskedLanguage.namedIn("English", "Rispondimi in inglese: chi è Adamas nella Pistis Sophia?"));
		assertTrue(AskedLanguage.namedIn("German", "D'ora in poi rispondimi sempre in tedesco. Cosa dice Archiati?"));
		assertTrue(AskedLanguage.namedIn("Italian", "Please answer in Italian: who is X?"));
		assertTrue(AskedLanguage.namedIn("German", "Antworte bitte auf Deutsch."));
		assertTrue(AskedLanguage.namedIn("French", "Réponds en français, s'il te plaît."));
	}

	@Test
	void theLanguageAMessageIsWrittenInIsNotAsked() {
		assertFalse(AskedLanguage.namedIn("English",
				"Write a detailed comparative analysis report: how does Rudolf Steiner describe the Guardian of the Threshold?"));
		assertFalse(AskedLanguage.namedIn("Italian",
				"Scrivi un'analisi dettagliata del ruolo degli arconti nel destino delle anime secondo la Pistis Sophia."));
		assertFalse(AskedLanguage.namedIn("English",
				"Now do a deep web search: compare the reception of Theosophy in India and in Europe, with sources."));
		assertFalse(AskedLanguage.namedIn("Klingonese", "Answer in Klingonese"), "no language has that name");
		assertFalse(AskedLanguage.namedIn(null, "Answer in English"));
	}

	@Test
	void aChatKeepsTheLanguageItStartedInWhateverALaterMessageIsDetectedAs() {
		// "E come ha conosciuto Gurdjieff?" detected Galician, "And about death?" too short
		assertEquals("Italian", DefaultRoutingChatPipelineStepServiceImpl.chatLanguage(null, null, "Italian", "Galician"));
		assertEquals("English", DefaultRoutingChatPipelineStepServiceImpl.chatLanguage(null, null, "English", null));
	}

	@Test
	void aLanguageTheUserAsksForWinsOverTheChatsOwn() {
		assertEquals("French", DefaultRoutingChatPipelineStepServiceImpl.chatLanguage("French", "German", "Italian", "Italian"),
				"asked in this message");
		assertEquals("German", DefaultRoutingChatPipelineStepServiceImpl.chatLanguage(null, "German", "Italian", "Italian"),
				"asked earlier and kept");
	}

	@Test
	void withoutAChatLanguageTheMessagesDetectionAppliesElseNone() {
		assertEquals("Italian", DefaultRoutingChatPipelineStepServiceImpl.chatLanguage(null, null, null, "Italian"));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.chatLanguage(null, null, null, null),
				"the prompts ask for the language of the request");
	}

	@Test
	void aChatStartedBeforeItsLanguageWasKeptHasTheLanguageOfItsEarliestRequestThatHadOne() {
		final ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession chat = new ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession();
		chat.getInteractions().add(interaction("r1", null));
		chat.getInteractions().add(interaction("r2", "Italian"));
		chat.getInteractions().add(interaction("r3", "Galician"));

		assertEquals("Italian", chat.earliestRequestLanguage("r3"));
		assertEquals("Galician", chat.earliestRequestLanguage("r2"), "the request being answered left out");
		assertNull(new ai.gebo.llms.chat.abstraction.layer.session.model.GUserChatSession().earliestRequestLanguage(null));
	}

	private static ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions interaction(String id,
			String language) {
		final ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest request = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest();
		request.setId(id);
		request.setUserLanguage(language);
		final ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions interaction = new ai.gebo.llms.chat.abstraction.layer.session.model.ChatInteractions();
		interaction.setRequest(request);
		return interaction;
	}
}
