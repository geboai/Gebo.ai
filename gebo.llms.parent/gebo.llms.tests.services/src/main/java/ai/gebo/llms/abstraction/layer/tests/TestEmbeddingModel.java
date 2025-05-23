/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.tests;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;

/**
 * AI generated comments
 * A test implementation of the EmbeddingModel interface to be used in testing scenarios.
 * This class extends AbstractTestingBusinessLogic and implements the EmbeddingModel interface
 * to provide a mock embedding model for testing purposes.
 */
public class TestEmbeddingModel extends AbstractTestingBusinessLogic implements EmbeddingModel {
	/** Configuration for the test embedding model */
	TestEmbeddingModelConfiguration configuration=null;
	
	/**
	 * Default constructor for the TestEmbeddingModel.
	 */
	public TestEmbeddingModel() {
		
	}

	/**
	 * Processes an embedding request and returns a response.
	 * Currently returns null as this is a test implementation.
	 * 
	 * @param request The embedding request to process
	 * @return EmbeddingResponse containing the embedding results (currently null)
	 */
	@Override
	public EmbeddingResponse call(EmbeddingRequest request) {
		
		return null;
	}

	/**
	 * Creates an embedding vector for the provided document.
	 * Currently returns null as this is a test implementation.
	 * 
	 * @param document The document to create an embedding for
	 * @return float array representing the embedding vector (currently null)
	 */
	@Override
	public float[] embed(Document document) {
		
		return null;
	}

	/**
	 * Gets the current configuration for this test embedding model.
	 * 
	 * @return The current TestEmbeddingModelConfiguration
	 */
	public TestEmbeddingModelConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration for this test embedding model.
	 * 
	 * @param configuration The TestEmbeddingModelConfiguration to set
	 */
	public void setConfiguration(TestEmbeddingModelConfiguration configuration) {
		this.configuration = configuration;
	}

}