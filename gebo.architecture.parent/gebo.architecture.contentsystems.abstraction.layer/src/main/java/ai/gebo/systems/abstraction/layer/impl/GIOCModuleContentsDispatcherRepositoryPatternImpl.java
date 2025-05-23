/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.IGIOCModuleContentsDispatcherRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the IGIOCModuleContentsDispatcherRepositoryPattern interface 
 * that extends GAbstractImplementationsRepositoryPattern for handling 
 * IGIOCModuleContentsDispatcher implementations.
 */
@Service
public class GIOCModuleContentsDispatcherRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGIOCModuleContentsDispatcher>
		implements IGIOCModuleContentsDispatcherRepositoryPattern {

	/**
	 * Constructs a new instance, injecting a list of IGIOCModuleContentsDispatcher
	 * implementations if available.
	 * 
	 * @param implementations a list of IGIOCModuleContentsDispatcher implementations
	 */
	public GIOCModuleContentsDispatcherRepositoryPatternImpl(
			@Autowired(required = false) List<IGIOCModuleContentsDispatcher> implementations) {
		super(implementations);
	}

	/**
	 * Returns a unique code value for a given IGIOCModuleContentsDispatcher instance.
	 * 
	 * @param x the IGIOCModuleContentsDispatcher instance
	 * @return the messaging module ID of the dispatcher
	 */
	@Override
	public String getCodeValue(IGIOCModuleContentsDispatcher x) {
		return x.getMessagingModuleId();
	}
}