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
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;

/**
 * AI generated comments
 * 
 * Data Access Object for Google Drive system configurations.
 * This service extends the abstract runtime configuration DAO to handle Google Drive specific
 * system configurations. It implements the Content Management System Configuration DAO interface
 * to provide standardized access to Google Drive configuration data.
 */
@Service
public class GoogleDriveSystemsDao extends GAbstractRuntimeConfigurationDao<GGoogleDriveSystem>
		implements IGContentManagementSystemConfigurationDao<GGoogleDriveSystem> {

	/**
	 * Constructs a new GoogleDriveSystemsDao with dynamic configuration source.
	 * Initializes with an empty list of static configurations and a dynamic source
	 * for runtime configuration changes.
	 *
	 * @param dynamic The dynamic configuration source for Google Drive systems
	 */
	public GoogleDriveSystemsDao(GoogleDriveDynamicSource dynamic) {
		super(List.of(), dynamic);
		
	}
}