package ai.gebo.architecture.documents.cache.repository;

import java.util.List;
import java.util.stream.Stream;

import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntryRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;

public interface DocumentChunkOperationRepository extends AbstractCachedEntryRepository<DocumentChunkOperation> {
	List<DocumentChunkOperation> findByOriginalDocumentCode(String code);

	public Stream<DocumentChunkOperation> findByChunkingSessionId(String chunkingSessionId);

	public void deleteByChunkingSessionId(String chunkingSessionId);
}
