/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.repositories.SharepointProjectEndpointRepository;

/**
 * AI generated comments This class serves as a dynamic configuration source for
 * Sharepoint project endpoints. It extends the
 * GDynamicConfigurationSourceAdapter to provide functionality for retrieving
 * and managing Sharepoint project endpoint configurations dynamically at
 * runtime. The class is annotated with @Service to be automatically detected
 * and registered as a Spring bean.
 */
@Service
class SharepointProjectEndpointDynamicConfigurationSource
		extends GDynamicConfigurationSourceAdapter<GSharepointProjectEndpoint, SharepointProjectEndpointRepository> {
}