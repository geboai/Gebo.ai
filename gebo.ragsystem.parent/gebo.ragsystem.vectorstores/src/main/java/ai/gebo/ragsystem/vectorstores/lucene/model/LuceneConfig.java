/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.lucene.model;

import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * AI generated comments
 * 
 * A configuration class for Lucene vector store settings.
 * This class extends the base vector store configuration and adds Lucene-specific
 * configuration parameters.
 */
public class LuceneConfig extends GBaseVectorStoreConfig {
	/** Maximum size of the document processing queue before backpressure is applied */
	int documentsQueueMax=4000;
	
	/**
	 * Default constructor that initializes a Lucene configuration with default values.
	 */
	public LuceneConfig() {
		
	}
	
	/**
	 * Gets the maximum number of documents that can be queued for processing.
	 * 
	 * @return the maximum queue size for documents
	 */
	public int getDocumentsQueueMax() {
		return documentsQueueMax;
	}
	
	/**
	 * Sets the maximum number of documents that can be queued for processing.
	 * 
	 * @param documentsQueueMax the maximum queue size for documents
	 */
	public void setDocumentsQueueMax(int documentsQueueMax) {
		this.documentsQueueMax = documentsQueueMax;
	}

}