package ai.gebo.llms.agent.standardtools;

import java.util.List;
import java.util.UUID;

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
	private final IDocumentsChunkService chunkingService;

	public abstract ToolCallback toTool();

	public abstract ToolReference toToolReference();

	protected SearchResultSampleList loadSamples(List<SearchResult> results, int textSampleTokens) {
		final String session = chunkingService.createChunkingSession("search:" + UUID.randomUUID().toString());
		ChunkingParams params = new ChunkingParams();
		ParallelFlux<IDocumentChunkWithRef> stream = chunkingService.streamChunks(results, params, session, 4)
				.doOnComplete(() -> {
					chunkingService.disposeChunkingSession(session);
				});
		ParallelFlux<SearchResultSample> out = stream.map(AbstractSearchServiceWrapperTool::toSample);
		return new SearchResultSampleList(out.sequential().buffer().blockLast());
	}

	static SearchResultSample toSample(IDocumentChunkWithRef object) {
		return null;
	}
}
