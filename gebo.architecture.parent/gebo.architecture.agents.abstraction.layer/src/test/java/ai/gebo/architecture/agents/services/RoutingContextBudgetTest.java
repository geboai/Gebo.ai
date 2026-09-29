/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService.RenderedRange;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Pins the token budget of the shared context rendered in a single block, as the
 * routing (controller) agent does: it must never exceed the budget however large a
 * contribution is, and the documents reach the routing agent as a digest.
 */
class RoutingContextBudgetTest {

	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	/** Natural text of about the given number of words. */
	private static String words(int count, String prefix) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < count; i++) {
			text.append(prefix).append(i % 97).append(' ');
		}
		return text.toString();
	}

	private static int tokens(String text) {
		return ITokensCountable.stringsTokensSize(text);
	}

	private static GAbstractGenericalAgentService genericService() {
		return mock(GAbstractGenericalAgentService.class, withSettings()
				.useConstructor(null, null, null, null, null, null, NO_RENDERER).defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	@SuppressWarnings("rawtypes")
	private static GBaseRoutingNetworkAgentService routingService() {
		return mock(GBaseRoutingNetworkAgentService.class,
				withSettings().useConstructor(null, null, null, null, null, null, "controller", "controller",
						String.class, Void.class, NO_RENDERER).defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	@Test
	void piecesWithinTheBudgetAreKeptWhole() {
		List<String> pieces = List.of(words(10, "a"), words(20, "b"));
		assertEquals(pieces, GAbstractGenericalAgentService.fitEqually(pieces, 10_000));
	}

	@Test
	void theBudgetIsSharedEquallyAndSmallPiecesLeaveTheirShareToTheLargeOnes() {
		String small = words(20, "small");
		String large1 = words(3000, "large");
		String large2 = words(3000, "other");
		int budget = 1000;

		List<String> fitted = GAbstractGenericalAgentService.fitEqually(List.of(small, large1, large2), budget);

		assertEquals(small, fitted.get(0), "a piece smaller than its share goes in whole");
		assertTrue(fitted.get(1).contains("truncated content"), fitted.get(1));
		assertTrue(fitted.get(2).contains("truncated content"));
		int largeShare = (budget - tokens(small)) / 2;
		// The large pieces share what the small one left, with a little slack for the
		// marker and the estimate of the cut.
		assertTrue(tokens(fitted.get(1)) > largeShare / 2, "the large piece keeps about its share");
		assertTrue(tokens(fitted.get(1)) <= largeShare + 40, "the large piece is cut to its share");
		assertTrue(tokens(String.join("", fitted)) <= budget + 80);
	}

	@Test
	void aSingleBlockNeverExceedsTheBudget() {
		GAbstractGenericalAgentService service = genericService();
		List<AgentProducedSessionContribution> contributions = new ArrayList<>();
		contributions.add(new AgentProducedSessionContribution<String>(1, "search", words(40_000, "doc")));
		contributions.add(new AgentProducedSessionContribution<String>(2, "tools", words(30, "tool")));
		int budget = 2000;

		RenderedRange range = service.renderAllContributions(contributions, budget);

		assertTrue(tokens(range.getContext()) <= budget + 100, "size:" + tokens(range.getContext()));
		assertTrue(range.getContext().contains(words(30, "tool")), "the small contribution is whole");
		assertTrue(range.getContext().contains("truncated content"));
		assertEquals(1, range.getStartContribution());
		assertEquals(2, range.getLastContribution());
		assertTrue(range.isFinishedContributions());
	}

	@Test
	@SuppressWarnings("deprecation")
	void withinTheBudgetTheSingleBlockIsUnchanged() {
		GAbstractGenericalAgentService service = genericService();
		List<AgentProducedSessionContribution> contributions = new ArrayList<>();
		contributions.add(new AgentProducedSessionContribution<String>(1, "search", words(50, "doc")));
		contributions.add(new AgentProducedSessionContribution<String>(2, "tools", words(30, "tool")));

		assertEquals(service.renderAllContributions(contributions).getContext(),
				service.renderAllContributions(contributions, 100_000).getContext());
	}

	@Test
	void theRoutingAgentReadsDocumentsAsADigest() {
		GBaseRoutingNetworkAgentService<?, ?> service = routingService();
		Document found = Document.builder().id("d1").text(words(2000, "body")).metadata(Map.of(DocumentMetaInfos.TITLE,
				"Annual report", DocumentMetaInfos.CONTENT_ORIGINAL_URL, "https://example.com/report",
				DocumentMetaInfos.GEBO_EXTERNAL_SEARCH_RESULT_JSON, words(5000, "json"))).build();
		Document other = Document.builder().id("d2").text("short text").build();

		String digest = service.renderSharedContributionData(List.of(found, other));

		assertTrue(digest.startsWith("2 document(s) found:"), digest);
		assertTrue(digest.contains("1. Annual report (https://example.com/report)"), digest);
		assertTrue(digest.contains("2. d2"), digest);
		assertTrue(digest.contains("short text"));
		assertFalse(digest.contains("json0"), "the metadata is not dumped");
		assertTrue(tokens(digest) < 2 * GBaseRoutingNetworkAgentService.DOCUMENT_DIGEST_EXCERPT_TOKENS + 100,
				"the digest keeps only an excerpt of each text: " + tokens(digest));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void thePrivateContextIsTakenOutOfTheSharedContextBudget() {
		ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext session = new ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext();
		for (int i = 0; i < 6; i++) {
			ai.gebo.architecture.agents.model.AgentsExchangeMessage<String> msg = new ai.gebo.architecture.agents.model.AgentsExchangeMessage<String>();
			msg.setCollaborationContextId(session.getId());
			msg.setMessageSemantic(ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic.RESPONSE);
			msg.setFromAgent("agent-" + i);
			msg.setToAgent("writer");
			msg.setPayload(words(400, "evidence" + i));
			session.addContribution(msg, session.getAndIncrementContributionNr());
		}
		ai.gebo.architecture.agents.model.AgentPrivateSessionContext<String, String> memory = new ai.gebo.architecture.agents.model.AgentPrivateSessionContext<String, String>();
		memory.setCollaborationContextId(session.getId());
		ai.gebo.architecture.agents.model.AgentsExchangeMessage<String> instruction = new ai.gebo.architecture.agents.model.AgentsExchangeMessage<String>();
		instruction.setPayload("write the report");
		memory.addInteraction(instruction, 0, words(1500, "draft"));
		ai.gebo.architecture.ai.model.GPromptTemplateConfig prompt = new ai.gebo.architecture.ai.model.GPromptTemplateConfig();
		prompt.setSystemPromptTemplate("system {PRIVATE_CONTEXT}");
		prompt.setUserPromptTemplate("user {SHARED_CONTEXT}");
		int budget = 5000;

		List<Map<String, Object>> windows = genericService().createAgentTemplateParams(prompt, null, null, null,
				session, memory, "input", null, 0, budget, true);

		int privateTokens = tokens(windows.get(0).get("PRIVATE_CONTEXT").toString());
		assertTrue(privateTokens > 1000, "the private context carries the draft");
		for (Map<String, Object> window : windows) {
			assertTrue(privateTokens + tokens(window.get("SHARED_CONTEXT").toString()) <= budget + 50,
					"private " + privateTokens + " + shared " + tokens(window.get("SHARED_CONTEXT").toString()));
		}
	}

	@Test
	void aTextIsSplitIntoConsecutiveChunksWithoutLosingAnything() {
		String text = words(5000, "w");
		List<String> chunks = GAbstractGenericalAgentService.splitToTokens(text, 800);

		assertTrue(chunks.size() > 1);
		assertEquals(text, String.join("", chunks), "the chunks are the whole text, in order");
		for (String chunk : chunks) {
			assertTrue(tokens(chunk) <= 800 + 20, "chunk of " + tokens(chunk));
		}
		assertEquals(List.of("short"), GAbstractGenericalAgentService.splitToTokens("short", 800));
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void aCollectionIsRenderedElementByElement() {
		IGDocumentContentRenderer upperCase = new IGDocumentContentRenderer<String>() {
			public String getId() {
				return "upper";
			}

			public Class<String> getRenderedType() {
				return String.class;
			}

			public boolean isCanRender(Object document) {
				return document instanceof String;
			}

			public String render(String document) {
				return document.toUpperCase();
			}
		};
		ai.gebo.architecture.ai.service.impl.GDocumentContentRendererProviderImpl provider = new ai.gebo.architecture.ai.service.impl.GDocumentContentRendererProviderImpl(
				List.of(upperCase), null);
		List<Object> items = new ArrayList<>(List.of("first", "second"));
		items.add(null);

		String rendered = provider.get(items).render(items);

		assertEquals("FIRST\r\nSECOND\r\n", rendered);
		assertEquals("plain", provider.get("plain").render("plain").toLowerCase());
	}

	@Test
	void otherContributionsAreRenderedAsUsualForTheRoutingAgent() {
		GBaseRoutingNetworkAgentService<?, ?> service = routingService();
		assertEquals("plain text", service.renderSharedContributionData("plain text"));
	}
}
