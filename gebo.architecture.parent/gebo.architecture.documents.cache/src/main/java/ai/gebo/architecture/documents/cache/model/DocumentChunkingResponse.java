package ai.gebo.architecture.documents.cache.model;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DocumentChunkingResponse {
	private String id = UUID.randomUUID().toString();
	private long totalBytesSize = 0, totalTokensSize = 0;
	private int totalChunksNumber = 0;
	private DocumentChunksSet currentChunkSet = null;
	private String nextChunkSetId = null;
	private boolean empty;

}
