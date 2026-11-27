/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.llms.google_vertex.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.metadata.DefaultUsage;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;

/**
 * Pins the one text per request the gemini-embedding models take: every path the
 * platform embeds through reaches the provider with a single text.
 */
class SingleTextEmbeddingModelTest {

	/** A provider model recording the texts of each request it receives. */
	static class RecordingModel implements EmbeddingModel {
		final List<List<String>> requests = new ArrayList<>();

		@Override
		public EmbeddingResponse call(EmbeddingRequest request) {
			requests.add(List.copyOf(request.getInstructions()));
			final List<Embedding> embeddings = new ArrayList<>();
			for (int i = 0; i < request.getInstructions().size(); i++) {
				embeddings.add(new Embedding(new float[] { request.getInstructions().get(i).length(), 1f }, i));
			}
			return new EmbeddingResponse(embeddings,
					new EmbeddingResponseMetadata("gemini-embedding-001", new DefaultUsage(3, 0, 3)));
		}

		@Override
		public float[] embed(Document document) {
			return call(new EmbeddingRequest(List.of(document.getText()), null)).getResult().getOutput();
		}
	}

	@Test
	void aRequestOfSeveralTextsGoesOneTextPerCallInOrder() {
		final RecordingModel provider = new RecordingModel();
		final EmbeddingResponse response = new SingleTextEmbeddingModel(provider)
				.call(new EmbeddingRequest(List.of("a", "bb", "ccc"), EmbeddingOptions.builder().build()));

		assertEquals(List.of(List.of("a"), List.of("bb"), List.of("ccc")), provider.requests);
		assertEquals(3, response.getResults().size());
		for (int i = 0; i < 3; i++) {
			assertEquals(i, response.getResults().get(i).getIndex());
			assertEquals(i + 1f, response.getResults().get(i).getOutput()[0], "the embedding of text " + i);
		}
		assertEquals(9, response.getMetadata().getUsage().getPromptTokens(), "the usage summed");
		assertEquals("gemini-embedding-001", response.getMetadata().getModel());
	}

	@Test
	void theVectorStoreBatchesADocumentAndAQueryGoOneTextAtATime() {
		final RecordingModel provider = new RecordingModel();
		final SingleTextEmbeddingModel model = new SingleTextEmbeddingModel(provider);

		final List<float[]> embedded = model.embed(List.of(new Document("first"), new Document("second")),
				EmbeddingOptions.builder().build(), new TokenCountBatchingStrategy());
		assertEquals(2, embedded.size());
		model.embed(new Document("a document"));
		model.embed("a query");
		assertTrue(provider.requests.stream().allMatch(texts -> texts.size() == 1), provider.requests.toString());
		assertEquals(4, provider.requests.size());
	}

	@Test
	void onlyTheGeminiEmbeddingModelsAreWrapped() {
		final RecordingModel provider = new RecordingModel();
		assertTrue(SingleTextEmbeddingModel.forModel("gemini-embedding-001", provider) instanceof SingleTextEmbeddingModel);
		assertSame(provider, SingleTextEmbeddingModel.forModel("text-multilingual-embedding-002", provider),
				"five texts per request: as before");
		assertFalse(SingleTextEmbeddingModel.takesOneTextPerRequest(null));
		assertEquals("gemini-embedding-001", GoogleVertexEmbeddingModelConfigurationSupportService.choices.get(0).getCode());
	}
}
