package ai.gebo.architecture.documents.cache.repository;

import java.util.stream.Stream;

import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntryRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentCacheEntry;

public interface DocumentCacheEntryRepository extends AbstractCachedEntryRepository<DocumentCacheEntry> {
	public Stream<DocumentCacheEntry> findByChunkingSessionId(String chunkingSessionId);

	public void deleteByChunkingSessionId(String chunkingSessionId);
}
