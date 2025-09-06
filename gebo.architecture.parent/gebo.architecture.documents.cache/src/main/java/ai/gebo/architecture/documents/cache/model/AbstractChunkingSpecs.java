package ai.gebo.architecture.documents.cache.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbstractChunkingSpecs {
	private final DocumentChunkType chunkType;
}
