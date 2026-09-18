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

import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import ai.gebo.architecture.agents.model.AgentProducedSessionContribution;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService.RenderedRange;

/**
 * Pins the forward-progress guarantee of the shared-context windowing used by the
 * report writer agent when the session carries more tokens than one LLM window.
 *
 * <p>
 * The window is produced by {@code renderBatchedContributions(...)} and paged by
 * the do/while in {@code createAgentTemplateParams(...)}, whose cursor is the last
 * rendered contribution. A window that renders nothing cannot move that cursor, so
 * the loop would re-render the same contribution for ever - which is what happened
 * whenever a single contribution was larger than the budget.
 * </p>
 */
class SharedContextWindowingTest {

	/** Renders a contribution as N characters, so token size is predictable. */
	private static AgentProducedSessionContribution<String> contribution(int nr, int chars) {
		return new AgentProducedSessionContribution<String>(nr, "agent-" + nr, "x".repeat(chars));
	}

	private static GAbstractGenericalAgentService serviceUnderTest() {
		// Returning no renderer makes renderContributionData(...) fall back to
		// genericRender(...), i.e. toString() - so a contribution's rendered size is
		// exactly the string it carries and the budget arithmetic under test is
		// predictable. Every other collaborator stays null on purpose, so the test
		// cannot drift into depending on anything but the windowing itself.
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

	@Test
	void anOversizedContributionIsStillRenderedSoTheCursorCanAdvance() {
		final GAbstractGenericalAgentService service = serviceUnderTest();
		final List<AgentProducedSessionContribution> contributions = new ArrayList<>();
		contributions.add(contribution(1, 4000));
		contributions.add(contribution(2, 10));

		// A budget far below the first contribution: before the fix nothing was
		// inserted, the range came back empty and the caller looped for ever.
		final RenderedRange range = service.renderBatchedContributions(contributions, 5);

		assertFalse(range.getContext().isBlank(), "an oversized contribution must still be rendered");
		assertEquals(1, range.getLastContribution(), "the cursor must name the contribution just rendered");
		assertFalse(range.isFinishedContributions(), "the second contribution still has to be windowed");
	}

	@Test
	void contributionsThatFitAreRenderedTogetherAndReportCompletion() {
		final GAbstractGenericalAgentService service = serviceUnderTest();
		final List<AgentProducedSessionContribution> contributions = new ArrayList<>();
		contributions.add(contribution(1, 10));
		contributions.add(contribution(2, 10));

		final RenderedRange range = service.renderBatchedContributions(contributions, 100000);

		assertTrue(range.isFinishedContributions(), "everything fitted, so the paging must stop");
		assertEquals(2, range.getLastContribution());
	}

	@Test
	void windowingStopsAtTheFirstContributionThatDoesNotFit() {
		final GAbstractGenericalAgentService service = serviceUnderTest();
		final List<AgentProducedSessionContribution> contributions = new ArrayList<>();
		contributions.add(contribution(1, 40));
		contributions.add(contribution(2, 40000));
		contributions.add(contribution(3, 40));

		final RenderedRange range = service.renderBatchedContributions(contributions, 200);

		// The window has to stay a contiguous range: the cursor is the last rendered
		// contribution, so skipping 2 to take 3 would leave 2 unrendered for ever.
		assertEquals(1, range.getLastContribution(), "must stop at the one that does not fit");
		assertFalse(range.isFinishedContributions());
	}

	@Test
	void anEmptyContributionListTerminates() {
		final GAbstractGenericalAgentService service = serviceUnderTest();
		final RenderedRange range = service.renderBatchedContributions(new ArrayList<>(), 100);
		assertTrue(range.isFinishedContributions(), "nothing to window means the paging is done");
	}
}
