package ai.gebo.architecture.documents.cache.service;

import java.util.List;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IChunkingParametersProvider {
	@Getter
	@AllArgsConstructor
	public static class ChunkingParams {
		private final List<AbstractChunkingSpecs> specs;
		private final boolean enrichWithMetaData;
	}

	public ChunkingParams provideChunkingParams(GDocumentReference reference);
}
