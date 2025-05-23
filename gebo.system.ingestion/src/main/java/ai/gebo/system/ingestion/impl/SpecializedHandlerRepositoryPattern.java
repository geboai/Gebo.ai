/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.IGSpecializedDocumentReferenceIngestionHandler;

/**
 * AI generated comments
 * 
 * This service manages specialized document reference ingestion handlers.
 * It extends the abstract implementation repository pattern to provide
 * functionality for finding appropriate handlers for specific document references.
 */
@Service
public class SpecializedHandlerRepositoryPattern
		extends GAbstractImplementationsRepositoryPattern<IGSpecializedDocumentReferenceIngestionHandler> {
	
	/**
	 * Constructs a new SpecializedHandlerRepositoryPattern with the given list of
	 * specialized document reference ingestion handlers.
	 * 
	 * @param implementations A list of IGSpecializedDocumentReferenceIngestionHandler implementations
	 *                        which may be null or empty (autowired, not required)
	 */
	public SpecializedHandlerRepositoryPattern(
			@Autowired(required = false) List<IGSpecializedDocumentReferenceIngestionHandler> implementations) {
		super(implementations);
	}

	/**
	 * Gets the code value from a specialized document reference ingestion handler.
	 * 
	 * @param x The handler to get the code from
	 * @return The code that identifies the handler
	 */
	@Override
	public String getCodeValue(IGSpecializedDocumentReferenceIngestionHandler x) {
		return x.getCode();
	}

	/**
	 * Finds an appropriate handler that can manage the given document reference.
	 * 
	 * @param reference The document reference to find a handler for
	 * @return A handler that can manage the document reference, or null if none is found
	 */
	public IGSpecializedDocumentReferenceIngestionHandler findByCanManage(GDocumentReference reference) {
		return this.findImplementation(x -> {
			return x.canManageContent(reference);
		});
	}
}