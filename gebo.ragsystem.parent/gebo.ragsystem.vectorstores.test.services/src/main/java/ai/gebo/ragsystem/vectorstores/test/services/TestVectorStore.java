/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.test.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.Filter.Expression;

import ai.gebo.architecture.testing.AbstractTestingBusinessLogic;
import ai.gebo.llms.abstraction.layer.vectorstores.IGExtendedVectorStore;
import ai.gebo.model.DocumentMetaInfos;

/**
 * AI generated comments
 * 
 * A test implementation of the IGExtendedVectorStore interface used for testing purposes.
 * This class simulates a vector store by storing documents in memory with configurable response times.
 */
public class TestVectorStore extends AbstractTestingBusinessLogic implements IGExtendedVectorStore {
	/** Map to store documents by their IDs */
	private Map<String, Document> data = new HashMap<String, Document>();
	/** Map for organizing documents by metadata attributes and their values */
	private Map<String, Map<Object, List<Document>>> metaInfoCataloging = new HashMap<String, Map<Object, List<Document>>>();
	/** List to track document IDs that have been deleted */
	private List<String> deletedDocumentIds=new ArrayList<String>();
	/** Configuration for the test vector store */
	TestVectorStoreConfig vectorStoreConfig = null;

	/**
	 * Default constructor that initializes metadata cataloging for all attributes.
	 */
	TestVectorStore() {
		for (String attr : DocumentMetaInfos.ALL_ATTRIBUTES) {
			metaInfoCataloging.put(attr, new HashMap<Object, List<Document>>());
		}
	}

	/**
	 * Constructor that accepts a configuration object.
	 * 
	 * @param vectorStoreConfig Configuration for the test vector store
	 */
	public TestVectorStore(TestVectorStoreConfig vectorStoreConfig) {
		this();
		this.vectorStoreConfig = vectorStoreConfig;
	}

	/**
	 * Adds documents to the vector store with optional simulated delay.
	 * 
	 * @param documents List of documents to add
	 */
	@Override
	public void add(List<Document> documents) {
		LOGGER.info("Begin add(documents) with:" + documents.size() + " documents");
		if (vectorStoreConfig != null && vectorStoreConfig.getMillisecondResponseTime() != null
				&& vectorStoreConfig.getMillisecondResponseTime() > 0) {
			LOGGER.info("Simulating embedding delay for " + vectorStoreConfig.getMillisecondResponseTime() + " msec");
			try {
				Thread.currentThread().sleep(vectorStoreConfig.getMillisecondResponseTime().longValue());
				LOGGER.info("Vector store returned from simulated remote call");
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		for (Document document : documents) {
			data.put(document.getId(), document);
		}
		indexedForMetas(documents);
		LOGGER.info("End add(documents)");

	}

	/**
	 * Deletes documents by their IDs.
	 * 
	 * @param idList List of document IDs to delete
	 */
	@Override
	public void delete(List<String> idList) {
		LOGGER.info("Begin delete(" + idList + ")");
		Boolean done = null;
		for (String id : idList) {
			done = data.containsKey(id);
			data.remove(id);
		}
		deletedDocumentIds.addAll(idList);
		LOGGER.info("End delete(" + idList + ")");

	}

	/**
	 * Retrieves all documents stored in the vector store.
	 * 
	 * @return List of all documents
	 */
	public List<Document> getAllData() {
		List<Document> out = new ArrayList<Document>();
		for (Entry<String, Document> entry : data.entrySet()) {
			out.add(entry.getValue());
		}
		return out;

	}

	/**
	 * Performs a similarity search (currently returns an empty list).
	 * 
	 * @param request The search request
	 * @return An empty list in this implementation
	 */
	@Override
	public List<Document> similaritySearch(SearchRequest request) {
		LOGGER.info("Running  similaritySearch(...)");
		return List.of();
	}

	/**
	 * Indexes documents by their metadata for faster retrieval.
	 * 
	 * @param documents List of documents to index
	 */
	private void indexedForMetas(List<Document> documents) {
		for (Document document : documents) {
			Map<String, Object> meta = document.getMetadata();
			if (meta.isEmpty() || !meta.containsKey(DocumentMetaInfos.CONTENT_CODE)) {
				LOGGER.error("Here we have a document without metadata or without document code");
			}
			Set<String> keys = meta.keySet();
			for (String key : keys) {
				if (!metaInfoCataloging.containsKey(key)) {
					metaInfoCataloging.put(key, new HashMap<Object, List<Document>>());
				}
				Object value = meta.get(key);
				if (value != null) {
					if (!metaInfoCataloging.get(key).containsKey(value)) {
						metaInfoCataloging.get(key).put(value, new ArrayList<Document>());
					}
					metaInfoCataloging.get(key).get(value).add(document);
				}
			}
		}
	}

	/**
	 * Gets a list of all handled document content codes.
	 * 
	 * @return List of document content codes as strings
	 */
	public List<String> getHandledGeboDocumentCodes() {
		Map<Object, List<Document>> map = metaInfoCataloging.get(DocumentMetaInfos.CONTENT_CODE);
		if (map != null) {
			return map.keySet().stream().map(x -> x.toString()).toList();
		}
		return List.of();

	}

	/**
	 * Retrieves documents that match a specific metadata attribute and value.
	 * 
	 * @param key The metadata attribute key
	 * @param value The value to match
	 * @return List of documents that match the criteria
	 */
	public List<Document> getStoredForMetaAttributeAndValue(String key, Object value) {
		if (metaInfoCataloging.containsKey(key) && metaInfoCataloging.get(key).containsKey(value))
			return metaInfoCataloging.get(key).get(value);
		return List.of();
	}

	/**
	 * Retrieves all documents organized by values for a specific metadata attribute.
	 * 
	 * @param key The metadata attribute key
	 * @return Map of values to documents for the specified key
	 */
	public Map<Object, List<Document>> getStoredForMetaAttribute(String key) {
		if (metaInfoCataloging.containsKey(key)) {
			return metaInfoCataloging.get(key);
		}
		return new HashMap<Object, List<Document>>();
	}

	/**
	 * Deletes documents that match a filter expression (not implemented).
	 * 
	 * @param filterExpression The filter expression
	 */
	@Override
	public void delete(Expression filterExpression) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets a list of document IDs that have been deleted.
	 * 
	 * @return List of deleted document IDs
	 */
	public List<String> getDeletedDocumentIds() {
		return deletedDocumentIds;
	}
}