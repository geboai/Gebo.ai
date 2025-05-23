/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.tables;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a row in a table, containing a list of cells.
 * Provides methods to access and modify the cells in the row.
 * 
 * @see TableDataCell
 * 
 * AI generated comments
 */
public class TableDataRow {

    // A list of cells in the row, initialized as an empty list.
    private List<TableDataCell> cells = new ArrayList<TableDataCell>();

    /**
     * Default constructor for creating an empty TableDataRow.
     */
    public TableDataRow() {

    }

    /**
     * Retrieves the list of cells in the row.
     *
     * @return a list of TableDataCell objects.
     */
    public List<TableDataCell> getCells() {
        return cells;
    }

    /**
     * Sets the list of cells for the row.
     *
     * @param cells a list of TableDataCell objects to be set in the row.
     */
    public void setCells(List<TableDataCell> cells) {
        this.cells = cells;
    }

}