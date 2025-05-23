/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGReadableContentsFormatHandler;
import ai.gebo.architecture.ai.IGReadableContentsFormatHandlerRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the IGReadableContentsFormatHandlerRepositoryPattern interface.
 * This class extends the GAbstractImplementationsRepositoryPattern and provides
 * a repository for handling IGReadableContentsFormatHandler instances.
 */
@Service
public class GReadableContentsFormatHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGReadableContentsFormatHandler>
		implements IGReadableContentsFormatHandlerRepositoryPattern {

	/**
	 * Constructor that initializes the repository with a list of content format handlers.
	 *
	 * @param handlers A list of IGReadableContentsFormatHandler objects, injected by Spring.
	 */
	public GReadableContentsFormatHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IGReadableContentsFormatHandler> handlers) {
		super(handlers); // Initialize the superclass with the provided handlers
	}

}