package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

public class PureSearchInternalKnowledgeBaseResultEntry
		extends AbstractPureSearchDocumentResultEntry<GDocumentReference> {

	public PureSearchInternalKnowledgeBaseResultEntry(GDocumentReference document, String sampleText) {
		super(document, sampleText);

	}

}
