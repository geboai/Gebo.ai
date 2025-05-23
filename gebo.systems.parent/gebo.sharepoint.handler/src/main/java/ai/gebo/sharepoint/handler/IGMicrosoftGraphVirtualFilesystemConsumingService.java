/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler;

import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphResourceReference;
import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemConsumingService;

/**
 * AI generated comments
 * 
 * This interface extends the remote virtual filesystem consuming service specifically for Microsoft Graph API.
 * It acts as a specialized service for interacting with SharePoint content management systems and project endpoints
 * using Microsoft Graph resource references as the reference type.
 * 
 * The interface doesn't define additional methods beyond what's inherited from IGRemoteVirtualFilesystemConsumingService,
 * but provides type safety and specification for Microsoft Graph integration.
 */
public interface IGMicrosoftGraphVirtualFilesystemConsumingService extends
		IGRemoteVirtualFilesystemConsumingService<GSharepointContentManagementSystem, GSharepointProjectEndpoint, MicrosoftGraphResourceReference> {

}