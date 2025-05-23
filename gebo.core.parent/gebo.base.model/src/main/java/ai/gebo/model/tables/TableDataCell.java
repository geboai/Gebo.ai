/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.tables;

/**
 * AI generated comments
 * Represents a cell in a table structure that can hold any type of content.
 */
public class TableDataCell {

    // The content of the cell, which can be of any object type.
    private Object content = null;

    /**
     * Default constructor for TableDataCell.
     * Initializes the cell with no content.
     */
    public TableDataCell() {

    }

    /**
     * Retrieves the content of the cell.
     * @return the current content of the cell
     */
    public Object getContent() {
        return content;
    }

    /**
     * Sets the content of the cell.
     * @param content the new content to be set in the cell
     */
    public void setContent(Object content) {
        this.content = content;
    }

}