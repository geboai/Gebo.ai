/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.filesystem.content.handler.impl.GFilesystemChangesHandlingService;

/**
 * AI generated comments
 * Configuration class for the Gabo AI filesystem changes handling system.
 * This class provides Spring bean definitions for filesystem change handling services.
 */
@Configuration
public class GaboAIFilesystemsChangeConfig {

	/** Runtime binder instance used for dependency injection and runtime binding */
	IGRuntimeBinder runtimeBinder = null;

	/**
	 * Constructor for the configuration class that initializes the runtime binder.
	 * 
	 * @param runtimeBinder The runtime binder dependency injected by Spring
	 */
	public GaboAIFilesystemsChangeConfig(@Autowired IGRuntimeBinder runtimeBinder) {
		this.runtimeBinder = runtimeBinder;
	}

	/**
	 * Creates and configures a singleton instance of the filesystem changes handling service.
	 * 
	 * @return A configured GFilesystemChangesHandlingService instance
	 */
	@Bean
	@Scope("singleton")
	public GFilesystemChangesHandlingService filesystemChangeHandlingService() {
		return new GFilesystemChangesHandlingService(runtimeBinder);
	}

}