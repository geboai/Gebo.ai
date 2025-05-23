/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.model;

/**
 * Gebo.ai comment agent
 * Represents configuration settings for an internal Lucene server,
 * particularly focusing on the settings for the save queue.
 */
public class InternalLuceneServerConfig {

    /**
     * Default constructor for InternalLuceneServerConfig.
     * Initializes a new instance of the configuration.
     */
    public InternalLuceneServerConfig() {

    }

    /**
     * The maximum length of the save queue. Defaults to 5000.
     */
    public int saveQueueMaxLength = 5000;

    /**
     * Retrieves the maximum length of the save queue.
     * 
     * @return the current maximum length of the save queue.
     */
    public int getSaveQueueMaxLength() {
        return saveQueueMaxLength;
    }

    /**
     * Sets a new value for the maximum length of the save queue.
     * 
     * @param saveQueueMaxLength the new maximum length to set for the save queue.
     */
    public void setSaveQueueMaxLength(int saveQueueMaxLength) {
        this.saveQueueMaxLength = saveQueueMaxLength;
    }
}