package ai.gebo.architecture.opensearch.service.impl;

import java.io.IOException;
import java.util.List;

import org.opensearch.client.opensearch._types.OpenSearchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.model.FullTextChunkSearchHit;
import ai.gebo.architecture.fulltext.model.MetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextSearchService;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Service
@AllArgsConstructor
public class GFullTextSearchServiceImpl implements IGFullTextSearchService {
	private final OpenSearchFullTextChunkSearchService search;

	@Override
	public List<FullTextChunkSearchHit> search(List<String> q, int topK, MetaDataFilter filter)
			throws FullTextException {

		try {
			return search.searchTopKChunks(q, topK, filter);
		} catch (OpenSearchException | IOException e) {
			throw new FullTextException("exception in search", e);
		}
	}

	@Override
	public List<FullTextChunkSearchHit> search(String q, int topK, MetaDataFilter filter) throws FullTextException {
		try {
			return search.searchTopKChunks(q, topK, filter);
		} catch (OpenSearchException | IOException e) {
			throw new FullTextException("exception in search", e);
		}
	}

}
