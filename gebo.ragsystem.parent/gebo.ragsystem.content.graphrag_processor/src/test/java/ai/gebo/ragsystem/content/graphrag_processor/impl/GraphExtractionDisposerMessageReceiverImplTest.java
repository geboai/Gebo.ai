/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.graphrag_processor.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphPersistenceService;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.ragsystem.content.graphrag_processor.config.GeboGraphRagProcessorConfig;

/**
 * Pins that the knowledge graph's erasure component is always there for a deletion:
 * with the graph deployed it erases what the deletion names, without it (no
 * persistence service, {@code ai.gebo.neo4j.enabled} off) it is still created and
 * receives the deletion with nothing to do, so sending a deletion never fails.
 */
class GraphExtractionDisposerMessageReceiverImplTest {

	private static GraphExtractionDisposerMessageReceiverImpl factoryWith(DefaultListableBeanFactory beans) {
		GraphExtractionDisposerMessageReceiverImpl factory = new GraphExtractionDisposerMessageReceiverImpl(
				new GeboGraphRagProcessorConfig());
		factory.beanFactory = beans;
		return factory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void deliverKnowledgeBaseDeletion(IGMessageReceiver receiver) {
		GKnowledgeBase knowledgeBase = new GKnowledgeBase();
		knowledgeBase.setCode("kb");
		GDeletedKnowledgeBasePayload payload = new GDeletedKnowledgeBasePayload();
		payload.setKnowledgeBase(knowledgeBase);
		GMessageEnvelope envelope = new GMessageEnvelope();
		envelope.setPayload(payload);
		receiver.accept(envelope);
	}

	@Test
	void withoutTheKnowledgeGraphADeletionIsReceivedWithNothingToErase() {
		// no persistence service: the knowledge graph is not deployed
		IGMessageReceiver receiver = factoryWith(new DefaultListableBeanFactory()).create();

		deliverKnowledgeBaseDeletion(receiver);
	}

	@Test
	void withTheKnowledgeGraphADeletionErasesWhatItNames() {
		DefaultListableBeanFactory beans = new DefaultListableBeanFactory();
		IKnowledgeGraphPersistenceService persistence = mock(IKnowledgeGraphPersistenceService.class);
		beans.registerSingleton("knowledgeGraphPersistenceService", persistence);

		deliverKnowledgeBaseDeletion(factoryWith(beans).create());

		verify(persistence).knowledgeGraphDeleteByKnowledgeBaseCode("kb");
	}
}
