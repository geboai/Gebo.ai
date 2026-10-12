/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;

/**
 * Pins the input_type the asymmetric NVIDIA embedding models need: "query" for a search
 * query, "passage" for the documents stored, the provider model's configured options
 * kept on every request, and the dimensions learnt with a query rather than with the
 * provider model's own probe, which would send no input_type.
 */
class InputTypeEmbeddingModelTest {

	private final List<EmbeddingRequest> sent = new ArrayList<>();

	private OpenAiEmbeddingModel provider() {
		OpenAiEmbeddingModel provider = mock(OpenAiEmbeddingModel.class);
		when(provider.getOptions()).thenReturn(OpenAiEmbeddingOptions.builder().model("nvidia/nemotron-3-embed-1b")
				.baseUrl("https://integrate.api.nvidia.com/v1").timeout(Duration.ofSeconds(42)).build());
		when(provider.call(any(EmbeddingRequest.class))).thenAnswer(call -> {
			EmbeddingRequest request = call.getArgument(0);
			sent.add(request);
			List<Embedding> embeddings = new ArrayList<>();
			for (int i = 0; i < request.getInstructions().size(); i++) {
				embeddings.add(new Embedding(new float[] { 1f, 2f, 3f }, i));
			}
			return new EmbeddingResponse(embeddings);
		});
		return provider;
	}

	private static OpenAiEmbeddingOptions optionsOf(EmbeddingRequest request) {
		return (OpenAiEmbeddingOptions) request.getOptions();
	}

	private InputTypeEmbeddingModel model(OpenAiEmbeddingModel provider) {
		return new InputTypeEmbeddingModel(provider, "query", "passage", MetadataMode.EMBED);
	}

	@Test
	void aQueryAndDocumentsGoWithTheirInputTypeAndTheConfiguredOptions() {
		InputTypeEmbeddingModel model = model(provider());

		model.call(model.forQuery(new EmbeddingRequest(List.of("q"), EmbeddingOptions.builder().build())));
		model.call(model.forDocuments(new EmbeddingRequest(List.of("a", "b"), null)));

		assertEquals("query", optionsOf(sent.get(0)).getExtraBody().get(InputTypeEmbeddingModel.INPUT_TYPE));
		assertEquals("passage", optionsOf(sent.get(1)).getExtraBody().get(InputTypeEmbeddingModel.INPUT_TYPE));
		assertEquals("nvidia/nemotron-3-embed-1b", optionsOf(sent.get(0)).getModel());
		assertEquals(Duration.ofSeconds(42), optionsOf(sent.get(0)).getTimeout(), "the configured timeout is kept");
	}

	@Test
	void aRequestThatSaysNothingIsEmbeddedAsDocuments() {
		InputTypeEmbeddingModel model = model(provider());

		model.call(new EmbeddingRequest(List.of("x"), null));
		model.embed(new Document("stored text"));

		assertEquals("passage", optionsOf(sent.get(0)).getExtraBody().get(InputTypeEmbeddingModel.INPUT_TYPE));
		assertEquals("passage", optionsOf(sent.get(1)).getExtraBody().get(InputTypeEmbeddingModel.INPUT_TYPE));
	}

	@Test
	void theDimensionsAreLearntOnceWithAQuery() {
		OpenAiEmbeddingModel provider = provider();
		InputTypeEmbeddingModel model = model(provider);

		assertEquals(3, model.dimensions());
		assertEquals(3, model.dimensions());

		verify(provider, times(1)).call(any(EmbeddingRequest.class));
		assertEquals("query", optionsOf(sent.get(0)).getExtraBody().get(InputTypeEmbeddingModel.INPUT_TYPE));
		assertEquals(List.of(InputTypeEmbeddingModel.DIMENSIONS_PROBE), sent.get(0).getInstructions());
	}
}
