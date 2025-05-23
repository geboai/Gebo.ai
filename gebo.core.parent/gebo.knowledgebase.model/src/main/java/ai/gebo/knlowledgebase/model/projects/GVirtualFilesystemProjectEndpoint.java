/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.projects;

import java.util.List;

import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * The GVirtualFilesystemProjectEndpoint class represents a specific type of project endpoint 
 * that deals with virtual filesystem references. It extends the base class GProjectEndpoint.
 */
public class GVirtualFilesystemProjectEndpoint extends GProjectEndpoint {
	// A list of virtual filesystem paths associated with this project endpoint.
	List<VFilesystemReference> paths = null;

	/**
	 * Retrieves the list of virtual filesystem paths associated with this project endpoint.
	 *
	 * @return a list of VFilesystemReference objects representing the paths.
	 */
	public List<VFilesystemReference> getPaths() {
		return paths;
	}

	/**
	 * Sets the list of virtual filesystem paths for this project endpoint.
	 *
	 * @param paths - a list of VFilesystemReference objects to be set as paths.
	 */
	public void setPaths(List<VFilesystemReference> paths) {
		this.paths = paths;
	}
}