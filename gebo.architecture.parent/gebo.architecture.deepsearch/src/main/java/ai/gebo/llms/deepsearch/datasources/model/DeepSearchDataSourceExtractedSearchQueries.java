package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.SearchQuery;
import lombok.Data;

@Data
public class DeepSearchDataSourceExtractedSearchQueries {
	private Boolean searchIsUnnecessary = null;
	private List<SearchQuery> searchQuery = new ArrayList<SearchQuery>();
}