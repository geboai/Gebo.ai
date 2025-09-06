package ai.gebo.architecture.documents.cache.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DocumentChunkingResponse {
	private String id = UUID.randomUUID().toString();
	private DocumentChunk currentChunk = null;
	private String nextChunkId = null;
	private boolean empty;
	
}
