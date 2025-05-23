/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.VectorStore;
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
import ai.gebo.core.messages.GDeletedKnowledgeBasePayload;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.core.messages.GDeletedProjectPayload;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GVectorizedContent;
import ai.gebo.llms.abstraction.layer.vectorstores.repository.VectorizedContentRepository;
import ai.gebo.ragsystem.content.vectorizator.config.GeboVectorizatorConfig;

/**
 * AI generated comments
 * 
 * Factory component responsible for creating message receivers that handle
 * vector cleanup operations when content is deleted from the system.
 * This component listens for deletion events of various types and
 * removes associated vector embeddings from vector stores.
 */
@Component
@Scope("singleton")
public class VectorizatorDisposerMessageReceiverImpl extends GAbstractMessageReceiverFactory {
	/**
	 * Constructor for the vectorizator disposer factory.
	 * 
	 * @param config Configuration for the vectorizator component
	 */
	protected VectorizatorDisposerMessageReceiverImpl(GeboVectorizatorConfig config) {
		super(config.getDisposerConfig());

	}

	/**
	 * Bean factory for retrieving required dependencies
	 */
	@Autowired
	BeanFactory beanFactory;
	
	/**
	 * Logger for the factory class
	 */
	static Logger LOGGER = LoggerFactory.getLogger(VectorizatorDisposerMessageReceiverImpl.class);

	/**
	 * Implementation of the message receiver that handles vector disposal operations.
	 * This class is responsible for cleaning up vector embeddings when content
	 * is deleted from the system.
	 */
	protected class VectorizatorDisposer implements IGMessageReceiver {
		/**
		 * Repository for accessing vectorized content data
		 */
		VectorizedContentRepository vectorizedContentRepository;
		
		/**
		 * DAO for retrieving embedding model configurations
		 */
		IGEmbeddingModelRuntimeConfigurationDao embeddingRuntimeDao;
		
		/**
		 * Logger for the disposer class
		 */
		static Logger LOGGER = LoggerFactory.getLogger(VectorizatorDisposer.class);

		/**
		 * Constructor for the vectorizator disposer.
		 * 
		 * @param vectorizedContentRepository Repository for accessing vectorized content
		 * @param embeddingRuntimeDao DAO for embedding model configurations
		 */
		VectorizatorDisposer(VectorizedContentRepository vectorizedContentRepository,
				IGEmbeddingModelRuntimeConfigurationDao embeddingRuntimeDao) {
			this.vectorizedContentRepository = vectorizedContentRepository;
			this.embeddingRuntimeDao = embeddingRuntimeDao;
		}

		@Override
		public List<String> getAcceptedPayloadTypes() {

			return VectorizatorDisposerMessageReceiverImpl.this.getAcceptedPayloadTypes();
		}

		@Override
		public boolean isAcceptEveryPayloadType() {

			return VectorizatorDisposerMessageReceiverImpl.this.isAcceptEveryPayloadType();
		}

		@Override
		public String getMessagingModuleId() {
			return VectorizatorDisposerMessageReceiverImpl.this.getMessagingModuleId();
		}

		@Override
		public String getMessagingSystemId() {
			return VectorizatorDisposerMessageReceiverImpl.this.getMessagingSystemId();
		}

		@Override
		public SystemComponentType getComponentType() {
			return VectorizatorDisposerMessageReceiverImpl.this.getComponentType();
		}

		/**
		 * Processes the message and performs vector deletion based on the payload type.
		 * Handles different types of deletion events including:
		 * - Project endpoint deletion
		 * - Document reference deletion
		 * - Project deletion
		 * - Knowledge base deletion
		 * 
		 * @param t The message envelope containing the deletion payload
		 */
		@Override
		public void accept(GMessageEnvelope t) {
			LOGGER.info("Begin accept(..) deleting vectors");
			// Get all embedding model configurations
			List<IGConfigurableEmbeddingModel> configs = embeddingRuntimeDao.getConfigurations();
			Map<String, VectorStore> storeById = new HashMap<String, VectorStore>();
			for (IGConfigurableEmbeddingModel entry : configs) {
				storeById.put(entry.getCode(), entry.getVectorStore());
			}
			
			// Consumer function to delete vectors from the appropriate vector store
			Consumer<GVectorizedContent> deletingConsumer = x -> {
				try {
					String vectorStoreId = x.getId().getVectorStoreId();
					VectorStore store = storeById.get(vectorStoreId);
					if (store != null && x.getVectorsId() != null && !x.getVectorsId().isEmpty()) {
						store.delete(x.getVectorsId());
					}
				} catch (Throwable th) {
					LOGGER.error("Error in dispose endpoint vectors", th);
				}
			};
			
			// Handle different payload types for deletion
			if (t.getPayload() instanceof GDeletedProjectEndpointPayload) {
				// Handle project endpoint deletion
				GDeletedProjectEndpointPayload payload = (GDeletedProjectEndpointPayload) t.getPayload();
				LOGGER.info("Deleting vectors for endpoint=>" + payload.getEndpoint().getCode());
				Stream<GVectorizedContent> vectorized = vectorizedContentRepository
						.findByProjectEndpoint(payload.getEndpoint());

				vectorized.forEach(deletingConsumer);
				try {
					vectorizedContentRepository.deleteByProjectEndpoint(payload.getEndpoint());
				} catch (Throwable th) {
					LOGGER.error("Error in dispose endpoint vectorized content infos", th);
				}
			} else if (t.getPayload() instanceof GInternalDeletionMessagePayload) {
				// Handle internal deletion messages
				GInternalDeletionMessagePayload payload = (GInternalDeletionMessagePayload) t.getPayload();
				switch (payload.getObjectsType()) {
				case DOCUMENTREF: {
					// Handle document reference deletion
					LOGGER.info("Deleting vectors for content ids=>" + payload.getCodes4deletion());
					Stream<GVectorizedContent> vectorized = vectorizedContentRepository
							.findByIdDocReferenceCodeIn(payload.getCodes4deletion());
					vectorized.forEach(deletingConsumer);
					try {
						vectorizedContentRepository.deleteByIdDocReferenceCodeIn(payload.getCodes4deletion());
					} catch (Throwable th) {
						LOGGER.error("Error in dispose vectorized content infos by codes", th);
					}
				}
					break;
				}

			} else if (t.getPayload() instanceof GDeletedProjectPayload) {
				// Handle project deletion
				GDeletedProjectPayload payload = (GDeletedProjectPayload) t.getPayload();
				LOGGER.info("Deleting vectors for project=>" + payload.getProject().getCode());
				Stream<GVectorizedContent> vectorized = vectorizedContentRepository
						.findByParentProjectCode(payload.getProject().getCode());
				vectorized.forEach(deletingConsumer);
				try {
					vectorizedContentRepository.deleteByParentProjectCode(payload.getProject().getCode());
				} catch (Throwable th) {
					LOGGER.error("Error in dispose vectorized content infos by project", th);
				}
			} else if (t.getPayload() instanceof GDeletedKnowledgeBasePayload) {
				// Handle knowledge base deletion
				GDeletedKnowledgeBasePayload payload = (GDeletedKnowledgeBasePayload) t.getPayload();
				LOGGER.info("Deleting vectors for knowledgebase=>" + payload.getKnowledgeBase().getCode());
				Stream<GVectorizedContent> vectorized = vectorizedContentRepository
						.findByRootKnowledgebaseCode(payload.getKnowledgeBase().getCode());
				vectorized.forEach(deletingConsumer);
				try {
					vectorizedContentRepository.deleteByRootKnowledgebaseCode(payload.getKnowledgeBase().getCode());
				} catch (Throwable th) {
					LOGGER.error("Error in dispose vectorized content infos by knowledgebase", th);
				}
			} else
				throw new IllegalStateException(
						"Received message with payload type:" + t.getPayload().getClass().getName());
			LOGGER.info("End accept(..) deleting vectors");
		}
	}

	/**
	 * @return The ID of the vectorizator module
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.VECTORIZATOR_MODULE;
	}

	/**
	 * @return The ID of the vectorization disposal component
	 */
	@Override
	public String getMessagingSystemId() {

		return GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT;
	}

	/**
	 * @return The type of component (application component)
	 */
	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * @return List of payload types this component can handle
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GDeletedProjectEndpointPayload.class.getName(), GInternalDeletionMessagePayload.class.getName(),
				GDeletedProjectPayload.class.getName(), GDeletedKnowledgeBasePayload.class.getName());
	}

	/**
	 * @return Whether this component accepts all payload types
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	/**
	 * Creates a new instance of the vectorizator disposer.
	 * 
	 * @return A new message receiver instance
	 */
	@Override
	public IGMessageReceiver create() {
		VectorizatorDisposer newDisposer = new VectorizatorDisposer(
				beanFactory.getBean(VectorizedContentRepository.class),
				beanFactory.getBean(IGEmbeddingModelRuntimeConfigurationDao.class));
		return newDisposer;
	}

}