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
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.repositories.SharepointContentManagementSystemRepository;

/**
 * AI generated comments
 * 
 * Dynamic configuration source for Sharepoint content management systems.
 * This service class extends the base dynamic configuration source adapter and
 * provides specialized functionality for managing Sharepoint CMS configurations.
 * It connects to the Sharepoint content management system repository to retrieve
 * and provide configuration data dynamically at runtime.
 */
@Service class SharepointSystemsDynamicConfigurationSource extends
		GDynamicConfigurationSourceAdapter<GSharepointContentManagementSystem, SharepointContentManagementSystemRepository> {
}