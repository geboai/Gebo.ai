package ai.gebo.architecture.documents.cache.service;

import java.util.List;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IChunkingParametersProvider {
	

	public ChunkingParams provideChunkingParams(GDocumentReference reference);
}
