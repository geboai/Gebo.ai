/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.core.messages.GDocumentReferencePayload;

/**
 * AI generated comments
 * 
 * Interface for accessing document content in the RAG system.
 * This interface defines functionality for retrieving document content
 * based on document reference information contained in a message envelope.
 */
public interface IGDocumentChunkServiceAccessor {
    /**
     * Accesses document content based on the provided document reference.
     *
     * @param envelope The message envelope containing document reference information
     * @return DocumentAccessResult containing the accessed document content and metadata
     */
	public DocumentAccessResult access(
			GMessageEnvelope<GDocumentReferencePayload> envelope);

}