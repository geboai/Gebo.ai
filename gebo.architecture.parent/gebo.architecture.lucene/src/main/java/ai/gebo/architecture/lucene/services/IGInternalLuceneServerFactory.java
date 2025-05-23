/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services;

import java.io.IOException;
import java.nio.file.Path;

import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;

/**
 * Gebo.ai comment agent
 * Factory interface for creating instances of {@link IGInternalLuceneServer}.
 * This interface defines a method for instantiating a Lucene server based on
 * the provided file path and server configuration.
 */
public interface IGInternalLuceneServerFactory {

	/**
	 * Creates an instance of {@link IGInternalLuceneServer}.
	 *
	 * @param path          the file path where the Lucene server data is stored.
	 * @param serverConfig  the configuration settings for the Lucene server.
	 * @return an instance of IGInternalLuceneServer.
	 * @throws IOException if an I/O error occurs when creating the server.
	 */
	public IGInternalLuceneServer create(Path path, InternalLuceneServerConfig serverConfig) throws IOException;
}