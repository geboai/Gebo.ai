/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilderRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This class is an implementation of the IGVectorStoreFactoryBuilderRepositoryPattern interface.
 * It extends GAbstractImplementationsRepositoryPattern and provides specific functionality for managing
 * IGVectorStoreFactoryBuilder implementations within a repository pattern framework.
 */
@Service
public class GVectorStoreFactoryBuilderRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGVectorStoreFactoryBuilder>
		implements IGVectorStoreFactoryBuilderRepositoryPattern {
	
	/**
	 * Constructor that initializes the repository with a list of IGVectorStoreFactoryBuilder implementations.
	 * 
	 * @param implementations A list of IGVectorStoreFactoryBuilder instances to manage.
	 */
	public GVectorStoreFactoryBuilderRepositoryPatternImpl(
			@Autowired(required = false) List<IGVectorStoreFactoryBuilder> implementations) {
		super(implementations);
	}
	
	/**
	 * Retrieves the code value for a given IGVectorStoreFactoryBuilder by returning the name of the product.
	 *
	 * @param x The IGVectorStoreFactoryBuilder instance from which to retrieve the code value.
	 * @return The name of the product associated with the given IGVectorStoreFactoryBuilder.
	 */
	@Override
	public String getCodeValue(IGVectorStoreFactoryBuilder x) {
		return x.getProduct().name();
	}
}
