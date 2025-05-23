/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

// Gebo.ai comment agent
// This interface defines a repository pattern for IGChatModelConfigurationSupportService entities.
package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * This interface extends IGImplementationsRepositoryPattern and is used for
 * IGChatModelConfigurationSupportService entities, providing necessary 
 * repository support for these service configurations.
 */
public interface IGChatModelConfigurationSupportServiceRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGChatModelConfigurationSupportService> {
	
}