package ai.gebo.architecture.documents.cache.service;

import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

@Component
public class ChunkingParametersProviderImpl implements IChunkingParametersProvider {

	public ChunkingParametersProviderImpl() {

	}

	@Override
	public ChunkingParams provideChunkingParams(GDocumentReference reference) {

		return ChunkingParams.defaultParams;
	}

}
