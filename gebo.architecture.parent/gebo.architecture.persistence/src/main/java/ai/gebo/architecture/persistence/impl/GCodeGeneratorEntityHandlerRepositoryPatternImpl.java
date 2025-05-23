/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandler;
import ai.gebo.architecture.persistence.IGCodeGeneratorEntityHandlerRepositoryPattern;
import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 * 
 * Service implementation of the IGCodeGeneratorEntityHandlerRepositoryPattern interface. 
 * It extends the GAbstractImplementationsRepositoryPattern class and provides 
 * specific implementations for handling IGCodeGeneratorEntityHandler objects.
 */
@Service
public class GCodeGeneratorEntityHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IGCodeGeneratorEntityHandler>
		implements IGCodeGeneratorEntityHandlerRepositoryPattern {

	/**
	 * Constructs an instance of GCodeGeneratorEntityHandlerRepositoryPatternImpl with an optional list of implementations.
	 * 
	 * @param implementations List of IGCodeGeneratorEntityHandler objects to be managed by this repository.
	 */
	public GCodeGeneratorEntityHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IGCodeGeneratorEntityHandler> implementations) {
		super(implementations);
	}

	/**
	 * Retrieves the code value associated with a given IGCodeGeneratorEntityHandler.
	 * 
	 * @param x The IGCodeGeneratorEntityHandler for which the code value is to be retrieved.
	 * @return The name of the affected class associated with the handler.
	 */
	@Override
	public String getCodeValue(IGCodeGeneratorEntityHandler x) {
		return x.getAffectedClass().getName();
	}

	/**
	 * Finds a suitable IGCodeGeneratorEntityHandler for a given GBaseObject.
	 * 
	 * @param <T>    The type parameter extending GBaseObject.
	 * @param object The object for which an applicable handler is to be retrieved.
	 * @return The IGCodeGeneratorEntityHandler that is applicable to the given object, or null if none is found.
	 */
	@Override
	public <T extends GBaseObject> IGCodeGeneratorEntityHandler getAppliableFor(T object) {
		if (object == null)
			return null;
		// Custom logic to find the implementation based on the object’s class
		return findImplementation(x -> {
			return x.getAffectedClass() != null && x.getAffectedClass().equals(object.getClass())
					|| (x.isAppliedToExtendingClass() && x.getAffectedClass().isAssignableFrom(object.getClass()));
		});
	}

}