/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.fulltext.processor.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextIngestionService;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.ragsystem.content.fulltext.processor.config.GeboFullTextProcessorConfig;

/**
 * Pins the full-text index's erasure component: each deletion the store erasers
 * receive deletes the chunks of what it names, matched as the indexer stored them,
 * and an error of the index never stops the deletion of the other stores.
 */
class FullTextDisposerMessageReceiverImplTest {

	private IGFullTextIngestionService ingestion;
	private FullTextDisposerMessageReceiverImpl.FullTextDisposer disposer;

	@BeforeEach
	void setUp() {
		ingestion = mock(IGFullTextIngestionService.class);
		FullTextDisposerMessageReceiverImpl factory = new FullTextDisposerMessageReceiverImpl(
				new GeboFullTextProcessorConfig());
		disposer = factory.new FullTextDisposer(ingestion);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void deliver(Object payload) {
		GMessageEnvelope envelope = new GMessageEnvelope();
		envelope.setPayload((ai.gebo.application.messaging.IGMessagePayloadType) payload);
		disposer.accept(envelope);
	}

	@Test
	void itIsTheFullTextIndexErasureComponent() {
		FullTextDisposerMessageReceiverImpl factory = new FullTextDisposerMessageReceiverImpl(
				new GeboFullTextProcessorConfig());
		assertEquals(GStandardModulesConstraints.FULLTEXT_MODULE, factory.getMessagingModuleId());
		assertEquals(GStandardModulesConstraints.FULLTEXT_DISPOSE_COMPONENT, factory.getMessagingSystemId());
		assertEquals(List.of(GDeletedProjectEndpointPayload.class.getName(),
				GInternalDeletionMessagePayload.class.getName(), GDeletedProjectPayload.class.getName(),
				GDeletedKnowledgeBasePayload.class.getName()), factory.getAcceptedPayloadTypes());
	}

	@SuppressWarnings("unchecked")
	@Test
	void deletedDocumentsLoseTheirChunksByTheCodeTheIndexerGaveThem() throws Exception {
		GInternalDeletionMessagePayload payload = new GInternalDeletionMessagePayload();
		payload.setObjectsType(GInternalDeletionMessagePayload.ObjectType.DOCUMENTREF);
		payload.setCodes4deletion(List.of("kb/project/source/a.pdf"));

		deliver(payload);

		ArgumentCaptor<List<FullTextDocument>> documents = ArgumentCaptor.forClass(List.class);
		verify(ingestion).deleteDocuments(documents.capture());
		assertEquals(IGFullTextIngestionService.KBSOURCE + "kb/project/source/a.pdf",
				documents.getValue().get(0).getCode());
	}

	@Test
	void aDeletedDataSourceLosesTheChunksOfItsProjectAndCode() throws Exception {
		GCentralizedProjectEndpoint endpoint = new GCentralizedProjectEndpoint();
		endpoint.setCode("source");
		endpoint.setParentProjectCode("project");

		deliver(new GDeletedProjectEndpointPayload(endpoint));

		verify(ingestion).deleteByProjectEndpoint("project", "source");
	}

	@Test
	void aDeletedProjectAndADeletedKnowledgeBaseLoseTheirChunks() throws Exception {
		GProject project = new GProject();
		project.setCode("project");
		GDeletedProjectPayload projectPayload = new GDeletedProjectPayload();
		projectPayload.setProject(project);
		GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setCode("kb");
		GDeletedKnowledgeBasePayload knowledgeBasePayload = new GDeletedKnowledgeBasePayload();
		knowledgeBasePayload.setKnowledgeBase(knowledgeBase);

		deliver(projectPayload);
		deliver(knowledgeBasePayload);

		verify(ingestion).deleteByProject("project");
		verify(ingestion).deleteByKnowledgeBase("kb");
	}

	@Test
	void anIndexErrorDoesNotStopTheOtherErasers() throws Exception {
		when(ingestion.deleteByProject(anyString())).thenThrow(new FullTextException("down", null));
		GProject project = new GProject();
		project.setCode("project");
		GDeletedProjectPayload payload = new GDeletedProjectPayload();
		payload.setProject(project);

		// logged, not rethrown
		deliver(payload);

		verify(ingestion).deleteByProject("project");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void withoutTheFullTextIndexADeletionIsReceivedWithNothingToErase() {
		// no ingestion service: the full-text index is not deployed (ai.gebo.opensearch.enabled off)
		FullTextDisposerMessageReceiverImpl factory = new FullTextDisposerMessageReceiverImpl(
				new GeboFullTextProcessorConfig());
		factory.beanFactory = new DefaultListableBeanFactory();
		GProject project = new GProject();
		project.setCode("project");
		GDeletedProjectPayload payload = new GDeletedProjectPayload();
		payload.setProject(project);
		GMessageEnvelope envelope = new GMessageEnvelope();
		envelope.setPayload(payload);

		factory.create().accept(envelope);
	}

	@Test
	void withTheFullTextIndexTheFactoryErasesThroughItsIngestionService() throws Exception {
		FullTextDisposerMessageReceiverImpl factory = new FullTextDisposerMessageReceiverImpl(
				new GeboFullTextProcessorConfig());
		DefaultListableBeanFactory beans = new DefaultListableBeanFactory();
		beans.registerSingleton("fullTextIngestionService", ingestion);
		factory.beanFactory = beans;
		GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setCode("kb");
		GDeletedKnowledgeBasePayload payload = new GDeletedKnowledgeBasePayload();
		payload.setKnowledgeBase(knowledgeBase);
		disposer = (FullTextDisposerMessageReceiverImpl.FullTextDisposer) factory.create();

		deliver(payload);

		verify(ingestion).deleteByKnowledgeBase("kb");
	}

	@Test
	void anInternalDeletionOfAnotherKindTouchesNoChunk() throws Exception {
		GInternalDeletionMessagePayload payload = new GInternalDeletionMessagePayload();
		for (GInternalDeletionMessagePayload.ObjectType type : GInternalDeletionMessagePayload.ObjectType.values()) {
			if (type != GInternalDeletionMessagePayload.ObjectType.DOCUMENTREF) {
				payload.setObjectsType(type);
				deliver(payload);
			}
		}
		verifyNoInteractions(ingestion);
	}
}
