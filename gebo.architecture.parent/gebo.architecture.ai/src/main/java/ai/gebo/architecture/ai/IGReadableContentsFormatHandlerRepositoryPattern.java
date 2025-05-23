/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 *
 * Interface representing a repository pattern for handling readable content format handlers.
 * Extends the IGImplementationsRepositoryPattern to provide default methods specific to handling content formats.
 */
public interface IGReadableContentsFormatHandlerRepositoryPattern
		extends IGImplementationsRepositoryPattern<IGReadableContentsFormatHandler> {
	
	/**
	 * Returns the code value for a given readable contents format handler by retrieving its ID.
	 *
	 * @param x the IGReadableContentsFormatHandler instance
	 * @return the content format handler ID
	 */
	@Override
	default String getCodeValue(IGReadableContentsFormatHandler x) {
		return x.getContentFormatHandlerId();
	}

	/**
	 * Retrieves a list of file extensions that are handled by the format handlers.
	 *
	 * @return a list of handled file extensions
	 */
	public default List<String> getHandledExtensions() {
		List<String> ext = new ArrayList<String>();
		// Collect handled extensions from all implementations
		for (IGReadableContentsFormatHandler impl : getImplementations()) {
			ext.addAll(impl.getHandledExtensions());
		}
		return ext;
	}

	/**
	 * Retrieves a list of content types that are handled by the format handlers.
	 *
	 * @return a list of handled content types
	 */
	public default List<String> getHandledContentTypes() {
		List<String> ext = new ArrayList<String>();
		// Collect handled content types from all implementations
		for (IGReadableContentsFormatHandler impl : getImplementations()) {
			ext.addAll(impl.getHandledContentTypes());
		}
		return ext;
	}
}