/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.system.ingestion.msformats.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import ai.gebo.model.tables.TableColumnMetaData;
import ai.gebo.model.tables.TableDataRow;

/**
 * Each value of a sheet is read under the name of its own column, also when the
 * header row has missing or empty cells.
 */
public class MsExcelTableDataTest {

	private static List<String> columnNames(MsExcelTableData table) {
		return table.getColumnsMetaData().stream().map(TableColumnMetaData::getColumnName).toList();
	}

	private static List<Object> cells(TableDataRow row) {
		return row.getCells().stream().map(x -> x.getContent()).toList();
	}

	@Test
	public void testHeaderWithoutItsFirstCellKeepsTheValuesUnderTheirColumns() throws Exception {
		// the layout of a sheet whose first column holds row numbers under an empty A1
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();
			Row header = sheet.createRow(0);
			header.createCell(1).setCellValue("First Name");
			header.createCell(2).setCellValue("Country");
			Row first = sheet.createRow(1);
			first.createCell(0).setCellValue(1);
			first.createCell(1).setCellValue("Dulce");
			first.createCell(2).setCellValue("United States");

			MsExcelTableData table = new MsExcelTableData(sheet);

			assertEquals(List.of("Column A", "First Name", "Country"), columnNames(table));
			List<TableDataRow> rows = table.streamRows().toList();
			assertEquals(1, rows.size());
			assertEquals(List.of(1.0d, "Dulce", "United States"), cells(rows.get(0)));
		}
	}

	@Test
	public void testEmptyHeaderCellsInTheMiddleAreNamedAfterTheirColumn() throws Exception {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Id");
			header.createCell(1).setBlank();
			header.createCell(3).setCellValue("Age");
			Row first = sheet.createRow(1);
			first.createCell(0).setCellValue(7);
			first.createCell(1).setCellValue("x");
			first.createCell(2).setCellValue("y");
			first.createCell(3).setCellValue(56);

			MsExcelTableData table = new MsExcelTableData(sheet);

			assertEquals(List.of("Id", "Column B", "Column C", "Age"), columnNames(table));
			assertEquals(List.of(7.0d, "x", "y", 56.0d), cells(table.streamRows().toList().get(0)));
		}
	}

	@Test
	public void testFullHeaderIsReadAsBefore() throws Exception {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Name");
			header.createCell(1).setCellValue("Age");
			Row first = sheet.createRow(1);
			first.createCell(0).setCellValue("Mara");
			first.createCell(1).setCellValue(25);
			Row second = sheet.createRow(2);
			second.createCell(0).setCellValue("Etta");
			second.createCell(1).setCellValue(56);
			Row third = sheet.createRow(3);
			third.createCell(0).setCellValue("Fallon");
			third.createCell(1).setCellValue(28);

			MsExcelTableData table = new MsExcelTableData(sheet);

			assertEquals(List.of("Name", "Age"), columnNames(table));
			List<TableDataRow> rows = table.streamRows().toList();
			assertEquals(3, rows.size(), "every row is read, the last one included");
			assertEquals(List.of("Mara", 25.0d), cells(rows.get(0)));
			assertEquals(List.of("Fallon", 28.0d), cells(rows.get(2)));
		}
	}

	@Test
	public void testReadingStopsAtTheFirstMissingRow() throws Exception {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();
			sheet.createRow(0).createCell(0).setCellValue("Name");
			sheet.createRow(1).createCell(0).setCellValue("Mara");
			// row 2 missing
			sheet.createRow(3).createCell(0).setCellValue("Fallon");

			List<TableDataRow> rows = new MsExcelTableData(sheet).streamRows().toList();

			assertEquals(1, rows.size());
			assertEquals(List.of("Mara"), cells(rows.get(0)));
		}
	}
}
