/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.external.IGExternalMessageReceiverProviderSource;
import ai.gebo.application.messaging.external.IGExternalMessageReceiverProviderSourceRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the repository pattern for external message receiver sources.
 * This service class extends the abstract implementations repository pattern
 * and is responsible for handling the collection of IGExternalMessageReceiverSource instances.
 */
@Service
public class GExternalMessageReceiverProviderSourceRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGExternalMessageReceiverProviderSource>
        implements IGExternalMessageReceiverProviderSourceRepositoryPattern {

    /**
     * Constructor for GExternalMessageReceiverSourceRepositoryPatternImpl.
     * 
     * @param implementations a list of IGExternalMessageReceiverSource implementations
     *                        that will be managed by this repository. Autowired to inject
     *                        dependencies, and not required by default.
     */
    public GExternalMessageReceiverProviderSourceRepositoryPatternImpl(
            @Autowired(required = false) List<IGExternalMessageReceiverProviderSource> implementations) {
        super(implementations);

    }

    /**
     * Retrieves the code value of the provided external message receiver source.
     * 
     * @param x the instance of IGExternalMessageReceiverSource for which to retrieve the code value.
     * @return the identifier of the external message receiver source.
     */
    @Override
    public String getCodeValue(IGExternalMessageReceiverProviderSource x) {
        return x.getId();
    }
}