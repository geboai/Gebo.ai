package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;

import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

public interface IDocumentsCacheService {

	public TypedInputStream streamDocument(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, IOException;

	/**
	 * The same, the copy kept for a chunking session: it is released with the session
	 * (see {@link #releaseSession(String)}), a document downloaded for a tool, a deep
	 * search or an ingestion job kept no longer than its procedure.
	 */
	public default TypedInputStream streamDocument(StreamingPurpose streamingPurpose,
			IGComponentOriginatedDocument reference, String chunkingSessionId)
			throws DocumentCacheAccessException, IOException {
		return streamDocument(streamingPurpose, reference);
	}

	/** The copies kept for a chunking session released: their records and their files. */
	public default void releaseSession(String chunkingSessionId) {
	}
}
