package ai.gebo.architecture.search.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchWithResults {
	SearchQuery searchQuery = null;
	Object nativeQueryObject = null;
	List<SearchResult> results = new ArrayList<SearchResult>();
}