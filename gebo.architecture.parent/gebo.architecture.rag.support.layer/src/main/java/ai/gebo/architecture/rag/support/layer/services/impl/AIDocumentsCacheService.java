/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.architecture.rag.support.layer.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentCacheItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.repository.RagDocumentCacheItemRepository;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.ExtractedDocumentMetaData;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler.IngestionHandlerData;
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
public class AIDocumentsCacheService implements IGAIDocumentsCacheService {

	// Inject dependencies
	final IGPersistentObjectManager persistentObject;

	final IGDocumentContentStreamer streamer;

	final RagDocumentCacheItemRepository cacheItemsRepository;

	final IGDocumentReferenceIngestionHandler ingestionHandler;

	final DocumentReferenceSnapshotRepository documentSnapshotRepository;

	/**
	 * Factory used to build the transient {@link GDocumentReference} that adapts an
	 * external {@link SearchResult} to the (GDocumentReference-typed) ingestion
	 * layer, mirroring the idiom in {@code DocumentsChunkServiceImpl}.
	 */
	final IGDocumentReferenceFactory docReferenceFactory;

	private final static Logger LOGGER = LoggerFactory.getLogger(AIDocumentsCacheService.class);

	private final static ObjectMapper objectMapper = new ObjectMapper();

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
	 * @throws DocumentContentStreamerException
	 */
	public void addCachedOrRetrieve(GObjectRef<GProjectEndpoint> objectRef, List<GDocumentReference> docList,
			AIDocumentsSet result) throws GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			GeboIngestionException, DocumentContentStreamerException {
		// Retrieve the project endpoint
		GProjectEndpoint endpoint = this.persistentObject.findByReference(objectRef, GProjectEndpoint.class);
		if (endpoint != null) {
			// Process each document reference
			for (GDocumentReference document : docList) {
				addCacheOrRetrieve(document, result);
			}
		}
	}

	public AIDocumentReferenceItem retrieve(GDocumentReference document) throws GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboIngestionException, DocumentContentStreamerException {
		AIDocumentsSet set = new AIDocumentsSet();
		addCachedOrRetrieve(document.getProjectEndpointReference(), List.of(document), set);
		if (!set.getDocumentItems().isEmpty())
			return set.getDocumentItems().get(0);
		return null;
	}

	/**
	 * Checks if a document is in cache or needs to be loaded and cached.
	 *
	 * @param document Document reference
	 * @param result   Result object to store retrieved document information
	 * @throws GeboContentHandlerSystemException
	 * @throws IOException
	 * @throws GeboIngestionException
	 * @throws GeboPersistenceException
	 * @throws DocumentContentStreamerException
	 */
	public void addCacheOrRetrieve(GDocumentReference document, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException {
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
			loadAddCacheAndAddToRetrieved(document, result);
		}
	}

	/**
	 * Adds cached document information to retrieved results.
	 * 
	 * @param cacheItem Cached document item
	 * @param document  Document reference
	 * @param result    Result object to store retrieved document information
	 */
	public void addToRetrieved(AIDocumentCacheItem cacheItem, GDocumentReference document, AIDocumentsSet result) {
		// The document reference is not needed here - the retrieved item is rebuilt
		// entirely from the cached item - so the same body serves both internal
		// documents and external search results.
		addToRetrieved(cacheItem, result);
	}

	private void addToRetrieved(AIDocumentCacheItem cacheItem, AIDocumentsSet result) {
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
	 * @param document Document reference
	 * @param result   Result object to store retrieved document information
	 * @throws GeboContentHandlerSystemException
	 * @throws IOException
	 * @throws GeboIngestionException
	 * @throws GeboPersistenceException
	 * @throws DocumentContentStreamerException
	 */
	public void loadAddCacheAndAddToRetrieved(GDocumentReference document, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException {
		// Stream content of the document, resolved via IGDocumentContentStreamer
		// (local on the monolith, chunker-proxying on microservices) rather than a
		// locally co-located IGContentManagementSystemHandler, which only exists on
		// the microservice that owns this document's content system.
		TypedInputStream is = streamer.streamContent(StreamingPurpose.INGESTING, document);
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

	@Override
	public AIDocumentReferenceItem retrieve(SearchResult searchResult) throws GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboIngestionException, DocumentContentStreamerException {
		AIDocumentsSet set = new AIDocumentsSet();
		addCacheOrRetrieve(searchResult, set);
		if (!set.getDocumentItems().isEmpty())
			return set.getDocumentItems().get(0);
		return null;
	}

	@Override
	public void addCachedOrRetrieve(List<SearchResult> searchResults, AIDocumentsSet result)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException, GeboIngestionException,
			DocumentContentStreamerException {
		if (searchResults != null) {
			for (SearchResult searchResult : searchResults) {
				addCacheOrRetrieve(searchResult, result);
			}
		}
	}

	@Override
	public void addCacheOrRetrieve(SearchResult searchResult, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException {
		// Same RAG cache as internal documents, keyed by the search result's own code.
		// External results carry no reliable modification date to compare against, so a
		// cached copy is served as-is; the byte-level freshness/eviction is handled by
		// the chunker's own cache behind IGDocumentContentStreamer on a miss.
		Optional<AIDocumentCacheItem> entry = cacheItemsRepository.findById(searchResult.getCode());
		if (entry.isPresent()) {
			addToRetrieved(entry.get(), result);
		} else {
			loadAddCacheAndAddToRetrieved(searchResult, result);
		}
	}

	/**
	 * Streams the external search result's raw content through the injected
	 * {@link IGDocumentContentStreamer} (never the chunker-local
	 * {@code IDocumentsCacheService}), ingests it through the standard ingestion
	 * layer via a transient adapter {@link GDocumentReference}, caches the resulting
	 * text and adds it to the retrieved set.
	 */
	private void loadAddCacheAndAddToRetrieved(SearchResult searchResult, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException {
		TypedInputStream is = streamer.streamContent(StreamingPurpose.INGESTING, searchResult);
		if (is == null || is.getInputStream() == null) {
			return;
		}
		GDocumentReference adapterReference = encodeDocumentReference(searchResult, is);
		IngestionHandlerData readData = ingestionHandler.handleContent(adapterReference, is);
		if (!readData.isUnmanagedContent()) {
			AIDocumentCacheItem cacheItem = AIDocumentCacheItem.of(readData.getStream());
			cacheItem.setCode(searchResult.getCode());
			persistentObject.update(cacheItem);
			addToRetrieved(cacheItem, result);
		}
	}

	/**
	 * Builds the transient {@link GDocumentReference} that adapts a
	 * {@link SearchResult} to the GDocumentReference-typed ingestion layer, mirroring
	 * {@code DocumentsChunkServiceImpl.encodeDocumentReference}: the content type and
	 * extension come from the streamed content, and the serialized search result is
	 * embedded in the metadata so downstream consumers can reconstruct it.
	 */
	private GDocumentReference encodeDocumentReference(SearchResult searchResult, TypedInputStream is)
			throws GeboContentHandlerSystemException {
		String code = searchResult.getCode();
		String uriCandidate = searchResult.getResultReference() != null
				&& searchResult.getResultReference().getUri() != null ? searchResult.getResultReference().getUri()
						: null;
		String nameCandidate = searchResult.getResultReference() != null
				&& searchResult.getResultReference().getName() != null ? searchResult.getResultReference().getName()
						: null;
		if (nameCandidate == null && searchResult.getNavigationReference() != null
				&& searchResult.getNavigationReference().path != null
				&& searchResult.getNavigationReference().path.name != null) {
			nameCandidate = searchResult.getNavigationReference().path.name;
		}
		String uri = uriCandidate != null ? uriCandidate : code;
		String name = nameCandidate != null ? nameCandidate : uri;
		GDocumentReference adapterReference = docReferenceFactory.createReference(uri, name, is.getContentType(),
				is.getExtension(), null, searchResult.getOriginComponent().getMessagingModuleId(),
				searchResult.getOriginComponent().getMessagingComponentId());
		adapterReference.getCustomMetaInfos().put(DocumentMetaInfos.CONTENT_CODE, code);
		adapterReference.getCustomMetaInfos().put(DocumentMetaInfos.GEBO_FILE_NAME, name);
		if (uriCandidate != null) {
			adapterReference.getCustomMetaInfos().put(DocumentMetaInfos.CONTENT_ORIGINAL_URL, uriCandidate);
		}
		try {
			String searchResultJSON = objectMapper.writeValueAsString(searchResult);
			adapterReference.getCustomMetaInfos().put(DocumentMetaInfos.GEBO_EXTERNAL_SEARCH_RESULT_JSON,
					searchResultJSON);
		} catch (Throwable th) {
			LOGGER.error("Cannot serialize search result for ingestion adapter reference", th);
		}
		return adapterReference;
	}

}