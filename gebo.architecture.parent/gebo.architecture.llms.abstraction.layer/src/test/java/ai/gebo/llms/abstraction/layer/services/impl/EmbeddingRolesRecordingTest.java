/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;

import ai.gebo.llms.abstraction.layer.services.IGRoleAwareEmbeddingModel;

/**
 * Pins how the recording wrapper the vector stores call tells a search query from the
 * documents it stores, for a model that needs to know (NVIDIA's input_type): a query
 * through embed(String), documents through embed(List of Document), both still through
 * call() where the usage is recorded; any other model keeps the interface defaults.
 */
class EmbeddingRolesRecordingTest {

	/** A provider model recording which role each request was made for. */
	static class RoleRecordingModel implements IGRoleAwareEmbeddingModel {
		final List<String> roles = new ArrayList<>();
		final List<Integer> sizes = new ArrayList<>();

		@Override
		public EmbeddingRequest forQuery(EmbeddingRequest request) {
			return new EmbeddingRequest(request.getInstructions(), new RoleOptions("query"));
		}

		@Override
		public EmbeddingRequest forDocuments(EmbeddingRequest request) {
			return new EmbeddingRequest(request.getInstructions(), new RoleOptions("passage"));
		}

		@Override
		public EmbeddingResponse call(EmbeddingRequest request) {
			roles.add(request.getOptions() instanceof RoleOptions role ? role.role : "none");
			sizes.add(request.getInstructions().size());
			List<Embedding> embeddings = new ArrayList<>();
			for (int i = 0; i < request.getInstructions().size(); i++) {
				embeddings.add(new Embedding(new float[] { 1f, 2f }, i));
			}
			return new EmbeddingResponse(embeddings);
		}

		@Override
		public float[] embed(Document document) {
			return new float[] { 1f, 2f };
		}
	}

	record RoleOptions(String role) implements EmbeddingOptions {
		@Override
		public String getModel() {
			return null;
		}

		@Override
		public Integer getDimensions() {
			return null;
		}
	}

	private static UsageRecordingEmbeddingModel recording(EmbeddingModel delegate) {
		return new UsageRecordingEmbeddingModel(delegate, () -> null, () -> null, () -> null);
	}

	@Test
	void aSearchQueryIsEmbeddedAsAQuery() {
		RoleRecordingModel provider = new RoleRecordingModel();

		float[] embedding = recording(provider).embed("which contracts expire in 2027?");

		assertEquals(2, embedding.length);
		assertEquals(List.of("query"), provider.roles);
	}

	@Test
	void theDocumentsAStoreAddsAreEmbeddedAsDocumentsBatchByBatch() {
		RoleRecordingModel provider = new RoleRecordingModel();
		List<Document> documents = List.of(new Document("first chunk"), new Document("second chunk"),
				new Document("third chunk"));

		List<float[]> embeddings = recording(provider).embed(documents, EmbeddingOptions.builder().build(),
				new TokenCountBatchingStrategy());

		assertEquals(3, embeddings.size());
		assertTrue(provider.roles.stream().allMatch("passage"::equals), provider.roles.toString());
		assertEquals(3, provider.sizes.stream().mapToInt(Integer::intValue).sum());
	}

	@Test
	void aModelNotTellingQueriesFromDocumentsKeepsTheDefaults() {
		List<EmbeddingRequest> seen = new ArrayList<>();
		EmbeddingModel plain = new EmbeddingModel() {
			@Override
			public EmbeddingResponse call(EmbeddingRequest request) {
				seen.add(request);
				return new EmbeddingResponse(List.of(new Embedding(new float[] { 3f }, 0)));
			}

			@Override
			public float[] embed(Document document) {
				return new float[] { 3f };
			}
		};

		recording(plain).embed("a query");

		assertEquals(1, seen.size());
		assertTrue(!(seen.get(0).getOptions() instanceof RoleOptions), "no role options for a plain model");
	}
}
