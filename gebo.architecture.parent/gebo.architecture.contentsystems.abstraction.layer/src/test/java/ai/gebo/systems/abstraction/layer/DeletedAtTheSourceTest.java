/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.systems.abstraction.layer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory.EnricherMappers;

/**
 * A document the source itself reports deleted (the folder watcher does, for a removed
 * file) has its contents erased from the stores at the end of the session, as a
 * document a publish finds gone; it is not sent to be read. Found live: the vectors of a
 * removed file stayed and the chat kept citing it.
 */
class DeletedAtTheSourceTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	void aDocumentDeletedAtTheSourceIsErasedFromTheStores() throws Exception {
		final IGMessageBroker broker = mock(IGMessageBroker.class);
		when(broker.checkReceivingComponentPresent(anyString(), anyString())).thenReturn(true);
		final List<GMessageEnvelope<?>> sent = new ArrayList<>();
		org.mockito.Mockito.doAnswer(call -> {
			sent.add(call.getArgument(0));
			return null;
		}).when(broker).accept(any());
		final IWorkflowRouter router = mock(IWorkflowRouter.class);
		final IGContentDispatchingEvaluator evaluator = mock(IGContentDispatchingEvaluator.class);
		when(evaluator.isProcessable(any(), any(), any(), any())).thenReturn(true);
		final IGContentManagementSystemHandler handler = mock(IGContentManagementSystemHandler.class);
		when(handler.checkUpdatedOrDeleted(any(), any(), any())).thenReturn(Stream.empty());
		final DocumentReferenceRepository documents = mock(DocumentReferenceRepository.class);
		when(documents.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
				any(), any(), any())).thenReturn(Stream.empty());
		final VirtualFolderRepository folders = mock(VirtualFolderRepository.class);
		when(folders.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCodeAndDeletedFalseAndLastesJobIdNot(
				any(), any(), any())).thenReturn(Stream.empty());
		final GJobStatus job = new GJobStatus();
		job.setCode("job-1");
		final GProjectEndpoint endpoint = mock(GProjectEndpoint.class);
		when(endpoint.getCode()).thenReturn("endpoint-1");
		final IGContentConsumer saved = mock(IGContentConsumer.class);

		final GIOCContentConsumer consumer = new GIOCContentConsumer(new EnricherMappers(x -> x, x -> x), job,
				evaluator, handler, endpoint, router, mock(GIOCModuleContentsDispatcher.class),
				mock(IGUserMessagesConsumer.class), mock(IGContentsAccessErrorConsumer.class), saved, broker, documents,
				folders, null);
		final GDocumentReference removed = new GDocumentReference();
		removed.setCode("kb/project/endpoint/archive/2026/review.txt");
		removed.setName("review.txt");
		removed.setDeleted(true);
		consumer.accept(removed);
		consumer.endConsuming();

		verify(saved).accept(removed);
		verify(router, never()).routeToNextSteps(any(), any(), any(), any(), any());
		final List<GInternalDeletionMessagePayload> deletions = sent.stream()
				.map(GMessageEnvelope::getPayload).filter(p -> p instanceof GInternalDeletionMessagePayload)
				.map(p -> (GInternalDeletionMessagePayload) p).toList();
		assertTrue(!deletions.isEmpty(), "every store holding contents is asked to erase them");
		deletions.forEach(d -> assertEquals(List.of("kb/project/endpoint/archive/2026/review.txt"), d.getCodes4deletion()));
	}
}
