/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.msformats.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.model.tables.AbstractTableData;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGTableDataHandler;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * Handles the ingestion of Microsoft Excel documents into the system.
 * Extracts data from Excel sheets and processes them using the table handler.
 */
@Service
public class MsExcellIngestionHandler extends GAbstractConfiguredHandler {
	/**
	 * Handler for processing table data after extraction from Excel files
	 */
	@Autowired
	IGTableDataHandler tableHandler;

	/**
	 * Constructs a new Excel ingestion handler with the specified configuration DAO
	 * 
	 * @param dao The configuration data access object for ingestion handlers
	 */
	public MsExcellIngestionHandler(IGIngestionHandlerConfigDao dao) {
		super(dao);
	}

	/**
	 * Returns the unique code identifier for this handler
	 * 
	 * @return The handler's code identifier
	 */
	@Override
	public String getCode() {
		return "MsExcellIngestionHandler";
	}

	/**
	 * Processes Excel content from an input stream and converts it to documents
	 * 
	 * @param reference The document reference for the Excel file
	 * @param is The input stream containing the Excel data
	 * @param manageMetaInfo Additional metadata information
	 * @return A stream of processed documents
	 * @throws GeboIngestionException If there's an error during ingestion
	 * @throws IOException If there's an error reading the input stream
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetaInfo) throws GeboIngestionException, IOException {
		try {
			List<AbstractTableData> tableDatas = null;
			enrichMetaData(reference, manageMetaInfo);
			tableDatas = readExcelTables(reference, is);

			Stream<Document> docstream = Stream.of();
			for (AbstractTableData abstractTableData : tableDatas) {
				docstream = Stream.concat(docstream, tableHandler.handleContent(abstractTableData, manageMetaInfo));
			}
			return docstream;
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
				// Silently ignore close exceptions
			}
		}
	}

	/**
	 * Indicates whether this handler supports automatic tokenization
	 * 
	 * @return true as Excel content can be automatically tokenized
	 */
	@Override
	public boolean isAutoTokenizeCapable() {
		return true;
	}

	/**
	 * Indicates whether the content handled by this handler can be hashed
	 * 
	 * @return false as Excel content is not hashable in the current implementation
	 */
	@Override
	public boolean isHashbleContent() {
		return false;
	}

	/**
	 * Reads Excel workbook and extracts table data from each sheet
	 * 
	 * @param reference The document reference for the Excel file
	 * @param is The input stream containing the Excel data
	 * @return A list of table data objects representing each sheet
	 * @throws EncryptedDocumentException If the document is encrypted
	 * @throws IOException If there's an error reading the input stream
	 */
	private List<AbstractTableData> readExcelTables(GDocumentReference reference, InputStream is)
			throws EncryptedDocumentException, IOException {
		List<AbstractTableData> tables = new ArrayList<AbstractTableData>();
		BufferedInputStream bis = new BufferedInputStream(is);
		try {
			// Create workbook from input stream
			Workbook workbook = WorkbookFactory.create(bis);
			int nsheets = workbook.getNumberOfSheets();
			// Process each sheet in the workbook
			for (int i = 0; i < nsheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				tables.add(new MsExcelTableData(sheet));
			}
		} finally {
			try {
				is.close();
			} catch (Throwable th) {
				// Silently ignore close exceptions
			}
		}
		return tables;
	}
}