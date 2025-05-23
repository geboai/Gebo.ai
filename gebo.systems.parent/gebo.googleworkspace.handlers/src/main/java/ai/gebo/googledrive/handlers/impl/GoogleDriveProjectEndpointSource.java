/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GDynamicConfigurationSourceAdapter;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveProjectEndpointRepository;

/**
 * AI generated comments
 * 
 * A service implementation that provides configuration source functionality for Google Drive project endpoints.
 * This class extends the dynamic configuration source adapter, enabling the dynamic loading and management
 * of Google Drive project endpoint configurations from a repository.
 * 
 * The class is marked as a Spring Service component, allowing it to be automatically detected and
 * registered in the Spring application context.
 */
@Service
public class GoogleDriveProjectEndpointSource extends
		GDynamicConfigurationSourceAdapter<GGoogleDriveProjectEndpoint, GoogleDriveProjectEndpointRepository> {
}