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
				agent.deliverableTemplateParams(DeliverableIntent.ANALISYS), evidenceTools, null).collectList().block());
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
	void theRetryIsMadeOnceAndTheLastIterationAnswersAnyway() {
		ScriptedLoopAgent once = toolUsingAgent(
				List.of(List.of("First. " + STOP), List.of("Second, still without tools. " + STOP)), Set.of());
		assertEquals("Second, still without tools. ", runNeedingEvidence(once, 5));
		assertEquals(2, once.receivedParams.size(), "one retry only");

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
					mock(INotificationSink.class), listener, agent.deliverableTemplateParams(DeliverableIntent.QA), Set.of(),
					runAs).collectList().block();
		} finally {
			org.springframework.security.core.context.SecurityContextHolder.clearContext();
		}

		assertEquals(List.of("user", "user"), calledAs, "the second iteration's model call runs as the user");
	}

	@Test
	void notifyingTheUserIsNoEvidence() {
		ScriptedLoopAgent agent = toolUsingAgent(List.of(List.of("From memory. " + STOP), List.of("Searched. " + STOP)),
				Set.of(0), "notifyUser");

		assertEquals("Searched. ", runNeedingEvidence(agent, 5), "a notification is not a search");
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
				agent.evidenceTools(DeliverableIntent.PURE_SEARCH, model));
		assertEquals(Set.of(), agent.evidenceTools(DeliverableIntent.QA, model));
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

	@Test
	void onlyAnalysesAndSearchesNeedTheSourcesEvidence() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.ANALISYS));
		assertTrue(AgenticLoopReactiveAgentServiceImpl.needsEvidence(DeliverableIntent.PURE_SEARCH));
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
}
