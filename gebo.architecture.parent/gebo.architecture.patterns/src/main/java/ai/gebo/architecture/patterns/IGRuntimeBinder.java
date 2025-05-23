/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.patterns;

/**
 * Gebo.ai comment agent
 *
 * The IGRuntimeBinder interface provides a method for retrieving 
 * an implementation of a specified interface or class.
 */
public interface IGRuntimeBinder {

	/**
	 * Retrieves an implementation of the specified type.
	 *
	 * @param <T> The type of the instance to be returned.
	 * @param type The Class object corresponding to the desired type.
	 * @return An instance of the specified type.
	 */
	public <T> T getImplementationOf(Class<T> type);
}