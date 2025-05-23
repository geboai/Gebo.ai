/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.vectorstores.GAccountingExtendedVectorStoreAdapter;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.abstraction.layer.vectorstores.model.EmbeddingTrafficInfo;

/**
 * AI generated comments
 * Abstract class representing a configurable embedding model with a specific configuration type.
 * 
 * @param <ModelConfig> The configuration type for the embedding model.
 * @param <EmbeddingModelType> The type of the embedding model.
 */
public abstract class GAbstractConfigurableEmbeddingModel<ModelConfig extends GBaseEmbeddingModelConfig, EmbeddingModelType extends EmbeddingModel>
        implements IGConfigurableEmbeddingModel<ModelConfig> {

    // Configuration of the embedding model.
    protected ModelConfig config = null;

    // The type of the embedding model.
    protected GEmbeddingModelType type = null;

    // The instantiated embedding model.
    protected EmbeddingModelType model = null;

    // Adapter for vector store accounting.
    protected GAccountingExtendedVectorStoreAdapter vectorStore = null;

    // Provider for the vector store factory.
    protected final IGVectorStoreFactoryProvider vectorStoreFactoryProvider;

    // Factory for creating vector stores.
    protected IGVectorStoreFactory storeFactory = null;

    /**
     * Constructs a configurable embedding model with the specified vector store factory provider.
     * 
     * @param storeFactoryProvider Provider for the vector store factory.
     */
    public GAbstractConfigurableEmbeddingModel(IGVectorStoreFactoryProvider storeFactoryProvider) {
        this.vectorStoreFactoryProvider = storeFactoryProvider;
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
        this.vectorStore = new GAccountingExtendedVectorStoreAdapter(this.storeFactory.create(config, model));
    }

    /**
     * Configures the embedding model using the provided configuration and type.
     * 
     * @param config Configuration of the embedding model.
     * @param type Type of the embedding model.
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
        return model;
    }

    @Override
    public VectorStore getVectorStore() {
        return vectorStore;
    }

    @Override
    public EmbeddingTrafficInfo getSampledBytesOfTraffic() {
        if (vectorStore != null)
            return vectorStore.getSampledBytesOfTraffic();
        return new EmbeddingTrafficInfo();
    }
}