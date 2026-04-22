package ai.gebo.architecture.fulltext.service;

import java.util.List;

import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;

public interface IGFullTextSearchService {
	public List<FullTextChunkSearchHit> search(List<String> q, int topK, FullTextSearchMetaDataFilter filter)
			throws FullTextException;

	public List<FullTextChunkSearchHit> search(String q, int topK, FullTextSearchMetaDataFilter filter) throws FullTextException;
	
}
