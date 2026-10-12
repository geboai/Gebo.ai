/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.local;

import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreProduct;
import ai.gebo.ragsystem.vectorstores.config.GeboAIVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.local.model.LocalConfig;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.services.IGVectorStoreFactoryBuilderWithTesting;

/**
 * Registers the embedded store as the {@link VectorStoreProduct#LOCAL} vendor and
 * converts its configuration between the yml and the persisted form.
 *
 * Being an ordinary {@code @Service} implementation of the builder interface is
 * all it takes to appear in
 * {@code GVectorStoreFactoryBuilderRepositoryPatternImpl}, so switching
 * {@code ai.gebo.vectorstore.use} to LOCAL is enough to select it and nothing else
 * in the platform learns about the embedded store.
 *
 * Unlike every other product here, LOCAL has nothing to connect to, so a missing
 * configuration block is not an error: the defaults of {@link LocalConfig} already
 * describe a working store under the work directory.
 */
@Service
public class LocalVectorStoreFactoryBuilderImpl implements IGVectorStoreFactoryBuilderWithTesting {

	/**
	 * @return the product this builder serves
	 */
	@Override
	public VectorStoreProduct getProduct() {
		return VectorStoreProduct.LOCAL;
	}

	/**
	 * Builds a factory over the given settings.
	 *
	 * @param config the embedded store settings, may be null for defaults
	 * @return the factory
	 * @throws LLMConfigException if the settings are of the wrong type
	 */
	@Override
	public <T extends GBaseVectorStoreConfig> IGVectorStoreFactory<T> build(T config) throws LLMConfigException {
		if (config != null && !(config instanceof LocalConfig)) {
			throw new LLMConfigException("The LOCAL vector store cannot be built from a "
					+ config.getClass().getName() + " configuration");
		}
		return new LocalVectorStoreFactory((LocalConfig) config);
	}

	/**
	 * Pulls the embedded store settings out of the yml configuration, defaulting
	 * them when the block is absent.
	 *
	 * @param ymlConfiguration the yml vector store configuration
	 * @return the embedded store settings, never null
	 * @throws LLMConfigException if the argument is not a yml configuration
	 */
	@Override
	public <T extends GBaseVectorStoreConfig, O> T extractConfiguration(O ymlConfiguration) throws LLMConfigException {
		if (!(ymlConfiguration instanceof GeboAIVectorStoreConfig cfg)) {
			throw new LLMConfigException("Expected a GeboAIVectorStoreConfig, got: "
					+ (ymlConfiguration == null ? "null" : ymlConfiguration.getClass().getName()));
		}
		LocalConfig local = cfg.getLocal();
		return (T) (local == null ? new LocalConfig() : local);
	}

	/**
	 * Converts the yml configuration into the form persisted in Mongo.
	 *
	 * @param config the yml configuration
	 * @return the persisted configuration
	 * @throws LLMConfigException if the argument is not a yml configuration
	 */
	@Override
	public <T, O> T yml2mongoConfig(O config) throws LLMConfigException {
		if (!(config instanceof GeboAIVectorStoreConfig cfg)) {
			throw new LLMConfigException("Expected a GeboAIVectorStoreConfig, got: "
					+ (config == null ? "null" : config.getClass().getName()));
		}
		GeboMongoVectorStoreConfig out = new GeboMongoVectorStoreConfig();
		out.setProduct(getProduct());
		out.setLocalConfig(cfg.getLocal() == null ? new LocalConfig() : cfg.getLocal());
		return (T) out;
	}

	/**
	 * Converts the persisted configuration back into the yml form.
	 *
	 * @param config the persisted configuration
	 * @return the yml configuration
	 * @throws LLMConfigException if the argument is not a persisted configuration
	 */
	@Override
	public <T, O> O mongo2ymlConfig(T config) throws LLMConfigException {
		if (!(config instanceof GeboMongoVectorStoreConfig in)) {
			throw new LLMConfigException("Expected a GeboMongoVectorStoreConfig, got: "
					+ (config == null ? "null" : config.getClass().getName()));
		}
		GeboAIVectorStoreConfig out = new GeboAIVectorStoreConfig();
		out.setUse(getProduct().name());
		out.setLocal(in.getLocalConfig() == null ? new LocalConfig() : in.getLocalConfig());
		return (O) out;
	}
}
