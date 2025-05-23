/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

import ai.gebo.architecture.lucene.model.LuceneVectorStoreEntry;
import ai.gebo.architecture.lucene.model.SearchKeywordRequest;
import ai.gebo.architecture.lucene.model.SearchKeywordResults;

/**
 * Gebo.ai comment agent
 * Interface for the internal Lucene server service. This extends the Runnable interface,
 * allowing implementations to run in a separate thread if desired. This service provides 
 * various methods for managing and querying documents in a Lucene-based datastore.
 */
public interface IGInternalLuceneServer extends Runnable {

	/**
	 * Retrieves the path where data is stored.
	 * 
	 * @return the path to the data directory
	 */
	public Path getDataPath();

	/**
	 * Saves a list of documents to the Lucene vector store.
	 * 
	 * @param documents a list of documents to be saved
	 */
	public void save(List<LuceneVectorStoreEntry> documents);

	/**
	 * Deletes documents with the specified IDs from the datastore.
	 * 
	 * @param ids a list of IDs of documents to be deleted
	 * @return an Optional containing a Boolean value indicating success or failure
	 */
	public Optional<Boolean> delete(List<String> ids);

	/**
	 * Runs a similarity query on the datastore using the provided search request and vector.
	 * 
	 * @param request the search request
	 * @param vector the vector to use for similarity comparison
	 * @return a list of Documents that match the query
	 * @throws IOException if an I/O error occurs
	 */
	public List<Document> runSimilarityQuery(SearchRequest request, float[] vector) throws IOException;

	/**
	 * Executes a keyword search across specified knowledge bases.
	 * 
	 * @param kbases the knowledge bases to search within
	 * @param x the keyword search request parameters
	 * @return the results of the search keyword request
	 * @throws IOException if an I/O error occurs
	 * @throws ParseException if a parsing error occurs during query parsing
	 */
	public SearchKeywordResults runSearch(List<String> kbases, SearchKeywordRequest x)
			throws IOException, ParseException;

	/**
	 * Shuts down the Lucene server, performing any necessary cleanup.
	 */
	public void shutdown();

	/**
	 * Deletes documents by their reference codes.
	 * 
	 * @param codes a list of reference codes for documents to be deleted
	 */
	public void deleteByDocumentReferenceCodes(List<String> codes);

	/**
	 * Deletes documents that match the specified filter expression.
	 * 
	 * @param filterExpression the expression used to filter documents for deletion
	 */
	public void delete(Expression filterExpression);

}