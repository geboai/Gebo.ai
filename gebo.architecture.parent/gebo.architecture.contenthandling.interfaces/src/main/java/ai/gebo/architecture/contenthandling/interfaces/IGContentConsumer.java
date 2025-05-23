/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contenthandling.interfaces;

import java.util.function.Consumer;

import ai.gebo.model.base.GBaseVersionableObject;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a content consumer that operates on 
 * {@link GBaseVersionableObject} instances.
 * <p>
 * Extends the {@link Consumer} interface to provide additional functionality
 * specific to content-consuming operations.
 * </p>
 */
public interface IGContentConsumer extends Consumer<GBaseVersionableObject>{
    
    /**
     * Method to be called to indicate that content consumption has ended.
     * This allows for any necessary cleanup operations when the consumer
     * has finished processing the content.
     */
    public void endConsuming();
}