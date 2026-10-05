/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;

/**
 * Pins the status notices of the contributions (how an agent's data was produced,
 * e.g. the sources a search could not reach): kept from the message to the shared
 * context and shown with the data to every agent reading it, by every way the shared
 * context is rendered, even when the agent produced no data.
 */
class ContributionStatusNoticesTest {

	private static final String NOTICE = "could not search The web: not responding (no answer within 60 s)";

	private static GAbstractGenericalAgentService serviceUnderTest() {
		final IGDocumentContentRendererProvider noRenderer = new IGDocumentContentRendererProvider() {
			@Override
			public <T> IGDocumentContentRenderer<T> get(T doc) {
				return null;
			}
		};
		return mock(GAbstractGenericalAgentService.class,
				withSettings().useConstructor(null, null, null, null, null, null, noRenderer)
						.defaultAnswer(Answers.CALLS_REAL_METHODS));
	}

	/** A search that found nothing because its source was not responding, and one that found something. */
	private static AgentsCollaborationSessionContext session() {
		AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		AgentsExchangeMessage<String> nothing = AgentsExchangeMessage.of(session, "router", "", MessageSemantic.RESPONSE);
		nothing.setFromAgent("web-search-agent");
		nothing.setStatusNotices(List.of(NOTICE));
		session.addContribution(nothing, 1);
		AgentsExchangeMessage<String> found = AgentsExchangeMessage.of(session, "router", "found text",
				MessageSemantic.RESPONSE);
		found.setFromAgent("kb-search-agent");
		session.addContribution(found, 2);
		AgentsExchangeMessage<String> empty = AgentsExchangeMessage.of(session, "router", "", MessageSemantic.RESPONSE);
		empty.setFromAgent("silent-agent");
		session.addContribution(empty, 3);
		return session;
	}

	@Test
	void theStatusIsKeptFromTheMessageToTheSharedContext() {
		List<AgentProducedSessionContribution> contributions = session().getSampledContributions();

		assertTrue(contributions.get(0).getStatusNotices().contains(NOTICE));
		assertTrue(contributions.get(1).getStatusNotices() == null);
	}

	@Test
	void everyRenderingOfTheSharedContextTellsTheStatusEvenWithoutData() {
		GAbstractGenericalAgentService service = serviceUnderTest();
		AgentsCollaborationSessionContext session = session();
		List<AgentProducedSessionContribution> contributions = session.getSampledContributions();

		String batched = service.renderBatchedContributions(contributions, 100000).getContext();
		String fitted = service.renderAllContributions(contributions, 100000).getContext();
		String windowed = String.join("", service.windowSharedContext(session, 0, 100000));

		for (String rendered : List.of(batched, fitted, windowed)) {
			assertTrue(rendered.contains("web-search-agent"), rendered);
			assertTrue(rendered.contains("STATUS: " + NOTICE), rendered);
			assertTrue(rendered.contains("found text"), rendered);
			// a contribution with neither data nor status is still left out
			assertFalse(rendered.contains("silent-agent"), rendered);
		}
	}
}
