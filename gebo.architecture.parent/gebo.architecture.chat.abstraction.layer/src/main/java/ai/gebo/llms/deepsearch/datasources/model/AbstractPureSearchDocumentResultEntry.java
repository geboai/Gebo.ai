package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractPureSearchDocumentResultEntry<DocumentType extends IGComponentOriginatedDocument> {
	private final DocumentType document;
	private final String sampleText;
}
