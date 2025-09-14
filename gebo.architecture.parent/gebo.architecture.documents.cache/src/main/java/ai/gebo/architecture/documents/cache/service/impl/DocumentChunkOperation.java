package ai.gebo.architecture.documents.cache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import lombok.Data;

@Document
@Data
public class DocumentChunkOperation extends AbstractCachedEntry {

	@HashIndexed
	private String originalDocumentCode = null;

	private List<String> chunksList = new ArrayList<String>();
	private List<AbstractChunkingSpecs> chunkingSpecs = new ArrayList<AbstractChunkingSpecs>();
	private boolean enrichWithMetaData = false;
	private long totalBytesSize = 0l, totalTokensSize = 0l;
	public DocumentChunkOperation() {
		id = UUID.randomUUID().toString();
	}

}
