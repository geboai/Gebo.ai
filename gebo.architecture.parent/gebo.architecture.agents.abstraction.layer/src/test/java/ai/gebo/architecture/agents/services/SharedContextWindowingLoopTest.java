/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) - With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.agents.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Answers;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;

/**
 * Drives the real shared-context paging loop of
 * {@code createAgentTemplateParams(..., splitByBudget=true)} - the windowing the
 * report writer performs before handing the windows to
 * {@code TokensBudgetFluxCoordinator.tokenBudgetCoordinateAlreadySplitted(...)}.
 *
 * <p>
 * {@link SharedContextWindowingTest} pins the window builder in isolation; this one
 * pins the caller that pages over it, which is where the cursor lives. Both defects
 * it guards against were invisible to a single-window test: a contribution larger
 * than the budget used to produce an empty window whose cursor never advanced (the
 * loop then ran for ever), and the cursor used to be set to the last rendered
 * contribution while {@code getSampledContributionsAfter} filters on {@code >=}, so
 * every window repeated the previous window's final contribution.
 * </p>
 */
class SharedContextWindowingLoopTest {

	private static final String SHARED_CONTEXT = "SHARED_CONTEXT";

	private static GAbstractGenericalAgentService serviceUnderTest() {
		// No renderer: renderContributionData(...) falls back to genericRender(...),
		// i.e. toString(), so a contribution's rendered size is exactly its payload.
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

	private static GPromptTemplateConfig sharedContextOnlyPrompt() {
		final GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setSystemPromptTemplate("system {" + SHARED_CONTEXT + "}");
		prompt.setUserPromptTemplate("user {" + SHARED_CONTEXT + "}");
		return prompt;
	}

	private static void addResponse(AgentsCollaborationSessionContext session, String from, String payload) {
		final AgentsExchangeMessage<String> msg = new AgentsExchangeMessage<String>();
		msg.setCollaborationContextId(session.getId());
		msg.setMessageSemantic(MessageSemantic.RESPONSE);
		msg.setFromAgent(from);
		msg.setToAgent("writer");
		msg.setPayload(payload);
		session.addContribution(msg, session.getAndIncrementContributionNr());
	}

	private static String marker(int index) {
		return "CONTRIB_MARKER_" + index + "_END";
	}

	private List<Map<String, Object>> window(AgentsCollaborationSessionContext session, int tokenBudget) {
		final AgentPrivateSessionContext<String, String> privateMemory = new AgentPrivateSessionContext<String, String>();
		privateMemory.setCollaborationContextId(session.getId());
		return serviceUnderTest().createAgentTemplateParams(sharedContextOnlyPrompt(), null, null, null, session,
				privateMemory, "input", null, 0, tokenBudget, true);
	}

	/** How many of the produced windows carried the given contribution marker. */
	private static int windowsCarrying(List<Map<String, Object>> windows, String marker) {
		int found = 0;
		for (Map<String, Object> w : windows) {
			Object ctx = w.get(SHARED_CONTEXT);
			if (ctx != null && ctx.toString().contains(marker)) {
				found++;
			}
		}
		return found;
	}

	@Test
	@Timeout(60)
	void everyContributionIsPagedIntoExactlyOneWindow() {
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		final int contributions = 30;
		for (int i = 0; i < contributions; i++) {
			addResponse(session, "agent-" + i, marker(i) + " " + "lorem ipsum ".repeat(60));
		}

		final List<Map<String, Object>> windows = window(session, 900);

		assertTrue(windows.size() > 1, "the shared context must be paged into several windows, was " + windows.size());
		for (int i = 0; i < contributions; i++) {
			// Exactly one: the cursor has to advance PAST the last rendered contribution,
			// because getSampledContributionsAfter(...) filters on >= and would otherwise
			// hand the next window the previous window's tail all over again.
			assertEquals(1, windowsCarrying(windows, marker(i)),
					"contribution " + i + " must be rendered into exactly one window");
		}
	}

	@Test
	@Timeout(60)
	void aContributionLargerThanTheBudgetIsSplitAcrossWindowsAndTheNextOneIsStillReached() {
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		addResponse(session, "huge", marker(0) + " " + "lorem ipsum ".repeat(4000) + marker(9));
		addResponse(session, "small", marker(1) + " a short follow up");

		final List<Map<String, Object>> windows = window(session, 3000);

		// The oversized contribution is split in consecutive parts, each in a window of
		// its own, so that no window overflows and none of its content is lost.
		assertTrue(windows.size() > 2, "the oversized contribution spans several windows, was " + windows.size());
		assertEquals(1, windowsCarrying(windows, marker(0)), "its beginning is in its first part");
		assertEquals(1, windowsCarrying(windows, marker(9)), "its end is in its last part, nothing is lost");
		assertEquals(1, windowsCarrying(windows, marker(1)), "the contribution after it must still be reached");
		for (Map<String, Object> w : windows) {
			assertTrue(ai.gebo.architecture.ai.model.ITokensCountable.stringsTokensSize(w.get(SHARED_CONTEXT).toString()) <= 3000,
					"no window over the budget");
		}
	}

	@Test
	@Timeout(60)
	void aBudgetAlreadyExhaustedByTheConstantsStillMakesProgress() {
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		addResponse(session, "a", marker(0) + " first");
		addResponse(session, "b", marker(1) + " second");

		// A negative budget is what a large prompt on a small-context model produces.
		// The shared context keeps its minimum, so small contributions still travel,
		// and the paging still ends.
		final List<Map<String, Object>> windows = window(session, -50);

		assertEquals(1, windows.size(), "the small contributions fit the minimum shared context");
		assertEquals(1, windowsCarrying(windows, marker(0)));
		assertEquals(1, windowsCarrying(windows, marker(1)));
	}
}
