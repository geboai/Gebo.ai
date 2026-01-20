package ai.gebo.architecture.opensearch.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.opensearch.client.opensearch._types.OpenSearchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.fulltext.service.IGFullTextIngestionService;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Service
@AllArgsConstructor
public class GFullTextIngestionServiceImpl implements IGFullTextIngestionService {
	private final OpenSearchFullTextChunkIndexService indexService;

	@Override
	public void deleteDocuments(List<FullTextDocument> documents) throws FullTextException {
		try {
			indexService.deleteByDocuments(documents);
		} catch (OpenSearchException | IOException e) {
			throw new FullTextException("Exception in deleteDocuments", e);
		}

	}

	@Override
	public void upsert(List<FullTextChunk> chunks) throws FullTextException {
		try {
			indexService.bulkUpsertChunks(chunks);
		} catch (OpenSearchException | IOException e) {
			throw new FullTextException("Exception in deleteDocuments", e);
		}

	}

}
