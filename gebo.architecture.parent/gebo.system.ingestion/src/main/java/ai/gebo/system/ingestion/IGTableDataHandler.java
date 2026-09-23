/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;

import ai.gebo.model.tables.AbstractTableData;

/**
 * AI generated comments
 * 
 * Interface defining a handler for table data during the ingestion process.
 * This interface is responsible for processing tabular data and converting it
 * into document format that can be used by the AI system.
 */
public interface IGTableDataHandler {
    /**
     * Processes table data and converts it into a stream of documents.
     * 
     * @param tableData The tabular data to be processed
     * @param metaData Additional metadata to be included in the processing
     * @return A stream of Document objects generated from the table data
     */
	public Stream<Document> handleContent(AbstractTableData tableData, Map<String, Object> metaData);
}