/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl.model;

/**
 * Represents a custom position within Google Drive structure.
 * This class identifies a location in Google Drive by its ID and type.
 * AI generated comments
 */
public class GoogleDriveCustomPosition {
	/**
	 * Enumeration of possible Google Drive item types that can be positioned.
	 */
	public static enum GoogleDriveType {
		/** Represents the top-level Drive */
		DRIVE, 
		/** Represents a folder within Drive */
		FOLDER, 
		/** Represents a file or resource within Drive */
		RESOURCE
	}

	/** The unique identifier of the Google Drive item */
	public String id = null;
	
	/** The type of the Google Drive item (DRIVE, FOLDER, or RESOURCE) */
	public GoogleDriveType type = null;

}