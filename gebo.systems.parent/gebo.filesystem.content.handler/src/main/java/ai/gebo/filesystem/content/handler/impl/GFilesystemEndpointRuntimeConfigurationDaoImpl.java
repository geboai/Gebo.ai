/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of the project endpoint runtime configuration data access object for filesystem endpoints.
 * This service manages the runtime configuration for filesystem project endpoints, allowing dynamic access
 * and modification of filesystem endpoint configurations.
 */
@Service
public class GFilesystemEndpointRuntimeConfigurationDaoImpl
		extends GAbstractRuntimeConfigurationDao<GFilesystemProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GFilesystemProjectEndpoint> {
	
	/**
	 * Nested adapter class that provides dynamic configuration source capabilities for filesystem endpoints.
	 * This adapter bridges the repository with the dynamic configuration interface.
	 */
	@Service
	public static class DynamicEndpointAdapter extends
			GDynamicConfigurationSourceAdapter<GFilesystemProjectEndpoint, FilesystemProjectEndpointRepository>
			implements IGDynamicConfigurationSource<GFilesystemProjectEndpoint> {

	}

	/**
	 * Constructor for the filesystem endpoint runtime configuration DAO.
	 * 
	 * @param dynamic Optional dynamic configuration source for filesystem project endpoints.
	 *                If provided, enables runtime configuration changes.
	 */
	public GFilesystemEndpointRuntimeConfigurationDaoImpl(
			@Autowired(required = false) IGDynamicConfigurationSource<GFilesystemProjectEndpoint> dynamic) {
		super(new ArrayList(), dynamic);
		// Initializes with an empty ArrayList and the provided dynamic configuration source
	}

}