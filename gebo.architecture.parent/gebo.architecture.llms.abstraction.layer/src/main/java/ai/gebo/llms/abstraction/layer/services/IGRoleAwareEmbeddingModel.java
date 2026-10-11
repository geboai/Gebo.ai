/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;

/**
 * An embedding model embedding a search query differently from the documents it is
 * searched in: the asymmetric retrieval models (NVIDIA's embedqa and nemotron embed
 * take an input_type, query or passage, with every request). The vector stores tell the
 * two apart by the method they call - a query through {@code embed(String)}, documents
 * through {@code embed(List<Document>, ...)} - and the usage recording wrapper they
 * call turns that into the request this model is called with.
 */
public interface IGRoleAwareEmbeddingModel extends EmbeddingModel {

	/** The request embedding the texts as search queries. */
	public EmbeddingRequest forQuery(EmbeddingRequest request);

	/** The request embedding the texts as documents to store and search in. */
	public EmbeddingRequest forDocuments(EmbeddingRequest request);
}
