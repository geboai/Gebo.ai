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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import java.util.Map;
import org.mockito.ArgumentCaptor;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.contents.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.session.model.GChatSessionOpeningOptions;
import ai.gebo.model.OperationStatus;
import org.springframework.web.multipart.MultipartFile;
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
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
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
	private IGUserUploadContentHandler uploadHandler;
	private GeboChatRequest request;
	private GeboChatResponse response;
	private LLMChatRequestResources resources;
	private MinimalChatContext minimalContext;
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
		uploadHandler = mock(IGUserUploadContentHandler.class);
		ObjectProvider<IGUserUploadContentHandler> uploads = mock(ObjectProvider.class);
		when(uploads.getIfAvailable()).thenReturn(uploadHandler);
		runner = new GChatEnvironmentNetworkRunner(binder, lifeCycle, chatModels, uploads,
				GNetworkEnvironmentBuilder.plain(), null);
		network = new GAgentsNetwork();
		network.setCode("the-network");
		request = new GeboChatRequest();
		request.setId("request-1");
		request.setUserChatContextCode("conversation-1");
		request.setQuery("What is new?");
		response = new GeboChatResponse();
		response.setId("response-1");
		resources = mock(LLMChatRequestResources.class);
		minimalContext = mock(MinimalChatContext.class);
		when(lifeCycle.getSessionChatModel(request)).thenReturn(chatModel);
		when(lifeCycle.createEmptyResponse(request)).thenReturn(response);
		when(lifeCycle.startRequest(eq(request), eq(chatModel), any())).thenReturn(resources);
		when(lifeCycle.getMinimalChatContext(eq(request), anyInt())).thenReturn(minimalContext);
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
		verify(lifeCycle, never()).removeChatSession(any());
	}

	@Test
	void theConversationIsOpenedOrSharedBeforeAnythingReadsIt() throws Exception {
		when(service.executeNetwork(any(), any(), any())).thenReturn(null);

		runner.run(GChatEnvironmentRunRequest.builder().network(network).request(request).origin("HARNESS")
				.sharedWithUsers(List.of("the-harness-account", "the-admin")).build());

		ArgumentCaptor<GChatSessionOpeningOptions> options = ArgumentCaptor.forClass(GChatSessionOpeningOptions.class);
		InOrder opening = inOrder(lifeCycle);
		opening.verify(lifeCycle).ensureChatSessionExists(eq(request), options.capture());
		opening.verify(lifeCycle).getSessionChatModel(request);
		assertEquals(List.of("the-harness-account", "the-admin"), options.getValue().getSharedWithUsers());
		assertEquals("HARNESS", options.getValue().getRunOrigin());
		assertTrue(!options.getValue().isTransientRun());
	}

	@Test
	void theFilesTheCallerSentAreCarriedByTheRequestWhoseStartTakesThemIn() throws Exception {
		UserUploadedContent stored = new UserUploadedContent();
		stored.setFileName("notes.txt");
		when(uploadHandler.chatSessionUpload(eq("conversation-1"), anyList())).thenReturn(OperationStatus.of(List.of(stored)));
		when(lifeCycle.startRequest(eq(request), eq(chatModel), any())).thenAnswer(invocation -> {
			assertEquals(List.of(stored), request.getUserUploadedContents(),
					"the request carries the stored files when it is started, as a chat's does");
			return resources;
		});
		when(service.executeNetwork(any(), any(), any())).thenReturn(null);

		runner.run(network, request,
				new ChatRunAttachments(List.of(mock(MultipartFile.class)), List.of(), List.of()), null);

		verify(lifeCycle).startRequest(eq(request), eq(chatModel), any());
		verify(lifeCycle, never()).addUploadedDocument(any(), any(), any(), any());
	}

	@Test
	void theNetworkIsGivenTheEnvironmentOfTheRunWithTheKnowledgeBasesOfTheConversation() throws Exception {
		GKnowledgeBase kb = new GKnowledgeBase();
		kb.setCode("kb-1");
		when(lifeCycle.getSessionAvailableKnowledgeBases(request)).thenReturn(List.of(kb));
		when(service.executeNetwork(any(), any(), any())).thenReturn(null);

		runner.run(GChatEnvironmentRunRequest.builder().network(network).request(request)
				.environmentOfThisRun(Map.of("OF_THE_CALLER", "yes")).build());

		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String, Object>> environment = ArgumentCaptor.forClass(Map.class);
		verify(service).executeNetwork(any(), eq("What is new?"), environment.capture());
		assertEquals(List.of("kb-1"), environment.getValue().get(StandardAgentsNetworkEnvironmentEntries.KNOWLEDGE_BASES_CODE));
		assertTrue(environment.getValue()
				.get(StandardAgentsNetworkEnvironmentEntries.CHAT_RUNTIME_DATA) instanceof ChatPipelineExecutionRuntimeData,
				"the network is asked the question; the request it is asked in is its environment");
		assertEquals("yes", environment.getValue().get("OF_THE_CALLER"));
		verify(resources).setAvailableKnowledgeBaseCodes(List.of("kb-1"));
		verify(minimalContext).setAvailableKnowledgeBaseCodes(List.of("kb-1"));
	}

	@Test
	void aTransientConversationIsDroppedWhenTheRunIsOverAndNothingOfItIsKept() throws Exception {
		when(service.executeNetwork(any(), any(), any())).thenReturn(null);

		runner.run(GChatEnvironmentRunRequest.builder().network(network).request(request).transientConversation(true)
				.origin("MCP").build());

		verify(lifeCycle).releaseRequest(request);
		verify(lifeCycle).removeChatSession("conversation-1");
		verify(lifeCycle, never()).endRequest(any(), any());
		verify(lifeCycle, never()).chatRequestCompleted(any(), any());
	}

	@Test
	void aRunThatFailedReleasesItsRequestSavesNothingAndNamesItsConversation() throws Exception {
		when(service.executeNetwork(any(), any(), any())).thenThrow(new AgentException("The model is down"));

		GChatEnvironmentRunException failure = assertThrows(GChatEnvironmentRunException.class,
				() -> runner.run(network, request, ChatRunAttachments.none(), null, null, null));

		assertEquals("conversation-1", failure.getChatContextCode());
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
