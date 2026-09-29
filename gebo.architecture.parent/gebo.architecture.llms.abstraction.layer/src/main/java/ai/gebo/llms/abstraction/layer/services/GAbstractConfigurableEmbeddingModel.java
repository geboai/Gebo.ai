/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

import io.micrometer.observation.ObservationRegistry;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.impl.LLMUsageRecorder;
import ai.gebo.llms.abstraction.layer.services.impl.UsageRecordingEmbeddingModel;
import ai.gebo.llms.abstraction.layer.vectorstores.GAccountingExtendedVectorStoreAdapter;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.EmbeddingTrafficInfo;

/**
 * AI generated comments Abstract class representing a configurable embedding
 * model with a specific configuration type.
 * 
 * @param <ModelConfig>        The configuration type for the embedding model.
 * @param <EmbeddingModelType> The type of the embedding model.
 */
public abstract class GAbstractConfigurableEmbeddingModel<ModelConfig extends GBaseEmbeddingModelConfig, EmbeddingModelType extends EmbeddingModel>
		implements IGConfigurableEmbeddingModel<ModelConfig>, IGProviderDealPricedModel {

	/**
	 * Prices this model by its provider deal; attached by the runtime DAO, null
	 * leaving it priced by its configuration.
	 */
	private volatile IGProviderDealService providerDealService = null;

	@Override
	public void setProviderDealService(IGProviderDealService providerDealService) {
		this.providerDealService = providerDealService;
	}

	/**
	 * The price the provider deal covering this model's API key gives to its
	 * configuration, else the configured one; read from an in-memory snapshot.
	 */
	@Override
	public GModelPricingConditions getPricingConditions() {
		return IGProviderDealPricedModel.dealOrConfiguredPricing(providerDealService, this);
	}


	// Configuration of the embedding model.
	protected ModelConfig config = null;

	// The type of the embedding model.
	protected GEmbeddingModelType type = null;

	// The instantiated embedding model.
	protected EmbeddingModelType model = null;

	// The provider model wrapped so that every call through it is recorded as usage:
	// what the vector store embeds with and what getEmbeddingModel() hands out.
	protected UsageRecordingEmbeddingModel recordingModel = null;

	// Attached by the runtime DAO once the model is created; null leaves the calls
	// unrecorded.
	private volatile LLMUsageRecorder usageRecorder = null;

	// Adapter for vector store accounting.
	protected GAccountingExtendedVectorStoreAdapter vectorStore = null;

	// Provider for the vector store factory.
	protected final IGVectorStoreFactoryProvider vectorStoreFactoryProvider;

	// Factory for creating vector stores.
	protected IGVectorStoreFactory storeFactory = null;

	/**
	 * The application's shared Micrometer {@link ObservationRegistry}, passed
	 * down to {@link #configureModel(GBaseEmbeddingModelConfig, GEmbeddingModelType)}
	 * implementations so Spring AI's built-in embedding-model observability
	 * actually reports into it, instead of each vendor building its own
	 * disconnected registry.
	 */
	protected final ObservationRegistry observationRegistry;

	/**
	 * Constructs a configurable embedding model with the specified vector store
	 * factory provider.
	 *
	 * @param storeFactoryProvider Provider for the vector store factory.
	 */
	public GAbstractConfigurableEmbeddingModel(IGVectorStoreFactoryProvider storeFactoryProvider,
			ObservationRegistry observationRegistry) {
		this.vectorStoreFactoryProvider = storeFactoryProvider;
		this.observationRegistry = observationRegistry;
	}

	@Override
	public String getCode() {
		return this.config != null ? this.config.getCode() : null;
	}

	@Override
	public String getDescription() {
		return this.config != null ? this.config.getDescription() : null;
	}

	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	@Override
	public void initialize(ModelConfig config, GEmbeddingModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		this.model = this.configureModel(config, type);
		this.recordingModel = new UsageRecordingEmbeddingModel(this.model, () -> this.config,
				() -> this.usageRecorder, this::getPricingConditions);
		this.storeFactory = this.vectorStoreFactoryProvider.get();

		// Close existing vector store if any
		if (this.vectorStore != null) {
			try {
				this.vectorStore.close();
				this.vectorStore = null;
			} catch (Throwable th) {
				// Ignore errors during vector store closing
			}
		}

		// Create and assign a new vector store adapter
		this.vectorStore = new GAccountingExtendedVectorStoreAdapter(this.storeFactory.create(config, recordingModel));
	}

	/**
	 * Attaches the recorder the embedding calls are accounted with. Called by the
	 * runtime DAO right after creating the model; the recording wrapper reads it on
	 * every call, so it applies to the vector store already built by initialize().
	 */
	public void setUsageRecorder(LLMUsageRecorder usageRecorder) {
		this.usageRecorder = usageRecorder;
	}

	/**
	 * Configures the embedding model using the provided configuration and type.
	 * 
	 * @param config Configuration of the embedding model.
	 * @param type   Type of the embedding model.
	 * @return The configured embedding model.
	 * @throws LLMConfigException If configuration fails.
	 */
	protected abstract EmbeddingModelType configureModel(ModelConfig config, GEmbeddingModelType type)
			throws LLMConfigException;

	@Override
	public ModelConfig getConfig() {
		return config;
	}

	@Override
	public void delete() throws LLMConfigException {
		// Implement deletion of resources if necessary
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, type);
	}

	@Override
	public EmbeddingModel getEmbeddingModel() {
		return recordingModel != null ? recordingModel : model;
	}

	@Override
	public IGExtendedVectorStore getVectorStore() {
		return vectorStore;
	}

	@Override
	public EmbeddingTrafficInfo getSampledBytesOfTraffic() {
		if (vectorStore != null)
			return vectorStore.getSampledBytesOfTraffic();
		return new EmbeddingTrafficInfo();
	}
}