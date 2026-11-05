package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.services.IDynamicAgentsNetworkDataSource;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationAnswer;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationOptions;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import reactor.core.publisher.Flux;

/**
 * Pins what the chat's network step streams when the network suspends: the last
 * message carries the suspended state, with the question rendered as the
 * answer, and it waits for the result of the run rather than following the
 * network's output sink. A network that answers leaves no state.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ChatNetworkStepSuspensionTest {
	private final IGReactiveChatAgentsNetworkService network = mock(IGReactiveChatAgentsNetworkService.class);
	private final IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = mock(
			IGAgentsNetworkServiceFactory.class);
	private final IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);

	private ReactiveChatAgentsNetworkStreamingOutputChatPipelineService step() throws Exception {
		GAgentsNetwork configured = new GAgentsNetwork();
		configured.setCode("the-network");
		IDynamicAgentsNetworkDataSource fallback = () -> List.of(configured);
		when(factory.create(any(), any(), any(), any(), any(), any())).thenReturn(network);
		when(lifeCycle.getSessionAvailableKnowledgeBases(any())).thenReturn(List.of());
		return new ReactiveChatAgentsNetworkStreamingOutputChatPipelineService(factory, fallback, lifeCycle, "step");
	}

	private static ChatPipelineExecutionRuntimeData runtimeData() {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-1");
		request.setUserChatContextCode("conv-1");
		request.setQuery("What is new?");
		resources.setCurrentRequest(request);
		GeboChatResponse response = new GeboChatResponse();
		return new ChatPipelineExecutionRuntimeData(null, 8192, resources, response, null, true,
				GeboToolCalling.builder().caller("test:ChatNetworkStepSuspensionTest").build());
	}

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarification() {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of("2023", "2024"));
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "the report differs by year", null, true, ClarificationOptions.class, options);
	}

	private GeboChatResponse lastResponseOf(ReactiveChatAgentsNetworkStreamingOutputChatPipelineService step,
			ChatPipelineExecutionRuntimeData runtimeData) throws Exception {
		List<GeboChatMessageEnvelope> streamed = step.execute(runtimeData, mock(ISinkUIEmitter.class), null, null)
				.collectList().block(Duration.ofSeconds(10));
		GeboChatMessageEnvelope last = streamed.get(streamed.size() - 1);
		assertTrue(last.isLastMessage());
		return (GeboChatResponse) last.getContent();
	}

	@Test
	void aNetworkThatSuspendsLeavesTheStateAndTheQuestionOnTheLastMessage() throws Exception {
		when(network.getFlux()).thenReturn(Flux.empty());
		when(network.executeNetwork(any(), any(), any(), any()))
				.thenReturn(NetworkOfAgentsExecutionResult.suspended("block-1", List.of(clarification())));
		ChatPipelineExecutionRuntimeData runtimeData = runtimeData();

		GeboChatResponse response = lastResponseOf(step(), runtimeData);

		assertNotNull(response.getNetworkOfAgentsSuspendedState(), "the state the client reacts to");
		assertEquals("block-1", response.getNetworkOfAgentsSuspendedState().getNetworkOfAgentsBlockId());
		assertEquals(1, response.getNetworkOfAgentsSuspendedState().getConditions().size());
		assertEquals("Which year?",
				response.getNetworkOfAgentsSuspendedState().getConditions().get(0).getAttributes().get("statement"));
		assertTrue(response.getQueryResponse().startsWith("Which year?"), "rendered as the answer: "
				+ response.getQueryResponse());
	}

	@Test
	void aNetworkThatAnswersLeavesNoState() throws Exception {
		GeboChatResponse answered = new GeboChatResponse();
		answered.setQueryResponse("Nothing new.");
		when(network.getFlux()).thenReturn(Flux.just(new GeboChatMessageEnvelope(answered)));
		when(network.executeNetwork(any(), any(), any(), any()))
				.thenReturn(NetworkOfAgentsExecutionResult.of(new GeboChatMessageEnvelope(answered)));

		GeboChatResponse response = lastResponseOf(step(), runtimeData());

		assertNull(response.getNetworkOfAgentsSuspendedState());
		assertEquals("Nothing new.", response.getQueryResponse());
	}

	@Test
	void aNetworkThatFailsStillEndsTheStreamWithTheLastMessage() throws Exception {
		when(network.getFlux()).thenReturn(Flux.empty());
		when(network.executeNetwork(any(), any(), any(), any())).thenThrow(new IllegalStateException("down"));

		GeboChatResponse response = lastResponseOf(step(), runtimeData());

		assertNull(response.getNetworkOfAgentsSuspendedState());
	}
}
