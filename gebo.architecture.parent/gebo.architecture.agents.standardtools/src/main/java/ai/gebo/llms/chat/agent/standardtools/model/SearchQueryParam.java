package ai.gebo.llms.chat.agent.standardtools.model;

import ai.gebo.architecture.search.model.SearchQuery;
import lombok.Data;
@Data
public class SearchQueryParam {
	private SearchQuery query;
	private int topK;
	private Integer textSampleTokens=null;
}
