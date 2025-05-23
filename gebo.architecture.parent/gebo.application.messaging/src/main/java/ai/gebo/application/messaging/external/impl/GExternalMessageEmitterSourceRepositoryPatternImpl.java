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

import ai.gebo.application.messaging.external.IGExternalMessageEmitterSource;
import ai.gebo.application.messaging.external.IGExternalMessageEmitterSourceRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

/**
 * Gebo.ai Commentor
 * 
 * Implementation of the IGExternalMessageEmitterSourceRepositoryPattern interface,
 * extending the generic GAbstractImplementationsRepositoryPattern class specific
 * to IGExternalMessageEmitterSource.
 * 
 * This service is responsible for managing the list of external message emitter
 * sources.
 */
@Service
public class GExternalMessageEmitterSourceRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGExternalMessageEmitterSource>
		implements IGExternalMessageEmitterSourceRepositoryPattern {

    /**
     * Injects a list of IGExternalMessageEmitterSource implementations.
     * 
     * @param implementations a list of implementations, injection may not be required.
     */
	public GExternalMessageEmitterSourceRepositoryPatternImpl(
			@Autowired(required = false) List<IGExternalMessageEmitterSource> implementations) {
		super(implementations);
	}

    /**
     * Retrieves the code value of a given IGExternalMessageEmitterSource.
     * 
     * @param x the external message emitter source
     * @return the ID of the source
     */
	@Override
	public String getCodeValue(IGExternalMessageEmitterSource x) {
		return x.getId();
	}
}