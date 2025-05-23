/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.odformats.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableColumnsElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextParagraph;
import org.odftoolkit.odfdom.pkg.OdfElement;

import ai.gebo.model.tables.AbstractTableData;
import ai.gebo.model.tables.TableColumnMetaData;
import ai.gebo.model.tables.TableDataCell;
import ai.gebo.model.tables.TableDataRow;

/**
 * AI generated comments
 * 
 * Implementation of AbstractTableData for handling OpenDocument format tables.
 * This class extracts and manages tabular data from OpenDocument formatted files.
 */
class OpenDocumentTableData extends AbstractTableData {
	/** List to store all table rows extracted from the document */
	private List<TableDataRow> rows = new ArrayList<TableDataRow>();

	/**
	 * Constructor that parses an OpenDocument table element into structured data.
	 * Extracts column metadata from the first row and parses all subsequent rows into data rows.
	 * 
	 * @param tableElement The ODF table element to parse
	 */
	OpenDocumentTableData(TableTableElement tableElement) {
		TableTableColumnsElement header = OdfElement.findFirstChildNode(TableTableColumnsElement.class,
				tableElement);
		TableTableRowElement tableRow = OdfElement.findFirstChildNode(TableTableRowElement.class, tableElement);
		if (tableRow != null) {
			setColumnsMetaData(getTableColumns(tableRow));
			while ((tableRow = OdfElement.findNextChildNode(TableTableRowElement.class, tableRow)) != null) {
				rows.add(readRow(tableRow));
			}
		}

	}

	/**
	 * Reads a table row element and converts it to a TableDataRow object.
	 * Extracts text from each cell in the row.
	 * 
	 * @param tableRow The table row element to process
	 * @return A TableDataRow containing the cell data from the row
	 */
	private TableDataRow readRow(TableTableRowElement tableRow) {
		TableDataRow dataRow = new TableDataRow();
		TableTableCellElement element = OdfElement.findFirstChildNode(TableTableCellElement.class, tableRow);
		if (element != null) {
			do {
				TableDataCell columnMeta = new TableDataCell();
				columnMeta.setContent(extractText(element));

				dataRow.getCells().add(columnMeta);
			} while ((element = OdfElement.findNextChildNode(TableTableCellElement.class, element)) != null);
		}
		return dataRow;
	}

	/**
	 * Extracts text content from an ODF stylable element by concatenating all paragraphs.
	 * 
	 * @param element The ODF element to extract text from
	 * @return String containing the element's text content
	 */
	private static String extractText(OdfStylableElement element) {
		StringBuffer sb = new StringBuffer();
		OdfTextParagraph paragraph = OdfElement.findFirstChildNode(OdfTextParagraph.class, element);
		if (paragraph != null) {
			do {
				sb.append(paragraph.getTextContent());
			} while ((paragraph = OdfElement.findNextChildNode(OdfTextParagraph.class, paragraph)) != null);
		}
		return sb.toString();
	}

	/**
	 * Extracts and creates column metadata from the first row of the table.
	 * Uses the cell content as column name and cell type as column type.
	 * 
	 * @param tableRow The first row of the table containing column headers
	 * @return List of TableColumnMetaData objects describing each column
	 */
	private static List<TableColumnMetaData> getTableColumns(TableTableRowElement tableRow) {
		List<TableColumnMetaData> columns = new ArrayList<TableColumnMetaData>();
		TableTableCellElement element = OdfElement.findFirstChildNode(TableTableCellElement.class, tableRow);
		if (element != null) {
			do {
				TableColumnMetaData columnMeta = new TableColumnMetaData();
				columnMeta.setColumnName(extractText(element));
				columnMeta.setColumnType(element.getTypeName());
				columns.add(columnMeta);
			} while ((element = OdfElement.findNextChildNode(TableTableCellElement.class, element)) != null);
		}
		return columns;
	}

	/**
	 * Provides a stream of table rows for processing the table data.
	 * 
	 * @return A stream of TableDataRow objects
	 */
	@Override
	public Stream<TableDataRow> streamRows() {
		return rows.stream();
	}

}