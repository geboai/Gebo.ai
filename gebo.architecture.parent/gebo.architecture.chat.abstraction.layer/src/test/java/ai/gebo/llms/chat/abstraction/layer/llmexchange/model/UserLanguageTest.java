/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;

import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.chat.abstraction.layer.services.UserLanguageDetection;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.system.ingestion.IGLanguageDetector;
import ai.gebo.system.ingestion.IGLanguageDetector.DetectedLanguage;

/**
 * Pins the user's language: detected on the user's message by the platform's
 * detector, trusted with the guards of the keywords' language, named in English,
 * carried by the request to every context and tools context of it.
 */
class UserLanguageTest {

	private static final String QUESTION = "Write a detailed analysis of how the figure of Christ is presented.";

	private static IGLanguageDetector detecting(String code, double confidence) throws IOException {
		IGLanguageDetector detector = mock(IGLanguageDetector.class);
		when(detector.detect(anyString())).thenReturn(new DetectedLanguage(code, confidence));
		return detector;
	}

	private static GeboChatRequest request(String language) {
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		request.setUserChatContextCode("chat-1");
		request.setQuery(QUESTION);
		request.setUserLanguage(language);
		return request;
	}

	@Test
	void aTrustedDetectionIsNamedInEnglish() throws IOException {
		assertEquals("English", UserLanguageDetection.of(detecting("en", 0.99), QUESTION));
		assertEquals("Italian",
				UserLanguageDetection.of(detecting("it", 0.9), "Scrivi un'analisi dettagliata della figura del Cristo."));
	}

	@Test
	void anUntrustedOrImpossibleDetectionNamesNothing() throws IOException {
		assertNull(UserLanguageDetection.of(null, QUESTION), "no detector deployed");
		assertNull(UserLanguageDetection.of(detecting("en", 0.2), QUESTION), "a low confidence is not trusted");
		assertNull(UserLanguageDetection.of(detecting("en", 0.99), null));

		IGLanguageDetector shortText = detecting("en", 0.99);
		assertNull(UserLanguageDetection.of(shortText, "Who is Fohat?"), "too short to detect");
		verify(shortText, never()).detect(anyString());

		IGLanguageDetector failing = mock(IGLanguageDetector.class);
		when(failing.detect(anyString())).thenThrow(new IOException("models not loaded"));
		assertNull(UserLanguageDetection.of(failing, QUESTION), "a failing detection leaves the language to the model");
	}

	@Test
	void aCodeIsNamedInEnglish() {
		assertEquals("German", UserLanguageDetection.englishName("de"));
		assertEquals("Portuguese", UserLanguageDetection.englishName(" pt "));
		assertNull(UserLanguageDetection.englishName(" "));
	}

	@Test
	void everyContextOfTheRequestNamesItsLanguage() {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request("English"));
		IChatRequestContext context = resources.createChatRequestContext();
		assertEquals("English", context.getUserLanguage());
		assertEquals("English", ToolCallbackDeclarationUtil.userLanguage(new ToolContext(context.getToolsContext())),
				"the tools calling a model of their own (a deep search) know it");
		assertEquals("English", IChatRequestContext.forAgent(context, null).getUserLanguage(), "an agent's context");
		assertEquals("English", context.withToolsRoom(1000).getUserLanguage(), "a context with a tools' room");

		MinimalChatContext minimal = new MinimalChatContext();
		minimal.setCurrentRequest(request("Italian"));
		IChatRequestContext minimalContext = minimal.createChatRequestContext();
		assertEquals("Italian", minimalContext.getUserLanguage());
		assertEquals("Italian",
				ToolCallbackDeclarationUtil.userLanguage(new ToolContext(minimalContext.getToolsContext())));
	}

	@Test
	void anUndetectedLanguageIsLeftToTheModel() {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request(null));
		IChatRequestContext context = resources.createChatRequestContext();

		assertNull(context.getUserLanguage());
		assertNull(ToolCallbackDeclarationUtil.userLanguage(new ToolContext(context.getToolsContext())));
		assertEquals(IChatRequestContext.USER_LANGUAGE_UNDETECTED, IChatRequestContext.answerLanguage(context));
		assertEquals(IChatRequestContext.USER_LANGUAGE_UNDETECTED, IChatRequestContext.answerLanguage(null));
	}
}
