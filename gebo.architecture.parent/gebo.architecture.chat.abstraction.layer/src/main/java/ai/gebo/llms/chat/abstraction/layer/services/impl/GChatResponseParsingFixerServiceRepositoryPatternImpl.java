/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatResponseParsingFixerServiceRepository;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the IGChatResponseParsingFixerServiceRepository interface
 * extending GAbstractImplementationsRepositoryPattern. This service manages a
 * repository of IGChatResponseParsingFixerService implementations.
 */
@Service
public class GChatResponseParsingFixerServiceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGChatResponseParsingFixerService>
        implements IGChatResponseParsingFixerServiceRepository {

    /**
     * Constructs a GChatResponseParsingFixerServiceRepositoryPatternImpl with a
     * list of IGChatResponseParsingFixerService implementations.
     * 
     * @param implementations List of IGChatResponseParsingFixerService
     *                        implementations, can be null if none are provided.
     */
    public GChatResponseParsingFixerServiceRepositoryPatternImpl(
            @Autowired(required = false) List<IGChatResponseParsingFixerService> implementations) {
        super(implementations);

    }

    /**
     * Retrieves the code value (name) of the handled type for a given service
     * implementation.
     * 
     * @param x IGChatResponseParsingFixerService instance
     * @return The name of the handled type.
     */
    @Override
    public String getCodeValue(IGChatResponseParsingFixerService x) {

        return x.getHandledType().getName();
    }

    /**
     * Finds an IGChatResponseParsingFixerService by the handled type.
     * 
     * @param <T>  The type parameter of the handled type.
     * @param type The class of the type to find an implementation for.
     * @return The IGChatResponseParsingFixerService implementation handling the
     *         specified type, or null if not found.
     */
    public <T> IGChatResponseParsingFixerService<T> findByHandledType(Class<T> type) {
        return findImplementation(x -> {

            return type != null && type.equals(x.getHandledType());
        });
    }
}