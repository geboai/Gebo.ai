package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.NetworkOfAgentsExecutionResult;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.agents.services.IGAgentsNetworkServiceFactory;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationAnswer;
import ai.gebo.architecture.agents.tools.AskUserClarificationTool.ClarificationOptions;
import ai.gebo.llms.abstraction.layer.services.toolcalling.GeboToolCalling;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ChatPipelineException;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.impl.ClarificationConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.DefaultConditionTransportTranslator;
import ai.gebo.llms.chat.pipelines.service.impl.NetworkOfAgentsSuspensionTransport;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl;
import reactor.core.publisher.Flux;

/**
 * Pins the respawner step: the conversation's open block, resolved with the
 * request's reaction through the translators, is respawned on a service created
 * for its network; a missing mandatory reaction, a reaction to another block or
 * a conversation with nothing suspended refuse the respawn before anything runs.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ChatNetworkRespawnStepTest {
	private final IGReactiveChatAgentsNetworkService network = mock(IGReactiveChatAgentsNetworkService.class);
	private final IGAgentsNetworkServiceFactory<String, GeboChatMessageEnvelope, IGReactiveChatAgentsNetworkService> factory = mock(
			IGAgentsNetworkServiceFactory.class);
	private final IGChatSessionLifeCycleService lifeCycle = mock(IGChatSessionLifeCycleService.class);
	private final GNetworkOfAgentsSuspensionRepository suspensions = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final NetworkOfAgentsSuspensionTransport transport = new NetworkOfAgentsSuspensionTransport(
			new AgentSuspendedWaitingConditionTransportTranslatorRepositoryPatternImpl(
					List.of(new DefaultConditionTransportTranslator(), new ClarificationConditionTransportTranslator())));
	private final GAgentsNetwork theNetwork = new GAgentsNetwork();
	private final AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> saved = clarification(true);

	private ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step() throws Exception {
		when(factory.create(any(), any(), any(), any(), any(), any())).thenReturn(network);
		when(lifeCycle.getSessionAvailableKnowledgeBases(any())).thenReturn(List.of());
		when(network.getFlux()).thenReturn(Flux.empty());
		return new ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService(factory, lifeCycle, suspensions,
				transport, null, null);
	}

	private static AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions> clarification(
			boolean mandatory) {
		ClarificationOptions options = new ClarificationOptions();
		options.setQuestion("Which year?");
		options.getOptions().addAll(List.of("2023", "2024"));
		return new AgentSuspendedWaitingCondition<ClarificationAnswer, ClarificationOptions>(ClarificationAnswer.class,
				"Which year?", "the report differs by year", null, mandatory, ClarificationOptions.class, options);
	}

	private GNetworkOfAgentsSuspension openBlock(AgentSuspendedWaitingCondition<?, ?>... conditions) {
		theNetwork.setCode("the-network");
		GNetworkOfAgentsSuspension block = new GNetworkOfAgentsSuspension();
		block.setCode("block-1");
		block.setConversationCode("conv-1");
		block.setNetworkCode("the-network");
		block.setNetwork(theNetwork);
		for (AgentSuspendedWaitingCondition<?, ?> condition : conditions) {
			block.getSuspendedAgents().add(SuspendedAgentState.of(null, null, null, condition));
		}
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.of(block));
		return block;
	}

	private static ChatPipelineExecutionRuntimeData runtimeData(NetworkOfAgentsSuspendedStateReaction reaction) {
		LLMChatRequestResources resources = new LLMChatRequestResources();
		GeboChatRequest request = new GeboChatRequest();
		request.setId("request-2");
		request.setUserChatContextCode("conv-1");
		request.setQuery("2024");
		request.setNetworkOfAgentsSuspendedStateReaction(reaction);
		resources.setCurrentRequest(request);
		return new ChatPipelineExecutionRuntimeData(null, 8192, resources, new GeboChatResponse(), null, true,
				GeboToolCalling.builder().caller("test:ChatNetworkRespawnStepTest").build());
	}

	private static NetworkOfAgentsSuspendedStateReaction reaction(String blockId, String conditionId,
			Map<String, Object> attributes) {
		AgentSuspendedWaitingConditionReactionDto dto = new AgentSuspendedWaitingConditionReactionDto();
		dto.setConditionType("clarification");
		dto.setConditionId(conditionId);
		dto.setAttributes(new LinkedHashMap<String, Object>(attributes));
		NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId(blockId);
		reaction.setReactions(List.of(dto));
		return reaction;
	}

	private GeboChatResponse lastResponseOf(ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step,
			ChatPipelineExecutionRuntimeData runtimeData) throws Exception {
		List<GeboChatMessageEnvelope> streamed = step.execute(runtimeData, mock(ISinkUIEmitter.class), null, null)
				.collectList().block(Duration.ofSeconds(10));
		return (GeboChatResponse) streamed.get(streamed.size() - 1).getContent();
	}

	@Test
	void theOpenBlockIsRespawnedOnAServiceOfItsNetworkWithTheReactionResolved() throws Exception {
		GNetworkOfAgentsSuspension block = openBlock(saved);
		GeboChatResponse answered = new GeboChatResponse();
		answered.setQueryResponse("In 2024: nothing new.");
		when(network.respawnNetworkExecution(any(), any(), any(), any(), any()))
				.thenReturn(NetworkOfAgentsExecutionResult.of(new GeboChatMessageEnvelope(answered)));
		ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step = step();

		GeboChatResponse response = lastResponseOf(step,
				runtimeData(reaction("block-1", saved.getSuspendedConditionId(), Map.of("chosenOption", "2024"))));

		verify(factory).create(any(), any(), any(), any(), any(), any());
		ArgumentCaptor<List<AgentSuspendedWaitingCondition<?, ?>>> resolved = ArgumentCaptor.forClass(List.class);
		verify(network).respawnNetworkExecution(any(), any(), resolved.capture(), any(), any());
		verify(network, never()).executeNetwork(any(), any(), any(), any());
		assertEquals(1, resolved.getValue().size());
		assertEquals(saved.getSuspendedConditionId(), resolved.getValue().get(0).getSuspendedConditionId(),
				"the saved condition, read back from the block");
		assertEquals("2024", ((ClarificationAnswer) resolved.getValue().get(0).getInstanceValue()).getChosenOption());
		assertNull(response.getNetworkOfAgentsSuspendedState(), "the run ended");
		assertSame(theNetwork, block.getNetwork());
	}

	@Test
	void aRespawnThatSuspendsAgainCarriesTheNewStateToTheClient() throws Exception {
		openBlock(saved);
		when(network.respawnNetworkExecution(any(), any(), any(), any(), any()))
				.thenReturn(NetworkOfAgentsExecutionResult.suspended("block-2", List.of(clarification(true))));

		GeboChatResponse response = lastResponseOf(step(),
				runtimeData(reaction("block-1", saved.getSuspendedConditionId(), Map.of("chosenOption", "2023"))));

		assertEquals("block-2", response.getNetworkOfAgentsSuspendedState().getNetworkOfAgentsBlockId());
		assertTrue(response.getQueryResponse().startsWith("Which year?"));
	}

	@Test
	void aMandatoryConditionWithoutReactionRefusesTheRespawnBeforeAnythingRuns() throws Exception {
		openBlock(saved, clarification(true));
		ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step = step();

		ChatPipelineException refusal = assertThrows(ChatPipelineException.class, () -> step.execute(
				runtimeData(reaction("block-1", saved.getSuspendedConditionId(), Map.of("chosenOption", "2024"))),
				mock(ISinkUIEmitter.class), null, null));

		assertTrue(refusal.getMessage().contains("mandatory"), refusal.getMessage());
		verify(factory, never()).create(any(), any(), any(), any(), any(), any());
	}

	@Test
	void anOptionalConditionWithoutReactionStaysWaiting() throws Exception {
		openBlock(saved, clarification(false));
		when(network.respawnNetworkExecution(any(), any(), any(), any(), any()))
				.thenReturn(NetworkOfAgentsExecutionResult.of(new GeboChatMessageEnvelope(new GeboChatResponse())));

		lastResponseOf(step(),
				runtimeData(reaction("block-1", saved.getSuspendedConditionId(), Map.of("chosenOption", "2024"))));

		ArgumentCaptor<List<AgentSuspendedWaitingCondition<?, ?>>> resolved = ArgumentCaptor.forClass(List.class);
		verify(network).respawnNetworkExecution(any(), any(), resolved.capture(), any(), any());
		assertEquals(1, resolved.getValue().size(), "only the answered one is resolved; the other waits on");
	}

	@Test
	void aReactionToAnotherBlockOrAnOptionNotOfferedIsRefused() throws Exception {
		openBlock(saved);
		ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step = step();

		assertThrows(ChatPipelineException.class, () -> step.execute(
				runtimeData(reaction("another-block", saved.getSuspendedConditionId(), Map.of("chosenOption", "2024"))),
				mock(ISinkUIEmitter.class), null, null));
		ChatPipelineException refused = assertThrows(ChatPipelineException.class, () -> step.execute(
				runtimeData(reaction("block-1", saved.getSuspendedConditionId(), Map.of("chosenOption", "2025"))),
				mock(ISinkUIEmitter.class), null, null));
		assertTrue(refused.getMessage().contains("2025"), refused.getMessage());
	}

	@Test
	void aConversationWithNothingSuspendedHasNothingToRespawn() throws Exception {
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.empty());
		ReactiveChatAgentsNetworkRespawnStreamingOutputChatPipelineService step = step();

		assertThrows(ChatPipelineException.class,
				() -> step.execute(runtimeData(null), mock(ISinkUIEmitter.class), null, null));
	}
}
