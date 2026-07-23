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
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This class is an implementation of the repository pattern for 
 * managing IGTranscriptModelConfigurationSupportService implementations.
 * It extends an abstract pattern to provide specific behavior for this service interface.
 */
@Service
public class GTranscriptModelConfigurationSupportServiceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGTranscriptModelConfigurationSupportService>
        implements IGTranscriptModelConfigurationSupportServiceRepositoryPattern {

    /**
     * Constructor for GTranscriptModelConfigurationSupportServiceRepositoryPatternImpl.
     * 
     * @param implementations a list of service implementations, which are autowired
     *                        optionally (it may be null or absent).
     */
    public GTranscriptModelConfigurationSupportServiceRepositoryPatternImpl(
            @Autowired(required = false) List<IGTranscriptModelConfigurationSupportService> implementations) {
        super(implementations);
    }

    /**
     * Retrieves the model type code a transcript support service declares: handlers are
     * looked up by that code, which is the same key the configurations carry in
     * modelTypeCode. getId() defaults to the class name for any handler that does not
     * override it, which would make it unfindable by type code.
     *
     * @param x an instance of IGTranscriptModelConfigurationSupportService
     * @return the model type code of the given service.
     */
    @Override
    public String getCodeValue(IGTranscriptModelConfigurationSupportService x) {
        return x.getType().getCode();
    }
}