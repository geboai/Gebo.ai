package ai.gebo.architecture.documents.cache.model;

import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IDocumentChunkWithRef {
	public DocumentChunk getChunk();

	public IGComponentOriginatedDocument getDocumentRef();

	public GUserMessage getErrorMessage();

	public boolean isErrorState();

	@AllArgsConstructor
	@Getter
	static class DocumentChunkWithRefImpl implements IDocumentChunkWithRef {
		final DocumentChunk chunk;
		final IGComponentOriginatedDocument documentRef;
		final boolean errorState;
		final GUserMessage errorMessage;
	}

	public static IDocumentChunkWithRef of(DocumentChunk chunk, IGComponentOriginatedDocument documentRef) {
		return new DocumentChunkWithRefImpl(chunk, documentRef, false, null);
	}

	public static IDocumentChunkWithRef ofError(IGComponentOriginatedDocument documentRef, String message,
			Throwable exception) {
		return new DocumentChunkWithRefImpl(null, documentRef, true, GUserMessage.errorMessage(message, exception));
	}
}