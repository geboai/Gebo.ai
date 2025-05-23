/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

// Gebo.ai comment agent

package ai.gebo.llms.abstraction.layer.vectorstores;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Interface to build a repository pattern for vector store factories.
 * This interface extends the generic interface {@code IGImplementationsRepositoryPattern}
 * using {@code IGVectorStoreFactoryBuilder} as the implementation type.
 * It ensures that any implementing class must handle operations related to
 * vector store factory builders in the context of a repository pattern.
 */
public interface IGVectorStoreFactoryBuilderRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGVectorStoreFactoryBuilder> {
	
}