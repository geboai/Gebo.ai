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
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;

/**
 * AI generated comments
 * 
 * This interface defines the contract for components that handle tokenization
 * and embedding operations for document fragments in a RAG (Retrieval-Augmented
 * Generation) system. It provides methods to process message fragments using
 * configurable embedding models and to report failures in the ingestion
 * process.
 */
public interface IGEmbedder {

	/**
	 * Processes a list of document message their chunked content and generating
	 * embeddings using the provided embedding models.
	 * 
	 * @param embeddingModels The list of embedding models to use for generating
	 *                        embeddings
	 * @param messagesList    The list of message envelopes containing document
	 *                        fragments to process
	 */
	public void embed(List<IGConfigurableEmbeddingModel> embeddingModels,
			List<GMessageEnvelope<GDocumentMessageFragmentPayload>> messagesList);

	/**
	 * Reports documents that failed during the ingestion process. This method
	 * allows the system to handle and track documents that could not be properly
	 * processed.
	 * 
	 * @param impossibleToIngest List of document access results that failed
	 *                           ingestion
	 */
	public void notifyFailingIngestion(List<DocumentAccessResult> impossibleToIngest);
}