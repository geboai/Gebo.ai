package ai.gebo.llms.chat.pipelines.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.gebo.architecture.agents.model.AgentSuspendedWaitingCondition;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension;
import ai.gebo.architecture.agents.model.GNetworkOfAgentsSuspension.SuspendedAgentState;
import ai.gebo.architecture.agents.repository.GNetworkOfAgentsSuspensionRepository;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSuspendedException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRuntimeChatProfileChatModelDao;
import ai.gebo.llms.chat.pipelines.service.IChatPipelinesExecutor;
import ai.gebo.llms.chat.pipelines.service.IPipelineUserMenuProviderServiceRepositoryPattern;
import ai.gebo.security.services.IGSecurityService;
import reactor.core.publisher.Flux;

/**
 * Pins what a normal interaction does in a conversation holding an open block
 * of a suspended network of agents: refused when the block is mandatory, the
 * block dropped and the interaction run when it is not.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class ChatPipelineServiceOpenSuspensionTest {
	private final IChatPipelinesExecutor executor = mock(IChatPipelinesExecutor.class);
	private final GNetworkOfAgentsSuspensionRepository suspensions = mock(GNetworkOfAgentsSuspensionRepository.class);
	private final ChatPipelineServiceImpl service = new ChatPipelineServiceImpl(executor,
			mock(IGChatModelRuntimeConfigurationDao.class), mock(IGPersistentObjectManager.class),
			mock(IGSecurityService.class), mock(IGRuntimeChatProfileChatModelDao.class),
			mock(IGChatSessionLifeCycleService.class), mock(IPipelineUserMenuProviderServiceRepositoryPattern.class),
			suspensions, NetworkOfAgentsSuspensionTransport.plain());

	private static GeboChatRequest request() {
		GeboChatRequest request = new GeboChatRequest();
		request.setUserChatContextCode("conv-1");
		request.setQuery("something else");
		return request;
	}

	private static GNetworkOfAgentsSuspension openBlock(boolean mandatory) {
		GNetworkOfAgentsSuspension block = new GNetworkOfAgentsSuspension();
		block.setCode("block-1");
		block.setConversationCode("conv-1");
		block.getSuspendedAgents().add(SuspendedAgentState.of(null, null, null,
				new AgentSuspendedWaitingCondition<String, Object>(String.class, "Which year?", "why", null, mandatory)));
		return block;
	}

	@Test
	void aConversationWaitingForAMandatoryAnswerRefusesANormalInteraction() throws Exception {
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.of(openBlock(true)));

		GeboChatSuspendedException refusal = assertThrows(GeboChatSuspendedException.class,
				() -> service.streamingChat(null, request(), new LinkedHashMap<String, Object>()));

		assertEquals("conv-1", refusal.getUserChatContextCode());
		assertNotNull(refusal.getPendingState());
		assertEquals("block-1", refusal.getPendingState().getNetworkOfAgentsBlockId());
		assertEquals("Which year?", refusal.getPendingState().getConditions().get(0).getAttributes().get("statement"));
		verify(executor, never()).streamingExecute(any(), any(), any(), any(), any());
		assertThrows(GeboChatSuspendedException.class,
				() -> service.chat(null, request(), new LinkedHashMap<String, Object>()), "the blocking chat too");
	}

	@Test
	void aConversationWithAnOptionalOpenBlockGoesOnAndDropsIt() throws Exception {
		GNetworkOfAgentsSuspension block = openBlock(false);
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.of(block));
		when(executor.streamingExecute(any(), any(), any(), any(), any())).thenReturn(Flux.empty());

		service.streamingChat(null, request(), new LinkedHashMap<String, Object>());

		assertNotNull(block.getDroppedAt(), "the user chose to go on without answering");
		verify(suspensions).save(block);
		verify(executor).streamingExecute(any(), any(), any(), any(), any());
	}

	@Test
	void aConversationWithNoOpenBlockIsNotTouched() throws Exception {
		when(suspensions.findOpenByConversationCode("conv-1")).thenReturn(Optional.empty());
		when(executor.streamingExecute(any(), any(), any(), any(), any())).thenReturn(Flux.empty());

		service.streamingChat(null, request(), new LinkedHashMap<String, Object>());

		verify(suspensions, never()).save(any());
		verify(executor).streamingExecute(any(), any(), any(), any(), any());
		assertNull(request().getNetworkOfAgentsSuspendedStateReaction());
	}
}
