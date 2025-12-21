package ai.gebo.llms.deepsearch.datasources.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchResults {
	SearchQuery searchQuery = null;
	List<SearchResult> results = new ArrayList<SearchResult>();
}