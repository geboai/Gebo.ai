package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.architecture.search.model.SearchResult;

public class PureSearchExternalDataSourceResultEntry extends AbstractPureSearchDocumentResultEntry<SearchResult> {

	public PureSearchExternalDataSourceResultEntry(SearchResult document, String sampleText) {
		super(document, sampleText);

	}

}
