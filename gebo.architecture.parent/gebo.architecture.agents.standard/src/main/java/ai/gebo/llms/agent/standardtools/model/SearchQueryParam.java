package ai.gebo.llms.agent.standardtools.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import ai.gebo.architecture.search.model.SearchQuery;
import lombok.Data;

@Data
public class SearchQueryParam {
	private List<SearchQuery> query;
	@JsonPropertyDescription("Number of elements returned")
	private int topK;
	@JsonPropertyDescription("Number of tokens for each document content sample")
	private int textSampleTokens = 250;
}
