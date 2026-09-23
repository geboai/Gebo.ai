/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.msformats.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import ai.gebo.model.tables.AbstractTableData;
import ai.gebo.model.tables.TableColumnMetaData;
import ai.gebo.model.tables.TableDataCell;
import ai.gebo.model.tables.TableDataRow;

/**
 * Implementation of AbstractTableData for Microsoft Excel sheets.
 * This class handles the extraction and management of data from Excel sheets.
 * AI generated comments
 */
public class MsExcelTableData extends AbstractTableData {
	/** The Excel sheet being processed */
	private Sheet sheet = null;
	/** String representation for boolean false values */
	public static final String BOOLEAN_FALSE_CELL_RENDER = "false";
	/** String representation for boolean true values */
	public static final String BOOLEAN_TRUE_CELL_RENDER = "true";
	/** String representation for empty or null cell values */
	public static final String EMPTY_OR_NULL_CELL_RENDER = "NO VALUE";

	/**
	 * Inner class that enables streaming of rows from the Excel sheet.
	 * Keeps track of the current row position and handles data extraction.
	 */
	protected class StreamSheetReader {
		/** Current row index, starts at 1 (after header row) */
		int actualRowIndex = 1;

		/**
		 * Extracts data from the current row and advances the cursor.
		 * 
		 * @return TableDataRow object containing the cell data from current row
		 */
		protected TableDataRow getActualRow() {
			Row row = sheet.getRow(actualRowIndex);
			TableDataRow actualRow = new TableDataRow();
			int idx = 0;
			for (TableColumnMetaData column : getColumnsMetaData()) {
				TableDataCell tdcell = new TableDataCell();
				actualRow.getCells().add(tdcell);
				if (row != null) {
					Cell cell = row.getCell(idx);
					if (cell != null) {
						tdcell.setContent(readCell(cell));
					}
				} else {
					// Cell is null, content remains empty
				}
				idx++;
			}
			actualRowIndex++;
			return actualRow;
		}

		/**
		 * Checks if there is another row to process.
		 * 
		 * @return true if another row exists, false otherwise
		 */
		protected boolean existentRow() {
			Row row = sheet.getRow(actualRowIndex);
			return row != null;
		}
	}

	/**
	 * Constructor that initializes the Excel table data from a sheet.
	 * Extracts column metadata from the first row (header).
	 * 
	 * @param sheet The Excel sheet to process
	 */
	public MsExcelTableData(Sheet sheet) {
		this.sheet = sheet;
		Row columnsRow = sheet.getRow(0);
		if (columnsRow != null) {
			setColumnsMetaData(extractColumnsMetaData(columnsRow));
		}
	}

	/**
	 * Creates a stream of table data rows from the Excel sheet.
	 * 
	 * @return A Stream of TableDataRow objects
	 */
	@Override
	public Stream<TableDataRow> streamRows() {
		StreamSheetReader sheetGeneration = new StreamSheetReader();
		return Stream.iterate(sheetGeneration.getActualRow(), x -> {
			return sheetGeneration.existentRow();
		}, x -> {
			return sheetGeneration.getActualRow();
		});
	}

	/**
	 * Extracts column metadata from the header row.
	 * 
	 * @param columnsRow The header row containing column names
	 * @return List of TableColumnMetaData objects
	 */
	protected static List<TableColumnMetaData> extractColumnsMetaData(Row columnsRow) {
		final List<TableColumnMetaData> columns = new ArrayList<TableColumnMetaData>();
		columnsRow.forEach(cell -> {
			Object readCell = readCell(cell);
			TableColumnMetaData meta = new TableColumnMetaData();
			meta.setColumnDescription(readCell.toString());
			meta.setColumnName(readCell.toString());
			columns.add(meta);
		});
		return columns;
	}

	/**
	 * Reads a cell's value based on its cell type.
	 * 
	 * @param cell The Excel cell to read
	 * @return The cell's value as an Object
	 */
	protected static Object readCell(Cell cell) {
		Object value = EMPTY_OR_NULL_CELL_RENDER;
		CellType cellType = cell.getCellType();

		if (cellType != null) {
			try {
				switch (cellType) {
				case _NONE: {
				}
					;
					break;
				case BLANK: {
					value = EMPTY_OR_NULL_CELL_RENDER;
				}
					;
					break;
				case BOOLEAN: {
					value = cell.getBooleanCellValue() ? BOOLEAN_TRUE_CELL_RENDER : BOOLEAN_FALSE_CELL_RENDER;
				}
					;
					break;
				case FORMULA: {
					value = cell.getNumericCellValue();
				}
					;
					break;
				case NUMERIC: {
					value = cell.getNumericCellValue();
				}
					;
					break;
				case STRING: {
					value = cell.getStringCellValue();
				}
					;
					break;
				}
			} catch (Throwable th) {
				// Silently handle any exceptions during cell reading
			}
		}
		return value;
	}
}