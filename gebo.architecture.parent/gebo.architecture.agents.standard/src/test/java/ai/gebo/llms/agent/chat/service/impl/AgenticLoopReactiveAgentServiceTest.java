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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.GAbstractGenericalAgentService;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.ai.service.IGDocumentContentRenderer;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.agent.standardtools.ToolsFoundDocuments;
import ai.gebo.architecture.ai.service.ToolsTokenBudget;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.ControlMarkerStripper;
import ai.gebo.llms.agent.chat.service.impl.AgenticLoopReactiveAgentServiceImpl.LoopIteration;
import ai.gebo.llms.agent.standard.services.StandardAgentsNetworkEnvironmentEntries;
import ai.gebo.architecture.ai.service.ToolCallbackDeclarationUtil;
import ai.gebo.llms.agent.standardtools.InternalKnowledgeBaseSearchToolSource;
import ai.gebo.llms.agent.standardtools.DeepSearchToolSource;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.ai.service.IGToolCallbackSource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.DeliverableIntent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.session.model.CSSConsolidatedChatHistory;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import reactor.core.publisher.Flux;

/**
 * Pins the loop of the single agent with tools: the control markers never reach the
 * user, the model decides when the loop ends, the iterations are bounded and each
 * one receives the history of the previous ones.
 */
class AgenticLoopReactiveAgentServiceTest {
	private static final String STOP = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_FINISHED;
	private static final String MORE = ReportWriterReactiveAgentServiceImpl.AGENT_CONTROL_MORE_TOOLS;

	private static final IGDocumentContentRendererProvider NO_RENDERER = new IGDocumentContentRendererProvider() {
		@Override
		public <T> IGDocumentContentRenderer<T> get(T doc) {
			return null;
		}
	};

	/** A loop agent whose model streams scripted answers, one per iteration. */
	static class ScriptedLoopAgent extends AgenticLoopReactiveAgentServiceImpl {
		final List<List<String>> answers;
		final List<Map<String, Object>> receivedParams = new ArrayList<>();

		ScriptedLoopAgent(List<List<String>> answers) {
			super(null, null, null, null, null, null, NO_RENDERER);
			this.answers = answers;
		}

		final List<IChatRequestContext> receivedContexts = new ArrayList<>();

		@Override
		protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
				IChatRequestContext context, Map<String, Object> params) {
			receivedParams.add(params);
			receivedContexts.add(context);
			int index = Math.min(receivedParams.size() - 1, answers.size() - 1);
			return Flux.fromIterable(answers.get(index));
		}

		ToolCallsListener listenerFor(IChatRequestContext context) {
			return agentToolCallsListener(context);
		}
	}

	private static String run(ScriptedLoopAgent agent, int maxIterations) {
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		List<LoopIteration> history = new ArrayList<>();
		return String.join("", agent.iteration(1, maxIterations, 10_000, history, null, new GPromptTemplateConfig(), null,
				persona, mock(INotificationSink.class), new ToolCallsListener()).collectList().block());
	}

	@Test
	void theModelMayNotifyTheUserWhenThePersonaIsAllowedTo() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of(STOP)));
		AgentNetworkParticipant allowed = mock(AgentNetworkParticipant.class);
		when(allowed.isAllowedToNotifyUser()).thenReturn(true);
		AgentNetworkParticipant notAllowed = mock(AgentNetworkParticipant.class);
		INotificationSink sink = mock(INotificationSink.class);

		List<ToolCallback> tools = agent.additionalTools(allowed, sink);

		assertEquals(List.of(GAbstractGenericalAgentService.NOTIFY_USER_TOOL),
				tools.stream().map(tool -> tool.getToolDefinition().name()).toList());
		assertNull(agent.additionalTools(notAllowed, sink));
		assertNull(agent.additionalTools(allowed, null));
	}

	@Test
	void aMarkerSplitAcrossChunksIsRemovedAndRecognised() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		StringBuilder shown = new StringBuilder();
		int half = MORE.length() / 2;
		shown.append(stripper.accept("The answer so far."));
		shown.append(stripper.accept(" " + MORE.substring(0, half)));
		shown.append(stripper.accept(MORE.substring(half)));
		shown.append(stripper.complete());

		assertEquals("The answer so far. ", shown.toString());
		assertTrue(stripper.isContinueRequested());
		assertFalse(stripper.isFinishRequested());
	}

	@Test
	void theDocumentsTheAnswerRestsOnAreReadFromItsLastLineAndNeverShown() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		String shown = stripper.accept("The answer.\n<ANSWER-DOC") + stripper.accept("UMENTS>#3 #1")
				+ stripper.accept(" #3</ANSWER-DOCUMENTS>") + stripper.accept(STOP) + stripper.complete();

		assertEquals("The answer.\n", shown);
		assertEquals(List.of("#3", "#1"), stripper.getAnswerDocuments());
		assertTrue(stripper.isFinishRequested());

		ControlMarkerStripper none = new ControlMarkerStripper();
		assertEquals("No documents.", none.accept("No documents.") + none.complete());
		assertNull(none.getAnswerDocuments(), "no marker given");

		ControlMarkerStripper empty = new ControlMarkerStripper();
		assertEquals("General knowledge. ", empty.accept("General knowledge. <answer-documents></answer-documents>")
				+ empty.complete());
		assertEquals(List.of(), empty.getAnswerDocuments(), "rests on no document, in any case");

		ControlMarkerStripper unclosed = new ControlMarkerStripper();
		assertEquals("Text ", unclosed.accept("Text <ANSWER-DOCUMENTS>2, 5") + unclosed.complete());
		assertEquals(List.of("#2", "#5"), unclosed.getAnswerDocuments(), "a marker left open ends with the text");
	}

	@Test
	void theAnswerDocumentsAreTheOnesItSaysElseTheOnesItCitesElseAll() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef release = webPage(
				"www.postgresql.org", "https://www.postgresql.org/docs/release/");
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef keycloak = webPage("www.keycloak.org",
				"https://www.keycloak.org/server/db");
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef herodevs = webPage("www.herodevs.com",
				"https://www.herodevs.com/blog-posts/postgresql-eol");
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		collector.add(List.of(keycloak, release, herodevs));

		// the answer says it rests on #2
		collector.addAnswerDocumentIds(List.of("#2"));
		assertEquals(List.of(release), agent.answerDocuments(collector, "PostgreSQL 18 (see the release notes)."));

		// no marker: the documents it cites
		ToolsFoundDocuments cited = new ToolsFoundDocuments();
		cited.add(List.of(keycloak, release, herodevs));
		assertEquals(List.of(release),
				agent.answerDocuments(cited, "See https://www.postgresql.org/docs/release/ for the dates."));

		// ids no tool gave: the documents it cites
		ToolsFoundDocuments unknown = new ToolsFoundDocuments();
		unknown.add(List.of(keycloak, release));
		unknown.addAnswerDocumentIds(List.of("#7"));
		assertEquals(List.of(keycloak), agent.answerDocuments(unknown, "As www.keycloak.org says."));

		// nothing to tell: all of them
		ToolsFoundDocuments silent = new ToolsFoundDocuments();
		silent.add(List.of(keycloak, release));
		assertEquals(List.of(keycloak, release), agent.answerDocuments(silent, "An answer citing nothing."));

		// an empty marker: the answer rests on no document
		ToolsFoundDocuments none = new ToolsFoundDocuments();
		none.add(List.of(keycloak));
		none.addAnswerDocumentIds(List.of());
		assertEquals(List.of(), agent.answerDocuments(none, "From general knowledge."));
	}

	@Test
	void textThatOnlyLooksLikeAMarkerIsKept() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		String shown = stripper.accept("a < b and <AGENT-C") + stripper.accept("hat") + stripper.complete();

		assertEquals("a < b and <AGENT-Chat", shown);
		assertFalse(stripper.isContinueRequested());
	}

	@Test
	void theModelEndsTheLoopWhenItSaysItIsDone() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching ", "first. ", MORE),
				List.of("The final ", "answer.", STOP), List.of("never reached")));

		String shown = run(agent, 5);

		assertEquals("Searching first. \r\n\r\nThe final answer.", shown);
		assertEquals(2, agent.receivedParams.size(), "the model ended the loop at the second iteration");
		assertEquals(2, agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.CURRENT_ITERATION_PROMPT_PARAM));
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains("Searching first."), "the second iteration receives the first one's text");
	}

	@Test
	void anAnswerWithoutMarkerEndsTheLoop() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Just the answer.")));

		assertEquals("Just the answer.", run(agent, 5));
		assertEquals(1, agent.receivedParams.size());
	}

	@Test
	void theLoopNeverExceedsItsIterations() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("more work", MORE)));

		String shown = run(agent, 3);

		assertEquals(3, agent.receivedParams.size());
		assertFalse(shown.contains("AGENT-CONTROL"), "no marker reaches the user");
		assertEquals(3, agent.receivedParams.get(2).get(AgenticLoopReactiveAgentServiceImpl.MAX_ITERATIONS_PARAM));
	}

	@Test
	void theNextIterationGoesOnFromTheLastOutcomeAsTheReportWriterDoes() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		collector.add(List.of(webPage("www.postgresql.org", "https://www.postgresql.org/docs/release/")));
		List<LoopIteration> history = List.of(new LoopIteration(1, "Shown to the user.", List.of()),
				new LoopIteration(2, "An older draft.", List.of(), "no search used"),
				new LoopIteration(3, "The draft built on the deep searches.", List.of(), "coverage thin: complete X", true));

		String story = agent.loopStory(history, 40_000, collector);

		assertTrue(story.contains("RESPONSE: Shown to the user."), "a shown text is part of the answer: " + story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.DISCARDED_DRAFT
				+ "The draft built on the deep searches."), "the last draft whole: " + story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.WHY_DISCARDED + "coverage thin: complete X"));
		assertTrue(!story.contains("An older draft.") && story.contains(AgenticLoopReactiveAgentServiceImpl.SUPERSEDED_DRAFT),
				"an older draft superseded: " + story);
		assertTrue(story.contains("#1 www.postgresql.org, https://www.postgresql.org/docs/release"),
				"the documents so far with their ids: " + story);
	}

	@Test
	void aDraftWrittenOnNoSourceIsNotGivenToBuildOnOnlyWhyItWasDiscarded() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		List<LoopIteration> history = List.of(
				new LoopIteration(1, "An answer from memory.", List.of(), "no search used"));

		String story = agent.loopStory(history, 40_000, null);

		assertFalse(story.contains("An answer from memory."), "a draft to rewrite would be rewritten: " + story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.DISCARDED_WITHOUT_DRAFT), story);
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.WHY_DISCARDED + "no search used"), story);
	}

	@Test
	void theLoopHistoryFitsHalfOfTheBudget() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		StringBuilder longText = new StringBuilder();
		for (int i = 0; i < 20_000; i++) {
			longText.append("word").append(i % 50).append(' ');
		}
		List<LoopIteration> history = List.of(new LoopIteration(1, longText.toString(), List.of()),
				new LoopIteration(2, "short", List.of()));

		String story = agent.loopStory(history, 4000);

		assertTrue(ITokensCountable.stringsTokensSize(story) <= 2000 + 100, "story of "
				+ ITokensCountable.stringsTokensSize(story));
		assertTrue(story.contains("BEGIN_AGENT-LOOP-2") && story.contains("short"));
		assertEquals("No previous iteration: this is the first one.", agent.loopStory(List.of(), 4000));
	}

	@Test
	void everyIterationIsShapedForTheDeliverableTheUserAskedFor() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching. " + MORE), List.of("4. " + STOP)));
		AgentsCollaborationSessionContext session = mock(AgentsCollaborationSessionContext.class);
		Map<String, Object> environment = new HashMap<>();
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_INTENT, DeliverableIntent.QA);
		when(session.getEnvironment()).thenReturn(environment);
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");

		Map<String, Object> deliverable = agent.deliverableTemplateParams(agent.sessionUserIntent(session));
		agent.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null, persona,
				mock(INotificationSink.class), new ToolCallsListener(), deliverable).collectList().block();

		assertEquals(2, agent.receivedParams.size());
		for (Map<String, Object> params : agent.receivedParams) {
			assertEquals("QA: direct short answer",
					params.get(ReportWriterReactiveAgentServiceImpl.REQUIRED_AGENT_COMPLETENESS_TEMPLATE_PARAM));
			assertTrue(String.valueOf(
					params.get(ReportWriterReactiveAgentServiceImpl.DELIVERABLE_FORMATTING_RULES_TEMPLATE_PARAM))
					.startsWith("Answer the question first"), "the QA formatting rules");
		}
	}

	/**
	 * A loop agent whose model streams scripted answers and, at the given calls (0
	 * based), uses a tool first, as the model's tool wrapper records it.
	 */
	private static ScriptedLoopAgent toolUsingAgent(List<List<String>> answers, Set<Integer> callsUsingATool) {
		return toolUsingAgent(answers, callsUsingATool, "deepSearchKnowledgeBase");
	}

	/** The same, the model using the given tool. */
	private static ScriptedLoopAgent toolUsingAgent(List<List<String>> answers, Set<Integer> callsUsingATool,
			String toolName) {
		return new ScriptedLoopAgent(answers) {
			@Override
			protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
					IChatRequestContext context, Map<String, Object> params) {
				final int call = receivedParams.size();
				final Flux<String> text = super.callLLMReactive(chatModel, prompt, context, params);
				if (!callsUsingATool.contains(call)) {
					return text;
				}
				return Flux.defer(() -> {
					context.getToolCallListener().addCall(toolName, "A tool", "{}", "found");
					return text;
				});
			}
		};
	}

	/** Runs the loop asking for an analysis, only the given tools counting as evidence. */
	private static String runNeedingEvidence(ScriptedLoopAgent agent, int maxIterations, Set<String> evidenceTools) {
		ToolCallsListener listener = new ToolCallsListener();
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.forAgent(IChatRequestContext.builder().requestID("r1").build(),
				listener);
		return String.join("", agent.iteration(1, maxIterations, 10_000, new ArrayList<>(), null,
				new GPromptTemplateConfig(), context, persona, mock(INotificationSink.class), listener,
				agent.deliverableTemplateParams(DeliverableIntent.ANALISYS),
				new AgenticLoopReactiveAgentServiceImpl.SourceGate(evidenceTools, true, List.of()), null).collectList()
				.block());
	}

	/** Runs the loop asking for an analysis, the evidence of the sources required. */
	private static String runNeedingEvidence(ScriptedLoopAgent agent, int maxIterations) {
		ToolCallsListener listener = new ToolCallsListener();
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.forAgent(IChatRequestContext.builder().requestID("r1").build(),
				listener);
		return String.join("", agent.iteration(1, maxIterations, 10_000, new ArrayList<>(), null,
				new GPromptTemplateConfig(), context, persona, mock(INotificationSink.class), listener,
				agent.deliverableTemplateParams(DeliverableIntent.ANALISYS), true).collectList().block());
	}

	@Test
	void anAnalysisWrittenWithoutToolsIsDiscardedAndTheSourcesAreSearched() {
		ScriptedLoopAgent agent = toolUsingAgent(
				List.of(List.of("From ", "memory. " + STOP), List.of("From ", "the documents. " + STOP)), Set.of(1));

		String shown = runNeedingEvidence(agent, 5);

		assertEquals("From the documents. ", shown, "the answer without evidence never reaches the user");
		assertEquals(2, agent.receivedParams.size());
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains(AgenticLoopReactiveAgentServiceImpl.DISCARDED_WITHOUT_EVIDENCE),
				"the next iteration knows why the answer was discarded");
	}

	@Test
	void anAnalysisUsingAToolStreamsAsUsual() {
		ScriptedLoopAgent agent = toolUsingAgent(List.of(List.of("From ", "the documents. " + STOP)), Set.of(0));

		assertEquals("From the documents. ", runNeedingEvidence(agent, 5));
		assertEquals(1, agent.receivedParams.size());
	}

	@Test
	void aRequestNeedingTheSourcesStaysHeldUntilItSearchesThemAndTheLastIterationAnswersAnyway() {
		ScriptedLoopAgent stubborn = toolUsingAgent(List.of(List.of("First. " + STOP),
				List.of("Second, still without tools. " + STOP), List.of("Third, still without tools. " + STOP)), Set.of());
		assertEquals("Third, still without tools. ", runNeedingEvidence(stubborn, 3),
				"discarded as long as it does not search, the last iteration shown");
		assertEquals(3, stubborn.receivedParams.size());

		ScriptedLoopAgent searching = toolUsingAgent(
				List.of(List.of("From memory. " + STOP), List.of("From the search. " + STOP)), Set.of(1));
		assertEquals("From the search. ", runNeedingEvidence(searching, 5));
		assertEquals(2, searching.receivedParams.size(), "shown as soon as it searches");

		ScriptedLoopAgent last = toolUsingAgent(List.of(List.of("Only answer. " + STOP)), Set.of());
		assertEquals("Only answer. ", runNeedingEvidence(last, 1), "no iteration left: the answer is shown");
	}

	@Test
	void everyIterationRunsAsTheUser() {
		List<String> calledAs = new java.util.concurrent.CopyOnWriteArrayList<>();
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching. " + MORE), List.of("Done. " + STOP))) {
			@Override
			protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
					IChatRequestContext context, Map<String, Object> params) {
				org.springframework.security.core.Authentication current = org.springframework.security.core.context.SecurityContextHolder
						.getContext().getAuthentication();
				calledAs.add(current != null ? current.getName() : "nobody");
				// the model streams on its own thread, as a provider client does
				return super.callLLMReactive(chatModel, prompt, context, params)
						.publishOn(reactor.core.scheduler.Schedulers.parallel());
			}
		};
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(
				new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("user", "", List.of()));
		try {
			ai.gebo.security.services.ReactiveIdentityUtil runAs = ai.gebo.security.services.ReactiveIdentityUtil.create();
			ToolCallsListener listener = new ToolCallsListener();
			agent.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null, persona,
					mock(INotificationSink.class), listener, agent.deliverableTemplateParams(DeliverableIntent.QA),
					AgenticLoopReactiveAgentServiceImpl.SourceGate.NONE, runAs).collectList().block();
		} finally {
			org.springframework.security.core.context.SecurityContextHolder.clearContext();
		}

		assertEquals(List.of("user", "user"), calledAs, "the second iteration's model call runs as the user");
	}

	@Test
	void notifyingTheUserIsNoEvidence() {
		ScriptedLoopAgent agent = toolUsingAgent(List.of(List.of("From memory. " + STOP), List.of("Searched. " + STOP)),
				Set.of(0), "notifyUser");

		assertEquals("Searched. ", runNeedingEvidence(agent, 2), "a notification is not a search: shown only as the last");
		assertEquals(2, agent.receivedParams.size());
	}

	@Test
	void anAnalysisNeedsTheDeepSearchWhenTheAgentHasOne() {
		ScriptedLoopAgent agent = toolUsingAgent(
				List.of(List.of("From five fragments. " + STOP), List.of("From the deep search. " + STOP)), Set.of(0),
				"searchKnowledgeBase");

		assertEquals("From the deep search. ",
				runNeedingEvidence(agent, 5, Set.of("deepSearchKnowledgeBase", "deepSearchWeb")));
		// Set.of has no iteration order: each name is looked for on its own
		String story = String.valueOf(
				agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM));
		assertTrue(story.contains("deepSearchKnowledgeBase") && story.contains("deepSearchWeb"),
				"the next iteration knows which tools count");
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void theEvidenceToolsComeFromTheSearchSourcesTheAgentMounts() throws Exception {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		IGToolCallbackSource knowledgeBase = source(InternalKnowledgeBaseSearchToolSource.INTERNAL_KNOWLEDGE_BASE_SEARCH_TOOL_SOURCE,
				"searchKnowledgeBase");
		IGToolCallbackSource deep = source(DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE, "deepSearchKnowledgeBase",
				"deepSearchWeb");
		IGToolCallbackSource date = source("ai.gebo.llms.abstraction.layer.functions.ActualDateFunctions", "getDate");
		when(repository.getImplementations()).thenReturn(List.of(knowledgeBase, deep, date));
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, repository, null,
				null, null, null, NO_RENDERER);
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig config = mock(
				ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.class);
		// deepSearchWeb is not mounted by this agent
		when(config.getEnabledFunctions()).thenReturn(List.of("searchKnowledgeBase", "deepSearchKnowledgeBase",
				"getDate", GAbstractGenericalAgentService.NOTIFY_USER_TOOL));
		when(model.getConfig()).thenReturn(config);

		assertEquals(Set.of("deepSearchKnowledgeBase"), agent.evidenceTools(DeliverableIntent.ANALISYS, model));
		assertEquals(Set.of("searchKnowledgeBase", "deepSearchKnowledgeBase"),
				agent.evidenceTools(DeliverableIntent.QA, true, model), "a search asked: any search");
		assertEquals(Set.of(), agent.evidenceTools(DeliverableIntent.QA, model));
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void aDocumentReadWholeIsTheEvidenceOfAnAnalysisAndTheListingOfASearch() throws Exception {
		IGToolCallbackSourceRepositoryPattern repository = mock(IGToolCallbackSourceRepositoryPattern.class);
		IGToolCallbackSource deep = source(DeepSearchToolSource.DEEP_SEARCH_TOOL_SOURCE, "deepSearchKnowledgeBase");
		IGToolCallbackSource browsing = source(
				ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE,
				ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.BROWSE_DOCUMENTS_TOOL,
				ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.DOCUMENT_CONTENTS_TOOL);
		when(repository.getImplementations()).thenReturn(List.of(deep, browsing));
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, repository, null,
				null, null, null, NO_RENDERER);
		IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
		ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig config = mock(
				ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.class);
		when(config.getEnabledFunctions()).thenReturn(List.of("deepSearchKnowledgeBase",
				"browseKnowledgeBaseDocuments", "getKnowledgeBaseDocumentContents"));
		when(model.getConfig()).thenReturn(config);

		assertEquals(Set.of("deepSearchKnowledgeBase", "getKnowledgeBaseDocumentContents"),
				agent.evidenceTools(DeliverableIntent.ANALISYS, model));
		assertEquals(Set.of("deepSearchKnowledgeBase", "browseKnowledgeBaseDocuments", "getKnowledgeBaseDocumentContents"),
				agent.evidenceTools(DeliverableIntent.SUMMARY, true, model));
	}

	private static IGToolCallbackSource source(String id, String... toolNames) {
		IGToolCallbackSource source = mock(IGToolCallbackSource.class);
		when(source.getId()).thenReturn(id);
		List<org.springframework.ai.tool.ToolCallback> tools = new ArrayList<>();
		for (String name : toolNames) {
			org.springframework.ai.tool.ToolCallback tool = mock(org.springframework.ai.tool.ToolCallback.class);
			when(tool.getToolDefinition()).thenReturn(
					org.springframework.ai.tool.definition.ToolDefinition.builder().name(name).description(name)
							.inputSchema("{}").build());
			tools.add(tool);
		}
		when(source.getToolCallbacks()).thenReturn(tools);
		return source;
	}

	@Test
	void theCitationsOfDocumentsNotReadForTheAnswerAreFound() {
		String answer = "As pistis_sophia_svelato.pdf says (cap. 3), and as Vangeli-non-canonici.pdf and "
				+ "The-Secret-Doctrine-1-of-4.PDF confirm; see also www.gnosis.org and Vangeli-non-canonici.pdf.";

		assertEquals(List.of("Vangeli-non-canonici.pdf", "The-Secret-Doctrine-1-of-4.PDF"),
				AgenticLoopReactiveAgentServiceImpl.unreadCitations(answer, List.of("pistis_sophia_svelato.pdf")));
		assertEquals(List.of(), AgenticLoopReactiveAgentServiceImpl.unreadCitations(answer,
				List.of("Pistis_Sophia_Svelato.pdf", "vangeli-non-canonici.pdf", "The-Secret-Doctrine-1-of-4.pdf")));
		assertEquals(List.of(), AgenticLoopReactiveAgentServiceImpl.unreadCitations("No document cited.", List.of()));
		assertEquals(List.of(), AgenticLoopReactiveAgentServiceImpl.unreadCitations("(see Report.pdf)",
				List.of("My Report.pdf")), "a name with spaces, cited by its last part");
	}

	@Test
	void theUserIsWarnedOfTheCitationsNotReadAndTheAnswerIsLeftAsItIs() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
		response.setQueryResponse("From a.pdf and b.pdf.");
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef found = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef();
		found.setName("a.pdf");
		response.setDocumentsRef(new ArrayList<>(List.of(found)));

		agent.warnAboutUnreadCitations(response, IChatRequestContext.builder().requestID("r1").build());

		assertEquals("From a.pdf and b.pdf.", response.getQueryResponse());
		assertEquals(1, response.getBackendMessages().size());
		assertTrue(response.getBackendMessages().get(0).getDetail().contains("b.pdf"));
		assertFalse(response.getBackendMessages().get(0).getDetail().contains("a.pdf"));
	}

	@Test
	void theAddressesAnAnswerMayGiveAreTheToolsTheUserAndTheChatOnes() {
		ToolCallsListener listener = new ToolCallsListener();
		AgenticLoopReactiveAgentServiceImpl.KnownAddresses known = new AgenticLoopReactiveAgentServiceImpl.KnownAddresses(
				IChatRequestContext.builder().requestID("r1").actualUserRequest("Look at https://user.example/page")
						.consolidatedHistory("Earlier: https://chat.example/old").build(),
				listener, null);

		assertFalse(known.isKnown("https://www.postgresql.org/download/"));
		listener.addCall("searchWeb", "web", "{}", "{\"fragments\":[{\"source\":\"https://www.postgresql.org/docs/release/\"}],"
				+ "\"documentsNotRead\":[{\"source\":\"https://www.postgresql.org/about/press/\",\"reason\":\"x\"}]}");

		assertTrue(known.isKnown("https://www.postgresql.org/docs/release"), "re-read as the tools are called");
		assertFalse(known.isKnown("https://www.postgresql.org/about/press/"), "a document not read is not citable");
		assertFalse(known.isKnown("https://www.postgresql.org/download/"));
		assertTrue(known.isKnown("https://user.example/page"));
		assertTrue(known.isKnown("https://chat.example/old"));
	}

	@Test
	void theUserIsWarnedOfAnAnswerThatNeededTheSourcesAndSearchedNone() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
		ToolsFoundDocuments collector = new ToolsFoundDocuments();

		agent.warnAboutAnswerWithoutSearch(response, collector);
		assertTrue(response.getBackendMessages() == null || response.getBackendMessages().isEmpty());

		collector.markAnsweredWithoutSearch();
		agent.warnAboutAnswerWithoutSearch(response, collector);
		assertEquals(1, response.getBackendMessages().size());
		assertEquals("No source searched for this answer", response.getBackendMessages().get(0).getSummary());
		assertTrue(AgenticLoopReactiveAgentServiceImpl.collectorOf(collector.sharedThrough(
				IChatRequestContext.builder().requestID("r1").toolsContext(Map.of()).build())) == collector);
	}

	@Test
	void theUserIsWarnedOfTheAddressesRemovedFromTheAnswer() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();

		agent.warnAboutRemovedAddresses(response, new java.util.LinkedHashSet<>());
		assertTrue(response.getBackendMessages() == null || response.getBackendMessages().isEmpty());

		agent.warnAboutRemovedAddresses(response, new java.util.LinkedHashSet<>(List.of("https://www.postgresql.org/download/")));
		assertEquals(1, response.getBackendMessages().size());
		assertTrue(response.getBackendMessages().get(0).getDetail().contains("https://www.postgresql.org/download/"));
	}

	/** A web search result as the web search services make it: named after its site. */
	private static ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef webPage(String site,
			String uri) {
		ai.gebo.architecture.search.model.SearchResult result = new ai.gebo.architecture.search.model.SearchResult();
		result.setResultReference(new ai.gebo.architecture.search.model.SearchResultReference());
		result.getResultReference().setUri(uri);
		result.getResultReference().setName(site);
		result.setId(uri);
		return new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef(result);
	}

	@Test
	void aWebPageReadIsCitableByTheFileNameOfItsAddress() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
		response.setQueryResponse("See preview-list.html, Guida%20utente.html and other-page.html.");
		response.setDocumentsRef(new ArrayList<>(List.of(
				webPage("docs.oracle.com", "https://docs.oracle.com/en/java/javase/25/docs/api/preview-list.html?x=1#top"),
				webPage("example.org", "https://example.org/docs/Guida%20utente.html"))));

		agent.warnAboutUnreadCitations(response, IChatRequestContext.builder().requestID("r1").build(), null);

		assertEquals(1, response.getBackendMessages().size());
		String detail = response.getBackendMessages().get(0).getDetail();
		assertTrue(detail.contains("other-page.html"), detail);
		assertFalse(detail.contains("preview-list.html"), detail);
		assertFalse(detail.contains("Guida"), detail);
		assertTrue(AgenticLoopReactiveAgentServiceImpl.citableNames(response.getDocumentsRef().get(1))
				.contains("https://example.org/docs/Guida utente.html"));
	}

	@Test
	void theDocumentsListedByTheToolsAreCitableTooAndTheBrowsingToolsAreEvidence() {
		AgenticLoopReactiveAgentServiceImpl agent = new AgenticLoopReactiveAgentServiceImpl(null, null, null, null,
				null, null, NO_RENDERER);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse response = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse();
		response.setQueryResponse("The knowledge base holds a.pdf and b.pdf.");
		ToolsFoundDocuments toolDocuments = new ToolsFoundDocuments();
		toolDocuments.addListed(List.of("a.pdf", "b.pdf"));

		agent.warnAboutUnreadCitations(response, IChatRequestContext.builder().requestID("r1").build(), toolDocuments);

		assertTrue(response.getBackendMessages() == null || response.getBackendMessages().isEmpty(),
				String.valueOf(response.getBackendMessages()));
		assertTrue(AgenticLoopReactiveAgentServiceImpl.EVIDENCE_TOOL_SOURCES.contains(
				ai.gebo.llms.agent.standardtools.KnowledgeBaseBrowsingToolSource.KNOWLEDGE_BASE_BROWSING_TOOL_SOURCE));
	}

	@Test
	void theChatRulesAreGivenNextToTheQuestion() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Risposta. " + STOP)));
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.builder().requestID("r1")
				.rulesToFollow(List.of("Always answer in Italian")).build();

		agent.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), context, persona,
				mock(INotificationSink.class), new ToolCallsListener()).collectList().block();

		assertTrue(String.valueOf(agent.receivedParams.get(0).get(AgenticLoopReactiveAgentServiceImpl.RULES_TO_FOLLOW_PARAM))
				.contains("- Always answer in Italian"));
		assertEquals("none", AgenticLoopReactiveAgentServiceImpl
				.rulesToFollow(IChatRequestContext.builder().requestID("r2").build()));
	}

	/** Runs the loop on a direct question, its answer checked for documents cited without being read. */
	private static String runCheckingCitations(ScriptedLoopAgent agent, List<String> chatDocuments) {
		ToolCallsListener listener = new ToolCallsListener();
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.forAgent(IChatRequestContext.builder().requestID("r1").build(),
				listener);
		return String.join("", agent.iteration(1, 5, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(),
				context, persona, mock(INotificationSink.class), listener, agent.deliverableTemplateParams(DeliverableIntent.QA),
				new AgenticLoopReactiveAgentServiceImpl.SourceGate(Set.of("searchKnowledgeBase"), false, chatDocuments),
				null).collectList().block());
	}

	@Test
	void anAnswerCitingDocumentsItDidNotReadIsDoneAgain() {
		ScriptedLoopAgent agent = toolUsingAgent(
				List.of(List.of("As history.pdf says. " + STOP), List.of("As the search found. " + STOP)), Set.of(1),
				"searchKnowledgeBase");

		assertEquals("As the search found. ", runCheckingCitations(agent, List.of()));
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains("history.pdf"), "the next iteration knows which citation was not read");
	}

	@Test
	void anAnswerCitingNothingOrTheChatDocumentsIsShownAsItIs() {
		ScriptedLoopAgent noCitation = toolUsingAgent(List.of(List.of("17 x 23 is ", "391. " + STOP)), Set.of());
		assertEquals("17 x 23 is 391. ", runCheckingCitations(noCitation, List.of()));
		assertEquals(1, noCitation.receivedParams.size());

		ScriptedLoopAgent chatDocument = toolUsingAgent(List.of(List.of("As chosen.pdf says. " + STOP)), Set.of());
		assertEquals("As chosen.pdf says. ", runCheckingCitations(chatDocument, List.of("chosen.pdf")));
		assertEquals(1, chatDocument.receivedParams.size());
	}

	@Test
	void onlyAnalysesAndSearchesNeedTheSourcesEvidence() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.ANALISYS));
		assertTrue(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.QA, true),
				"the user asked to search, whatever the deliverable");
		assertFalse(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.QA, false));
		assertFalse(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.QA));
		assertFalse(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.SUMMARY));
	}

	@Test
	void theToolsOfAnIterationReachTheNextOneAndTheUserRequest() {
		ToolCallsListener request = new ToolCallsListener();
		IChatRequestContext requestContext = IChatRequestContext.builder().requestID("r1").toolCallListener(request)
				.build();
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Searching. " + MORE), List.of("4. " + STOP))) {
			@Override
			protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
					IChatRequestContext context, Map<String, Object> params) {
				if (receivedParams.isEmpty()) {
					// what the model's tool wrapper does: record into the context's listener
					context.getToolCallListener().addCall("searchWeb", "Search the web", "{\"query\":\"q\"}", "x");
				}
				return super.callLLMReactive(chatModel, prompt, context, params);
			}
		};
		ToolCallsListener agentListener = agent.listenerFor(requestContext);
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");

		agent.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(),
				IChatRequestContext.forAgent(requestContext, agentListener), persona, mock(INotificationSink.class),
				agentListener).collectList().block();

		assertEquals(2, agent.receivedParams.size());
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains("searchWeb"), "the second iteration knows the tool the first one called");
		assertEquals(1, agentListener.getCalls().size());
		assertEquals(1, request.getCalls().size(), "the user request collects the agent's call");
	}

	@Test
	void bothRequestContextsCarryTheRecorderAndTheRequestId() {
		GeboChatRequest chatRequest = new GeboChatRequest();
		chatRequest.setQuery("question");
		ToolCallsListener request = new ToolCallsListener();
		LLMChatRequestResources resources = new LLMChatRequestResources(null, null, null, null,
				new CSSConsolidatedChatHistory(), chatRequest, null);
		resources.setToolCallsListener(request);
		MinimalChatContext minimal = new MinimalChatContext();
		minimal.setCurrentRequest(chatRequest);
		minimal.setToolCallsListener(request);

		for (IChatRequestContext context : List.of(resources.createChatRequestContext(),
				minimal.createChatRequestContext())) {
			assertSame(request, context.getToolCallListener());
			assertEquals(chatRequest.getId(), context.getToolsContext().get(ToolCallbackDeclarationUtil.REQUEST_ID_CONTEXT_KEY));
		}
	}

	@Test
	void withoutAClassifiedIntentTheLoopAsksForASummary() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		AgentsCollaborationSessionContext session = mock(AgentsCollaborationSessionContext.class);
		when(session.getEnvironment()).thenReturn(new HashMap<>());

		assertEquals(DeliverableIntent.SUMMARY, agent.sessionUserIntent(session));
	}

	@Test
	void theToolsOfAnIterationMayTakeWhatTheLoopBudgetLeaves() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("Done. " + STOP)));
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		IChatRequestContext context = collector.sharedThrough(IChatRequestContext.builder().requestID("r1").build());
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");

		agent.iteration(1, 5, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), context, persona,
				mock(INotificationSink.class), new ToolCallsListener(),
				agent.deliverableTemplateParams(DeliverableIntent.QA),
				AgenticLoopReactiveAgentServiceImpl.SourceGate.NONE, null).collectList().block();

		Object shared = agent.receivedContexts.get(0).getToolsContext().get(ToolsTokenBudget.TOOLS_CONTEXT_KEY);
		assertTrue(shared instanceof ToolsTokenBudget, "the model call carries the tools' room");
		int left = ((ToolsTokenBudget) shared).left();
		assertEquals(ToolsTokenBudget.leftForTools(10_000, agent.receivedParams.get(0)), left);
		assertTrue(left > 0 && left < 10_000, "the loop budget less the iteration's own placeholders: " + left);
		assertTrue(agent.receivedContexts.get(0).getToolsContext().get(ToolsFoundDocuments.TOOLS_CONTEXT_KEY) != null,
				"what the context already shared with the tools is kept");
	}

	// ---------------------------------------------------------------- coverage gate

	private static final String THIN = "{\"status\":\"OK\",\"message\":null,\"coverage\":{\"completionRequired\":true,"
			+ "\"note\":\"The coverage of this deep search is thin: the analysis rests on 1 of the 6 documents found.\","
			+ "\"documentsFound\":6,\"documentsUsed\":1},\"fragmentsAnalysed\":23,\"analysis\":\"Steiner says...\"}";
	private static final String COVERED = "{\"status\":\"OK\",\"coverage\":{\"completionRequired\":false,\"note\":null},"
			+ "\"analysis\":\"...\"}";

	private static AgenticLoopReactiveAgentServiceImpl.CoverageGate coverageGate() {
		return new AgenticLoopReactiveAgentServiceImpl.CoverageGate(
				Set.of("searchKnowledgeBase", "getKnowledgeBaseDocumentContents", "deepSearchKnowledgeBase", "searchWeb",
						"deepSearchWeb"),
				Set.of("deepSearchKnowledgeBase", "deepSearchWeb"),
				Set.of("searchKnowledgeBase", "getKnowledgeBaseDocumentContents", "deepSearchKnowledgeBase"));
	}

	@Test
	void theCoverageIsReadFromTheDeepSearchResultEvenWhenTheResultIsCut() {
		AgenticLoopReactiveAgentServiceImpl.CoverageVerdict thin = AgenticLoopReactiveAgentServiceImpl.coverageOf(THIN);
		assertTrue(thin.completionRequired());
		assertTrue(thin.note().contains("rests on 1 of the 6"), thin.note());

		AgenticLoopReactiveAgentServiceImpl.CoverageVerdict cut = AgenticLoopReactiveAgentServiceImpl
				.coverageOf(THIN.substring(0, THIN.indexOf("\"analysis\"") + 15));
		assertTrue(cut.completionRequired(), "a result cut to the room keeps the coverage written first");
		assertTrue(cut.note().contains("rests on 1 of the 6"), cut.note());

		assertFalse(AgenticLoopReactiveAgentServiceImpl.coverageOf(COVERED).completionRequired());
		assertNull(AgenticLoopReactiveAgentServiceImpl.coverageOf("{\"status\":\"OK\",\"analysis\":\"x\"}"));
		assertNull(AgenticLoopReactiveAgentServiceImpl.coverageOf("not json at all"));
		assertNull(AgenticLoopReactiveAgentServiceImpl.coverageOf(null));
	}

	@Test
	void aThinCoverageIsCompletedOnlyByASearchOfTheSameKindOfSource() {
		ToolCallsListener listener = new ToolCallsListener();
		listener.addCall("deepSearchKnowledgeBase", "deep", "{}", THIN);
		assertTrue(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()) != null);

		listener.addCall("searchWeb", "web", "{}", "found");
		assertTrue(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()) != null,
				"a web search does not complete the knowledge base");

		listener.addCall("getKnowledgeBaseDocumentContents", "read", "{}", "text");
		assertNull(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()));

		ToolCallsListener covered = new ToolCallsListener();
		covered.addCall("deepSearchWeb", "deep", "{}", COVERED);
		assertNull(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(covered, 0, coverageGate()));
		assertNull(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 3, coverageGate()),
				"only the calls of the iteration count");
	}

	@Test
	void aRefusedOrFailedDeepSearchDoesNotCompleteTheCoverage() {
		ToolCallsListener listener = new ToolCallsListener();
		listener.addCall("deepSearchKnowledgeBase", "deep", "{}", THIN);
		// the same searches again: refused, it did not run
		listener.addCall("deepSearchKnowledgeBase", "deep", "{}",
				"{\"status\":\"NOT_ALLOWED\",\"message\":\"A deep search of this request already ran these searches\"}");
		assertTrue(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()) != null);
		// nor does a failed one
		listener.addCall("deepSearchKnowledgeBase", "deep", "{}",
				"{\"status\":\"FAILED\",\"message\":\"The documents found could not be analysed\"}");
		assertTrue(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()) != null);

		// a deep search that ran with other searches and found what was missing
		listener.addCall("deepSearchKnowledgeBase", "deep", "{}", COVERED);
		assertNull(AgenticLoopReactiveAgentServiceImpl.pendingCoverage(listener, 0, coverageGate()));
		assertFalse(AgenticLoopReactiveAgentServiceImpl.didNotRun(COVERED));
	}

	/** A loop agent whose model calls make the given tool calls (name and result) before writing. */
	private static ScriptedLoopAgent callingAgent(List<List<String>> answers, Map<Integer, List<String[]>> callsByModelCall) {
		return new ScriptedLoopAgent(answers) {
			@Override
			protected Flux<String> callLLMReactive(IGConfigurableChatModel chatModel, GPromptTemplateConfig prompt,
					IChatRequestContext context, Map<String, Object> params) {
				final int call = receivedParams.size();
				final Flux<String> text = super.callLLMReactive(chatModel, prompt, context, params);
				final List<String[]> calls = callsByModelCall.get(call);
				if (calls == null) {
					return text;
				}
				return Flux.defer(() -> {
					for (String[] toolCall : calls) {
						context.getToolCallListener().addCall(toolCall[0], "A tool", "{}", toolCall[1]);
					}
					return text;
				});
			}
		};
	}

	private static String runWithCoverageGate(ScriptedLoopAgent agent, int maxIterations) {
		ToolCallsListener listener = new ToolCallsListener();
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.forAgent(IChatRequestContext.builder().requestID("r1").build(),
				listener);
		return String.join("", agent.iteration(1, maxIterations, 10_000, new ArrayList<>(), null,
				new GPromptTemplateConfig(), context, persona, mock(INotificationSink.class), listener,
				agent.deliverableTemplateParams(DeliverableIntent.ANALISYS),
				new AgenticLoopReactiveAgentServiceImpl.SourceGate(
						Set.of("deepSearchKnowledgeBase", "searchKnowledgeBase", "getKnowledgeBaseDocumentContents"), true,
						List.of(), coverageGate()),
				null).collectList().block());
	}

	@Test
	void anAnswerOnAThinCoverageIsDiscardedOnceAndTheNextIterationIsToldWhatToComplete() {
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("From Steiner only. " + STOP), List.of("From all the authors. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN }), 1,
						List.<String[]>of(new String[] { "searchKnowledgeBase", "found" })));

		String shown = runWithCoverageGate(agent, 5);

		assertEquals("From all the authors. ", shown, "the answer on the thin coverage never reaches the user");
		assertEquals(2, agent.receivedParams.size());
		String story = String.valueOf(
				agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM));
		assertTrue(story.contains(AgenticLoopReactiveAgentServiceImpl.DISCARDED_THIN_COVERAGE), story);
		assertTrue(story.contains("rests on 1 of the 6"), story);
	}

	@Test
	void afterACoverageDiscardTheRequestHasItsEvidenceAndAnAnswerWithoutANewSearchIsShown() {
		// the sources were searched in the first iteration: the second one, building on its
		// draft without completing the coverage, is not discarded again for lack of evidence
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("From Steiner only. " + STOP), List.of("The draft, rewritten. " + STOP),
						List.of("Never asked. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN })));

		String shown = runWithCoverageGate(agent, 5);

		assertEquals("The draft, rewritten. ", shown);
		assertEquals(2, agent.receivedParams.size(), "no iteration spent on an answer resting on the first search");
	}

	@Test
	void afterACoverageDiscardAnAnswerCitingDocumentsNotReadIsStillDiscarded() {
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("From Steiner only. " + STOP), List.of("As never-read.pdf says. " + STOP),
						List.of("Without it. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN })));

		String shown = runWithCoverageGate(agent, 5);

		assertEquals("Without it. ", shown);
		assertEquals(3, agent.receivedParams.size());
	}

	@Test
	void anAnswerThatCompletesTheCoverageOrRestsOnAGoodOneStreamsAsUsual() {
		ScriptedLoopAgent completed = callingAgent(List.of(List.of("Complete. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN },
						new String[] { "getKnowledgeBaseDocumentContents", "text" })));
		assertEquals("Complete. ", runWithCoverageGate(completed, 5));
		assertEquals(1, completed.receivedParams.size());

		ScriptedLoopAgent covered = callingAgent(List.of(List.of("Covered. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", COVERED })));
		assertEquals("Covered. ", runWithCoverageGate(covered, 5));
		assertEquals(1, covered.receivedParams.size());
	}

	@Test
	void onTheLastIterationTheAnswerOnAThinCoverageIsShown() {
		ScriptedLoopAgent last = callingAgent(List.of(List.of("Only answer. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN })));

		assertEquals("Only answer. ", runWithCoverageGate(last, 1));
	}

	// ---------------------------------------------------------------- coverage for every deliverable, every iteration

	private static String runWithGate(ScriptedLoopAgent agent, int maxIterations,
			AgenticLoopReactiveAgentServiceImpl.SourceGate gate) {
		ToolCallsListener listener = new ToolCallsListener();
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");
		IChatRequestContext context = IChatRequestContext.forAgent(IChatRequestContext.builder().requestID("r1").build(),
				listener);
		return String.join("", agent.iteration(1, maxIterations, 10_000, new ArrayList<>(), null,
				new GPromptTemplateConfig(), context, persona, mock(INotificationSink.class), listener,
				agent.deliverableTemplateParams(DeliverableIntent.DECISION), gate, null).collectList().block());
	}

	private static final Set<String> SEARCHES = Set.of("deepSearchKnowledgeBase", "searchKnowledgeBase",
			"getKnowledgeBaseDocumentContents");

	@Test
	void aDecisionOnAThinCoverageIsCompletedAsAnAnalysisIs() {
		AgenticLoopReactiveAgentServiceImpl.SourceGate gate = new AgenticLoopReactiveAgentServiceImpl.SourceGate(
				SEARCHES, false, List.of(), coverageGate());
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("Pick the first. " + STOP), List.of("Pick the third, all compared. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN }), 1,
						List.<String[]>of(new String[] { "searchKnowledgeBase", "found" })));

		assertEquals("Pick the third, all compared. ", runWithGate(agent, 5, gate));
		assertNull(gate.coverage().state().notCompleted(), "completed by the second iteration's search");
	}

	@Test
	void aDeepSearchMadeAfterAContinueIsCoverageCheckedToo() {
		AgenticLoopReactiveAgentServiceImpl.SourceGate gate = new AgenticLoopReactiveAgentServiceImpl.SourceGate(
				SEARCHES, true, List.of(), coverageGate());
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("Part one. " + MORE), List.of("Part two on a thin coverage. " + STOP),
						List.of("Part two completed. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "searchKnowledgeBase", "found" }), 1,
						List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN }), 2,
						List.<String[]>of(new String[] { "searchKnowledgeBase", "found" })));

		String shown = runWithGate(agent, 5, gate);

		assertTrue(shown.startsWith("Part one. "), shown);
		assertTrue(shown.endsWith("Part two completed. "), shown);
		assertFalse(shown.contains("thin coverage"), "the second part on the thin coverage is redone: " + shown);
	}

	@Test
	void theAnswerIsRedoneOncePerRequestThenShownWithWhatTheCoverageMisses() {
		AgenticLoopReactiveAgentServiceImpl.SourceGate gate = new AgenticLoopReactiveAgentServiceImpl.SourceGate(
				SEARCHES, true, List.of(), coverageGate());
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("First draft. " + STOP), List.of("Second draft, thin again. " + STOP),
						List.of("Never asked. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN }), 1,
						List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN })));

		assertEquals("Second draft, thin again. ", runWithGate(agent, 5, gate));
		assertEquals(2, agent.receivedParams.size(), "redone once per request");
		assertTrue(gate.coverage().state().notCompleted() != null, "the user is told what the coverage misses");
	}

	@Test
	void aRedoThatDoesNotSearchLeavesTheCoverageToTell() {
		AgenticLoopReactiveAgentServiceImpl.SourceGate gate = new AgenticLoopReactiveAgentServiceImpl.SourceGate(
				SEARCHES, true, List.of(), coverageGate());
		ScriptedLoopAgent agent = callingAgent(
				List.of(List.of("From Steiner only. " + STOP), List.of("The draft, rewritten. " + STOP)),
				Map.of(0, List.<String[]>of(new String[] { "deepSearchKnowledgeBase", THIN })));

		assertEquals("The draft, rewritten. ", runWithGate(agent, 5, gate));
		assertTrue(gate.coverage().state().notCompleted().contains("rests on 1 of the 6"));
	}

	@Test
	void aUniqueIdGivenAsADocumentIdNamesItsDocument() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds uniqueIds = mock(
				ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds.class);
		when(uniqueIds.documentUniqueId("kb/doctrine.pdf")).thenReturn(12L);
		agent.setUniqueIds(uniqueIds);
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef doctrine = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef();
		doctrine.setDocumentCode("kb/doctrine.pdf");
		List<String> unknown = new ArrayList<>(List.of("#12", "#99"));
		List<ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef> listed = new ArrayList<>();

		agent.byUniqueId(List.of(doctrine), unknown, listed);

		assertEquals(List.of(doctrine), listed);
		assertEquals(List.of("#99"), unknown);
	}

	@Test
	void aFileNameInsideAnAddressIsNoCitation() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl
				.unreadCitations("See https://www.rsi.ch/info/La-fusione-realt%C3%A0--3225783.html for it.", List.of())
				.isEmpty());
		assertEquals(List.of("report.pdf"),
				AgenticLoopReactiveAgentServiceImpl.unreadCitations("As report.pdf says.", List.of()));
	}

	@Test
	void anIdThatIsAlsoAUniqueIdNamesTheDocumentTheAnswerCites() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds uniqueIds = mock(
				ai.gebo.knowledgebase.repositories.uniqueid.VirtualFilesystemUniqueIds.class);
		ToolsFoundDocuments collector = new ToolsFoundDocuments();
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef first = webPage("www.a.org", "https://www.a.org/x");
		ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef senses = new ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef();
		senses.setDocumentCode("kb/twelve-senses.pdf");
		senses.setName("twelve-senses.pdf");
		collector.add(List.of(first, senses));
		when(uniqueIds.documentUniqueId("kb/twelve-senses.pdf")).thenReturn(1L);
		agent.setUniqueIds(uniqueIds);
		List<String> unknown = new ArrayList<>();

		// #1 is the web page's id and the knowledge base document's uniqueId: the answer cites the document
		List<ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef> listed = agent.resolveAnswerIds(
				collector, collector.getDocuments(), List.of("#1"), "As twelve-senses.pdf says.", unknown);

		assertEquals(List.of(senses), listed);
		assertTrue(unknown.isEmpty());
		// citing neither, the id is the request's
		assertEquals(List.of(first), agent.resolveAnswerIds(collector, collector.getDocuments(), List.of("#1"),
				"No citation.", unknown));
	}

	@Test
	void aFileNameCitedWithItsWordsSpacedIsTheDocumentRead() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl
				.unreadCitations("From Frammenti di un insegnamento sconosciuto.pdf.",
						List.of("Frammentidiuninsegnamentosconosciuto.pdf"))
				.isEmpty());
		assertTrue(AgenticLoopReactiveAgentServiceImpl
				.unreadCitations("From nicola rosti - febbraio 2014.pdf.", List.of("nicola rosti - febbraio 2014.pdf"))
				.isEmpty());
		assertEquals(List.of("other.pdf"),
				AgenticLoopReactiveAgentServiceImpl.unreadCitations("From other.pdf.", List.of("document.pdf")));
	}

	// ---------------------------------------------------------------- the documents line

	private static String strip(ControlMarkerStripper stripper, String... chunks) {
		StringBuilder shown = new StringBuilder();
		for (String chunk : chunks) {
			shown.append(stripper.accept(chunk));
		}
		return shown.append(stripper.complete()).toString();
	}

	@Test
	void theDocumentsLineIsRemovedWhateverFormTheModelGivesIt() {
		ControlMarkerStripper sequence = new ControlMarkerStripper();
		assertEquals("The answer.\n\n", strip(sequence, "The answer.\n\n@@DO", "CS@@ #1 #", "4\n", STOP));
		assertEquals(List.of("#1", "#4"), sequence.getAnswerDocuments());
		assertTrue(sequence.isFinishRequested());

		ControlMarkerStripper tag = new ControlMarkerStripper();
		assertEquals("The answer. ", strip(tag, "The answer. <ANSWER-DOCUMENTS>#2</ANSWER-DOCUMENTS>", STOP));
		assertEquals(List.of("#2"), tag.getAnswerDocuments());

		// the bare word, the ids on the next line, nothing else: what a model wrote
		ControlMarkerStripper bare = new ControlMarkerStripper();
		assertEquals("Tutte tratte dal documento.\n\n",
				strip(bare, "Tutte tratte dal documento.\n\nANSWER-DOCUMENTS\n#", "5\n\n"));
		assertEquals(List.of("#5"), bare.getAnswerDocuments());
	}

	@Test
	void theWordInTheTextWithoutIdsIsText() {
		ControlMarkerStripper stripper = new ControlMarkerStripper();
		assertEquals("The ANSWER-DOCUMENTS line is internal. ",
				strip(stripper, "The ANSWER-DOCUMENTS line is internal. ", STOP));
		assertEquals(null, stripper.getAnswerDocuments());
	}

	// ---------------------------------------------------------------- the answer's language

	@Test
	void theAnswersLanguageIsTheDetectedOneOrLeftToTheModel() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of("x")));
		AgentsCollaborationSessionContext session = mock(AgentsCollaborationSessionContext.class);
		Map<String, Object> environment = new HashMap<>();
		when(session.getEnvironment()).thenReturn(environment);

		assertEquals(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_UNDETECTED, agent.sessionUserLanguage(session));
		assertEquals(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_UNDETECTED, agent.sessionUserLanguage(null));
		environment.put(StandardAgentsNetworkEnvironmentEntries.USER_LANGUAGE, "English");
		assertEquals("English", agent.sessionUserLanguage(session));
	}

	@Test
	void everyIterationNamesTheAnswersLanguage() {
		AgentNetworkParticipant persona = mock(AgentNetworkParticipant.class);
		when(persona.getNetworkAgentName()).thenReturn("agenticLoopAgent");

		ScriptedLoopAgent named = new ScriptedLoopAgent(List.of(List.of("4. " + STOP)));
		Map<String, Object> deliverable = new HashMap<>(named.deliverableTemplateParams(DeliverableIntent.QA));
		deliverable.put(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_PARAM, "English");
		named.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null, persona,
				mock(INotificationSink.class), new ToolCallsListener(), deliverable).collectList().block();
		assertEquals("English", named.receivedParams.get(0).get(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_PARAM));

		// a loop started without the session's language still renders the prompts naming it
		ScriptedLoopAgent unnamed = new ScriptedLoopAgent(List.of(List.of("4. " + STOP)));
		unnamed.iteration(1, 3, 10_000, new ArrayList<>(), null, new GPromptTemplateConfig(), null, persona,
				mock(INotificationSink.class), new ToolCallsListener()).collectList().block();
		assertEquals(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_UNDETECTED,
				unnamed.receivedParams.get(0).get(AgenticLoopReactiveAgentServiceImpl.USER_LANGUAGE_PARAM));
	}

	// ---------------------------------------------------------------- an iteration writing no text

	@Test
	void anIterationWritingNoTextIsWrittenOnceMore() {
		// the model's output cut before any text: only the control marker arrives
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of(STOP), List.of("4. " + STOP)));

		assertEquals("4. ", run(agent, 3));
		assertEquals(2, agent.receivedParams.size(), "written once more");
		assertTrue(String.valueOf(agent.receivedParams.get(1).get(ReportWriterReactiveAgentServiceImpl.AGENT_SESSION_STORY_PROMPT_PARAM))
				.contains(AgenticLoopReactiveAgentServiceImpl.EMPTY_ANSWER_STORY), "the next iteration is told why");
	}

	@Test
	void anIterationWritingNoTextAgainEndsTheLoop() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of(STOP), List.of(STOP), List.of("never " + STOP)));

		assertEquals("", run(agent, 5));
		assertEquals(2, agent.receivedParams.size(), "once more, not more");
	}

	@Test
	void theLastIterationWritingNoTextIsNotWrittenAgain() {
		ScriptedLoopAgent agent = new ScriptedLoopAgent(List.of(List.of(STOP), List.of("never " + STOP)));

		assertEquals("", run(agent, 1));
		assertEquals(1, agent.receivedParams.size());
	}
}
