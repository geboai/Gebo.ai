/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.VectorStoreRuntimeConfiguration;

/**
 * Gebo.ai comment agent
 * 
 * This interface provides a method to obtain the runtime configuration 
 * for a vector store. Implementations are expected to deliver the necessary
 * configuration details required for vector store operations.
 */
public interface IGVectorStoreConfigurationProvider {
	/**
	 * Retrieves the runtime configuration for a vector store.
	 * 
	 * @return a VectorStoreRuntimeConfiguration object containing configuration data
	 * @throws LLMConfigException if the configuration cannot be provided
	 */
	public VectorStoreRuntimeConfiguration get() throws LLMConfigException;
}
