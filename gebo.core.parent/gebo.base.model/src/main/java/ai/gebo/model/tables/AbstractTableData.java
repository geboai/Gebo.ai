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
import java.util.stream.Stream;

/**
 * AI generated comments
 * Abstract class representing the fundamental structure for table data.
 */
public abstract class AbstractTableData {
	// List holding metadata about table columns
	private List<TableColumnMetaData> columnsMetaData = new ArrayList<TableColumnMetaData>();

	/**
	 * Constructor for AbstractTableData.
	 */
	public AbstractTableData() {

	}

	/**
	 * Retrieves the column metadata for the table.
	 * 
	 * @return a list of TableColumnMetaData objects.
	 */
	public List<TableColumnMetaData> getColumnsMetaData() {
		return columnsMetaData;
	}

	/**
	 * Sets the column metadata for the table.
	 * 
	 * @param columnsMetaData A list of TableColumnMetaData to be set.
	 */
	public void setColumnsMetaData(List<TableColumnMetaData> columnsMetaData) {
		this.columnsMetaData = columnsMetaData;
	}

	/**
	 * Abstract method that must be implemented to provide a stream of table rows.
	 * 
	 * @return a stream of TableDataRow objects.
	 */
	public abstract Stream<TableDataRow> streamRows();
	
}