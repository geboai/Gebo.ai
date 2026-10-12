package ai.gebo.architecture.rag.support.layer.services;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentCacheItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IGAIDocumentsCacheService {
	public void addCachedOrRetrieve(GObjectRef<GProjectEndpoint> objectRef, List<GDocumentReference> docList,
			AIDocumentsSet result) throws GeboPersistenceException, GeboContentHandlerSystemException, IOException,
			GeboIngestionException, DocumentContentStreamerException;

	public void addCacheOrRetrieve(GDocumentReference document, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException;

	public void addToRetrieved(AIDocumentCacheItem cacheItem, GDocumentReference document, AIDocumentsSet result);

	public void loadAddCacheAndAddToRetrieved(GDocumentReference document, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException;

	public AIDocumentReferenceItem retrieve(GDocumentReference document) throws GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboIngestionException, DocumentContentStreamerException;

	/**
	 * Retrieves the ingested, RAG-cacheable incarnation of an external search
	 * result so the user can "chat with" it as if it were an internal document.
	 *
	 * <p>
	 * A {@link SearchResult} cannot be addressed by an internal document code, so it
	 * carries its own identity ({@link SearchResult#getCode()}). This method reuses
	 * the same {@link AIDocumentCacheItem} cache as the {@link GDocumentReference}
	 * variant, keyed by that code: on a cache hit it rebuilds the
	 * {@link AIDocumentReferenceItem} from the cached text; on a miss it streams the
	 * raw content through the injected {@code IGDocumentContentStreamer} (which owns
	 * the delegated chain of responsibility - and the chunker's binary cache - for
	 * search results), ingests it through the standard ingestion layer, caches the
	 * result and returns it. The content read always goes through
	 * {@code IGDocumentContentStreamer}; the chunker-local {@code IDocumentsCacheService}
	 * is never accessed directly (it is not reachable in the microservices topology).
	 */
	public AIDocumentReferenceItem retrieve(SearchResult searchResult) throws GeboPersistenceException,
			GeboContentHandlerSystemException, IOException, GeboIngestionException, DocumentContentStreamerException;

	public void addCacheOrRetrieve(SearchResult searchResult, AIDocumentsSet result)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException,
			DocumentContentStreamerException;

	public void addCachedOrRetrieve(List<SearchResult> searchResults, AIDocumentsSet result)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException, GeboIngestionException,
			DocumentContentStreamerException;

}
