/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.odformats.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.dom.element.office.OfficeSpreadsheetElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.IGTableDataHandler;
import ai.gebo.system.ingestion.impl.GAbstractConfiguredHandler;

/**
 * AI generated comments
 * 
 * This service handles ingestion of ODS (OpenDocument Spreadsheet) files.
 * It extracts table data from ODS files and processes it using a table renderer.
 */
@Service
public class GOdsIngestionHandlerImpl extends GAbstractConfiguredHandler {
	/** Table renderer component to handle the extracted table data */
	@Autowired
	IGTableDataHandler tableRenderer;

	/**
	 * Constructor for the ODS ingestion handler.
	 * 
	 * @param dao Data access object for ingestion handler configuration
	 */
	public GOdsIngestionHandlerImpl(IGIngestionHandlerConfigDao dao) {
		super(dao);

	}

	/**
	 * Returns the unique code identifier for this handler.
	 * 
	 * @return String code that identifies this handler type
	 */
	@Override
	public String getCode() {

		return "OdsIngestionHandler";
	}

	/**
	 * Reads tables from an ODS file input stream.
	 * 
	 * @param is The input stream containing the ODS file
	 * @return List of extracted table data objects
	 * @throws GeboIngestionException If there is an error reading the spreadsheet
	 */
	private static List<OpenDocumentTableData> readTables(InputStream is) throws GeboIngestionException {
		ArrayList<OpenDocumentTableData> docs = new ArrayList<OpenDocumentTableData>();
		try {
			OdfSpreadsheetDocument odfSpreadSheet = OdfSpreadsheetDocument.loadDocument(is);
			OfficeSpreadsheetElement root = odfSpreadSheet.getContentRoot();
			List<TableTableElement> tables = odfSpreadSheet.getTables();
			for (TableTableElement tableTableElement : tables) {
				OpenDocumentTableData odfTable = new OpenDocumentTableData(tableTableElement);
				docs.add(odfTable);
			}
		} catch (Exception e) {
			throw new GeboIngestionException("Cannot read odf spreadsheet", e);
		}
		return docs;
	}

	/**
	 * Processes the content of an ODS file and converts it into Document objects.
	 * 
	 * @param reference The document reference information
	 * @param is Input stream containing the ODS file data
	 * @param manageMetaInfo Map of metadata about the document
	 * @return Stream of Document objects extracted from the ODS file
	 * @throws GeboIngestionException If there is an error processing the file
	 * @throws IOException If there is an I/O error
	 */
	@Override
	public Stream<Document> handleContent(GDocumentReference reference, InputStream is,
			Map<String, Object> manageMetaInfo) throws GeboIngestionException, IOException {
		Stream<Document> stream = Stream.of();
		try {
			enrichMetaData(reference, manageMetaInfo);
			List<OpenDocumentTableData> tables = readTables(is);
			for (OpenDocumentTableData oftTableData : tables) {
				Stream<Document> thisData = tableRenderer.handleContent(oftTableData, manageMetaInfo);
				stream = Stream.concat(stream, thisData);
			}
		} catch (Exception e) {
			throw new GeboIngestionException("Cannot read odf spreadsheet", e);
		}
		return stream;
	}

	
}