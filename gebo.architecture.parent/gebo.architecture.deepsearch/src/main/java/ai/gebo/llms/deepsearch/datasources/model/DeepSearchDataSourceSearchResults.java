package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import lombok.Data;

@Data
public class DeepSearchDataSourceSearchResults {
	SearchQuery searchQuery = null;
	List<SearchResult> results = new ArrayList<SearchResult>();
}