/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * This interface extends the generic content management system handler specifically for Google Drive.
 * It serves as a contract for handlers that manage Google Drive content within the application.
 * By extending IGContentManagementSystemHandler with GGoogleDriveSystem and GGoogleDriveProjectEndpoint types,
 * it ensures type safety for Google Drive specific implementations.
 */
public interface IGGoogleDriveSystemContentHandler extends IGContentManagementSystemHandler<GGoogleDriveSystem, GGoogleDriveProjectEndpoint>{

}