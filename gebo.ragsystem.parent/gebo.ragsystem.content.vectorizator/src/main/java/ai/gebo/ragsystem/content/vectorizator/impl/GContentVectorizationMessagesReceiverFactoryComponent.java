/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.core.messages.GRawContentMessageFragmentPayload;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreConfigurationProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.ragsystem.content.vectorizator.IGEmbeddingMessageReceiver;
import ai.gebo.ragsystem.content.vectorizator.config.GeboVectorizatorConfig;
import ai.gebo.ragsystem.vectorstores.qdrant.model.QdrantConfig;
import ai.gebo.ragsystem.vectorstores.redis.model.RedisConfig;

/**
 * AI generated comments
 * 
 * Factory component responsible for creating content vectorization message
 * receivers. This singleton component handles the creation of message receivers
 * for vectorizing different types of content payloads within the RAG system.
 */
@Component
@Scope("singleton")
public class GContentVectorizationMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GContentVectorizationMessagesReceiverFactoryComponent.class);

	/** Configuration for the vectorizator component */
	final GeboVectorizatorConfig config;

	/**
	 * Constructor that initializes the factory with the vectorizator configuration.
	 * 
	 * @param config The configuration for the vectorizator
	 */
	public GContentVectorizationMessagesReceiverFactoryComponent(GeboVectorizatorConfig config) {
		super(config.getVectorizatorReceiverConfig());
		this.config = config;
	}

	/** Runtime binder for dependency injection */
	@Autowired
	protected IGRuntimeBinder runtimeBinder;

	// Field-injected, and optional, purely so adding the data-flow register does
	// not change this component's constructor signature or make it fail to start
	// on a node where the vector store or the embedding models are not configured
	// yet - the register must degrade to "reports nothing", never to a boot error.
	@Autowired(required = false)
	protected IGVectorStoreConfigurationProvider vectorStoreConfigurationProvider;

	@Autowired(required = false)
	protected IGEmbeddingModelRuntimeConfigurationDao embeddingModelsDao;

	/**
	 * Reports the vectorization step: chunk text is sent to an embedding model and
	 * the resulting vectors are retained in the configured vector store.
	 *
	 * <p>
	 * This is the most consequential entry in the whole register. The embedding
	 * model receives the <b>content of the customer's documents verbatim</b>, and
	 * when it is a hosted provider that is a transfer to an external processor -
	 * the thing a GDPR Art. 44 review exists to find. So each configured embedding
	 * model is reported as its own endpoint, alongside the store the vectors land
	 * in.
	 * </p>
	 */
	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		DataEndpoint vectorStore = describeVectorStore();
		if (vectorStore != null) {
			flow.getDataEndpoints().add(vectorStore);
		}

		for (DataEndpoint model : describeEmbeddingModels()) {
			flow.getDataEndpoints().add(model);
			DataTransformationMetaInfo engine = DataTransformationMetaInfo.of("embedding-" + model.getId(),
					"Embedding model " + model.getDescription(), List.of(MetaEndpointType.CHUNK),
					List.of(MetaEndpointType.VECTORIAL_DATABASE));
			flow.getEngines().add(engine);
			if (vectorStore != null) {
				flow.getTransformations()
						.add(DataTransformationInfo.of("vectorization-" + model.getId(),
								"Chunk text is sent to the embedding model and the vectors are retained", engine,
								flow.qualifiedId(model.getId()), flow.qualifiedId(vectorStore.getId())));
			}
			// The chunk store this component consumes from lives in the tokenizer
			// component, so the inbound edge is named with that component's identity.
			flow.getTransformations()
					.add(DataTransformationInfo.of("chunk-submission-" + model.getId(),
							"Chunk text leaves this installation for the embedding model", engine,
							GDataFlowMetaInfos.qualifiedId(
									new GeboComponentInfo(GStandardModulesConstraints.TOKENIZER_MODULE,
											GStandardModulesConstraints.TOKENIZER_COMPONENT),
									"chunk-cache"),
							flow.qualifiedId(model.getId())));
		}

		return flow.getDataEndpoints().isEmpty() ? null : flow;
	}

	/**
	 * The configured vector store, named down to host and port. Returns null when
	 * no vector store is configured yet, or when the configured product is one
	 * whose concrete config carries no address.
	 */
	/** The default embedding model's code, which names its Qdrant collection. */
	private String defaultEmbeddingModelCode() {
		if (embeddingModelsDao == null) {
			return null;
		}
		try {
			IGConfigurableEmbeddingModel model = embeddingModelsDao.defaultHandler();
			return model != null ? model.getCode() : null;
		} catch (RuntimeException e) {
			return null;
		}
	}

	private DataEndpoint describeVectorStore() {
		if (vectorStoreConfigurationProvider == null) {
			return null;
		}
		VectorStoreRuntimeConfiguration configuration;
		try {
			configuration = vectorStoreConfigurationProvider.get();
		} catch (LLMConfigException | RuntimeException e) {
			// Not configured yet is a normal state on a fresh installation.
			LOGGER.debug("Vector store not configured, omitting it from the data-flow register", e);
			return null;
		}
		if (configuration == null || configuration.getProduct() == null) {
			return null;
		}
		// The collection/keyspace the vectors are written to is named after the
		// embedding model's code (QdrantVectorStoreFactory uses
		// embeddingConfiguration.getCode()); naming it here gives an auditor the
		// exact store location. There is one collection per embedding model - the
		// default model's is reported as the representative collection.
		String collection = defaultEmbeddingModelCode();

		DataEndpoint endpoint = new DataEndpoint();
		endpoint.setId("vector-store");
		endpoint.setDescription(collection != null ? "Vector store of embedded chunks (collection '" + collection + "')"
				: "Vector store of embedded chunks");
		endpoint.setProduct(configuration.getProduct().name());
		endpoint.setTypes(new ArrayList<MetaEndpointType>(List.of(MetaEndpointType.VECTORIAL_DATABASE)));
		endpoint.setOutput(true);
		// Vectors are derived from the ingested text and remain re-identifiable
		// enough to be treated as carrying whatever the sources carried.
		endpoint.setPersonalData(true);
		endpoint.setDisposer(new GeboComponentInfo(GStandardModulesConstraints.VECTORIZATOR_MODULE,
				GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT));

		GBaseVectorStoreConfig config = configuration.getConfiguration();
		if (config instanceof QdrantConfig qdrant) {
			endpoint.setEndpoint(qdrant.isTls() ? "https" : "http", qdrant.getHost(), qdrant.getPort(), collection);
			if (qdrant.getApiKey() != null && !qdrant.getApiKey().isEmpty()) {
				// Named, never carried.
				endpoint.setSecretReference("ai.gebo.vectorstore.qdrant.apiKey");
			}
		} else if (config instanceof RedisConfig redis) {
			endpoint.setEndpoint("redis", redis.getHost(), redis.getPort(), collection);
			if (redis.getUsername() != null && !redis.getUsername().isEmpty()) {
				endpoint.setSecretReference("ai.gebo.vectorstore.redis.password");
			}
		}
		endpoint.setLocality(DataEndpointLocality.hintFromLocator(endpoint.getEndpoint()));
		return endpoint;
	}

	/**
	 * One endpoint per configured embedding model, located by the base URL the
	 * chunk text is actually posted to.
	 *
	 * <p>
	 * Locality errs deliberately towards {@link DataEndpointLocality#EXTERNAL_PROVIDER}:
	 * only Ollama and the ONNX embeddings run inside a deployment, so an inference
	 * endpoint that is not on a loopback or single-label host is at best
	 * self-hosted elsewhere and at worst a third-party processor. Over-flagging a
	 * transfer costs an administrator one review; under-flagging one is the
	 * failure this register exists to prevent.
	 * </p>
	 */
	private List<DataEndpoint> describeEmbeddingModels() {
		List<DataEndpoint> out = new ArrayList<DataEndpoint>();
		if (embeddingModelsDao == null) {
			return out;
		}
		List<IGConfigurableEmbeddingModel> models;
		try {
			models = embeddingModelsDao.getConfigurations();
		} catch (RuntimeException e) {
			LOGGER.debug("Embedding models not readable, omitting them from the data-flow register", e);
			return out;
		}
		if (models == null) {
			return out;
		}
		for (IGConfigurableEmbeddingModel model : models) {
			if (model == null || model.getConfig() == null) {
				continue;
			}
			GBaseModelConfig config = model.getConfig();
			GBaseModelChoice choosedModel = config.getChoosedModel();
			// The model actually selected, and the provider it belongs to - the two
			// facts an auditor needs about an inference endpoint. providerId is the
			// same identifier LLM usage is accounted under by
			// AbstractLLMSUsageCrudService, so the register and the usage records
			// name the same thing.
			String modelCode = choosedModel != null ? choosedModel.getCode() : null;
			String providerId = choosedModel != null && choosedModel.getMetaInfos() != null
					? choosedModel.getMetaInfos().getProviderId()
					: null;

			DataEndpoint endpoint = new DataEndpoint();
			endpoint.setId("embedding-model-" + model.getCode());
			endpoint.setDescription(modelCode != null
					? (model.getDescription() != null ? model.getDescription() + " (" + modelCode + ")" : modelCode)
					: (model.getDescription() != null ? model.getDescription() : model.getCode()));
			endpoint.setProduct(providerId != null ? providerId
					: (config.getModelTypeCode() != null ? config.getModelTypeCode() : "embedding model"));
			endpoint.setEndpoint(config.getBaseUrl());
			endpoint.setTypes(new ArrayList<MetaEndpointType>(List.of(MetaEndpointType.LLM_ENDPOINT)));
			// Chunk text goes out; the vectors come back.
			endpoint.setInput(true);
			endpoint.setOutput(true);
			endpoint.setPersonalData(true);
			if (config.getApiSecretCode() != null && !config.getApiSecretCode().isEmpty()) {
				endpoint.setSecretReference(config.getApiSecretCode());
			}
			DataEndpointLocality hint = DataEndpointLocality.hintFromLocator(endpoint.getEndpoint());
			endpoint.setLocality(hint == DataEndpointLocality.LOCAL_DEPLOYMENT ? DataEndpointLocality.LOCAL_DEPLOYMENT
					: DataEndpointLocality.EXTERNAL_PROVIDER);
			out.add(endpoint);
		}
		return out;
	}

	/**
	 * Specifies which payload types this factory's receivers can process.
	 * 
	 * @return A list of accepted payload type class names
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GRawContentMessageFragmentPayload.class.getName(), GDocumentReferencePayload.class.getName());
	}

	/**
	 * Indicates whether the factory accepts all payload types.
	 * 
	 * @return false, as this factory only accepts specific payload types
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	/**
	 * Returns the messaging module identifier for this factory.
	 * 
	 * @return The vectorizator module ID from standard module constraints
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.VECTORIZATOR_MODULE;
	}

	/**
	 * Returns the messaging system identifier for this factory.
	 * 
	 * @return The vectorization component ID from standard module constraints
	 */
	@Override
	public String getMessagingSystemId() {

		return GStandardModulesConstraints.VECTORIZATION_COMPONENT;
	}

	/**
	 * Specifies the component type of this factory.
	 * 
	 * @return APPLICATION_COMPONENT as the system component type
	 */
	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Nested class that implements a batch message receiver specifically for
	 * content vectorization. Extends the generic nested batch aggregator to provide
	 * content-specific processing logic.
	 */
	class BatchContentVectorizationMessagesReceiver extends GNestedBatchAggregatorMessageReceiver {

		/**
		 * Constructor for the batch content vectorization message receiver.
		 * 
		 * @param batchMessagesReceiver The batch message receiver implementation
		 * @param batchThreshold        The threshold for batching messages
		 */
		public BatchContentVectorizationMessagesReceiver(IGBatchMessagesReceiver batchMessagesReceiver,
				int batchThreshold) {
			super(batchMessagesReceiver, batchThreshold);
		}

		/**
		 * Accepts messages for processing if they contain compatible payload types.
		 * 
		 * @param msg The message envelope to process
		 */
		@Override
		public void accept(GMessageEnvelope msg) {
			if (msg.getPayload() instanceof GDocumentReferencePayload) {
				super.accept(msg);
			}
		}

		/**
		 * Determines if a batch should be immediately processed based on cumulative
		 * content size.
		 * 
		 * @param messages The list of message envelopes in the current batch
		 * @return true if the cumulative size exceeds the configured threshold
		 */
		@Override
		protected boolean isImmediateFlushRequired(List<GMessageEnvelope> messages) {
			long cumulativeSize = 0l;
			for (GMessageEnvelope msg : messages) {
				if (msg.getPayload() instanceof GDocumentReferencePayload) {
					GDocumentReferencePayload payload = (GDocumentReferencePayload) msg.getPayload();
					if (payload.getDocumentReference() != null
							&& payload.getDocumentReference().getFileSize() != null) {
						cumulativeSize += payload.getDocumentReference().getFileSize().longValue();
					}
				}
			}
			return cumulativeSize >= config.getMaximumMessagesCumulatedBytesThreshold();
		}
	};

	/**
	 * Creates a new message receiver instance for content vectorization.
	 * 
	 * @return A timed-out message receiver configured for content vectorization
	 */
	@Override
	public IGTimedOutMessageReceiver create() {
		IGEmbeddingMessageReceiver router = runtimeBinder.getImplementationOf(IGEmbeddingMessageReceiver.class);
		return new BatchContentVectorizationMessagesReceiver(router,
				factoryConfig.getFlushThreshold() != null ? factoryConfig.getFlushThreshold() : 10);
	}

}