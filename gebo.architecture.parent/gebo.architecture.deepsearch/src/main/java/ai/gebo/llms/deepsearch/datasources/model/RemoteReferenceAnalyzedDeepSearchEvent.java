package ai.gebo.llms.deepsearch.datasources.model;

import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import lombok.Data;

@Data
public class RemoteReferenceAnalyzedDeepSearchEvent
		extends AbstractDeepSearchEvent<SearchResult, AnalyzedSearchResult> {

}