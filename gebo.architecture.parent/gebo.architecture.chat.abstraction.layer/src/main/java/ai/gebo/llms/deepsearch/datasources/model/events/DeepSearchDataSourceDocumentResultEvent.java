package ai.gebo.llms.deepsearch.datasources.model.events;

import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import lombok.Data;

@Data
public class DeepSearchDataSourceDocumentResultEvent
		extends AbstractDeepSearchEvent<SearchResult, DeepSearchDataSourceDocumentResult> {

}