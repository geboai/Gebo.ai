/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions.model;

/**
 * Gebo.ai comment agent
 * 
 * The VoidObject class is a simple data holder class that encapsulates a 
 * single field, queryText, along with its getter and setter methods.
 */
public class VoidObject {

    /** 
     * A string field representing a query text. Initialized to null.
     */
    public String queryText = null;

    /**
     * Retrieves the value of queryText.
     *
     * @return the current value of the queryText field.
     */
    public String getQueryText() {
        return queryText;
    }

    /**
     * Sets the value of queryText.
     *
     * @param queryText a new value for the queryText field.
     */
    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }
}