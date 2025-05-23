/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.multilanguage.support.model;

/**
 * Gebo.ai comment agent
 * The LanguageResource interface defines methods for setting and retrieving 
 * the ID and language code associated with a language resource.
 */
public interface LanguageResource {

    /**
     * Sets the ID of the language resource.
     * 
     * @param s the ID to set
     */
    public void setId(String s);

    /**
     * Retrieves the ID of the language resource.
     * 
     * @return the ID of the language resource
     */
    public String getId();

    /**
     * Retrieves the language code of the language resource.
     * 
     * @return the language code of the language resource
     */
    public String getLangCode();

    /**
     * Sets the language code of the language resource.
     * 
     * @param s the language code to set
     */
    public void setLangCode(String s);
}