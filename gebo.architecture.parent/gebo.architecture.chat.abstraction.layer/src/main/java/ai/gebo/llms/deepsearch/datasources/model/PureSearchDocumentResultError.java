package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import lombok.Getter;
@Getter
public class PureSearchDocumentResultError extends AbstractPureSearchDocumentResultEntry {
	private final GUserMessage userMessage;
	public PureSearchDocumentResultError(IGComponentOriginatedDocument document, String sampleText, GUserMessage userMessage) {
		super(document, sampleText);
		this.userMessage = userMessage;
	}

}
