/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of a configuration data access object for Sharepoint content management systems.
 * This service provides access to runtime configuration data specific to Sharepoint systems.
 * It extends the abstract runtime configuration DAO and implements the CMS configuration DAO interface.
 */
@Service class SharepointSystemsConfiguratoinDao
		extends GAbstractRuntimeConfigurationDao<GSharepointContentManagementSystem>
		implements IGContentManagementSystemConfigurationDao<GSharepointContentManagementSystem> {
	
	/**
	 * Constructs a new SharepointSystemsConfiguratoinDao with dynamic configuration source.
	 * Initializes with an empty list of static configurations and the provided dynamic configuration source.
	 * 
	 * @param dynamic The dynamic configuration source for Sharepoint systems
	 */
	public SharepointSystemsConfiguratoinDao(SharepointSystemsDynamicConfigurationSource dynamic) {
		super(List.of(), dynamic);
	}
}