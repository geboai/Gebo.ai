package ai.gebo.architecture.documents.cache.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class ChunkingParams {
	public ChunkingParams() {

	}

	ChinkingPolicy chunkingPolicy = null;
	Integer tokensThreashold = null;
	Integer keywordHits = null;
	List<String> matchingKeywords = new ArrayList<String>();
	List<AbstractChunkingSpecs> chunkingSpecs = new ArrayList<AbstractChunkingSpecs>();
	private boolean enrichWithMetaData;
	private long tokensPerChunkSet;
	public final static ChunkingParams defaultParams = new ChunkingParams(ChinkingPolicy.SPLIT_CHUNKS, null, null, null,
			List.of(new TextChunkingSpecs()), true, 50000);
}
