/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryBuilder;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * Utility class for testing vector store factory builders.
 * Provides functionality to validate that vector store configurations
 * can properly build factories and stores with test data.
 */
public class VectorStoreFactoryBuilderTester {
	private final static Logger LOGGER = LoggerFactory.getLogger(VectorStoreFactoryBuilderTester.class);

	/**
	 * Dummy implementation of EmbeddingModel for testing purposes.
	 * Generates random embedding vectors rather than using a real model.
	 */
	public static class DummyEmbeddingModel implements EmbeddingModel {
		int calledIndex = 0;

		/**
		 * Generates a random vector with 1024 dimensions for testing purposes.
		 * 
		 * @return a randomly generated float array with 1024 elements
		 */
		private float[] randomVector() {
			float[] vector = new float[1024];
			for (int i = 0; i < vector.length; i++) {
				vector[i] = (float) (Math.random() * 24.0);
			}
			return vector;
		}

		/**
		 * Processes an embedding request by returning a random vector.
		 * 
		 * @param request the embedding request to process
		 * @return an embedding response containing a single embedding with random vector
		 */
		@Override
		public EmbeddingResponse call(EmbeddingRequest request) {
			Embedding embedding = new Embedding(randomVector(), ++calledIndex);
			return new EmbeddingResponse(List.of(embedding));
		}

		/**
		 * Creates an embedding for a document.
		 * 
		 * @param document the document to embed
		 * @return a random vector representing the document embedding
		 */
		@Override
		public float[] embed(Document document) {
			return randomVector();
		}

		/**
		 * Creates an embedding for a text string.
		 * 
		 * @param text the text to embed
		 * @return a random vector representing the text embedding
		 */
		@Override
		public float[] embed(String text) {
			return randomVector();
		}

		/**
		 * Creates embeddings for a list of text strings.
		 * 
		 * @param texts the list of texts to embed
		 * @return a list of random vectors representing the text embeddings
		 */
		@Override
		public List<float[]> embed(List<String> texts) {
			return texts.isEmpty() ? List.of() : texts.stream().map(x -> randomVector()).toList();
		}

		/**
		 * Returns the dimensionality of the embedding vectors.
		 * 
		 * @return the number of dimensions in the embedding vectors (1024)
		 */
		@Override
		public int dimensions() {
			return 1024;
		}
	}

	/**
	 * Sample text used for testing embeddings.
	 * First lines from Dante's Divine Comedy in Italian.
	 */
	public static String EMBEDDING_TEXT = "Nel mezzo del cammin di nostra vita mi ritrovai in una selva oscura, che la retta via era smarrita";

	/**
	 * Tests a vector store factory builder with a given configuration.
	 * Creates a factory, builds a vector store, and adds a test document to verify functionality.
	 * 
	 * @param <T> the type of vector store configuration
	 * @param config the configuration to test
	 * @param builder the factory builder to test
	 * @return an operation status containing the factory if successful, or error details if failed
	 */
	public static <T extends GBaseVectorStoreConfig> OperationStatus<IGVectorStoreFactory<T>> test(T config,
			IGVectorStoreFactoryBuilder builder) {
		IGVectorStoreFactory<T> factory = null;
		try {
			factory = builder.build(config);
			GBaseEmbeddingModelConfig dummyEmbeddingModelConfiguration = new GBaseEmbeddingModelConfig();
			dummyEmbeddingModelConfiguration.setCode("test-embedding-vectorstore-gebo-ai");
			DummyEmbeddingModel dummyEmbeddingModel = new DummyEmbeddingModel();
			IGExtendedVectorStore vectorStore = factory.create(dummyEmbeddingModelConfiguration, dummyEmbeddingModel);
			vectorStore.add(List.of(new Document(EMBEDDING_TEXT)));
			return OperationStatus.of(factory);
		} catch (Throwable th) {
			final String msg = "Error testing " + builder.getProduct().name() + " vector db config";
			OperationStatus<IGVectorStoreFactory<T>> out = OperationStatus.of(factory);
			out.getMessages().clear();
			out.getMessages().add(GUserMessage.errorMessage(msg, th));
			LOGGER.error(msg, th);
			return out;
		}
	}
}