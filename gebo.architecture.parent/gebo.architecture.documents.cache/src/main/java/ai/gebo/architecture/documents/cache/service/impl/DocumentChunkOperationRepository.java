package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;

public interface DocumentChunkOperationRepository extends AbstractCachedEntryRepository<DocumentChunkOperation> {
	List<DocumentChunkOperation> findByOriginalDocumentCode(String code);
}
