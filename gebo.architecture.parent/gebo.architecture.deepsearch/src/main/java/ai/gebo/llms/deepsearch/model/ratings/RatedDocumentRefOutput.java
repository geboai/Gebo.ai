package ai.gebo.llms.deepsearch.model.ratings;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import lombok.Data;

@Data
@JsonClassDescription("document reference output with rating")
public class RatedDocumentRefOutput {
	@JsonPropertyDescription("unique id of the referred document")
	private String itemId = null;
	@JsonPropertyDescription("relevance rating of the referred document for the user question and the context")
	private Integer relevanceScore = null;
	@JsonPropertyDescription("confidence in the rating")
	private Float confidence = null;
}