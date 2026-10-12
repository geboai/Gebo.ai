/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.ragsystem.content.fulltext.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GInternalDeletionMessagePayload;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.architecture.fulltext.service.IGFullTextIngestionService;
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.ragsystem.content.fulltext.processor.config.GeboFullTextProcessorConfig;

/**
 * The erasure component of the full-text index: when a document, a data source, a
 * project or a knowledge base is deleted, the chunks the full-text indexer wrote for
 * it to OpenSearch ({@code kb_chunks}) are deleted too, as the vectorizator's and the
 * knowledge graph's erasure components delete theirs. It receives the same deletion
 * payloads, sent to every store eraser by {@code GStoreDisposers}.
 *
 * <p>
 * The chunks are matched on what the indexer stored with them: the document code
 * ({@code kbsource:} plus the document reference code), the parent project and the
 * root knowledge base of the document reference - the scopes the vectorizator erases
 * by - and, for a data source, its parent project plus the content codes descending
 * from the data source's root item.
 * </p>
 *
 * <p>
 * Erasing the index must never break the deletion of the other stores: an error is
 * logged rather than rethrown. The component is registered whether or not the
 * full-text index is deployed ({@code ai.gebo.opensearch.enabled}): a deletion
 * always finds it - under microservices every topology component is a remote
 * receiver of the other services, deployed store or not - and without the index it
 * has nothing to erase.
 * </p>
 */
@Component
@Scope("singleton")
public class FullTextDisposerMessageReceiverImpl extends GAbstractMessageReceiverFactory {
	static final Logger LOGGER = LoggerFactory.getLogger(FullTextDisposerMessageReceiverImpl.class);

	@Autowired
	BeanFactory beanFactory;

	protected FullTextDisposerMessageReceiverImpl(GeboFullTextProcessorConfig config) {
		super(config.getDisposerConfig());
	}

	/** Erases from the full-text index what a deletion payload names. */
	protected class FullTextDisposer implements IGMessageReceiver {
		private final IGFullTextIngestionService ingestionService;

		FullTextDisposer(IGFullTextIngestionService ingestionService) {
			this.ingestionService = ingestionService;
		}

		@Override
		public List<String> getAcceptedPayloadTypes() {
			return FullTextDisposerMessageReceiverImpl.this.getAcceptedPayloadTypes();
		}

		@Override
		public boolean isAcceptEveryPayloadType() {
			return FullTextDisposerMessageReceiverImpl.this.isAcceptEveryPayloadType();
		}

		@Override
		public String getMessagingModuleId() {
			return FullTextDisposerMessageReceiverImpl.this.getMessagingModuleId();
		}

		@Override
		public String getMessagingSystemId() {
			return FullTextDisposerMessageReceiverImpl.this.getMessagingSystemId();
		}

		@Override
		public SystemComponentType getComponentType() {
			return FullTextDisposerMessageReceiverImpl.this.getComponentType();
		}

		@Override
		public void accept(GMessageEnvelope t) {
			if (ingestionService == null) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Full-text index not deployed here, nothing to erase for "
							+ (t.getPayload() != null ? t.getPayload().getClass().getName() : null));
				}
				return;
			}
			LOGGER.info("Begin accept(..) deleting full-text index chunks");
			try {
				dispose(t);
			} catch (Throwable th) {
				LOGGER.error("Error erasing full-text index chunks on deletion; continuing", th);
			}
			LOGGER.info("End accept(..) deleting full-text index chunks");
		}

		void dispose(GMessageEnvelope t) throws Exception {
			if (t.getPayload() instanceof GDeletedProjectEndpointPayload payload) {
				final String projectCode = payload.getEndpoint().getParentProjectCode();
				final String endpointCode = payload.getEndpoint().getCode();
				LOGGER.info("Deleting full-text chunks for endpoint=>" + projectCode + "/" + endpointCode);
				final long deleted = ingestionService.deleteByProjectEndpoint(projectCode, endpointCode);
				LOGGER.info("Deleted " + deleted + " full-text chunk(s) of endpoint=>" + endpointCode);
			} else if (t.getPayload() instanceof GInternalDeletionMessagePayload payload) {
				switch (payload.getObjectsType()) {
				case DOCUMENTREF: {
					LOGGER.info("Deleting full-text chunks for content ids=>" + payload.getCodes4deletion());
					final List<FullTextDocument> documents = new ArrayList<>();
					for (String code : payload.getCodes4deletion()) {
						// as FullTextIndexingBatchMessageReceiver codes the documents it indexes
						final FullTextDocument document = new FullTextDocument();
						document.setCode(IGFullTextIngestionService.KBSOURCE + code);
						documents.add(document);
					}
					ingestionService.deleteDocuments(documents);
				}
					break;
				default:
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Internal deletion of " + payload.getObjectsType() + " has no full-text chunks");
					}
					break;
				}
			} else if (t.getPayload() instanceof GDeletedProjectPayload payload) {
				LOGGER.info("Deleting full-text chunks for project=>" + payload.getProject().getCode());
				final long deleted = ingestionService.deleteByProject(payload.getProject().getCode());
				LOGGER.info("Deleted " + deleted + " full-text chunk(s) of project=>" + payload.getProject().getCode());
			} else if (t.getPayload() instanceof GDeletedKnowledgeBasePayload payload) {
				LOGGER.info("Deleting full-text chunks for knowledgebase=>" + payload.getKnowledgeBase().getCode());
				final long deleted = ingestionService.deleteByKnowledgeBase(payload.getKnowledgeBase().getCode());
				LOGGER.info("Deleted " + deleted + " full-text chunk(s) of knowledgebase=>"
						+ payload.getKnowledgeBase().getCode());
			} else {
				throw new IllegalStateException(
						"Received message with payload type:" + t.getPayload().getClass().getName());
			}
		}
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.FULLTEXT_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.FULLTEXT_DISPOSE_COMPONENT;
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

	/** A disposer erasing through the full-text ingestion service, when the index is deployed. */
	@Override
	public IGMessageReceiver create() {
		return new FullTextDisposer(beanFactory.getBeanProvider(IGFullTextIngestionService.class).getIfAvailable());
	}
}
