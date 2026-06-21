/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI generated comments
 * Represents the metadata of a table column, providing information about its name,
 * description, data type, and format.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableColumnMetaData {
    
    // The name of the column
	private String columnName = null;
    
    // A brief description of the column
	private String columnDescription = null;
    
    // The data type of the column (e.g., VARCHAR, INTEGER)
	private String columnType = null;
    
    // The format of the column (e.g., date format for date columns)
	private String columnFormat = null;

 
}