/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactory;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This class is an implementation of the IGEntityProcessingRunnableFactoryRepositoryPattern 
 * interface. It extends the abstract class GAbstractImplementationsRepositoryPattern, 
 * providing a concrete implementation for handling multiple IGEntityProcessingRunnableFactory 
 * implementations using the repository pattern.
 */
@Service
public class GEntityProcessingRunnableFactoryRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGEntityProcessingRunnableFactory>
		implements IGEntityProcessingRunnableFactoryRepositoryPattern {

	/**
	 * Constructor for GEntityProcessingRunnableFactoryRepositoryPatternImpl.
	 * 
	 * @param implementations a list of IGEntityProcessingRunnableFactory instances that
	 *                        can be injected by Spring Framework. The injection is 
	 *                        optional (i.e., not required).
	 */
	public GEntityProcessingRunnableFactoryRepositoryPatternImpl(
			@Autowired(required = false) List<IGEntityProcessingRunnableFactory> implementations) {
		super(implementations);
	}

	/**
	 * Retrieves the code value for a given IGEntityProcessingRunnableFactory instance.
	 * 
	 * @param x the IGEntityProcessingRunnableFactory instance
	 * @return the name of the entity class as a String
	 */
	@Override
	public String getCodeValue(IGEntityProcessingRunnableFactory x) {
		// Returns the name of the entity class associated with the factory
		return x.getEntityClass().getName();
	}
}