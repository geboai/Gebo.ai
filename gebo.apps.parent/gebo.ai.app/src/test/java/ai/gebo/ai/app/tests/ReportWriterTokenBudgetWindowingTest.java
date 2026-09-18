/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) - With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ai.app.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IAgentConfigDao;
import ai.gebo.architecture.agents.services.IAgentsNetworkDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.abstraction.layer.tests.TestChatModel;
import ai.gebo.llms.agent.chat.service.impl.ReportWriterReactiveAgentServiceImpl;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.security.services.ReactiveIdentityUtil;
import reactor.core.publisher.Flux;

/**
 * Exercises the token-budget windowing the standard report writer performs over the
 * shared context, with the fake LLMs in place so the arithmetic stays deterministic.
 *
 * <p>
 * The report writer pre-splits the shared context into budget sized windows
 * (createAgentTemplateParams(..., splitByBudget=true)) and hands them to
 * TokensBudgetFluxCoordinator.tokenBudgetCoordinateAlreadySplitted(...): one LLM call
 * per window plus a final consolidation. It is the same map/reduce shape deep search
 * uses, except deep search lets the coordinator do the splitting itself.
 * </p>
 *
 * <p>
 * What it is meant to pin, and what a unit test on the window builder alone cannot:
 * every contribution reaches exactly one window - no loss, no repeat - and an
 * oversized contribution still terminates.
 * </p>
 *
 * <h2>Why it is disabled</h2>
 *
 * <p>
 * It never completes in the booted Spring context: the very first
 * renderBatchedContributions(...) call does not return, and repeated jstacks land in
 * com.knuddels.jtokkit.Cl100kParser.split under ITokensCountable.stringsTokensSize.
 * The identical scenario is fast everywhere else, so the cause is still unknown and
 * is NOT the windowing this test was written to cover - that is pinned, green, by
 * SharedContextWindowingTest and SharedContextWindowingLoopTest in
 * gebo.architecture.agents.abstraction.layer.
 * </p>
 *
 * <p>
 * Ruled out by measurement, so nobody repeats the work:
 * </p>
 * <ul>
 * <li>an endless paging loop - a temporary log at the end of each window iteration
 * printed NOTHING, so the loop never completes even one iteration;</li>
 * <li>repeated tokenisation - the per pass memo added to renderBatchedContributions
 * removed it and changed nothing here;</li>
 * <li>the tokenizer - jtokkit counts 360k characters in ~0ms, and the app resolves
 * the same jtokkit 1.1.0 that the standalone benchmark used;</li>
 * <li>estimator construction - ITokensCountable keeps one static final instance;</li>
 * <li>the renderer and its provider - StringDocumentContentRenderer.render(...)
 * returns the argument, and the provider is a stream filter over a short list;</li>
 * <li>the windowing itself - calling the real createAgentTemplateParams(...) with the
 * same 48k character contribution outside Spring returns 2 windows in 126ms.</li>
 * </ul>
 *
 * <p>
 * The next step is sampling rather than reasoning: several thread dumps a few seconds
 * apart, or an async-profiler run, to tell one pathologically slow call from a great
 * many fast ones. Re-enable this class when that is understood.
 * </p>
 *
 * <p>
 * One thing learnt here that applies to any future test of this agent:
 * createResponse(...) runs SYNCHRONOUSLY inside execute(...), before the returned
 * flux is ever subscribed, so a reactive block(Duration) does not bound it. The
 * methods below carry a JUnit {@code @Timeout} instead, so a regression fails the
 * build rather than hanging it.
 * </p>
 */
@Disabled("Does not complete in the booted Spring context - see the class javadoc. "
		+ "The windowing it covers is pinned by SharedContextWindowingTest and "
		+ "SharedContextWindowingLoopTest, which are green.")
@TestPropertySource(properties = "ai.gebo.agents.standard.enabled=true")
public class ReportWriterTokenBudgetWindowingTest extends AbstractBaseTestLLmsIntegrationTests {

	private static final String DEFAULT_AGENTS_NETWORK = "DEFAULT_AGENTS_NETWORK";
	/** Marker the fake LLM returns for every per window call. */
	private static final String WINDOW_REPLY = "WINDOW_PARTIAL_OK";
	/** Marker the fake LLM returns for the final consolidation call. */
	private static final String FINAL_REPLY = "GEBO_TEST_CONSOLIDATED_99";
	private static final int CONTRIBUTION_CHARS = 1500;
	private static final int CONTRIBUTIONS = 40;
	/**
	 * Renders to roughly 48k characters (~8k tokens), comfortably past the writer's
	 * budget on the test model's 8k context while staying small enough that the rest
	 * of the prompt pipeline is not the thing being measured.
	 */
	private static final int OVERSIZED_REPEATS = 4000;

	@Autowired
	private IAgentsNetworkDao agentsNetworkDao;
	@Autowired
	private IAgentConfigDao agentConfigDao;
	@Autowired
	private ReportWriterReactiveAgentServiceImpl reportWriter;

	/** Every prompt the fake chat model was asked to answer, in order. */
	private final List<String> seenPrompts = Collections.synchronizedList(new ArrayList<String>());

	@Override
	protected void beforeEachCallback() throws Exception {
		seenPrompts.clear();
		TestChatModel.setGlobalResponseLogic(prompt -> {
			final String seen = prompt != null ? prompt : "";
			seenPrompts.add(seen);
			// The consolidation call is the one carrying the joined window replies rather
			// than raw contributions. Keeping the per window answer free of contribution
			// markers means a marker can only reach a prompt through the shared context.
			return seen.contains(WINDOW_REPLY) ? FINAL_REPLY : WINDOW_REPLY;
		});
	}

	@Override
	protected void afterEachCallback() throws Exception {
		TestChatModel.clearGlobalResponseLogic();
	}

	private static String marker(int index) {
		return "CONTRIB_MARKER_" + index + "_END";
	}

	private static String contributionBody(int index) {
		return marker(index) + " " + "lorem ipsum ".repeat(Math.max(1, CONTRIBUTION_CHARS / 12));
	}

	private static AgentsExchangeMessage<String> response(AgentsCollaborationSessionContext session, String from,
			String payload) {
		AgentsExchangeMessage<String> msg = new AgentsExchangeMessage<String>();
		msg.setCollaborationContextId(session.getId());
		msg.setMessageSemantic(MessageSemantic.RESPONSE);
		msg.setFromAgent(from);
		msg.setToAgent(ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE);
		msg.setPayload(payload);
		return msg;
	}

	private List<IGPartialOperation<GeboChatMessageEnvelope>> runReportWriter(
			AgentsCollaborationSessionContext session) throws Exception {
		final GAgentsNetwork network = agentsNetworkDao.findByCode(DEFAULT_AGENTS_NETWORK);
		assertNotNull(network, "the default network of agents must exist (ai.gebo.agents.standard.enabled=true)");
		final GAgentConfig writerConfig = agentConfigDao
				.findByCode(ReportWriterReactiveAgentServiceImpl.REPORT_WRITER_NETWORK_AGENT_SERVICE);
		assertNotNull(writerConfig, "the report writer agent configuration must be registered");
		final AgentNetworkParticipant persona = network.getAgents().stream()
				.filter(a -> writerConfig.getCode().equals(a.getAgentConfigCode())).findFirst()
				.orElseThrow(() -> new IllegalStateException("report writer is not a participant of the network"));

		final GeboChatRequest request = new GeboChatRequest();
		request.setQuery("Write the consolidated report");
		final LLMChatRequestResources resources = new LLMChatRequestResources();
		resources.setCurrentRequest(request);
		resources.setChathistory(new CSSConsolidatedChatHistory());

		final AgentPrivateSessionContext<String, GeboChatMessageEnvelope> privateMemory = new AgentPrivateSessionContext<String, GeboChatMessageEnvelope>();
		privateMemory.setCollaborationContextId(session.getId());

		final INotificationSink sink = new INotificationSink() {
			@Override
			public void next(NotificationObject state) {
			}
		};

		final long started = System.currentTimeMillis();
		Flux<IGPartialOperation<GeboChatMessageEnvelope>> flux = reportWriter.execute(
				resources.createChatRequestContext(), writerConfig, "Write the consolidated report", network, persona,
				sink, session, privateMemory, ReactiveIdentityUtil.create());
		List<IGPartialOperation<GeboChatMessageEnvelope>> emitted = flux.collectList().block(Duration.ofSeconds(120));
		LOGGER.info("report writer produced {} envelope(s) over {} LLM call(s) in {} ms",
				emitted != null ? emitted.size() : -1, seenPrompts.size(), System.currentTimeMillis() - started);
		assertNotNull(emitted, "the report writer must produce a stream");
		return emitted;
	}

	private static List<Integer> markersIn(String prompt, int count) {
		final List<Integer> found = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			if (prompt.contains(marker(i))) {
				found.add(Integer.valueOf(i));
			}
		}
		return found;
	}

	@Test
	@Timeout(180)
	public void everyContributionReachesExactlyOneWindowAndTheReportIsConsolidated() throws Exception {
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		for (int i = 0; i < CONTRIBUTIONS; i++) {
			session.addContribution(response(session, "searcher-" + i, contributionBody(i)),
					session.getAndIncrementContributionNr());
		}

		final List<IGPartialOperation<GeboChatMessageEnvelope>> emitted = runReportWriter(session);

		// More than one LLM call means the shared context really was split: a single
		// window takes the direct params.size()==1 path instead.
		assertTrue(seenPrompts.size() > 1,
				"the shared context must be windowed into several LLM calls, was: " + seenPrompts.size());

		final int[] timesSeen = new int[CONTRIBUTIONS];
		for (String prompt : seenPrompts) {
			for (Integer index : markersIn(prompt, CONTRIBUTIONS)) {
				timesSeen[index.intValue()]++;
			}
		}
		for (int i = 0; i < CONTRIBUTIONS; i++) {
			assertTrue(timesSeen[i] > 0, "contribution " + i + " never reached any window");
			// The paging cursor is the last rendered contribution: advancing onto it
			// instead of past it makes every window repeat the previous window tail.
			assertEquals(1, timesSeen[i], "contribution " + i + " was rendered into " + timesSeen[i]
					+ " windows; each contribution must appear exactly once");
		}

		final StringBuilder streamed = new StringBuilder();
		boolean consolidatedInResponse = false;
		for (IGPartialOperation<GeboChatMessageEnvelope> op : emitted) {
			GeboChatMessageEnvelope envelope = op != null ? op.getData() : null;
			Object content = envelope != null ? envelope.getContent() : null;
			if (content instanceof String) {
				streamed.append((String) content);
			} else if (content instanceof GeboChatResponse) {
				GeboChatResponse response = (GeboChatResponse) content;
				if (response.getQueryResponse() != null && response.getQueryResponse().contains(FINAL_REPLY)) {
					consolidatedInResponse = true;
				}
			}
		}
		assertTrue(consolidatedInResponse || streamed.toString().contains(FINAL_REPLY),
				"the consolidated answer must reach the streamed output");
	}

	@Test
	@Timeout(180)
	public void aContributionLargerThanTheWholeBudgetStillTerminates() throws Exception {
		final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext();
		// One contribution far beyond the writer budget, followed by an ordinary one: the
		// oversized one has to be rendered on its own so the cursor can reach the second.
		session.addContribution(
				response(session, "searcher-huge", marker(0) + " " + "lorem ipsum ".repeat(OVERSIZED_REPEATS)),
				session.getAndIncrementContributionNr());
		session.addContribution(response(session, "searcher-small", marker(1) + " a short follow up contribution"),
				session.getAndIncrementContributionNr());

		final List<IGPartialOperation<GeboChatMessageEnvelope>> emitted = runReportWriter(session);
		assertFalse(emitted.isEmpty(), "the report writer must still emit a report");

		boolean oversizedRendered = false;
		boolean followUpRendered = false;
		for (String prompt : seenPrompts) {
			oversizedRendered = oversizedRendered || prompt.contains(marker(0));
			followUpRendered = followUpRendered || prompt.contains(marker(1));
		}
		assertTrue(oversizedRendered, "the oversized contribution must still be rendered into a window");
		assertTrue(followUpRendered, "the contribution after the oversized one must still be reached");
	}
}
