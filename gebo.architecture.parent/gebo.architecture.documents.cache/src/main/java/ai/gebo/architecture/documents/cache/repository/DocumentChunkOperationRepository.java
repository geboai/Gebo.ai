package ai.gebo.architecture.documents.cache.repository;

import java.util.List;

import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntryRepository;
import ai.gebo.architecture.documents.cache.service.impl.model.DocumentChunkOperation;

public interface DocumentChunkOperationRepository extends AbstractCachedEntryRepository<DocumentChunkOperation> {
	List<DocumentChunkOperation> findByOriginalDocumentCode(String code);
}
