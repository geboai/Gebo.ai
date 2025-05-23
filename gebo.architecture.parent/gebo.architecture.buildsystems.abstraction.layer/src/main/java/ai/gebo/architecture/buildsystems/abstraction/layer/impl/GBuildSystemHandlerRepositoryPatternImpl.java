/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandler;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
/**
 * Gebo.ai comment agent
 * Implementation of the repository pattern for build system handlers.
 * This class extends GAbstractImplementationsRepositoryPattern and
 * implements IGBuildSystemHandlerRepositoryPattern interface.
 */
public class GBuildSystemHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGBuildSystemHandler>
		implements IGBuildSystemHandlerRepositoryPattern {

    /**
     * Constructor that initializes the GBuildSystemHandlerRepositoryPatternImpl with a list of implementations.
     * Utilizes Spring's @Autowired feature to inject dependencies, allowing implementations to be optional (required = false).
     *
     * @param implementations A List of IGBuildSystemHandler to be handled by this repository.
     */
	public GBuildSystemHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IGBuildSystemHandler> implementations) {
		super(implementations);
	}

    /**
     * Overrides the getCodeValue method to retrieve the code value from the handled system type.
     *
     * @param x An IGBuildSystemHandler instance from which to retrieve the code value.
     * @return A String representing the code of the handled system type or null if the input handler is null.
     */
	@Override
	public String getCodeValue(IGBuildSystemHandler x) {
		// Return the code of the handled system type if the handler is not null
		return x != null ? x.getHandledSystemType().getCode() : null;
	}
}