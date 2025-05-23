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
 * Gebo.ai comment agent
 * Represents a row in a table, containing a list of {@link TableCell} objects.
 */
public class TableRow {

    // List of TableCell objects that make up this row
    private List<TableCell> cells = new ArrayList<TableCell>();

    /**
     * Retrieves the list of table cells in this row.
     * 
     * @return A list of {@link TableCell} objects.
     */
    public List<TableCell> getCells() {
        return cells;
    }

    /**
     * Sets the list of table cells for this row.
     * 
     * @param cells A list of {@link TableCell} objects to be set for this row.
     */
    public void setCells(List<TableCell> cells) {
        this.cells = cells;
    }
}