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
import ai.gebo.llms.abstraction.layer.services.IGModelsListProvider;
import ai.gebo.llms.abstraction.layer.services.IGModelsListProviderRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Service implementation class for the IGModelsListProviderRepositoryPattern interface.
 * This class extends the GAbstractImplementationsRepositoryPattern by providing
 * the necessary implementation for the IGModelsListProvider repository pattern.
 * It is annotated with @Service to denote it's a Spring service component.
 */
@Service
public class GModelsListProviderRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGModelsListProvider>
		implements IGModelsListProviderRepositoryPattern {

	/**
	 * Constructor for GModelsListProviderRepositoryPatternImpl.
	 * Initializes the service by injecting a list of IGModelsListProvider implementations.
	 * 
	 * @param implementations A list of IGModelsListProvider implementations, automatically
	 *                        wired by Spring. This parameter is not required by default.
	 */
	public GModelsListProviderRepositoryPatternImpl(
			@Autowired(required = false) List<IGModelsListProvider> implementations) {
		super(implementations);

	}

	/**
	 * Retrieves the code value from the given IGModelsListProvider.
	 * 
	 * @param x The IGModelsListProvider instance from which to extract the code value.
	 * @return The code value, which is the ID of the provider.
	 */
	@Override
	public String getCodeValue(IGModelsListProvider x) {
		return x.getId();
	}

}