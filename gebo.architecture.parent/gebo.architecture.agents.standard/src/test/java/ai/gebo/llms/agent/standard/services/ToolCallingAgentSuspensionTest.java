package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentPrivateSessionContext;
import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage;
import ai.gebo.architecture.agents.model.AgentsExchangeMessage.MessageSemantic;
import ai.gebo.architecture.agents.model.GAgentConfig;
import ai.gebo.architecture.agents.model.GAgentRole;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GAgentsNetwork.AgentNetworkParticipant;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.IGAgentsNetworkRuntimeDao;
import ai.gebo.architecture.agents.services.IGNetworkOfAgentsSuspensionHandler;
import ai.gebo.architecture.agents.services.IGWaitExternalCondition;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.tools.AskUserSingleChoiceQuestionTool;
import ai.gebo.architecture.agents.model.ClarificationAnswer;
import ai.gebo.architecture.agents.model.ClarificationOption;
import ai.gebo.architecture.agents.model.ClarificationOptions;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ToolCallsListener;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.abstraction.layer.services.toolcalling.IToolCallingControlsProvider;
import ai.gebo.security.services.ReactiveIdentityUtil;

/**
 * Pins how the tool-calling agent suspends a run and resumes it: the wait sink
 * it loads for every model call, filled by the clarification tool the model
 * calls, makes it ask the network to suspend the run and say nothing; respawned
 * with the answer, it tells the model the answer before the message it was
 * given. An agent whose tools declare nothing answers as always.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ToolCallingAgentSuspensionTest {
	private static final GeboToolCalling RUN_TOOL_CALLING = GeboToolCalling.builder()
			.caller("test:ToolCallingAgentSuspensionTest").build();
	private final IGConfigurableChatModel model = mock(IGConfigurableChatModel.class);
	private final IGNetworkOfAgentsSuspensionHandler handler = mock(IGNetworkOfAgentsSuspensionHandler.class);
	private final List<Object> inputsGivenToTheModel = new ArrayList<Object>();
	private final List<String> rulesOfTheTurns = new ArrayList<String>();
	private String rulesOfTheLibraries = "";
	private final AgentNetworkParticipant me = new AgentNetworkParticipant();
	private final GAgentConfig config = new GAgentConfig();
	private final GAgentsNetwork network = new GAgentsNetwork();
	private final AgentsCollaborationSessionContext session = new AgentsCollaborationSessionContext("session-1");

	/** The agent with its model, prompt and budget resolved here: the test is about what happens around the call. */
	private final class TestAgent extends StringToStringToolCallingNetworkAgent {
		private TestAgent() {
			super(null, null, null, null, mock(IAgentRoleDao.class), null, null);
		}

		/** What the conversation holds is the instances service's: here, a mark in the tools context. */
		@Override
		protected AgentLibraries reclaimTheLibrariesOfTheConversation(IChatRequestContext chatRequestContext,
				GAgentConfig agentConfig) {
			return new AgentLibraries(chatRequestContext.withToolsContextValue("held", agentConfig.getCode()), List.of(),
					rulesOfTheLibraries);
		}

		@Override
		protected IGConfigurableChatModel getAgentModel(GAgentConfig agentConfig, ToolCallsListener callBacksListener,
				INotificationSink notificationSink, ReactiveIdentityUtil runAs, List<ToolCallback> libraryTools)
				throws LLMConfigException {
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
			rulesOfTheTurns.add(mySessionContext.getAdditionalRules());
			return new LinkedHashMap<String, Object>(Map.of("input", String.valueOf(input)));
		}
	}

	private AgentsExchangeMessage<String> theQuestion() {
		return AgentsExchangeMessage.of(session, "the-router", "the-agent", "What is new?",
				MessageSemantic.EXECUTE_AND_SHARE_RESULT);
	}

	private List<AgentsExchangeMessage<String>> run(TestAgent agent) throws Exception {
		me.setAgentConfigCode("the-agent");
		me.setAllowedToNotifyUser(false);
		config.setCode("the-agent");
		network.setCode("the-network");
		return agent.onMessage(IChatRequestContext.of("What is new?"), config, theQuestion(), 0, network, me,
				state -> {
				}, session, new AgentPrivateSessionContext<String, String>(), null,
				mock(IGAgentsNetworkRuntimeDao.class), RUN_TOOL_CALLING, handler);
	}

	/** The model calls the question tool: in the test, the tool itself, with the call's tool context. */
	private void theModelAsksTheUser() throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenAnswer(invocation -> {
			GeboToolCalling callToolCalling = invocation.getArgument(3, GeboToolCalling.class);
			return AskUserSingleChoiceQuestionTool.declare().call(
					"{\"question\":\"Which year?\",\"whyNeeded\":\"it matters\",\"options\":[{\"label\":\"2023\"},{\"label\":\"2024\"}]}",
					new org.springframework.ai.chat.model.ToolContext(callToolCalling.toolsContext(Map.of())));
		});
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
	}

	@Test
	void theWaitSinkIsLoadedInEveryModelCallBesideWhatTheRunCarries() throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("the answer");
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		ArgumentCaptor<GeboToolCalling> callToolCalling = ArgumentCaptor.forClass(GeboToolCalling.class);
		verify(model).textResponse(any(), any(), any(), callToolCalling.capture());
		IToolCallingControlsProvider provider = callToolCalling.getValue().getProvider();
		assertNotNull(provider.get(IGWaitExternalCondition.class), "a mounted waiting tool finds where to declare");
		assertEquals("test:ToolCallingAgentSuspensionTest", provider.caller(), "the run's caller stays");
		assertEquals(1, out.size());
		assertEquals("the answer", out.get(0).getPayload());
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void anAgentHoldingALibraryWithRulesCallsItsModelWithThemInEveryTurnEvenRebuilt() throws Exception {
		rulesOfTheLibraries = "Ask before going on.";
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("the answer");
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());

		run(new TestAgent());
		run(new TestAgent()); // a new private context, as a respawned agent has

		ArgumentCaptor<GPromptTemplateConfig> prompt = ArgumentCaptor.forClass(GPromptTemplateConfig.class);
		verify(model, org.mockito.Mockito.times(2)).textResponse(prompt.capture(), any(), any(), any(GeboToolCalling.class));
		for (GPromptTemplateConfig called : prompt.getAllValues()) {
			assertTrue(called.getPlaceholders().containsKey("additionalRulesSlot"), called.getSystemPromptTemplate());
		}
		assertEquals(List.of("Ask before going on.", "Ask before going on."), rulesOfTheTurns);
	}

	@Test
	void aToolDeclaringAConditionMakesTheAgentSuspendTheRunAndSayNothing() throws Exception {
		theModelAsksTheUser();

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		assertTrue(out.isEmpty(), "the agent waits: nothing is said to the network");
		ArgumentCaptor<AgentSuspendedWaitingCondition> condition = ArgumentCaptor
				.forClass(AgentSuspendedWaitingCondition.class);
		verify(handler).enterSuspensionState(eq(network), eq(config), eq(me), any(), condition.capture());
		assertEquals("Which year?", condition.getValue().getWaitingConditionStatement());
		assertSame(ClarificationAnswer.class, condition.getValue().getType());
		assertEquals(List.of("2023", "2024"),
				((ClarificationOptions) condition.getValue().getLlmChosenOptions()).labels());
		assertTrue(condition.getValue().isMandatory());
		assertEquals(List.of("What is new?"), inputsGivenToTheModel, "the message as it was given");
	}

	@Test
	void respawnedWithTheAnswerTheAgentTellsItsModelBeforeTheMessage() throws Exception {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of(ClarificationOption.of("2023"), ClarificationOption.of("2024")));
		AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> answered = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Which year?", "it matters", ClarificationAnswer.option("2024"), true,
				ClarificationOptions.class, options);
		when(handler.takeResolvedConditionsOf(me)).thenReturn(List.<AgentSuspendedWaitingCondition>of(answered));
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("In 2024: nothing new.");

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		assertEquals(1, out.size());
		assertEquals("In 2024: nothing new.", out.get(0).getPayload());
		assertEquals("You asked the user: Which year?\nThe user chose: 2024\n\nWhat is new?",
				inputsGivenToTheModel.get(0), "the answer first, then the message it was executing");
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void theLibrariesOfTheConversationReachTheModelCall() throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenReturn("the answer");
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());

		run(new TestAgent());

		ArgumentCaptor<IChatRequestContext> callContext = ArgumentCaptor.forClass(IChatRequestContext.class);
		verify(model).textResponse(any(), any(), callContext.capture(), any(GeboToolCalling.class));
		assertEquals("the-agent", callContext.getValue().getToolsContext().get("held"));
	}

	/** A tool the agent's configuration lists as needing a person's confirmation. */
	private final java.util.concurrent.atomic.AtomicInteger deletions = new java.util.concurrent.atomic.AtomicInteger();
	private final ToolCallback deleteDocument = new ToolCallback() {
		@Override
		public org.springframework.ai.tool.definition.ToolDefinition getToolDefinition() {
			return org.springframework.ai.tool.definition.ToolDefinition.builder().name("deleteDocument")
					.description("Deletes a document.").inputSchema("{}").build();
		}

		@Override
		public String call(String toolInput) {
			deletions.incrementAndGet();
			return "deleted";
		}
	};

	/** The model calls deleteDocument: the tool as the call mounts it, with the call's tool context. */
	private void theModelCallsDeleteDocument(String arguments) throws Exception {
		when(model.textResponse(any(), any(), any(), any(GeboToolCalling.class))).thenAnswer(invocation -> {
			GeboToolCalling callToolCalling = invocation.getArgument(3, GeboToolCalling.class);
			ToolCallback mounted = callToolCalling.decorated(List.of(deleteDocument)).get(0);
			return mounted.call(arguments,
					new org.springframework.ai.chat.model.ToolContext(callToolCalling.toolsContext(Map.of())));
		});
	}

	@Test
	void aToolNeedingConfirmationSuspendsTheRunWithTheCallAndDoesNotRun() throws Exception {
		config.setToolsNeedingConfirmation(List.of("deleteDocument"));
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
		theModelCallsDeleteDocument("{\"id\":\"doc-1\"}");

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		assertTrue(out.isEmpty(), "the agent waits for the person");
		assertEquals(0, deletions.get(), "nothing runs before a person allows it");
		ArgumentCaptor<AgentSuspendedWaitingCondition> condition = ArgumentCaptor
				.forClass(AgentSuspendedWaitingCondition.class);
		verify(handler).enterSuspensionState(eq(network), eq(config), eq(me), any(), condition.capture());
		ai.gebo.architecture.agents.model.ToolCallConfirmationOptions options = (ai.gebo.architecture.agents.model.ToolCallConfirmationOptions) condition
				.getValue().getLlmChosenOptions();
		assertEquals("deleteDocument", options.getToolName());
		assertEquals("{\"id\":\"doc-1\"}", options.getArguments());
	}

	@Test
	void respawnedWithTheCallAllowedTheModelIsToldAndTheCallRuns() throws Exception {
		config.setToolsNeedingConfirmation(List.of("deleteDocument"));
		ai.gebo.architecture.agents.model.ToolCallConfirmationOptions options = new ai.gebo.architecture.agents.model.ToolCallConfirmationOptions();
		options.setToolName("deleteDocument");
		options.setArguments("{\"id\":\"doc-1\"}");
		AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> allowed = new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(
				ClarificationAnswer.class, "Allow?", null,
				ClarificationAnswer.option(ai.gebo.architecture.agents.model.ToolCallConfirmationOptions.ALLOW), true,
				ClarificationOptions.class, options);
		when(handler.takeResolvedConditionsOf(me)).thenReturn(List.<AgentSuspendedWaitingCondition>of(allowed));
		theModelCallsDeleteDocument("{ \"id\" : \"doc-1\" }");

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		assertEquals(1, deletions.get(), "the allowed call runs");
		assertEquals(1, out.size());
		assertEquals("deleted", out.get(0).getPayload());
		assertTrue(String.valueOf(inputsGivenToTheModel.get(0)).startsWith("A person allowed the call to the tool deleteDocument"),
				String.valueOf(inputsGivenToTheModel.get(0)));
		verify(handler, never()).enterSuspensionState(any(), any(), any(), any(), any());
	}

	@Test
	void anAgentListingNoToolRunsItsToolsAsTheyAre() throws Exception {
		when(handler.takeResolvedConditionsOf(any())).thenReturn(List.of());
		theModelCallsDeleteDocument("{\"id\":\"doc-1\"}");

		List<AgentsExchangeMessage<String>> out = run(new TestAgent());

		assertEquals(1, deletions.get());
		assertEquals("deleted", out.get(0).getPayload());
	}
}
