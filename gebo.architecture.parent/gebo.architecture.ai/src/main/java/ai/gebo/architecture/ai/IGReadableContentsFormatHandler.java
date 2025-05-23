/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai;

import java.util.List;

/**
 * Gebo.ai comment agent
 * 
 * Interface for handling readable content formats. Defines methods to retrieve 
 * extensions, content types, and a unique handler ID for specific content formats.
 */
public interface IGReadableContentsFormatHandler {

    /**
     * Retrieves a list of file extensions that the handler can manage.
     * 
     * @return List of strings representing the handled file extensions.
     */
    public List<String> getHandledExtensions();

    /**
     * Retrieves a list of MIME content types that the handler can manage.
     * 
     * @return List of strings representing the handled content types.
     */
    public List<String> getHandledContentTypes();

    /**
     * Retrieves the identifier for the content format handler.
     * 
     * @return A string that uniquely identifies the content format handler.
     */
    public String getContentFormatHandlerId();
}