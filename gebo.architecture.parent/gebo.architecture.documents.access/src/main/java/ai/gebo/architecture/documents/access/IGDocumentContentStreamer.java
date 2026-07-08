package ai.gebo.architecture.documents.access;

import java.io.IOException;

import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

public interface IGDocumentContentStreamer {
	public TypedInputStream streamContent(StreamingPurpose purpose, IGComponentOriginatedDocument document)
			throws DocumentContentStreamerException, IOException;
}
