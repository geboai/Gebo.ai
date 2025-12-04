package ai.gebo.architecture.documents.cache.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class DocumentChunksSet {
	String id = UUID.randomUUID().toString();
	long totalTokens=0l, totalBytes=0l;
	List<DocumentChunk> chunks = new ArrayList<DocumentChunk>();
}