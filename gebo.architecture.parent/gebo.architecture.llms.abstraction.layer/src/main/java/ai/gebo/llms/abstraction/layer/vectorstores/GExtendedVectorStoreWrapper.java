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
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

/**
 * Gebo.ai comment agent
 * Generic wrapper class for VectorStore implementations, providing additional 
 * functionalities while maintaining existing vector store operations.
 *
 * @param <VSType> The type of VectorStore being wrapped.
 */
public class GExtendedVectorStoreWrapper<VSType extends VectorStore> implements IGExtendedVectorStore {

	// The underlying vector store instance that this wrapper delegates to.
	protected final VSType vs;

	/**
	 * Constructs a new wrapper for the specified vector store.
	 *
	 * @param vs The vector store instance to wrap.
	 */
	public GExtendedVectorStoreWrapper(VSType vs) {
		this.vs = vs;
	}

	/**
	 * Adds a list of documents to the vector store.
	 *
	 * @param documents The documents to add.
	 */
	@Override
	public void add(List<Document> documents) {
		vs.add(documents);
	}

	/**
	 * Deletes documents from the vector store using a list of document IDs.
	 *
	 * @param idList The list of document IDs to delete.
	 */
	@Override
	public void delete(List<String> idList) {
		vs.delete(idList);
	}

	/**
	 * Performs a similarity search in the vector store based on the specified request.
	 *
	 * @param request The search request containing parameters for similarity search.
	 * @return A list of documents that are similar according to the search criteria.
	 */
	@Override
	public List<Document> similaritySearch(SearchRequest request) {
		return vs.similaritySearch(request);
	}

	/**
	 * Factory method to create a new instance of GExtendedVectorStoreWrapper.
	 *
	 * @param <VSType> The type of VectorStore.
	 * @param vs The vector store instance to wrap.
	 * @return A new instance of GExtendedVectorStoreWrapper wrapping the specified vector store.
	 */
	public static <VSType extends VectorStore> GExtendedVectorStoreWrapper<VSType> of(VSType vs) {
		return new GExtendedVectorStoreWrapper<VSType>(vs);
	}

	/**
	 * Deletes documents from the vector store using a filter expression.
	 *
	 * @param filterExpression The expression that specifies which documents to delete.
	 */
	@Override
	public void delete(Expression filterExpression) {
		vs.delete(filterExpression);
	}
}