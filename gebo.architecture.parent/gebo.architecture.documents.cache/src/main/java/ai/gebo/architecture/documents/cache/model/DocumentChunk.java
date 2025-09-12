package ai.gebo.architecture.documents.cache.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class DocumentChunk {
	private String originalDocumentCode = null;
	private String id = null;
	private String mimeType = null;
	private DocumentChunkType chunkType = null;
	private String chunkData = null;
	private Map<String, Object> metaData = new HashMap<String, Object>();

	public static DocumentChunk ofText(String originalDocumentCode,String id, String mimeType, String text, Map<String, Object> metaData) {
		DocumentChunk chunk = new DocumentChunk();
		chunk.setId(id);
		chunk.setOriginalDocumentCode(originalDocumentCode);
		chunk.setChunkType(DocumentChunkType.TEXT);
		if (metaData != null)
			chunk.getMetaData().putAll(metaData);
		chunk.chunkData = text;
		chunk.mimeType = mimeType;
		return chunk;
	}

	public static DocumentChunk ofText(String originalDocumentCode,String text, Map<String, Object> metaData) {
		return ofText(originalDocumentCode,UUID.randomUUID().toString(), "text/plain", text, metaData);
	}
}
