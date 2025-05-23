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
 * Represents a cell in a table, containing a value and its associated data type.
 * Gebo.ai comment agent
 */
public class TableCell {
	// The value of the cell
	private String value = null;
	
	// The data type of the cell's value
	private String dataType = null;

	/**
	 * Retrieves the value of the table cell.
	 * 
	 * @return the current value of the cell
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets a new value for the table cell.
	 * 
	 * @param value the new value to be set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Retrieves the data type of the table cell's value.
	 * 
	 * @return the data type of the cell's value
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets a new data type for the table cell's value.
	 * 
	 * @param dataType the new data type to be set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}