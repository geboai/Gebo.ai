/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This class is an implementation of the IGEmbeddingModelConfigurationSupportServiceRepositoryPattern.
 * It is a Spring component with a singleton scope.
 * It manages a list of IGEmbeddingModelConfigurationSupportService implementations.
 */
@Component
@Scope("singleton")
public class GEmbeddingModelConfigurationSupportServiceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGEmbeddingModelConfigurationSupportService>
        implements IGEmbeddingModelConfigurationSupportServiceRepositoryPattern {

    /**
     * Constructor for GEmbeddingModelConfigurationSupportServiceRepositoryPatternImpl.
     * It initializes the class with a provided list of IGEmbeddingModelConfigurationSupportService
     * implementations.
     * 
     * @param implementations A list of IGEmbeddingModelConfigurationSupportService implementations,
     *                        autowired (injected by Spring) into this component. If no implementations are provided, 
     *                        the list can be null.
     */
    public GEmbeddingModelConfigurationSupportServiceRepositoryPatternImpl(
            @Autowired(required = false) List<IGEmbeddingModelConfigurationSupportService> implementations) {
        super(implementations);
    }

    /**
     * Gets the code value for a given IGEmbeddingModelConfigurationSupportService instance.
     * 
     * @param x An instance of IGEmbeddingModelConfigurationSupportService for which to retrieve the code.
     * @return The code value from the type of the provided service instance.
     */
    @Override
    public String getCodeValue(IGEmbeddingModelConfigurationSupportService x) {
        // Retrieve the code from the type of the service
        return x.getType().getCode();
    }

}