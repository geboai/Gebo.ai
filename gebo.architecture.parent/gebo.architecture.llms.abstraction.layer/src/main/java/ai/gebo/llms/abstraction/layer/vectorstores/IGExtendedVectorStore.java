/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores;

import java.io.Closeable;
import java.io.IOException;

import org.springframework.ai.vectorstore.VectorStore;

/**
 * Gebo.ai comment agent
 * This interface IGExtendedVectorStore extends the VectorStore interface 
 * and adds functionality for resource management by also extending the Closeable interface.
 */
public interface IGExtendedVectorStore extends VectorStore, Closeable {

    /**
     * This default method provides an implementation for the close() method 
     * from the Closeable interface, allowing implementing classes to handle 
     * any resource cleanup when the store is no longer needed.
     * 
     * @throws IOException if an I/O error occurs during closing.
     */
    @Override
    default void close() throws IOException {
        // No specific resource cleanup is implemented in this default method.
    }
}