/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

import ai.gebo.llms.abstraction.layer.vectorstores.model.EmbeddingTrafficInfo;

/**
 * AI generated comments
 * This class acts as an adapter for the IGExtendedVectorStore to add accounting features
 * that track the byte size of documents and search queries processed by the vector store.
 */
public class GAccountingExtendedVectorStoreAdapter implements IGExtendedVectorStore {
	
	// The underlying vector store that is wrapped and extended by this adapter.
	final IGExtendedVectorStore wrapped;

	// Tracks the current traffic information regarding embeddings.
	private EmbeddingTrafficInfo current = new EmbeddingTrafficInfo();
	
	// Monitor object used for synchronizing access to current traffic information.
	private Object monitor = new Object();

	/**
	 * Constructs a GAccountingExtendedVectorStoreAdapter that wraps the given vector store.
	 * 
	 * @param wrapped the IGExtendedVectorStore instance to be wrapped and extended.
	 */
	public GAccountingExtendedVectorStoreAdapter(IGExtendedVectorStore wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * Adds a list of documents to the wrapped vector store and tracks the byte size of the documents.
	 * 
	 * @param documents the list of documents to be added to the vector store.
	 */
	@Override
	public void add(List<Document> documents) {
		this.wrapped.add(documents);
		long totalSize = 0l;
		for (Document document : documents) {
			final String text = document.getText();
			// Calculate the byte size of each document's text and add to totalSize.
			totalSize += text.getBytes().length;
		}
		// Update the current byte count in a synchronized block.
		synchronized (monitor) {
			this.current.bytesCount += totalSize;
		}
	}

	/**
	 * Deletes documents identified by their IDs from the wrapped vector store.
	 * 
	 * @param idList the list of document IDs to be deleted.
	 */
	@Override
	public void delete(List<String> idList) {
		this.wrapped.delete(idList);
	}

	/**
	 * Performs a similarity search using the given search request and tracks the byte size of the query.
	 * 
	 * @param request the search request containing the query and other search parameters.
	 * @return a list of documents that are similar to the search query.
	 */
	@Override
	public List<Document> similaritySearch(SearchRequest request) {
		final String query = request.getQuery();

		List<Document> docs = this.wrapped.similaritySearch(request);
		if (query != null) {
			// Calculate and track the byte size of the query.
			long totalSize = query.getBytes().length;
			synchronized (monitor) {
				this.current.bytesCount += totalSize;
			}
		}
		return docs;
	}

	/**
	 * Retrieves the sampled bytes of traffic information and resets the current tracking data.
	 * 
	 * @return the EmbeddingTrafficInfo containing sampled bytes of traffic.
	 */
	public EmbeddingTrafficInfo getSampledBytesOfTraffic() {
		EmbeddingTrafficInfo old = current;
		synchronized (monitor) {
			// Reset the current traffic information instance.
			this.current = new EmbeddingTrafficInfo();
		}
		return old;
	}

	/**
	 * Returns the wrapped vector store instance.
	 * 
	 * @return the IGExtendedVectorStore that is wrapped by this adapter.
	 */
	public IGExtendedVectorStore getWrapped() {
		return wrapped;
	}

	/**
	 * Deletes documents that match the given filter expression from the wrapped vector store.
	 * 
	 * @param filterExpression the expression that specifies which documents to delete.
	 */
	@Override
	public void delete(Expression filterExpression) {
		wrapped.delete(filterExpression);
	}
}