package ai.gebo.architecture.search.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.architecture.search.service.INativeQueryObject;
import lombok.Data;

@Data
public class SearchWithResults {
	SearchQuery searchQuery = null;
	INativeQueryObject nativeQueryObject = null;
	List<SearchResult> results = new ArrayList<SearchResult>();
}