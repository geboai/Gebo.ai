/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.core.analisys.services.IGAnalisysProvider;
import ai.gebo.core.analisys.services.IGAnalisysProviderRepositoryPattern;

/**
 * Implementation of the IGAnalisysProviderRepositoryPattern interface.
 * Utilizes a repository pattern to manage analysis providers.
 * 
 * AI generated comments
 */
@Service
public class GAnalisysProviderRepositoryPatternImpl extends
		GAbstractImplementationsRepositoryPattern<IGAnalisysProvider> implements IGAnalisysProviderRepositoryPattern {

	/**
	 * Constructor that initializes the repository with a list of analysis provider implementations.
	 * 
	 * @param implementations A list of IGAnalisysProvider instances, injected by Spring. 
	 *                        It may be null if no implementations are available.
	 */
	public GAnalisysProviderRepositoryPatternImpl(
			@Autowired(required = false) List<IGAnalisysProvider> implementations) {
		super(implementations);

	}

	/**
	 * Retrieves the code value for a given analysis provider.
	 * 
	 * @param x The analysis provider instance from which the code value (ID) is retrieved.
	 * @return The ID of the given analysis provider.
	 */
	@Override
	public String getCodeValue(IGAnalisysProvider x) {
		return x.getId();
	}

}