/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * This class provides a Data Access Object (DAO) implementation for Google Drive project endpoints.
 * It extends the abstract runtime configuration DAO and implements the project endpoint runtime
 * configuration DAO interface specifically for Google Drive endpoints.
 */
@Service
public class GoogleDriveProjectEndpointDao
		extends GAbstractRuntimeConfigurationDao<GGoogleDriveProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GGoogleDriveProjectEndpoint> {

	/**
	 * Constructs a new GoogleDriveProjectEndpointDao with the specified dynamic source.
	 * Initializes the parent class with an empty static configuration list and the provided
	 * dynamic configuration source.
	 *
	 * @param dynamic The dynamic source for Google Drive project endpoint configurations
	 */
	public GoogleDriveProjectEndpointDao(GoogleDriveProjectEndpointSource dynamic) {
		super(List.of(), dynamic);

	}

}