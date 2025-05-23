/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.richresponse.model;

/**
 * Gebo.ai comment agent
 *
 * Represents a text object containing HTML code, part of a rich response structure.
 * This class extends from RichresponseChild.
 */
public class RichHtmlText extends RichresponseChild {

    // Holds the HTML code as a String, initialized to null
    private String htmlCode = null;

    /**
     * Default constructor for RichHtmlText.
     */
    public RichHtmlText() {
        // No initialization for default constructor
    }

    /**
     * Retrieves the HTML code stored in this object.
     *
     * @return the HTML code as a String.
     */
    public String getHtmlCode() {
        return htmlCode;
    }

    /**
     * Sets the HTML code for this object.
     *
     * @param htmlCode the HTML code to be stored, as a String.
     */
    public void setHtmlCode(String htmlCode) {
        this.htmlCode = htmlCode;
    }

}