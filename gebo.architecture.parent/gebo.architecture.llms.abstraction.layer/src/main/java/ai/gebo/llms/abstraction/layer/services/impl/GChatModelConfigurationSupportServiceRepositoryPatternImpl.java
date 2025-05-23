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
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportServiceRepositoryPattern;

/**
 * Gebo.ai comment agent
 *
 * Implementation of the IGChatModelConfigurationSupportServiceRepositoryPattern.
 * This class is a component with a singleton scope.
 * It serves as a repository for managing IGChatModelConfigurationSupportService implementations.
 */
@Component
@Scope("singleton")
public class GChatModelConfigurationSupportServiceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGChatModelConfigurationSupportService>
        implements IGChatModelConfigurationSupportServiceRepositoryPattern {

    /**
     * Constructor for GChatModelConfigurationSupportServiceRepositoryPatternImpl.
     *
     * @param implementations a list of IGChatModelConfigurationSupportService implementations
     *        that will be managed by this repository. This parameter is optional (can be null).
     */
    public GChatModelConfigurationSupportServiceRepositoryPatternImpl(
            @Autowired(required = false) List<IGChatModelConfigurationSupportService> implementations) {
        super(implementations);
    }

    /**
     * Retrieves the code value from a given IGChatModelConfigurationSupportService instance.
     *
     * @param x the IGChatModelConfigurationSupportService instance from which to retrieve the code value
     * @return the code value as a String
     */
    @Override
    public String getCodeValue(IGChatModelConfigurationSupportService x) {
        // Return the code of the type associated with the given service instance
        return x.getType().getCode();
    }
}