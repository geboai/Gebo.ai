/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.model.tables.AbstractTableData;
import ai.gebo.model.tables.TableColumnMetaData;
import ai.gebo.model.tables.TableDataCell;
import ai.gebo.model.tables.TableDataRow;
import ai.gebo.system.ingestion.IGTableDataHandler;

/**
 * AI generated comments
 * 
 * Implementation of the IGTableDataHandler interface that processes table data.
 * This service converts tabular data into a stream of Document objects for further processing.
 */
@Service
public class GTableDataHandlerImpl implements IGTableDataHandler {

	/**
	 * Default constructor for GTableDataHandlerImpl.
	 */
	public GTableDataHandlerImpl() {

	}

	/**
	 * Handles the table content by converting it into a stream of Document objects.
	 * Each row in the table is transformed into a document where each cell's data
	 * is formatted as "column description: cell value".
	 *
	 * @param tableData The table data to be processed
	 * @param metaData Additional metadata to be included in each document
	 * @return A stream of Document objects representing the table data
	 */
	@Override
	public Stream<Document> handleContent(final AbstractTableData tableData, Map<String, Object> metaData) {
		Stream<TableDataRow> stream = tableData.streamRows();
		return stream.map(row -> {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < tableData.getColumnsMetaData().size(); i++) {
				TableColumnMetaData column = tableData.getColumnsMetaData().get(i);
				String description = column.getColumnDescription();
				// Use column name if description is empty
				if (description == null || description.trim().length() == 0) {
					description = column.getColumnName();
				}
				TableDataCell cell = row.getCells().get(i);
				String cellData = formatCell(cell, column);
				buffer.append(description + ":" + cellData + "\t\n");
			}
			return new Document(buffer.toString(), metaData);
		});
	}

	/**
	 * Formats a table cell's content for inclusion in a document.
	 * 
	 * @param cell The table cell to format
	 * @param column The metadata for the column containing the cell
	 * @return A string representation of the cell's content or "No value" if null
	 */
	private String formatCell(TableDataCell cell, TableColumnMetaData column) {
		return cell.getContent() != null ? cell.getContent().toString() : "No value";
	}

}