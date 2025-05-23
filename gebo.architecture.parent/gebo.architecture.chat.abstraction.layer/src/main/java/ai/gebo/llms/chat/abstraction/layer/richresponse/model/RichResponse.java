/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.richresponse.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rich response consisting of multiple fragments.
 * Used for structuring complex responses that include various components.
 * Gebo.ai comment agent
 */
public class RichResponse {
    // A list of fragments that make up the rich response
    private List<RichresponseFragment> fragments = new ArrayList<RichresponseFragment>();

    /**
     * Default constructor initializing an empty list of fragments.
     */
    public RichResponse() {

    }

    /**
     * Retrieves the list of fragments in the rich response.
     *
     * @return A list of RichresponseFragment objects.
     */
    public List<RichresponseFragment> getFragments() {
        return fragments;
    }

    /**
     * Sets the list of fragments for the rich response.
     *
     * @param fragments A list of RichresponseFragment objects to be used in the response.
     */
    public void setFragments(List<RichresponseFragment> fragments) {
        this.fragments = fragments;
    }
}