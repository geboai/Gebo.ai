/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.system.ingestion.model;

import java.util.HashMap;
import java.util.Map;

/**
 * AI generated comments
 * 
 * Represents metadata header information for ingested files. This class stores
 * information about the structure and location of metadata within a file,
 * including block delimiters, starting line, header content, and the type of
 * file being processed.
 */
public class MetaDataHeaderInfos {
	/** The marker or string that identifies the beginning of a metadata block */
	public String startBlock = null;
	/** The marker or string that identifies the end of a metadata block */
	public String endBlock = null;
	/** The line number or marker where metadata begins */
	public String startLine = null;
	/** The content of the metadata header */
	public String metaDataHeader = null;
	/** The type of file being processed for ingestion */
	public IngestionFileType fileType = null;
	public Map<String, Object> customMetaInfos=new HashMap<>();
}