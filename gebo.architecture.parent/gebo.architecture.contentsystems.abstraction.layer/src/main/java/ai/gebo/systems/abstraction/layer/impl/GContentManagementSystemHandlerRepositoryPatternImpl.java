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
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the IGContentManagementSystemHandlerRepositoryPattern interface.
 * This class extends the GAbstractImplementationsRepositoryPattern to provide
 * behavior specific to content management system handlers.
 */
@Service
public class GContentManagementSystemHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGContentManagementSystemHandler>
		implements IGContentManagementSystemHandlerRepositoryPattern {

	/**
	 * Constructs a new GContentManagementSystemHandlerRepositoryPatternImpl.
	 * 
	 * @param implementations a list of IGContentManagementSystemHandler implementations, 
	 *                        may be empty if none are available for autowiring.
	 */
	public GContentManagementSystemHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IGContentManagementSystemHandler> implementations) {
		super(implementations);
	}

	/**
	 * Retrieves the code value for a given IGContentManagementSystemHandler.
	 * 
	 * @param x the content management system handler from which to retrieve the code value.
	 * @return the code value of the handled system type, or null if the handler is null.
	 */
	@Override
	public String getCodeValue(IGContentManagementSystemHandler x) {
		// Provide a code value for a given handler, or null if the handler is null
		return x != null ? x.getHandledSystemType().getCode() : null;
	}
}