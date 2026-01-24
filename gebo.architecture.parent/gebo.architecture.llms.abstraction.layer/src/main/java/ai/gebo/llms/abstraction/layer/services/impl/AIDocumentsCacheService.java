/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.llms.abstraction.layer.model.AIDocumentCacheItem;
import ai.gebo.llms.abstraction.layer.model.AIDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.AIDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.AIDocumentsSet;
import ai.gebo.llms.abstraction.layer.repositories.RagDocumentCacheItemRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import lombok.AllArgsConstructor;

/**
 * Service for handling full documents cache.
 * 
 * @Service annotation indicates that this is a Spring service component.
 * 
 *          AI generated comments
 */
@Service
@AllArgsConstructor
class AIDocumentsCacheService {

	// Inject dependencies
	final IGPersistentObjectManager persistentObject;

	final IGContentManagementSystemHandlerRepositoryPattern contentSystemHandlersPattern;

	final RagDocumentCacheItemRepository cacheItemsRepository;

	final IGDocumentReferenceIngestionHandler ingestionHandler;

	final DocumentReferenceSnapshotRepository documentSnapshotRepository;

	/**
	 * Adds documents to the cache or retrieves them if they already exist.
	 * 
	 * @param objectRef Reference to the project endpoint
	 * @param docList   List of document references
	 * @param result    Result object to store retrieved document information
	 * @throws GeboPersistenceException
	 * @throws GeboContentHandlerSystemException
	 * @throws IOException
	 * @throws GeboIngestionException
	 */
	void addCachedOrRetrieve(GObjectRef<GProjectEndpoint> objectRef, List<GDocumentReference> docList,
			AIDocumentsSet result)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException, GeboIngestionException {
		// Retrieve the project endpoint
		GProjectEndpoint endpoint = this.persistentObject.findByReference(objectRef, GProjectEndpoint.class);
		if (endpoint != null) {
			Map handlerWorkCache = new HashMap();
			IGContentManagementSystemHandler handler = contentSystemHandlersPattern.findByHandledEndpoint(endpoint);
			if (handler != null) {
				// Process each document reference
				for (GDocumentReference document : docList) {
					addCacheOrRetrieve(document, handler, endpoint, result, handlerWorkCache);
				}
			}
		}
	}

	/**
	 * Checks if a document is in cache or needs to be loaded and cached.
	 * 
	 * @param document         Document reference
	 * @param handler          Content management system handler
	 * @param endpoint         Project endpoint
	 * @param result           Result object to store retrieved document information
	 * @param handlerWorkCache Cache for handler work
	 * @throws GeboContentHandlerSystemException
	 * @throws IOException
	 * @throws GeboIngestionException
	 * @throws GeboPersistenceException
	 */
	void addCacheOrRetrieve(GDocumentReference document, IGContentManagementSystemHandler handler,
			GProjectEndpoint endpoint, AIDocumentsSet result, Map handlerWorkCache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException {
		// Check if the document is already in cache
		Optional<AIDocumentCacheItem> entry = cacheItemsRepository.findById(document.getCode());
		boolean load = true;
		if (entry.isPresent()) {
			AIDocumentCacheItem cacheItem = entry.get();
			if (cacheItem.getDateModified() != null && document.getModificationDate() != null) {
				if (cacheItem.getDateModified().after(document.getModificationDate())) {
					addToRetrieved(cacheItem, document, result);
					load = false; // Document is up-to-date and does not need to be loaded again
				}
			}
		}
		if (load) {
			// Load and cache the document
			loadAddCacheAndAddToRetrieved(document, handler, endpoint, result, handlerWorkCache);
		}
	}

	/**
	 * Adds cached document information to retrieved results.
	 * 
	 * @param cacheItem Cached document item
	 * @param document  Document reference
	 * @param result    Result object to store retrieved document information
	 */
	void addToRetrieved(AIDocumentCacheItem cacheItem, GDocumentReference document, AIDocumentsSet result) {
		// Add metadata to result
		if (cacheItem.getTokensSize() != null) {
			cacheItem.getMetaData().put(DocumentMetaInfos.GEBO_TOKEN_LENGTH, cacheItem.getTokensSize());
		}
		if (cacheItem.getBytesSize() != null) {
			cacheItem.getMetaData().put(DocumentMetaInfos.GEBO_BYTES_LENGTH, cacheItem.getBytesSize());
		}
		// Create a new document from cache data and add it to the response
		Document aidocument = new Document(cacheItem.getText(), cacheItem.getMetaData());
		ExtractedDocumentMetaData docMetaData = ExtractedDocumentMetaData.of(cacheItem.getMetaData());
		AIDocumentReferenceItem refitem = new AIDocumentReferenceItem(docMetaData);
		refitem.setTotalFileNTokens(cacheItem.getTokensSize() != null ? cacheItem.getTokensSize() : 0);
		AIDocumentFragment fragment = new AIDocumentFragment(aidocument, docMetaData);
		if (cacheItem.getTokensSize() != null) {
			fragment.setTokensSize(cacheItem.getTokensSize().intValue());
		}
		if (cacheItem.getBytesSize() != null) {
			fragment.setNBytes(cacheItem.getBytesSize().longValue());
		}
		refitem.getFragments().add(fragment);
		result.getDocumentItems().add(refitem);
	}

	/**
	 * Loads document content, caches it if necessary, and adds it to the retrieved
	 * results.
	 * 
	 * @param document         Document reference
	 * @param handler          Content management system handler
	 * @param endpoint         Project endpoint
	 * @param result           Result object to store retrieved document information
	 * @param handlerWorkCache Cache for handler work
	 * @throws GeboContentHandlerSystemException
	 * @throws IOException
	 * @throws GeboIngestionException
	 * @throws GeboPersistenceException
	 */
	void loadAddCacheAndAddToRetrieved(GDocumentReference document, IGContentManagementSystemHandler handler,
			GProjectEndpoint endpoint, AIDocumentsSet result, Map handlerWorkCache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException {
		// Stream content of the document
		InputStream is = handler.streamContent(document, handlerWorkCache);
		// Handle content ingestion
		IngestionHandlerData readData = ingestionHandler.handleContent(document, is);
		if (!readData.isUnmanagedContent()) {
			// Create a new cache item and update it
			AIDocumentCacheItem cacheItem = AIDocumentCacheItem.of(readData.getStream());
			cacheItem.setCode(document.getCode());
			persistentObject.update(cacheItem);
			// Add cache item to retrieved results
			addToRetrieved(cacheItem, document, result);
		}
	}

}