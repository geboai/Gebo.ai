package ai.gebo.architecture.search.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
public class BaseSearchResultsExtractionDataType {
	@JsonPropertyDescription(value = "MD text format Extracted relevant content for the user question and actual context/consolidated infos")
	private String extractedRelevantContent = null;
	@JsonPropertyDescription(value = "True if actual content extraction is relevant for the user question or to enrich related context")
	private Boolean contentIsRelevant = null;

}
