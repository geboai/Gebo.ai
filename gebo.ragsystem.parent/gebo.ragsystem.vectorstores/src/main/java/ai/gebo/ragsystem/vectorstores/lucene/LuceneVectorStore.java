/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingOptionsBuilder;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

import ai.gebo.architecture.lucene.model.LuceneVectorStoreEntry;
import ai.gebo.architecture.lucene.services.IGInternalLuceneServer;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;

/**
 * AI generated comments
 * 
 * A Lucene-based implementation of the extended vector store interface.
 * This class provides vector storage and similarity search capabilities using Apache Lucene
 * and integrates with embedding models for vector generation.
 */
public class LuceneVectorStore implements IGExtendedVectorStore {
	/** Logger for this class */
	final private static Logger LOGGER = LoggerFactory.getLogger(LuceneVectorStore.class);
	/** Internal Lucene server for storing and querying vector data */
	final IGInternalLuceneServer luceneServer;
	/** Model used to generate embeddings */
	final EmbeddingModel embeddingModel;
	/** Configuration for the embedding model */
	final GBaseEmbeddingModelConfig config;
	/** Options for generating embeddings */
	final EmbeddingOptions embeddingOptions;

	/**
	 * Constructs a new LuceneVectorStore with the specified embedding model and Lucene server.
	 *
	 * @param config Configuration for the embedding model
	 * @param embeddingModel The embedding model used to generate vectors
	 * @param luceneServer The Lucene server for storing and retrieving data
	 * @throws IOException If there is an error initializing the vector store
	 */
	public LuceneVectorStore(GBaseEmbeddingModelConfig config, EmbeddingModel embeddingModel,
			IGInternalLuceneServer luceneServer) throws IOException {
		this.embeddingModel = embeddingModel;
		this.config = config;
		this.luceneServer = luceneServer;
		EmbeddingOptionsBuilder builder = EmbeddingOptionsBuilder.builder().withDimensions(1024);
		builder.withModel(config.getChoosedModel().getCode());
		this.embeddingOptions = builder.build();

	}

	/**
	 * Adds a list of documents to the vector store by generating embeddings
	 * and storing them in the Lucene server.
	 *
	 * @param documents The documents to add to the vector store
	 */
	@Override
	public void add(List<Document> documents) {
		List<LuceneVectorStoreEntry> entries = new ArrayList<LuceneVectorStoreEntry>();
		List<float[]> list = embeddingModel.embed(documents, embeddingOptions, new TokenCountBatchingStrategy());
		for (int i = 0; i < documents.size(); i++) {
			Document document = documents.get(i);
			float[] vector = list.get(i);
			LuceneVectorStoreEntry entry = new LuceneVectorStoreEntry(document, vector);
			entries.add(entry);
		}
		luceneServer.save(entries);
	}

	/**
	 * Deletes documents from the vector store based on their IDs.
	 *
	 * @param idList List of document IDs to delete
	 */
	@Override
	public void delete(List<String> idList) {

		luceneServer.delete(idList);

	}

	/**
	 * Performs a similarity search using the provided query.
	 * The method converts the query to an embedding vector and uses
	 * the Lucene server to find similar documents.
	 *
	 * @param request The search request containing the query and parameters
	 * @return List of documents that are similar to the query
	 */
	@Override
	public List<Document> similaritySearch(SearchRequest request) {
		Document document = new Document(request.getQuery());
		List<float[]> list = embeddingModel.embed(List.of(document), embeddingOptions,
				new TokenCountBatchingStrategy());
		try {
			return luceneServer.runSimilarityQuery(request, list.get(0));
		} catch (IOException e) {
			throw new RuntimeException("Exception in similaritySearch", e);
		}
	}

	/**
	 * Closes the vector store and releases resources.
	 *
	 * @throws IOException If there is an error during shutdown
	 */
	@Override
	public void close() throws IOException {
		luceneServer.shutdown();
	}

	/**
	 * Deletes documents from the vector store based on a filter expression.
	 *
	 * @param filterExpression The expression used to filter documents for deletion
	 */
	@Override
	public void delete(Expression filterExpression) {
		luceneServer.delete(filterExpression);

	}
}