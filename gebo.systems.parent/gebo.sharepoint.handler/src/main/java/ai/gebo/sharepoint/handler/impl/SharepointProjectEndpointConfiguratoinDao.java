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
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * This service class provides data access operations for SharePoint project endpoints.
 * It extends the abstract runtime configuration DAO and implements the project endpoint
 * runtime configuration DAO interface specific to SharePoint endpoints.
 */
@Service 
class SharepointProjectEndpointConfiguratoinDao
		extends GAbstractRuntimeConfigurationDao<GSharepointProjectEndpoint>
		implements IGProjectEndpointRuntimeConfigurationDao<GSharepointProjectEndpoint> {
	
	/**
	 * Constructs a new SharePoint project endpoint configuration DAO.
	 * Initializes the DAO with an empty list of static configurations and
	 * the provided dynamic configuration source.
	 * 
	 * @param dynamic The dynamic configuration source for SharePoint project endpoints
	 */
	public SharepointProjectEndpointConfiguratoinDao(SharepointProjectEndpointDynamicConfigurationSource dynamic) {
		super(List.of(), dynamic);
	}
}