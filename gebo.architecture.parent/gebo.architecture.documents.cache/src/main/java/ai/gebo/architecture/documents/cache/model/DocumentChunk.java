package ai.gebo.architecture.documents.cache.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ai.gebo.model.DocumentMetaInfos;
import lombok.Data;

@Data
public class DocumentChunk {
	private String originalDocumentCode = null;
	private String id = null;
	private String mimeType = null;
	private DocumentChunkType chunkType = null;
	private String chunkData = null;
	private Map<String, Object> metaData = new HashMap<String, Object>();
	private Long tokensSize = null, bytesSize = null;
	private Long chunkPosition = null;
	private Long chunksCount = null;
	private String chunkingSessionId = null;

	public static DocumentChunk ofText(String originalDocumentCode, String id, String mimeType, String text,
			Map<String, Object> metaData) {
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

	public static DocumentChunk ofText(String originalDocumentCode, String text, Map<String, Object> metaData) {
		return ofText(originalDocumentCode, UUID.randomUUID().toString(), "text/plain", text, metaData);
	}

	/**
	 * A chunk holding a media of the document as it is - an image, a recording, a
	 * video - whole: its bytes in base64 as the chunk's data, its mime type, and the
	 * metadata the document's texts have. Null for a mime type that is none of them.
	 */
	public static DocumentChunk ofMedia(String originalDocumentCode, String mimeType, byte[] data,
			Map<String, Object> metaData) {
		final DocumentChunkType type = DocumentChunkType.ofMediaMimeType(mimeType);
		if (type == null || data == null) {
			return null;
		}
		DocumentChunk chunk = new DocumentChunk();
		chunk.setId(UUID.randomUUID().toString());
		chunk.setOriginalDocumentCode(originalDocumentCode);
		chunk.setChunkType(type);
		if (metaData != null)
			chunk.getMetaData().putAll(metaData);
		chunk.chunkData = java.util.Base64.getEncoder().encodeToString(data);
		chunk.mimeType = mimeType;
		return chunk;
	}

	/** Whether the chunk holds text; a chunk written before the kinds existed holds text. */
	public boolean holdsText() {
		return chunkType == null || chunkType == DocumentChunkType.TEXT;
	}

	/** The bytes of the media a media chunk holds; null for a text chunk. */
	public byte[] mediaBytes() {
		return holdsText() || chunkData == null ? null : java.util.Base64.getDecoder().decode(chunkData);
	}

	public void setChunkPosition(Long chunkPosition) {
		this.chunkPosition = chunkPosition;
		if (metaData != null && chunkPosition != null) {
			metaData.put(DocumentMetaInfos.GEBO_CHUNK_POSITION, chunkPosition);
		}
	}

	public void setChunksCount(Long chunksCount) {
		this.chunksCount = chunksCount;
		if (metaData != null && chunksCount != null) {
			metaData.put(DocumentMetaInfos.GEBO_CHUNKS_COUNT, chunksCount);
		}
	}
}
