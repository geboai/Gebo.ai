package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.WaitingCondition;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.AgentSuspendedWaitingConditionReactionDto;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedStateReaction;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IConditionReactionRecorder;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderServiceRepositoryPattern;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Answer;
import ai.gebo.llms.chat.pipelines.service.RecordedConditionReaction.Next;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Pins how a respawn request reaches the recorder of the answers: every reaction
 * answering something is recorded where a recorder is present, whole or one
 * condition at a time, so the respawn is the one claim the chat, the events and
 * the panel contend for; an answer that solves nothing yet is answered and kept
 * without running, one that solves the block runs the respawn with the answers
 * of every source and says when it ended. A reaction answering nothing, the
 * dispatcher's own respawn and every request where no recorder is present go to
 * the respawner as they always did.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ChatPipelineServiceOneByOneTest {
	private final IChatPipelinesExecutor executor = mock(IChatPipelinesExecutor.class);
	private final GNetworkOfAgentsSuspensionRepository suspensions = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final IGChatSessionLifeCycleService lifecycle = mock(IGChatSessionLifeCycleService.class);
	private final IConditionReactionRecorder recorder = mock(IConditionReactionRecorder.class);
	private final ObjectProvider<IConditionReactionRecorder> recorders = mock(ObjectProvider.class);
	private final List<GeboChatRequest> ran = new ArrayList<GeboChatRequest>();
	private ChatPipelineServiceImpl service;
	private GNetworkOfAgentsSuspension block;

	@BeforeEach
	void setUp() throws Exception {
		service = new ChatPipelineServiceImpl(executor, mock(IGChatModelRuntimeConfigurationDao.class),
				mock(IGPersistentObjectManager.class), mock(IGSecurityService.class),
				mock(IGRuntimeChatProfileChatModelDao.class), lifecycle,
				mock(IPipelineUserMenuProviderServiceRepositoryPattern.class), suspensions,
				NetworkOfAgentsSuspensionTransport.plain(), recorders);
		when(recorders.getIfAvailable()).thenReturn(recorder);
		block = new GNetworkOfAgentsSuspension();
		block.setCode("block-1");
		block.setConversationCode("conv-1");
		block.setNetwork(new GAgentsNetwork());
		final SuspendedAgentState agent = SuspendedAgentState.of(null, null, null, condition("year", "Which year?", true));
		agent.getWaitingConditions().add(WaitingCondition.of(condition("approval", "Manager approval", true)));
		block.getSuspendedAgents().add(agent);
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.of(block));
		when(lifecycle.createEmptyResponse(any())).thenAnswer(i -> new GeboChatResponse());
		when(executor.streamingRespawnAgenticChat(any(), any(), any(), any(), any())).thenAnswer(i -> {
			ran.add(i.getArgument(0));
			return Flux.just(new GeboChatMessageEnvelope<GeboChatResponse>(new GeboChatResponse(), true));
		});
	}

	private static AgentSuspendedWaitingCondition<?, ?> condition(String id, String statement, boolean mandatory) {
		final AgentSuspendedWaitingCondition condition = new AgentSuspendedWaitingCondition<String, Object>(String.class,
				statement, "why", null, mandatory);
		condition.setSuspendedConditionId(id);
		return condition;
	}

	private static GeboChatRequest answering(String... conditionIds) {
		final NetworkOfAgentsSuspendedStateReaction reaction = new NetworkOfAgentsSuspendedStateReaction();
		reaction.setNetworkOfAgentsBlockId("block-1");
		final List<AgentSuspendedWaitingConditionReactionDto> reactions = new ArrayList<AgentSuspendedWaitingConditionReactionDto>();
		for (String id : conditionIds) {
			final AgentSuspendedWaitingConditionReactionDto answer = new AgentSuspendedWaitingConditionReactionDto();
			answer.setConditionId(id);
			answer.setConditionType(DefaultConditionTransportTranslator.ID);
			answer.setAttributes(new LinkedHashMap<String, Object>());
			reactions.add(answer);
		}
		reaction.setReactions(reactions);
		final GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode("conv-1");
		request.setQuery("2024");
		request.setNetworkOfAgentsSuspendedStateReaction(reaction);
		return request;
	}

	@Test
	void aReactionAnsweringTheWholeBlockIsRecordedAndRunsTheRespawnItClaimed() throws Exception {
		final GeboChatRequest request = answering("year", "approval");
		final NetworkOfAgentsSuspendedStateReaction sent = request.getNetworkOfAgentsSuspendedStateReaction();
		final NetworkOfAgentsSuspendedStateReaction claimed = answering("year", "approval")
				.getNetworkOfAgentsSuspendedStateReaction();
		when(recorder.record(eq(block), eq(sent), any(), anyString())).thenReturn(new RecordedConditionReaction(
				List.of(new Answer("year", "ACCEPTED", null), new Answer("approval", "ACCEPTED", null)), List.of(),
				Next.RESPAWN, claimed));

		service.streamingRespawnAgenticChat(null, request, new LinkedHashMap<String, Object>()).blockLast();

		assertEquals(1, ran.size());
		assertSame(claimed, ran.get(0).getNetworkOfAgentsSuspendedStateReaction(),
				"the reaction the recorder built of the answers it kept");
		verify(recorder).concluded(block, request.getId());
	}

	@Test
	void aReactionAnsweringNothingGoesToTheRespawnerAsItAlwaysDid() throws Exception {
		final GeboChatRequest request = answering();
		final NetworkOfAgentsSuspendedStateReaction sent = request.getNetworkOfAgentsSuspendedStateReaction();

		service.streamingRespawnAgenticChat(null, request, new LinkedHashMap<String, Object>()).blockLast();

		verify(recorder, never()).record(any(), any(), any(), any());
		assertEquals(1, ran.size(), "nothing to record: the respawner refuses it, saying which condition is mandatory");
		assertSame(sent, ran.get(0).getNetworkOfAgentsSuspendedStateReaction());
	}

	@Test
	void withNoRecorderAPartialReactionGoesToTheRespawnerAsItAlwaysDid() throws Exception {
		when(recorders.getIfAvailable()).thenReturn(null);

		service.streamingRespawnAgenticChat(null, answering("year"), new LinkedHashMap<String, Object>()).blockLast();

		assertEquals(1, ran.size(), "the respawner refuses it, saying why");
	}

	@Test
	void anAnswerAskingToGoOnWithoutAConditionIsAlwaysRecorded() throws Exception {
		when(recorder.record(eq(block), any(), any(), anyString())).thenReturn(new RecordedConditionReaction(
				List.of(new Answer("year", "ACCEPTED", null), new Answer("approval", "ACCEPTED", null)), List.of(),
				Next.WAIT, null));
		final GeboChatRequest request = answering("year", "approval");
		request.getNetworkOfAgentsSuspendedStateReaction().getReactions().get(1).getAttributes()
				.put(GAbstractConditionTransportTranslator.GO_ON_WITHOUT, Boolean.TRUE);

		service.streamingRespawnAgenticChat(null, request, new LinkedHashMap<String, Object>()).collectList().block();

		verify(recorder).record(eq(block), any(), any(), anyString());
		assertTrue(ran.isEmpty(), "the recorder reads the request to go on without, as the conversation's own user;"
				+ " the respawner would refuse it");
	}

	@Test
	void anAnswerLeavingAMandatoryConditionIsRecordedAndAnsweredWithoutRunning() throws Exception {
		when(recorder.record(eq(block), any(), any(), anyString())).thenReturn(new RecordedConditionReaction(
				List.of(new Answer("year", "ACCEPTED", null)), List.of("Manager approval"), Next.WAIT, null));
		final GeboChatRequest request = answering("year");

		final List<GeboChatMessageEnvelope> messages = service
				.streamingRespawnAgenticChat(null, request, new LinkedHashMap<String, Object>()).collectList().block();

		assertTrue(ran.isEmpty(), "nothing runs before the approval");
		assertEquals(1, messages.size());
		assertTrue(messages.get(0).isLastMessage());
		final GeboChatResponse reply = (GeboChatResponse) messages.get(0).getContent();
		assertTrue(reply.getQueryResponse().startsWith(ConditionReactionReplies.RECORDED), reply.getQueryResponse());
		assertTrue(reply.getQueryResponse().contains("- Manager approval"), reply.getQueryResponse());
		assertEquals("block-1", reply.getNetworkOfAgentsSuspendedState().getNetworkOfAgentsBlockId(),
				"the block as it stands, to answer the rest");
		verify(lifecycle).endRequest(request, reply);
		verify(lifecycle).releaseRequest(request);
		verify(recorder, never()).concluded(any(), any());
	}

	@Test
	void anAnswerJoiningThoseOfOtherSourcesRespawnsWithAllOfThemAndSaysWhenItEnded() throws Exception {
		final NetworkOfAgentsSuspendedStateReaction merged = answering("year", "approval")
				.getNetworkOfAgentsSuspendedStateReaction();
		when(recorder.record(eq(block), any(), any(), anyString()))
				.thenReturn(new RecordedConditionReaction(List.of(new Answer("year", "ACCEPTED", null)), List.of(),
						Next.RESPAWN, merged));
		final GeboChatRequest request = answering("year");

		final Flux<GeboChatMessageEnvelope> stream = service.streamingRespawnAgenticChat(null, request,
				new LinkedHashMap<String, Object>());
		verify(recorder, never()).concluded(any(), any());
		stream.blockLast();

		assertEquals(1, ran.size());
		assertSame(merged, ran.get(0).getNetworkOfAgentsSuspendedStateReaction(), "the answers of every source");
		verify(recorder).concluded(block, request.getId());
	}

	@Test
	void theDispatchersOwnRespawnIsNeverRecordedAgain() throws Exception {
		final GeboChatRequest request = answering("year");
		request.setRunOrigin(IConditionReactionRecorder.RESPAWN_ORIGIN);

		service.respawnAgenticChat(null, request, new LinkedHashMap<String, Object>());

		verify(recorder, never()).record(any(), any(), any(), any());
		assertEquals(1, ran.size());
	}

	@Test
	void aBlockAnotherAlreadyRespawnsIsSaidSoWithoutRunning() throws Exception {
		when(recorder.record(eq(block), any(), any(), anyString())).thenReturn(new RecordedConditionReaction(
				List.of(new Answer("approval", "ACCEPTED", null)), List.of(), Next.RESPAWNING_APART, null));

		final GeboChatResponse reply = service.respawnAgenticChat(null, answering("approval"),
				new LinkedHashMap<String, Object>());

		assertTrue(ran.isEmpty());
		assertTrue(reply.getQueryResponse().contains(ConditionReactionReplies.RESUMING_APART), reply.getQueryResponse());
	}
}
