/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of a configuration data access object for the Filesystem Content Management System.
 * This service provides configuration data for the filesystem-based content handler.
 * It extends the abstract runtime configuration DAO and implements the content management system
 * configuration interface.
 */
@Service
public class GFilesystemConfigurationDao
		extends GAbstractRuntimeConfigurationDao<GFilesystemContentManagementSystem>
		implements IGContentManagementSystemConfigurationDao<GFilesystemContentManagementSystem> {
	
	/**
	 * Static singleton instance of the Filesystem CMS with default configuration.
	 * This serves as the predefined configuration for the filesystem content handler.
	 */
	static final GFilesystemContentManagementSystem singleSystem = new GFilesystemContentManagementSystem();
	
	/**
	 * Static initializer block to configure the default properties for the filesystem CMS.
	 * Sets the type, code, description, and read-only status for the system.
	 */
	static {
		singleSystem.setContentManagementSystemType("DEFAULT.FILESYSTEM.CONTENT.HANDLER");
		singleSystem.setCode("FILESYSTEM.HANDLER");
		singleSystem.setDescription("local/shared filesystem");
		singleSystem.setReadonly(true);
	}

	/**
	 * Constructor that initializes the configuration DAO with the default filesystem CMS
	 * and an optional dynamic configuration source.
	 * 
	 * @param dynamic Optional dynamic configuration source that can provide runtime updates to the configuration
	 */
	public GFilesystemConfigurationDao(
			@Autowired(required = false) IGDynamicConfigurationSource<GFilesystemContentManagementSystem> dynamic) {
		super(List.of(singleSystem), dynamic);
	}
}