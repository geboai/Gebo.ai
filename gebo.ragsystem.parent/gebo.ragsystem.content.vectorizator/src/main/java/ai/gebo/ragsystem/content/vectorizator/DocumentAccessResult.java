/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator;

import java.util.List;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.model.GUserMessage;

/**
 * AI generated comments
 * 
 * Represents the result of a document access operation in the RAG system.
 * This class encapsulates information about the success of document processing,
 * whether content was empty, ingested fragments, document references, and
 * any negative feedback messages.
 */
public class DocumentAccessResult {
	/** Indicates if the document was successfully processed */
	final boolean successfullyHandled;
	/** Indicates if the document had no content */
	final boolean emptyContent;
	/** List of message envelopes containing document fragments that were ingested */
	final List<GMessageEnvelope<GDocumentMessageFragmentPayload>> ingested;
	/** Message envelope containing document reference information */
	final GMessageEnvelope<GDocumentReferencePayload> docReferenceMessage;
	/** Message to be shown when document access was not successful */
	final GUserMessage negativeMessage;

	/**
	 * Constructs a DocumentAccessResult with all relevant information about the document processing operation.
	 * 
	 * @param successfullyHandled Whether the document was successfully handled
	 * @param emptyContent Whether the document had no content
	 * @param ingested List of message envelopes containing document fragments that were processed
	 * @param docReferenceMessage Message envelope containing document reference information
	 * @param negativeMessage Message to be shown when document access was not successful
	 */
	public DocumentAccessResult(boolean successfullyHandled,boolean emptyContent, List<GMessageEnvelope<GDocumentMessageFragmentPayload>> ingested,
			GMessageEnvelope<GDocumentReferencePayload> docReferenceMessage, GUserMessage negativeMessage) {
		this.successfullyHandled = successfullyHandled;
		this.ingested = ingested;
		this.negativeMessage = negativeMessage;
		this.docReferenceMessage = docReferenceMessage;
		this.emptyContent=emptyContent;
	}

	/**
	 * Returns whether the document was successfully handled.
	 * 
	 * @return true if the document was successfully processed, false otherwise
	 */
	public boolean isSuccessfullyHandled() {
		return successfullyHandled;
	}

	/**
	 * Returns the negative message to be shown if document access failed.
	 * 
	 * @return the negative message, or null if there is none
	 */
	public GUserMessage getNegativeMessage() {
		return negativeMessage;
	}

	/**
	 * Returns the document reference message envelope.
	 * 
	 * @return the document reference message envelope
	 */
	public GMessageEnvelope<GDocumentReferencePayload> getDocReferenceMessage() {
		return docReferenceMessage;
	}

	/**
	 * Returns the list of ingested document fragment messages.
	 * 
	 * @return list of message envelopes containing document fragments
	 */
	public List<GMessageEnvelope<GDocumentMessageFragmentPayload>> getIngested() {
		return ingested;
	}

	/**
	 * Indicates whether the document had no content.
	 * 
	 * @return true if the document was empty, false otherwise
	 */
	public boolean isEmptyContent() {
		return emptyContent;
	}
}