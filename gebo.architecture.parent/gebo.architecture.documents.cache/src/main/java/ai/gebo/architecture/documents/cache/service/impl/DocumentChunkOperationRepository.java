package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentChunkOperationRepository extends MongoRepository<DocumentChunkOperation, String> {
	List<DocumentChunkOperation> findByOriginalDocumentCode(String code);
}
