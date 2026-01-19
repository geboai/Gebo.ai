package ai.gebo.architecture.documents.cache.model;

import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IDocumentChunkWithRef {
	public DocumentChunk getChunk();

	public IGComponentOriginatedDocument getDocumentRef();

	@AllArgsConstructor
	@Getter
	static class DocumentChunkWithRefImpl implements IDocumentChunkWithRef {
		final DocumentChunk chunk;
		final IGComponentOriginatedDocument documentRef;
	}

	public static IDocumentChunkWithRef of(DocumentChunk chunk, IGComponentOriginatedDocument documentRef) {
		return new DocumentChunkWithRefImpl(chunk, documentRef);
	}
}