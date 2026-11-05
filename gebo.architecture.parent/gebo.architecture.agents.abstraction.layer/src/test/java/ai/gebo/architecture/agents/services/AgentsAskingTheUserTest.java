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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentCapabilities;
import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.model.RuntimeAgentInfos;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * The agents that can ask the user beside the tool-calling one: a task performer,
 * a routing agent and a documents search agent suspend the run when a tool of
 * theirs declares a question, say nothing, and are told the answer when they are
 * re-executed. An agent whose tools declare nothing goes on as always.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class AgentsAskingTheUserTest {
	private static final String QUESTION = "{\"question\":\"Which year?\",\"options\":[{\"label\":\"2023\"},{\"label\":\"2024\"}]}";
	private static final String TOLD = "You asked the user: Which year?\nThe user chose: 2024\n\n";
	private final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final IGAgentsNetworkRuntimeDao agentsDao = mock(IGAgentsNetworkRuntimeDao.class);
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentConfig config = new GAgentConfig();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");
	private final List<Object> inputsGivenToTheModel = new ArrayList<Object>();

	@BeforeEach
	void setUp() {
		me.setAgentConfigCode("the-agent");
		me.setAllowedToNotifyUser(false);
		config.setCode("the-agent");
		network.setCode("the-network");
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
	}

	private final class Performer extends GBaseTaskPerformerNetworkAgentService<String, String> {
		private Performer() {
			super(null, null, null, null, mock(IAgentRoleDao.class), null, String.class, String.class, "performer",
					"performer", null);
		}

		@Override
		protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
				INotificationSink notificationSink, ReactiveIdentityUtil runAs) throws LLMConfigException {
			return model;
		}

		@Override
		protected GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
				throws AgentException {
			return new GPromptTemplateConfig();
		}

		@Override
		protected int agentTokenBudget(IGConfigurableChatModel agentModel, GPromptTemplateConfig prompt,
				IChatRequestContext chatRequestContext) {
			return 1000;
		}

		@Override
		protected <I, O> Map<String, Object> createAgentTemplateParams(GPromptTemplateConfig prompt,
				GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
				AgentsCollaborationSessionContext session, AgentPrivateSessionContext<I, O> mySessionContext,
				Object input, IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget) {
			inputsGivenToTheModel.add(input);
			return new LinkedHashMap<String, Object>();
		}
	}

	private final class Router extends GBaseRoutingNetworkAgentService<String, Void> {
		private Router() {
			super(null, null, null, null, mock(IAgentRoleDao.class), null, "router", "router", String.class, Void.class,
					null);
		}

		@Override
		protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
				INotificationSink notificationSink, ReactiveIdentityUtil runAs) throws LLMConfigException {
			return model;
		}

		@Override
		protected GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
				throws AgentException {
			return new GPromptTemplateConfig();
		}

		@Override
		protected int agentTokenBudget(IGConfigurableChatModel agentModel, GPromptTemplateConfig prompt,
				IChatRequestContext chatRequestContext) {
			return 1000;
		}

		@Override
		protected <I, O> Map<String, Object> createAgentTemplateParams(GPromptTemplateConfig prompt,
				GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
				AgentsCollaborationSessionContext session, AgentPrivateSessionContext<I, O> mySessionContext,
				Object input, IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget) {
			inputsGivenToTheModel.add(input);
			return new LinkedHashMap<String, Object>();
		}
	}

	private final class Searcher extends GAbstractDocumentsSearchNetworkAgentService {
		private boolean asks = false;
		private boolean delivered = false;
		private SearchAgentCommand searched = null;

		private Searcher() {
			super(null, null, null, null, mock(IAgentRoleDao.class), null, null);
		}

		@Override
		public String getId() {
			return "searcher";
		}

		@Override
		public String getDescription() {
			return "searcher";
		}

		@Override
		public List<GAgentConfig> getAccessibleConfigurations() {
			return List.of();
		}

		@Override
		public AgentCapabilities getAgentCapabilities(GAgentConfig agentConfig) {
			return new AgentCapabilities();
		}

		@Override
		protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
				INotificationSink notificationSink, ReactiveIdentityUtil runAs) throws LLMConfigException {
			return model;
		}

		@Override
		protected GPromptTemplateConfig resolvePrompt(GPromptTemplateConfig prompt, String useCode, boolean nullable)
				throws AgentException {
			return new GPromptTemplateConfig();
		}

		@Override
		protected int agentTokenBudget(IGConfigurableChatModel agentModel, GPromptTemplateConfig prompt,
				IChatRequestContext chatRequestContext) {
			return 1000;
		}

		@Override
		protected <I, O> Map<String, Object> createAgentTemplateParams(GPromptTemplateConfig prompt,
				GAgentsNetwork network, GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
				AgentsCollaborationSessionContext session, AgentPrivateSessionContext<I, O> mySessionContext,
				Object input, IGAgentsNetworkRuntimeDao agentsDao, int actualContributionNr, int tokenBudget) {
			inputsGivenToTheModel.add(input);
			return new LinkedHashMap<String, Object>();
		}

		@Override
		protected List<Document> retrieveDocuments(GPromptTemplateConfig prompt, IChatRequestContext chatRequestContext,
				IGConfigurableChatModel agentModel, Map<String, Object> params, GAgentsNetwork network,
				GAgentRole agentRole, AgentNetworkParticipant contextAgentPersona,
				AgentsCollaborationSessionContext session,
				AgentPrivateSessionContext<SearchAgentCommand, List<Document>> mySessionContext,
				AgentsExchangeMessage<SearchAgentCommand> msg, IGAgentsNetworkRuntimeDao agentsDao,
				INotificationSink notificationSink, List<String> statusNotices, GeboToolCalling toolCalling)
				throws AgentException {
			searched = msg.getPayload();
			if (asks) {
				theToolAsks(toolCalling);
				return List.of();
			}
			return List.of(new Document("found"));
		}

		@Override
		protected List<Document> deliverDocuments(List<Document> documents, SearchAgentCommand command,
				IChatRequestContext chatRequestContext, IGConfigurableChatModel agentModel,
				AgentsCollaborationSessionContext session, AgentNetworkParticipant contextAgentPersona,
				INotificationSink notificationSink, ReactiveIdentityUtil runAs, List<String> statusNotices,
				GeboToolCalling toolCalling) throws AgentException {
			delivered = true;
			return documents;
		}
	}

	/** The question tool, called with the call's tool context as the model would. */
	private static Object theToolAsks(GeboToolCalling toolCalling) {
		AskUserSingleChoiceQuestionTool.declare().call(QUESTION, new ToolContext(toolCalling.toolsContext(Map.of())));
		return null;
	}

	private void theModelAsksInItsText() throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class)))
				.thenAnswer(call -> theToolAsks(call.getArgument(3, GeboToolCalling.class)));
	}

	private void theModelAsksInItsPlan() throws Exception {
		when(model.structuredResponse(any(), any(), any(), eq(LinkedHashMap.class), any(GeboToolCalling.class)))
				.thenAnswer(call -> theToolAsks(call.getArgument(4, GeboToolCalling.class)));
	}

	private void theUserAnswered() {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> answered = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Which year?", "it matters", ClarificationAnswer.option("2024"), true,
				ClarificationOptions.class, options);
		when(handler.takeResolvedConditionsOf(me)).thenReturn(List.<AgentSuspendedWaitingCondition>of(answered));
	}

	private void aQuestionSuspendedTheRun() throws Exception {
		verify(handler).enterSuspensionState(eq(network), eq(config), eq(me), any(), any());
	}

	private <I, O> List<AgentsExchangeMessage<O>> run(IGNetworkAgentService<I, O> agent, I payload) throws Exception {
		return agent.onMessage(IChatRequestContext.of("What is new?"), config,
				AgentsExchangeMessage.of(session, "the-sender", "the-agent", payload,
						MessageSemantic.EXECUTE_AND_SHARE_RESULT),
				0, network, me, state -> {
				}, session, new AgentPrivateSessionContext<I, O>(), null, agentsDao,
				GeboToolCalling.builder().caller("test:the-run").build(), handler);
	}

	private void theRouterHasAPeer() throws Exception {
		me.setCommunicationList(List.of("the-peer"));
		RuntimeAgentInfos peer = mock(RuntimeAgentInfos.class);
		IGNetworkAgentService peerService = mock(IGNetworkAgentService.class);
		when(peerService.getInputType()).thenReturn((Class) String.class);
		when(peer.getService()).thenReturn(peerService);
		when(agentsDao.findAgentByCode("the-peer")).thenReturn(peer);
	}

	private static SearchAgentCommand search(String text) {
		SearchAgentCommand command = new SearchAgentCommand();
		command.setCommand(text);
		command.setTopK(7);
		command.setDeepSearch(true);
		return command;
	}

	@Test
	void aTaskPerformerWhoseToolAsksSuspendsTheRunAndSaysNothing() throws Exception {
		theModelAsksInItsText();

		assertTrue(run(new Performer(), "What is new?").isEmpty());

		aQuestionSuspendedTheRun();
	}

	@Test
	void aRespawnedTaskPerformerTellsItsModelTheAnswerBeforeTheMessage() throws Exception {
		theUserAnswered();
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("In 2024: nothing new.");

		List<AgentsExchangeMessage<String>> out = run(new Performer(), "What is new?");

		assertEquals(TOLD + "What is new?", inputsGivenToTheModel.get(0));
		assertEquals("In 2024: nothing new.", out.get(0).getPayload());
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void aTaskPerformerWhoseToolsDeclareNothingAnswersAsAlways() throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("the answer");

		List<AgentsExchangeMessage<String>> out = run(new Performer(), "What is new?");

		assertEquals("What is new?", inputsGivenToTheModel.get(0));
		assertEquals("the answer", out.get(0).getPayload());
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void aRoutingAgentWhoseToolAsksSuspendsTheRunAndRoutesNothing() throws Exception {
		theRouterHasAPeer();
		theModelAsksInItsPlan();

		assertTrue(run(new Router(), "What is new?").isEmpty());

		aQuestionSuspendedTheRun();
	}

	@Test
	void aRespawnedRoutingAgentTellsItsModelTheAnswerBeforeTheMessage() throws Exception {
		theRouterHasAPeer();
		theUserAnswered();
		theModelAsksInItsPlan();

		run(new Router(), "What is new?");

		assertEquals(TOLD + "What is new?", inputsGivenToTheModel.get(0));
	}

	@Test
	void aSearchAgentWhoseToolAsksSuspendsTheRunAndHandsNothingOver() throws Exception {
		Searcher searcher = new Searcher();
		searcher.asks = true;

		assertTrue(run(searcher, search("the news")).isEmpty());

		aQuestionSuspendedTheRun();
		assertFalse(searcher.delivered, "no analysis of documents not searched for");
	}

	@Test
	void aRespawnedSearchAgentIsToldTheAnswerInItsCommandAndTheMessageStaysAsItWas() throws Exception {
		theUserAnswered();
		Searcher searcher = new Searcher();
		SearchAgentCommand received = search("the news");

		List<AgentsExchangeMessage<List<Document>>> out = run(searcher, received);

		assertEquals(TOLD + "the news", searcher.searched.getCommand());
		assertEquals(TOLD + "the news", ((SearchAgentCommand) inputsGivenToTheModel.get(0)).getCommand());
		assertEquals(7, searcher.searched.getTopK());
		assertTrue(searcher.searched.isDeepSearchRequested());
		assertEquals("the news", received.getCommand(), "the turn a further suspension keeps");
		assertEquals(1, out.get(0).getPayload().size());
		assertTrue(searcher.delivered);
	}

	@Test
	void aSearchAgentNotRespawnedSearchesTheCommandItReceived() throws Exception {
		Searcher searcher = new Searcher();
		SearchAgentCommand received = search("the news");

		run(searcher, received);

		assertTrue(received == searcher.searched);
	}
}
