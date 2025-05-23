/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler;

import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * This class represents a file system share reference in the Gebo AI system.
 * It extends the GBaseObject base class and provides functionality to store and
 * manage a virtual filesystem reference along with its MongoDB configuration status.
 */
public class GFileSystemShareReference extends GBaseObject {
	/** Flag indicating whether MongoDB is configured for this reference */
	private Boolean mongoConfigured = false;
	
	/** The virtual filesystem reference that cannot be null */
	@NotNull
	private VFilesystemReference reference = null;

	/**
	 * Returns the MongoDB configuration status
	 * 
	 * @return Boolean indicating if MongoDB is configured
	 */
	public Boolean getMongoConfigured() {
		return mongoConfigured;
	}

	/**
	 * Sets the MongoDB configuration status
	 * 
	 * @param fileConfigured Boolean value to set the configuration status
	 */
	public void setMongoConfigured(Boolean fileConfigured) {
		this.mongoConfigured = fileConfigured;
	}

	/**
	 * Returns the virtual filesystem reference
	 * 
	 * @return The VFilesystemReference object
	 */
	public VFilesystemReference getReference() {
		return reference;
	}

	/**
	 * Sets the virtual filesystem reference
	 * 
	 * @param reference The VFilesystemReference to set
	 */
	public void setReference(VFilesystemReference reference) {
		this.reference = reference;
	}

}