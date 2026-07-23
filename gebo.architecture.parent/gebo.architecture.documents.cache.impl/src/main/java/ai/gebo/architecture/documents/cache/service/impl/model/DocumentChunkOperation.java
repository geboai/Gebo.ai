package ai.gebo.architecture.documents.cache.service.impl.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.service.impl.AbstractCachedEntry;
import lombok.Data;

@Document
@Data
public class DocumentChunkOperation extends AbstractCachedEntry {

	@HashIndexed
	private String originalDocumentCode = null;
	private List<String> chunkSetsList = new ArrayList<String>();
	private List<AbstractChunkingSpecs> chunkingSpecs = new ArrayList<AbstractChunkingSpecs>();
	private List<String> matchingKeywords = null;
	private boolean enrichWithMetaData = false;
	private ChunkingPolicy chunkingPolicy = null;
	private long totalBytesSize = 0l, totalTokensSize = 0l;
	private int totalChunks = 0;
	@HashIndexed
	private String chunkingSessionId = null;

	public DocumentChunkOperation() {
		id = UUID.randomUUID().toString();
	}

}
