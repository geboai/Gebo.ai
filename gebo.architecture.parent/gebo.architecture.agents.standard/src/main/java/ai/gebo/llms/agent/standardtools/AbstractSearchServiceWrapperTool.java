package ai.gebo.llms.agent.standardtools;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallback;

import ai.gebo.architecture.ai.model.ToolReference;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.agent.standardtools.model.SearchResultSample;
import ai.gebo.llms.agent.standardtools.model.SearchResultSample.SearchResultSampleList;
import lombok.AllArgsConstructor;
import reactor.core.publisher.ParallelFlux;

@AllArgsConstructor
public abstract class AbstractSearchServiceWrapperTool {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSearchServiceWrapperTool.class);
	private final IDocumentsChunkService chunkingService;

	public abstract ToolCallback toTool();

	public abstract ToolReference toToolReference();

	protected SearchResultSampleList loadSamples(List<SearchResult> results, int textSampleTokens) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin loadSamples(...) results:" + (results != null ? results.size() : 0)
					+ " textSampleTokens:" + textSampleTokens);
		}
		final String session = chunkingService.createChunkingSession("search:" + UUID.randomUUID().toString());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Created chunking session:" + session);
		}
		ChunkingParams params = new ChunkingParams();
		ParallelFlux<IDocumentChunkWithRef> stream = chunkingService.streamChunks(results, params, session, 4)
				.doOnComplete(() -> {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Disposing chunking session:" + session);
					}
					chunkingService.disposeChunkingSession(session);
				});
		ParallelFlux<SearchResultSample> out = stream.map(AbstractSearchServiceWrapperTool::toSample);
		SearchResultSampleList sampleList = new SearchResultSampleList(out.sequential().buffer().blockLast());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End loadSamples(...) for session:" + session);
		}
		return sampleList;
	}

	static SearchResultSample toSample(IDocumentChunkWithRef object) {
		return null;
	}
}
