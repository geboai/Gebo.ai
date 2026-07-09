package ai.gebo.architecture.documents.cache.repository;

import java.util.List;

import ai.gebo.architecture.documents.cache.service.impl.model.ChunkingSession;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;

public interface ChunkingSessionRepository extends IGBaseMongoDBRepository<ChunkingSession> {
	@Override
	default Class<ChunkingSession> getManagedType() {
		return ChunkingSession.class;
	}

	public List<ChunkingSession> findByChunkingReference(String externalReference);
}
