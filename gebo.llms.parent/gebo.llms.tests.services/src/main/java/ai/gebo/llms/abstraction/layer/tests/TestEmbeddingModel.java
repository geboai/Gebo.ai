/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.tests;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;

/**
 * Embedding model stand-in for the integration tests.
 *
 * It used to answer {@code null} everywhere, which was enough while the only
 * test vector store was {@link ai.gebo.ragsystem.vectorstores.test.services
 * TestVectorStore} - that one keys documents by id and never looks at a vector.
 * A test running against a REAL vector store needs real vectors, so this model
 * now produces them.
 *
 * <h2>Properties the tests rely on</h2>
 * <ul>
 * <li><b>Deterministic</b>: the same text always yields the same vector, so a
 * query can be asserted to retrieve the chunk that talks about it. A random
 * vector would only have proven that the plumbing moves floats around.</li>
 * <li><b>Semantically ordered</b>: it is a hashed bag of words, normalised to
 * unit length, so texts sharing words come out closer under cosine
 * similarity.</li>
 * <li><b>A realistic width</b>: 1536 is one of the mainstream embedding sizes, so
 * a store exercised by these tests sees vectors of the shape it will really be
 * given rather than a small one chosen to make the test cheap.</li>
 * <li><b>Free</b>: no network, no provider, no cost.</li>
 * </ul>
 */
public class TestEmbeddingModel extends AbstractTestingBusinessLogic implements EmbeddingModel {

	/**
	 * Width used when the configuration does not ask for another one, matching one
	 * of the mainstream embedding sizes.
	 */
	public static final int DEFAULT_DIMENSIONS = 1536;

	/** Configuration for the test embedding model */
	TestEmbeddingModelConfiguration configuration = null;

	/**
	 * Builds a model with the default width.
	 */
	public TestEmbeddingModel() {
	}

	/**
	 * Number of components of every vector this model produces.
	 *
	 * @return the configured width, or {@link #DEFAULT_DIMENSIONS}
	 */
	@Override
	public int dimensions() {
		if (configuration != null && configuration.getTestDimensions() != null
				&& configuration.getTestDimensions() > 0) {
			return configuration.getTestDimensions();
		}
		return DEFAULT_DIMENSIONS;
	}

	/**
	 * Embeds every instruction of the request.
	 *
	 * @param request the texts to embed
	 * @return one embedding per instruction, in the same order
	 */
	@Override
	public EmbeddingResponse call(EmbeddingRequest request) {
		List<Embedding> embeddings = new ArrayList<>();
		if (request != null && request.getInstructions() != null) {
			int index = 0;
			for (String instruction : request.getInstructions()) {
				embeddings.add(new Embedding(embed(instruction), index++));
			}
		}
		return new EmbeddingResponse(embeddings);
	}

	/**
	 * Embeds the text of a document.
	 *
	 * @param document the document to embed
	 * @return its vector
	 */
	@Override
	public float[] embed(Document document) {
		if (document == null) {
			return embed("");
		}
		String text = document.getText();
		return embed(text != null ? text : document.getFormattedContent());
	}

	/**
	 * Deterministic hashed bag-of-words embedding, normalised to unit length so
	 * cosine similarity is the plain dot product.
	 *
	 * @param text the text to embed, may be null
	 * @return its vector, never null
	 */
	@Override
	public float[] embed(String text) {
		final int width = dimensions();
		float[] vector = new float[width];
		if (text != null) {
			for (String token : text.toLowerCase().split("[^\\p{L}\\p{N}]+")) {
				if (token.isEmpty()) {
					continue;
				}
				// Two buckets per token: it lowers the collision rate enough that
				// unrelated chunks of a real test corpus do not end up neighbours.
				int hash = token.hashCode();
				vector[Math.abs(hash % width)] += 1.0f;
				vector[Math.abs((hash * 31 + 7) % width)] += 0.5f;
			}
		}
		double norm = 0.0;
		for (float component : vector) {
			norm += component * component;
		}
		norm = Math.sqrt(norm);
		if (norm > 0.0) {
			for (int i = 0; i < width; i++) {
				vector[i] = (float) (vector[i] / norm);
			}
		} else {
			// An empty or punctuation-only chunk still has to be a legal vector: a
			// zero vector has no direction, and cosine similarity is undefined on it.
			vector[0] = 1.0f;
		}
		return vector;
	}

	/**
	 * Embeds a batch of texts.
	 *
	 * @param texts the texts to embed
	 * @return one vector per text, in the same order
	 */
	@Override
	public List<float[]> embed(List<String> texts) {
		List<float[]> out = new ArrayList<>();
		if (texts != null) {
			for (String text : texts) {
				out.add(embed(text));
			}
		}
		return out;
	}

	/**
	 * @return the configuration this model was built from
	 */
	public TestEmbeddingModelConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration the configuration to apply
	 */
	public void setConfiguration(TestEmbeddingModelConfiguration configuration) {
		this.configuration = configuration;
	}
}
