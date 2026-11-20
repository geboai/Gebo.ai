package ai.gebo.architecture.documents.cache.service;

import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkType;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

@Component
public class ChunkingParametersProviderImpl implements IChunkingParametersProvider {

	public ChunkingParametersProviderImpl() {

	}

	@Override
	public ChunkingParams provideChunkingParams(GDocumentReference reference) {
		if (!imagesIngestionEnabled && !recordingsIngestionEnabled && !videosIngestionEnabled) {
			return ChunkingParams.defaultParams;
		}
		// the ingestion asks for the media the installation takes, beside the texts: the
		// chunking returns them as they are, whole, among the chunks
		final java.util.List<AbstractChunkingSpecs> specs = new java.util.ArrayList<>(
				ChunkingParams.defaultParams.getChunkingSpecs());
		if (imagesIngestionEnabled) {
			specs.add(new AbstractChunkingSpecs(DocumentChunkType.IMAGE));
		}
		if (recordingsIngestionEnabled) {
			specs.add(new AbstractChunkingSpecs(DocumentChunkType.AUDIO));
		}
		if (videosIngestionEnabled) {
			specs.add(new AbstractChunkingSpecs(DocumentChunkType.VIDEO));
		}
		final ChunkingParams defaults = ChunkingParams.defaultParams;
		return new ChunkingParams(defaults.getChunkingPolicy(), defaults.getTokensThreashold(), defaults.getKeywordHits(),
				defaults.getMatchingKeywords(), specs, defaults.isEnrichWithMetaData(), defaults.getTokensPerChunkSet(),
				defaults.getSampledTokens(), defaults.isSamplingMode());
	}

	/** The switches of the installation: which media of the documents the ingestion takes. */
	@org.springframework.beans.factory.annotation.Value("${ai.gebo.images.ingestion-enabled:false}")
	private boolean imagesIngestionEnabled = false;
	@org.springframework.beans.factory.annotation.Value("${ai.gebo.recordings.ingestion-enabled:false}")
	private boolean recordingsIngestionEnabled = false;
	@org.springframework.beans.factory.annotation.Value("${ai.gebo.videos.ingestion-enabled:false}")
	private boolean videosIngestionEnabled = false;

}
