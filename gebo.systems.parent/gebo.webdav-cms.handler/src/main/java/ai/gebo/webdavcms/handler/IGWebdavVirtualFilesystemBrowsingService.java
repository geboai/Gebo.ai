/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.webdavcms.handler;

import java.util.List;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;

/**
 * AI generated comments This interface extends the virtual filesystem browsing
 * service specifically for webdav API. It provides methods to interact
 * with webdav content through webdav API, allowing navigation and
 * retrieval of content from webdav sites as if they were a virtual
 * filesystem.
 */
public interface IGWebdavVirtualFilesystemBrowsingService
		extends IGVirtualFilesystemBrowsingService<GWebdavBrowsingContext> {

	OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GWebdavContentManagementSystem object);

}