/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Gebo.ai comment agent
 * 
 * Abstract class that implements the IGImplementationsRepositoryPattern interface.
 * Provides a mechanism to manage a list of implementations for a given interface.
 * Acts as a listener for Spring's ContextRefreshedEvent to perform actions 
 * upon application context initialization.
 *
 * @param <ImplementedInterface> The interface type for which this repository maintains implementations.
 */
public abstract class GAbstractImplementationsRepositoryPattern<ImplementedInterface> implements
		IGImplementationsRepositoryPattern<ImplementedInterface>, ApplicationListener<ContextRefreshedEvent> {

	// Logger instance for logging information and errors.
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	// List to store implementations of the interface.
	List<ImplementedInterface> implementations = new ArrayList<ImplementedInterface>();

	/**
	 * Constructor with a list of implementations.
	 *
	 * @param implementations A list of implementations for the interface.
	 */
	public GAbstractImplementationsRepositoryPattern(List<ImplementedInterface> implementations) {
		this.implementations = implementations;
	}

	/**
	 * Constructor with a single implementation.
	 *
	 * @param iface An implementation of the interface.
	 */
	public GAbstractImplementationsRepositoryPattern(ImplementedInterface iface) {
		this.implementations = iface != null ? List.of(iface) : null;
	}

	/**
	 * Returns the list of registered implementations.
	 *
	 * @return List of ImplementedInterface.
	 */
	@Override
	public List<ImplementedInterface> getImplementations() {
		return implementations != null ? implementations : new ArrayList<ImplementedInterface>();
	}

	/**
	 * Adds a new implementation to the list.
	 * If an implementation with the same code already exists, a RuntimeException is thrown.
	 *
	 * @param iface The implementation to add.
	 */
	@Override
	public void addImplementation(ImplementedInterface iface) {
		LOGGER.info("Runtime adding implementation => " + iface.getClass().getName()+" with code:"+getCodeValue(iface));
		if (this.implementations == null) {
			this.implementations = new ArrayList<ImplementedInterface>();
		}
		String code = getCodeValue(iface);
		if (this.findByCode(code) != null) {
			String errorMessage = "Duplicating " + code + " implementation id with class: "
					+ iface.getClass().getName();
			LOGGER.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		this.implementations.add(iface);
	}

	/**
	 * Handles the ContextRefreshedEvent to log the current list of implementations.
	 * Executed when the application context is initialized or refreshed.
	 *
	 * @param event The ContextRefreshedEvent event.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Begin listing implementation repository");
			if (this.implementations != null) {
				for (ImplementedInterface implementedInterface : implementations) {
					LOGGER.info("bound implementation=>" + implementedInterface.getClass().getName()+" with code:"+ getCodeValue(implementedInterface));
				}
			}
			LOGGER.info("End listing implementation repository");
		}
	}
}