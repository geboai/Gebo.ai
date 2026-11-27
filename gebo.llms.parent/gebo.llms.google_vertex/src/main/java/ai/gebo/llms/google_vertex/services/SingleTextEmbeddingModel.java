/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.google_vertex.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;

/**
 * An embedding model sending one text per request to the Vertex embedding model it
 * wraps: "For gemini-embedding-001, each request can only include a single input text"
 * (docs.cloud.google.com/vertex-ai/generative-ai/docs/embeddings/get-text-embeddings,
 * 2026-11-27), while Spring AI's {@code VertexAiTextEmbeddingModel} puts every text of a
 * request in one predict call. A request of several texts is made as one call per text,
 * the embeddings answered in the request's order and the token usage summed. Every path
 * the platform embeds through (the vector store's batches, a document, a search query,
 * the dimensions probe) reaches the provider through {@link #call(EmbeddingRequest)}.
 */
public class SingleTextEmbeddingModel implements EmbeddingModel {
	/** The prefix of the Vertex models taking one text per request. */
	static final String SINGLE_TEXT_MODELS_PREFIX = "gemini-embedding";

	private final EmbeddingModel delegate;

	public SingleTextEmbeddingModel(EmbeddingModel delegate) {
		this.delegate = delegate;
	}

	/** Whether the Vertex model takes one text per request. */
	static boolean takesOneTextPerRequest(String modelCode) {
		return modelCode != null && modelCode.startsWith(SINGLE_TEXT_MODELS_PREFIX);
	}

	/** The model as the requests of the model code need it: wrapped when it takes one text per request. */
	static EmbeddingModel forModel(String modelCode, EmbeddingModel model) {
		return takesOneTextPerRequest(modelCode) ? new SingleTextEmbeddingModel(model) : model;
	}

	@Override
	public EmbeddingResponse call(EmbeddingRequest request) {
		final List<String> texts = request.getInstructions();
		if (texts == null || texts.size() <= 1) {
			return delegate.call(request);
		}
		final List<Embedding> embeddings = new ArrayList<>(texts.size());
		String model = null;
		Integer promptTokens = null;
		for (int i = 0; i < texts.size(); i++) {
			final EmbeddingResponse one = delegate.call(new EmbeddingRequest(List.of(texts.get(i)), request.getOptions()));
			final Embedding embedding = one.getResult();
			embeddings.add(new Embedding(embedding != null ? embedding.getOutput() : new float[0], i,
					embedding != null ? embedding.getMetadata() : null));
			final EmbeddingResponseMetadata metadata = one.getMetadata();
			if (metadata != null) {
				if (model == null)
					model = metadata.getModel();
				final Usage usage = metadata.getUsage();
				if (usage != null && usage.getPromptTokens() != null) {
					promptTokens = (promptTokens != null ? promptTokens : 0) + usage.getPromptTokens();
				}
			}
		}
		final Usage usage = promptTokens != null ? new DefaultUsage(promptTokens, 0, promptTokens) : null;
		return new EmbeddingResponse(embeddings,
				usage != null ? new EmbeddingResponseMetadata(model, usage) : new EmbeddingResponseMetadata());
	}

	@Override
	public float[] embed(Document document) {
		return embed(getEmbeddingContent(document));
	}
}
