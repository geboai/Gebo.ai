/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services.impl;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.lucene.model.InternalLuceneServerConfig;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServer;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServerFactory;
import ai.gebo.architecture.multithreading.IGeboThreadManager;

/**
 * Factory implementation for creating instances of IGInternalLuceneServer
 * Gebo.ai comment agent
 */
@Component
@Scope("singleton")
public class GInternalLuceneServerFactoryImpl implements IGInternalLuceneServerFactory {
    // Autowired thread manager for handling server tasks
    @Autowired
    IGeboThreadManager taskExecutor;

    /**
     * Creates an instance of IGInternalLuceneServer.
     * 
     * @param path The path where the server operates.
     * @param serverConfig Configuration for the Lucene server.
     * @return An instance of IGInternalLuceneServer.
     * @throws IOException if there is an error during server creation.
     */
    @Override
    public IGInternalLuceneServer create(Path path, InternalLuceneServerConfig serverConfig) throws IOException {
        // Instantiates a new Lucene server with the given path and configuration
        GInternalLuceneServerImpl server = new GInternalLuceneServerImpl(path, serverConfig);
        // Executes the server instance using the thread manager
        taskExecutor.run(server);
        // Returns the created server instance
        return server;
    }
}