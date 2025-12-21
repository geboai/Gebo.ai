package ai.gebo.llms.deepsearch.datasources.model;

import lombok.Data;

@Data
public class AnalyzedSearchResult {
	private Boolean emptyResult = null;
	private String analyzedResult = null;
}