/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a repository for chat response parsing fixer services.
 * This repository provides a method to find services by the type they handle.
 *
 */
public interface IGChatResponseParsingFixerServiceRepository
        extends IGImplementationsRepositoryPattern<IGChatResponseParsingFixerService> {
    
    /**
     * Finds an IGChatResponseParsingFixerService that can handle the specified type.
     *
     * @param <T>  the handled type.
     * @param type the class object representing the type to find the service for.
     * @return the service capable of handling the specified type.
     */
    public <T> IGChatResponseParsingFixerService<T> findByHandledType(Class<T> type);
}