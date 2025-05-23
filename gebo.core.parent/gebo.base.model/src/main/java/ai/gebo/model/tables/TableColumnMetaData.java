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
 * Represents the metadata of a table column, providing information about its name,
 * description, data type, and format.
 */
public class TableColumnMetaData {
    
    // The name of the column
	private String columnName = null;
    
    // A brief description of the column
	private String columnDescription = null;
    
    // The data type of the column (e.g., VARCHAR, INTEGER)
	private String columnType = null;
    
    // The format of the column (e.g., date format for date columns)
	private String columnFormat = null;

    /**
     * Retrieves the name of the column.
     * 
     * @return the column name
     */
	public String getColumnName() {
		return columnName;
	}

    /**
     * Sets the name of the column.
     * 
     * @param columnName the column name
     */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

    /**
     * Retrieves the description of the column.
     * 
     * @return the column description
     */
	public String getColumnDescription() {
		return columnDescription;
	}

    /**
     * Sets the description of the column.
     * 
     * @param columnDescription the column description
     */
	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

    /**
     * Retrieves the data type of the column.
     * 
     * @return the column type
     */
	public String getColumnType() {
		return columnType;
	}

    /**
     * Sets the data type of the column.
     * 
     * @param columnType the column type
     */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

    /**
     * Retrieves the format of the column.
     * 
     * @return the column format
     */
	public String getColumnFormat() {
		return columnFormat;
	}

    /**
     * Sets the format of the column.
     * 
     * @param columnFormat the column format
     */
	public void setColumnFormat(String columnFormat) {
		this.columnFormat = columnFormat;
	}
}