/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.ai.document.Document;

/**
 * AI generated comments
 * Interface for document hashing services that calculate hash values for collections of documents.
 * This service is used to generate unique identifiers for document sets which can be used for
 * caching, comparison, or verification purposes.
 */
public interface IGDocumentsHashingService {
    /**
     * Recalculates a hash value for a list of documents.
     * 
     * @param content The list of Document objects to hash
     * @return A string representation of the calculated hash
     * @throws IOException If there is an issue reading the document content
     * @throws NoSuchAlgorithmException If the requested cryptographic algorithm is not available
     */
	public String recalculateHash(List<Document> content) throws IOException, NoSuchAlgorithmException;
	
}