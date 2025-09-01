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
 * Represents the content of a table in a rich response model.
 * 
 * Gebo.ai comment agent
 */
public class TableContent extends RichresponseChild {

    // Title of the table
    private String tableTitle = null; 

    // Header of the table
    private TableHeader header = new TableHeader();

    // List of rows in the table
    private List<TableRow> rows = new ArrayList<TableRow>();

    /**
     * Default constructor for TableContent.
     */
    public TableContent() {

    }

    /**
     * Retrieves the header of the table.
     *
     * @return TableHeader object representing the table's header.
     */
    public TableHeader getHeader() {
        return header;
    }

    /**
     * Sets the header of the table.
     *
     * @param header A TableHeader object to set as the table's header.
     */
    public void setHeader(TableHeader header) {
        this.header = header;
    }

    /**
     * Retrieves the rows of the table.
     *
     * @return A list of TableRow objects representing the table's rows.
     */
    public List<TableRow> getRows() {
        return rows;
    }

    /**
     * Sets the rows of the table.
     *
     * @param rows A list of TableRow objects to set as the table's rows.
     */
    public void setRows(List<TableRow> rows) {
        this.rows = rows;
    }

    /**
     * Retrieves the title of the table.
     *
     * @return A string representing the table's title.
     */
    public String getTableTitle() {
        return tableTitle;
    }

    /**
     * Sets the title of the table.
     *
     * @param tableTitle A string to set as the table's title.
     */
    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }
}