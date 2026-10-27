/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.agent.standard.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.ObjectProvider;

import ai.gebo.architecture.agents.model.GAgentsNetwork;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkService;
import ai.gebo.llms.agent.chat.service.IGReactiveChatAgentsNetworkServiceFactory;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatEnvironmentRun;
import ai.gebo.llms.agent.standard.services.GChatEnvironmentNetworkRunner.ChatRunAttachments;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGUserUploadContentHandler;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import reactor.core.publisher.Flux;

/**
 * A run in a chat environment ends its request the way a chat does: the answer
 * is saved as an interaction of the conversation, the request is completed and
 * released - and a run that fails leaves the conversation free.
 */
class GChatEnvironmentNetworkRunnerTest {
	private IGChatSessionLifeCycleService lifeCycle;
	private IGReactiveChatAgentsNetworkService service;
	private IGConfigurableChatModel chatModel;
	private GeboChatRequest request;
	private GeboChatResponse response;
	private GAgentsNetwork network;
	private GChatEnvironmentNetworkRunner runner;

	@BeforeEach
	@SuppressWarnings("unchecked")
	void setUp() throws Exception {
		lifeCycle = mock(IGChatSessionLifeCycleService.class);
		service = mock(IGReactiveChatAgentsNetworkService.class);
		chatModel = mock(IGConfigurableChatModel.class);
		when(chatModel.getContextLength()).thenReturn(30000);
		IGChatModelRuntimeConfigurationDao chatModels = mock(IGChatModelRuntimeConfigurationDao.class);
		when(chatModels.defaultHandler()).thenReturn(chatModel);
		IGReactiveChatAgentsNetworkServiceFactory factory = mock(IGReactiveChatAgentsNetworkServiceFactory.class);
		when(factory.create(any(), any(), any(), any(), any())).thenReturn(service);
		when(service.getFlux()).thenReturn(Flux.empty());
		IGRuntimeBinder binder = mock(IGRuntimeBinder.class);
		when(binder.getImplementationOf(IGReactiveChatAgentsNetworkServiceFactory.class)).thenReturn(factory);
		ObjectProvider<IGUserUploadContentHandler> uploads = mock(ObjectProvider.class);
		runner = new GChatEnvironmentNetworkRunner(binder, lifeCycle, chatModels, uploads);

		network = new GAgentsNetwork();
		network.setCode("the-network");
		request = new GeboChatRequest();
		request.setId("request-1");
		request.setUserChatContextCode("conversation-1");
		request.setQuery("What is new?");
		response = new GeboChatResponse();
		response.setId("response-1");
		when(lifeCycle.getSessionChatModel(request)).thenReturn(chatModel);
		when(lifeCycle.createEmptyResponse(request)).thenReturn(response);
		when(lifeCycle.startRequest(eq(request), eq(chatModel), any())).thenReturn(mock(LLMChatRequestResources.class));
		when(lifeCycle.getMinimalChatContext(eq(request), anyInt())).thenReturn(mock(MinimalChatContext.class));
	}

	@Test
	void aRunThatAnsweredEndsItsRequestAsAChatDoesWithTheAnswerOnTheRequestsResponse() throws Exception {
		GeboChatResponse answered = new GeboChatResponse();
		answered.setQueryResponse("Nothing new since yesterday.");
		answered.setListedDocumentNames(List.of("the-report.pdf"));
		when(service.executeNetwork(any(), any(), any())).thenReturn(new GeboChatMessageEnvelope(answered));

		ChatEnvironmentRun run = runner.run(network, request, ChatRunAttachments.none(), null, null, null);

		assertSame(answered, run.getResponse(), "the caller gets the network's answer, as before");
		assertEquals("Nothing new since yesterday.", response.getQueryResponse(),
				"the answer is saved on the request's own response, whose called functions the recorder fills");
		assertEquals(List.of("the-report.pdf"), response.getListedDocumentNames());
		InOrder ending = inOrder(lifeCycle);
		ending.verify(lifeCycle).endRequest(request, response);
		ending.verify(lifeCycle).chatRequestCompleted(request, chatModel);
		ending.verify(lifeCycle).releaseRequest(request);
	}

	@Test
	void aRunThatFailedReleasesItsRequestAndSavesNothing() throws Exception {
		when(service.executeNetwork(any(), any(), any())).thenThrow(new AgentException("The model is down"));

		assertThrows(AgentException.class,
				() -> runner.run(network, request, ChatRunAttachments.none(), null, null, null));

		verify(lifeCycle).releaseRequest(request);
		verify(lifeCycle, never()).endRequest(any(), any());
		verify(lifeCycle, never()).chatRequestCompleted(any(), any());
		verify(service).dispose();
	}

	@Test
	void aRunThatCouldNotStartHasNothingToRelease() throws Exception {
		when(lifeCycle.startRequest(eq(request), eq(chatModel), any()))
				.thenThrow(new ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException(
						"Another request is still running in the chat conversation-1"));

		assertThrows(AgentException.class,
				() -> runner.run(network, request, ChatRunAttachments.none(), null, null, null));

		verify(lifeCycle, never()).releaseRequest(any());
		verify(lifeCycle, never()).endRequest(any(), any());
	}
}
