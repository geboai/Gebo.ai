/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import ai.gebo.model.annotations.SchedulableObject;
import ai.gebo.model.virtualfs.VFilesystemReference;

/**
 * AI generated comments
 * 
 * This class represents a project endpoint for filesystem content sharing.
 * It extends GProjectEndpoint to handle shared filesystem contents and is stored in MongoDB.
 * The class maintains a path of virtual filesystem references which define the location
 * within the shared filesystem.
 */
@Document
@EntityDescription(description = "Shared file system contents", entityCategory = GProjectEndpoint.class)
@SchedulableObject
public class GFilesystemProjectEndpoint extends GProjectEndpoint {

	/**
	 * List of virtual filesystem references that define the path to the content
	 * in the shared filesystem.
	 */
	private List<VFilesystemReference> path = null;

	/**
	 * Default constructor for the GFilesystemProjectEndpoint.
	 */
	public GFilesystemProjectEndpoint() {

	}

	/**
	 * Gets the path of virtual filesystem references.
	 * 
	 * @return the list of virtual filesystem references that make up the path
	 */
	public List<VFilesystemReference> getPath() {
		return path;
	}

	/**
	 * Sets the path of virtual filesystem references.
	 * 
	 * @param path the list of virtual filesystem references to set
	 */
	public void setPath(List<VFilesystemReference> path) {
		this.path = path;
	}

}