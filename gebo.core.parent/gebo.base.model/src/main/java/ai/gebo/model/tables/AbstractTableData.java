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


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI generated comments
 * Abstract class representing the fundamental structure for table data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractTableData {
	// List holding metadata about table columns
	private List<TableColumnMetaData> columnsMetaData = new ArrayList<TableColumnMetaData>();
	public abstract Stream<TableDataRow> streamRows();
	
}