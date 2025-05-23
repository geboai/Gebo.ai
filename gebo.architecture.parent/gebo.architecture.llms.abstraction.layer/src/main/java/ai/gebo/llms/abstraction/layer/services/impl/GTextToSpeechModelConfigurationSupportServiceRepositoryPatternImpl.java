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
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the text-to-speech model configuration support service repository pattern.
 * This component is defined as a singleton, meaning a single instance will be created and shared.
 */
@Component
@Scope("singleton")
public class GTextToSpeechModelConfigurationSupportServiceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGTextToSpeechModelConfigurationSupportService>
        implements IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern {

    /**
     * Constructor that accepts a list of text-to-speech model configuration support service implementations.
     * It uses dependency injection to support optional implementations list.
     * 
     * @param implementations a list of IGTextToSpeechModelConfigurationSupportService implementations, 
     *                        potentially injected by Spring's IoC container.
     */
    public GTextToSpeechModelConfigurationSupportServiceRepositoryPatternImpl(
            @Autowired(required = false) List<IGTextToSpeechModelConfigurationSupportService> implementations) {
        super(implementations);
    }

    /**
     * Returns the unique identifier for a given text-to-speech model configuration support service.
     * 
     * @param x an instance of IGTextToSpeechModelConfigurationSupportService from which to extract the code value.
     * @return a string representing the unique identifier of the given service.
     */
    @Override
    public String getCodeValue(IGTextToSpeechModelConfigurationSupportService x) {
        return x.getId();
    }

}