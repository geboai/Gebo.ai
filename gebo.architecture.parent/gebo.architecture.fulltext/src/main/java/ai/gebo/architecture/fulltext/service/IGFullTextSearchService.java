package ai.gebo.architecture.fulltext.service;

import java.util.List;

import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;

public interface IGFullTextSearchService {
	public List<FullTextChunkSearchHit> search(List<String> q, int topK, FullTextSearchMetaDataFilter filter)
			throws FullTextException;

	public List<FullTextChunkSearchHit> search(String q, int topK, FullTextSearchMetaDataFilter filter) throws FullTextException;

	/**
	 * The indexed chunks of a document, in their order in the document, a page at a
	 * time: {@code size} chunks from the {@code from}-th. None when the index cannot
	 * give a document's chunks.
	 */
	public default List<FullTextChunkSearchHit> documentChunks(String documentCode, int from, int size)
			throws FullTextException {
		return List.of();
	}
	
}
