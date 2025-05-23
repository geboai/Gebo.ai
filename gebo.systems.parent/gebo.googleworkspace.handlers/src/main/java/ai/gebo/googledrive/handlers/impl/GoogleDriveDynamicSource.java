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
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveSystemRepository;

/**
 * AI generated comments
 * 
 * A service implementation that provides dynamic configuration source 
 * functionality for Google Drive systems. This class extends the generic
 * dynamic configuration source adapter, specializing it for Google Drive
 * system entities and their repository.
 * 
 * The class is annotated with @Service to be automatically detected and
 * registered as a Spring Bean during component scanning.
 */
@Service
public class GoogleDriveDynamicSource
		extends GDynamicConfigurationSourceAdapter<GGoogleDriveSystem, GoogleDriveSystemRepository> {
}