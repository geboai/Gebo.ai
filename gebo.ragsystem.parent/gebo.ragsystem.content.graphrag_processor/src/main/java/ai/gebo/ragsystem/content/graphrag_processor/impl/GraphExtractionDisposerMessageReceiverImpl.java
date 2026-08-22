/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphPersistenceService;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.ragsystem.content.graphrag_processor.config.GeboGraphRagProcessorConfig;

/**
 * Erases knowledge-graph data when a knowledge base, project, data-source
 * endpoint or set of documents is deleted.
 *
 * <p>
 * The vector store and the Mongo chunk store already plug into the admin
 * deletion propagation - the {@code GDeleted*Payload} / {@code
 * GInternalDeletionMessagePayload} messages emitted by the knowledge-base and
 * project controllers - through their own disposer receivers. The knowledge
 * graph did not, so entities and relations extracted from a deleted source were
 * left behind in Neo4j (a GDPR Art. 17 gap the data-flow register surfaced).
 * This receiver closes that gap, mirroring {@code
 * VectorizatorDisposerMessageReceiverImpl}: it consumes the same deletion
 * messages and purges the matching subgraph, keyed by the same identity fields
 * ({@code knowledgeBaseCode}, {@code projectCode}, {@code
 * projectEndpointClass}/{@code projectEndpointCode}, document code) that the
 * extractor stamps on each document node.
 * </p>
 *
 * <p>
 * Registered only when {@code ai.gebo.neo4j.enabled} is true, matching the
 * persistence service it drives.
 * </p>
 */
@ConditionalOnProperty(prefix = "ai.gebo.neo4j", name = "enabled", havingValue = "true")
@Component
@Scope("singleton")
public class GraphExtractionDisposerMessageReceiverImpl extends GAbstractMessageReceiverFactory {

	static Logger LOGGER = LoggerFactory.getLogger(GraphExtractionDisposerMessageReceiverImpl.class);

	@Autowired
	BeanFactory beanFactory;

	protected GraphExtractionDisposerMessageReceiverImpl(GeboGraphRagProcessorConfig config) {
		super(config.getDisposerConfig());
	}

	/**
	 * Message receiver that runs the actual graph erasure for one deletion event.
	 */
	protected class GraphExtractionDisposer implements IGMessageReceiver {

		private final IKnowledgeGraphPersistenceService knowledgeGraphPersistenceService;

		GraphExtractionDisposer(IKnowledgeGraphPersistenceService knowledgeGraphPersistenceService) {
			this.knowledgeGraphPersistenceService = knowledgeGraphPersistenceService;
		}

		@Override
		public List<String> getAcceptedPayloadTypes() {
			return GraphExtractionDisposerMessageReceiverImpl.this.getAcceptedPayloadTypes();
		}

		@Override
		public boolean isAcceptEveryPayloadType() {
			return GraphExtractionDisposerMessageReceiverImpl.this.isAcceptEveryPayloadType();
		}

		@Override
		public String getMessagingModuleId() {
			return GraphExtractionDisposerMessageReceiverImpl.this.getMessagingModuleId();
		}

		@Override
		public String getMessagingSystemId() {
			return GraphExtractionDisposerMessageReceiverImpl.this.getMessagingSystemId();
		}

		@Override
		public SystemComponentType getComponentType() {
			return GraphExtractionDisposerMessageReceiverImpl.this.getComponentType();
		}

		@Override
		public void accept(GMessageEnvelope t) {
			LOGGER.info("Begin accept(..) deleting knowledge-graph data");
			if (t.getPayload() instanceof GDeletedProjectEndpointPayload payload) {
				// The message carries the shareable centralized endpoint; match the graph
				// by the concrete endpoint reference (real class name + code) stamped on
				// the document nodes at extraction time.
				var endpointRef = payload.getEndpoint().getRemoteProjectReference();
				String endpointClass = endpointRef.getClassName() != null ? String.valueOf(endpointRef.getClassName())
						: null;
				String endpointCode = endpointRef.getCode() != null ? String.valueOf(endpointRef.getCode()) : null;
				LOGGER.info("Deleting knowledge-graph data for endpoint=>" + endpointCode);
				knowledgeGraphPersistenceService.knowledgeGraphDeleteByProjectEndpoint(endpointClass, endpointCode);
			} else if (t.getPayload() instanceof GInternalDeletionMessagePayload payload) {
				switch (payload.getObjectsType()) {
				case DOCUMENTREF:
					LOGGER.info("Deleting knowledge-graph data for content ids=>" + payload.getCodes4deletion());
					knowledgeGraphPersistenceService.knowledgeGraphDeleteByDocumentCodes(payload.getCodes4deletion());
					break;
				default:
					break;
				}
			} else if (t.getPayload() instanceof GDeletedProjectPayload payload) {
				LOGGER.info("Deleting knowledge-graph data for project=>" + payload.getProject().getCode());
				knowledgeGraphPersistenceService.knowledgeGraphDeleteByProjectCode(payload.getProject().getCode());
			} else if (t.getPayload() instanceof GDeletedKnowledgeBasePayload payload) {
				LOGGER.info("Deleting knowledge-graph data for knowledgebase=>" + payload.getKnowledgeBase().getCode());
				knowledgeGraphPersistenceService
						.knowledgeGraphDeleteByKnowledgeBaseCode(payload.getKnowledgeBase().getCode());
			} else {
				throw new IllegalStateException(
						"Received message with payload type:" + t.getPayload().getClass().getName());
			}
			LOGGER.info("End accept(..) deleting knowledge-graph data");
		}
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.KNOWLEDGE_GRAPH_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.KNOWLEDGE_GRAPH_DISPOSE_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(GDeletedProjectEndpointPayload.class.getName(), GInternalDeletionMessagePayload.class.getName(),
				GDeletedProjectPayload.class.getName(), GDeletedKnowledgeBasePayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	@Override
	public IGMessageReceiver create() {
		return new GraphExtractionDisposer(beanFactory.getBean(IKnowledgeGraphPersistenceService.class));
	}

}
