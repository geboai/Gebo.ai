package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;

import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

public interface IDocumentsCacheService {

	public TypedInputStream streamDocument(StreamingPurpose streamingPurpose, IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, IOException;
}
