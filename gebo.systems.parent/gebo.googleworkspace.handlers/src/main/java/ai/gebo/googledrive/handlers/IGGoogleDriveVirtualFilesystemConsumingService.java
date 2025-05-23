/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResourceReference;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * AI generated comments
 * 
 * Interface for services that consume Google Drive's virtual filesystem.
 * This interface extends the generic remote virtual filesystem consuming service
 * with specific Google Drive implementations for system, project endpoint, and resource reference.
 * It acts as a bridge between generic filesystem operations and Google Drive-specific functionality.
 */
public interface IGGoogleDriveVirtualFilesystemConsumingService extends
		IGRemoteVirtualFilesystemConsumingService<GGoogleDriveSystem, GGoogleDriveProjectEndpoint, GoogleDriveResourceReference> {

}